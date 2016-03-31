package com.exedosoft.plat.ui.weui.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TSelect  extends TCheckBox {

	private static Log log = LogFactory.getLog(TSelect.class);
	
	public TSelect(){
		dealTemplatePath("/form/TSelect.ftl");
	}
	

}
