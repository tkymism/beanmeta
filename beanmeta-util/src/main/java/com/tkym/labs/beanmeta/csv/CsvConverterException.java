package com.tkym.labs.beanmeta.csv;

@SuppressWarnings("serial")
public class CsvConverterException extends RuntimeException	{
	static final String EXCEPTION_MESSAGE = "Csv is Illegal";
	public CsvConverterException(String message, Throwable t) {
		super(message, t);
	}
	public CsvConverterException(String message) {
		super(message);
	}
}