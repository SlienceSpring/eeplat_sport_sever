package com.exedosoft.plat.gene.jquery.bootstrap;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOPaneLinks;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.bootstrap.grid.GridFormTable;
import com.exedosoft.plat.ui.jquery.form.DOInputHidden;
import com.exedosoft.plat.ui.jquery.form.DOInputText;
import com.exedosoft.plat.ui.jquery.form.DOStaticList;
import com.exedosoft.plat.ui.jquery.form.DOTextArea;
import com.exedosoft.plat.ui.jquery.form.DOValueDate;
import com.exedosoft.plat.ui.jquery.form.DOValueDateTime;
import com.exedosoft.plat.ui.jquery.form.DOValueSimple;
import com.exedosoft.plat.ui.jquery.form.DOValueStaticList;
import com.exedosoft.plat.ui.jquery.form.TCloseTab;
import com.exedosoft.plat.ui.jquery.form.TPane;
import com.exedosoft.plat.ui.jquery.form.TReturn;
import com.exedosoft.plat.ui.jquery.form.TServiceUf;
import com.exedosoft.plat.ui.jquery.form.my97.DatePicker;
import com.exedosoft.plat.ui.jquery.grid.GridDetails;
import com.exedosoft.plat.ui.jquery.grid.GridList;
import com.exedosoft.plat.ui.jquery.grid.GridSupportMore;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.ui.jquery.pane.ContentPaneScroll;
import com.exedosoft.plat.util.DOGlobals;

public class GeneUIBase {

	private static Log log = LogFactory.getLog(GeneUIBase.class);

	private static final String SQL_SELECT_SERVICE = "select service.* from do_service service,do_bo bo where service.bouid = bo.objuid and  bo.name = ?";

	private static DOController gridList = DOController
			.getControllerByClassName(GridList.class.getName());

	private static DOController gridSupportMore = DOController
			.getControllerByClassName(GridSupportMore.class.getName());

	private static DOController gridFormTable = DOController
			.getControllerByClassName(GridFormTable.class.getName());

	private static DOController gridDetails = DOController
			.getControllerByClassName(GridDetails.class.getName());

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

	private static DOController contentPane = DOController
			.getControllerByClassName(ContentPane.class.getName());

	private static DOController contentPaneScroll = DOController
			.getControllerByClassName(ContentPaneScroll.class.getName());

	private static DOController formServiceUf = DOController
			.getControllerByClassName(TServiceUf.class.getName());

	private static DOController formReturn = DOController
			.getControllerByClassName(TReturn.class.getName());
	// private DOController formDelete =
	// DOController.getControllerByName(TDelete.class.getName());

	private static DOController formPane = DOController
			.getControllerByClassName(TPane.class.getName());

	private static DOController formHidden = DOController
			.getControllerByClassName(DOInputHidden.class.getName());

	// /////////////////////不能在这个就获取bp,因为类变量，当这个实例实例化时就执行了

	private String geneATable = "";

	private String label;

	private String geneType = "2";

	private String boUID;

	private static Hashtable<String, String> multiL10ns = new Hashtable<String, String>();

	public GeneUIBase(String aTable, String aLabel, String aBOUID) {

		this.label = aLabel;
		this.geneATable = aTable;
		this.boUID = aBOUID;
	}

