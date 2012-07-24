package com.tkym.labs.beanmeta.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class CsvWriter{
	private OutputStream os = null;
	private Writer wr = null;
	private BufferedWriter bw = null;
	private String charset = "UTF-8";
	
	public CsvWriter(File file) throws IOException {
		if(file.exists()) file.delete();
		file.createNewFile();
		os = new FileOutputStream(file);	
		wr = new OutputStreamWriter(os, charset);
		bw = new BufferedWriter(wr);
	}
	
	public void write(Object... values) throws IOException {
		write(Arrays.asList(values));
	}
	
	public void write(List<Object> values) throws IOException{
		bw.write(createLine(values));
		bw.newLine();
	}
	
	public void flush() throws IOException{
		bw.flush();
	}
	
	private String createLine(List<Object> values){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object value : values){
			if (first) first = false;
			else sb.append(",");
			sb.append("\"");
			sb.append(value.toString());
			sb.append("\"");
		}
		return sb.toString();
	}
	
	void close() throws IOException{
		bw.close();
		wr.close();
		os.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		close();
	}
}