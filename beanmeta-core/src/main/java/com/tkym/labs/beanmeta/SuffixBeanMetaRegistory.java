package com.tkym.labs.beanmeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SuffixBeanMetaRegistory {
	private static final SuffixBeanMetaRegistory singleton = 
			new SuffixBeanMetaRegistory();
	private Map<Class<?>, SuffixBeanMetaMap<?,?>> suffixMetaMap;
	private boolean autoGenerateSuffix = true;
	private SuffixBeanMetaRegistory(){
		this.suffixMetaMap = 
				new ConcurrentHashMap<Class<?>, SuffixBeanMetaMap<?,?>>();
	}
	public static SuffixBeanMetaRegistory get(){
		return singleton;
	}
	int size() {
		return suffixMetaMap.size();
	}
	<BT> boolean contain(Class<BT> beanType){
		return suffixMetaMap.containsKey(beanType);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	<BT,KT> SuffixBeanMetaMap<BT,KT> register(BeanMeta<BT, KT> beanMeta){
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
	public void setAutoGenerateSuffix(boolean autoCreateSuffix){
		this.autoGenerateSuffix = autoCreateSuffix;
	}
	public boolean isAutoGenerateSuffix(){
		return this.autoGenerateSuffix;
	}
}
