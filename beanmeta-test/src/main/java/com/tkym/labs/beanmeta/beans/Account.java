package com.tkym.labs.beanmeta.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Account implements Serializable{
	private String email;
	private String address;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
}