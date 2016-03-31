package com.exedosoft.plat.gene.jquery.bootstrap;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.BusiPackage;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormLinks;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneLinks;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.form.DOInputText;
import com.exedosoft.plat.ui.jquery.form.DOTextArea;
import com.exedosoft.plat.ui.jquery.form.DOValueDate;
import com.exedosoft.plat.ui.jquery.form.DOValueDateTime;
import com.exedosoft.plat.ui.jquery.form.DOValuePane;
import com.exedosoft.plat.ui.jquery.form.DOValueSimple;
import com.exedosoft.plat.ui.jquery.form.DOValueStaticList;
import com.exedosoft.plat.ui.jquery.form.TAPane;
import com.exedosoft.plat.ui.jquery.form.TAServiceUf;
import com.exedosoft.plat.ui.jquery.form.TClose;
import com.exedosoft.plat.ui.jquery.form.TConditionExtend;
import com.exedosoft.plat.ui.jquery.form.TConditionExtendSwith;
import com.exedosoft.plat.ui.jquery.form.TExportData4Extend;
import com.exedosoft.plat.ui.jquery.form.TImportData;
import com.exedosoft.plat.ui.jquery.form.TPane;
import com.exedosoft.plat.ui.jquery.form.TPaneSearch;
import com.exedosoft.plat.ui.jquery.form.TServiceUf;
import com.exedosoft.plat.ui.jquery.form.TSuite;
import com.exedosoft.plat.ui.jquery.form.TValuePaneRecentView;
import com.exedosoft.plat.ui.jquery.form.my97.DatePicker;
import com.exedosoft.plat.ui.jquery.grid.GridConditionExtend;
import com.exedosoft.plat.ui.jquery.grid.GridList;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.ui.jquery.pane.ContentPaneScroll;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

public class GeneUIExtend {

	private static Log log = LogFactory.getLog(GeneUIExtend.class);

	private final String SQL_SELECT_SERVICE = "select service.* from do_service service,do_bo bo where service.bouid = bo.objuid  bo.name = ?";

	private DOController gridList = DOController
			.getControllerByClassName(GridList.class.getName());

	private DOController gridExtendView = DOController
			.getControllerByClassName(GridConditionExtend.class.getName());

	private DOController formInputText = DOController
			.getControllerByClassName(DOInputText.class.getName());

	private DOController formTextArea = DOController
			.getControllerByClassName(DOTextArea.class.getName());

	private DOController formValueSimple = DOController
			.getControllerByClassName(DOValueSimple.class.getName());

	private DOController formValueDate = DOController
			.getControllerByClassName(DOValueDate.class.getName());

	private DOController formValueDateTime = DOController
			.getControllerByClassName(DOValueDateTime.class.getName());

	private DOController formDateMy97 = DOController
			.getControllerByClassName(DatePicker.class.getName());

	private DOController contentPane = DOController
			.getControllerByClassName(ContentPane.class.getName());

	private DOController formServiceUf = DOController
			.getControllerByClassName(TServiceUf.class.getName());

	private DOController paneOverFlow = DOController
			.getControllerByClassName(ContentPaneScroll.class.getName());



	private static DOController formValueStaticList = DOController
			.getControllerByClassName(DOValueStaticList.class.getName());
	// private DOController formDelete =
	// DOController.getControllerByName(TDelete.class.getName());

	private DOController formPane = DOController
			.getControllerByClassName(TPane.class.getName());
	
	private DOController formPaneSearch = DOController
			.getControllerByClassName(TPaneSearch.class.getName());

	private DOController formImportData = DOController
			.getControllerByClassName(TImportData.class.getName());

	private DOController formExportData = DOController
			.getControllerByClassName(TExportData4Extend.class.getName());

	private DOController aServiceUf = DOController
			.getControllerByClassName(TAServiceUf.class.getName());

	private DOController aPane = DOController
			.getControllerByClassName(TAPane.class.getName());


	private DOController valuePane = DOController
			.getControllerByClassName(DOValuePane.class.getName());

	private DOController tSuite = DOController
			.getControllerByClassName(TSuite.class.getName());

