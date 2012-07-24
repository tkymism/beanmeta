package com.tkym.labs.beanmeta.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Parent {
	public Class<?> value() default Void.class;
}
