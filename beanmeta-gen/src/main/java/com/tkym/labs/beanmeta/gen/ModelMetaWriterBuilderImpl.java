package com.tkym.labs.beanmeta.gen;

import java.io.IOException;
import java.io.Writer;

import com.tkym.labs.beanmeta.AbstractBeanMeta;
import com.tkym.labs.beanmeta.IndexedAccessorResolver;
import com.tkym.labs.beanmeta.PropertyAccessorResolver;
import com.tkym.labs.beanmeta.PropertyMeta;

class ModelMetaWriterBuilderImpl implements ModelMetaWriterBuilder{
	@Override
	public ModelMetaWriter build(final ModelDesc desc) throws AptException {
		return new ModelMetaWriter() {
			@Override
			public void write(Writer writer) throws Exception {
				MetaFileWriter fileGenerator = new MetaFileWriter(writer);
				fileGenerator.write(desc);
			}
		};
	}
	static class MetaFileWriter{
		private SourceFileWriter w_;
		private static final Class<?> C_ABST_META = AbstractBeanMeta.class;  
		private static final Class<?> C_ACCESSER  = PropertyAccessorResolver.class;  
		private static final Class<?> C_P_META    = PropertyMeta.class;
		private static final Class<?> C_INDEXED_A = IndexedAccessorResolver.class;  
		private static final String ABST_META = AbstractBeanMeta.class.getSimpleName();  
		private static final String ACCESSER  = PropertyAccessorResolver.class.getSimpleName();  
		private static final String INDEXED_ACCESSER  = IndexedAccessorResolver.class.getSimpleName();  
		private static final String PROP_META    = PropertyMeta.class.getSimpleName();
		
		MetaFileWriter(Writer writer){
			w_ = new SourceFileWriter(writer);
		}
		
		void ln(){
			w_.ln("");
		}
		
		void write(ModelDesc desc){
			String C = desc.getMetaType();
			String B = desc.getModelType();
			String K = desc.getKeyDesc().getType();
			if (desc.getPackage() != null)
				w_.ln("package "+desc.getPackage()+";");
			w_.ln();
			w_.ln("import "+C_ABST_META.getName()+";");
			w_.ln("import "+C_ACCESSER.getName()+";");
			w_.ln("import "+C_P_META.getName()+";");
			if (hasArraysDesc(desc))
				w_.ln("import "+C_INDEXED_A.getName()+";");
			w_.ln();
			w_.ln("/**");
			w_.ln(" * This Code is generated automatically "+B);
			w_.ln(" */");
			w_.ln("public class "+C+" extends "+ABST_META+"<"+B+", "+K+">{");
			w_.addIndent();
				w_.ln("private static final "+C+" singleton = new "+C+"();");
				w_.ln();
				w_.ln("public static "+C+" get(){ return singleton; }");
				w_.ln();
				w_.ln("private "+C+"(){ super(\""+desc.getName()+"\", "+B+".class); }");
				w_.ln();
				for (AttributeDesc attr : desc.getAttributes()) write(attr, desc);
				w_.ln("@Override");
				w_.ln("public "+PROP_META+"<"+B+", "+K+"> getKeyPropertyMeta() { return "+desc.getKeyDesc().getField()+"; }");
				w_.ln();
				if (desc.getParentMeta() != null){
					w_.ln("@SuppressWarnings(\"unchecked\")");
					w_.ln("@Override");
					w_.ln("public "+desc.getParentMeta()+" parent() { return "+desc.getParentMeta()+".get(); }");
				}
				w_.ln("@Override");
				w_.ln("public "+B+" newInstance() { return new "+B+"(); }");
				w_.ln();
			w_.removeIndent();
			w_.ln("}");
		}
		
		void write(ArraysDesc attr, ModelDesc model){
			writeAccesser(attr, model);
			writeField(attr, model);
		}
		
		void write(AttributeDesc attr, ModelDesc model){
			if (attr instanceof ArraysDesc){
				writeAccesser((ArraysDesc) attr, model);
				writeField((ArraysDesc) attr, model);
			}else { 
				writeAccesser(attr, model);
				writeField(attr, model);
			}
		}
		
