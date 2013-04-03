package com.tkym.labs.common;

import static com.tkym.labs.common.ChainComparator.asc;
import static com.tkym.labs.common.ChainComparator.desc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChainComparatorTest {
	private static List<Sample> TESTDATA = new ArrayList<Sample>();
	static class Sample{
		private int a;
		private int b;
		Sample(){
			this(0,0);
		}
		Sample(int a, int b){
			this.a = a;
			this.b = b;
		}
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}
		@Override
		public String toString() {
			return "["+a+","+b+"]";
		}
	}
	@BeforeClass
	public static void setupClass(){
		for (int i=0; i<100; i++)
			for (int j=0; j<100; j++)
				TESTDATA.add(new Sample(i, j));
	}
	@AfterClass
	public static void teardownClass(){
		TESTDATA.clear();
		TESTDATA = null;
	}
	private Comparator<Sample> compA = new Comparator<Sample>() {
		@Override
		public int compare(Sample o1, Sample o2) {
			return new Integer(o1.a).compareTo(o2.a);
		}
	};
	private Comparator<Sample> compB = new Comparator<Sample>() {
		@Override
		public int compare(Sample o1, Sample o2) {
			return new Integer(o1.b).compareTo(o2.b);
		}
	};
	public static Sample S(int a, int b){
		return new Sample(a,b);
	}
	public void testComparatorCase001(){
		int ret = new Integer(0).compareTo(1);
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase001(){
		int ret = 
				asc(compA).
				asc(compB).
				chain().
				compare(S(0,0), S(0,1));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase002(){
		int ret = 
				asc(compA).
				asc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase003(){
		int ret = 
				asc(compA).
				asc(compB).
				chain().
				compare(S(1,1), S(1,1));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase004(){
		int ret = 
				asc(compA).
				asc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase005(){
		int ret = 
				desc(compA).
				asc(compB).
				chain().
				compare(S(0,0), S(0,1));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase006(){
		int ret = 
				desc(compA).
				asc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase007(){
		int ret = 
				desc(compA).
				asc(compB).
				chain().
				compare(S(1,1), S(1,1));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase008(){
		int ret = 
				desc(compA).
				asc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase011(){
		int ret = 
				asc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase012(){
		int ret = 
				asc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase013(){
		int ret = 
				desc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase014(){
		int ret = 
				desc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase021(){
		int ret = 
				asc(compA).
				desc(compB).
				chain().
				compare(S(0,0), S(0,1));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase022(){
		int ret = 
				asc(compA).
				desc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase023(){
		int ret = 
				asc(compA).
				desc(compB).
				chain().
				compare(S(1,1), S(1,1));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase024(){
		int ret = 
				asc(compA).
				desc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase025(){
		int ret = 
				desc(compA).
				desc(compB).
				chain().
				compare(S(0,0), S(0,1));
		assertThat(ret, is(1));
	}
	@Test
	public void testChainComparatorCase026(){
		int ret = 
				desc(compA).
				desc(compB).
				chain().
				compare(S(1,0), S(0,1));
		assertThat(ret, is(-1));
	}
	@Test
	public void testChainComparatorCase027(){
		int ret = 
				desc(compA).
				desc(compB).
				chain().
				compare(S(1,1), S(1,1));
		assertThat(ret, is(0));
	}
	@Test
	public void testChainComparatorCase028(){
		int ret = 
				desc(compA).
				desc(compB).
				chain().
				compare(S(1,0), S(0,0));
		assertThat(ret, is(-1));
	}
//	@Test
//	public void testChainComparatorCase051(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				asc(compA, compB).
//				chain().
//				compare(S(0,0), S(0,1));
//		assertThat(ret, is(-1));
//	}
//	@Test
//	public void testChainComparatorCase052(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				asc(compA,compB).
//				chain().
//				compare(S(1,0), S(0,1));
//		assertThat(ret, is(1));
//	}
//	@Test
//	public void testChainComparatorCase053(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				asc(compA, compB).
//				chain().
//				compare(S(1,1), S(1,1));
//		assertThat(ret, is(0));
//	}
//	@Test
//	public void testChainComparatorCase054(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				asc(compA,compB).
//				chain().
//				compare(S(1,0), S(0,0));
//		assertThat(ret, is(1));
//	}
//	@Test
//	public void testChainComparatorCase055(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				desc(compA, compB).
//				chain().
//				compare(S(0,0), S(0,1));
//		assertThat(ret, is(1));
//	}
//	@Test
//	public void testChainComparatorCase056(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				desc(compA, compB).
//				chain().
//				compare(S(1,0), S(0,1));
//		assertThat(ret, is(-1));
//	}
//	@Test
//	public void testChainComparatorCase057(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				desc(compA, compB).
//				chain().
//				compare(S(1,1), S(1,1));
//		assertThat(ret, is(0));
//	}
//	@Test
//	public void testChainComparatorCase058(){
//		@SuppressWarnings("unchecked")
//		int ret = 
//				desc(compA, compB).
//				chain().
//				compare(S(1,0), S(0,0));
//		assertThat(ret, is(-1));
//	}
}
