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

/**
 * @since 4.3
 */
public class DebuggerSettingsElement extends AbstractLaunchElement {

	final private static String ELEMENT_ID = ".debuggerSettings"; //$NON-NLS-1$

	public DebuggerSettingsElement(ILaunchElement parent) {
		super(parent, parent.getId() + ELEMENT_ID, "Advanced Settings", "Debugger advanced settings");
	}

	@Override
	protected void doCreateChildren(Map<String, Object> attributes) {
		addChildren(new ILaunchElement[] {
		});
	}

	@Override
	protected void doInitializeFrom(Map<String, Object> attributes) {
	}

	@Override
	protected void doPerformApply(Map<String, Object> attributes) {
	}

	@Override
	protected void doSetDefaults(Map<String, Object> attributes) {
	}

	@Override
	protected boolean isContentValid() {
		return true;
	}
}
