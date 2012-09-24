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
	@SuppressWarnings({ "unchecked" })
	public Key<B, K> build(){
		Key<?, ?> current = this.parent;
		for (KeyBuildChain<?,?> chain : chainList)
			current = build(current, chain);
		return (Key<B, K>) current;
	}
	@SuppressWarnings("unchecked")
	private Key<?,?> build(Key<?,?> parent, KeyBuildChain<?,?> chain){
		@SuppressWarnings({ "rawtypes" })
		KeyFactory factory = new KeyFactory(chain.beanMeta);
		if (chain.define.equals(KeyValueDefine.MAX)) return factory.max(parent);
		else if (chain.define.equals(KeyValueDefine.MIN)) return factory.min(parent);
		else return factory.create(parent, chain.value); 
	}
	private enum KeyValueDefine{ DEFAULT, MAX, MIN }
	public static class KeyBuildChain<BT, KT>{
		private final BeanMeta<BT, KT> beanMeta;
		private final KeyBuilder<?,?> builder;
		private KT value;
		private KeyValueDefine define;
		private KeyBuildChain(KeyBuilder<?,?> builder, BeanMeta<BT, KT> beanMeta) {
			this.builder = builder;
			this.beanMeta = beanMeta;
		}
		public KeyBuilder<BT,KT> is(KT value){
			this.value = value;
			this.define = KeyValueDefine.DEFAULT;
			return builder.chain(this);
		}
		public KeyBuilder<BT,KT> max(){
			this.value = null;
			this.define = KeyValueDefine.MAX;
			return builder.chain(this);
		}
		public KeyBuilder<BT,KT> min(){
			this.value = null;
			this.define = KeyValueDefine.MIN;
			return builder.chain(this);
		}
	}
}