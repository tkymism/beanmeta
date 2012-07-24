package com.tkym.labs.beanmeta.csv;

public interface CsvDataProcesser<BT>{
	public void process(BT bean) throws Exception;
}