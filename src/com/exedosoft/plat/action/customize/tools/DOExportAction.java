package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.StringUtil;

public class DOExportAction extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 568992871873045123L;

	@Override
	public String excute() throws ExedoException {

		StringBuilder sb = new StringBuilder("<export>");
		DOBO bo = DOBO.getDOBOByName("DO_ActionConfig");

		BOInstance biController = bo.getCorrInstance();
		if (biController != null) {
			sb.append("\n<action><li>")
					.append(StringUtil.filter(biController.toJSONString()))
					.append("</li></action>");
			// /导出携带的资源
			if (biController.getValue("coreScript") != null) {

				String scriptUid = biController.getValue("coreScript");
				if (scriptUid.length() == 32) {

					DOService findScriptService = DOService
							.getService("DO_BO_Icon_Browse");
					BOInstance biScript = findScriptService
							.getInstance(scriptUid);
					sb.append("\n<scripts><li>")
							.append(StringUtil.filter(biScript.toJSONString()))
							.append("</li></scripts>");
				}
			}
		}
		sb.append("</export>");
		this.setEchoValue(sb.toString());
		return DEFAULT_FORWARD;

	}

}
