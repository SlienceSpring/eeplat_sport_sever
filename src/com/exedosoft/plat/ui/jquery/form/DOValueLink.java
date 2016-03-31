package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIView;

public class DOValueLink extends DOBaseForm  implements DOIView {
	
	
	public DOValueLink(){
		super();
	}


	public String getHtmlCode(DOIModel aModel) {
		
		if(isUsingTemplate){
			return super.getHtmlCode(aModel);
		}

		DOFormModel property = (DOFormModel) aModel;
		StringBuffer buffer = new StringBuffer();
		// //////////对picture类型的特殊处理
		
		
		if(property.getValue()==null){
			return "&nbsp;";
		}

		String[] oneLink = property.getValue().split(",");
		for (int i = 0; i < oneLink.length; i++) {
			
			String aLink = oneLink[i];
			if(aLink.indexOf("://")==-1){
				aLink = "http://" + aLink; 
			}
			
			buffer.append("<a target='_blank' class='exedo_link' href='").append(aLink).append(
					"'>").append(oneLink[i]).append("</a>");
			if (i < oneLink.length - 1) {
				buffer.append(",&nbsp;");
			}
		}

		return buffer.toString();

	}

	

}
