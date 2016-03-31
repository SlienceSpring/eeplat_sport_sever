package com.exedosoft.plat.ui.jquery.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.SSOController;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;

public class GridDetails extends DOViewTemplate {

	public GridDetails() {
		
		dealTemplatePath( "/grid/GridDetails.ftl");

	}
	
	
	public Map<String, Object> putData(DOIModel doimodel) {

		List list = new ArrayList();
		
		DOGridModel gm = (DOGridModel)doimodel;
		
		DOService theService = gm.getService(); 
		if(theService!=null){
			try {
				theService.fireBeforRules();
				list = theService.invokeSelect();
				theService.fireAfterRules();
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", doimodel);
		if (list.size() > 0) {
			BOInstance ins = (BOInstance) list.get(0);
			data.put("data", ins);
		}
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		String mainPaneName = theService.getBo().getMainPaneModel().getName();
		data.put("mainPaneName", mainPaneName);
		
		
		Integer  colNum = gm.getColNum();
		if(colNum==null){
			colNum = 2;
		}
		if(SSOController.isMobile()){
			colNum = 1;
		}
		data.put("colNum", colNum);
		
		data.put("mapForms",  gm.getCategoryForms() );
	
		DOService  updateOneValue = DOService.getService(theService.getBo().getName()  + "_update_onevalue");
		
		if(updateOneValue!=null){
			data.put("update_onevalue_service", updateOneValue.getName());
		}
		// /判断是否为弹出窗口的情况，不再判断是否为dialog , 详细信息不允许有dialog的存在。
//		data.put("isdialog", false);
//		DOPaneModel pm = gm.getContainerPane();
//		if (pm != null) {
//			List children = pm.retrieveChildren();
//			if (children == null || children.size() == 0) {
//				try {
//					String eeplatdialog = DOGlobals.getInstance()
//							.getSessoinContext().getFormInstance()
//							.getValue("eeplatdialog");
//					System.out.println("Dialog::" + eeplatdialog);
//					if ("true".equals(eeplatdialog)) {
//						data.put("isdialog", true);
//					}
//				} catch (Exception e) {
//				}
//			}
//		}

		return data;
	}

}
