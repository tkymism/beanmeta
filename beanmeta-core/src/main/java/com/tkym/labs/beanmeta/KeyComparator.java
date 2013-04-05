package com.tkym.labs.beanmeta;

import java.util.Comparator;

import com.tkym.labs.beanmeta.Key.MaxKeyValue;
import com.tkym.labs.beanmeta.Key.MinKeyValue;

public class KeyComparator<BT,KT extends Comparable<KT>> implements Comparator<Key<BT,KT>>{
	private final PropertyMeta<BT, KT> meta;
	KeyComparator(PropertyMeta<BT, KT> meta){
		this.meta = meta;
	}
	public static <BT,KT extends Comparable<KT>> KeyComparator<BT,KT> comparator(BeanMeta<BT, KT> meta){
		return new KeyComparator<BT, KT>(meta.getKeyPropertyMeta());
	}
	@Override
	public int compare(Key<BT, KT> o1, Key<BT, KT> o2) {
		return typeUnsafeComparator.compare(o1, o2);
	}
	PropertyMeta<BT, KT> meta(){
		return meta;
	}
	private static Comparator<Key<?,?>> typeUnsafeComparator = new Comparator<Key<?,?>>(){
		@Override
		public int compare(Key<?, ?> o1, Key<?, ?> o2) {
			if (o1.getParent() != null && o2.getParent() != null){
				int parent = compare((Key<?, ?>)o1.getParent(), (Key<?, ?>)o2.getParent());
				if (parent != 0) return parent;
			}
			int ret = compareType(o1, o2);
			if (ret != 0) return ret;
			return compareValue(o1, o2);
		}
		
		<T extends Key<?,?>> int typeCompareValue(Class<T> type){
			if (type.equals(MinKeyValue.class)) return -1;
			else if (type.equals(MaxKeyValue.class)) return 1;
			else return 0;
		}
		
		int compareType(Key<?,?> o1, Key<?,?> o2){
			int t1 = typeCompareValue(o1.getClass());
			int t2 = typeCompareValue(o2.getClass());
			int compare = t2-t1;
			if (compare == 0) return 0;
			else if (compare > 0) return -1;
			else return 1;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		int compareValue(Key<?,?> o1, Key<?,?> o2){
			Class<?> c1 = o1.getBeanMeta().getKeyPropertyMeta().getPropertyType();
			Class<?> c2 = o2.getBeanMeta().getKeyPropertyMeta().getPropertyType();
			if (!c1.equals(c2))
				throw new IllegalArgumentException(
						"types are different." +
						" key1="+c1.getName()+
						" key2="+c2.getName());
			if (!Comparable.class.isAssignableFrom(c1))
				throw new IllegalArgumentException(
						"type is not Comparable" +
						" key1="+c1.getName()+
						" key2="+c2.getName());
			if (o1.value() == null && o2.value() != null) return -1;
			else if (o1.value() != null && o2.value() == null) return 1;
			else if (o1.value() == null && o2.value() == null) return 0;
			else return ((Comparable) o1.value()).compareTo(o2.value());
		}
	};
}
