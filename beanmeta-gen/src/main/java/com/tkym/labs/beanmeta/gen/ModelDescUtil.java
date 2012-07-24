package com.tkym.labs.beanmeta.gen;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.TypeKind;

class ModelDescUtil {
	private static ModelDescUtil singleton = new ModelDescUtil();
	private Map<TypeKind, String> primitiveTypeMap = new HashMap<TypeKind, String>(); 
	
	static ModelDescUtil get(){ return singleton; }
	
	private ModelDescUtil(){
		primitiveTypeMap.put(TypeKind.INT, 		Integer.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.LONG, 	Long.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.DOUBLE, 	Double.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.FLOAT, 	Float.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.BOOLEAN, 	Boolean.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.BYTE, 	Byte.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.SHORT, 	Short.class.getSimpleName());
		primitiveTypeMap.put(TypeKind.CHAR, 	Character.class.getSimpleName());
	} 
	
	String getSimpleClassName(TypeKind kind){
		if (!primitiveTypeMap.containsKey(kind))
			throw new IllegalArgumentException(kind.name()+" is illegal type");
		return primitiveTypeMap.get(kind);
	}
}
