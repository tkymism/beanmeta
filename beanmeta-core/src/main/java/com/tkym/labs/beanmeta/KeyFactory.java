package com.tkym.labs.beanmeta;

class KeyFactory<BT,KT> {
	private final BeanMeta<BT, KT> beanMeta;
	KeyFactory(BeanMeta<BT, KT> beanMeta){
		this.beanMeta = beanMeta;
	}
	Key<BT, KT> create(Key<?,?> parent, KT value){
		if (parent != null)
			checkParentBeanMeta(parent);
		return new Key<BT, KT>(parent, beanMeta, value);
	}
	Key<BT, KT> create(KT value){
		return create(null, value);
	}
	private void checkParentBeanMeta(Key<?,?> parent){
		BeanMeta<?,?> definedMeta = beanMeta.parent();
		if (definedMeta == null){
			if (parent != null) 
				throw new IllegalArgumentException(
						beanMeta.getName()+"'s "+
								"parent BeanMeta difine as null " + 
								", but key parent is setting " + 
								parent.getBeanMeta().getName());
		}else{
			if (parent == null)
				throw new IllegalArgumentException(
						beanMeta.getName()+"'s "+
								"parent BeanMeta difine:" + 
								beanMeta.parent().getName() +
								", but key parent is null ");
			else
				if (!parent.getBeanMeta().equals(definedMeta))
					throw new IllegalArgumentException(
							beanMeta.getName()+"'s "+
									"parent BeanMeta difine:" + 
									beanMeta.parent().getName() +
									", but key parent is " + 
									parent.getBeanMeta().getName());
		}
	}
}