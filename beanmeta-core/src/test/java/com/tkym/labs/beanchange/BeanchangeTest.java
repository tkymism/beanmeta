package com.tkym.labs.beanchange;


import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.tkym.labs.beanmeta.Key;
import com.tkym.labs.beans.Account;
import com.tkym.labs.beans.AccountMeta;
import com.tkym.labs.beans.Bill;
import com.tkym.labs.beans.BillMeta;
import com.tkym.labs.beans.DataProvider;
import com.tkym.labs.beans.Person;
import com.tkym.labs.beans.PersonMeta;


public class BeanchangeTest {
	private static Map<Key<Person,Long>, Person> PERSON_MAP;
	private static Map<Key<Account,String>, Account> ACCOUNT_MAP;
	private static Map<Key<Bill,Integer>, Bill> BILL_MAP;

	@BeforeClass
	public static  void testBeanMetaCase001(){
		PERSON_MAP = new HashMap<Key<Person,Long>, Person>();
		ACCOUNT_MAP = new HashMap<Key<Account,String>, Account>();
		BILL_MAP = new HashMap<Key<Bill,Integer>, Bill>();
		for (long i=0; i<100; i++) {
			Person person = DataProvider.create(i);
			Key<Person,Long> personKey = 
					PersonMeta.get().key(null, person.getId());
			PERSON_MAP.put(personKey, person);
			for (int j=0; j<10; j++){
				Account account = DataProvider.create(i, j);
				Key<Account,String> accountKey = 
						AccountMeta.get().
						key(personKey, account.getEmail());
				ACCOUNT_MAP.put(accountKey, account);
				for (int k=0; k<10; k++){
					Bill bill = DataProvider.create(i, j, k);
					Key<Bill,Integer> billKey = 
							BillMeta.get().key(
									accountKey, bill.getNo());
					BILL_MAP.put(billKey, bill);
				}
			}
		}
	}
	
	@Test
	public void testBeanchangeCase001(){
		BeanchangeStore changeStore = new BeanchangeStore();
		for (Key<Person,Long> key : PERSON_MAP.keySet()) 
			changeStore.attach(key, PERSON_MAP.get(key));
		
		Person p1 =
				PERSON_MAP.get(PersonMeta.get().key(null, 1L));
		p1.setName("aaa");
		changeStore.put(PersonMeta.get().key(null, 1L), p1);
		List<BeanchangeModify<Person, Long>> modifyList = 
			changeStore.viewOf(PersonMeta.get()).modify().asList();
		assertThat(modifyList.size(), is(1));
		
		BeanchangeModify<Person, Long> modify = modifyList.get(0);
		assertThat(modify.getAfter().getName(), is("aaa"));
		
		
	}
	
	@Test
	public void testBeanchangeCase002(){
		BeanchangeStore changeStore = new BeanchangeStore();
		for (Key<Person,Long> key : PERSON_MAP.keySet()) 
			changeStore.attach(key, PERSON_MAP.get(key));
		Person p1001 = DataProvider.create(1001L);
		changeStore.put(PersonMeta.get().key(null, p1001.getId()), p1001);
		List<BeanchangeModify<Person, Long>> modifyList = 
			changeStore.viewOf(PersonMeta.get()).modify().asList();
		List<BeanchangeAdd<Person, Long>> addList = 
				changeStore.viewOf(PersonMeta.get()).add().asList();
		List<BeanchangeRemove<Person, Long>> removeList = 
				changeStore.viewOf(PersonMeta.get()).remove().asList();
		List<BeanchangeInit<Person, Long>> initList = 
				changeStore.viewOf(PersonMeta.get()).init().asList();
		assertThat(modifyList.size(), is(0));
		assertThat(initList.size(), is(PERSON_MAP.size()));
		assertThat(addList.size(), is(1));
		assertThat(removeList.size(), is(0));
	}
	
	@Test
	public void testBeanchangeCase003(){
		BeanchangeStore changeStore = new BeanchangeStore();
		for (Key<Person,Long> key : PERSON_MAP.keySet()) 
			changeStore.attach(key, PERSON_MAP.get(key));
		Person p1001 = DataProvider.create(1001L);
		changeStore.put(PersonMeta.get().key(null, p1001.getId()), p1001);
		changeStore.remove(PersonMeta.get().key(null, 50L));
		Key<Person,Long> key32 = PersonMeta.get().key(null, 32L);
		Person p32 = PERSON_MAP.get(key32);
		p32.setName("changedName");
		changeStore.put(key32, p32);
		List<BeanchangeModify<Person, Long>> modifyList = 
			changeStore.viewOf(PersonMeta.get()).modify().asList();
		List<BeanchangeAdd<Person, Long>> addList = 
				changeStore.viewOf(PersonMeta.get()).add().asList();
		List<BeanchangeRemove<Person, Long>> removeList = 
				changeStore.viewOf(PersonMeta.get()).remove().asList();
		List<BeanchangeInit<Person, Long>> initList = 
				changeStore.viewOf(PersonMeta.get()).init().asList();
		assertThat(modifyList.size(), is(1));
		assertThat(initList.size(), is(PERSON_MAP.size()-2));
		assertThat(addList.size(), is(1));
		assertThat(removeList.size(), is(1));
	}
	
