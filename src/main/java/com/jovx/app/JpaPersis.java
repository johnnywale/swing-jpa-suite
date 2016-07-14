package com.jovx.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jovx.app.convert.BeanConvert;
import com.jovx.app.convert.DtoMapper;
import com.jovx.jpa.JpaAccessHelper;
import com.jovx.xswing.event.ModelAddEvent;
import com.jovx.xswing.event.ModelDeleteEvent;
import com.jovx.xswing.event.ModelFieldValueChangedEvent;
import com.jovx.xswing.event.ModelFullUpdateEvent;
import com.jovx.xswing.persis.PersisEventListener;

@Component
@Transactional
public class JpaPersis implements PersisEventListener {
	@Autowired
	private JpaAccessHelper persisBean;
	@Autowired(required = false)
	private DtoMapper dtoMapper;

	@Override
	public <T> void onModelAddEvent(ModelAddEvent<T> modelAddEvent) {
		Class<T> clazz = modelAddEvent.getInstanceClass();

		synchronized (clazz) {
			List<T> all = modelAddEvent.getChangedList();
			if (dtoMapper != null) {
				Class target = dtoMapper.getTarget(clazz);
				if (target != null) {
					all = BeanConvert.mapList(all, target);
					clazz = target;
				}
			}
			for (Object o : all) {
				persisBean.persist(o);
				persisBean.flush();
			}
		}

	}

	@Override
	public <T> void onModelDeleteEvent(ModelDeleteEvent<T> modelDeleteEvent) {
		List<T> all = modelDeleteEvent.getChangedList();
		Class<T> clazz = modelDeleteEvent.getInstanceClass();

		Class target = dtoMapper.getTarget(clazz);
		if (target != null) {
			all = BeanConvert.mapList(all, target);
			clazz = target;
		}
		persisBean.deleteAll(all);

	}

	@Override
	public <T> void onModelFieldValueChangedEvent(
			ModelFieldValueChangedEvent<T> modelFieldValueChangedEvent) {
		Object o = modelFieldValueChangedEvent.getModel();
		Class<T> clazz = modelFieldValueChangedEvent.getInstanceClass();

		Class target = dtoMapper.getTarget(clazz);
		if (target != null) {
			o = BeanConvert.map(o, target);
			clazz = target;
		}

		persisBean.modify(o);
	}

	@Override
	public <T> void onModelFullUpdateEvent(
			ModelFullUpdateEvent<T> modelFieldValueChangedEvent) {
		List<T> obs = modelFieldValueChangedEvent.getChangedList();
		Class<T> clazz = modelFieldValueChangedEvent.getInstanceClass();
		for (Object o : obs) {
			Class target = null;
			if (dtoMapper != null) {
				target = dtoMapper.getTarget(clazz);

			}

			if (target != null) {
				o = BeanConvert.map(o, target);
				clazz = target;
			}

			persisBean.modify(o);

		}

	}

}
