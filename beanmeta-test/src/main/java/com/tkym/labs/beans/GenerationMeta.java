package com.tkym.labs.beans;

import com.tkym.labs.beanmeta.AbstractSuffixBeanMeta;
import com.tkym.labs.beanmeta.PropertyAccessorResolver;
import com.tkym.labs.beanmeta.PropertyMeta;

public class GenerationMeta extends AbstractSuffixBeanMeta<Generation, Integer>{
	private static final GenerationMeta singleton = new GenerationMeta();
	private GenerationMeta(){
		super("generation", Generation.class);
	}
	public static GenerationMeta get(){ return singleton; }
	private PropertyAccessorResolver<Generation, Integer> _id_ = new PropertyAccessorResolver<Generation, Integer>(){
		@Override
		public Integer get(Generation bean) {
			return bean.getId();
		}
		@Override
		public void set(Generation bean, Integer value) {
			bean.setId(value);
		}
	};
	public final PropertyMeta<Generation, Integer> id = property("id", Integer.class).accessor(_id_);
	private PropertyAccessorResolver<Generation, String> _name_ = new PropertyAccessorResolver<Generation, String>(){
		@Override
		public String get(Generation bean) {
			return bean.getName();
		}
		@Override
		public void set(Generation bean, String value) {
			bean.setName(value);
		}
	};
	public final PropertyMeta<Generation,String> name = property("name", String.class).accessor(_name_);
	@Override
	public PropertyMeta<Generation, Integer> getKeyPropertyMeta() { return id; }
	@Override
	public Generation newInstance() { return new Generation(); }
}
