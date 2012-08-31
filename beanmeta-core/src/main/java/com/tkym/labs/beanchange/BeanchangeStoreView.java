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

public class BeanchangeStoreView<B,K> {
	private final Map<Key<B,K>, Beanchange<B,K>> map;
	BeanchangeStoreView(Map<Key<B,K>, Beanchange<B,K>> map){
		this.map = new ConcurrentHashMap<Key<B,K>, Beanchange<B,K>>(map);
	}
	private BeanchangeStoreView<B,K> filter(BeanchangeState state){
		Map<Key<B,K>, Beanchange<B,K>> filterd = 
				new ConcurrentHashMap<Key<B,K>, Beanchange<B,K>>();
		for (Key<B,K> key : map.keySet()){
			Beanchange<B,K> change = map.get(key);
			if(change.getState().equals(state))
				filterd.put(key, change);
		}
		return new BeanchangeStoreView<B,K>(filterd);
	}
	public BeanchangeStoreView<B,K> remove(){
		return filter(REMOVE);
	}
	public BeanchangeStoreView<B,K> add(){
		return filter(ADD);
	}
	public BeanchangeStoreView<B,K> modify(){
		return filter(MODIFY);
	}
	public BeanchangeStoreView<B,K> init(){
		return filter(INIT);
	}
	public List<Beanchange<B,K>> asList(){
		return new ArrayList<Beanchange<B,K>>(map.values());
	}
	public Set<Beanchange<B,K>> asSet(){
		return new HashSet<Beanchange<B,K>>(map.values());
	}
	public Map<Key<B,K>, Beanchange<B,K>> asMap(){
		return new HashMap<Key<B,K>, Beanchange<B,K>>(map);
	}
}