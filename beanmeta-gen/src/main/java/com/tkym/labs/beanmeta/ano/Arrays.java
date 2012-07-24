package com.tkym.labs.beanmeta.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Arrays {
	public String name() default "";
	public int length();
	public int start() default 0;
	public String pattern() default "";
}