package com.exedosoft.plat.ui.jquery.pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;

public class Tab extends DOViewTemplate {

	public Tab() {

		dealTemplatePath("/panel/TabPanel.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		DOPaneModel pm = (DOPaneModel) doimodel;
		List<DOPaneModel> items = pm.retrieveChildren();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("model", pm);

		List dataitems = new ArrayList();
		for (DOPaneModel pmChild : items) {
			Map oneItem = new HashMap();
			oneItem.put("l10n", pmChild.getL10n());
			oneItem.put("name", pmChild.getName());
			oneItem.put("title", pmChild.getTitle());
			oneItem.put("fullCorrHref", pmChild.getFullCorrHref());

			if (pmChild != null) {
				oneItem.put("paneid", pmChild.getObjUid());

				if ("number".equals(pm.getNote())) {

					if (pmChild.getGridModel() != null
							&& pmChild.getGridModel().getService() != null) {
						if (pmChild.getGridModel().getService().isSelect()) {
							int cnt = pmChild.getGridModel().getService()
									.getResultSize();
							oneItem.put("size", cnt);

						}
					} else if (pmChild.retrieveChildren() != null
							&& pmChild.retrieveChildren().size() > 1) {
						DOPaneModel resultModel = (DOPaneModel) pmChild
								.retrieveChildren().get(1);

						if (resultModel.getGridModel().getService().isSelect()) {
							int cnt = resultModel.getGridModel().getService()
									.getResultSize();
							oneItem.put("size", cnt);

						}
					}
				}

			}
			dataitems.add(oneItem);
		}

		data.put("items", dataitems);

		return data;
	}

}
