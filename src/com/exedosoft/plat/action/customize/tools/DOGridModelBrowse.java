package com.exedosoft.plat.action.customize.tools;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;

/**
 * 
 * 保存扩展视图
 * 
 */

public class DOGridModelBrowse extends DOAbstractAction {

	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(DOGridModelBrowse.class);

	public DOGridModelBrowse() {
	}

	public String excute() {

		DOBO boGrid = DOBO.getDOBOByName("do_ui_gridmodel");
		BOInstance biGrid = boGrid.getCorrInstance();

		if (biGrid == null) {

			BOInstance backInstance = new BOInstance();
			backInstance.putValue("error_str", "没有选中表格！");
			this.setEchoValue("没有选中表格！");
			this.setInstance(backInstance);
			return this.NO_FORWARD;
		}

		try {

			String paras = biGrid.getValue("exeDoc");

			if (paras != null && !paras.trim().equals("")) {
				JSONObject reportConfig = new JSONObject(paras);

				// /gridType 让交叉报表和分组报表容易切换
				if (reportConfig.has("gridType")) {
					if ("2".equals(reportConfig.get("gridType"))
							|| "3".equals(reportConfig.get("gridType"))) {
						reportConfig.remove("gridType");
					}
				}

				BOInstance aConfig = BOInstance.fromJSONObject(reportConfig);
				biGrid.getMap().putAll(aConfig.getMap());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		this.setInstance(biGrid);

		return DEFAULT_FORWARD;
	}

	public static void main(String[] args) {

		System.out.println("test:"
				+ StringEscapeUtils.escapeSql("like '%1%' or (1=1)"));

	}

}
