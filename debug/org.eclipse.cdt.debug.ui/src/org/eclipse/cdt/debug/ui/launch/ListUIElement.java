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
import org.eclipse.cdt.debug.core.launch.IListLaunchElement;
import org.eclipse.cdt.debug.internal.ui.CDebugImages;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.cdt.ui.grid.CompositePresentationModel;
import org.eclipse.cdt.ui.grid.GridElement;
import org.eclipse.cdt.ui.grid.IPresentationModel;
import org.eclipse.cdt.ui.grid.StringPresentationModel;
import org.eclipse.cdt.ui.grid.ViewElement;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * @since 7.4
 */
public abstract class ListUIElement extends ViewElement {

	final static public int SHOW_REMOVE_BUTTON = 0x1;
	final static public int SHOW_UP_BUTTON = 0x2;
	final static public int SHOW_DOWN_BUTTON = 0x4;
	private IListLaunchElement launchElement;
		
	public IListLaunchElement getLaunchElement() {
		return launchElement;
	}

	public ListUIElement(IListLaunchElement listElement) {
		super(new CompositePresentationModel("Executables"));
		this.launchElement = listElement;
		
		final int length = listElement.getChildren().length;	

		for (int i = 0; i < length; ++i) {
										
			ILaunchElement child = listElement.getChildren()[i];
			int showButtons = 0;
			if (i+1 < length) {
				showButtons |= SHOW_DOWN_BUTTON;
			}
			if (i > 0) {
				showButtons |= SHOW_UP_BUTTON;
			}
			if (child.canRemove() && listElement.getLowerLimit() < length) {
				showButtons |= SHOW_REMOVE_BUTTON;
			}
			addChild(createListElementContent(child, showButtons));
		}
	}
	
	@Override
	public void createImmediateContent(Composite parent) {
		// TODO Auto-generated method stub
			
		Label title = new Label(parent, SWT.NONE);
		title.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		title.setText(getModel().getName());
	}
		
	@Override
	public void adjustChildren(Composite parent) {
		getChildElements().get(0).getChildControls().get(0).dispose();
	}

	protected GridElement createListElementContent(final ILaunchElement element, final int flags) {
		
		final StringPresentationModel m = new StringPresentationModel(getLinkLabel(element)) {
			public void activate() {
				notifyListeners(IPresentationModel.ACTIVATED, element.getId());
			};
		};
		((CompositePresentationModel)getModel()).add(m);
		
		
		return new GridElement() {
			@Override
			public void createImmediateContent(Composite parent) {
				new Label(parent, SWT.NONE);
				new Label(parent, SWT.NONE);
				
				Link link = new Link(parent, SWT.NONE);
				link.setText(String.format("<a>%s</a>", getLinkLabel(element))); //$NON-NLS-1$
				link.setToolTipText(getLinkDescription(element));
				link.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				CDTUITools.getGridLayoutData(link).horizontalSpan = 2;
				link.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						m.activate();
					}
				});
			
				Composite buttonsComp = new Composite(parent, SWT.NONE);
				boolean showUpButton = (flags & SHOW_UP_BUTTON) != 0;
				boolean showDownButton = (flags & SHOW_DOWN_BUTTON) != 0;
				boolean showRemoveButton = (flags & SHOW_REMOVE_BUTTON) != 0;
				int columns = 0;
				if (showUpButton) {
					++columns;
				}
				if (showDownButton) {
					++columns;
				}
				if (showRemoveButton) {
					++columns;
				}
				GridLayout layout = new GridLayout(columns, true);
				layout.marginHeight = layout.marginWidth = 0;
				buttonsComp.setLayout(layout);
				buttonsComp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
				if (showUpButton) {
					Button button = createButton(buttonsComp, CDebugImages.get(CDebugImages.IMG_LCL_UP_UIELEMENT), "Up", 1, 1);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							upButtonPressed(element);
						}
					});
				}
				if (showDownButton) {
					Button button = createButton(buttonsComp, CDebugImages.get(CDebugImages.IMG_LCL_DOWN_UIELEMENT), "Down", 1, 1);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							downButtonPressed(element);
						}
					});
				}
				if (showRemoveButton) {
					Button button = createButton(buttonsComp, CDebugImages.get(CDebugImages.IMG_LCL_REMOVE_UIELEMENT), "Delete", 1, 1);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							removeButtonPressed(element);
						}
					});
				}				
				
			}
		};
	}
	
	protected String getLinkLabel(ILaunchElement element) {
		return element.getName();
	}
		
	protected String getLinkDescription(ILaunchElement element) {
		return element.getDescription();
	}
	
	protected Button createButton(Composite parent, Image image, String tooltip, int horSpan, int verSpan) {
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, horSpan, verSpan));
		button.setImage(image);
		button.setToolTipText(tooltip);
		return button;
	}

	abstract protected void createButtonBar(Composite parent);

	protected void upButtonPressed(ILaunchElement element) {
		getLaunchElement().moveElementUp(element);
	}
	
	protected void downButtonPressed(ILaunchElement element) {
		getLaunchElement().moveElementDown(element);
	}
	
	protected void removeButtonPressed(ILaunchElement element) {
		getLaunchElement().removeElement(element);;
	}
}
