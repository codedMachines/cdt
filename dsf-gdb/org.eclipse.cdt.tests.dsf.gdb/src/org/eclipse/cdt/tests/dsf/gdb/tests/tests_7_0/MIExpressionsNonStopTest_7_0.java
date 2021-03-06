/*******************************************************************************
 * Copyright (c) 2013 Ericsson and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Marc Khouzam (Ericsson) - Initial Implementation
 *******************************************************************************/
package org.eclipse.cdt.tests.dsf.gdb.tests.tests_7_0;

import org.eclipse.cdt.dsf.gdb.IGDBLaunchConfigurationConstants;
import org.eclipse.cdt.tests.dsf.gdb.framework.BackgroundRunner;
import org.eclipse.cdt.tests.dsf.gdb.tests.ITestConstants;
import org.junit.runner.RunWith;

@RunWith(BackgroundRunner.class)
public class MIExpressionsNonStopTest_7_0 extends MIExpressionsTest_7_0 {
	@Override
	protected void setGdbVersion() {
		setGdbProgramNamesLaunchAttributes(ITestConstants.SUFFIX_GDB_7_0);
	}
	
	@Override
	protected void setLaunchAttributes() {
		super.setLaunchAttributes();
		
    	setLaunchAttribute(IGDBLaunchConfigurationConstants.ATTR_DEBUGGER_NON_STOP, true);
	}
}
