package com.jovx.app.convert;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
	private HashMap<Class, Class> convert = new HashMap();

	public HashMap<Class, Class> getConvert() {
		return convert;
	}

	public void setConvert(HashMap<Class, Class> convert) {
		this.convert = convert;
	}

	public Class getTarget(Class clazz1) {
		return convert.get(clazz1);
	}

	public void registerConvert(Class clazz1, Class clazz2) {
		convert.put(clazz1, clazz2);
	}
}
