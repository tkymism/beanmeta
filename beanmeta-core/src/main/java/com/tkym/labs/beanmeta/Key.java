package com.tkym.labs.beanmeta;

import java.util.Comparator;

public class Key<BT,KT> implements Comparable<Key<?,?>>{
	private final BeanMeta<BT, KT> beanMeta; 
	private final KT value;
	private final Key<?,?> parent;
	Key(Key<?,?> parent, BeanMeta<BT, KT> beanMeta, KT value){
		this.parent = parent;
		this.beanMeta = beanMeta;
		this.value = value;
	}
	public BeanMeta<BT, KT> getBeanMeta() {
		return beanMeta;
	}
	public KT value() {
		return value;
	}
	public Key<?,?> getParent() {
		return parent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beanMeta == null) ? 0 : beanMeta.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Key<BT,KT> other = (Key<BT,KT>) obj;
		if (beanMeta == null) {
			if (other.beanMeta != null)
				return false;
		} else if (!beanMeta.equals(other.beanMeta))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	public boolean isSiblingOf(Key<?,?> other){
		Key<?,?> otherParent = other.getParent();
		if (this.parent == null && otherParent == null) return true;
		if (this.parent != null && otherParent != null && this.parent.equals(otherParent)) return true;
		return false;
	}
	public int rank(){
		if (this.getParent() == null) 
			return 0;
		else 
			return this.getParent().rank() + 1;
	} 
	public boolean isAncestorOf(Key<?,?> other){
		if (other.getParent() == null) return false;
		if (this.equals(other.getParent())) return true;
		else return isAncestorOf(other.getParent());
	}
	public boolean isDescendantOf(Key<?,?> other){
		return other.isAncestorOf(this);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (parent != null) sb.append(parent.toString());
		sb.append(" "+beanMeta.getName()+"="+this.value()+", ");
		return super.toString();
	}
	public Class<BT> getBeanType() {
		return beanMeta.getBeanType();
	}
	@Override
	public int compareTo(Key<?, ?> o) {
		return COMPARATOR.compare(this, o);
	}
	private static Comparator<Key<?,?>> COMPARATOR = new Comparator<Key<?,?>>() {
		@Override
		public int compare(Key<?, ?> o1, Key<?, ?> o2) {
			if (o1.getParent() != null && o2.getParent() != null){
				int parent = compare(o1.getParent(), o2.getParent());
				if (parent != 0) return parent;
			}
			return compareValue(o1, o2);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		int compareValue(Key<?,?> o1, Key<?,?> o2){
			Class<?> c1 = o1.getBeanMeta().getKeyPropertyMeta().getPropertyType();
			Class<?> c2 = o2.getBeanMeta().getKeyPropertyMeta().getPropertyType();
			if (!c1.equals(c2))
				throw new IllegalArgumentException(
						"types are different." +
						" key1="+c1.getName()+
						" key2="+c2.getName());
			if (!Comparable.class.isAssignableFrom(c1))
				throw new IllegalArgumentException(
						"type is not Comparable" +
						" key1="+c1.getName()+
						" key2="+c2.getName());
			if (o1.value() == null && o2.value() != null) return -1;
			else if (o1.value() != null && o2.value() == null) return 1;
			return ((Comparable) o1.value).compareTo(o2.value);
		}
	};
}