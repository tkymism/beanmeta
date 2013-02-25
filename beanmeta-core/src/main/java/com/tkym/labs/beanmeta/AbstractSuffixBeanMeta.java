package com.tkym.labs.beanmeta;


public abstract class AbstractSuffixBeanMeta<BT,KT> extends AbstractBeanMeta<BT,KT>{
	private SuffixBeanMetaMap<BT,KT> instanceMap;
	protected AbstractSuffixBeanMeta(String name, Class<BT> beanType) {
		super(name, beanType);
		instanceMap = 
				SuffixBeanMetaRegistory.
				get().
				register(this);
	}
	public SuffixBeanMeta<BT, KT> suffix(String suffix){
		return instanceMap.get(suffix);
	}
}
