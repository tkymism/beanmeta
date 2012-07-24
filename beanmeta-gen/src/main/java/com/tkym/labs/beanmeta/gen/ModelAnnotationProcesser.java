package com.tkym.labs.beanmeta.gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import com.tkym.labs.beanmeta.ano.Arrays;
import com.tkym.labs.beanmeta.ano.Attribute;
import com.tkym.labs.beanmeta.ano.Index;
import com.tkym.labs.beanmeta.ano.Key;


@SupportedAnnotationTypes("jp.tkym.labs.beanstore.ano.Model")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ModelAnnotationProcesser extends AbstractProcessor{
	private ModelDescFactory modelDescFactory = new ModelDescFactory();
	private ModelMetaWriterBuilder builder = new ModelMetaWriterBuilderImpl();
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Iterator<? extends TypeElement> ite = annotations.iterator();
		while(ite.hasNext())
			for(Element classEl : roundEnv.getElementsAnnotatedWith(ite.next()))
				process(asTypeEl(classEl), roundEnv);
		return true;
	}
	
	private TypeElement asTypeEl(Element el){
        if (el instanceof TypeElement) return (TypeElement) el;
        throw new IllegalArgumentException("element is illegal type"+el.getKind().name());
	}
	
	private VariableElement asVariableEl(Element el){
        if (el instanceof VariableElement) return (VariableElement) el;
        throw new IllegalArgumentException("element is illegal type"+el.getKind().name());
	}
	
	private void process(TypeElement classEl, RoundEnvironment roundEnv) { 
		
		ModelDesc modelDesc = modelDescFactory.create(classEl);
	    if (modelDesc == null) 
	    	throw new IllegalArgumentException("model desc is null");
	    try {
	        VariableElementBuilder builder = new VariableElementBuilder(modelDesc);
	    	for (Element el : classEl.getEnclosedElements())
	    		if (el instanceof VariableElement)
	    		    builder.visitVariable(asVariableEl(el), null);
	        validateModelDesc(modelDesc);
	        generateSource(modelDesc);
		} catch (Exception e) {
		    processingEnv.getMessager().printMessage(Kind.ERROR, classEl.getQualifiedName()+":"+e.toString());
		}
	}
	
	private void validateModelDesc(ModelDesc modelDesc){
	    if (modelDesc.getAttributes().size()==0)
	        throw new IllegalArgumentException("attributes size is 0");
        if (modelDesc.getKeyDesc() == null)
            throw new IllegalArgumentException("key is null");
        if (modelDesc.getMetaType() == null)
            throw new IllegalArgumentException("meta type is null");
	}
	
	class VariableElementBuilder {
		
	    private final AttributeDescFactory attributeDescFactory = new AttributeDescFactory();
	    private final ModelDesc modelDesc;
	    VariableElementBuilder(ModelDesc modelDesc){
	        this.modelDesc = modelDesc;
	    }
	    
		public Void visitVariable(VariableElement el, Void p) {
	        Attribute attrAno = el.getAnnotation(Attribute.class);
	        if (attrAno != null) build(el, attrAno);
	        Arrays arrays = el.getAnnotation(Arrays.class);
	        if (arrays != null) build(el, arrays);
	        Key keyAno = el.getAnnotation(Key.class);
	        if (keyAno != null) build(el, keyAno);
	        return null;
		}
	    
	    private void build(VariableElement el, Arrays ano){
	        ArraysDesc arrays = attributeDescFactory.create(el, ano);
	        modelDesc.addAttribute(arrays);
	    }
	    
	    private void build(VariableElement el, Key ano){
	    	AttributeDesc attr = attributeDescFactory.create(el, ano.name());
            modelDesc.addAttribute(attr);
            modelDesc.setKeyDesc(attr);
        }
	    
	    private void build(VariableElement el, Attribute ano){
	    	boolean indexed = false;
	        Index indexedAno = el.getAnnotation(Index.class);
	        if (indexedAno != null) indexed = true;
	    	modelDesc.addAttribute(attributeDescFactory.create(el, ano.name(), indexed));
	    }
	}
	
	
	private void generateSource(ModelDesc desc) {
		PrintWriter writer = null;
		try {
			JavaFileObject sourcefile = 
					processingEnv.getFiler().
					createSourceFile(desc.getPackage()+"."+desc.getMetaType());
			writer = new PrintWriter(sourcefile.openWriter(), true);
			builder.build(desc).write(writer);
		} catch (Exception e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, e.toString());
		} finally {
			if (writer!=null)
				writer.close();
		}
	}
	
	@SuppressWarnings("unused")
	private void generateSource(ModelInfo info) {
		PrintWriter writer = null;
		try {
			JavaFileObject sourcefile = 
					processingEnv.getFiler().
					createSourceFile("jp.tkym.labs.gen."+info.getClassName());
			writer = new PrintWriter(sourcefile.openWriter(), true);
			writer.println("package jp.tkym.labs.gen;");
			writer.println("public class "+info.getClassName() + " {");
			writer.println("	public "+info.getClassName()+"(){}");
			for(String attr : info.getAttrbiteList())
				writer.println("public void "+attr+"(){}");
			writer.println("}");
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, e.toString());
		} finally {
			if (writer!=null)
				writer.close();
		}
	}

	class ModelInfo {
		private final String className;
		private List<String> attributeList = new ArrayList<String>();
		ModelInfo(String className){
			this.className = className;
		}
		String getClassName(){
			return this.className;
		}
		void addAttribute(String attribute){
			this.attributeList.add(attribute);
		}
		List<String> getAttrbiteList(){
			return this.attributeList;
		}
	}
}