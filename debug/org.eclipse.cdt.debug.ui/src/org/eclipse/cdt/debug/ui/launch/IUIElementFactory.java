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

package org.eclipse.cdt.debug.ui.launch;

import org.eclipse.cdt.debug.core.launch.ILaunchElement;
import org.eclipse.cdt.ui.grid.GridElement;
import org.eclipse.cdt.ui.grid.IPresentationModel;

/**
 * @since 7.4
 */
public interface IUIElementFactory {

	GridElement createUIElement2(ILaunchElement l, boolean b);
	
	// FIXME: temporary hack, remove.
	IPresentationModel createPresentationModel(ILaunchElement element);
}
