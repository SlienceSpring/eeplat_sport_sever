package com.exedosoft.plat.action.customize.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.CacheFactory;
import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormLinks;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.grid.GridList;
import com.exedosoft.plat.ui.jquery.pane.ContentPaneScroll;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;
import com.exedosoft.plat.util.PinYin;

/**
 * 
 * 保存扩展视图
 * 
 */

public class DORemoveExtendConfig extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(DORemoveExtendConfig.class);

	public DORemoveExtendConfig() {
	}

	public String excute() {

		log.info("Enter DOSaveExtendConfig::::::::::::::::::::::::::");

		BOInstance uiForm = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();

		String selectedPml = uiForm.getValue("selectedPml");
		String extendFormUid = uiForm.getValue("extendFormUid");

		DOBO boPane = DOBO.getDOBOByName("do_ui_panemodel");

		try {
			DOPaneModel dpm = DOPaneModel.getPaneModelByName(selectedPml);

			String resultPaneName = "PM_" + dpm.getCategory().getName()
					+ "_Result";
			if (dpm.getName().equals(resultPaneName)) {
				return NO_FORWARD;
			}

			System.out.println("Delete panel:::" + dpm);

			boPane.refreshContext(dpm.getObjUid());

			// ///首先删除的是表格元素
			DOFormModel extendForm = DOFormModel
					.getFormModelByID(extendFormUid);
			String userid = DOGlobals.getInstance().getSessoinContext()
					.getUser().getUid();
			List<DOFormModel> linkForms = extendForm.getLinkFormsOfUser(userid);
			for (DOFormModel fm : linkForms) {

				if (fm.getLinkPaneModel().equals(dpm)) {
					DAOUtil.INSTANCE().delete(fm);
					DOService deleteRubishLinks = DOService
							.getService("DO_UI_FormLinks_deleterubbish");
					deleteRubishLinks.invokeAll();
					break;
				}

			}

			// /服务的删除,这个服务没有参数和规则，所以可以不用invokeAll
			DOService deleteService = DOService
					.getService("DO_Service_DeleteIncludeRelations");
			String serviceUid = dpm.getGridModel().getService().getObjUid();
			deleteService.invokeUpdate(serviceUid);

			System.out.println("Current pane1:::" + boPane.getCorrInstance());

			// 面板删除，包含表格
			DOService deletePane = DOService
					.getService("DO_UI_PaneModel_Delete_All");
			List paras = new ArrayList();
			paras.add(dpm.getObjUid());
			deletePane.setParaList(paras);
			deletePane.invokeAll();

			CacheFactory.getCacheData().clear();
			CacheFactory.getCacheRelation().getData().clear();
			CacheFactory.getCacheData().fromSerialObject();

		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT_FORWARD;
	}

	public static void main(String[] args) {

		System.out.println("test:"
				+ StringEscapeUtils.escapeSql("like '%1%' or (1=1)"));

	}

}
