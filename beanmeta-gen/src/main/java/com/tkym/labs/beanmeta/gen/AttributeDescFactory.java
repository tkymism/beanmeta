package com.tkym.labs.beanmeta.gen;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor6;

import com.tkym.labs.beanmeta.ano.Arrays;


class AttributeDescFactory {
	
	ArraysDesc create(VariableElement varEl, Arrays ano){
		ArraysDesc desc = new ArraysDesc();
		desc.setField(fieldName(varEl));
		desc.setType(new ArraysTypeBuilder(varEl.asType()).build());
		buildAccessorDesc(desc, desc.getField());
        if (!ano.name().equals("")) 
            desc.setName(ano.name());
        else 
            desc.setName(desc.getField());
        
        desc.setLength(ano.length());
        desc.setStart(ano.start());
        if (!ano.pattern().equals(""))
        	desc.setPattern(ano.pattern());
        return desc;
	}
	
	AttributeDesc create(VariableElement varEl, String nameOfAnoValue){
		return create(varEl, nameOfAnoValue, false);
	}
	
	AttributeDesc create(VariableElement varEl, String nameOfAnoValue, boolean indexed){
		try {
			AttributeDesc desc = new AttributeDesc();
	        desc.setField(fieldName(varEl));
			desc.setType(new TypeNameBuilder(varEl.asType()).build());
			buildAccessorDesc(desc, desc.getField());
	        if (nameOfAnoValue != null && !nameOfAnoValue.equals("")) 
	            desc.setName(nameOfAnoValue);
	        else 
	            desc.setName(desc.getField());
	        desc.setIndexed(indexed);
			return desc;
		} catch (NullPointerException e) {
			throw new RuntimeException("NullPointerException on AttributeDescFactory#create", e);
		}
	}
	
	private void buildAccessorDesc(AttributeDesc desc, String propertyName){
		if (propertyName.length() < 1)
			throw new IllegalArgumentException(
					"field name [" + propertyName + "] is illegal");
		String first = propertyName.substring(0, 1);
		String camelCase = propertyName.replaceFirst(first, first.toUpperCase());
		desc.setGetter("get"+camelCase);
		desc.setSetter("set"+camelCase);
	}
	
	private String fieldName(VariableElement varEl){
		return varEl.getSimpleName().toString();
	}
	
	class ArraysTypeBuilder extends TypeNameBuilder{
		ArraysTypeBuilder(TypeMirror typeMirror){
			super(typeMirror);
		}
		@Override
		public String visitArray(ArrayType t, Void p) {
			return t.getComponentType().accept(this, p);
		}
	}
	
	class TypeNameBuilder extends SimpleTypeVisitor6<String, Void>{
		private TypeMirror typeMirror;
		
		TypeNameBuilder(TypeMirror typeMirror){
			this.typeMirror = typeMirror;
		}
		
		String build(){
			return this.typeMirror.accept(this, null);
		}
		
		@Override
		public String visitArray(ArrayType t, Void p) {
			return t.toString();
		}
		
		@Override
		public String visitDeclared(DeclaredType t, Void p) {
			return t.toString();
		}
		
		@Override
		public String visitPrimitive(PrimitiveType t, Void p) {
			return ModelDescUtil.get().getSimpleClassName(t.getKind());
		}
	}
}