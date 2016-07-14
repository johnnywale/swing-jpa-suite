package com.jovx.app;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class LogInstall {
	public static void installLog() {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
	}
}
