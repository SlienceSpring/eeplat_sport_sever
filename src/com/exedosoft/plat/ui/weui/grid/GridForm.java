package com.exedosoft.plat.ui.weui.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.SSOController;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

public class GridForm extends DOViewTemplate {

	private static Log log = LogFactory.getLog(GridForm.class);

	public GridForm() {

		dealTemplatePath("/grid/GridForm.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		List list = new ArrayList();

		DOGridModel gm = (DOGridModel) doimodel;

		DOService theService = gm.getService();
		if (theService != null) {
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
		data.put("mapForms",  gm.getAllCategoryForms() );
	
		return data;
	}

	public List<BOInstance> getListData(DOGridModel gridModel, Map<String, Object> data) {
		List<BOInstance> list;
		int pageNo = 1;
		int pageNum = 0;

		boolean isDirectLink = true;

		if (gridModel.getContainerPane() != null) {
			if (DOGlobals.getInstance().getSessoinContext().getFormInstance() != null
					&& !gridModel.getContainerPane().getObjUid().equals(
							DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("panemodeluid"))) {
				isDirectLink = false;

			}

		}

		if (DOGlobals.getInstance().getSessoinContext().getFormInstance() != null
				&& DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("pageNo") != null) {
			try {
				pageNo = Integer
						.parseInt(DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("pageNo"));
			} catch (Exception e) {

			}
			String pagiPml = DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("pagiPml");
			if (pagiPml != null) {
				if (!gridModel.getContainerPane().getName().equalsIgnoreCase(pagiPml)) {
					pageNo = 1;
				}
			}

		}
		// pageNo = DOGlobals.getInstance().getSessoinContext().splitPageContext
		// .getPageNo(gridModel.getService());
		// log.info("SplitPage Filter Table Get PageNO:::" + pageNo);

		if (gridModel.getRowSize() != null) {
			pageNum = gridModel.getRowSize().intValue();
		}
		if (pageNum <= 0) {

			if ("0".equals(gridModel.getTemplate()) && !isDirectLink) {
				list = new ArrayList();
			} else {
				list = gridModel.getService().invokeSelect();
			}
		} else {
			data.put("rowSize", pageNum);
			int resultSize = gridModel.getService().getResultSize();
			int pageSize = StringUtil.getPageSize(resultSize, pageNum);
			data.put("pageSize", pageSize);
			data.put("resultSize", resultSize);
			data.put("pageNo", pageNo);

			if ("0".equals(gridModel.getTemplate()) && !isDirectLink) {
				list = new ArrayList();
			} else {
				list = gridModel.getService().invokeSelect(pageNo, pageNum);
			}
		}

		// ///处理第二服务（统计用）
		DOService secondService = gridModel.getSecondService();
		if (secondService != null) {
			List secondResult = secondService.invokeSelect();
			if (secondResult != null && secondResult.size() > 0) {
				BOInstance statistics = (BOInstance) secondResult.get(0);
				data.put("statistics", statistics.getMap());
				StringBuilder sb = new StringBuilder();
				List<DOFormModel> listFm = gridModel.getStatisticOutGridFormLinks();
				if (listFm != null && listFm.size() > 0) {
					for (Iterator<DOFormModel> it = listFm.iterator(); it.hasNext();) {
						DOFormModel aFm = it.next();
						aFm.setData(statistics);
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(aFm.getL10n()).append("：").append(aFm.getValue())
								.append("&nbsp;&nbsp;&nbsp;&nbsp;");
					}
				}
				data.put("statistics_details", sb.toString());

			}
		}

		return list;
	}

}
