package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.util.StringUtil;

public class DOExportScript extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 568992871873045123L;

	@Override
	public String excute() throws ExedoException {

		StringBuilder sb = new StringBuilder("<export>");
		DOBO bo = DOBO.getDOBOByName("DO_BO_Icon");

		BOInstance biController = bo.getCorrInstance();
		if (biController != null) {
			sb.append("\n<scripts><li>")
					.append(StringUtil.filter(biController.toJSONString()))
					.append("</li></scripts>");
		}
		sb.append("</export>");
		this.setEchoValue(sb.toString());
		return DEFAULT_FORWARD;

	}

}
