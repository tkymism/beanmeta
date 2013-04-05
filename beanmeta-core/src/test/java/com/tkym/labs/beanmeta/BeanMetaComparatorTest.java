package com.tkym.labs.beanmeta;

import static com.tkym.labs.common.ChainComparator.asc;
import static com.tkym.labs.common.ChainComparator.desc;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tkym.labs.beans.Person;
import com.tkym.labs.beans.PersonMeta;

public class BeanMetaComparatorTest {
	private static final PersonMeta PERSON = PersonMeta.get();
	private static final List<Person> PERSON_LIST = new ArrayList<Person>();
	private static final int NAMECHANGE_PER_SIZE = 100;
	private static String createName(int id){
		return "hoge" + id % NAMECHANGE_PER_SIZE;
	}
	@BeforeClass
	public static final void setupClass(){
		int size = 10010;
		for (long i=0; i<size; i++)
			PERSON_LIST.add(p(i, createName((int)i)));
	}
	
	@AfterClass
	public static final void teardownClass(){
		PERSON_LIST.clear();
	}
	
	private static Person p(long id, String name){
		Person p = new Person();
		p.setId(id);
		p.setName(name);
		return p;
	}
	
	@Test
	public void testPropertyMetaComparator(){
		int actual = 
				BeanMetaUtils.get().beanComparator(PERSON.name).
				compare(p(1L, "aaa"), p(1L,"aab"));
		assertThat(actual, is(-1));
	}
	
	@Test
	public void testChainComparatorCase_lessThan(){
		int actual = 
				asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
				asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
				chain().
				compare(p(1L, "aaa"), p(1L,"aab"));
		assertThat(actual, is(-1));
	}

	@Test
	public void testChainComparatorCase_equal(){
		int actual = 
				asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
				asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
				chain().
				compare(p(1L, "aaa"), p(1L,"aaa"));
		assertThat(actual, is(0));
	}

	@Test
	public void testChainComparator_Case_greaterThan(){
		int actual = 
				asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
				asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
				chain().
				compare(p(1L, "aab"), p(1L,"aaa"));
		assertThat(actual, is(1));
	}
	
	@Test
	public void testTreeSet_asc_Case001(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
					asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
					chain()
				);
		
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(2L,"aab"));
		treeSet.add(p(2L,"aaa"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
	}
	
	@Test
	public void testTreeSet_asc_Case002(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
					asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
					chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aaa"));
		
		assertThat(treeSet.size(), is(2));

		Iterator<Person> ite = treeSet.iterator();
		Person p;

		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
	}
	
	@Test
	public void testTreeSet_asc_Case003(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					asc(BeanMetaUtils.get().beanComparator(PERSON.name)).
					asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
					chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aab"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
	}
	
	@Test
	public void testTreeSet_desc_Case001(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					desc(BeanMetaUtils.get().beanComparator(PERSON.name)).
					desc(BeanMetaUtils.get().beanComparator(PERSON.id)).
					chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aab"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
		
 		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
		
 		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
	}
	
	@Test
	public void testTreeSet_desc_Case002(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					desc(BeanMetaUtils.get().beanComparator(PERSON.name)).
					asc(BeanMetaUtils.get().beanComparator(PERSON.id)).
					chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aab"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
	}
	
	@Test
	public void testBeanMetaComparatorCase_lessThan(){
		int actual = 
				builder(PERSON).
				asc(PERSON.name).
				ascKey().chain().
				compare(p(1L, "aaa"), p(1L,"aab"));
		assertThat(actual, is(-1));
	}

	@Test
	public void testBeanMetaComparatorCase_equal(){
		int actual = 
				builder(PERSON).
				asc(PERSON.name).
				ascKey().chain().
				compare(p(1L, "aaa"), p(1L,"aaa"));
		assertThat(actual, is(0));
	}
	
	@Test
	public void testBeanMetaComparator_Case_greaterThan(){
		int actual = 
				builder(PERSON).
				asc(PERSON.name).
				ascKey().chain().
				compare(p(1L, "aab"), p(1L,"aaa"));
		assertThat(actual, is(1));
	}
	
	@Test
	public void testBeanMetaComparator_Case001(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					builder(PERSON).
					desc(PERSON.name).
					ascKey().chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aab"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
	}
	
	@Test
	public void testBeanMetaComparator_Case002(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					builder(PERSON).
					asc(PERSON.name).
					ascKey().chain()
				);
		
		treeSet.add(p(1L,"aaa"));
		treeSet.add(p(1L,"aab"));
		treeSet.add(p(2L,"aaa"));
		treeSet.add(p(2L,"aab"));
		
		assertThat(treeSet.size(), is(4));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aaa"));
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("aab"));
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("aab"));
	}

	@Test
	public void testBeanMetaComparator_CaseAscNullFirst(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					builder(PERSON).
					ascNullFirst(PERSON.name).
					ascKey().chain()
				);
		
		treeSet.add(p(2L,"a"));
		treeSet.add(p(2L,null));
		treeSet.add(p(2L,"b"));
		treeSet.add(p(1L,"b"));
		treeSet.add(p(1L,null));
		treeSet.add(p(1L,"a"));
		
		assertThat(treeSet.size(), is(6));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertNull(p.getName());
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertNull(p.getName());
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("a"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("a"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("b"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("b"));
	}
	
	@Test
	public void testBeanMetaComparator_CaseAscNullLast(){
		TreeSet<Person> treeSet = new TreeSet<Person>(
					builder(PERSON).
					ascNullLast(PERSON.name).
					ascKey().chain()
				);
		
		treeSet.add(p(2L,"a"));
		treeSet.add(p(2L,null));
		treeSet.add(p(2L,"b"));
		treeSet.add(p(1L,"b"));
		treeSet.add(p(1L,null));
		treeSet.add(p(1L,"a"));
		
		assertThat(treeSet.size(), is(6));
		Iterator<Person> ite = treeSet.iterator();
		Person p;
		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("a"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("a"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertThat(p.getName(), is("b"));
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertThat(p.getName(), is("b"));

		p = ite.next();
 		assertThat(p.getId(), is(1L));
 		assertNull(p.getName());
 		
		p = ite.next();
 		assertThat(p.getId(), is(2L));
 		assertNull(p.getName());
	}
	
	static TreeSet<Person> treesetForPerformance;
	
	@Test
	public void testPerformance001(){
		treesetForPerformance = new TreeSet<Person>(
				builder(PERSON).
				asc(PERSON.name).
				ascKey().chain()
			); 
		for (Person p : PERSON_LIST)
			treesetForPerformance.add(p);
		assertThat(treesetForPerformance.size(), is(PERSON_LIST.size()));
	}
	
	@Test
	public void testPerformance002(){
		Iterator<Person> ite = treesetForPerformance.iterator();
		int count = 0;
		Person p = null;
		while(ite.hasNext()){
			p = ite.next();
			count++;
		}
		assertThat(count, is(PERSON_LIST.size()));
		assertThat(p.getName(), is(createName(9999)));
	}
	
	public static <BT,KT extends Comparable<KT>> BeanMetaComparatorBuilder<BT,KT> builder(BeanMeta<BT,KT> beanMeta){
		return BeanMetaUtils.get().comparatorBulder(beanMeta);
	}
}
