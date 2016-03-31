package com.exedosoft.plat.action.customize.tools;

import com.exedosoft.plat.CacheFactory;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.gene.jquery.GeneUIParentChild;
import com.exedosoft.plat.gene.jquery.PropertyManager;
import com.exedosoft.plat.gene.jquery.bootstrap.GeneUIMasterSlave;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.util.I18n;

public class DOGeneConfigBuildRelation extends DOAbstractAction {

	private static final long serialVersionUID = 4555077593344493040L;

	@Override
	public String excute() throws ExedoException {

		DOBO bo = DOBO.getDOBOByName("do_bo");
		BOInstance instance = bo.getCorrInstance();
		if (instance == null) {
			this.setEchoValue(I18n.instance().get("没有数据!"));
			return NO_FORWARD;
		}

		String colName = this.actionForm.getValue("col_name");
		String dobouid = this.actionForm.getValue("bouid");
		String linktype = this.actionForm.getValue("linktype");

		if (colName == null && dobouid == null) {
			this.setEchoValue(I18n.instance().get("字段名称或者类型没有定义!"));
			return NO_FORWARD;
		}

		DOBO linkBO = DOBO.getDOBOByID(dobouid);
		// ////////////////////////
		// ////////////////////////先处理组合关系
		// ///////////////////// 2 为组合
		if ("2".equals(linktype)) {

			String ctlPm = "PM_" + linkBO.getSqlStr() + "_control_main";
			DOPaneModel aPM = DOPaneModel.getPaneModelByName(ctlPm);
			if (aPM != null) {// /extend View 模式。
				GeneUIMasterSlave gm = new GeneUIMasterSlave(
						linkBO.getSqlStr(), instance.getValue("sqlstr"),
						colName);
				gm.geneConfig();
			} else {
				GeneUIParentChild gpc = new GeneUIParentChild(
						linkBO.getSqlStr(), instance.getValue("sqlstr"),
						colName);
				gpc.geneConfig();
			}
		}

		// ///////////////////////再处理关联关系
		PropertyManager pm = new PropertyManager();

		int iLinkType = 1;
		try {
			iLinkType = Integer.parseInt(linktype);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pm.buildRelation(instance.getUid(), colName, linkBO, iLinkType);

		CacheFactory.getCacheData().clear();
		CacheFactory.getCacheRelation().getData().clear();
		CacheFactory.getCacheData().fromSerialObject();

		this.setEchoValue(I18n.instance().get("初始化成功!"));

		return DEFAULT_FORWARD;
	}

}
