package com.jovx.app.spring;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;

import com.jovx.app.ApplicationContentHolder;
import com.jovx.jpa.DoWithEntityManager;
import com.jovx.jpa.JpaAccessHelper;

public class DataQuery {

	public static JpaAccessHelper getJpaPersisHelper() {
		JpaAccessHelper jpaPersisHelper = ApplicationContentHolder.applicationContext
				.getBean(JpaAccessHelper.class);
		return jpaPersisHelper;
	}

	public void doCallBack(DoWithEntityManager doWithEntityManager) {
		getJpaPersisHelper().doCallBack(doWithEntityManager);
	}

	public static <T> List<T> getAll(Class<T> clazz) {
		return getJpaPersisHelper().loadAllByClass(clazz);
	}

	public static <T> T getSingle(Class<T> clazz) {
		return DataAccessUtils.singleResult(getJpaPersisHelper()
				.loadAllByClass(clazz));
	}

}
