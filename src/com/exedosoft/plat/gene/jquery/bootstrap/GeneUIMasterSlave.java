package com.exedosoft.plat.gene.jquery.bootstrap;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.CacheFactory;
import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DORule;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.DOServiceRule;
import com.exedosoft.plat.gene.jquery.GeneUIParentChild;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormLinks;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneLinks;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.bootstrap.grid.GridFormTable;
import com.exedosoft.plat.ui.bootstrap.grid.GridListChild;
import com.exedosoft.plat.ui.jquery.form.DOInputHidden;
import com.exedosoft.plat.ui.jquery.form.DOInputText;
import com.exedosoft.plat.ui.jquery.form.DOStaticList;
import com.exedosoft.plat.ui.jquery.form.DOTextArea;
import com.exedosoft.plat.ui.jquery.form.DOValueDate;
import com.exedosoft.plat.ui.jquery.form.DOValueDateTime;
import com.exedosoft.plat.ui.jquery.form.DOValueSimple;
import com.exedosoft.plat.ui.jquery.form.DOValueStaticList;
import com.exedosoft.plat.ui.jquery.form.TAPane;
import com.exedosoft.plat.ui.jquery.form.TAServiceUf;
import com.exedosoft.plat.ui.jquery.form.TPane;
import com.exedosoft.plat.ui.jquery.form.TReturn;
import com.exedosoft.plat.ui.jquery.form.TServiceUf;
import com.exedosoft.plat.ui.jquery.form.TSuite;
import com.exedosoft.plat.ui.jquery.form.my97.DatePicker;
import com.exedosoft.plat.ui.jquery.menu.RightMenu;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

public class GeneUIMasterSlave {

	private static Log log = LogFactory.getLog(GeneUIMasterSlave.class);

	private final String SQL_SELECT_SERVICE = "select service.* from do_service service,do_bo bo where service.bouid = bo.objuid  bo.name = ?";

	private DOController gridListChild = DOController
			.getControllerByClassName(GridListChild.class.getName());

	private DOController contentPane = DOController
			.getControllerByClassName(ContentPane.class.getName());

	private DOController aServiceUf = DOController
			.getControllerByClassName(TAServiceUf.class.getName());

	private static DOController gridFormTable = DOController
			.getControllerByClassName(GridFormTable.class.getName());

	private DOController rightMenu = DOController
			.getControllerByClassName(RightMenu.class.getName());

	private DOController aPane = DOController
			.getControllerByClassName(TAPane.class.getName());

	private DOController tPane = DOController
			.getControllerByClassName(TPane.class.getName());
	
	
	private static DOController formReturn = DOController
			.getControllerByClassName(TReturn.class.getName());
	

	private DOController tSuite = DOController
			.getControllerByClassName(TSuite.class.getName());

	private static DOController formInputText = DOController
			.getControllerByClassName(DOInputText.class.getName());

	private static DOController formTextArea = DOController
			.getControllerByClassName(DOTextArea.class.getName());

	private static DOController formValueSimple = DOController
			.getControllerByClassName(DOValueSimple.class.getName());

	private static DOController formValueStaticList = DOController
			.getControllerByClassName(DOValueStaticList.class.getName());

	private static DOController formStaticList = DOController
			.getControllerByClassName(DOStaticList.class.getName());

	private static DOController formValueDate = DOController
			.getControllerByClassName(DOValueDate.class.getName());

	private static DOController formValueDateTime = DOController
			.getControllerByClassName(DOValueDateTime.class.getName());

	private static DOController formDateMy97 = DOController
			.getControllerByClassName(DatePicker.class.getName());

	private static DOController formServiceUf = DOController
			.getControllerByClassName(TServiceUf.class.getName());


	// private DOController formDelete =
	// DOController.getControllerByName(TDelete.class.getName());

	private static DOController formPane = DOController
			.getControllerByClassName(TPane.class.getName());

	private static DOController formHidden = DOController
			.getControllerByClassName(DOInputHidden.class.getName());

	private String parentATable = "";
	private String parentLabel = "";

	private String childATable = "";
	private String childLabel = "";

	private String linkCol = "";

	private DOBO parentCategory = null;
	private DOBO childCategory = null;

