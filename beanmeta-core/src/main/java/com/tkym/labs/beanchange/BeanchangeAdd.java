package com.tkym.labs.beanchange;

import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.ADD;

import com.tkym.labs.beanmeta.Key;

public class BeanchangeAdd<B,K> extends Beanchange<B,K>{
	private final B after;
	BeanchangeAdd(Key<B,K> key, B after){
		super(key);
		this.after = after;
	}
	public B getAfter() {
		return after;
	}
	public BeanchangeState getState(){
		return ADD;
	}
}