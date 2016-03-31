package com.exedosoft.plat.ui.weui.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;
import com.exedosoft.plat.ui.jquery.form.DOStaticList;
import com.exedosoft.plat.util.StringUtil;

public class TCheckBox extends DOBaseForm {

	private static Log log = LogFactory.getLog(TCheckBox.class);

	public TCheckBox() {
		dealTemplatePath("/form/TCheckBox.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		DOFormModel property = (DOFormModel) doimodel;
		Map<String, Object> data = super.putData(property);


		List listData = new ArrayList();
		if (property.getLinkService() != null) {
			
			for (Iterator it = property.getLinkService().invokeSelect().iterator(); it.hasNext();) {

				BOInstance instance = (BOInstance) it.next();
				ItemValue iv = new ItemValue();
				iv.setValue(instance.getUid());
				iv.setLabel(instance.getName());
				if (DOStaticList.isChecked(instance.getUid(), property.getValue())) {
					iv.setChecked(true);
				}
				listData.add(iv);
			}
		} else if (property.getInputConfig() != null) {

			List list = StringUtil.getStaticList(property.getInputConfig());
			for (Iterator it = list.iterator(); it.hasNext();) {
				String[] half = (String[]) it.next();
				ItemValue iv = new ItemValue();
				iv.setValue(half[0]);
				iv.setLabel(half[1]);
				if (DOStaticList.isChecked(half[0], property.getValue())) {
					iv.setChecked(true);
				}
				listData.add(iv);
			}
		}
		data.put("listData", listData);
		
		String validStr = this.appendValidateConfig(property,true,true);
		
		data.put("validStr", validStr);
		
		StringBuffer buffer = new StringBuffer();
		appendHtmlJs(buffer, property);
		data.put("htmlJsStr", buffer.toString());
		
		return data;

	
	}


}
