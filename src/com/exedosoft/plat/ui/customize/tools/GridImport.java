package com.exedosoft.plat.ui.customize.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.grid.GridList;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.RestUtil;

/**
 * @author aa
 */
public class GridImport extends GridList {

	private static Log log = LogFactory.getLog(GridImport.class);

	public GridImport() {

		dealTemplatePath("/customize/tools/GridImport.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		// DOGridModel gm = (DOGridModel) doimodel;

		String tablename = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("tablename");


		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);
		data.put("tablename", tablename);

	
		return data;
	}

}
