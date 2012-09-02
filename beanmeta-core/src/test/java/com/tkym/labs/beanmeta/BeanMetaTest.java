package com.tkym.labs.beanmeta;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tkym.labs.beans.Account;
import com.tkym.labs.beans.AccountMeta;
import com.tkym.labs.beans.Bill;
import com.tkym.labs.beans.BillMeta;
import com.tkym.labs.beans.DataProvider;
import com.tkym.labs.beans.Person;
import com.tkym.labs.beans.PersonMeta;

public class BeanMetaTest {
	private static Map<Key<Person,Long>, Person> PERSON_MAP;
	private static Map<Key<Account,String>, Account> ACCOUNT_MAP;
	private static Map<Key<Bill,Integer>, Bill> BILL_MAP;

	@Test
	public void testBeanMetaCase001(){
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
}
