package org.eclipse.cdt.ui.grid;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.cdt.ui.grid.IPresentationModel.Listener;

public class StringViewElement implements IGridElement {

	public StringViewElement(IStringPresentationModel model) {
		this.model = model;
	}

	@Override
	public void fillIntoGrid(Composite parent) {
		
		Label l = new Label(parent, SWT.NONE);
		l.setText(model.getName());
		
		new Label(parent, SWT.NONE);
		
		final Text t = new Text(parent, SWT.BORDER);
		t.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (!blockSignals)				
					model.setValue(t.getText());
			}
		});
		
		model.addAndCallListener(new Listener() {
			@Override
			public void changed(int what, Object object) {
				if ((what | IPresentationModel.CHANGED) != 0) {
					blockSignals = true;
					t.setText(model.getValue());
					blockSignals = false;
				}
			}
		});
		
		CDTUITools.getGridLayoutData(t).horizontalSpan = 2;
		CDTUITools.grabAllWidth(t);
		
		new Label(parent, SWT.NONE);
	}
	
	private IStringPresentationModel model;
	private boolean blockSignals; 
}
