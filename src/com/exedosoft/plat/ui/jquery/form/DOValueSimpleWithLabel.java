package com.exedosoft.plat.ui.jquery.form;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIView;

public class DOValueSimpleWithLabel extends DOBaseForm implements DOIView {

	public DOValueSimpleWithLabel() {
		super();
	}

	public String getHtmlCode(DOIModel aModel) {

		if (isUsingTemplate) {
			return super.getHtmlCode(aModel);
		}

		DOFormModel fm = (DOFormModel) aModel;
		String value = fm.getValue();

		if (value != null && !value.trim().equals("")) {

			if (fm.getStyle() != null && !"".equals(fm.getStyle())) {
				// return "<span style='"+fm.getStyle()+"'>"+value+"<";;
				StringBuffer sb = new StringBuffer();
				sb.append("<span style='").append(fm.getStyle()).append("'>")
						.append(value).append("</span>");
				if(fm.getNote()!=null){
					sb.append(fm.getNote());
				}
				return sb.toString();
			}
			if(fm.getNote()!=null){
				value = value + fm.getNote();
			}
			return value;
		} else {
			return "&nbsp;";
		}
	}
}
