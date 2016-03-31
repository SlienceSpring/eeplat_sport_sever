package com.exedosoft.plat.ui.customize.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOViewTemplate;

/**
 * Pane和Service的调用分开
 * @author aa
 * 
 */
public class TWfDesigner extends DOViewTemplate {

	private static Log log = LogFactory.getLog(TWfDesigner.class);
	
	public TWfDesigner(){
		
		dealTemplatePath(  "/customize/tools/TWfDesigner.ftl");
	}
	


}
