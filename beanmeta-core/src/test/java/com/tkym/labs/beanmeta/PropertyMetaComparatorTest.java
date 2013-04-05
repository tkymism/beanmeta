package com.tkym.labs.beanmeta;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.tkym.labs.beans.HogeMeta;

public class PropertyMetaComparatorTest {
	private static HogeMeta HOGE = HogeMeta.get(); 
	private static BeanMetaUtils UTILS = BeanMetaUtils.get();
	@Test
	public void testPropertyMetaComparator_Case001(){
		assertThat(UTILS.propertyComparator(HOGE.intValue).compare(1, 2), is(-1));
	}
	@Test
	public void testPropertyMetaComparator_Case002(){
		assertThat(UTILS.propertyComparator(HOGE.intValue).compare(1, 1), is(0));
	}
	@Test
	public void testPropertyMetaComparator_Case003(){
		assertThat(UTILS.propertyComparator(HOGE.intValue).compare(1, 0), is(1));
	}
	@Test
	public void testPropertyMetaComparator_Case004(){
		assertThat(UTILS.propertyComparator(HOGE.doubleValue).compare(0.1d, 0.2d), is(-1));
	}
	@Test
	public void testPropertyMetaComparator_Case005(){
		assertThat(UTILS.propertyComparator(HOGE.doubleValue).compare(0.2d, 0.2d), is(0));
	}
	@Test
	public void testPropertyMetaComparator_Case006(){
		assertThat(UTILS.propertyComparator(HOGE.doubleValue).compare(0.2d, 0.1d), is(1));
	}
	@Test
	public void testPropertyMetaComparator_Case007(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).compare("a", "b"), is(-1));
	}
	@Test
	public void testPropertyMetaComparator_Case008(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).compare("b", "b"), is(0));
	}
	@Test
	public void testPropertyMetaComparator_Case009(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).compare("b", "a"), is(1));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPropertyMetaComparatorNullError_Case001(){
		UTILS.propertyComparator(HOGE.stringValue).compare(null, "a");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPropertyMetaComparatorNullError_Case002(){
		UTILS.propertyComparator(HOGE.stringValue).compare("b", null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPropertyMetaComparatorNullError_Case003(){
		UTILS.propertyComparator(HOGE.stringValue).compare(null, null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPropertyMetaComparatorNullError_Case0011(){
		UTILS.propertyComparator(HOGE.stringValue).nullError().compare(null, "a");
	}
	@Test
	public void testPropertyMetaComparatorNullFirst_Case001(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullFirst().compare(null, "a"), is(-1));
	}
	@Test
	public void testPropertyMetaComparatorNullFirst_Case002(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullFirst().compare("a", null), is(1));
	}
	@Test
	public void testPropertyMetaComparatorNullFirst_Case003(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullFirst().compare(null, null), is(0));
	}
	@Test
	public void testPropertyMetaComparatorNullLast_Case001(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullLast().compare(null, "a"), is(1));
	}
	@Test
	public void testPropertyMetaComparatorNullLast_Case002(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullLast().compare("a", null), is(-1));
	}
	@Test
	public void testPropertyMetaComparatorNullLast_Case003(){
		assertThat(UTILS.propertyComparator(HOGE.stringValue).nullLast().compare(null, null), is(0));
	}
}
