package com.tkym.labs.beanmeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SuffixBeanMetaMapRegister {
	private static final SuffixBeanMetaMapRegister singleton = 
			new SuffixBeanMetaMapRegister();
	private Map<Class<?>, SuffixBeanMetaMap<?,?>> suffixMetaMap;
	private SuffixBeanMetaMapRegister(){
		this.suffixMetaMap = 
				new ConcurrentHashMap<Class<?>, SuffixBeanMetaMap<?,?>>();
	}
	
	public static SuffixBeanMetaMapRegister getInstance(){
		return singleton;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <BT,KT> SuffixBeanMetaMap<BT,KT> register(BeanMeta<BT, KT> beanMeta){
		SuffixBeanMetaMap<BT,KT> create = new SuffixBeanMetaMap(beanMeta);
		suffixMetaMap.put(beanMeta.getBeanType(), create);
		return create;
	}
	@SuppressWarnings("unchecked")
	public <BT,KT> SuffixBeanMetaMap<BT,KT> meta(BeanMeta<BT,KT> beanMeta){
		return (SuffixBeanMetaMap<BT,KT>) suffixMetaMap.get(beanMeta.getBeanType());
	}
	@SuppressWarnings("unchecked")
	public <BT,KT> SuffixBeanMetaMap<BT,KT> type(Class<BT> cls){
		return (SuffixBeanMetaMap<BT,KT>) suffixMetaMap.get(cls);
	}
}
