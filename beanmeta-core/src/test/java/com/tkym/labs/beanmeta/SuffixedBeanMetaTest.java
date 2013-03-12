package com.tkym.labs.beanmeta;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tkym.labs.beans.Generation;
import com.tkym.labs.beans.GenerationMeta;

public class SuffixedBeanMetaTest {
	@Test
	public void testSuffixedBeanMetaCase001(){
		SuffixBeanMetaRegistory registory = SuffixBeanMetaRegistory.get(); 
		assertThat(registory.size(), is(0));
		GenerationMeta geneMeta = GenerationMeta.get();
		assertThat(registory.size(), is(1));
		geneMeta.s("aaa");
	}
	@Test
	public void testSuffixedBeanMetaCase002(){
		SuffixBeanMetaRegistory registory = SuffixBeanMetaRegistory.get(); 
		GenerationMeta GENERATION = GenerationMeta.get();
		assertThat(registory.size(), is(1));
		registory.meta(GENERATION).register("aaa","bbb");
		assertThat(registory.meta(GENERATION).size(), is(2));
		assertTrue(registory.meta(GENERATION).contain("aaa"));
		assertTrue(registory.meta(GENERATION).contain("bbb"));
		SuffixBeanMeta<Generation, Integer> aaaMeta = GENERATION.s("aaa");
		SuffixBeanMeta<Generation, Integer> bbbMeta = GENERATION.s("bbb");
		assertFalse(aaaMeta.equals(bbbMeta));
		registory.meta(GENERATION).unregister("aaa","bbb");
		assertThat(registory.meta(GENERATION).size(), is(0));
	}
}
