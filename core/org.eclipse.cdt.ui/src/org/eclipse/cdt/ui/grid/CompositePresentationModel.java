package org.eclipse.cdt.ui.grid;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 5.7
 */
public class CompositePresentationModel extends PresentationModel implements ICompositePresentationModel {
	
	public CompositePresentationModel()
	{
		super("");
	}
	
	public CompositePresentationModel(String name)
	{
		super(name);
	}
	
	public void add(IPresentationModel child)
	{
		children.add(child);
		child.addAndCallListener(new IPresentationModel.Listener() {
			
			@Override
			public void changed(int what, Object object) {
				if ((what & IPresentationModel.ACTIVATED) != 0)
					notifyListeners(what, object);
							
				// TODO: maybe, need to do something sensible for other whats				
			}
		});
	}
	
	@Override
	public void setVisible(boolean v) {
		for (IPresentationModel child: children) {
			if (child instanceof PresentationModel) {
				((PresentationModel)child).setVisible(v);
			}
		}
		super.setVisible(v);
	}

	public List<IPresentationModel> getChildren() {
		return children;
	}
	
	public String name;
	public List<IPresentationModel> children = new ArrayList<IPresentationModel>();

}
