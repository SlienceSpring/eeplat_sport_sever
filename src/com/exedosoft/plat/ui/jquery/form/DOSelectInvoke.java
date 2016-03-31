package com.exedosoft.plat.ui.jquery.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.util.DOGlobals;

public class DOSelectInvoke extends DOBaseForm {

	public DOSelectInvoke() {
		super();
	}

	public String getHtmlCode(DOIModel iModel) {

		if (isUsingTemplate) {
			return super.getHtmlCode(iModel);
		}

		DOFormModel property = (DOFormModel) iModel;

		StringBuffer buffer = new StringBuffer();

		buffer.append("<div class='form-inline'><input name=\"")
				.append(property.getColName()).append("\" type=\"hidden\"")
				.append(" id=\"").append(property.getFullColID()).append("\" ");

		
		if (property.getValue() != null
				&& !property.getValue().trim().equals("")) {

			buffer.append(" value=\"").append(property.getValue()).append("\"");
		}

		buffer.append(" />");

		// onkeydown
		buffer.append(
				"<input style='min-width:250px;max-width:350px;' class='form-control' name=\"")
				.append(property.getColName()).append("_show\" type=\"text\"")
				.append(" id=\"").append(property.getFullColID())
				.append("_show\"")
		// .append(" onkeydown=\"")
		// .append("recieveKeyValue('")
		// .append(property.getFullColName())
		// .append("',this.value)\"")
		;

		buffer.append(this.appendValidateConfig(property));

		if (property.getSecondService() != null && property.getData()!=null) {
			buffer.append(" value=\"");
			BOInstance bi = property.getSecondService().getInstance(property.getValue());
			buffer.append(bi.getName());
			buffer.append("\" ");
		}
		else if (property.getLinkService() != null && property.getData()!=null) {
			int i = 0;
			buffer.append(" value=\"");
			for (Iterator it = property.getLinkService()
					.invokeSelect(property.getData().getUid()).iterator(); it
					.hasNext();) {
				BOInstance bi = (BOInstance<?>) it.next();
				String theName = bi.getName();
				if (theName == null) {
					theName = bi.getValue("name");
				}
				buffer.append(theName);
				buffer.append(";");
				i++;
			}
			buffer.append("\" ");

		} else if (property.getValue() != null
				&& !property.getValue().trim().equals("")) {

			DOBO doBO = null;

			if (property.getLinkBO() != null) {
				doBO = property.getLinkBO();
			} else if (property.getLinkService() != null) {
				doBO = property.getLinkService().getBo();
			}

			if (doBO == null) {
				doBO = property.getDoBO();
			}

			BOInstance bi = DOValueResultList.getAInstance(property, doBO,
					property.getValue());

			if (bi != null) {
				buffer.append(" value=\"").append(bi.getName()).append("\"");
			}

		}

		buffer.append(" readonly=\"readonly\" ");

		// ////增加装饰
		buffer.append(getDecoration(property));

		appendHtmlJs(buffer, property);
		// ///////end 增加装饰
		buffer.append(" title='").append(property.getL10n()).append("'");

		buffer.append(" size=\"").append(getInputSize(property)).append("\"/>");

		String searchStr = "查找";
		if ("en".equals(DOGlobals.getValue("lang.local"))) {
			searchStr = "Search";
		}

		buffer.append(DOValueService.stardardSelectPane(property, searchStr));

		buffer.append("</div>");

		// if (property.isNotNull()) {
		// buffer.append("&nbsp;<font color='red'>*</font>&nbsp;");
		// }

		if (property.getOnChangeJs() != null
				&& !"".equals(property.getOnChangeJs())) {
			buffer.append("<script>");

			buffer.append("if($.browser.mozilla) $('#")
					.append(property.getFullColID())
					.append("')[0].addEventListener('DOMAttrModified',function(){")
					.append(property.getOnChangeJs()).append("},false);");

			buffer.append("</script>");

		}
		// buffer.append(DOValueService.stardardOnlyPane(property, "新增"));

		return buffer.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(null);
		System.out.println(list);

	}

}
