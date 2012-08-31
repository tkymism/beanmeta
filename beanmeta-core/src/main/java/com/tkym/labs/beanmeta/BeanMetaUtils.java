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
				if (!meta(meta.getPropertyMeta(name)).same(b1, b2)) return false;
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
	
	@SuppressWarnings("unchecked")
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
			return p1.equals(p2);
		}
		
		public int compare(BT b1, BT b2){
			PT p1 = meta.access(b1).get();
			PT p2 = meta.access(b2).get();
			if (p1 == null)
				throw new IllegalArgumentException(
						"["+meta.getBeanType().getName()+"#"+meta.getPropertyName()+"]"+" of b1 is Null.");
			if (p1 instanceof Comparable)
				return ((Comparable<PT>)p1).compareTo(p2);
			else 
				throw new IllegalArgumentException(
						meta.getPropertyType().getName()+" is not Comparable "+
						"["+meta.getBeanType().getName()+"#"+meta.getPropertyName()+"]");
		}
		
		public Comparator<BT> comparator() {
			return new Comparator<BT>() {
				@Override
				public int compare(BT o1, BT o2) {
					return this.compare(o1, o2);
				}
			};
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