package com.exedosoft.plat.ui.bootstrap.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOIViewTemplate;

public class DOIconSelector extends DOResultListPopup implements
		DOIViewTemplate {

	private static Log log = LogFactory.getLog(DOIconSelector.class);

	public DOIconSelector() {
		dealTemplatePath("/form/DOIconSelector.ftl");
	}

	
}
