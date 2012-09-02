package com.tkym.labs.beanchange;

import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.ADD;
import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.INIT;
import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.MODIFY;
import static com.tkym.labs.beanchange.Beanchange.BeanchangeState.REMOVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tkym.labs.beanchange.Beanchange.BeanchangeState;
import com.tkym.labs.beanmeta.Key;

public class BeanchangeStoreView<B,K,T extends Beanchange<B, K>> {
	private final Map<Key<B,K>, T> map;
	BeanchangeStoreView(Map<Key<B,K>, T> map){
		this.map = new ConcurrentHashMap<Key<B,K>, T>(map);
	}
	private BeanchangeStoreView<B,K,T> filter(BeanchangeState state){
		Map<Key<B,K>, T> filterd = 
				new ConcurrentHashMap<Key<B,K>, T>();
		for (Key<B,K> key : map.keySet()){
			T change = map.get(key);
			if(change.getState().equals(state))
				filterd.put(key, change);
		}
		return new BeanchangeStoreView<B,K,T>(filterd);
	}
	@SuppressWarnings("unchecked")
	public BeanchangeStoreView<B,K,BeanchangeRemove<B, K>> remove(){
		return (BeanchangeStoreView<B,K,BeanchangeRemove<B, K>>) filter(REMOVE);
	}
	@SuppressWarnings("unchecked")
	public BeanchangeStoreView<B,K,BeanchangeAdd<B,K>> add(){
		return (BeanchangeStoreView<B,K,BeanchangeAdd<B, K>>) filter(ADD);
	}
	@SuppressWarnings("unchecked")
	public BeanchangeStoreView<B,K,BeanchangeModify<B,K>> modify(){
		return (BeanchangeStoreView<B,K,BeanchangeModify<B,K>>) filter(MODIFY);
	}
	@SuppressWarnings("unchecked")
	public BeanchangeStoreView<B,K,BeanchangeInit<B,K>> init(){
		return (BeanchangeStoreView<B,K,BeanchangeInit<B,K>>) filter(INIT);
	}
	public List<T> asList(){
		return new ArrayList<T>(map.values());
	}
	public Set<T> asSet(){
		return new HashSet<T>(map.values());
	}
	public Map<Key<B,K>, T> asMap(){
		return new HashMap<Key<B,K>, T>(map);
	}
}