/*
 * Copyright (c) 2013 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.cdt.qt.core.index;

/**
 * Represents a QMake environment. It is usually created by IQMakeEnvProvider.createEnv() method for a specific project configuration.
 *
 * Note that IQMakeEnv has destroy method only and it is expected that the instance is already initialized in the constructor.
 * This means that it may happen that IQMakeEnv instance is created and destroyed immediately.
 *
 * See IQMakeEnv2 interface if you want to get an explicit notification when IQMakeEnv gets really used. In that case, the instance initialization
 * needs to be done in init method completely - not in the constructor.
 */
public interface IQMakeEnv {

	/**
	 * Notifies that this environment is no longer used.
	 * This method should not use any workspace-lock or sync-locks that might call QMake-related structures.
	 */
	void destroy();

	/**
	 * Returns an actual QMake environment information that is used for a single qmake run to retrieve QMake information.
	 *
	 * @return the actual QMake environment information
	 */
	QMakeEnvInfo getQMakeEnvInfo();

}
