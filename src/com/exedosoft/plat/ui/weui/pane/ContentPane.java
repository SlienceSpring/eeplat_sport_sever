package com.exedosoft.plat.ui.weui.pane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.jquery.pane.TPaneTemplate;

public class ContentPane extends TPaneTemplate {


	private static Log log = LogFactory.getLog(ContentPane.class);

	public ContentPane() {

		dealTemplatePath("/panel/ContentPane.ftl");
	}

	
	
}