	public GeneUIMasterSlave(String parentTable, String childTable,
			String aLinkCol) {
		parentTable = StringUtil.get_Name(parentTable);
		this.parentATable = parentTable;
		this.childATable = childTable;
		this.linkCol = aLinkCol;
		parentCategory = DOBO.getDOBOByName(parentTable);
		this.parentLabel = parentCategory.getL10n();
		childCategory = DOBO.getDOBOByName(childTable);
		this.childLabel = childCategory.getL10n();
	}

	public void geneConfig() {
		geneConfigControl();
	}

	/**
	 * 生成修改的相关配置
	 */

	public void geneConfigControl() {

		String childTitle = childCategory.getL10n();

		DOGridModel theChildGrid = DOGridModel.getGridModelByName("GM_"
				+ childATable + "_findby" + linkCol + "_" + this.parentATable);

		if (theChildGrid != null) {
			return;
		}

		// ///geneInsertParent
		try {

			DOService aService = DOService.getService(childATable + "_browse");

			DOService findByParentService = geneFindByParentService();
			DOPaneModel childPaneModel = geneChildrenResult(childATable
					+ "_findby" + linkCol + "_" + this.parentATable,
					childTitle, findByParentService);

			String modify = "修改" + this.childLabel;
			String insert = "新增" + this.childLabel;

			if ("en".equals(DOGlobals.getValue("lang.local"))) {
				modify = "Edit " + this.childLabel;
				insert = "New " + this.childLabel;
				;
			}
			// //依赖于childPaneModel 的生成
			genePaneAndGrid(aService, childATable + "_update4master_"
					+ this.parentATable, modify);

			genePaneAndGrid(aService, childATable + "_insert4master_"
					+ this.parentATable, insert);

			geneChildrenResultButton(childPaneModel);

			// /////////////////保存时，保存主子表 。

			/**
			 * 生成子面板以及表格，包含子面板多数据的新增、修改、删除
			 */

			/**
			 * 为主表的删除操作增加rule
			 */
			geneDeleteServiceAndRule();

			/**
			 * 生成菜单
			 */

			String menuName = this.parentATable + "_right_menu";

			DOMenuModel parentMenu = DOMenuModel.getMenuModelByName(menuName);
			DOBO boMenu = DOBO.getDOBOByName("DO_UI_MenuModel");

			if (parentMenu == null) {
				parentMenu = new DOMenuModel();
				parentMenu.setName(menuName);
				parentMenu.setL10n(menuName);
				parentMenu.setCategory(this.parentCategory);
				parentMenu.setController(rightMenu);
				parentMenu.setOrderNum(5);
				DOService menuModelInsert = DOService
						.getService("DO_UI_MenuModel_copy");
				DAOUtil.INSTANCE().store(parentMenu, menuModelInsert);

				DOPaneModel menuPane = new DOPaneModel();
				menuPane.setCategory(this.parentCategory);
				menuPane.setName("PM_" + this.parentATable + "_RightMenu");

				// ///下一步考虑 是不是名称采用和Servie 一致
				menuPane.setTitle(this.parentATable);
				menuPane.setL10n("PM_" + this.parentATable + "_RightMenu");
				menuPane.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_MENU));
				menuPane.setLinkUID(parentMenu.getObjUid());
				menuPane.setSizerWidth(20);
				menuPane.setController(contentPane);
				DOService insertPane = DOService
						.getService("do_ui_panemodel_copy");
				DAOUtil.INSTANCE().store(menuPane, insertPane);

				DOPaneModel controlPaneMain = DOPaneModel
						.getPaneModelByName("PM_" + this.parentATable
								+ "_control_main");

				DOService linksCopy = DOService
						.getService("DO_UI_PaneLinks_copy");
				DOPaneLinks links = new DOPaneLinks();
				links.setParentPane(controlPaneMain);
				links.setChildPane(menuPane);
				links.setOrderNum(9);
				DAOUtil.INSTANCE().store(links, linksCopy);

				DOPaneModel controlPaneLeft = DOPaneModel
						.getPaneModelByName("PM_" + this.parentATable
								+ "_control_left");
				controlPaneLeft.setSizerWidth(80);
				DAOUtil.INSTANCE().store(controlPaneLeft);
			}

