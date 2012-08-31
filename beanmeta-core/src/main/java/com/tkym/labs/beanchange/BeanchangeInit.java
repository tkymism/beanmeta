package com.tkym.labs.beanchange;

import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.INIT;

import com.tkym.labs.beanmeta.BeanMetaUtils;
import com.tkym.labs.beanmeta.Key;

public class BeanchangeInit<B,K> extends Beanchange<B,K>{
	private final B init;
	BeanchangeInit(Key<B,K> key, B init){
		super(key);
		this.init = 
				BeanMetaUtils.
				get().
				meta(key.getBeanMeta()).
				clone(init);
	}
	B getInit(){
		return this.init;
	}
	public BeanchangeState getState(){
		return INIT;
	}
}