package com.tkym.labs.beanchange;

import com.tkym.labs.beanmeta.Key;

public abstract class Beanchange<B,K>{
	public enum BeanchangeState{
		INIT,ADD,MODIFY,REMOVE
	}
	private final Key<B,K> key;
	Beanchange(Key<B,K> key){
		this.key = key;
	}
	public Key<B, K> key() {
		return key;
	}
	public abstract BeanchangeState getState();
}