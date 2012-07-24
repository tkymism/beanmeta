package com.tkym.labs.beanmeta;

import com.tkym.labs.beanmeta.PropertyAccessor.PropertyAccessorBuilder;

public interface PropertyMeta<BT,PT>{
	public String getPropertyName();
	public Class<PT> getPropertyType();
	public Class<BT> getBeanType();
	public PropertyMeta<BT,PT> rename(String propertyName);
	public PropertyAccessor<PT> access(BT bean); 
	public boolean isIndexed();
	public void setIndexed(boolean indexed);
	
	public static class PropertyMetaImpl<BT,PT> implements PropertyMeta<BT,PT>{
		private String propertyName;
		private final Class<PT> propertyType;
		private final Class<BT> beanType;
		private final PropertyAccessorBuilder<BT,PT> accessorBuilder;
		private boolean indexed = false;
		PropertyMetaImpl(Class<BT> beanType, String propertyName, Class<PT> propertyType, PropertyAccessorBuilder<BT,PT> accesssorBuilder){
			this.beanType = beanType;
			this.propertyName = propertyName;
			this.propertyType = propertyType;
			this.accessorBuilder = accesssorBuilder;
		}
		public String getPropertyName() {
			return propertyName;
		}
		public Class<PT> getPropertyType() {
			return propertyType;
		}
		public Class<BT> getBeanType() {
			return beanType;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		public PropertyMeta<BT,PT> rename(String newPropertyName){
			return new PropertyMetaImpl<BT,PT>(beanType, newPropertyName, propertyType, accessorBuilder);
		}
		public PropertyAccessor<PT> access(BT bean){
			return accessorBuilder.accessor(bean);
		}
		public void setIndexed(boolean indexed){
			this.indexed = indexed;
		}
		public boolean isIndexed(){
			return this.indexed;
		}
	}
}