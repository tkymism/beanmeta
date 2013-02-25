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
			if (isAutoCreateSuffix()) registed = create(suffix);
			else throwUnregistedException(beanMeta, suffix);
		return registed;
	}
	private boolean isAutoCreateSuffix(){
		return SuffixBeanMetaRegistory.get().isAutoGenerateSuffix();
	}
	private void throwUnregistedException(BeanMeta<?, ?> beanMeta, String suffix){
		throw new UnregisterException(beanMeta, suffix);
	}
	SuffixBeanMeta<BT,KT> create(String suffix){
		SuffixBeanMeta<BT,KT> created = new SuffixBeanMetaImpl<BT, KT>(suffix, beanMeta);;
		instanceMap.put(created.getSuffix(), created);
		return created;
	}
	public void register(String... suffix){
		for (String sfx : suffix) create(sfx);
	}
	boolean contain(String suffix){
		return instanceMap.containsKey(suffix);
	}
	void remove(String suffix){
		if (instanceMap.containsKey(suffix))
			instanceMap.remove(suffix);
	}
	public void unregister(String... suffix){
		for (String sfx : suffix)  remove(sfx);
	}
	int size(){
		return instanceMap.size();
	}
	public static class UnregisterException extends RuntimeException{
		/** auto generated SID by eclipse. */
		private static final long serialVersionUID = 1L;
		private final String suffix;
		private final BeanMeta<?,?> meta;
		UnregisterException(BeanMeta<?,?> meta, String suffix) {
			super("This Suffix is not registed ["+meta.getBeanType()+":"+suffix+"]");
			this.suffix = suffix;
			this.meta = meta;
		}
		public String getSuffix() {
			return suffix;
		}
		public BeanMeta<?, ?> getBeanMeta() {
			return meta;
		}
	}
}