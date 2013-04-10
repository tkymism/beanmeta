package com.tkym.labs.beans;


public class BeanBuilder {
	public static class HogeBuilder{
		private final Hoge bean;
		private HogeBuilder(Hoge bean){
			this.bean = bean;
		}
		public static HogeBuilder create(){
			return modify(new Hoge());
		}
		public static HogeBuilder modify(Hoge hoge){
			return new HogeBuilder(hoge);
		}
		public Hoge bean(){ return bean; }
		public HogeBuilder key(String key){ 
			bean.setKey(key);
			return this; 
		}
		public HogeBuilder byteValue(byte byteValue){ 
			bean.setByteValue(byteValue);
			return this; 
		}
		public HogeBuilder shortValue(short shortValue){ 
			bean.setShortValue(shortValue);
			return this; 
		}
		public HogeBuilder intValue(int intValue){ 
			bean.setIntValue(intValue);
			return this; 
		}
		public HogeBuilder longValue(long longValue){ 
			bean.setLongValue(longValue);
			return this; 
		}
		public HogeBuilder floatValue(float floatValue){ 
			bean.setFloatValue(floatValue);
			return this; 
		}
		public HogeBuilder doubleValue(double doubleValue){ 
			bean.setDoubleValue(doubleValue);
			return this; 
		}
		public HogeBuilder stringValue(String stringValue){ 
			bean.setStringValue(stringValue);
			return this; 
		}
	}
}
