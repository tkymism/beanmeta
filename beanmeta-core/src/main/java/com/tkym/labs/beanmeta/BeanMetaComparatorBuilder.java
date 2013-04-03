package com.tkym.labs.beanmeta;

import java.util.Comparator;

import com.tkym.labs.common.ChainComparator;
import com.tkym.labs.common.ChainComparator.ChainComparatorBuilder;

/**
 * 
 * @author takayama
 * @param <BT> 
 * @param <KT> 
 */
public class BeanMetaComparatorBuilder<BT,KT> {
	private final BeanMetaUtils utils = BeanMetaUtils.get();
	private final PropertyMeta<BT,KT> keyProperty;
	private final ChainComparatorBuilder<BT> builder = ChainComparator.root(); 
	BeanMetaComparatorBuilder(PropertyMeta<BT,KT> keyProperty){
		this.keyProperty = keyProperty;
	}
	public <PT> BeanMetaComparatorBuilder<BT,KT> asc(PropertyMeta<BT,PT> propertyMeta){
		builder.asc(utils.meta(propertyMeta).comparator());
		return this;
	}
	public <PT> BeanMetaComparatorBuilder<BT,KT> desc(PropertyMeta<BT,PT> propertyMeta){
		builder.desc(utils.meta(propertyMeta).comparator());
		return this;
	}
	public Comparator<BT> chain(){
		return builder.chain();
	}
	public Comparator<BT> chainAscKey(){
		return asc(keyProperty).chain();
	}
	public Comparator<BT> chainDescKey(){
		return desc(keyProperty).chain();
	}
}