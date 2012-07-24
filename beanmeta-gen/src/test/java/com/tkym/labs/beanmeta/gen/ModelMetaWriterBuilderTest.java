package com.tkym.labs.beanmeta.gen;

import java.io.StringWriter;
import java.text.DecimalFormat;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;


public class ModelMetaWriterBuilderTest {
	
	
	private ModelDesc personDesc(){
		ModelDesc desc = new ModelDesc();
		desc.setPackage("org.hoge");
		desc.setMetaType("PersonMeta");
		desc.setModelType("Person");
		desc.setName("PERSON");
		AttributeDesc attr = new AttributeDesc();
		attr.setName("ID");
		attr.setField("id");
		attr.setType("Long");
		attr.setGetter("getId");
		attr.setSetter("setId");
		desc.setKeyDesc(attr);
		desc.addAttribute(attr);
		return desc;
	}
	
	
	@Test
	public void testModelMetaWriterCase001() throws AptException, Exception{
		ModelMetaWriterBuilder builder = new ModelMetaWriterBuilderImpl();
		StringWriter sw = new StringWriter();
		builder.build(personDesc()).write(sw);
		String[] lines = sw.toString().split(System.getProperty("line.separator"));
		Assert.assertThat(lines[9], CoreMatchers.is("public class PersonMeta extends AbstractBeanMeta<Person, Long>{"));
		Assert.assertThat(lines[16], CoreMatchers.is("	private PropertyAccessorResolver<Person, Long> _id_ = new PropertyAccessorResolver<Person, Long>(){"));
		for(int i=0; i<lines.length; i++)
			System.out.println(new DecimalFormat("00").format(i)+":"+lines[i]);
	}
}
