package com.tkym.labs.beanmeta.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Key {
	public String name() default "";
	public int order() default 0;
}