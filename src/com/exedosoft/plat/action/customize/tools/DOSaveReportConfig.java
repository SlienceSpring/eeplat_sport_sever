package com.exedosoft.plat.action.customize.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.jquery.grid.GridListCrosstab;
import com.exedosoft.plat.ui.jquery.grid.GridListGroup;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;
import com.exedosoft.plat.util.I18n;

/**
 * 
 * 保存扩展视图
 * 
 */

public class DOSaveReportConfig extends DOAbstractAction {

	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(DOSaveReportConfig.class);
	

	private static DOController gridListGroup = DOController
			.getControllerByClassName(GridListGroup.class.getName());

	private static DOController gridListCrosstab = DOController
			.getControllerByClassName(GridListCrosstab.class.getName());

	public DOSaveReportConfig() {
	}

	public String excute() {

		BOInstance uiForm = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();
		log.info("Form:::::::" + uiForm);

		DOBO boGrid = DOBO.getDOBOByName("do_ui_gridmodel");
		BOInstance biGrid = boGrid.getCorrInstance();

		if (biGrid == null) {

			BOInstance backInstance = new BOInstance();
			backInstance.putValue("error_str", "没有选中表格！");
			this.setEchoValue("没有选中表格！");
			this.setInstance(backInstance);
			return this.NO_FORWARD;
		}
		
		

		Transaction t = boGrid.getDataBase().getTransaction();
		try {
			String paras = Escape.unescape(uiForm.getValue("paras"));
			JSONObject reportConfig = new JSONObject(paras);
			
//			if( (reportConfig.has("groupcol") && reportConfig.getString("groupcol").equals("")) ||
//				(reportConfig.has("group1") && reportConfig.getString("group1").equals("")) 
//				){
//				
//				BOInstance backInstance = new BOInstance();
//				backInstance.putValue("error_str", "没有选择数据！");
//				this.setEchoValue("没有选择数据！");
//				this.setInstance(backInstance);
//				return this.NO_FORWARD;
//			}

			log.info("chartConfig:::::::" + reportConfig);

			String gridType = reportConfig.getString("gridType");
			int iGridType = Integer.parseInt(gridType);
			t.begin();

			DOGridModel theGridModel = DOGridModel.getGridModelByID(biGrid
					.getUid());

			theGridModel.setGridType(iGridType);
			
			if(iGridType == DOGridModel.GRID_TYPE_GROUP){
				theGridModel.setController(gridListGroup);
			}else{
				theGridModel.setController(gridListCrosstab);
			}
			
			theGridModel.setExeDoc(paras);

			DAOUtil.INSTANCE().store(theGridModel);

			t.end();

		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();

			return NO_FORWARD;
		}

		this.setEchoValue(I18n.instance().get("保存成功!"));
		return DEFAULT_FORWARD;
	}

	public static void main(String[] args) {
		
		System.out.println("test:"
				+ GridListGroup.class.getName());

		System.out.println("test:"
				+ DOController
				.getControllerByName(GridListGroup.class.getName()));

	}

}
