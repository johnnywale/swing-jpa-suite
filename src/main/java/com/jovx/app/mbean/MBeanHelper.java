package com.jovx.app.mbean;

import javax.management.ObjectName;

import org.apache.commons.modeler.Registry;

import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class MBeanHelper {
	public static void startMBeanHttpService() {
		HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();
		htmlAdaptorServer.addUserAuthenticationInfo(new AuthInfo("admin",
				"admin"));
		htmlAdaptorServer.start();
		ObjectName adapterName;
		try {
			adapterName = new ObjectName(
					"SimulatorAgent:name=htmladapter,port=8082");
			Registry.getRegistry(null, null).getMBeanServer()
					.registerMBean(htmlAdaptorServer, adapterName);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
