package com.exedosoft.plat.ui.weui.form;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;


public class TInputText   extends DOBaseForm  {

	private static Log log = LogFactory.getLog(TInputText.class);
	
	public TInputText(){
		dealTemplatePath("/form/TInputText.ftl");
	}
	
	
	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);
		DOFormModel fm = (DOFormModel)doimodel;
		String validStr = this.appendValidateConfig(fm,false,true);
		
		data.put("validStr", validStr);
		
		StringBuffer buffer = new StringBuffer();
		appendHtmlJs(buffer, fm);
		data.put("htmlJsStr", buffer.toString());
				
		return data;
	}

}
