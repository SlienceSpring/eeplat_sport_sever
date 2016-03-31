package com.exedosoft.plat.action.customize.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
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

public class DOSaveExtendConfig extends DOAbstractAction {

	// ////////////////1，所有的名称上面加上用户名 完成
	// //2， 动态下来类别的值问题
	// //3，当前时间（今天，今天之前，今天之后）。（只做这两个也可以）//当前用户， 不做了，可以选择
	// //4， 最近的的视图，保存到表格的设计说明里面，以json格式。//后面实现
	// ///5,初始化成这种形式，顺带tab方式。///后面实现

	/**
	 * 
	 */
	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(DOSaveExtendConfig.class);

	private static List<String> LIMIT_OPERATOR = new ArrayList<String>();

	private static DOController paneOverFlow = DOController
			.getControllerByClassName(ContentPaneScroll.class.getName());

	private static DOController gridList = DOController
			.getControllerByClassName(GridList.class.getName());

	static {

		LIMIT_OPERATOR.add("like");
		LIMIT_OPERATOR.add("not like");
		LIMIT_OPERATOR.add("start");
		LIMIT_OPERATOR.add("end");

		LIMIT_OPERATOR.add("is");
		LIMIT_OPERATOR.add("is not");

		LIMIT_OPERATOR.add("is date");
		LIMIT_OPERATOR.add("is not date");

		LIMIT_OPERATOR.add("after");
		LIMIT_OPERATOR.add("befor");
		LIMIT_OPERATOR.add("now");
		LIMIT_OPERATOR.add("afternow");
		LIMIT_OPERATOR.add("befornow");

		LIMIT_OPERATOR.add("=");
		LIMIT_OPERATOR.add("!=");
		LIMIT_OPERATOR.add(">");
		LIMIT_OPERATOR.add("<");

		LIMIT_OPERATOR.add(">=");
		LIMIT_OPERATOR.add("<=");
		LIMIT_OPERATOR.add("is null");
		LIMIT_OPERATOR.add("is not null");

	}

	public DOSaveExtendConfig() {
	}