	private DOController tConditionExtend = DOController
			.getControllerByClassName(TConditionExtend.class.getName());

	private DOController tConditionExtendSwith = DOController
			.getControllerByClassName(TConditionExtendSwith.class.getName());

	private String geneATable = "";

	private String label;

	private DOBO category = null;

	String mainPaneName = "";
	String condtionPaneName = "";
	String resultPaneName = "";

	String condtionGridName = "";
	String resultGridName = "";

	public GeneUIExtend(String aTable, String alabel) {
		aTable = StringUtil.get_Name(aTable);
		this.label = alabel;
		this.geneATable = aTable;
		category = DOBO.getDOBOByName(aTable);

		mainPaneName = "PM_" + geneATable + "_Main";
		condtionPaneName = "PM_" + geneATable + "_Criteria";
		resultPaneName = "PM_" + geneATable + "_Result";

		condtionGridName = "GM_" + geneATable + "_Criteria";
		resultGridName = "GM_" + geneATable + "_Result";
	}

	public void geneConfig() {

		// /面板命名方式:PM_DO_LOG_Insert PM_DO_Parameter_Service_Browse

		// /表格命名方式 GM_DO_Application_List_List

		if (category == null) {
			return;
		}

		// //需要用到的业务对象
		DOBO bo = DOBO.getDOBOByName("DO_BO");
		bo.refreshContext(category.getObjUid());

		List properties = category.retrieveProperties();
		if (properties.size() == 0) {
			return;
		}

		// 总面板面呈
		DODataSource dds = DODataSource.parseGlobals();
		DAOUtil.INSTANCE().currentDataSource(dds);
		Transaction t = dds.getTransaction();

		try {

			if (DOPaneModel.getPaneModelByName(mainPaneName) != null) {
				System.err.println("面板已经存在------------");
				return;
			}
			t.begin();

			/**
			 * 生成总面板
			 */
			DOPaneModel pmTotal = new DOPaneModel();
			pmTotal.setName(mainPaneName);
			pmTotal.setL10n(mainPaneName);
			pmTotal.setCategory(category);
			pmTotal.setController(paneOverFlow);

			DOService aService = DOService.getService("do_ui_panemodel_copy");
			DAOUtil.INSTANCE().store(pmTotal, aService);

			/**
			 * 删除按钮的目标面板
			 */

			DOGridModel theControlGridModel = DOGridModel
					.getGridModelByName("GM_" + geneATable + "_control");
			
			String delL10n = "删除";
			if ("en".equals(DOGlobals.getValue("lang.local"))) {
				delL10n = "Delete";
			} 
			DOFormModel aFm = DOFormModel.getFormModelByL10n(
					theControlGridModel, delL10n);
			aFm.setLinkPaneModel(pmTotal);
			DAOUtil.INSTANCE().store(aFm);

			DOPaneModel pmResult = geneResult(properties);
			DOPaneModel pmCondition = geneCondition(properties, pmResult);

			aService = DOService.getService("DO_UI_PaneLinks_copy");
			DOPaneLinks link1 = new DOPaneLinks();
			link1.setParentPane(pmTotal);
			link1.setChildPane(pmCondition);
			link1.setOrderNum(5);
			DAOUtil.INSTANCE().store(link1, aService);

			DOPaneLinks link2 = new DOPaneLinks();
			link2.setParentPane(pmTotal);
			link2.setChildPane(pmResult);
			link2.setOrderNum(10);
			DAOUtil.INSTANCE().store(link2, aService);

			// //搞一下菜单

			DOPaneModel _opener_tab = DOPaneModel
					.getPaneModelByName("_opener_tab");

			String menuName = "";

			try {
				String bpUid = DOGlobals.getInstance().getSessoinContext()
						.getFormInstance().getValue("bpUid");
				BusiPackage dbp = BusiPackage.getPackageByID(bpUid);
				menuName = dbp.getApplication().getName();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DOMenuModel parentMenu = DOMenuModel.getMenuModelByName(menuName);
			DOBO boMenu = DOBO.getDOBOByName("DO_UI_MenuModel");

			if (parentMenu == null) {
				DOMenuModel dmm = DOMenuModel.getMenuModelByName(menuName
						+ "_root");
				if (dmm != null) {
					parentMenu = (DOMenuModel) dmm.retrieveChildren().get(0);
				}
			}

			if (parentMenu != null) {
				boMenu.refreshContext(parentMenu.getObjUid());
			}
			DOMenuModel dmm = new DOMenuModel();
			dmm.setName(geneATable);
			dmm.setL10n(this.label);
			dmm.setLinkPane(pmTotal);
			dmm.setTargetPane(_opener_tab);
			dmm.setParentMenu(parentMenu);
			dmm.setCategory(category);
			dmm.setOrderNum(5);
			DOService menuModelInsert = DOService
					.getService("DO_UI_MenuModel_copy");
			DAOUtil.INSTANCE().store(dmm, menuModelInsert);

			t.end();
			// conditionGrid.setService(sService)

		} catch (Exception e) {
			t.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private DOPaneModel geneCondition(List properties, DOPaneModel pmResult)
			throws ExedoException {
		/**
		 * 生成表格
		 */
		DOGridModel conditionGrid = new DOGridModel();
		conditionGrid.setL10n(condtionGridName);
		conditionGrid.setName(condtionGridName);
		if ("en".equals(DOGlobals.getValue("lang.local"))) {
			conditionGrid.setCaption("Specify Search Criteria");

		} else {
			conditionGrid.setCaption("请输入查询条件");
		}
		conditionGrid.setCategory(category);

		conditionGrid.setController(gridExtendView);

		conditionGrid = DAOUtil.INSTANCE().store(conditionGrid);

		/**
		 * 生成面板
		 */
		DOPaneModel pmCondition = new DOPaneModel();
		if ("en".equals(DOGlobals.getValue("lang.local"))) {
			conditionGrid.setCaption("Specify Search Criteria");

		} else {
			pmCondition.setTitle("请输入查询条件");
		}
		pmCondition.setName(condtionPaneName);
		pmCondition.setL10n(condtionPaneName);
		pmCondition.setCategory(category);
		pmCondition.setController(contentPane);
		pmCondition
				.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_GRIDMODEL));
		pmCondition.setLinkUID(conditionGrid.getObjUid());
		DAOUtil.INSTANCE().store(pmCondition);

		int i = 20;
		for (Iterator<DOBOProperty> it = properties.iterator(); it.hasNext(); i = i + 5) {
			DOBOProperty p = it.next();

			if (p.isBlobType() || p.isClobType()) {
				continue;
			}

			if (!p.isKeyCol()) {
				DOFormModel fm = new DOFormModel();
				DOFormModel old = DOFormModel.getFormModelByProperty(p
						.getObjUid());
				if (old != null) {
					fm.setL10n(old.getL10n());
				} else {
					fm.setL10n(p.getL10n());
				}
				fm.setRelationProperty(p);
				fm.setGridModel(conditionGrid);
				fm.setOrderNum(Integer.valueOf(i));
				fm.setIsNull(DOFormModel.NULL_YES);

				if (p.isDateOrTimeType()) {
					fm.setController(formDateMy97);
				} else {
					fm.setController(formInputText);
				}

				if (p.isInt() || p.isLong()) {
					fm.setExedojoType("digits");
				} else if (p.isNumberType()) {
					fm.setExedojoType("number");
				}
				DAOUtil.INSTANCE().store(fm);
			}
		}

		GeneUIBase.geneControlButtonForm(null, pmResult, conditionGrid, "查询",
				"Search", formPaneSearch, Integer.valueOf(i), null,
				DOFormModel.OUTGRID_BOTTOM, null, "noEnter");

		GeneUIBase.geneControlButtonForm(null, pmResult, conditionGrid, "", "",
				tConditionExtend, Integer.valueOf(1), null,
				DOFormModel.OUTGRID_TOP, null, "noEnter");

		GeneUIBase.geneControlButtonForm(null, null, conditionGrid, "", "",
				tConditionExtendSwith, Integer.valueOf(18), null,
				DOFormModel.OUTGRID_TOP, null, "noEnter");

		DOPaneModel pmInsert = DOPaneModel.getPaneModelByName("PM_"
				+ geneATable + "_insert");
		GeneUIBase.geneControlButtonForm(null, pmInsert, conditionGrid, "新增",
				"New", formPane, Integer.valueOf(10), null,
				DOFormModel.OUTGRID_TOP, null, "noEnter");

		GeneUIBase.geneControlButtonForm(null, null, conditionGrid, "导入",
				"Import", formImportData, Integer.valueOf(15), null,
				DOFormModel.OUTGRID_TOP, null, "noEnter");

		GeneUIBase.geneControlButtonForm(null, null, conditionGrid, "导出",
				"Export", formExportData, Integer.valueOf(16), null,
				DOFormModel.OUTGRID_TOP, null, "noEnter");

		return pmCondition;
	}

	private DOPaneModel geneResult(List properties) throws ExedoException {

		/**
		 * 相关服务生成
		 */
		DOService rService = new DOService();
		rService.setName(geneATable + "_auto_criteria");
		rService.setL10n(geneATable + "_auto_criteria");
		rService.setBo(category);
		rService.setMainSql("select * from " + this.geneATable
				+ " /*criteria*/ ");
		rService.setType(DOService.TYPE_SELECT_AUTO_CONDITION);
		DAOUtil.INSTANCE().store(rService);

		List paras = this.category.retrieveParameties();
		int i = 1;
		for (Iterator<DOParameter> it = paras.iterator(); it.hasNext(); i = i + 5) {
			DOParameter dop = it.next();
			if (dop.getProperty() == null || dop.getProperty().isKeyCol()) {
				continue;
			}
			DOParameterService dps = new DOParameterService();
			dps.setDop(dop);
			dps.setDos(rService);
			dps.setOrderNum(Integer.valueOf(i));
			if (dop.getProperty().isString()) {
				dps.setPatterm("#col# like ?;%#value#%");
			}

			DOService aService = DOService
					.getService("DO_Parameter_Service_Copy_Batch");
			DAOUtil.INSTANCE().store(dps, aService);
		}
		/**
		 * END 相关服务生成
		 */

		/**
		 * 生成表格
		 */
		DOGridModel gmResult = new DOGridModel();
		gmResult.setL10n(resultGridName);
		gmResult.setName(resultGridName);

		gmResult.setCategory(category);
		gmResult.setController(gridList);
		gmResult.setService(rService);
		gmResult.setRowSize(10);
		gmResult.setIsCheckBox(DOGridModel.CHECK_BOX);

		gmResult = DAOUtil.INSTANCE().store(gmResult);

		/**
		 * 生成面板
		 */
		DOPaneModel pmResult = new DOPaneModel();
		if ("en".equals(DOGlobals.getValue("lang.local"))) {
			pmResult.setTitle("All " + this.label);
		} else {
			pmResult.setTitle("全部" + this.label);
		}
		pmResult.setName(resultPaneName);
		pmResult.setL10n(resultPaneName);
		pmResult.setCategory(category);
		pmResult.setController(contentPane);
		pmResult.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_GRIDMODEL));
		pmResult.setLinkUID(gmResult.getObjUid());
		pmResult = DAOUtil.INSTANCE().store(pmResult);

