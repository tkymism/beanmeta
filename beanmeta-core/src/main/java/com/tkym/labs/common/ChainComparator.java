package com.tkym.labs.common;

import java.util.Comparator;

/**
 * <pre>
 *  T x;
 *  T y;
 *  Comparator<T> compA;
 *  Comparator<T> compB;
 * 	ChainComparator.
 * 		asc(compA).
 * 		asc(compB).
 * 		chain().
 * 		compare(x, y);
 * </pre>
 * @author takayama
 * @param <T>
 */
public class ChainComparator<T> implements Comparator<T>{
	private final Comparator<T> current;
	private Comparator<T> parent;
	private ChainComparator(Comparator<T> current){
		this(null, current);
	}
	private ChainComparator(Comparator<T> parent, Comparator<T> current){
		this.current = current;
		this.parent = parent;
	}
	Comparator<T> getParent() {
		return parent;
	}
	void setParent(Comparator<T> parent) {
		this.parent = parent;
	}
	@Override
	public int compare(T o1, T o2) {
		int ret = 0;
		if (this.parent != null)
			ret = parent.compare(o1, o2);
		if (ret == 0)
			ret = current.compare(o1, o2);
		return ret;
	}
	public static <T> ChainComparator.ChainComparatorBuilder<T> root(){
		return new ChainComparator.ChainComparatorBuilder<T>();
	}
	public static <T> ChainComparator.ChainComparatorBuilder<T> asc(Comparator<T> comparator){
		return new ChainComparator.ChainComparatorBuilder<T>(comparator);
	}
	public static <T> ChainComparator.ChainComparatorBuilder<T> desc(Comparator<T> comparator){
		return asc(reverse(comparator));
	}
	public ChainComparator<T> chain(Comparator<T> comparator){
		return new ChainComparator<T>(this, comparator);
	}
	public static class ChainComparatorBuilder<T>{
		private ChainComparator<T> comparator;
		private ChainComparatorBuilder(){
			this.comparator = null;
		}
		private ChainComparatorBuilder(Comparator<T> comparator){
			this.comparator = new ChainComparator<T>(null, comparator);
		}
		public ChainComparatorBuilder<T> asc(Comparator<T> child){
			return buildChain(child, false);
		}
		public ChainComparatorBuilder<T> desc(Comparator<T> child){
			return buildChain(child, true);
		}
		ChainComparatorBuilder<T> buildChain(Comparator<T> child, boolean reverse){
			this.comparator = create(this.comparator, child, reverse);
			return this;
		}
		private ChainComparator<T> create(ChainComparator<T> parent, Comparator<T> child, boolean reverse){
			Comparator<T> comparator;
			if (reverse) comparator = reverse(child);
			else comparator = child;
			return new ChainComparator<T>(parent, comparator);
		}
		public ChainComparator<T> chain(){
			return comparator;
		}
	}
	public static <T> ReverseComparator<T> reverse(Comparator<T> comparator){
		return new ReverseComparator<T>(comparator);
	}
	static class ReverseComparator<T> implements Comparator<T>{
		private final Comparator<T> current;
		ReverseComparator(Comparator<T> current){
			this.current = current;
		}
		@Override
		public int compare(T o1, T o2) {
			return current.compare(o2, o1);
		}
	}
}