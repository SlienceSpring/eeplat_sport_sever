package com.exedosoft.plat.ui.jquery.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOViewTemplate;

public class RightMenu extends DOViewTemplate {

	public RightMenu() {
		dealTemplatePath("/menu/RightMenu.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> map = super.putData(doimodel);
		DOMenuModel rootMenu = (DOMenuModel) doimodel;

		List<DOMenuModel> listMenu = rootMenu.retrieveChildren();
		map.put("menuitems",listMenu);

		// //连接的内容有 内部面板，外部面板，服务。都可以。（暂时不考虑服务，只考虑外部和内部面板）
		// / 前台可以判断外部、内部面板，通过dom查询，是否在页面中有，有的话，转向锚，否则打开新的面板。

		List data = new ArrayList();
		for (DOMenuModel dmm : listMenu) {
			Map oneItem = new HashMap();
			oneItem.put("l10n", dmm.getL10n());
			oneItem.put("name", dmm.getName());
			if (dmm.getLinkPane() != null) {
				oneItem.put("paneid", dmm.getLinkPane().getObjUid());
				oneItem.put("panename", dmm.getLinkPane().getName());
				if (dmm.getLinkPane().getGridModel() != null
						&& dmm.getLinkPane().getGridModel().getService() != null) {
					if (dmm.getLinkPane().getGridModel().getService()
							.isSelect()) {
						int cnt = dmm.getLinkPane().getGridModel().getService()
								.getResultSize();
						oneItem.put("size", cnt);

					}

				}

			} 
			data.add(oneItem);
		}
		map.put("data", data);

		return map;
	}

}
