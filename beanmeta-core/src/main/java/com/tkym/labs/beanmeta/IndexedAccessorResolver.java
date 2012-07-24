package com.tkym.labs.beanmeta;

public interface IndexedAccessorResolver<B,T>{
	public T get(B bean, int index);
	public void set(B bean, int index, T value);
}