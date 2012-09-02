package com.tkym.labs.beans;

import com.tkym.labs.beanmeta.AbstractBeanMeta;
import com.tkym.labs.beanmeta.PropertyAccessorResolver;
import com.tkym.labs.beanmeta.PropertyMeta;

public class BigDataMeta extends AbstractBeanMeta<BigData, Integer>{
	private static final BigDataMeta singleton = new BigDataMeta();
	public final static BigDataMeta get(){ return singleton; }
	private BigDataMeta() { super("bigData", BigData.class); }
	@Override
	public PropertyMeta<BigData, Integer> getKeyPropertyMeta() {
		return no;
	}
	@Override
	public BigData newInstance() {
		return new BigData();
	}
	private PropertyAccessorResolver<BigData, Integer> _no_ = new PropertyAccessorResolver<BigData, Integer>(){
		@Override
		public Integer get(BigData bean) {
			return bean.getNo();
		}
		@Override
		public void set(BigData bean, Integer value) {
			bean.setNo(value);
		}
	};
	public final PropertyMeta<BigData,Integer> no = property("no", Integer.class).accessor(_no_);
	private PropertyAccessorResolver<BigData, String> _data_ = new PropertyAccessorResolver<BigData, String>() {
		@Override
		public String get(BigData bean) {
			return bean.getData();
		}
		@Override
		public void set(BigData bean, String value) {
			bean.setData(value);
		}
	};
	public final PropertyMeta<BigData,String> data = property("data", String.class).accessor(_data_);
}
