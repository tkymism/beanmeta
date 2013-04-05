package com.tkym.labs.beanmeta;

import com.tkym.labs.beanmeta.BeanMetaUtils;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.tkym.labs.beans.Person;
import com.tkym.labs.beans.PersonMeta;

public class BeanMetaUtilsTest {
	Person person(long id, String name){
		Person bean = new Person();
		bean.setId(id);
		bean.setName(name);
		return bean;
	}
	
	@Test
	public void testCompareCase001(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(2L,"foo2");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().id).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(-1));
	}

	@Test
	public void testCompareCase002(){
		Person b1 = person(2L,"foo2");
		Person b2 = person(1L,"foo1");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().id).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(1));
	}

	@Test
	public void testCompareCase003(){
		Person b1 = person(1L,"foo2");
		Person b2 = person(1L,"foo1");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().id).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(0));
	}

	@Test
	public void testCompareCase011(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(2L,"foo2");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().name).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(-1));
	}
	
	@Test
	public void testCompareCase012(){
		Person b1 = person(1L,"foo2");
		Person b2 = person(1L,"foo1");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().name).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(1));
	}
	
	@Test
	public void testCompareCase013(){
		Person b1 = person(1L,"foo2");
		Person b2 = person(2L,"foo2");
		int actual = BeanMetaUtils.get().beanComparator(PersonMeta.get().name).compare(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(0));
	}

	@Test
	public void testEqualsCase001(){
		Person b1 = person(1L,"foo2");
		Person b2 = person(2L,"foo2");
		boolean actual = BeanMetaUtils.get().meta(PersonMeta.get().name).same(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(true));
	}

	@Test
	public void testEqualsCase002(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(2L,"foo2");
		boolean actual = BeanMetaUtils.get().meta(PersonMeta.get().name).same(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(false));
	}

	@Test
	public void testEqualsCase011(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(1L,"foo2");
		boolean actual = BeanMetaUtils.get().equalsKey(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(true));
	}

	@Test
	public void testEqualsCase012(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(2L,"foo2");
		boolean actual = BeanMetaUtils.get().equalsKey(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(false));
	}

	@Test
	public void testEqualsCase021(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(2L,"foo2");
		boolean actual = BeanMetaUtils.get().equals(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(false));
	}

	@Test
	public void testEqualsCase022(){
		Person b1 = person(1L,"foo1");
		Person b2 = person(1L,"foo1");
		boolean actual = BeanMetaUtils.get().equals(b1, b2);
		Assert.assertThat(actual, CoreMatchers.is(true));
	}
}