package com.tkym.labs.beanchange;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tkym.labs.beanmeta.BeanMeta;
import com.tkym.labs.beanmeta.Key;

public class BeanchangeStore{
	private Map<Key<?,?>, Beanchange<?,?>> beanchangeMap;
	BeanchangeStore(){
		this.beanchangeMap = 
			new ConcurrentHashMap<Key<?,?>, Beanchange<?,?>>();
	}
	Map<Key<?,?>, Beanchange<?,?>> map(){
		return this.beanchangeMap;
	}
	@SuppressWarnings("unchecked")
	public <B,K> Beanchange<B,K> attach(Key<B,K> key, B bean){
		return (Beanchange<B,K>) beanchangeMap.put(key, init(key, bean));
	}
	@SuppressWarnings("unchecked")
	public <B,K> Beanchange<B,K> detach(Key<B,K> key){
		return (Beanchange<B,K>) beanchangeMap.remove(key);
	}
	@SuppressWarnings("unchecked")
	public <B,K> Beanchange<B,K> get(Key<B,K> key){
		return (Beanchange<B,K>) beanchangeMap.get(key);
	}
	public <B,K> void put(Key<B,K> key, B bean){
		Beanchange<B,K> managed = get(key);
		if (managed == null) 
			beanchangeMap.put(key, add(key, bean));
		else
			beanchangeMap.put(key, convert(managed, bean));
	}
	public <B,K> void remove(Key<B,K> key){
		Beanchange<B,K> managed = get(key);
		if (managed == null)
			throw new BeanchangeException(
					"Beanchange can't remove for not attached."
							+key.getBeanMeta().getName());
		else if (managed instanceof BeanchangeInit)
			beanchangeMap.put(key, remove((BeanchangeInit<B,K>) managed));
		else if (managed instanceof BeanchangeAdd) 
			detach(key);
		else if (managed instanceof BeanchangeModify) 
			beanchangeMap.put(key, remove((BeanchangeModify<B,K>) managed));
		else if (managed instanceof BeanchangeRemove) 
			throw new BeanchangeException(
					"Beanchange already removed"
							+key.getBeanMeta().getName());
	}
	
	@SuppressWarnings("unchecked")
	public <B,K> BeanchangeStoreView<B,K> viewOf(BeanMeta<B,K> beanMeta){
		Map<Key<B,K>, Beanchange<B,K>> map = new HashMap<Key<B,K>, Beanchange<B,K>>();
		for (Key<?,?> key : map().keySet())
			if (key.getBeanMeta().equals(beanMeta))
				map.put( (Key<B,K>) key, (Beanchange<B,K>) map().get(key));
		return new BeanchangeStoreView<B, K>(map);
	}
	
	private <B,K> Beanchange<B,K> convert(Beanchange<B,K> managed, B bean){
		if (managed instanceof BeanchangeInit)
			return modify((BeanchangeInit<B,K>)managed, bean);
		else if (managed instanceof BeanchangeRemove)
			return modify((BeanchangeRemove<B,K>)managed, bean);
		else if (managed instanceof BeanchangeModify)
			return modify((BeanchangeModify<B,K>)managed, bean);
		else if (managed instanceof BeanchangeAdd)
			return add((BeanchangeAdd<B,K>)managed, bean);
		else
			throw new BeanchangeException(
					"illegal Beanchange instance "
							+managed.key().getBeanMeta().getName());
	}
	private <B,K> BeanchangeRemove<B,K> remove(BeanchangeModify<B,K> modify){
		return remove(modify.key(), modify.getBefore());
	}
	private <B,K> BeanchangeRemove<B,K> remove(BeanchangeInit<B,K> init){
		return remove(init.key(), init.getInit());
	}
	private <B,K> BeanchangeRemove<B,K> remove(Key<B,K> key, B before){
		return new BeanchangeRemove<B,K>(key, before);
	}
	private <B,K> Beanchange<B,K> modify(BeanchangeRemove<B,K> remove, B after){
		return modify(remove.key(), remove.getBefore(), after);
	}
	private <B,K> Beanchange<B,K> modify(BeanchangeModify<B,K> init, B after){
		return modify(init.key(), init.getBefore(), after);
	}
	private <B,K> Beanchange<B,K> modify(BeanchangeInit<B,K> init, B after){
		return modify(init.key(), init.getInit(), after);
	}
	private <B,K> Beanchange<B,K> modify(Key<B,K> key, B before, B after){
		BeanchangeModify<B,K> modify = new BeanchangeModify<B, K>(key, before, after);
		if (modify.isEquals())
			return new BeanchangeInit<B,K>(key, before);
		else
			return modify;
	}
	private <B,K> BeanchangeInit<B,K> init(Key<B,K> key, B bean){
		return new BeanchangeInit<B,K>(key, bean);
	}
	private <B,K> BeanchangeAdd<B,K> add(BeanchangeAdd<B,K> add, B after){
		return add(add.key(), after);
	}
	private <B,K> BeanchangeAdd<B,K> add(Key<B,K> key, B bean){
		return new BeanchangeAdd<B,K>(key, bean);
	}
}