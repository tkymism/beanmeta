package com.tkym.labs.beanmeta;


public class BeanMetaCollections____ {
//	private static final HogeMeta HOGE = HogeMeta.get(); 
//	
//	@Test
//	public void testBeanMetaNavigableSetBuilder_Case001(){
//		NavigableSet<Hoge> set = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			builder().
//			asc(HOGE.intValue).
//			ascKey().
//			asConcurrentSkipListSet();
//		
//		set.add(HogeBuilder.create().key("aab").intValue(3).bean());
//		set.add(HogeBuilder.create().key("aab").intValue(2).bean());
//		set.add(HogeBuilder.create().key("aab").intValue(1).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(3).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(2).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(1).bean());
//		
//		Iterator<Hoge> ite = set.iterator();
//		Hoge h = null;
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(1));
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(1));
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(2));
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(2));
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(3));
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(3));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetBuilder_Case002(){
//		NavigableSet<Hoge> set = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			builder().
//			asc(HOGE.intValue).
//			descKey().
//			asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("aab").intValue(3).bean());
//		set.add(HogeBuilder.create().key("aab").intValue(2).bean());
//		set.add(HogeBuilder.create().key("aab").intValue(1).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(3).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(2).bean());
//		set.add(HogeBuilder.create().key("aaa").intValue(1).bean());
//		Iterator<Hoge> ite = set.iterator();
//		Hoge h = null;
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(1));
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(1));
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(2));
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(2));
//		h = ite.next();
//		assertThat(h.getKey(), is("aab"));
//		assertThat(h.getIntValue(), is(3));
//		h = ite.next();
//		assertThat(h.getKey(), is("aaa"));
//		assertThat(h.getIntValue(), is(3));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetBuilder_Case003(){
//		NavigableSet<Hoge> set = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			builder().
//			asc(HOGE.floatValue).
//			asc(HOGE.intValue).
//			asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("a").intValue(3).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("b").intValue(3).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("c").intValue(2).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("d").intValue(2).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("e").intValue(1).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("f").intValue(1).floatValue(0.7f).bean());
//		Iterator<Hoge> ite = set.iterator();
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("c"));
//		assertThat(ite.next().getKey(), is("a"));
//		assertThat(ite.next().getKey(), is("f"));
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("b"));
//		assertThat(ite.hasNext(), is(false));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetBuilder_Case004(){
//		NavigableSet<Hoge> set = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			builder().
//			desc(HOGE.floatValue).
//			asc(HOGE.intValue).
//			ascKey().
//			asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("a").intValue(3).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("b").intValue(3).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("c").intValue(2).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("d").intValue(2).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("e").intValue(1).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("f").intValue(1).floatValue(0.7f).bean());
//		Iterator<Hoge> ite = set.iterator();
//		assertThat(ite.next().getKey(), is("f"));
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("b"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("c"));
//		assertThat(ite.next().getKey(), is("a"));
//		assertThat(ite.hasNext(), is(false));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetBuilder_Case005(){
//		NavigableSet<Hoge> set = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			builder().
//			desc(HOGE.floatValue).
//			asc(HOGE.intValue).
//			ascKey().
//			asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("a").intValue(3).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("b").intValue(3).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("c").intValue(2).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("d").intValue(2).floatValue(0.7f).bean());
//		set.add(HogeBuilder.create().key("e").intValue(1).floatValue(0.3f).bean());
//		set.add(HogeBuilder.create().key("f").intValue(1).floatValue(0.7f).bean());
//		Iterator<Hoge> ite = set.iterator();
//		assertThat(ite.next().getKey(), is("f"));
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("b"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("c"));
//		assertThat(ite.next().getKey(), is("a"));
//		assertThat(ite.hasNext(), is(false));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetScanner_Case001(){
//		NavigableSet<Hoge> set = 
//				BeanMetaCollections.
//				navigableSet(HOGE).
//				builder().
//				ascKey().
//				asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("a").intValue(3).bean());
//		set.add(HogeBuilder.create().key("b").intValue(2).bean());
//		set.add(HogeBuilder.create().key("c").intValue(1).bean());
//		set.add(HogeBuilder.create().key("d").intValue(3).bean());
//		set.add(HogeBuilder.create().key("e").intValue(2).bean());
//		set.add(HogeBuilder.create().key("f").intValue(1).bean());
//
//		Iterator<Hoge> ite = set.iterator();
//		assertThat(ite.next().getKey(), is("a"));
//		assertThat(ite.next().getKey(), is("b"));
//		assertThat(ite.next().getKey(), is("c"));
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("f"));
//		
//		Hoge a = HogeBuilder.create().key("a").intValue(3).bean();
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("f"));
//		assertThat(ite.hasNext(), is(false));
//	}
//	
//	@Test
//	public void testBeanMetaNavigableSetScanner_Case002(){
//		NavigableSet<Hoge> set = 
//				BeanMetaCollections.
//				navigableSet(HOGE).
//				builder().
//				ascKey().
//				asConcurrentSkipListSet();
//		set.add(HogeBuilder.create().key("a").intValue(3).bean());
//		set.add(HogeBuilder.create().key("b").intValue(2).bean());
//		set.add(HogeBuilder.create().key("c").intValue(1).bean());
//		set.add(HogeBuilder.create().key("d").intValue(3).bean());
//		set.add(HogeBuilder.create().key("e").intValue(2).bean());
//		set.add(HogeBuilder.create().key("f").intValue(1).bean());
//
//		Iterator<Hoge> ite = set.iterator();
//		assertThat(ite.next().getKey(), is("a"));
//		assertThat(ite.next().getKey(), is("b"));
//		assertThat(ite.next().getKey(), is("c"));
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("f"));
//		
//		NavigableSet<Hoge> scan = 
//			BeanMetaCollections.
//			navigableSet(HOGE).
//			scanner(set).
//			filter(HOGE.key).
//			tailSet("d", true);
//		ite = scan.iterator();
//		assertThat(ite.next().getKey(), is("d"));
//		assertThat(ite.next().getKey(), is("e"));
//		assertThat(ite.next().getKey(), is("f"));
//		assertThat(ite.hasNext(), is(false));
//	}
//	
//	static class BeanMetaNavigableSetScanner<BT, KT>{
//		private final BeanMeta<BT, KT> beanMeta;
//		private final List<BeanMetaNavigableSetFilterBuilder<BT, KT, ?>> filters;
//		private BeanMetaNavigableSetScanner(BeanMeta<BT, KT> beanMeta){
//			this.beanMeta = beanMeta;
//			this.filters = new ArrayList<BeanMetaNavigableSetFilterBuilder<BT,KT,?>>();
//		}
//		<PT> BeanMetaNavigableSetFilterBuilder<BT, KT, PT> filter(PropertyMeta<BT, PT> property){
//			return new BeanMetaNavigableSetFilterBuilder<BT, KT, PT>(property, this);
//		}
//		BT buildBean(){
//			BT bean = beanMeta.newInstance();
//			for (BeanMetaNavigableSetFilterBuilder<BT, KT, ?> builder : filters)
//				builder.setTo(bean);
//			return bean;
//		}
//	}
//	
//	public static class BeanMetaNavigableSetFilterBuilder<BT, KT, PT>{
//		private final PropertyMeta<BT, PT> propertyMeta;
//		private final BeanMetaNavigableSetScanner<BT, KT> parent;
//		private PT value;
//		BeanMetaNavigableSetFilterBuilder(PropertyMeta<BT, PT> propertyMeta, BeanMetaNavigableSetScanner<BT, KT> parent){
//			this.parent = parent;
//			this.propertyMeta = propertyMeta;
//		}
//		BeanMetaNavigableSetScanner<BT, KT> is(PT value){
//			this.value = value;
//			this.parent.filters.add(this);
//			return this.parent;
//		}
//		void setTo(BT bean){
//			this.propertyMeta.access(bean).set(value);
//		}
//	}
//	
// 	/**
//	 * <h1>Usage:</h1>
//	 * <pre>
//	 * 	NavigableSet<BT> set = 
//	 * 		BeanMetaCollections.
//	 * 		buildNavigableSetFor(PERSON).	// set BeanMeta
//	 * 		asc(PERSON.name).				// first sort
//	 * 		asc(PERSON.id).					// second sort
//	 * 		asConcurrentSkipListSet();		// create ConcurrentSkipListSet; 
//	 * </pre>
//	 * @author takayama
//	 * @param <BT>
//	 * @param <KT>
//	 */
//	public static class BeanMetaNavigableSetBuilder<BT,KT extends Comparable<KT>>{
//		private final BeanMetaComparatorBuilder<BT, KT> comparatorBuilder;
//		private final PropertyMeta<BT,KT> keyProperty;
//		BeanMetaNavigableSetBuilder(BeanMeta<BT, KT> beanMeta){
//			comparatorBuilder = BeanMetaUtils.get().comparatorBulder(beanMeta);
//			keyProperty = beanMeta.getKeyPropertyMeta();
//		}
//		NavigableSetFactory<BT> asTreeSet = new NavigableSetFactory<BT>(){
//			@Override
//			public NavigableSet<BT> create(Comparator<BT> comparator) {
//				return new TreeSet<BT>(comparator);
//			}
//		};
//		NavigableSetFactory<BT> asConcurrentSkipListSet = new NavigableSetFactory<BT>(){
//			@Override
//			public NavigableSet<BT> create(Comparator<BT> comparator) {
//				return new ConcurrentSkipListSet<BT>(comparator);
//			}
//		};
//		public <PT extends Comparable<PT>> BeanMetaNavigableSetBuilder<BT,KT> asc(PropertyMeta<BT, PT> property) {
//			comparatorBuilder.asc(property);
//			return this;
//		}
//		public <PT extends Comparable<PT>> BeanMetaNavigableSetBuilder<BT,KT> desc(PropertyMeta<BT, PT> property) {
//			comparatorBuilder.desc(property);
//			return this;
//		}
//		public <PT> BeanMetaNavigableSetBuilder<BT,KT> ascKey() {
//			comparatorBuilder.ascNullFirst(keyProperty);
//			return this;
//		}
//		public <PT> BeanMetaNavigableSetBuilder<BT,KT> descKey() {
//			comparatorBuilder.descNullFirst(keyProperty);
//			return this;
//		}
//		NavigableSet<BT> build(NavigableSetFactory<BT> factory){
//			return factory.create(comparatorBuilder.chain());
//		}
//		/**
//		 * build as TreeSet; 
//		 * (Non-Thread-Safe)
//		 * @return NavigableSet<BT>
//		 */
//		public NavigableSet<BT> asTreeSet(){
//			return build(asTreeSet);
//		}
//		/**
//		 * build as ConcurrentSkipListSet 
//		 * (Thread-Safe)
//		 * @return NavigableSet<BT>
//		 */
//			return build(asConcurrentSkipListSet);
//		}
//		static interface NavigableSetFactory<T> {
//			NavigableSet<T> create(Comparator<T> comparator);
//		}
//	}
//	public static class BeanMetaNavigableSetHelper<BT, KT extends Comparable<KT>>{
//		private final BeanMeta<BT, KT> beanMeta;
//		BeanMetaNavigableSetHelper(BeanMeta<BT, KT> beanMeta){
//			this.beanMeta = beanMeta;
//		}
//		public BeanMetaNavigableSetScanner<BT, KT> scanner(){
//			return new BeanMetaNavigableSetScanner<BT, KT>(this.beanMeta);
//		}
//		public BeanMetaNavigableSetBuilder<BT,KT> builder(){
//			return new BeanMetaNavigableSetBuilder<BT,KT>(this.beanMeta);
//		}
//	}
//	public static class BeanMetaCollections{
//		public static <BT,KT extends Comparable<KT>> BeanMetaNavigableSetHelper<BT, KT> navigableSet(BeanMeta<BT, KT> beanMeta){
//			return new BeanMetaNavigableSetHelper<BT, KT>(beanMeta);
//		}
//	}
//
//	/**
//	 * 
//	 * @author takayama
//	 * @param <BT>
//	 * @param <PT>
//	 * @param <KT>
//	 */
//	static class PropertyMetaNavigableSetFilter<BT,PT,KT>{
//		private final PropertyMeta<BT, PT> propertyMeta;
//		private final NavigableSet<BT> navigableSet;
//		private final BeanMeta<BT,KT> beanMeta;
//		private PropertyMetaNavigableSetFilter(PropertyMeta<BT, PT> propertyMeta, BeanMetaNavigableSetScanner<BT,KT> parent) {
//			this.propertyMeta = propertyMeta;
//			this.beanMeta = parent.beanMeta;
//			this.navigableSet = parent.navigableSet;
//		}
//		private BT createFor(PT value){
//			BT arg = this.beanMeta.newInstance();
//			propertyMeta.access(arg).set(value);
//			return arg;
//		}
//		BT lower(PT value){ 
//			return navigableSet.lower(createFor(value));
//		}
//		BT higher(PT value){ 
//			return navigableSet.higher(createFor(value));
//		}
//		BT floor(PT value){ 
//			return navigableSet.floor(createFor(value));
//		}
//		BT ceiling(PT value){
//			return navigableSet.ceiling(createFor(value));
//		}
//		NavigableSet<BT> subSet(PT from, boolean fromInclusive, PT to, boolean toInclusive){
//			return navigableSet.subSet(createFor(from), fromInclusive, createFor(to), toInclusive);
//		}
//		NavigableSet<BT> headSet(PT to, boolean inclusive){
//	    	return navigableSet.headSet(createFor(to), inclusive);
//	    }
//		NavigableSet<BT> tailSet(PT to, boolean inclusive){
//	    	return navigableSet.tailSet(createFor(to), inclusive);
//	    }
//	}
}