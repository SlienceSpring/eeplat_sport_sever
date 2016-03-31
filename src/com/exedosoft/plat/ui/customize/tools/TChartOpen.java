package com.exedosoft.plat.ui.customize.tools;

import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

public class TChartOpen extends DOViewTemplate {

	
	public TChartOpen(){
		dealTemplatePath(  "/customize/tools/TChartOpen.ftl" );
	}
	
	public Map<String, Object> putData(DOIModel doimodel) {

		DOFormModel fm = (DOFormModel)doimodel;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);
		
		BOInstance bi = fm.getData();
		String pml = "PM_c"
				+ bi.getValue("name").substring(1);
		data.put("chartPml", pml);

		return data;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
