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

import org.eclipse.cdt.debug.ui.dialogs.GridUtils;
import org.eclipse.cdt.debug.ui.launch.AbstractUIElement;
import org.eclipse.cdt.dsf.gdb.newlaunch.ArgumentsElement;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ArgumentsUIElement extends AbstractUIElement {

	private Text fArgsText;

	public ArgumentsUIElement(ArgumentsElement launchElement, boolean showDetails) {
		// always in "show details" mode
		super(launchElement, true);
	}

	@Override
	public ArgumentsElement getLaunchElement() {
		return (ArgumentsElement)super.getLaunchElement();
	}

	@Override
	public void disposeContent() {
		super.disposeContent();
		fArgsText = null;
	}

	@Override
	protected void doCreateDetailsContent(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		GridUtils.fillIntoGrid(label, parent);
		label.setText("Arguments: ");
		
		fArgsText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		fArgsText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		fArgsText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				getLaunchElement().setArguments(fArgsText.getText().trim());
			}
		});
	}

	@Override
	protected void initializeDetailsContent() {
		if (fArgsText != null) {
			fArgsText.setText(getLaunchElement().getArguments());
		}
	}
}