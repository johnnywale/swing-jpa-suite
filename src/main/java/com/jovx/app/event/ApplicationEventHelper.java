package com.jovx.app.event;

import java.util.List;

import com.jovx.app.Application;
import com.jovx.xswing.event.AppEvent;
import com.jovx.xswing.event.IEventService;
import com.jovx.xswing.factory.XSwingFactory;

public class ApplicationEventHelper {
	public static IEventService eventService = XSwingFactory.getInstance()
			.findEventService(Application.CHANNEL_APP);

	public static void fireAppEvent(AppEvent<?> appStatusChange) {
		eventService.fireEvent(appStatusChange);

		List<String> strings = appStatusChange.getChannels();
		if (strings.size() > 0) {
			for (String x : strings) {
				eventService.fireEvent(x);
			}

		}
	}

}
