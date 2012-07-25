package com.tkym.labs.beanmeta.csv;

import java.util.ArrayList;
import java.util.List;

import com.tkym.labs.beanmeta.BeanMeta;
import com.tkym.labs.beanmeta.PropertyMeta;

public class CsvBeanConverterBuilder<BT,KT>{
	private final BeanMeta<BT,KT> beanMeta;
	private List<PropertyMeta<?, ?>> list = new ArrayList<PropertyMeta<?, ?>>();
	public CsvBeanConverterBuilder(BeanMeta<BT,KT> beanMeta){
		this.beanMeta = beanMeta;
	}
	
	public CsvBeanConverter<BT,KT> buildAsAllProperty(){
		for(String name : beanMeta.getPropertyNames())
			list.add(beanMeta.getPropertyMeta(name));
		return converter();
	}
	
	public <PT> CsvBeanConverterBuilder<BT,KT> property(PropertyMeta<BT, PT> meta){
		list.add((PropertyMeta<?, ?>)meta);
		return this;
	}
	@SuppressWarnings("unchecked")
	public CsvBeanConverter<BT,KT> converter(){
		PropertyMeta<?,?>[] array = new PropertyMeta[list.size()];
		list.toArray(array);
		return new CsvBeanConverter<BT, KT>(beanMeta, (PropertyMeta<BT, ?>[])array);
	}
}