/**********************************************************************
 * Copyright (c) 2002-2004 IBM Canada and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v0.5
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors: 
 * IBM Rational Software - Initial API and implementation */
package org.eclipse.cdt.internal.core.parser2.c;

/**
 * @author jcamelon
 */
public interface ICParserExtensionConfiguration {

    public boolean supportStatementsInExpressions();
    public boolean supportGCCStyleDesignators();
	public boolean supportTypeofUnaryExpressions();
	public boolean supportAlignOfUnaryExpression();

    
}