		int max = 7;
		int num = 0;

		DOBOProperty valueCol = DOBOProperty.getDOBOPropertyByName(
				category.getSqlStr(), category.getValueCol());
		geneResultForm(1, gmResult, valueCol);

		i = 5;
		for (Iterator<DOBOProperty> it = properties.iterator(); it.hasNext(); i = i + 5) {
			DOBOProperty p = it.next();

			if (p.isBlobType() || p.isClobType() || p.isKeyCol()
					|| p.isValueCol()) {
				continue;
			}
			if (num >= max) {
				break;
			}

			num++;
			geneResultForm(i, gmResult, p);
		}

		DOFormModel suiteFm = GeneUIBase.geneControlButtonForm(null, null,
				gmResult, "操作", "Operation", tSuite, Integer.valueOf(i + 5),
				null, DOFormModel.OUTGRID_NOMOUNT, null, "noEnter");
		suiteFm.setWidth("55px");
		DAOUtil.INSTANCE().store(suiteFm);

		DOPaneModel pmUpdate = DOPaneModel.getPaneModelByName("PM_"
				+ geneATable + "_update");

		DOFormModel modiFm = GeneUIBase.geneControlButtonForm(null, pmUpdate,
				gmResult, "修改", "Edit", aPane, Integer.valueOf(i + 10), null,
				DOFormModel.OUTGRID_INLINE, null, "noEnter");

