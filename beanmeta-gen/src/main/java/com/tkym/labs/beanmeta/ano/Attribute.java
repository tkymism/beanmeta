package com.tkym.labs.beanmeta.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Attribute {
	public String name() default "";
}