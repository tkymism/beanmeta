package com.tkym.labs.beanmeta.csv;

import java.util.List;

class CsvLineResolver {
	private static final String COMMA = ",";
	private String quote;
	
	CsvLineResolver(String quote){
		this.quote = quote;
	}
	
	String removeQuoteIfEnclosed(String str){
		if(isEnclosed(str)) 
			return removeQuote(str);
		else
			return str;
	}
	
	boolean isEnclosed(String fromCsv){
		if(fromCsv.equals(quote))
			throw new CsvConverterException(CsvConverterException.EXCEPTION_MESSAGE+fromCsv);
		
		boolean isQuoteStart = fromCsv.startsWith(quote);
		boolean isQuoteEnd = fromCsv.endsWith(quote);
		if(isQuoteStart && isQuoteEnd) 
			return true;
		else if(!isQuoteStart && !isQuoteEnd)
			return false;
		else
			throw new CsvConverterException(CsvConverterException.EXCEPTION_MESSAGE+fromCsv);
	}
	
	String removeQuote(String fromCsv){
		String retString = fromCsv;
		if(retString.startsWith(quote))
			retString = retString.substring(1);
		if(retString.endsWith(quote))
			retString = retString.substring(0,retString.length()-1);
		return retString;
	}
	
	String[] separateByComma(String line){
		String[] strings = line.split(COMMA);
		CsvLineArranger arranger = new CsvLineArranger(COMMA, quote);
		for(String target : strings) arranger.put(target);
		List<String> list = arranger.getResultList();
		String[] retArray = new String[list.size()];
		list.toArray(retArray);
		return retArray;
	}
}