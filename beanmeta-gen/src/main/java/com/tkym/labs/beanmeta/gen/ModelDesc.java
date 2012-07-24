package com.tkym.labs.beanmeta.gen;

import java.util.ArrayList;
import java.util.List;


class ModelDesc {
	private String package_;
	private String metaType;
	private String modelType;
	private String name;
	private String parentMeta;
	private List<AttributeDesc> attributeList = new ArrayList<AttributeDesc>();
	private AttributeDesc keyDesc;
	public String getPackage() {
		return this.package_;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPackage(String _package) {
		this.package_ = _package;
	}
	public String getMetaType() {
		return metaType;
	}
	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public List<AttributeDesc> getAttributes() {
		return attributeList;
	}
	public void addAttribute(AttributeDesc desc){
		this.attributeList.add(desc);
	}
	public String getParentMeta() {
		return parentMeta;
	}
	public void setParentMeta(String parentMeta) {
		this.parentMeta = parentMeta;
	}
	public AttributeDesc getKeyDesc() {
		return keyDesc;
	}
	public void setKeyDesc(AttributeDesc keyDesc) {
		this.keyDesc = keyDesc;
	}
}