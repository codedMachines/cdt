/**********************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.cdt.core.dom.ast;

/**
 * Represents a label statement.
 * 
 * @author Doug Schaefer
 */
public interface IASTLabelStatement extends IASTStatement {

	public static final ASTNodeProperty NAME = new ASTNodeProperty("name");  //$NON-NLS-1$

	/**
	 * The name for the label. The name resolves to an ILabel binding.
	 * 
	 * @return the name for the label
	 */
	public IASTName getName();

	public void setName(IASTName name);
	
}
