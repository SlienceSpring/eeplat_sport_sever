package com.exedosoft.plat.ui.jquery.form;

import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

/**
 * Export data 用于扩展试图
 */
public class TExportData4Extend extends DOViewTemplate {

	public TExportData4Extend() {
		dealTemplatePath("/form/TExportData4Extend.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		// DOPaneModel pm = (DOPaneModel)doimodel;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);

		DOFormModel fm = (DOFormModel) doimodel;

		StringBuffer strLink = new StringBuffer();

		strLink.append(DOGlobals.PRE_FULL_FOLDER).append(
				"file/downloadfile_excel_extend.jsp?1=1");
		data.put("excelurl", strLink.toString());

		return data;
	}

}
