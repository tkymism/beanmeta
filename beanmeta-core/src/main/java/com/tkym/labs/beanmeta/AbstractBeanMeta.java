package com.tkym.labs.beanmeta;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author takayama 
 * @param <BT> Type of Bean
 * @param <KT> Type of Key
 */
public abstract class AbstractBeanMeta<BT, KT> implements BeanMeta<BT, KT>{
	private final Class<BT> beanType;
	private final String name;
	private final String namespace;
	private final Map<String,PropertyMeta<BT, ?>> propertiesMap = new LinkedHashMap<String,PropertyMeta<BT, ?>>();
	protected AbstractBeanMeta(String name, Class<BT> beanType){
		this(null, name, beanType);
	}
	protected AbstractBeanMeta(String namespace, String name, Class<BT> beanType){
		this.name = name;
		this.namespace = namespace;
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
	public String getNamespace() {
		return namespace;
	}
	@Override
	public String getName(){
		return this.name;
	}
	@Override
	public Class<BT> getBeanType(){
		return this.beanType;
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
		return new KeyFactory<BT, KT>(this).create(parent,value);
	}
	@Override
	public String parentKeyProperty(BeanMeta<?, ?> parent) {
		return parent.getKeyPropertyMeta().getPropertyName();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beanType == null) ? 0 : beanType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		@SuppressWarnings("unchecked")
		AbstractBeanMeta<BT, KT> other = (AbstractBeanMeta<BT, KT>) obj;
		if (beanType == null) {
			if (other.beanType != null)
				return false;
		} else if (!beanType.equals(other.beanType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}