	public String excute() {

		log.info("Enter DOSaveExtendConfig::::::::::::::::::::::::::");

		BOInstance uiForm = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();

		// paras={"viewName":"fsdfds","showColNames":["402812814a08c236014a08c237500002","402812814a08c236014a08c237a30003","402812814a08c236014a08c237f50004","402812814a08c236014a08c238470005","402812814a08c236014a08c238970006","402812814a08c236014a08c238e60007","402812814a08c236014a08c239270008","402812814a08c236014a08c239870009","402812814a08c236014a08c239d8000a"],
		// "criterias":[{"pre":"and","col":"402812814a08c236014a08c236760000","operator":"isn't","value":"dss"},{"pre":"and","col":"402812814a08c236014a08c237500002","operator":"contains","value":"fdsfs"},{"pre":"and","col":"402812814a08c236014a08c237a30003","operator":"<","value":"2014-12-29"}],"criteriaStatement":"( ( 1 与 2 ) 与 3 )"}}

		// //把value拼装以;分割放入note ,service.setNote

		log.info("Form:::::::" + uiForm);

		String paras = Escape.unescape(uiForm.getValue("paras"));

		String loginName = "";

		try {
			loginName = DOGlobals.getInstance().getSessoinContext().getUser()
					.getName();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			JSONObject extendConfig = new JSONObject(paras);

			String viewName = extendConfig.getString("viewName");
			String tableName = extendConfig.getString("tableName");
			JSONArray showColNames = extendConfig.getJSONArray("showColNames");
			JSONArray criterias = extendConfig.getJSONArray("criterias");
			String criteriaStatement = extendConfig
					.getString("criteriaStatement");

			String selectedPml = null;

			if (extendConfig.has("selectedPml")) {

				selectedPml = extendConfig.getString("selectedPml");

			}

			criteriaStatement = criteriaStatement.replaceAll("与", "and");
			criteriaStatement = criteriaStatement.replaceAll("或", "or");

			// ///////////////////////////////////////////
			DOBO bo = DOBO.getDOBOByName(tableName);
			// //刷新当前的业务对象
			DOBO motherBO = DOBO.getDOBOByName("do_bo");
			motherBO.refreshContext(bo.getObjUid());

			// //////////////////////有效性检查，是否重复

			String paneName = "pm_" + bo.getName() + "_" + loginName + "_"
					+ PinYin.getPinYin(viewName);

			DOService paneSelectByName = DOService
					.getService("DO_UI_PaneModel_selectbyname");
			List list = paneSelectByName.invokeSelect(paneName);

			if (list.size() > 0 && !paneName.equals(selectedPml)) {

				BOInstance backInstance = new BOInstance();
				backInstance.putValue("error_str", "视图名称不能重复！");
				this.setEchoValue("视图名称不能重复！");
				this.setInstance(backInstance);
				return this.NO_FORWARD;
			}

			// //修改情况

			// //////////PaneModel
			DOPaneModel thePaneModel = null;
			// ////////GridModel
			DOGridModel theGridModel = null;
			// ///////Service
			DOService theService = null;
			if (selectedPml != null && !"".equals(selectedPml)) {
				thePaneModel = DOPaneModel.getPaneModelByName(selectedPml);
				theGridModel = thePaneModel.getGridModel();
				theService = theGridModel.getService();
			}

			// 把这个参数以ExeDoc的形式存储起来，供修改（暂不考虑后台改动）。
			// / 带数据过滤的baseSQL

			String finalSql = "select * from " + tableName
					+ " where 1=1 /*criteria*/";

			DOService baseService = DOService.getService(tableName
					+ "_auto_criteria");
			if (baseService != null) {
				finalSql = baseService.getMainSql();
				if (finalSql.toLowerCase().indexOf(" where ") == -1) {
					finalSql = finalSql.replace("/*criteria*/",
							"where 1=1 /*criteria*/");
				}
			}
			// /不直接拼了，还是用?的方式，这样更好。

			String sqlstatement = "";

			// ////处理条件
			for (int i = 0; i < criterias.length(); i++) {

				JSONObject oneCriteria = criterias.getJSONObject(i);
				DOFormModel theForm = DOFormModel.getFormModelByID(oneCriteria
						.getString("col"));

				if (theForm == null) {
					continue;
				}

				String col = theForm.getColName();

				String operator = oneCriteria.getString("operator");

				// <option value="like">包含</option>
				// <option value="not like">不包含</option>
				// <option value="start">开始字符</option>
				// <option value="end">结束字符</option>

				StringBuffer aColStatement = new StringBuffer(col);

				List<String> values = new ArrayList<String>();

				// //限制操作符，如果非法输入，则不让录入。
				if (!LIMIT_OPERATOR.contains(operator)) {
					this.setEchoValue("已阻止，存在非法字符入侵！");
					return NO_FORWARD;
				}

				if (operator.equals("like") || operator.equals("start")
						|| operator.equals("end")) {

					aColStatement.append(" like ?");

				} else if (operator.equals("not like")) {

					aColStatement.append(" not like ?");

				} else if (operator.equals("is null")
						|| operator.equals("is not null")) {

					aColStatement.append(" ").append(operator);
				} else if (operator.equals("now")) {
					aColStatement.append(" = ?");
				} else if (operator.equals("befornow")) {
					aColStatement.append(" < ?");
				} else if (operator.equals("afternow")) {
					aColStatement.append(" > ?");
				} else {

					aColStatement.append(" ").append(operator).append(" ? ");

				}

				if (criteriaStatement.equals("")) {
					criteriaStatement = aColStatement.toString();
				} else {
					criteriaStatement = criteriaStatement.replace(" " + (i + 1)
							+ " ", " " + aColStatement.toString() + " ");
				}

			}
			// /////////////////////////////////////////////
			if (!criteriaStatement.equals("")) {
				finalSql = finalSql.replace("/*criteria*/", "/*criteria*/ and "
						+ criteriaStatement);
			} else {
				finalSql = finalSql.replace("where 1=1 /*criteria*/", "");
			}

			boolean isNew = false;
			Transaction t = bo.getDataBase().getTransaction();
			t.begin();
			try {

				// //保存服务

				if (theService == null) {
					isNew = true;
					theService = new DOService();
					theService.setType(DOService.TYPE_SELECT_SELF_DEFINED);
					theService.setBo(bo);
				}

				theService.setMainSql(finalSql);
				theService.setName(bo.getName() + "_self_" + loginName + "_"
						+ PinYin.getPinYin(viewName));
				theService.setL10n(bo.getName() + "_self_" + loginName + "_"
						+ viewName);
				theService.setExedoc(paras);
				DAOUtil.INSTANCE().store(theService);

				// ///获取baseService的参数
				if (isNew) {
					DOService copyParaLinks = DOService
							.getService("DO_Parameter_Service_Copy_Batch");
					copyParaLinks.beginBatch();
					DOBO boParaService = DOBO
							.getDOBOByName("DO_Parameter_Service");
					for (DOParameterService dps : (List<DOParameterService>) baseService
							.retrieveParaServiceLinks()) {
						if (dps.getDop().getType().intValue() != DOParameter.TYPE_FORM) {
							BOInstance bi = boParaService.getInstance(dps
									.getObjUid());
							bi.putValue("objuid", null);
							bi.putValue("serviceUid", theService.getObjUid());
							copyParaLinks.addBatch(bi);
						}
					}
					copyParaLinks.endBatch();
				}

				// /保存表格

				if (theGridModel == null) {
					theGridModel = new DOGridModel();
					theGridModel.setService(theService);
					theGridModel.setCategory(bo);
					theGridModel.setRowSize(10);
					theGridModel.setController(gridList);
				} else {
					// ////////删除下面的表格
					String hql = "delete FROM do_ui_formmodel where gridModelUid = ? ";
					DAOUtil.INSTANCE().delete(hql, theGridModel.getObjUid());
				}

				theGridModel.setName("gm_" + bo.getName() + "_" + loginName
						+ "_" + PinYin.getPinYin(viewName));
				theGridModel.setL10n("gm_" + bo.getName() + "_" + loginName
						+ "_" + viewName);
				theGridModel.setCaption(viewName);
				DAOUtil.INSTANCE().store(theGridModel);

				// ////////保存表格元素
				DOBO boForm = DOBO.getDOBOByName("do_ui_formmodel");
				DOService copyService = DOService
						.getService("DO_UI_FormModel_COPY_TO_GRID");
				if ("en".equals(DOGlobals.getValue("lang.local"))) {
					copyService = DOService
							.getService("DO_UI_FormModel_COPY_TO_GRID_EN");
				}
				// ////处理条件
				for (int i = 0; i < showColNames.length(); i++) {

					String formUid = showColNames.getString(i);

					BOInstance biForm = boForm.getInstance(formUid);

					biForm.putValue("objuid", null);
					biForm.putValue("gridModelUid", theGridModel.getObjUid());
					biForm.putValue("orderNum", (i + 1) * 5);
					BOInstance newBiForm = copyService.invokeUpdate(biForm);
				}

				// /////////保存面板
				if (thePaneModel == null) {
					thePaneModel = new DOPaneModel();
					thePaneModel.setLinkType(DOPaneModel.LINKTYPE_GRIDMODEL);
					thePaneModel.setLinkUID(theGridModel.getObjUid());
					thePaneModel.setCategory(bo);
					thePaneModel.setController(paneOverFlow);
				}

				thePaneModel.setName(paneName);
				thePaneModel.setL10n("pm_" + bo.getName() + "_" + loginName
						+ "_" + viewName);
				thePaneModel.setTitle(viewName);
				DAOUtil.INSTANCE().store(thePaneModel);

				// ///////////建立关联关系
				if (selectedPml == null || "".equals(selectedPml)) {

					// ///和父窗口发生关联
					// 得到父窗口gridModelUid 得到父窗口 extend formModelUid
					String extendFormUid = extendConfig
							.getString("extendFormUid");
					String extendGridUid = extendConfig
							.getString("extendGridUid");

					DOGridModel extendGrid = DOGridModel
							.getGridModelByID(extendGridUid);
					DOFormModel extendForm = DOFormModel
							.getFormModelByID(extendFormUid);

					DOFormModel newFormModel = new DOFormModel();
					newFormModel.setIsOutGridAction(DOFormModel.OUTGRID_INLINE);
					newFormModel.setLinkPaneModel(thePaneModel);
					newFormModel.setL10n("Link to " + thePaneModel.getL10n());
					newFormModel.setGridModel(extendGrid);
					DAOUtil.INSTANCE().store(newFormModel);

					DOFormLinks formLinks = new DOFormLinks();
					formLinks.setForm(extendForm);
					formLinks.setLinkform(newFormModel);

					try {
						formLinks.setExtend2(DOGlobals.getInstance()
								.getSessoinContext().getUser().getUid());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DAOUtil.INSTANCE().store(formLinks);

				}
				// /callback返回的数据
				BOInstance backInstance = new BOInstance();
				backInstance.putValue("newPaneName", thePaneModel.getName());
				backInstance
						.putValue("newPaneObjUid", thePaneModel.getObjUid());
				backInstance.putValue("newPaneTitle", thePaneModel.getTitle());

				System.out.println("ret::" + backInstance);

				this.setInstance(backInstance);

				t.end();
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				t.rollback();
			}

			log.info("Final SQL:::" + finalSql);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return NO_FORWARD;
		}

		return DEFAULT_FORWARD;
	}

	public static void main(String[] args) {

		System.out.println("test:"
				+ StringEscapeUtils.escapeSql("like '%1%' or (1=1)"));

	}

}