		DOService delService = DOService
				.getService(this.geneATable + "_delete");

		DOFormModel delFm = GeneUIBase.geneControlButtonForm(delService,
				pmResult, gmResult, "删除", "Del", aServiceUf,
				Integer.valueOf(i + 15), "confirmDelete()",
				DOFormModel.OUTGRID_INLINE, null, "noEnter");

		DOFormLinks linksModi = new DOFormLinks();
		linksModi.setForm(suiteFm);
		linksModi.setLinkform(modiFm);
		linksModi.setExtend1("1");
		DAOUtil.INSTANCE().store(linksModi);

		linksModi = new DOFormLinks();
		linksModi.setForm(suiteFm);
		linksModi.setLinkform(delFm);
		linksModi.setExtend1("2");
		DAOUtil.INSTANCE().store(linksModi);

		delService = DOService.getService(this.geneATable + "_delete_selected");

		GeneUIBase.geneControlButtonForm(delService, pmResult, gmResult, "删除",
				"Del", formServiceUf, Integer.valueOf(i + 15),
				"confirmDelete()", DOFormModel.OUTGRID_BOTTOM, null, "noEnter");

		return pmResult;
	}

	void geneResultForm(int i, DOGridModel gmResult, DOBOProperty p)
			throws ExedoException {
		DOFormModel fm = new DOFormModel();

		DOFormModel old = DOFormModel.getFormModelByProperty(p.getObjUid());
		if (old != null) {
			fm.setL10n(old.getL10n());
		} else {
			fm.setL10n(p.getL10n());
		}
		fm.setRelationProperty(p);
		fm.setGridModel(gmResult);
		fm.setIsNull(DOFormModel.NULL_YES);
		fm.setOrderNum(Integer.valueOf(i));

		if (p.isValueCol()) {
			fm.setController(valuePane);

			DOPaneModel paneModel = DOPaneModel.getPaneModelByName("PM_"
					+ geneATable + "_control_main");
			fm.setLinkPaneModel(paneModel);
		} else if (p.isDateType()) {

			fm.setController(formValueDate);

		} else if (p.isDateOrTimeType()) {

			fm.setController(formValueDateTime);

		} else {

			if (p.getInputConfig() != null
					&& p.getInputConfig().indexOf(";") != -1) {
				fm.setInputConfig(p.getInputConfig());
				fm.setController(formValueStaticList);
			} else {
				fm.setController(formValueSimple);
			}

		}
		DAOUtil.INSTANCE().store(fm);
	}

	public static void main(String[] args) {

		// DOMenuModel parentMenu = new DOMenuModel();
		// parentMenu.setObjUid("cccc");
		//
		// DOMenuModel dmm = new DOMenuModel();
		// dmm.setName("a");
		// dmm.setL10n("b");
		// dmm.setParentMenu(parentMenu);
		// dmm.setOrderNum(5);
		//
		// BOInstance bi = new BOInstance();
		// bi.fromObject(dmm);
		//
		// System.out.println(bi);

		DOPaneModel aPm = DOPaneModel.getPaneModelByName("clubnew_portal");

		System.out.println("DOPaneModel:::::" + aPm);

		// CacheFactory.getCacheData().cacheAllConfigData();
		//
		// DOController paneOverFlow =
		// DOController.getControllerByName(ContentPaneScroll.class
		// .getName());
		//
		// DOBO category = DOBO.getDOBOByName("DO_DataSource");
		//
		// DOPaneModel pmTotal = new DOPaneModel();
		// pmTotal.setName("aaa");
		// pmTotal.setL10n("aaa");
		// pmTotal.setCategory(category);
		// pmTotal.setController(paneOverFlow);
		// try {
		// DAOUtil.INSTANCE().store(pmTotal);
		// } catch (ExedoException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
