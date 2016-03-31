package com.exedosoft.plat.ui.weui.form;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;
import com.exedosoft.plat.util.StringUtil;


public class TDateTime  extends DOBaseForm {

	private static Log log = LogFactory.getLog(TDateTime.class);
	
	public TDateTime(){
		dealTemplatePath("/form/TDateTime.ftl");
	}
	
	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);
		DOFormModel fm = (DOFormModel)doimodel;
		String validStr = this.appendValidateConfig(fm,false,true);
		
		data.put("validStr", validStr);
		
		StringBuffer buffer = new StringBuffer();
		appendHtmlJs(buffer, fm);
		data.put("htmlJsStr", buffer.toString());

		String dateValue = "";
		if(fm.getValue()!=null){
			dateValue =	StringUtil
				.getDateStr(
						fm.getData().getDateValue(
								fm.getRelationProperty()),
						"yyyy-MM-dd HH:mm");
		}
		data.put("dateValue", dateValue.replace(" ", "T"));
		return data;
	}


}
