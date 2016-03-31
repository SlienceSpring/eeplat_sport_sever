package com.exedosoft.plat.ui.jquery.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

public class GridConditionExtend extends DOViewTemplate {

	public GridConditionExtend() {
		dealTemplatePath("/grid/GridConditionExtend.ftl");

	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> map = super.putData(doimodel);
		DOGridModel gm = (DOGridModel) doimodel;

		List tops = gm.getTopOutGridFormLinks();

		if (tops.size() > 0) {
			map.put("extendControl", tops.get(0));
			tops.remove(0);
			map.put("extendRests", tops);
		} else {

			DOFormModel extendControl = new DOFormModel();
			DOController cc = DOController
					.getControllerByClassName("com.exedosoft.plat.ui.jquery.form.TConditionExtend");
			extendControl.setController(cc);
			extendControl.setGridModel(gm);
			extendControl.setOrderNum(-10);
			extendControl.setIsOutGridAction(DOFormModel.OUTGRID_TOP);
			
			///////////关联resultPaneModel
			if (gm.getContainerPane() != null
					&& gm.getContainerPane().getParent() != null) {

				// //自动判断条件面板
				List children = gm.getContainerPane().getParent()
						.retrieveChildren();

				if (children != null && children.size() >= 2) {
					DOPaneModel resultModel = (DOPaneModel) children.get(1);
					if (resultModel != null) {
						if (resultModel.getDOGridModel() != null) {
							extendControl.setLinkPaneModel(resultModel);
						}
					}
				}
			}

			try {
				DAOUtil.INSTANCE().store(extendControl);
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DOFormModel extendControlSwitch = new DOFormModel();
			cc = DOController
					.getControllerByClassName("com.exedosoft.plat.ui.jquery.form.TConditionExtendSwith");
			extendControlSwitch.setController(cc);
			extendControlSwitch.setGridModel(gm);
			extendControlSwitch.setOrderNum(0);
			extendControlSwitch.setIsOutGridAction(DOFormModel.OUTGRID_TOP);

			try {
				DAOUtil.INSTANCE().store(extendControlSwitch);
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// com.exedosoft.plat.ui.jquery.form.TConditionExtendSwith

			map.put("extendControl", extendControl);
			List list = new ArrayList();
			list.add(extendControlSwitch);

			map.put("extendRests", list);

		}
		
		/////////如果是修改的情况
		BOInstance form = DOGlobals.getInstance().getSessoinContext().getFormInstance();
		if(form!=null && "true".equals(form.getValue("modify"))){
			
			
			
			
			
		}
		
		
		
		

		return map;
	}

}
