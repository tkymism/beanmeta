package com.tkym.labs.beanmeta.gen;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import com.tkym.labs.beanmeta.ano.Model;



class ModelDescFactory {
	private static final String STR_META = "Meta";
	
	/**
	 * 
	 * @param classEl
	 * @return
	 */
	ModelDesc create(TypeElement classEl){
		ModelDesc desc = new ModelDesc();
		desc.setModelType(classEl.getSimpleName().toString());
		desc.setMetaType(desc.getModelType()+STR_META);
		desc.setPackage(convertToPackageName(classEl));
		buildFromAnnotation(desc, classEl);
		return desc;
	}
	
	private void buildFromAnnotation(ModelDesc desc, TypeElement classEl){
		Model ano = classEl.getAnnotation(Model.class);
		if (ano == null)
			throw new IllegalArgumentException();
		String name = ano.name();
		if (!name.equals("")) desc.setName(name);
		else desc.setName(desc.getModelType());
		String parent = searchParentClassValue(classEl);
		if (parent != null)
			desc.setParentMeta(parent+STR_META);
	}
	
	private String convertToPackageName(TypeElement typeElement){
		String qualifiedName = typeElement.getQualifiedName().toString();
		String simpleName = typeElement.getSimpleName().toString();
		if (!qualifiedName.endsWith(simpleName))
			throw new IllegalArgumentException();
		if (qualifiedName.equals(simpleName)) return null;
		int packageEndIndex = qualifiedName.length() - simpleName.length() - 1; 
		return qualifiedName.substring(0, packageEndIndex);
	}
	
	private String searchParentClassValue(TypeElement typeEl){
		for(AnnotationMirror mirror : typeEl.getAnnotationMirrors())
			if(mirror.getAnnotationType().toString().equals(Model.class.getName()))
				for(ExecutableElement exec : mirror.getElementValues().keySet())
					if(exec.getSimpleName().toString().equals("parent"))
						return mirror.getElementValues().get(exec).getValue().toString();
		return null;
	}
}