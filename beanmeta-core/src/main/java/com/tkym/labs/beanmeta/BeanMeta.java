package com.tkym.labs.beanmeta;

import java.util.Set;

public interface BeanMeta<BT,KT>{
	public Key<BT,KT> key(Key<?,?>parent,  KT value);
	public Key<BT,KT> maxKey(Key<?,?>parent);
	public Key<BT,KT> minKey(Key<?,?>parent);
	public String getName();
	public String getNamespace();
	public Class<BT> getBeanType();
	public PropertyMeta<BT, KT> getKeyPropertyMeta();
	public <PT> PropertyMeta<BT,PT> getPropertyMeta(String propertyName);
	public Set<String> getPropertyNames();
	public <PBT,PKT> BeanMeta<PBT,PKT> parent();
	public String parentKeyProperty(BeanMeta<?,?> parent);
	public BT newInstance();
}