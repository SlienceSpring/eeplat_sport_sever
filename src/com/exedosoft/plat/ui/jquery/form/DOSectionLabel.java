package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;

public class DOSectionLabel extends DOBaseForm {
	
	public DOSectionLabel(){
		super();
	}


	public String getHtmlCode(DOIModel aModel) {
		
		if(isUsingTemplate){
			return super.getHtmlCode(aModel);
		}

		DOFormModel fm = (DOFormModel) aModel;

		StringBuffer buffer = new StringBuffer();
		String note = fm.getNote();
		if (note == null) {
			note = fm.getL10n();
		}

		buffer.append("<span style='font-weight:bold'>");
		buffer.append(note);
		buffer.append("</span>");



		return buffer.toString();
	}

}
