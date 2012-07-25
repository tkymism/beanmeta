package com.tkym.labs.beanmeta.csv;

import java.util.ArrayList;
import java.util.List;

import com.tkym.labs.beanmeta.BeanMeta;
import com.tkym.labs.beanmeta.PropertyMeta;

public class CsvBeanConverter<BT,KT> {
	private BeanMeta<BT,KT> beanMeta;
	private PropertyMeta<BT, ?>[] properties;
	private CsvLineResolver resolver = new CsvLineResolver("\"");
	
	CsvBeanConverter(BeanMeta<BT,KT> beanMeta, PropertyMeta<BT, ?>... properties){
		this.beanMeta = beanMeta;
		this.properties = properties;
	}
	
	String replaceCamma(String source){
		return source.replaceAll(",", "");
	}
	
	Object cast(Class<?> cls, String value){
		if (cls.equals(Integer.class) || cls.equals(int.class))    
			return Integer.parseInt(replaceCamma(value));
		if (cls.equals(Long.class)    || cls.equals(long.class))   
			return Long.parseLong(replaceCamma(value));
		if (cls.equals(Short.class)   || cls.equals(short.class))  
			return Short.parseShort(replaceCamma(value));
		if (cls.equals(Byte.class)    || cls.equals(byte.class))   
			return Byte.parseByte(replaceCamma(value));
		if (cls.equals(Float.class)   || cls.equals(float.class))  
			return Float.parseFloat(replaceCamma(value));
		if (cls.equals(Double.class)  || cls.equals(double.class)) 
			return Double.parseDouble(replaceCamma(value));
		return value;
	}
	
	BT bean(String line){
		BT bean = beanMeta.newInstance();
		List<String> sep = separatedList(line);
		for (int i=0; i<sep.size(); i++){
			@SuppressWarnings("unchecked")
			PropertyMeta<BT,Object> meta = (PropertyMeta<BT,Object>)properties[i];
			try {
				Object value = cast(meta.getPropertyType(),sep.get(i));
				meta.access(bean).set(value);
			} catch (Exception e) {
				throw new CsvConverterException("property is "+meta.getPropertyName()+
						" occurs Exception."+"value is "+sep.get(i),e);
			}	
		}
		return bean;
	}
	
	List<String> separatedList(String line){
		String[] separated = resolver.separateByComma(line);
		List<String> list = new ArrayList<String>();
		for(String value : separated)
			list.add(resolver.removeQuoteIfEnclosed(value));
		return list;
	}
}