	@Test
	public void testBeanchangeCase004(){
		BeanchangeStore changeStore = new BeanchangeStore();
		Key<Person, Long> parent = PersonMeta.get().key(null, 1L);
		Account account0 = new Account();
		account0.setAddress("hogehoge");
		account0.setEmail("foo@gmail.com");
		Key<Account, String> key0 = 
				AccountMeta.get().key(
				parent, 
				account0.getEmail());
		Account account1 = new Account();
		account1.setAddress("hoge");
		account1.setEmail("bar@gmail.com");
		Key<Account, String> key1 = 
				AccountMeta.get().key(
				parent, 
				account1.getEmail());
		changeStore.attach(key0, account0);
		changeStore.put(key1, account1);
		assertThat(
				changeStore.
				viewOf(AccountMeta.get()).
				add().asList().iterator().
				next().getAfter().getAddress(),is("hoge"));
		account0.setAddress("barbar");
		changeStore.put(key0, account0);
		assertThat(
				changeStore.
				viewOf(AccountMeta.get()).
				modify().asList().iterator().
				next().getAfter().getAddress(),is("barbar"));
		changeStore.remove(key0);
		changeStore.remove(key1);
		assertThat(
				changeStore.
				viewOf(AccountMeta.get()).
				remove().asList().iterator().
				next().getBefore().getAddress(),is("hogehoge"));
		assertThat(
				changeStore.
				viewOf(AccountMeta.get()).
				asList().size(), is(1));
		Account account3 = new Account();
		account3.setAddress("hogehoge");
		account3.setEmail("foo@gmail.com");
		changeStore.put(key0, account3);
		assertThat(
				changeStore.
				viewOf(AccountMeta.get()).
				init().asList().iterator().next().getInit().getAddress(), is("hogehoge"));
	}
	
	@Test
	public void testBeanchangeCase010(){
		BeanchangeStore changeStore = new BeanchangeStore();
		for (Key<Person,Long> key : PERSON_MAP.keySet()) 
			changeStore.attach(key, PERSON_MAP.get(key));
		for (Key<Account,String> key : ACCOUNT_MAP.keySet()) 
			changeStore.attach(key, ACCOUNT_MAP.get(key));
		for (Key<Bill,Integer> key : BILL_MAP.keySet()) 
			changeStore.attach(key, BILL_MAP.get(key));
		assertThat(
				changeStore.viewOf(BillMeta.get()).
				init().asList().size(), is(BILL_MAP.size()));
		assertThat(
				changeStore.viewOf(BillMeta.get()).
				add().asList().size(), is(0));
		assertThat(
				changeStore.viewOf(BillMeta.get()).
				remove().asList().size(), is(0));
		assertThat(
				changeStore.viewOf(BillMeta.get()).
				modify().asList().size(), is(0));
		Key<Person, Long> pk = PersonMeta.get().key(null, 10L);
		Key<Account, String> ak = AccountMeta.get().key(pk, "hoge10@email0.com");
		Key<Bill,Integer> bk = BillMeta.get().key(ak, 0);
		assertNotNull(BILL_MAP.get(bk));
		assertNotNull(changeStore.get(bk));
		for (Key<Bill,Integer> key : BILL_MAP.keySet()){
			if (key.value()%10 == 0){
				Bill bill = BILL_MAP.get(key);
				bill.setAmount(-100.0f);
				changeStore.put(key, bill);
			}else if (key.value()%10 == 2){
				changeStore.remove(key);
			}
		}
		List<BeanchangeModify<Bill, Integer>> modifyList = 
				changeStore.viewOf(BillMeta.get()).modify().asList();
		assertThat(modifyList.size(), is(1000));
		for (BeanchangeModify<Bill, Integer> modify : modifyList){
			assertThat(modify.getBefore().getNo()%10, is(0));
			assertThat(modify.getAfter().getAmount(), is(-100.0f));
		}
		List<BeanchangeRemove<Bill, Integer>> remove = 
				changeStore.viewOf(BillMeta.get()).remove().asList();
		assertThat(remove.size(), is(1000));
	}
}