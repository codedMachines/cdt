/*******************************************************************************
 * Copyright (c) 2014 Mentor Graphics and others.
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
import org.eclipse.cdt.dsf.gdb.IGdbDebugPreferenceConstants;
import org.eclipse.cdt.dsf.gdb.internal.GdbPlugin;
import org.eclipse.cdt.dsf.gdb.newlaunch.OverviewElement.SessionTypeChangeEvent;
import org.eclipse.cdt.dsf.gdb.service.SessionType;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

/**
 * @since 4.3
 */
public class StopModeElement extends AbstractLaunchElement {

	final private static String ELEMENT_ID = ".stopMode"; //$NON-NLS-1$
	final private static String ATTR_NON_STOP = ".nonStop"; //$NON-NLS-1$

	private boolean fNonStop = Platform.getPreferencesService().getBoolean(
		GdbPlugin.PLUGIN_ID, IGdbDebugPreferenceConstants.PREF_DEFAULT_NON_STOP, false, null);

	public StopModeElement(ILaunchElement parent) {
		super(parent, parent.getId() + ELEMENT_ID, "Stop Mode", "Stop mode");
	}

	@Override
	protected void doCreateChildren(Map<String, Object> attributes) {
	}

	@Override
	protected void doInitializeFrom(Map<String, Object> attributes) {
		fNonStop = getAttribute(attributes, getId() + ATTR_NON_STOP, fNonStop);
	}

	@Override
	protected void doPerformApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(getId() + ATTR_NON_STOP, fNonStop);
	}

	@Override
	protected void doSetDefaults(ILaunchConfigurationWorkingCopy config) {
		fNonStop = Platform.getPreferencesService().getBoolean(
				GdbPlugin.PLUGIN_ID, IGdbDebugPreferenceConstants.PREF_DEFAULT_NON_STOP, false, null);
			config.setAttribute(getId() + ATTR_NON_STOP, fNonStop);
	}

	@Override
	protected boolean isContentValid() {
		return true;
	}

	public boolean isNonStop() {
		return fNonStop;
	}

	public void setNonStop(boolean nonStop) {
		if (fNonStop == nonStop)
			return;
		fNonStop = nonStop;
		elementChanged(CHANGE_DETAIL_STATE);
	}

	@Override
	public void update(IChangeEvent event) {
		if (event instanceof SessionTypeChangeEvent) {
			handleSessionTypeChange((SessionTypeChangeEvent)event);
		}
		super.update(event);
	}

	private void handleSessionTypeChange(SessionTypeChangeEvent event) {
		 setEnabled(!SessionType.CORE.equals(event.getNewType()));
	}
}
