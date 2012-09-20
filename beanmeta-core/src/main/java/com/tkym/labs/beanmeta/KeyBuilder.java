package com.tkym.labs.beanmeta;

import java.util.ArrayList;
import java.util.List;


public class KeyBuilder<B,K>{
	private List<KeyBuildChain<?, ?>> chainList = 
			new ArrayList<KeyBuildChain<?,?>>();
	private final Key<B,K> parent;
	private KeyBuilder(Key<B,K> parent){ this.parent = parent; }
	public static KeyBuilder<Void,Void> root(){
		return new KeyBuilder<Void,Void>(null);
	}
	public static <PBT,PKT> KeyBuilder<PBT,PKT> parent(Key<PBT,PKT> parent){
		return new KeyBuilder<PBT,PKT>(parent);
	}
	public <BT, KT> KeyBuildChain<BT, KT> meta(BeanMeta<BT, KT> meta){
		return new KeyBuildChain<BT, KT>(this, meta);
	}
	@SuppressWarnings("unchecked")
	private <BT,KT> KeyBuilder<BT,KT> chain(KeyBuildChain<BT, KT> chain){
		chainList.add(chain);
		return (KeyBuilder<BT,KT>)this;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Key<B, K> build(){
		Key<?, ?> current = this.parent;
		for (KeyBuildChain<?,?> chain : chainList)
			current = new KeyFactory(chain.beanMeta).create(current, chain.value);
		return (Key<B, K>) current;
	}
	public static class KeyBuildChain<BT, KT>{
		private final BeanMeta<BT, KT> beanMeta;
		private final KeyBuilder<?,?> builder;
		private KT value;
		private KeyBuildChain(KeyBuilder<?,?> builder, BeanMeta<BT, KT> beanMeta) {
			this.builder = builder;
			this.beanMeta = beanMeta;
		}
		public KeyBuilder<BT,KT> is(KT value){
			this.value = value;
			return builder.chain(this);
		}
	}
}