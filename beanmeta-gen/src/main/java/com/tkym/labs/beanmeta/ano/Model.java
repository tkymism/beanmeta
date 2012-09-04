package com.tkym.labs.beanmeta.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Model {
	public String name() default "";
	public Class<?> parent() default Void.class;
	public boolean isSuffix() default false;
}