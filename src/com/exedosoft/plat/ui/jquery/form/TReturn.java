package com.exedosoft.plat.ui.jquery.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOViewTemplate;

/**
 * Pane和Service的调用分开
 * @author aa
 * 
 */
public class TReturn extends DOViewTemplate {

	private static Log log = LogFactory.getLog(TReturn.class);
	
	public TReturn(){
		dealTemplatePath( "/form/TReturn.ftl");
	}
	


}
