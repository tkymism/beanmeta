package com.tkym.labs.beanmeta;

import com.tkym.labs.beanmeta.Key;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.tkym.labs.beanmeta.beans.Account;
import com.tkym.labs.beanmeta.beans.AccountMeta;
import com.tkym.labs.beanmeta.beans.Bill;
import com.tkym.labs.beanmeta.beans.BillMeta;
import com.tkym.labs.beanmeta.beans.Person;
import com.tkym.labs.beanmeta.beans.PersonMeta;

public class KeyTest {
	
	@Test
	public void testRankCase001(){
		Key<Person,Long> personKey = PersonMeta.get().key(null, 1L);
		Assert.assertThat(personKey.rank(), CoreMatchers.is(0));
		Key<Account,String> accountKey = AccountMeta.get().key(personKey, "foo@bar.com");
		Assert.assertThat(accountKey.rank(), CoreMatchers.is(1));
	}
	
	@Test
	public void testKeyEqualsCase001(){
		Key<Person,Long> a = PersonMeta.get().key(null, 1L);
		Key<Person,Long> b = PersonMeta.get().key(null, 1L);
		Assert.assertTrue(a.equals(b));
	}
	
	@Test
	public void testKeyEqualsCase002(){
		Key<Person,Long> p1_0 = PersonMeta.get().key(null, 1L);
		Key<Person,Long> p1_1 = PersonMeta.get().key(null, 1L);
		Key<Account, String> a1_0 = AccountMeta.get().key(p1_0, "foo1");
		Key<Account, String> a1_1 = AccountMeta.get().key(p1_1, "foo1");
		Assert.assertTrue(a1_0.equals(a1_1));
	}
	
	@Test
	public void testKeyEqualsCase003(){
		Key<Person,Long> p1_0 = PersonMeta.get().key(null, 1L);
		Key<Person,Long> p1_1 = PersonMeta.get().key(null, 1L);
		Key<Account, String> a1_0 = AccountMeta.get().key(p1_0, "foo1");
		Key<Account, String> a1_1 = AccountMeta.get().key(p1_1, "foo2");
		Assert.assertTrue(a1_0.isSiblingOf(a1_1));
	}

	@Test
	public void testIsAncestorOfCase001(){
		Key<Person,Long> p = PersonMeta.get().key(null, 1L);
		Key<Account, String> a = AccountMeta.get().key(p, "foo1");
		Key<Bill, Integer> b = BillMeta.get().key(a, 3);
		Assert.assertTrue(a.isAncestorOf(b));
		Assert.assertTrue(p.isAncestorOf(b));
		Assert.assertTrue(PersonMeta.get().key(null, 1L).isAncestorOf(b));
		Assert.assertFalse(PersonMeta.get().key(null, 2L).isAncestorOf(b));
	}
	
	@Test
	public void testIsDescendantOfCase001(){
		Key<Person,Long> p = PersonMeta.get().key(null, 1L);
		Key<Account, String> a = AccountMeta.get().key(p, "foo1");
		Key<Bill, Integer> b = BillMeta.get().key(a, 3);
		Assert.assertTrue(b.isDescendantOf(a));
		Assert.assertTrue(b.isDescendantOf(p));
		Assert.assertTrue(b.isDescendantOf(PersonMeta.get().key(null, 1L)));
		Assert.assertFalse(b.isDescendantOf(PersonMeta.get().key(null, 2L)));
	}
}