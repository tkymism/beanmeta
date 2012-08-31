package com.tkym.labs.beanmeta;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tkym.labs.beanmeta.IndexedAccessorResolver;
import com.tkym.labs.beanmeta.PropertyMeta;
import com.tkym.labs.beanmeta.PropertyMetaBuilderAsArray;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.tkym.labs.beans.Bill;
import com.tkym.labs.beans.BillMeta;

public class IndexedPropertyMetaBuilderTest {
	@Test
	public void testPropertyMetaBuilderAsArray(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.indexedPropertyName("item", "000", 1);
		assertThat(actual, is("item001"));
	}

	@Test
	public void testPropertyMetaBuilderAsArrayCase002(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.indexedPropertyName("item", "0", 1);
		assertThat(actual, is("item1"));
	}
	
	
	@Test
	public void testCreatePatternCase001(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(100, 0);
		assertThat(actual, is("00"));
	}
	
	@Test
	public void testCreatePatternCase002(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(10, 0);
		assertThat(actual, is("0"));
	}
	
	@Test
	public void testCreatePatternCase003(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(10, 1);
		assertThat(actual, is("00"));
	}
	
	@Test
	public void testCreatePatternCase004(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(9, 1);
		assertThat(actual, is("0"));
	}
	
	@Test
	public void testCreatePatternCase005(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(99, 1);
		assertThat(actual, is("00"));
	}
	
	@Test
	public void testCreatePatternCase006(){
		PropertyMetaBuilderAsArray<Bill, String> builder = 
				new PropertyMetaBuilderAsArray<Bill, String>(Bill.class, "item", String.class, new HashMap<String, PropertyMeta<Bill, ?>>());
		String actual = builder.createPattern(100, 1);
		assertThat(actual, is("000"));
	}
	
	@Test
	public void testIndexedPropertyMeta001(){
		Map<String, PropertyMeta<TestBean, ?>> map = new HashMap<String, PropertyMeta<TestBean, ?>>();
		PropertyMetaBuilderAsArray<TestBean, String> builder = 
				new PropertyMetaBuilderAsArray<TestBean, String>(TestBean.class, "string", String.class, map);
		PropertyMeta<TestBean,String>[] meta = builder.length(50).accessor(new StringAccessor());
		
		TestBean bean = new TestBean();
		for(int i=0; i<50; i++)
			bean.setString(i, i+10+"s");
		
		for(int i=0; i<50; i++)
			Assert.assertThat(meta[i].access(bean).get(), CoreMatchers.is(i+10+"s"));
			
	}
	
	@Test
	public void testIndexedPropertyMeta002(){
		Map<String, PropertyMeta<TestBean, ?>> map = new HashMap<String, PropertyMeta<TestBean, ?>>();
		PropertyMetaBuilderAsArray<TestBean, Integer> builder = 
				new PropertyMetaBuilderAsArray<TestBean, Integer>(TestBean.class, "integer", Integer.class, map);
		PropertyMeta<TestBean,Integer>[] meta = builder.length(50).accessor(new IntegerAccessor());
		
		TestBean bean = new TestBean();
		for(int i=0; i<50; i++)
			bean.setInteger(i, i+10);
		
		for(int i=0; i<50; i++)
			Assert.assertThat(meta[i].access(bean).get(), CoreMatchers.is(i+10));
		
		int idx=0;
		Set<String> treeSet = new TreeSet<String>();
		treeSet.addAll(map.keySet());
		for(String prop : treeSet)
			Assert.assertThat(prop, CoreMatchers.is("integer"+new DecimalFormat("00").format((idx++))));
	}
	
	@Test
	public void testIndexedPropertyMeta003(){
		Map<String, PropertyMeta<TestBean, ?>> map = new HashMap<String, PropertyMeta<TestBean, ?>>();
		PropertyMetaBuilderAsArray<TestBean, Integer> builder = 
				new PropertyMetaBuilderAsArray<TestBean, Integer>(TestBean.class, "integer", Integer.class, map);
		PropertyMeta<TestBean,Integer>[] meta = builder.length(50).start(1).accessor(new IntegerAccessor());
		
		TestBean bean = new TestBean();
		for(int i=0; i<50; i++)
			bean.setInteger(i, i+10);
		
		for(int i=0; i<50; i++)
			Assert.assertThat(meta[i].access(bean).get(), CoreMatchers.is(i+10));
		
		int idx=1;
		Set<String> treeSet = new TreeSet<String>();
		treeSet.addAll(map.keySet());
		for(String prop : treeSet)
			Assert.assertThat(prop, CoreMatchers.is("integer"+new DecimalFormat("00").format((idx++))));
	}
	
	private class IntegerAccessor implements IndexedAccessorResolver<TestBean, Integer>{
		@Override
		public Integer get(TestBean bean, int index) {
			return bean.getInteger(index);
		}

		@Override
		public void set(TestBean bean, int index, Integer value) {
			bean.setInteger(index, value);
		}
	}
	
	private class StringAccessor implements IndexedAccessorResolver<TestBean, String>{
		@Override
		public String get(TestBean bean, int index) {
			return bean.getString(index);
		}

		@Override
		public void set(TestBean bean, int index, String value) {
			bean.setString(index, value);
		}
	}
	
	class TestBean {
		private int[] integer = new int[50];
		private String[] string = new String[50];
		int getInteger(int index){
			return this.integer[index];
		}
		void setInteger(int index, int value){
			this.integer[index] = value;
		}
		String getString(int index){
			return this.string[index];
		}
		void setString(int index, String value){
			this.string[index] = value;
		}
	} 

	DecimalFormat df = new DecimalFormat("00");
	
	@Test
	public void testBillArraysAttribute(){
		Bill bean = new Bill();
		bean.setNo(1);
		bean.setAmount(10.0f);
		String[] values = new String[20];
		for (int i=0; i<20; i++)
			values[i] = "value"+df.format(i);
		for (int i=0; i<20; i++)
			bean.setItem(i, values[i]);
		BillMeta meta = BillMeta.get();
		assertThat(meta.no.access(bean).get(), is(1));
		assertThat(meta.amount.access(bean).get(), is(10.0f));
		for (int i=0; i<20; i++)
			assertThat(meta.item[i].access(bean).get(), is(values[i]));
		for (int i=0; i<20; i++)
			assertThat(meta.item[i].getPropertyName(), is("item"+df.format(i+1)));
		
		assertThat(meta.getPropertyNames().size(), is(20+2));
	}
}
