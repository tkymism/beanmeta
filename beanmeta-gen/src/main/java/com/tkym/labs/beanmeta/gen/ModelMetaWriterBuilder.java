package com.tkym.labs.beanmeta.gen;


interface ModelMetaWriterBuilder {
	ModelMetaWriter build(ModelDesc desc)  throws AptException;
}
