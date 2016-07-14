package com.jovx.app.mbean;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistration;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.commons.modeler.Registry;

import com.jovx.app.BaseComponentModule;

public class BaseModuleMBean implements MBeanRegistration {
	private String domain = null;
	private ObjectName oname = null;
	private MBeanServer mserver = null;
	private BaseComponentModule baseComponentModule;

	public BaseModuleMBean(BaseComponentModule baseComponentModule) {
		this.baseComponentModule = baseComponentModule;
	}

	/**
	 * @return the mserver
	 */
	public MBeanServer getMserver() {
		return mserver;
	}

	@Override
	public ObjectName preRegister(MBeanServer server, ObjectName name)
			throws Exception {
		this.mserver = server;
		this.oname = name;
		this.domain = name.getDomain();

		return oname;
	}

	public void initInternal() {

		if (oname == null) {
			mserver = Registry.getRegistry(null, null).getMBeanServer();

			oname = register(this,
					baseComponentModule.getObjectNameKeyProperties());
		}
	}

	protected final ObjectName register(Object obj,
			String objectNameKeyProperties) {

		// Construct an object name with the right domain
		StringBuilder name = new StringBuilder(baseComponentModule.getDomain());
		name.append(':');
		name.append(objectNameKeyProperties);

		ObjectName on = null;

		try {
			on = new ObjectName(name.toString());

			Registry.getRegistry(null, null).registerComponent(obj, on, null);
		} catch (MalformedObjectNameException e) {
			// log.warn(
			// sm.getString("lifecycleMBeanBase.registerFail", obj, name),
			// e);
		} catch (Exception e) {
			// log.warn(
			// sm.getString("lifecycleMBeanBase.registerFail", obj, name),
			// e);
		}

		return on;
	}

	protected final void unregister(ObjectName on) {

		// If null ObjectName, just return without complaint
		if (on == null) {
			return;
		}

		// If the MBeanServer is null, log a warning & return
		if (mserver == null) {

			return;
		}

		try {
			mserver.unregisterMBean(on);
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void postRegister(Boolean registrationDone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preDeregister() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postDeregister() {
		// TODO Auto-generated method stub

	}
}
