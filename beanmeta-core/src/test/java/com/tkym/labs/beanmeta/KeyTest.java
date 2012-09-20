package com.tkym.labs.beanmeta;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.tkym.labs.beans.Account;
import com.tkym.labs.beans.AccountMeta;
import com.tkym.labs.beans.Bill;
import com.tkym.labs.beans.BillMeta;
import com.tkym.labs.beans.Person;
import com.tkym.labs.beans.PersonMeta;

public class KeyTest {
	
	@Test
	public void testRankCase001(){
		Key<Person,Long> personKey = PersonMeta.get().key(null, 1L);
		Assert.assertThat(personKey.rank(), CoreMatchers.is(0));
		Key<Account,String> accountKey = AccountMeta.get().key(personKey, "foo@bar.com");
		assertThat(accountKey.rank(), is(1));
	}
	
	@Test
	public void testKeyEqualsCase001(){
		Key<Person,Long> a = PersonMeta.get().key(null, 1L);
		Key<Person,Long> b = PersonMeta.get().key(null, 1L);
		assertTrue(a.equals(b));
	}
	
	@Test
	public void testKeyEqualsCase002(){
		Key<Person,Long> p1_0 = PersonMeta.get().key(null, 1L);
		Key<Person,Long> p1_1 = PersonMeta.get().key(null, 1L);
		Key<Account, String> a1_0 = AccountMeta.get().key(p1_0, "foo1");
		Key<Account, String> a1_1 = AccountMeta.get().key(p1_1, "foo1");
		assertTrue(a1_0.equals(a1_1));
	}
	
	@Test
	public void testKeyEqualsCase003(){
		Key<Person,Long> p1_0 = PersonMeta.get().key(null, 1L);
		Key<Person,Long> p1_1 = PersonMeta.get().key(null, 1L);
		Key<Account, String> a1_0 = AccountMeta.get().key(p1_0, "foo1");
		Key<Account, String> a1_1 = AccountMeta.get().key(p1_1, "foo2");
		assertTrue(a1_0.isSiblingOf(a1_1));
	}

	@Test
	public void testIsAncestorOfCase001(){
		Key<Person,Long> p = PersonMeta.get().key(null, 1L);
		Key<Account, String> a = AccountMeta.get().key(p, "foo1");
		Key<Bill, Integer> b = BillMeta.get().key(a, 3);
		assertTrue(a.isAncestorOf(b));
		assertTrue(p.isAncestorOf(b));
		assertTrue(PersonMeta.get().key(null, 1L).isAncestorOf(b));
		assertFalse(PersonMeta.get().key(null, 2L).isAncestorOf(b));
	}
	
	@Test
	public void testIsDescendantOfCase001(){
		Key<Person,Long> p = PersonMeta.get().key(null, 1L);
		Key<Account, String> a = AccountMeta.get().key(p, "foo1");
		Key<Bill, Integer> b = BillMeta.get().key(a, 3);
		assertTrue(b.isDescendantOf(a));
		assertTrue(b.isDescendantOf(p));
		assertTrue(b.isDescendantOf(PersonMeta.get().key(null, 1L)));
		assertFalse(b.isDescendantOf(PersonMeta.get().key(null, 2L)));
	}
	
	@Test
	public void testCompareTo_Case001(){
		PersonMeta PERSON = PersonMeta.get();
		Key<Person, Long> current = PERSON.key(null, 0L);
		for (long i=1L; i<100; i++)
			assertThat(current.compareTo(PERSON.key(null, i)), is(-1));
	}
	
	@Test
	public void testCompareTo_Case002(){
		PersonMeta PERSON = PersonMeta.get();
		Key<Person, Long> current = PERSON.key(null, 100L);
		for (long i=0L; i<99; i++)
			assertThat(current.compareTo(PERSON.key(null, i)), is(1));
	}
	@Test
	public void testCompareTo_Case003(){
		PersonMeta PERSON = PersonMeta.get();
		Key<Person, Long> current = PERSON.key(null, null);
		for (long i=0L; i<99; i++)
			assertThat(current.compareTo(PERSON.key(null, i)), is(-1));
	}
	
	@Test
	public void testKeyBuilderCase001(){
		Key<Bill,Integer> key0 = 
				KeyBuilder.root().
				meta(PersonMeta.get()).is(0L).
				meta(AccountMeta.get()).is("222@email.com").
				meta(BillMeta.get()).is(300).
				build();
		assertThat(key0.value(), is(300));
		assertThat(key0.getBeanMeta(), 
				sameInstance((BeanMeta<Bill,Integer>)BillMeta.get()));
		@SuppressWarnings("unchecked")
		Key<Account,String> key1 = (Key<Account,String>) key0.getParent();
		assertThat(key1.value(), is("222@email.com"));
		assertThat(key1.getBeanMeta(), 
				sameInstance((BeanMeta<Account,String>)AccountMeta.get()));
		@SuppressWarnings("unchecked")
		Key<Person,Long> key2 = (Key<Person,Long>) key1.getParent();
		assertThat(key2.value(), is(0L));
		assertThat(key2.getBeanMeta(), 
				sameInstance((BeanMeta<Person,Long>)PersonMeta.get()));
	}
	
	@Test
	public void testKeyBuilderCase002(){
		Key<Person, Long> parent = 
				KeyBuilder.root().
				meta(PersonMeta.get()).
				is(0L).build();
		Key<Bill,Integer> key0 = 
				KeyBuilder.parent(parent).
				meta(AccountMeta.get()).is("222@email.com").
				meta(BillMeta.get()).is(300).
				build();
		assertThat(key0.value(), is(300));
		assertThat(key0.getBeanMeta(), 
				sameInstance((BeanMeta<Bill,Integer>)BillMeta.get()));
		@SuppressWarnings("unchecked")
		Key<Account,String> key1 = (Key<Account,String>) key0.getParent();
		assertThat(key1.value(), is("222@email.com"));
		assertThat(key1.getBeanMeta(), 
				sameInstance((BeanMeta<Account,String>)AccountMeta.get()));
		@SuppressWarnings("unchecked")
		Key<Person,Long> key2 = (Key<Person,Long>) key1.getParent();
		assertThat(key2.value(), is(0L));
		assertThat(key2.getBeanMeta(), 
				sameInstance((BeanMeta<Person,Long>)PersonMeta.get()));
	}
	
	DecimalFormat format000 = new DecimalFormat("000");
	@Test
	public void testCompareTo_Case004(){
		PersonMeta PERSON = PersonMeta.get();
		AccountMeta ACCOUNT = AccountMeta.get();
		Key<Account, String> current = 
				KeyBuilder.root().
					meta(PERSON).is(0L).
					meta(ACCOUNT).is("a000@email.com").
					build();
		for (long i=1L; i<10; i++)
			for (int j=0; j<10; j++)
				assertThat(current.
						compareTo(
								KeyBuilder.root().
									meta(PERSON).is(i).
									meta(ACCOUNT).is("a"+format000.format(j)+"@email.com").
									build()
								),
							is(-1));
	}
	
}