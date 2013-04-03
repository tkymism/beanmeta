package com.tkym.labs.beanmeta;

import java.util.NavigableSet;

import org.junit.Test;

import com.tkym.labs.beans.HogeMeta;


public class BeanMetaCollectionsTest {
	private static final HogeMeta HOGE = HogeMeta.get(); 
	private static final BeanMetaUtils UTILS = BeanMetaUtils.get();
//	@Test
//	public void testNavigableSetOperatorCase001(){
//		UTILS.meta(HOGE).comparator().asc(propertyMeta)
//	}
	
	class NavigableOperator<BT>{
		private final NavigableSet<BT> navigableSet;
		NavigableOperator(NavigableSet<BT> navigableSet){
			this.navigableSet = navigableSet;
		}
	}
	
	class BeanMetaCollections<BT, KT>{
		private BeanMeta<BT, KT> beanMeta;
		BeanMetaCollections(BeanMeta<BT, KT> beanMeta){
			this.beanMeta = beanMeta;
		}
	}
}
