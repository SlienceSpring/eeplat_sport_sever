package com.exedosoft.plat.ui.weui.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.SessionContext;
import com.exedosoft.plat.ui.DOViewTemplate;

/**
 * 
 * Pane和Service的调用分开
 * @author aa
 */
public class TServiceUfConfirm extends DOViewTemplate {

	private static Log log = LogFactory.getLog(TServiceUfConfirm.class);
	
	public TServiceUfConfirm(){
		System.out.println("SessionContext.getInstance().getWebStylePath():::" + SessionContext.getInstance().getWebStylePath());
		dealTemplatePath( "/form/TServiceUfConfirm.ftl");
	}
}
