package com.tkym.labs.beanchange;

import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.REMOVE;

import com.tkym.labs.beanmeta.Key;

public class BeanchangeRemove<B,K> extends Beanchange<B,K>{
	private final B before;
	BeanchangeRemove(Key<B,K> key, B before){
		super(key);
		this.before = before;
	}
	public B getBefore() {
		return before;
	}
	public BeanchangeState getState(){
		return REMOVE;
	}
}