	public void geneConfig() {

		DODataSource dss = DODataSource.parseGlobals();
		Transaction t = dss.getTransaction();

		// ///////////////////second generator grid and panes
		try {
			t.begin();
			List sers = DAOUtil.INSTANCE().select(DOService.class,
					SQL_SELECT_SERVICE, this.geneATable);

			/**
			 * 根据Service生成ui组件。
			 */

			String list = "所有" + this.label;
			String browse = this.label;
			String modify = "修改" + this.label;
			String copy = "复制" + this.label;
			String insert = "新增" + this.label;

			if ("en".equals(DOGlobals.getValue("lang.local"))) {
				list = "All " + this.label;
				browse = this.label;
				modify = "Edit " + this.label;
				copy = "Clone " + this.label;
				;
				insert = "New " + this.label;
				;
			}

			DOService aService = DOService.getService(geneATable + "_list");
			genePaneAndGrid(aService, gridList, geneATable + "_list", list);

			aService = DOService.getService(geneATable + "_browse");

			genePaneAndGrid(aService, gridSupportMore, geneATable + "_browse",
					browse);

			DOGridModel controlGrid = genePaneAndGrid(aService, gridDetails,
					geneATable + "_control", browse);

			genePaneAndGrid(aService, gridFormTable, geneATable + "_update",
					modify);

			genePaneAndGrid(aService, gridFormTable,
					geneATable + "_dulplicate", copy);

			genePaneAndGrid(aService, gridFormTable, geneATable + "_insert",
					insert);

			/**
			 * 处理controlgrid的按钮
			 */

			String tableName = aService.getBo().getSqlStr();
			DOPaneModel pmUpdate = DOPaneModel.getPaneModelByName("PM_"
					+ tableName + "_update");
			geneControlButtonForm(null, pmUpdate, controlGrid, "修改", "Edit",
					formPane, 1000, null, DOFormModel.OUTGRID_TOP, null,
					"noEnter");

			DOService deleteService = DOService.getService(tableName
					+ "_delete");

			DOPaneModel targetPaneModel = DOPaneModel
					.getPaneModelByID("_opener_tab");
			DOPaneModel mainPane = DOPaneModel.getPaneModelByName("PM_"
					+ tableName + "_main");

			geneControlButtonForm(deleteService, mainPane, controlGrid, "删除",
					"Delete", formServiceUf, 1005, "confirmDelete()",
					DOFormModel.OUTGRID_TOP, targetPaneModel, "noEnter");

			DOPaneModel pmClone = DOPaneModel.getPaneModelByName("PM_"
					+ tableName + "_dulplicate");
			geneControlButtonForm(null, pmClone, controlGrid, "复制", "Clone",
					formPane, 1010, null, DOFormModel.OUTGRID_TOP, null,
					"noEnter");

			/**
			 * GeneUIMasterSlave 会改写linkPane和targetPane
			 */
			geneControlButtonForm(null, null, controlGrid, "返回", "BACK",
					formReturn, 1015, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			t.end();
		}
	}

	// private void geneForms(HbmDAO dao) {
	//
	// try {
	// List list = dao.list(selectProperty, this.geneATable);
	// for (Iterator it = list.iterator(); it.hasNext();) {
	// DOBOProperty prop = (DOBOProperty) it.next();
	// DOFormModel formModel = new DOFormModel();
	// formModel.setRelationProperty(prop);
	// if (prop.isNumberType()) {
	// formModel.setExedojoType("RealNumber");
	// }
	//
	// formModel.setL10n(prop.getColName());
	// dao.store(formModel);
	// log.info("正在从" + prop.getColName() + "生成界面组件.....");
	// }
	// } catch (DAOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public static Hashtable<String, String> dealMultiL10ns() {

		// if (multiL10ns.size() == 0) {
		// // /获取 对应的l10n
		// DOService aService = DOService
		// .getService("multi_tenancy_column_findbytableid");
		// List list = aService.invokeSelect();
		//
		// }
		return multiL10ns;

	}

