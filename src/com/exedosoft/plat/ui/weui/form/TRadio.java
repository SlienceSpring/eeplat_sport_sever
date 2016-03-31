package com.exedosoft.plat.ui.weui.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TRadio extends TCheckBox {

	private static Log log = LogFactory.getLog(TRadio.class);
	
	public TRadio(){
		dealTemplatePath("/form/TRadio.ftl");
	}
	

}