		void writeAccesser(AttributeDesc attr, ModelDesc model){
			String b = model.getModelType();
			String p = attr.getType();
			String f = attr.getField();
			w_.ln("private "+ACCESSER+"<"+b+", "+p+"> _"+f+"_ = new "+ACCESSER+"<"+b+", "+p+">(){").addIndent();
			w_.ln("@Override");
			w_.ln("public "+p+" get("+b+" bean){").addIndent();
			w_.ln("return bean."+attr.getGetter()+"();");
			w_.removeIndent().ln("}");
			w_.ln();
			w_.ln("@Override");
			w_.ln("public void set("+b+" bean, "+p+" value){").addIndent();
			w_.ln("bean."+attr.getSetter()+"(value);");
			w_.removeIndent().ln("}");
			w_.removeIndent().ln("};");
		}
		
		void writeAccesser(ArraysDesc attr, ModelDesc model){
			String b = model.getModelType();
			String p = attr.getType();
			String f = attr.getField();
			w_.ln("private "+INDEXED_ACCESSER+"<"+b+", "+p+"> _"+f+"_ = new "+INDEXED_ACCESSER+"<"+b+", "+p+">(){").addIndent();
			w_.ln("@Override");
			w_.ln("public "+p+" get("+b+" bean, int index){").addIndent();
			w_.ln("return bean."+attr.getGetter()+"(index);");
			w_.removeIndent().ln("}");
			w_.ln();
			w_.ln("@Override");
			w_.ln("public void set("+b+" bean, int index, "+p+" value){").addIndent();
			w_.ln("bean."+attr.getSetter()+"(index, value);");
			w_.removeIndent().ln("}");
			w_.removeIndent().ln("};");
		}
		
		void writeField(AttributeDesc attr, ModelDesc model){
			String b = model.getModelType();
			String p = attr.getType();
			String asIndex = "";
			if (attr.isIndexed())
				asIndex = "asIndex().";
			String f = attr.getField();
				w_.ln("public final "+PROP_META+"<"+b+", "+p+"> "+f+" = "+
						"property(\""+attr.getName()+"\", "+p+".class)."+asIndex+"accessor(_"+f+"_);");
			w_.ln();
		}
		
		void writeField(ArraysDesc attr, ModelDesc model){
			String b = model.getModelType();
			String p = attr.getType();
			String f = attr.getField();
			StringBuilder sb = new StringBuilder();
			sb.append("public final "+PROP_META+"<"+b+", "+p+">[] "+f+" = ");
			sb.append("arrays(\""+attr.getName()+"\", "+p+".class)");
			sb.append(".length("+attr.getLength()+")");
			sb.append(".start("+attr.getStart()+")");
			if (attr.getPattern() != null && !attr.getPattern().equals(""))
				sb.append(".pattern("+attr.getPattern()+")");
			sb.append(".accessor(_"+f+"_);");
			w_.ln(sb.toString());
			w_.ln();
		}
		
		boolean hasArraysDesc(ModelDesc desc){
			for (AttributeDesc attr : desc.getAttributes())
				if (attr instanceof ArraysDesc) return true;
			return false;
		}
	}
	
	static class SourceFileWriter{
		private final Writer writer;
		private int indentLevel = 0; 
		private static final String _N = System.getProperty("line.separator");
		
		SourceFileWriter(Writer writer){
			this.writer = writer;
		}
		
		SourceFileWriter removeIndent(){
			indentLevel--;
			return this;
		}
		
		SourceFileWriter addIndent(){
			indentLevel++;
			return this;
		}
		
		SourceFileWriter ln(){
			return ln("");
		}
		
		SourceFileWriter wr(String... lines){
			for(String line : lines) ln(line);
			return this;
		}
		
		SourceFileWriter ln(String line){
			try {
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<indentLevel;i++) sb.append("\t");
				sb.append(line);
				sb.append(_N);
				writer.write(sb.toString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return this;
		}
	}
}