			int menuSize = parentMenu.retrieveChildrenNoAuth().size();

			int orderNum = (menuSize + 1) * 5;

			DOMenuModel dmm = new DOMenuModel();
			dmm.setName(this.childATable);
			dmm.setL10n(this.childCategory.getL10n());
			dmm.setLinkPane(childPaneModel);
			dmm.setParentMenu(parentMenu);
			dmm.setCategory(this.childCategory);
			dmm.setOrderNum(orderNum);
			dmm.setController(rightMenu);
			DOService menuModelInsert = DOService
					.getService("DO_UI_MenuModel_copy");
			DAOUtil.INSTANCE().store(dmm, menuModelInsert);

			DOGridModel controlGrid = DOGridModel.getGridModelByName("GM_"
					+ this.childATable + "_control");


		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void geneChildrenResultButton(DOPaneModel childPaneModel)
			throws ExedoException {
		DOGridModel gridM = childPaneModel.getDOGridModel();
		DOFormModel suiteFm = GeneUIBase.geneControlButtonForm(null, null,
				gridM, "操作", "Operation", tSuite, Integer.valueOf(1005), null,
				DOFormModel.OUTGRID_NOMOUNT, null, "noEnter");
		suiteFm.setWidth("55px");
		DAOUtil.INSTANCE().store(suiteFm);

		DOPaneModel pmUpdate = DOPaneModel.getPaneModelByName("PM_"
				+ this.childATable + "_update4master_" + this.parentATable);

		DOFormModel modiFm = GeneUIBase.geneControlButtonForm(null, pmUpdate,
				gridM, "修改", "Edit", aPane, Integer.valueOf(1010), null,
				DOFormModel.OUTGRID_INLINE, null, "noEnter");

		DOService delService = DOService.getService(this.childATable
				+ "_delete");

		DOFormModel delFm = GeneUIBase.geneControlButtonForm(delService,
				childPaneModel, gridM, "删除", "Del", aServiceUf,
				Integer.valueOf(1015), "confirmDelete()",
				DOFormModel.OUTGRID_INLINE, null, "noEnter");

		DOFormLinks linksModi = new DOFormLinks();
		linksModi.setForm(suiteFm);
		linksModi.setLinkform(modiFm);
		linksModi.setExtend1("1");
		DAOUtil.INSTANCE().store(linksModi);

		linksModi = new DOFormLinks();
		linksModi.setForm(suiteFm);
		linksModi.setLinkform(delFm);
		linksModi.setExtend1("3");
		DAOUtil.INSTANCE().store(linksModi);

		DOPaneModel pmInsert = DOPaneModel.getPaneModelByName("PM_"
				+ this.childATable + "_insert4master_" + this.parentATable);
		GeneUIBase.geneControlButtonForm(null, pmInsert, gridM, "新增", "New",
				tPane, Integer.valueOf(10), null, DOFormModel.OUTGRID_BOTTOM,
				null, "noEnter");

		DOPaneModel controlPaneLeft = DOPaneModel.getPaneModelByName("PM_"
				+ this.parentATable + "_control_left");

		DOService linksCopy = DOService.getService("DO_UI_PaneLinks_copy");
		DOPaneLinks links = new DOPaneLinks();
		links.setParentPane(controlPaneLeft);
		links.setChildPane(childPaneModel);

		int orderSize = 5;
		if (controlPaneLeft.retrieveChildren() != null) {
			orderSize = (controlPaneLeft.retrieveChildren().size() + 1) * 5 + 2;
		}

		links.setOrderNum(orderSize);

		DAOUtil.INSTANCE().store(links, linksCopy);
	}

