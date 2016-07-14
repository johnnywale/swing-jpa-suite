package com.jovx.app.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

public class BeanConvert {

	private static DozerBeanMapper dozer = new DozerBeanMapper();

	public static <T> T map(Object source, Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	} 

	public static <T> List<T> mapList(Collection sourceList,
			Class<T> destinationClass) {
		List<T> destinationList = new ArrayList<T>();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	public static void copy(Object source, Object destinationObject) {
		dozer.map(source, destinationObject);
	}

	public static void copy(final Object source,
			final Object destinationObject, final String... ignores) {
		BeanMappingBuilder builder = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				TypeMappingBuilder x = mapping(source.getClass(),
						destinationObject.getClass(),
						TypeMappingOptions.oneWay());
				for (String ig : ignores) {
					x.exclude(ig);
				}
			}

		};
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(builder);
		mapper.map(source, destinationObject);

	}

}