	/**
	 * @param dao
	 * @param aService
	 * @throws ExedoException
	 * @throws DAOException
	 */
	public static DOGridModel genePaneAndGrid(DOService aService,
			DOController controller, String aName, String title)
			throws ExedoException {

		DOGridModel gridM = new DOGridModel();
		gridM.setCategory(aService.getBo());
		gridM.setCaption(title);
		gridM.setName("GM_" + aName);
		gridM.setL10n("GM_" + aName);

		if (!aName.endsWith("insert")) {
			gridM.setService(aService);
		}

		gridM.setController(controller);
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
			DOFormModel formM = new DOFormModel();
			formM.setRelationProperty(prop);

			// /// 这一块从,multi_tenancy_column 这个表中取

			formM.setL10n(prop.getL10n());

			/**
			 * * 客户端验证配置，分为３部分，以;隔开 １，类型：Integer RealNumber EMail Text Others 2,
			 * 长度 ３, 其他Script 约束
			 * 
			 */

			if (!(aName.endsWith("browse") || aName.endsWith("control") || aName
					.endsWith("list"))) {
				if (prop.isValueCol()) {
					formM.setIsNull(DOFormModel.NULL_NO);
				}
			}

			if (prop.isInt() || prop.isLong()) {
				formM.setExedojoType("digits");
			} else if (prop.isNumberType()) {
				formM.setExedojoType("number");
			} else if (prop.isString()) {
				// if ("en".equals(DOGlobals.getValue("lang.local"))) {
				formM.setUiType("maxlength=" + prop.getDbSize().intValue());
				// } else {
				// formM.setUiType("maxlength:"
				// + (int) (prop.getDbSize().intValue() / 2));
				//
				// }
			}

			if (multiL10ns.get(prop.getOldColName()) != null) {
				formM.setL10n(multiL10ns.get(prop.getOldColName()));
			} else {
				formM.setL10n(prop.getL10n());
			}
			formM.setGridModel(gridM);

			formM.setOrderNum(Integer.valueOf(i * 5));
			if (prop.isDateOrTimeType()) {
				if (aName.endsWith("browse") || aName.endsWith("control")
						|| aName.endsWith("list")) {
					if (prop.isDateType()) {
						formM.setController(formValueDate);
					} else {
						formM.setController(formValueDateTime);
					}
				} else {
					formM.setController(formDateMy97);
				}
			} else {

				if (aName.endsWith("browse") || aName.endsWith("control")
						|| aName.endsWith("list")) {

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

		if (aName.endsWith("control")) {

			DOPaneModel mainPane = new DOPaneModel();
			mainPane.setCategory(aService.getBo());
			mainPane.setName("PM_" + aName + "_main");
			mainPane.setTitle(title);
			mainPane.setL10n("PM_" + aName + "_main");
			mainPane.setLayOutType(DOPaneModel.LAYOUT_HORIZONTAL);
			mainPane.setController(contentPane);
			mainPane = DAOUtil.INSTANCE().store(mainPane);

			DOPaneModel leftPane = new DOPaneModel();
			leftPane.setCategory(aService.getBo());
			leftPane.setName("PM_" + aName + "_left");
			leftPane.setTitle(title);
			leftPane.setL10n("PM_" + aName + "_left");
			leftPane.setController(contentPaneScroll);
			leftPane.setSizerWidth(100);
			leftPane = DAOUtil.INSTANCE().store(leftPane);

			aService = DOService.getService("DO_UI_PaneLinks_copy");
			DOPaneLinks links = new DOPaneLinks();
			links.setParentPane(mainPane);
			links.setChildPane(leftPane);
			links.setOrderNum(6);
			DAOUtil.INSTANCE().store(links, aService);

			DOPaneLinks links2 = new DOPaneLinks();
			links2.setParentPane(leftPane);
			links2.setChildPane(pane);
			links2.setOrderNum(6);
			DAOUtil.INSTANCE().store(links2, aService);

		}

		if (aName.endsWith("_update")) {
			DOService linkService = DOService.getService(aService.getBo()
					.getName() + "_update");
			DOPaneModel controlMain = DOPaneModel.getPaneModelByName("PM_"
					+ aService.getBo().getName() + "_control_main");
			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1000, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

			geneControlButtonForm(null, controlMain, gridM, "返回", "BACK",
					formReturn, 1015, null, DOFormModel.OUTGRID_TOP, null,
					"noEnter");

			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1020, null,
					DOFormModel.OUTGRID_BOTTOM, null, null);

			geneControlButtonForm(null, controlMain, gridM, "返回", "BACK",
					formReturn, 1025, null, DOFormModel.OUTGRID_BOTTOM, null,
					"noEnter");

		} else if (aName.endsWith("_insert")) {

			DOService linkService = DOService.getService(aService.getBo()
					.getName() + "_insert");

			DOPaneModel controlMain = DOPaneModel.getPaneModelByName("PM_"
					+ aService.getBo().getName() + "_control_main");

			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1000, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

			geneControlButtonForm(linkService, pane, gridM, "保存并新建",
					"Save&New", formServiceUf, 1005, null,
					DOFormModel.OUTGRID_TOP, null, "noEnter");

			geneControlButtonForm(null, null, gridM, "返回", "BACK",
					formReturn, 1015, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1020, null,
					DOFormModel.OUTGRID_BOTTOM, null, "noEnter");

			geneControlButtonForm(linkService, pane, gridM, "保存并新建",
					"Save&New", formServiceUf, 1025, null,
					DOFormModel.OUTGRID_BOTTOM, null, null);

			geneControlButtonForm(null, null, gridM, "返回", "BACK",
					formReturn, 1035, null, DOFormModel.OUTGRID_BOTTOM,
					null, "noEnter");

		} else if (aName.endsWith("_dulplicate")) {
			DOService linkService = DOService.getService(aService.getBo()
					.getName() + "_insert");

			DOPaneModel controlMain = DOPaneModel.getPaneModelByName("PM_"
					+ aService.getBo().getName() + "_control_main");

			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1000, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

			geneControlButtonForm(null, null, gridM, "返回", "BACK",
					formReturn, 1015, null, DOFormModel.OUTGRID_TOP,
					null, "noEnter");

			geneControlButtonForm(linkService, controlMain, gridM, "保存",
					"Save", formServiceUf, 1020, null,
					DOFormModel.OUTGRID_BOTTOM, null, null);

			geneControlButtonForm(null, null, gridM, "返回", "BACK",
					formReturn, 1025, null, DOFormModel.OUTGRID_BOTTOM,
					null, "noEnter");
		}

		return gridM;
	}

	static DOFormModel geneControlButtonForm(DOService linkService,
			DOPaneModel paneModel, DOGridModel gridM, String chL10n,
			String enL10n, DOController cc, int orderNum, String confirmJs,
			int position, DOPaneModel targetPaneModel, String noEnter)
			throws ExedoException {

		DOFormModel formM = new DOFormModel();
		if ("en".equals(DOGlobals.getValue("lang.local"))) {
			formM.setL10n(enL10n);
		} else {
			formM.setL10n(chL10n);
		}

		formM.setIsOutGridAction(position);

		if (paneModel != null) {
			formM.setLinkPaneModel(paneModel);
		}

		if (targetPaneModel != null) {
			formM.setTargetPaneModel(targetPaneModel);
		}

		if (linkService != null) {
			formM.setLinkService(linkService);
		}

		if (confirmJs != null) {
			formM.setEchoJs(confirmJs);
		}

		if (noEnter != null) {
			formM.setInputConstraint(noEnter);
		}

		formM.setGridModel(gridM);
		formM.setOrderNum(orderNum);
		formM.setController(cc);

		DAOUtil.INSTANCE().store(formM);

		return formM;

	}

	public void setGeneType(String aGeneType) {
		this.geneType = aGeneType;
	}

	public static void main(String[] args) {

		System.out.println(gridFormTable);

		System.out.println(contentPane);

	}

}
