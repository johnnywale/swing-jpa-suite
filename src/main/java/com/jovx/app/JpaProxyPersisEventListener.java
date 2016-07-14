package com.jovx.app;

import com.jovx.xswing.event.ModelAddEvent;
import com.jovx.xswing.event.ModelDeleteEvent;
import com.jovx.xswing.event.ModelFieldValueChangedEvent;
import com.jovx.xswing.event.ModelFullUpdateEvent;
import com.jovx.xswing.persis.PersisEventListener;

public class JpaProxyPersisEventListener implements PersisEventListener {

	@Override
	public <T> void onModelAddEvent(ModelAddEvent<T> modelAddEvent) {
		getJpaPersis().onModelAddEvent(modelAddEvent);
	}

	private JpaPersis getJpaPersis() {

		return ApplicationContentHolder.applicationContext
				.getBean(JpaPersis.class);
	}

	@Override
	public <T> void onModelDeleteEvent(ModelDeleteEvent<T> modelDeleteEvent) {
		getJpaPersis().onModelDeleteEvent(modelDeleteEvent);

	}

	@Override
	public <T> void onModelFieldValueChangedEvent(
			ModelFieldValueChangedEvent<T> modelFieldValueChangedEvent) {
		getJpaPersis().onModelFieldValueChangedEvent(
				modelFieldValueChangedEvent);

	}

	@Override
	public <T> void onModelFullUpdateEvent(ModelFullUpdateEvent<T> o) {
		getJpaPersis().onModelFullUpdateEvent(o);

	}

}