	public DOService geneFindByParentService() throws ExedoException {
		/**
		 * 修改主表的insert链接到主子表新增面板 修改主表的删除到新增级联删除服务。
		 */

		String serviceName = childATable + "_findby_" + this.linkCol + "_"
				+ this.parentATable;
		// ///////////////////////////TEAdd
		DOService findChildByParentService = DOService.getService(serviceName);
		if (findChildByParentService == null) {
			findChildByParentService = new DOService();
			findChildByParentService.setName(serviceName);
			findChildByParentService.setL10n(serviceName);
			findChildByParentService.setBo(parentCategory);
			findChildByParentService.setType(DOService.TYPE_SELECT);
			findChildByParentService.setMainSql("select * from " + childATable
					+ " where " + linkCol + " =?");
			DAOUtil.INSTANCE().store(findChildByParentService);

			/**
			 * 获取参数
			 */
			DOParameter dopParentCurrent = DOParameter.getParameterByProperty(
					DOBOProperty.getDOBOPropertyByName(parentATable,
							parentCategory.getKeyCol()),
					DOParameter.TYPE_CURRENT);

			/**
			 * 创建服务和参数的链接
			 */
			DOParameterService dps = new DOParameterService();
			dps.setDop(dopParentCurrent);
			dps.setDos(findChildByParentService);
			dps.setOrderNum(5);
			DAOUtil.INSTANCE().store(dps);
		}

		return findChildByParentService;
	}

	public DOService geneDeleteServiceAndRule() throws ExedoException {
		/**
		 * 修改主表的insert链接到主子表新增面板 修改主表的删除到新增级联删除服务。
		 */

		DOService deleteService = DOService.getService(childATable
				+ "_deletebymaster" + "_" + this.parentATable);
		if (deleteService != null) {
			return deleteService;
		}

		// ///////////////////////////TEAdd
		DOService deleteChildByParentService = new DOService();
		deleteChildByParentService.setName(childATable + "_deletebymaster"
				+ "_" + this.parentATable);
		deleteChildByParentService.setL10n(childATable + "_deletebymaster"
				+ "_" + this.parentATable);
		deleteChildByParentService.setBo(parentCategory);
		deleteChildByParentService.setType(DOService.TYPE_DELETE);
		deleteChildByParentService.setMainSql("delete from " + childATable
				+ " where " + linkCol + " =?");
		DAOUtil.INSTANCE().store(deleteChildByParentService);

		/**
		 * 获取参数
		 */
		DOParameter dopParentCurrent = DOParameter.getParameterByProperty(
				DOBOProperty.getDOBOPropertyByName(parentATable,
						parentCategory.getKeyCol()), DOParameter.TYPE_CURRENT);

		/**
		 * 创建服务和参数的链接
		 */
		DOParameterService dps = new DOParameterService();
		dps.setDop(dopParentCurrent);
		dps.setDos(deleteChildByParentService);
		dps.setOrderNum(5);
		DAOUtil.INSTANCE().store(dps);

		DORule deleteChildByParentServiceRule = this
				.geneServiceToRule(deleteChildByParentService);

		DOService mainDeleteService = DOService.getService(parentATable
				+ "_delete");
		DOServiceRule dsrDelete = new DOServiceRule();
		dsrDelete.setRule(deleteChildByParentServiceRule);
		dsrDelete.setService(mainDeleteService);
		dsrDelete.setExeType(DOServiceRule.EXETYPE_BEFOR);
		dsrDelete.setLinkSalience(5);
		DOService serviceRuleInsert = DOService
				.getService("DO_Service_Rule_copy");

		DAOUtil.INSTANCE().store(dsrDelete, serviceRuleInsert);

		mainDeleteService = DOService.getService(parentATable
				+ "_delete_selected");
		dsrDelete = new DOServiceRule();
		dsrDelete.setRule(deleteChildByParentServiceRule);
		dsrDelete.setService(mainDeleteService);
		dsrDelete.setExeType(DOServiceRule.EXETYPE_BEFOR);
		dsrDelete.setLinkSalience(5);

		DAOUtil.INSTANCE().store(dsrDelete, serviceRuleInsert);

		return mainDeleteService;
	}

