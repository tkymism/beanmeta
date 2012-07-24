package com.tkym.labs.beanmeta;

public interface PropertyAccessorResolver<B,T> {
	public T get(B bean);
	public void set(B bean, T value);
}
