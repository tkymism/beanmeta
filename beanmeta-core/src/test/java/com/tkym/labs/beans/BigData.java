package com.tkym.labs.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BigData implements Serializable{
	private int no;
	private String data;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}