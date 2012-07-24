package com.tkym.labs.beanmeta;

import java.text.DecimalFormat;
import java.util.Map;

import com.tkym.labs.beanmeta.PropertyAccessor.IndexedPropertyAccessorBuilder;
import com.tkym.labs.beanmeta.PropertyMeta.PropertyMetaImpl;


public class PropertyMetaBuilderAsArray<B,P>{
	private static final int DEFAULT_START_SUFFIX = 0;
	private final Class<B> beanType;
	private final String propertyName;
	private final Class<P> propertyType;
	private final Map<String, PropertyMeta<B, ?>> propertyMap;
	private int start = DEFAULT_START_SUFFIX;
	private String pattern = null;
	private int length = 1;
	PropertyMetaBuilderAsArray(Class<B> beanType, String propertyName, Class<P> propertyType,
			Map<String, PropertyMeta<B, ?>> propertyMap) {
		this.beanType = beanType;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.propertyMap = propertyMap;
	}
	
	private IndexedPropertyAccessorBuilder<B,P> createBuilder(int index, IndexedAccessorResolver<B,P> resolver){
		return new IndexedPropertyAccessorBuilder<B, P>(index, resolver);
	}
	
	private PropertyMeta<B,P> createMeta(String indexedPropertyName, IndexedPropertyAccessorBuilder<B,P> builder){
		PropertyMeta<B,P> meta = new PropertyMetaImpl<B, P>(beanType, indexedPropertyName, propertyType, builder);
		propertyMap.put(meta.getPropertyName(), meta);
		return meta;
	}
	
	public PropertyMetaBuilderAsArray<B,P> start(int start){
		this.start = start;
		return this;
	}
	
	public PropertyMetaBuilderAsArray<B,P> pattern(String pattern){
		this.pattern = pattern;
		return this;
	}
	
	public PropertyMetaBuilderAsArray<B,P> length(int length){
		this.length = length;
		return this;
	}
	
	public PropertyMeta<B,P>[] accessor(IndexedAccessorResolver<B, P> resolver){
		if (pattern == null || pattern.equals(""))
			pattern = createPattern(length, start);
		return build(length, start, pattern, resolver);
	}
	
	@SuppressWarnings("unchecked")
	private PropertyMeta<B,P>[] build(int length, int start, String pattern, IndexedAccessorResolver<B, P> resolver){
		PropertyMeta<?,?>[] arrays = new PropertyMeta[length];
		for (int i=0; i<length; i++)
			arrays[i] = createMeta(
					indexedPropertyName(propertyName, pattern, start+i), 
					createBuilder(i, resolver)
					);
		return (PropertyMeta<B,P>[]) arrays;
	}
	
	String indexedPropertyName(String propertyName, String indexPattern, int index){
		DecimalFormat formatter = new DecimalFormat(indexPattern);
		return propertyName + formatter.format(index);
	}
	
	String createPattern(int length, int start){
		int size = new Integer((length-1+start)).toString().length();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<size; i++) sb.append("0");
		return sb.toString();
	}
}