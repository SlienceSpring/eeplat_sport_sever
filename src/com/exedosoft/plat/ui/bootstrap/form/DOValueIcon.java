package com.exedosoft.plat.ui.bootstrap.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIView;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;

public class DOValueIcon extends DOBaseForm  implements DOIView {
	
	public DOValueIcon(){
		super();
	}
	

	public String getHtmlCode(DOIModel aModel) {
		
		
		if(isUsingTemplate){
			return super.getHtmlCode(aModel);
		}

		DOFormModel fm = (DOFormModel) aModel;
		String value = fm.getValue();
		

		if (value != null && !value.trim().equals("")) {
				StringBuffer sb=new StringBuffer();
				sb.append("<i class='").append(value).append("'>").append("</i>");
				return sb.toString();

		} else {
			return "&nbsp;";
		}
	}

}
