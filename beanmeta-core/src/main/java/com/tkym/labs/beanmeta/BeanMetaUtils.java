package com.tkym.labs.beanmeta;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;



public class BeanMetaUtils {
	private static final BeanMetaUtils singleton = new BeanMetaUtils();
	private BeanMetaUtils(){}
	public static BeanMetaUtils get(){ return singleton; }
	private Map<Class<?>, BeanMeta<?,?>> map = new HashMap<Class<?>, BeanMeta<?,?>>();
	public <B,K> void register(Class<B> cls, BeanMeta<B,K> meta){ 
		map.put(cls, meta);
	}
	@SuppressWarnings("unchecked")
	public <B,K> BeanMeta<B,K> get(Class<B> cls){
		return (BeanMeta<B,K>) map.get(cls);
	}
	public <B,K> BeanTypeMetaUtils<B,K> meta(BeanMeta<B, K> meta){
		return new BeanTypeMetaUtils<B, K>(meta);
	}
	
	public <B,P> PropertyMetaUtils<B,P> meta(PropertyMeta<B,P> meta){
		return new PropertyMetaUtils<B, P>(meta);
	}
	
	public <B,P extends Comparable<P>> PropertyMetaComparator<B,P> propertyComparator(PropertyMeta<B,P> meta){
		return new PropertyMetaComparator<B, P>(meta);
	}
	
	public <B,P extends Comparable<P>> PropertyMetaComparatorForBean<B,P> beanComparator(PropertyMeta<B,P> meta){
		return new PropertyMetaComparatorForBean<B, P>(meta);
	}
	
//	public <B,P extends Comparable<P>> PropertyMetaComparatorForBean<B,P> beanComparator(PropertyMeta<B,P> meta){
//		return new PropertyMetaComparatorForBean<B, P>(meta);
//	}
	
	public <B,K extends Comparable<K>> BeanMetaComparatorBuilder<B, K> comparatorBulder(BeanMeta<B,K> meta){
		return new BeanMetaComparatorBuilder<B,K>(meta.getKeyPropertyMeta());
	}
	
	public <B> boolean equalsKey(B b1, B b2){
		@SuppressWarnings("unchecked")
		BeanMeta<B, ?> meta = (BeanMeta<B, ?>) getFromMap(b1.getClass());
		return meta(meta).sameKey(b1, b2);
	}
	
	public <B> boolean equals(B b1, B b2){
		@SuppressWarnings("unchecked")
		BeanMeta<B, ?> meta = (BeanMeta<B, ?>) getFromMap(b1.getClass());
		return meta(meta).equals(b1, b2);
	}
	
	public class BeanTypeMetaUtils<BT,KT>{
		private BeanMeta<BT,KT> meta;
		BeanTypeMetaUtils(BeanMeta<BT,KT> beanMeta){
			this.meta = beanMeta;
		}
		public boolean equals(BT b1, BT b2){
			for (String name : meta.getPropertyNames())
				if(!meta(meta.getPropertyMeta(name)).same(b1, b2)) return false;
			return true;
		}
		public boolean sameKey(BT b1, BT b2){
			return meta(meta.getKeyPropertyMeta()).same(b1, b2);
		}
		public BT clone(BT source){
			BT clone = meta.newInstance();
			copy(source, clone);
			return clone;
		}
		void copy(BT source, BT target){
			for(String name : meta.getPropertyNames())
				meta(meta.getPropertyMeta(name)).copy(source, target);
		}
	}
	
	public class PropertyMetaUtils<BT,PT>{
		private PropertyMeta<BT,PT> meta;
		private PropertyMetaUtils(PropertyMeta<BT,PT> propertyMeta){
			this.meta = propertyMeta;
		}
		
		private void copy(BT source, BT target){
			meta.access(target).set(meta.access(source).get());
		}
		
		public boolean same(BT b1, BT b2){
			PT p1 = meta.access(b1).get();
			PT p2 = meta.access(b2).get();
			if (p1 == null && p2 == null) return true;
			else if (p1 != null && p2 == null) return false;
			else if (p1 == null && p2 != null) return false;
			else return p1.equals(p2);
		}
	}
	public static class PropertyMetaComparatorForBean<BT,PT extends Comparable<PT>> implements Comparator<BT>{
		private final PropertyMetaComparator<BT,PT> comparator;
		private final PropertyMeta<BT,PT> meta;
		PropertyMetaComparatorForBean(PropertyMeta<BT,PT> meta){
			this(meta, BehaveNull.NULL_ERROR);
		}
		PropertyMetaComparatorForBean(PropertyMeta<BT,PT> meta, BehaveNull behaveNull){
			comparator = new PropertyMetaComparator<BT,PT>(meta, behaveNull); 
			this.meta = meta;
		}
		@Override
		public int compare(BT b1, BT b2) {
			PT p1 = meta.access(b1).get();
			PT p2 = meta.access(b2).get();
			return comparator.compare(p1, p2);
		}
		public PropertyMetaComparatorForBean<BT,PT> nullFirst(){
			comparator.nullFirst();
			return this;
		}
		public PropertyMetaComparatorForBean<BT,PT> nullLast(){
			comparator.nullLast();
			return this;
		}
		public PropertyMetaComparatorForBean<BT,PT> nullError(){
			comparator.nullError();
			return this;
		}
	}
	public static enum BehaveNull{
		NULL_FIRST,NULL_LAST,NULL_ERROR,
	}
	public static class PropertyMetaComparator<BT,PT extends Comparable<PT>> implements Comparator<PT>{
		private PropertyMeta<BT,PT> meta;
		private BehaveNull behaveNull; 
		PropertyMetaComparator(PropertyMeta<BT,PT> meta, BehaveNull behaveNull){
			this.behaveNull = behaveNull;
			this.meta = meta;
		}
		PropertyMetaComparator(PropertyMeta<BT,PT> meta){
			this(meta, BehaveNull.NULL_ERROR);
		}
		public PropertyMetaComparator<BT,PT> nullFirst(){
			this.behaveNull = BehaveNull.NULL_FIRST;
			return this;
		}
		public PropertyMetaComparator<BT,PT> nullLast(){
			this.behaveNull = BehaveNull.NULL_LAST;
			return this;
		}
		public PropertyMetaComparator<BT,PT> nullError(){
			this.behaveNull = BehaveNull.NULL_ERROR;
			return this;
		}
		@Override
		public int compare(PT p1, PT p2){
			int n1 = ensureNull(p1, 1);
			int n2 = ensureNull(p2, 2);
			if (n1 == 0 && n2 == 0) 		// not null.
				return p1.compareTo(p2);	
			else {							// contains null
				if (n1 > n2) return 1;
				else if (n1 < n2) return -1;
				else return 0;
			}
		}
		private int ensureNull(PT p, int num){
			if (p != null) return 0;
			switch(behaveNull){
				case NULL_ERROR: 
					throw new IllegalArgumentException(
							"["+meta.getBeanType().getName()+"#"+meta.getPropertyName()+"]"+" of b"+num+" is Null.");
				case NULL_FIRST: 
					return -1;
				case NULL_LAST: 
					return 1;
				default:
					throw new IllegalAccessError("behaveNull is Illegal:"+behaveNull);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <B,K> BeanMeta<B,K> getFromMap(Class<B> cls){
		BeanMeta<?,?> meta = map.get(cls);
		if (meta == null)
			throw new IllegalArgumentException(cls.getName()+" is not defined");
		return (BeanMeta<B,K>) meta;
	}
}