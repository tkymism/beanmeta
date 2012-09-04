package com.tkym.labs.beanmeta;

import java.util.Set;

class SuffixBeanMetaImpl<BT, KT> implements SuffixBeanMeta<BT, KT>{
	private final BeanMeta<BT,KT> delegate;
	private final String suffix;
	SuffixBeanMetaImpl(String suffix, BeanMeta<BT,KT> beanMeta){
		this.delegate = beanMeta;
		this.suffix = suffix;
	}
	@Override
	public String getSuffix(){
		return this.suffix;
	}
	@Override
	public Key<BT, KT> key(Key<?, ?> parent, KT value) {
		return delegate.key(parent, value);
	}
	@Override
	public String getName() {
		return delegate.getName();
	}
	@Override
	public Class<BT> getBeanType() {
		return delegate.getBeanType();
	}
	@Override
	public PropertyMeta<BT, KT> getKeyPropertyMeta() {
		return delegate.getKeyPropertyMeta();
	}
	@Override
	public <PT> PropertyMeta<BT, PT> getPropertyMeta(String propertyName) {
		return delegate.getPropertyMeta(propertyName);
	}
	@Override
	public Set<String> getPropertyNames() {
		return delegate.getPropertyNames();
	}
	@Override
	public <PBT, PKT> BeanMeta<PBT, PKT> parent() {
		return delegate.parent();
	}
	@Override
	public String parentKeyProperty(BeanMeta<?, ?> parent) {
		return delegate.parentKeyProperty(parent);
	}
	@Override
	public BT newInstance() {
		return delegate.newInstance();
	}
}