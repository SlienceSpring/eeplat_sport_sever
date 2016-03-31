package com.exedosoft.plat.ui.customize.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.jquery.grid.GridList;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.RestUtil;

/**
 * @author aa
 */
public class GridExtendConfig extends GridList {

	private static Log log = LogFactory.getLog(GridExtendConfig.class);

	public GridExtendConfig() {

		dealTemplatePath("/customize/tools/GridExtendConfig.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		// DOGridModel gm = (DOGridModel) doimodel;

		String tablename = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("tablename");

		String gridModelUid = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance().getValue("gridModelUid");

		DOGridModel gm = DOGridModel.getGridModelByID(gridModelUid);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", gm);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);

		List showForms = null;

		try {
			DOPaneModel bottom = (DOPaneModel) gm.getContainerPane()
					.retrieveChildren().get(1);
			showForms = bottom.getGridModel().getAllGridFormLinks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		if (showForms == null) {
			DOGridModel resultGrid = DOGridModel.getGridModelByName("GM_"
					+ tablename + "_list");

			showForms = resultGrid.getAllGridFormLinks();
		}

		List normalForms = gm.getNormalGridFormLinks();

		List formTypes = new ArrayList();
		for (Iterator it = normalForms.iterator(); it.hasNext();) {

			DOFormModel aForm = (DOFormModel) it.next();

			if (aForm.getRelationProperty() == null) {
				continue;
			}

			Map ft = new HashMap();
			ft.put("formModel", aForm);

			if (aForm.getController() != null
					&& aForm.getController().getCategory() != null
					&& aForm.getController().getCategory().getName()
							.equals("c_form_list")) {
				ft.put("type", "list");
			} else if (aForm.getRelationProperty().isDateOrTimeType()) {
				ft.put("type", "date");
			} else if (aForm.getRelationProperty().isNumberType()) {
				ft.put("type", "number");
			} else {
				ft.put("type", "varchar");
			}
			formTypes.add(ft);

		}

		data.put("formTypes", formTypes);

		data.put("showForms", showForms);

		// /////////////修改的情况

		BOInstance form = DOGlobals.getInstance().getSessoinContext()
				.getFormInstance();
		String selectedPml = form.getValue("selectedPml");
		String method = form.getValue("method");

		if (selectedPml != null) {

			DOPaneModel selectedPane = DOPaneModel
					.getPaneModelByName(selectedPml);
			String configJson = selectedPane.getGridModel().getService()
					.getExedoc();
			data.put("configJson", configJson);
			data.put("selectedPml", selectedPml);
			data.put("method", method);
		}

		return data;
	}

	public List<BOInstance> getList(String callSelect) {
		JSONArray jsonArray = null;
		List<BOInstance> listData = new ArrayList<BOInstance>();
		try {
			StringBuffer buffer = RestUtil.call(callSelect);
			// System.out.println("BUFFER::::" + buffer);
			jsonArray = new JSONArray(buffer.toString());
			System.out.println("jsonArray::::" + jsonArray);
			if (jsonArray != null) {

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.getJSONObject(i);
					BOInstance bi = BOInstance.fromJSONObject(jo);
					listData.add(bi);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listData;
	}
}
