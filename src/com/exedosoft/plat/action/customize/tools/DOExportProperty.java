package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.util.StringUtil;

public class DOExportProperty extends DOAbstractAction {

	private static final long serialVersionUID = 568992871873045123L;

	public String excute() throws ExedoException {

		StringBuilder sb = new StringBuilder("<export>");
		DOBO bo = DOBO.getDOBOByName("DO_BO_Property");

		BOInstance biController = bo.getCorrInstance();
		if (biController != null) {
			sb.append("\n<property><li>")
					.append(StringUtil.filter(biController.toJSONString()))
					.append("</li></property>");
		}
		sb.append("</export>");
		this.setEchoValue(sb.toString());
		return DEFAULT_FORWARD;

	}

}
