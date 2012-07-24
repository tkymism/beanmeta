package com.tkym.labs.beanmeta.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvDataLoader<BT, KT>{
	private final InputStream inputStream;
	private final CsvBeanConverter<BT, KT> converter;
	private String charset = "UTF-8";
	public CsvDataLoader(InputStream inputStream, CsvBeanConverter<BT, KT> converter){
		this.inputStream = inputStream;
		if (inputStream == null)
			throw new IllegalArgumentException("inputstream is null");
		this.converter = converter;
	}
	
	public CsvDataLoader<BT, KT> charset(String charset){
		this.charset = charset;
		return this;
	}
	
	public List<BT> asList() throws Exception{
		final List<BT> result = new ArrayList<BT>();
		load(new CsvDataProcesser<BT>() {
			@Override
			public void process(BT bean) {
				result.add(bean);
			}
		});
		return result;
	}
	
	public void load(CsvDataProcesser<BT> processer) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(charset)));
		while(br.ready())
			processer.process(converter.bean(br.readLine()));
		br.close();
		inputStream.close();
	}
}