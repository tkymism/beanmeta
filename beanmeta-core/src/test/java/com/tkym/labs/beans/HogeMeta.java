package com.tkym.labs.beans;

import com.tkym.labs.beanmeta.AbstractBeanMeta;
import com.tkym.labs.beanmeta.PropertyAccessorResolver;
import com.tkym.labs.beanmeta.PropertyMeta;

/**
 * This Code is generated automatically Hoge
 */
public class HogeMeta extends AbstractBeanMeta<Hoge, java.lang.String>{
	private static final HogeMeta singleton = new HogeMeta();
	
	public static HogeMeta get(){ return singleton; }
	
	private HogeMeta(){ super("Hoge", Hoge.class); }
	
	private PropertyAccessorResolver<Hoge, java.lang.String> _key_ = new PropertyAccessorResolver<Hoge, java.lang.String>(){
		@Override
		public java.lang.String get(Hoge bean){
			return bean.getKey();
		}
		
		@Override
		public void set(Hoge bean, java.lang.String value){
			bean.setKey(value);
		}
	};
	public final PropertyMeta<Hoge, java.lang.String> key = property("key", java.lang.String.class).accessor(_key_);
	
	private PropertyAccessorResolver<Hoge, Byte> _byteValue_ = new PropertyAccessorResolver<Hoge, Byte>(){
		@Override
		public Byte get(Hoge bean){
			return bean.getByteValue();
		}
		
		@Override
		public void set(Hoge bean, Byte value){
			bean.setByteValue(value);
		}
	};
	public final PropertyMeta<Hoge, Byte> byteValue = property("byteValue", Byte.class).accessor(_byteValue_);
	
	private PropertyAccessorResolver<Hoge, Short> _shortValue_ = new PropertyAccessorResolver<Hoge, Short>(){
		@Override
		public Short get(Hoge bean){
			return bean.getShortValue();
		}
		
		@Override
		public void set(Hoge bean, Short value){
			bean.setShortValue(value);
		}
	};
	public final PropertyMeta<Hoge, Short> shortValue = property("shortValue", Short.class).accessor(_shortValue_);
	
	private PropertyAccessorResolver<Hoge, Integer> _intValue_ = new PropertyAccessorResolver<Hoge, Integer>(){
		@Override
		public Integer get(Hoge bean){
			return bean.getIntValue();
		}
		
		@Override
		public void set(Hoge bean, Integer value){
			bean.setIntValue(value);
		}
	};
	public final PropertyMeta<Hoge, Integer> intValue = property("intValue", Integer.class).accessor(_intValue_);
	
	private PropertyAccessorResolver<Hoge, Long> _longValue_ = new PropertyAccessorResolver<Hoge, Long>(){
		@Override
		public Long get(Hoge bean){
			return bean.getLongValue();
		}
		
		@Override
		public void set(Hoge bean, Long value){
			bean.setLongValue(value);
		}
	};
	public final PropertyMeta<Hoge, Long> longValue = property("longValue", Long.class).accessor(_longValue_);
	
	private PropertyAccessorResolver<Hoge, Float> _floatValue_ = new PropertyAccessorResolver<Hoge, Float>(){
		@Override
		public Float get(Hoge bean){
			return bean.getFloatValue();
		}
		
		@Override
		public void set(Hoge bean, Float value){
			bean.setFloatValue(value);
		}
	};
	public final PropertyMeta<Hoge, Float> floatValue = property("floatValue", Float.class).accessor(_floatValue_);
	
	private PropertyAccessorResolver<Hoge, Double> _doubleValue_ = new PropertyAccessorResolver<Hoge, Double>(){
		@Override
		public Double get(Hoge bean){
			return bean.getDoubleValue();
		}
		
		@Override
		public void set(Hoge bean, Double value){
			bean.setDoubleValue(value);
		}
	};
	public final PropertyMeta<Hoge, Double> doubleValue = property("doubleValue", Double.class).accessor(_doubleValue_);
	
	private PropertyAccessorResolver<Hoge, java.lang.String> _stringValue_ = new PropertyAccessorResolver<Hoge, java.lang.String>(){
		@Override
		public java.lang.String get(Hoge bean){
			return bean.getStringValue();
		}
		
		@Override
		public void set(Hoge bean, java.lang.String value){
			bean.setStringValue(value);
		}
	};
	public final PropertyMeta<Hoge, java.lang.String> stringValue = property("stringValue", java.lang.String.class).accessor(_stringValue_);
	
	@Override
	public PropertyMeta<Hoge, java.lang.String> getKeyPropertyMeta() { return key; }
	
	@Override
	public Hoge newInstance() { return new Hoge(); }
	
}

