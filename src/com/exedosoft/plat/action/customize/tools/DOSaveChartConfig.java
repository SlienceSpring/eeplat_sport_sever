package com.exedosoft.plat.action.customize.tools;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOController;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.bootstrap.grid.GridSmallBox;
import com.exedosoft.plat.ui.jquery.grid.echarts.Chart;
import com.exedosoft.plat.ui.jquery.pane.ContentPane;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.Escape;
import com.exedosoft.plat.util.I18n;

/**
 * 
 * 保存扩展视图
 * 
 */

public class DOSaveChartConfig extends DOAbstractAction {

	private static final long serialVersionUID = -7581994809740062108L;

	private static Log log = LogFactory.getLog(DOSaveChartConfig.class);

	private static DOController contentPane = DOController
			.getControllerByClassName(ContentPane.class.getName());

	private static DOController echart = DOController
			.getControllerByClassName(Chart.class.getName());

	private static DOController smallBox = DOController
			.getControllerByClassName(GridSmallBox.class.getName());

	public DOSaveChartConfig() {
	}

	public String excute() {

		BOInstance uiForm = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();
		log.info("Form:::::::" + uiForm);

		DOBO bo_ = DOBO.getDOBOByName("do_bo");
		DOBO bo = DOBO.getDOBOByID(bo_.getCorrInstance().getUid());

		Transaction t = bo.getDataBase().getTransaction();
		try {
			String paras = Escape.unescape(uiForm.getValue("paras"));
			JSONObject chartConfig = new JSONObject(paras);

			log.info("chartConfig:::::::" + chartConfig);

			String name = chartConfig.getString("name");
			String l10n = chartConfig.getString("l10n");
			String serviceUid = chartConfig.getString("serviceUid");

			String selectedGridName = null;
			if (chartConfig.has("gridModelName")) {
				selectedGridName = "Chart_" + bo.getName() + "_"  + chartConfig.getString("gridModelName");
			}

			String charttype = chartConfig.getString("chartType");

			t.begin();

			DOService service = DOService.getServiceByID(serviceUid);

			String paneName = "PM_chart_" + bo.getName() + "_" + name;
			String paneL10n = "PM_chart_" + bo.getName() + "_" + l10n;
			String gridName = "Chart_" + bo.getName() + "_" + name;
			String gridL10n = "Chart_" + bo.getName() + "_" + l10n;

			// //gridName是录入的表格名称，selectedGridName是选择的表格名称
			DOGridModel theOldGM = DOGridModel.getGridModelByName(gridName);

			if (theOldGM != null && !gridName.equals(selectedGridName)) {

				BOInstance backInstance = new BOInstance();
				backInstance.putValue("error_str", "名称不能重复！");
				this.setEchoValue("名称不能重复！");
				this.setInstance(backInstance);
				return this.NO_FORWARD;
			}

			DOGridModel gm = null;
			DOPaneModel pm = null;
			if (selectedGridName != null && !selectedGridName.equals("")) {
				gm = DOGridModel.getGridModelByName(selectedGridName);
				pm = DOPaneModel.getPaneModelByName("PM_c"
						+ selectedGridName.substring(1));
				System.out.println("==============================================================");
				System.out.println("==============================================================");
				System.out.println("==============================================================");
				System.out.println("==============================================================");
				System.out.println("==============================================================");
				System.out.println("================::" + pm);
				
			}
			if (gm == null) {
				gm = new DOGridModel();
				gm.setGridType(DOGridModel.GRID_TYPE_CHART);
				gm.setCategory(bo);
			}
			gm.setName(gridName);
			gm.setL10n(gridL10n);
			gm.setCaption(l10n);
			gm.setService(service);
			gm.setExeDoc(paras);

			if ("box".equals(charttype)) {
				gm.setController(smallBox);
			} else {
				gm.setController(echart);
			}
			gm = DAOUtil.INSTANCE().store(gm);

			if (pm == null) {
				pm = new DOPaneModel();
				pm.setLinkType(DOPaneModel.LINKTYPE_GRIDMODEL);
				pm.setLinkUID(gm.getObjUid());
				pm.setCategory(bo);
				pm.setController(contentPane);
			}
			pm.setName(paneName);
			pm.setL10n(paneL10n);
			pm.setTitle(l10n);
			DAOUtil.INSTANCE().store(pm);

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
				+ StringEscapeUtils.escapeSql("like '%1%' or (1=1)"));

	}

}
