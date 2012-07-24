package com.tkym.labs.beanmeta;

import java.util.Set;



public interface BeanMeta<BT,KT>{
	public Key<BT,KT> key(Key<?,?>parent,  KT value);
	public String getName();
	public PropertyMeta<BT, KT> getKeyPropertyMeta();
	public <PT> PropertyMeta<BT,PT> getPropertyMeta(String propertyName);
	public Set<String> getPropertyNames();
	public <PBT,PKT> BeanMeta<PBT,PKT> parent();
	public String parentKeyProperty(BeanMeta<?,?> parent);
	public BT newInstance();
}