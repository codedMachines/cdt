/*******************************************************************************
 * Copyright (c) 2009 Alena Laskavaia 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alena Laskavaia  - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.codan.internal.checkers;

import java.util.regex.Pattern;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.codan.core.model.ICheckerWithParameters;
import org.eclipse.cdt.codan.core.model.IProblem;
import org.eclipse.cdt.codan.core.model.IProblemParameterInfo;
import org.eclipse.cdt.codan.core.model.IProblemWorkingCopy;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

/**
 * @author Alena
 * 
 */
public class NamingConventionFunctionChecker extends AbstractIndexAstChecker
		implements ICheckerWithParameters {
	private static final String DEFAULT_PATTERN = "^[a-z]"; // name starts with english lowercase letter //$NON-NLS-1$
	public static final String PARAM_KEY = "pattern"; //$NON-NLS-1$
	private static final String ER_ID = "org.eclipse.cdt.codan.internal.checkers.NamingConventionFunctionChecker"; //$NON-NLS-1$

	public void processAst(IASTTranslationUnit ast) {
		final IProblem pt = getProblemById(ER_ID, getFile());
		try {
			ast.accept(new ASTVisitor() {
				{
					shouldVisitDeclarations = true;
				}

				public int visit(IASTDeclaration element) {
					if (element instanceof IASTFunctionDefinition) {
						String parameter = (String) pt.getParameter(PARAM_KEY);
						Pattern pattern = Pattern.compile(parameter);
						String name = ((IASTFunctionDefinition) element)
								.getDeclarator().getName().toString();
						if (!pattern.matcher(name).find()) {
							reportProblem(ER_ID, getFile(), 1, // TODO: line
																// number
									name, parameter);
						}
					}
					return PROCESS_SKIP;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.codan.core.model.ICheckerWithParameters#initParameters
	 * (org.eclipse.cdt.codan.core.model.IProblemWorkingCopy)
	 */
	public void initParameters(IProblemWorkingCopy problem) {
		IProblemParameterInfo info = new IProblemParameterInfo() {
			public String getUiInfo() {
				return null;
			}

			public String getType() {
				return IProblemParameterInfo.TYPE_STRING;
			}

			public String getLabel() {
				return "Name Pattern";
			}

			public String getKey() {
				return PARAM_KEY;
			}

			public IProblemParameterInfo getElement(String key) {
				return null;
			}
		};
		problem.setParameterInfo(info);
		problem.setParameter(PARAM_KEY, DEFAULT_PATTERN);
	}

	@Override
	public boolean runInEditor() {
		return true;
	}

}
