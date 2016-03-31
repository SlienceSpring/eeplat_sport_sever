package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.StringUtil;

public class DOExportController extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 568992871873045123L;

	@Override
	public String excute() throws ExedoException {

		StringBuilder sb = new StringBuilder("<export>");
		DOBO bo = DOBO.getDOBOByName("DO_UI_Controller");

		BOInstance biController = bo.getCorrInstance();
		if (biController != null) {

			sb.append("\n<controller><li>").append(StringUtil.filter(biController.toJSONString())).append(
					"</li></controller>");
			
			////导出携带的资源。
			sb.append("\n<scripts>");

			DOService findScriptService = DOService
					.getService("DO_BO_Icon_Browse");
			
			if (biController.getValue("genScript") != null) {
				String scriptUid = biController.getValue("genScript");
				if (scriptUid.length() == 32) {


					BOInstance biScript = findScriptService
							.getInstance(scriptUid);
					sb.append("<li>")
							.append(StringUtil.filter(biScript.toJSONString()))
							.append("</li>");
				}
			}

			if (biController.getValue("template") != null) {
				String scriptUid = biController.getValue("template");
				if (scriptUid.length() == 32) {
					BOInstance biScript = findScriptService
							.getInstance(scriptUid);
					sb.append("<li>")
							.append(StringUtil.filter(biScript.toJSONString()))
							.append("</li>");
				}
			}

			
			sb.append("\n</scripts>");
			
		}
		
		sb.append("</export>");
		this.setEchoValue(sb.toString());
		return DEFAULT_FORWARD;

	}



}
