package com.exedosoft.plat.ui.bootstrap.form;

import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIView;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;
import com.exedosoft.plat.util.StringUtil;

public class DOValueIconDashboard extends DOBaseForm implements DOIView {

	public DOValueIconDashboard() {
		super();
	}

	public String getHtmlCode(DOIModel aModel) {

		if (isUsingTemplate) {
			return super.getHtmlCode(aModel);
		}

		DOFormModel fm = (DOFormModel) aModel;
		String value = fm.getValue();

		if (value != null && !value.trim().equals("")) {
			StringBuffer sb = new StringBuffer();
			if (fm.getData().getValue("bo_uid") != null) {
				DOBO bo = DOBO.getDOBOByID(fm.getData().getValue("bo_uid"));
				if (bo!=null && bo.getIcon() != null) {
					sb.append("<i class='")
							.append(StringUtil.getFaIcon(bo.getIcon()))
							.append("'>").append("</i>&nbsp;");
				}
			}
			sb.append(value);
			return sb.toString();

		} else {
			return "&nbsp;";
		}
	}

}
