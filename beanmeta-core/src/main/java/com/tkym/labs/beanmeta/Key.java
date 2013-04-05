package com.tkym.labs.beanmeta;


public class Key<BT,KT>{
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
	public static class MaxKeyValue<BT,KT> extends Key<BT, KT>{
		public MaxKeyValue(Key<?, ?> parent, BeanMeta<BT, KT> beanMeta) {
			super(parent, beanMeta, null);
		}
	}
	public static class MinKeyValue<BT,KT> extends Key<BT, KT>{
		public MinKeyValue(Key<?, ?> parent, BeanMeta<BT, KT> beanMeta) {
			super(parent, beanMeta, null);
		}
	}
}