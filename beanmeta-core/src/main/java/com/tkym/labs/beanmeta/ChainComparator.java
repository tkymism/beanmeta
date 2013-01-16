package com.tkym.labs.beanmeta;

import java.util.Comparator;

/**
 * <pre>
 *  T x;
 *  T y;
 *  Comparator<T> compA;
 *  Comparator<T> compB;
 * 	ChainComparator.
 * 		chain(compA).
 * 		chain(compB).
 * 		build().
 * 		compare(x, y);
 * </pre>
 * @author takayama
 * @param <T>
 */
public class ChainComparator<T> implements Comparator<T>{
	private final Comparator<T> current;
	private Comparator<T> parent;
	public static <T> ChainComparator.ChainComparatorBuilder<T> chain(Comparator<T> comparator){
		return new ChainComparator.ChainComparatorBuilder<T>(comparator);
	}
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
	public static class ChainComparatorBuilder<T>{
		private ChainComparator<T> comparator;
		private ChainComparatorBuilder(Comparator<T> comparator){
			this.comparator = new ChainComparator<T>(null, comparator);
		}
		public ChainComparatorBuilder<T> chain(Comparator<T> child){
			return chain(child, false);
		}
		public ChainComparatorBuilder<T> chainR(Comparator<T> child){
			return chain(child, true);
		}
		ChainComparatorBuilder<T> chain(Comparator<T> child, boolean reverse){
			this.comparator = create(this.comparator, child, reverse);
			return this;
		}
		private ChainComparator<T> create(ChainComparator<T> parent, Comparator<T> child, boolean reverse){
			Comparator<T> comparator;
			if (reverse) comparator = new ReverseComparator<T>(child);
			else comparator = child;
			return new ChainComparator<T>(parent, comparator);
		}
		public ChainComparator<T> build(){
			return comparator;
		}
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