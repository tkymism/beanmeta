package com.tkym.labs.beanmeta;

import java.util.Comparator;

import com.tkym.labs.common.ChainComparator;
import com.tkym.labs.common.ChainComparator.ChainComparatorBuilder;

/**
 * Represents a Comparator Builder using a bean-meta framework.
 * <h3>Usage:</h3>
 * <pre>
 * 	PersonMeta PERSON = PersonMeta.get();
 * 	Comparator&ltPerson&gt comparator = 
 * 		BeanMetaUtils.
 * 		comparatorBuilder(PERSON).	// define Person.
 * 		desc(PERSON.name).			// descending by property "name".  
 * 		asc(PERSON.id).				// ascending by property "id".
 * 		chain();					// join two comparators to one comparator for bean. 
 * </pre>
 * @author takayama
 * @param <BT> 
 * @param <KT> 
 */
public class BeanMetaComparatorBuilder<BT,KT extends Comparable<KT>> {
	private final BeanMetaUtils utils = BeanMetaUtils.get();
	private final PropertyMeta<BT,KT> keyProperty;
	private final ChainComparatorBuilder<BT> builder = ChainComparator.root(); 
	BeanMetaComparatorBuilder(PropertyMeta<BT,KT> keyProperty){
		this.keyProperty = keyProperty;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> asc(PropertyMeta<BT,PT> propertyMeta){
		builder.asc(utils.beanComparator(propertyMeta));
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> desc(PropertyMeta<BT,PT> propertyMeta){
		builder.desc(utils.beanComparator(propertyMeta));
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> ascNullFirst(PropertyMeta<BT,PT> propertyMeta){
		builder.asc(utils.beanComparator(propertyMeta).nullFirst());
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> descNullFirst(PropertyMeta<BT,PT> propertyMeta){
		builder.desc(utils.beanComparator(propertyMeta).nullFirst());
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> ascNullLast(PropertyMeta<BT,PT> propertyMeta){
		builder.asc(utils.beanComparator(propertyMeta).nullLast());
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> descNullLast(PropertyMeta<BT,PT> propertyMeta){
		builder.desc(utils.beanComparator(propertyMeta).nullLast());
		return this;
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> ascKey(){
		return asc(keyProperty);
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> descKey(){
		return desc(keyProperty);
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> ascKeyNullFirst(){
		return ascNullFirst(keyProperty);
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> descKeyNullFirst(){
		return descNullFirst(keyProperty);
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> ascKeyNullLast(){
		return ascNullLast(keyProperty);
	}
	public <PT extends Comparable<PT>> BeanMetaComparatorBuilder<BT,KT> descKeyNullLast(){
		return descNullLast(keyProperty);
	}
	public Comparator<BT> chain(){
		return builder.chain();
	}
}