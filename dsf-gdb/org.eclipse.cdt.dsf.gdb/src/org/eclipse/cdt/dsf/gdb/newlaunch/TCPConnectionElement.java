/*******************************************************************************
 * Copyright (c) 2013 Mentor Graphics and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mentor Graphics - Initial API and implementation
 *******************************************************************************/

package org.eclipse.cdt.dsf.gdb.newlaunch;

import java.util.Map;

import org.eclipse.cdt.debug.core.launch.AbstractLaunchElement;
import org.eclipse.cdt.debug.core.launch.ILaunchElement;
import org.eclipse.cdt.dsf.gdb.newlaunch.ConnectionElement.ConnectionType;
import org.eclipse.cdt.dsf.gdb.newlaunch.ConnectionElement.ConnectionTypeChangeEvent;

/**
 * @since 4.3
 */
public class TCPConnectionElement extends AbstractLaunchElement {

	final private static String ELEMENT_ID = ".tcp"; //$NON-NLS-1$
	final private static String ATTR_HOST_NAME = ".hostName"; //$NON-NLS-1$
	final private static String ATTR_PORT_NUMBER = ".portNumber"; //$NON-NLS-1$

	final private static String DEFAULT_HOST_NAME = "localhost"; //$NON-NLS-1$
	final private static String DEFAULT_PORT_NUMBER = "10000"; //$NON-NLS-1$

	private String fHostName = DEFAULT_HOST_NAME;
	private String fPortNumber = DEFAULT_PORT_NUMBER;

	public TCPConnectionElement(ILaunchElement parent) {
		super(parent, parent.getId() + ELEMENT_ID, "TCP", "TCP Connection");
	}

	@Override
	protected void doCreateChildren(Map<String, Object> attributes) {
	}

	@Override
	protected void doInitializeFrom(Map<String, Object> attributes) {
		fHostName = getAttribute(attributes, getId() + ATTR_HOST_NAME, DEFAULT_HOST_NAME);
		fPortNumber = getAttribute(attributes, getId() + ATTR_PORT_NUMBER, DEFAULT_PORT_NUMBER);
	}

	@Override
	protected void doPerformApply(Map<String, Object> attributes) {
		attributes.put(getId() + ATTR_HOST_NAME, fHostName);
		attributes.put(getId() + ATTR_PORT_NUMBER, fPortNumber);
	}

	@Override
	protected void doSetDefaults(Map<String, Object> attributes) {
		fHostName = DEFAULT_HOST_NAME;
		fPortNumber = DEFAULT_PORT_NUMBER;
		attributes.put(getId() + ATTR_HOST_NAME, DEFAULT_HOST_NAME);
		attributes.put(getId() + ATTR_PORT_NUMBER, DEFAULT_PORT_NUMBER);
	}

	@Override
	protected boolean isContentValid() {
		setErrorMessage(null);
		if (fHostName.isEmpty()) {
			setErrorMessage("Host name or IP address must be specified.");
		}
		else if (!hostNameIsValid(fHostName)) {
			setErrorMessage("Invalid host name or IP address.");
		}
		else if (fPortNumber.isEmpty()) {
			setErrorMessage("Port number must be specified.");
			return false;
		}
		else if (!portNumberIsValid(fPortNumber)) {
			setErrorMessage("Invalid port number.");
		}
		return super.getInternalErrorMessage() == null;
	}

	protected boolean hostNameIsValid(String hostName) {
		return true;
	}

	protected boolean portNumberIsValid(String portNumber) {
		try {
			int port = Integer.parseInt(portNumber);
			return (port > 0 && port <= 0xFFFF);
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

	public static String getDefaultHostName() {
		return DEFAULT_HOST_NAME;
	}

	public static String getDefaultPortNumber() {
		return DEFAULT_PORT_NUMBER;
	}

	public String getHostName() {
		return fHostName;
	}

	public void setHostName(String hostName) {
		if (fHostName.equals(hostName))
			return;
		fHostName = hostName;
		elementChanged(CHANGE_DETAIL_STATE);
	}

	public String getPortNumber() {
		return fPortNumber;
	}

	public void setPortNumber(String portNumber) {
		if (fPortNumber.equals(portNumber))
			return;
		fPortNumber = portNumber;
		elementChanged(CHANGE_DETAIL_STATE);
	}

	@Override
	public void update(IChangeEvent event) {
		if (event instanceof ConnectionTypeChangeEvent) {
			handleConnectionTypeChange((ConnectionTypeChangeEvent)event);
		}
		super.update(event);
	}
	
	private void handleConnectionTypeChange(ConnectionTypeChangeEvent event) {
		 setEnabled(ConnectionType.TCP.equals(event.getNewType()));
	}
}