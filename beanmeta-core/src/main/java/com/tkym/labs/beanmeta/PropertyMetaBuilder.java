package com.tkym.labs.beanmeta;

import java.util.Map;

import com.tkym.labs.beanmeta.PropertyAccessor.DefaultPropertyAccessorBuilder;
import com.tkym.labs.beanmeta.PropertyAccessor.PropertyAccessorBuilder;
import com.tkym.labs.beanmeta.PropertyMeta.PropertyMetaImpl;


public class PropertyMetaBuilder<B,P> {
	protected final Class<B> beanType;
	protected final String propertyName;
	protected boolean indexed = false;
	final Class<P> propertyType;
	protected final Map<String,PropertyMeta<B, ?>> propertiesMap;
	PropertyMetaBuilder(Class<B> beanType, String propertyName, Class<P> propertyType, Map<String,PropertyMeta<B, ?>> propertiesMap){
		this.beanType = beanType;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.propertiesMap = propertiesMap;
	}
	private PropertyMeta<B,P> accesser(PropertyAccessorBuilder<B,P> builder){
		return build(builder);
	}
	private PropertyMeta<B,P> build(PropertyAccessorBuilder<B, P> builder){
		PropertyMeta<B,P> meta = new PropertyMetaImpl<B,P>(beanType, propertyName, propertyType, builder);
		meta.setIndexed(indexed);
		propertiesMap.put(propertyName, meta);
		return meta;
	}
	public PropertyMetaBuilder<B,P> asIndex(){
		indexed = true;
		return this;
	}
	public PropertyMeta<B,P> accessor(PropertyAccessorResolver<B,P> accessor){
		return accesser(new DefaultPropertyAccessorBuilder<B, P>(accessor));
	}
}