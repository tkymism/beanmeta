package com.tkym.labs.beanmeta.csv;

import java.util.ArrayList;
import java.util.List;

class CsvLineArranger{
	private final String quote;
	private final String separator;
	private List<String> list = new ArrayList<String>();
	private StringBuilder builder = new StringBuilder();
	private boolean quoting = false;
	
	CsvLineArranger(String separator, String quoteString){
		this.quote = quoteString;
		this.separator = separator;
	}
	
	void put(String target){
		if(quoting){
			builder.append(separator);
			builder.append(target);
			if(target.endsWith(quote)){
				quoting = false;
				list.add(builder.toString());
				builder = new StringBuilder();
			}
		}else{
			if(target.startsWith(quote)){
				if(!target.equals(quote) && target.endsWith(quote)){
					list.add(target);
				}else{
					quoting = true;
					builder.append(target);
				}
			}else{
				list.add(target);
			}
		}
	}
	
	List<String> getResultList(){
		return list;
	}
}