package com.tkym.labs.beanchange;

import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.MODIFY;

import com.tkym.labs.beanmeta.BeanMetaUtils;
import com.tkym.labs.beanmeta.Key;
import com.tkym.labs.beanmeta.BeanMetaUtils.BeanTypeMetaUtils;

public class BeanchangeModify<B,K> extends Beanchange<B,K>{
	private final B before;
	private B after;
	BeanchangeModify(Key<B,K> key, B before, B after){
		super(key);
		this.before = before;
		this.after = after;
	}
	public B getBefore() {
		return before;
	}
	public B getAfter() {
		return after;
	}
	boolean isEquals(){
		BeanTypeMetaUtils<B,K> type = BeanMetaUtils.get().meta(key().getBeanMeta());
		return type.equals(before, after);
	}
	public BeanchangeState getState(){
		return MODIFY;
	}
}