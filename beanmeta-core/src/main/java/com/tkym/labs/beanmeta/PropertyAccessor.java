package com.tkym.labs.beanmeta;



public interface PropertyAccessor<T> {
	public T get();
	public void set(T value);
	
	public interface PropertyAccessorBuilder<B,T> {
		public PropertyAccessor<T> accessor(B bean);
	}
	
	class IndexedPropertyAccessorBuilder<B,T> implements PropertyAccessorBuilder<B,T>{
		private final IndexedAccessorResolver<B,T> resolver;
		private final int index;
		IndexedPropertyAccessorBuilder(int index, IndexedAccessorResolver<B,T> resolver){
			this.index = index;
			this.resolver = resolver;
		}
		@Override
		public PropertyAccessor<T> accessor(B bean) {
			return new AbstractPropertyAccessor<B, T>(bean) {
				@Override
				protected T get(B bean) {
					return resolver.get(bean, index);
				}

				@Override
				protected void set(B bean, T value) {
					resolver.set(bean, index, value);
				}
			};
		}
	}
	
	class DefaultPropertyAccessorBuilder<B,T> implements PropertyAccessorBuilder<B,T>{
		private final PropertyAccessorResolver<B,T> resolver;
		
		DefaultPropertyAccessorBuilder(PropertyAccessorResolver<B,T> accessor){
			this.resolver = accessor;
		}
		
		public PropertyAccessor<T> accessor(B bean){
			return new AbstractPropertyAccessor<B,T>(bean) {
				@Override
				protected T get(B bean) {
					return resolver.get(bean);
				}
				@Override
				protected void set(B bean, T value) {
					resolver.set(bean, value);
				}
			};
		}
	}
	
	abstract class AbstractPropertyAccessor<B,T> implements PropertyAccessor<T> {
		private B bean;
		AbstractPropertyAccessor(B bean){
			this.bean = bean;
		}
		protected abstract T get(B bean);
		public final T get(){
			return this.get(this.bean);
		}
		protected abstract void set(B bean, T value);
		public final void set(T value){
			this.set(bean, value);
		}
	}
}