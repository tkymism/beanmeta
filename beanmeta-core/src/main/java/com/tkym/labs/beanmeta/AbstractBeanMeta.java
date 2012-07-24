package com.tkym.labs.beanmeta;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;



public abstract class AbstractBeanMeta<BT, KT> implements BeanMeta<BT, KT>{
	private final Class<BT> beanType;
	private final String name;
	private final Map<String,PropertyMeta<BT, ?>> propertiesMap = new LinkedHashMap<String,PropertyMeta<BT, ?>>();
	protected AbstractBeanMeta(String name, Class<BT> beanType){
		this.name = name;
		this.beanType = beanType;
		BeanMetaUtils.get().register(beanType, this);
	}
	protected <PT> PropertyMetaBuilder<BT, PT> property(String propertyName, Class<PT> propertyType){
		return new PropertyMetaBuilder<BT, PT>(beanType, propertyName, propertyType, propertiesMap);
	}
	protected <PT> PropertyMetaBuilderAsArray<BT, PT> arrays(String propertyName, Class<PT> propertyType){
		return new PropertyMetaBuilderAsArray<BT, PT>(beanType, propertyName, propertyType, propertiesMap);
	}
	@Override
	public String getName(){
		return this.name;
	}
	@Override
	public <PBT,PKT> BeanMeta<PBT,PKT> parent(){
		return null;
	}
	@SuppressWarnings("unchecked")
	public final <PT> PropertyMeta<BT,PT> getPropertyMeta(String propertyName){
		return (PropertyMeta<BT,PT>) propertiesMap.get(propertyName);
	}
	@Override
	public final Set<String> getPropertyNames(){
		return propertiesMap.keySet();
	}
	@Override
	public final Key<BT,KT> key(Key<?,?> parent,  KT value) {
		return new Key<BT, KT>(parent, this, value);
	};
	@Override
	public String parentKeyProperty(BeanMeta<?, ?> parent) {
		return parent.getKeyPropertyMeta().getPropertyName();
	}
}