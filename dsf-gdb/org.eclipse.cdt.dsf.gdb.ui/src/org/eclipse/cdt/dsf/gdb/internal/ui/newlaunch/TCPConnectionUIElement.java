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

package org.eclipse.cdt.dsf.gdb.internal.ui.newlaunch;

import org.eclipse.cdt.ui.grid.IStringPresentationModel;
import org.eclipse.cdt.ui.grid.StringViewElement;
import org.eclipse.cdt.ui.grid.ViewElement;
import org.eclipse.swt.widgets.Text;

public class TCPConnectionUIElement extends ViewElement {

	private Text fHostNameText;
	private Text fPortText;
	
	public TCPConnectionUIElement(TcpConnectionPresentationModel model) {
		super(model);
		
		// FIXME: this is highly fragile. Need to go via factory.
		addChild(new StringViewElement((IStringPresentationModel)model.getChildren().get(0)));
		addChild(new StringViewElement((IStringPresentationModel)model.getChildren().get(1)));
	}
}