	/**
	 * @param dao
	 * @param aService
	 * @throws ExedoException
	 * @throws DAOException
	 */
	public DOPaneModel geneChildrenResult(String aName, String title,
			DOService gridService) throws ExedoException {

		DOGridModel gridM = new DOGridModel();
		gridM.setCategory(childCategory);
		gridM.setCaption(title);
		gridM.setName("GM_" + aName);
		gridM.setL10n("GM_" + aName);

		if (gridService != null) {
			gridM.setService(gridService);
		}

		gridM.setController(gridListChild);
		gridM.setColNum(Integer.valueOf(2));

		DAOUtil.INSTANCE().store(gridM);

		int max = 7;
		int num = 0;

		GeneUIExtend gut = new GeneUIExtend(this.childATable, this.childLabel);
		DOBOProperty valueCol = DOBOProperty.getDOBOPropertyByName(
				this.childATable, this.childCategory.getValueCol());
		gut.geneResultForm(1, gridM, valueCol);

		int i = 5;
		// ///服务的属性 原来是aService.retrieveProperties
		for (Iterator itProp = childCategory.retrieveProperties().iterator(); itProp
				.hasNext();) {

			DOBOProperty prop = (DOBOProperty) itProp.next();
			if (prop.isBlobType() || prop.isClobType() || prop.isKeyCol()
					|| prop.isValueCol()) {
				continue;
			}

			if (prop.getColName().equals(this.linkCol)) {
				continue;
			}

			if (num >= max) {
				break;
			}
			num++;
			gut.geneResultForm(i, gridM, prop);
		}

		DOPaneModel childPane = new DOPaneModel();
		childPane.setCategory(childCategory);
		childPane.setName("PM_" + aName);

		// ///下一步考虑 是不是名称采用和Servie 一致
		childPane.setTitle(title);
		childPane.setL10n("PM_" + aName);
		childPane.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_GRIDMODEL));
		childPane.setLinkUID(gridM.getObjUid());
		childPane.setController(contentPane);
		DOService insertPane = DOService.getService("do_ui_panemodel_copy");
		DAOUtil.INSTANCE().store(childPane, insertPane);

		return childPane;
	}

	/**
	 * @param dao
	 * @param aService
	 * @throws ExedoException
	 * @throws DAOException
	 */
	private DOGridModel genePaneAndGrid(DOService aService, String aName,
			String title) throws ExedoException {

		DOGridModel gridM = new DOGridModel();
		gridM.setCategory(this.childCategory);
		gridM.setCaption(title);
		gridM.setName("GM_" + aName);
		gridM.setL10n("GM_" + aName);

		if (!aName.contains("_insert4master")) {
			gridM.setService(aService);
		}

		gridM.setController(gridFormTable);
		gridM.setColNum(Integer.valueOf(2));
		gridM.setCategory(aService.getBo());
		DAOUtil.INSTANCE().store(gridM);

		int i = 1;

		// ///服务的属性 原来是aService.retrieveProperties
		for (Iterator itProp = aService.getBo().retrieveProperties().iterator(); itProp
				.hasNext();) {

			DOBOProperty prop = (DOBOProperty) itProp.next();
			if (prop.isKeyCol()) {
				continue;
			}

			if (prop.getColName().equalsIgnoreCase(this.linkCol)) {
				continue;
			}

			DOFormModel formM = new DOFormModel();
			formM.setRelationProperty(prop);

			// /// 这一块从,multi_tenancy_column 这个表中取

			formM.setL10n(prop.getL10n());

			/**
			 * * 客户端验证配置，分为３部分，以;隔开 １，类型：Integer RealNumber EMail Text Others 2,
			 * 长度 ３, 其他Script 约束
			 * 
			 */
			if (prop.isValueCol()) {
				formM.setIsNull(DOFormModel.NULL_NO);
			}

			if (prop.isInt() || prop.isLong()) {
				formM.setExedojoType("digits");
			} else if (prop.isNumberType()) {
				formM.setExedojoType("number");
			} else if (prop.isString()) {
				// if ("en".equals(DOGlobals.getValue("lang.local"))) {
				formM.setUiType("maxlength=" + prop.getDbSize().intValue());

			}

			formM.setL10n(prop.getL10n());

			formM.setGridModel(gridM);

			formM.setOrderNum(Integer.valueOf(i * 5));
			if (prop.isDateOrTimeType()) {
				if (aName.endsWith("browse") || aName.endsWith("control")) {
					if (prop.isDateType()) {
						formM.setController(formValueDate);
					} else {
						formM.setController(formValueDateTime);
					}
				} else {
					formM.setController(formDateMy97);
				}
			} else {

				if (aName.endsWith("browse") || aName.endsWith("control")) {

					if (prop.getInputConfig() != null
							&& prop.getInputConfig().indexOf(";") != -1) {
						formM.setInputConfig(prop.getInputConfig());
						formM.setController(formValueStaticList);
					} else {
						formM.setController(formValueSimple);
					}
				} else {

					if (prop.getInputConfig() != null
							&& prop.getInputConfig().indexOf(";") != -1) {
						formM.setInputConfig(prop.getInputConfig());
						formM.setController(formStaticList);
					} else if (prop.getDbSize() != null
							&& prop.getDbSize().intValue() > 500) {
						formM.setController(formTextArea);
						formM.setIsNewLine(DOFormModel.NEWLINE_YES);
					} else {
						formM.setController(formInputText);
					}
				}

			}

			if (prop.getColName().equalsIgnoreCase("eversion")) {
				formM.setController(formHidden);
				formM.setIsHidden(DOFormModel.HIDDEN_YES);
				formM.setDefaultValue("1");
				formM.setIsOutGridAction(DOFormModel.OUTGRID_LEFT);
			}

			DAOUtil.INSTANCE().store(formM);
			i++;
		}

		// 对每个Grid赋給一个Pane

		DOPaneModel pane = new DOPaneModel();
		pane.setCategory(aService.getBo());
		pane.setName("PM_" + aName);

		// ///下一步考虑 是不是名称采用和Servie 一致
		pane.setTitle(title);
		pane.setL10n("PM_" + aName);
		pane.setLinkType(Integer.valueOf(DOPaneModel.LINKTYPE_GRIDMODEL));
		pane.setLinkUID(gridM.getObjUid());
		pane.setController(contentPane);
		DAOUtil.INSTANCE().store(pane);

		DOPaneModel parentPaneMain = DOPaneModel.getPaneModelByName("PM_"
				+ this.parentATable + "_control_main");

		if (aName.contains("_update4master")) {

			DOService defaultUpdateService = DOService.getService(aService
					.getBo().getName() + "_update");

			String newModfiyServiceName = childATable + "_update4master_"
					+ this.parentATable;

			DOService childModifyService = DOService
					.getService(newModfiyServiceName);
			if (childModifyService == null) {
				childModifyService = GeneUIParentChild.copyService(
						defaultUpdateService, newModfiyServiceName,
						this.childCategory.getObjUid(), this.parentATable,
						this.linkCol);
			}

			DOPaneModel targetChildGrid = DOPaneModel.getPaneModelByName("PM_"
					+ childATable + "_findby" + linkCol + "_"
					+ this.parentATable);

			GeneUIBase.geneControlButtonForm(childModifyService,
					parentPaneMain, gridM, "保存", "Save", formServiceUf, 1000,
					null, DOFormModel.OUTGRID_TOP, targetChildGrid, "noEnter");

			GeneUIBase.geneControlButtonForm(null, parentPaneMain, gridM, "返回",
					"BACK", formReturn, 1015, null, DOFormModel.OUTGRID_TOP,
					targetChildGrid, "noEnter");

			GeneUIBase.geneControlButtonForm(childModifyService,
					parentPaneMain, gridM, "保存", "Save", formServiceUf, 1020,
					null, DOFormModel.OUTGRID_BOTTOM, targetChildGrid,
					"noEnter");

			GeneUIBase.geneControlButtonForm(null, parentPaneMain, gridM, "返回",
					"BACK", formReturn, 1025, null, DOFormModel.OUTGRID_BOTTOM,
					targetChildGrid, "noEnter");

		} else if (aName.contains("_insert4master")) {

			DOService defaultInsertService = DOService.getService(aService
					.getBo().getName() + "_insert");

			String newInsertServiceName = childATable + "_insert4master_"
					+ this.parentATable;

			DOService childInsertService = DOService
					.getService(newInsertServiceName);
			if (childInsertService == null) {
				childInsertService = GeneUIParentChild.copyService(
						defaultInsertService, newInsertServiceName,
						this.childCategory.getObjUid(), this.parentATable,
						this.linkCol);
			}

			DOPaneModel targetChildGrid = DOPaneModel.getPaneModelByName("PM_"
					+ childATable + "_findby" + linkCol + "_"
					+ this.parentATable);

			GeneUIBase.geneControlButtonForm(childInsertService,
					parentPaneMain, gridM, "保存", "Save", formServiceUf, 1000,
					null, DOFormModel.OUTGRID_TOP, targetChildGrid, "noEnter");

			GeneUIBase.geneControlButtonForm(null, parentPaneMain, gridM, "返回",
					"BACK", formReturn, 1015, null, DOFormModel.OUTGRID_TOP,
					targetChildGrid, "noEnter");

			GeneUIBase.geneControlButtonForm(childInsertService,
					parentPaneMain, gridM, "保存", "Save", formServiceUf, 1020,
					null, DOFormModel.OUTGRID_BOTTOM, targetChildGrid,
					"noEnter");

			GeneUIBase.geneControlButtonForm(null, parentPaneMain, gridM, "返回",
					"BACK", formReturn, 1025, null, DOFormModel.OUTGRID_BOTTOM,
					targetChildGrid, "noEnter");

		}
		return gridM;
	}

	public DORule geneServiceToRule(DOService dos) throws ExedoException {

		BOInstance biService = new BOInstance();
		biService.fromObject(dos);

		BOInstance paras = new BOInstance();
		paras.putValue("serviceUid", biService.getUid());
		paras.putValue("onlyRun", "1");
		paras.putValue("condition", "true");
		paras.putValue("conditionType", "1");// 1 代表script
		paras.putValue("name", "Rule_" + biService.getValue("name"));
		paras.putValue("l10n", "Rule_" + biService.getValue("l10n"));
		paras.putValue("salience", "5");
		paras.putValue("bouid", biService.getValue("bouid"));

		DOService insertRule = DOService.getService("DO_Rule_Insert");
		BOInstance biRule = insertRule.invokeUpdate(paras);

		return DORule.getDORuleByID(biRule.getUid());
	}

	public static void main(String[] args) throws ExedoException {

		CacheFactory.getCacheData().fromSerialObject();
		DOBO boServicePara = DOBO.getDOBOByName("DO_Parameter_Service");
		BOInstance newServiceParas = new BOInstance();
		newServiceParas.setUid(null);
		newServiceParas.putValue("serviceUid", "abc");
		newServiceParas.putValue("parameterUid", "efg");
		System.out.println("newServiceParas::" + newServiceParas);
		System.out.println("DInsert Service::"
				+ boServicePara.getDInsertService());
		boServicePara.getDInsertService().invokeUpdate(newServiceParas);
	}

	// private static void geneSaveButtonForm(DOService aService, String aName,
	// DOGridModel gridM,String l10n) throws ExedoException {
	//
	// DOFormModel formM = new DOFormModel();
	// formM.setL10n(l10n);
	//
	//
	//
	// DOService linkService = DOService.getService(aService.getBo().getName()
	// + uName);
	// formM.setLinkService(linkService);
	// formM.setIsNewLine(1);
	// formM.setNameColspan(Integer.valueOf(0));
	// formM.setIsOutGridAction(DOFormModel.OUTGRID_BOTTOM);
	//
	// formM.setAlign("center");
	//
	// DOPaneModel pm = DOPaneModel.getPaneModelByName("PM_"
	// + aService.getBo().getName() + "_list");
	// formM.setGridModel(gridM);
	// formM.setOrderNum(Integer.valueOf(1000));
	// formM.setController(formServiceUf);
	// formM.setLinkPaneModel(pm);
	// formM.setTargetPaneModel(pm);
	// DAOUtil.INSTANCE().store(formM);
	//
	// }

	// public DOService getBatchInsertChildService() throws ExedoException {
	// DOService insertService = DOService.getService(childATable + "_insert");
	// System.out.println("Insert Service:::" + insertService);
	// DOService newService = copyService(insertService);
	// DOActionConfig doc = DOActionConfig
	// .getActionConfig("com.exedosoft.plat.action.CoreSaveEditAction");
	//
	// newService.setL10n(insertService.getName() + "_batch");
	// newService.setName(insertService.getName() + "_batch");
	// newService.setActionConfig(doc);
	// newService.setBo(childCategory);
	// DAOUtil.INSTANCE().store(newService);
	// return newService;
	// }

}
