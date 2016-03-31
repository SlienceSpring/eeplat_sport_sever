package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;

public class DOValueStaticList extends DOStaticList {
	
	
	
	public DOValueStaticList(){
		super();
	}


	public String getHtmlCode(DOIModel aModel) {
		
		if(isUsingTemplate){
			return super.getHtmlCode(aModel);
		}

		DOFormModel property = (DOFormModel) aModel;
		String theValue = getStaticListValue(property);
		
		if(property.getStyle()!=null&&!"".equals(property.getStyle()))
		{
			//return "<span style='"+fm.getStyle()+"'>"+value+"<";;
			StringBuffer sb=new StringBuffer();
			sb.append("<span style='").append(property.getStyle()).append("'>").append(theValue).append("</span>");
			return sb.toString();
		}
		return theValue;
	}
}
