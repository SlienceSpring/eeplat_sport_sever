package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIView;
import com.exedosoft.plat.util.DOGlobals;

public class DOValueSimpleLoginMan extends DOBaseForm implements DOIView {

	public DOValueSimpleLoginMan() {
		super();
	}

	public String getHtmlCode(DOIModel aModel) {

		if (isUsingTemplate) {
			return super.getHtmlCode(aModel);
		}
		BOInstance bi = DOGlobals.getInstance().getSessoinContext().getUser();
		if (bi != null) {
			return bi.getName();
		}
		return "&nbsp;";
	}
}
