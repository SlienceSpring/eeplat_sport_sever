package com.exedosoft.plat.ui.jquery.form;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.SessionContext;
import com.exedosoft.plat.template.HtmlTemplateGenerator;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIViewTemplate;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.util.DOGlobals;

public class TConditionExtend extends DOValueResultList implements
		DOIViewTemplate {

	private static Log log = LogFactory.getLog(TAutoComplete.class);

	public TConditionExtend() {
		dealTemplatePath("/form/TConditionExtend.ftl");
	}

	public String getHtmlCode(DOIModel doimodel) {

		Map<String, Object> data = this.putData(doimodel);

		String s = "";
		try {
			s = HtmlTemplateGenerator.getContentFromTemplate(this.templateFile,
					data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);
		DOFormModel aForm = (DOFormModel) doimodel;
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);

		StringBuffer buff = new StringBuffer("<optgroup stag='default' label='")
				.append("预定义视图' >");

		buff.append("\t<option  selected=true value='")
				.append(aForm.getLinkPaneModel().getName()).append("'>")
				.append(aForm.getLinkPaneModel().getTitle())
				.append("</option>\n");

		List<DOFormModel> linkForms = aForm.getLinkForms();
		if (linkForms.size() > 0) {
			for (DOFormModel fm : linkForms) {
				DOPaneModel dpm = fm.getLinkPaneModel();
				if (dpm != null) {
					buff.append("\t<option value='").append(dpm.getName())
							.append("'>").append(dpm.getTitle())
							.append("</option>\n");
				}
			}
		}

		buff.append("</optgroup>\n");

		SessionContext us = (SessionContext) DOGlobals.getInstance()
				.getSessoinContext();
		buff.append("<optgroup stag='cust' label='").append("客户创建的视图' >");

		if (us != null && us.getUser() != null) {
			String userid = us.getUser().getUid();
			List<DOFormModel> userForms = aForm.getLinkFormsOfUser(userid);
			if (userForms.size() > 0) {
				for (DOFormModel fm : userForms) {

					DOPaneModel dpm = fm.getLinkPaneModel();
					buff.append("\t<option value='").append(dpm.getName())
							.append("'>").append(dpm.getTitle())
							.append("</option>\n");
				}
			}
		}
		buff.append("</optgroup>\n");

		data.put("htmls", buff.toString());

		return data;
	}
}
