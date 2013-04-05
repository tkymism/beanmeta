package com.tkym.labs.beanmeta;


public abstract class AbstractSuffixBeanMeta<BT,KT> extends AbstractBeanMeta<BT,KT>{
	private SuffixBeanMetaMap<BT,KT> instanceMap;
	protected AbstractSuffixBeanMeta(String name, Class<BT> beanType) {
		this(null, name, beanType);
	}
	protected AbstractSuffixBeanMeta(String namespace, String name, Class<BT> beanType) {
		super(namespace, name, beanType);
		instanceMap = 
				SuffixBeanMetaRegistory.
				get().
				register(this);
	}
	public SuffixBeanMeta<BT, KT> s(String suffix){
		return instanceMap.get(suffix);
	}
}
