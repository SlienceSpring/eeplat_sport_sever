package com.exedosoft.plat.ui.weui.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

public class TValueCellHd extends DOViewTemplate {

	private static Log log = LogFactory.getLog(TValueCellHd.class);

	public TValueCellHd() {
		dealTemplatePath("/form/TValueCellHd.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = new HashMap<String, Object>();
		DOFormModel property = (DOFormModel) doimodel;
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);

		String defaultIcon = "<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=' alt='icon'  style='width:20px;margin-right:5px;display:block' />";

		BOInstance biData = property.getData();

		if (biData.getBo() != null) {

			if (property.getValue() != null && !property.getValue().equals("")) {
				defaultIcon = defaultIcon = "<img src='" + property.getValue()
						+ "' alt='icon'  style='width:20px;margin-right:5px;display:block;color:black' />";
			} else {
				defaultIcon = appendBOIcon(defaultIcon, biData);
			}
		}
		data.put("itemIcon", defaultIcon);

		return data;
	}

	private String appendBOIcon(String defaultIcon, BOInstance biData) {
		if (biData.getBo().getIcon() != null && !biData.getBo().getIcon().trim().equals("")) {
			if (biData.getBo().getIcon().indexOf("fa") != -1) {
				String theIcon = StringUtil.getFaIcon(biData.getBo().getIcon());
				defaultIcon = "<i style='width:20px;margin-right:5px;display:block;color:black' class='" + theIcon + "'></i>";
			}
		}
		return defaultIcon;
	}

}
