package com.tkym.labs.beanmeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SuffixBeanMetaMap<BT,KT>{
	private final Map<String, SuffixBeanMeta<BT, KT>> instanceMap =
			new ConcurrentHashMap<String, SuffixBeanMeta<BT,KT>>();
	private final BeanMeta<BT,KT> beanMeta;
	SuffixBeanMetaMap(BeanMeta<BT,KT> beanMeta){
		this.beanMeta = beanMeta;
	}
	public SuffixBeanMeta<BT,KT> get(String suffix){
		SuffixBeanMeta<BT, KT> registed = instanceMap.get(suffix);
		if (registed == null) 
			throw new IllegalArgumentException(
					"This Suffix is not registed ["+beanMeta.getBeanType()+":"+suffix+"]");
		return registed;
	}
	SuffixBeanMeta<BT,KT> create(String suffix){
		SuffixBeanMeta<BT,KT> created = new SuffixBeanMetaImpl<BT, KT>(suffix, beanMeta);;
		instanceMap.put(created.getSuffix(), created);
		return created;
	}
	public void register(String... suffix){
		for (String sfx : suffix) create(sfx);
	}
}