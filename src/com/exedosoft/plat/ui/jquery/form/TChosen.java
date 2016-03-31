package com.exedosoft.plat.ui.jquery.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.SSOController;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.template.HtmlTemplateGenerator;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIViewTemplate;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.util.DOGlobals;

/**
 * 在传统模式下不再使用，和jquery以及jquery ui版本匹配太难。 只在bootstrap 模式下使用。
 * 
 * @author aa
 * 
 */
public class TChosen extends DOValueResultList implements DOIViewTemplate {

	private static Log log = LogFactory.getLog(TChosen.class);

	public TChosen() {
		dealTemplatePath("/form/TChosen.ftl");

	}

	public String getHtmlCode(DOIModel doimodel) {

		Map<String, Object> data = this.putData(doimodel);

		String s = "";
		try {
			s = HtmlTemplateGenerator.getContentFromTemplate(this.templateFile,
					data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * 获取动态列表形式的Select Form
	 * 
	 * @param property
	 *            TODO
	 * @param db
	 * @return
	 */
	String getDynaListForm(DOFormModel property) {

		List halfs = new ArrayList();
		for (Iterator it = property.getLinkService().invokeSelect().iterator(); it
				.hasNext();) {
			BOInstance svo = (BOInstance) it.next();
			String[] half = new String[2];
			half[0] = (String) svo.getUid();
			half[1] = (String) svo.getName();

			halfs.add(half);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(formSelectStr(property, halfs));

		return buffer.toString();
	}

	public String formSelectStr(DOFormModel property, List list) {

		StringBuffer buffer = new StringBuffer();

		String value = property.getValue();

		buffer.append("<select  data-placeholder='请选择...' class='resultlistpopup combox' style='");
		if (property.getStyle() != null) {
			if ("100".equals(property.getStyle())) {
				buffer.append(" ");
			} else {
				buffer.append(property.getStyle());
			}
		} else {
			try {
				if (!SSOController.isMobile()) {
					buffer.append("width:100px");
				}
			} catch (Exception e) {
				buffer.append("width:100px");
			}

		}
		buffer.append("'");
		if (property.getInputConstraint() != null
				&& property.getInputConstraint().startsWith("@multi@")) {
			buffer.append(" size='10' multiple='multiple' ");
		}

		buffer.append(" id=\"").append(property.getFullColID()).append("\" ");

		buffer.append(" name=\"").append(property.getFullColName())
				.append("\" ");

		buffer.append(" title='").append(property.getL10n().trim()).append("'");

		if (isReadOnly(property)) {
			buffer.append(" disabled=\"disabled\" ");
		}

		appendHtmlJs(buffer, property);

		buffer.append(this.appendValidateConfig(property, true));

		buffer.append(getDecoration(property));

		buffer.append(" >\n");

		buffer.append("<option/>\n");

		if (list != null) {
			boolean isFirst = true;
			String defaultValue = getDefaultListValue(property);
			for (Iterator it = list.iterator(); it.hasNext();) {
				String[] half = (String[]) it.next();
				buffer.append("<option value=\"").append(half[0]);
				buffer.append("\"");
				if (isFirst) {
					if (value == null
							&& defaultValue == null
							&& (property.getDefaultValue() != null && !property
									.getDefaultValue().trim().equals(""))) {
						buffer.append(" selected=\"selected\"  ");
					}
					isFirst = false;
				}

				if (value != null) { // ////////修改的情况

					// ////////////////////add by weikx at 20070806
					// 只要修改的情况输出标签就可以了
					DOPaneModel cPaneModel = null;
					if (property.getGridModel() != null) {
						cPaneModel = property.getGridModel().getContainerPane();
					}

					if (DOStaticList.isChecked(half[0], value)) {
						buffer.append(" selected=\"selected\"  ");
					}

					// ////////////end add by weikx at 20070806

				} else { // //////添加的情况

					if (defaultValue != null && defaultValue.equals(half[0])) {
						buffer.append(" selected=\"selected\"  ");
					}

				}
				buffer.append(">");
				buffer.append(half[1]);
				buffer.append("</option>\n");
			}
		}
		buffer.append("</select><span/>");

//		if (property.isNotNull()) {
//			buffer.append("&nbsp;<font color='red'>*</font>");
//		}

		if (property.getNote() != null && !"".equals(property.getNote())) {
			if (property.getStyle() != null && !"".equals(property.getStyle())) {
				buffer.append("&nbsp;&nbsp;&nbsp;<span style=\"")
						.append(property.getStyle()).append("\">")
						.append(property.getNote()).append("</span>");
			} else {
				buffer.append(property.getNote());
			}
		}

		/////特殊的onchange
		if (property.getOnChangeJs() != null
				&& !"".equals(property.getOnChangeJs())) {
			buffer.append("<script>");

			buffer.append(" $('#").append(property.getFullColID())
					.append("').change(function(){")
					.append(property.getOnChangeJs()).append("});");

			buffer.append("</script>");

		}

		return buffer.toString();
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = new HashMap<String, Object>();
		DOFormModel property = (DOFormModel) doimodel;
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);
		data.put("validRules", this.appendValidateConfig(property));

		String theHtmlCode = this.getDynaListForm(property);

		data.put("htmlcode", theHtmlCode);

		if (property.getData() != null) {

			String theValue = property.getValue();

			if (theValue != null) {
				DOBO corrBO = property.getLinkBO();

				if (corrBO == null && property.getLinkService() != null) {
					corrBO = property.getLinkService().getBo();
				}
				BOInstance bi = getAInstance(property, corrBO, theValue);
				if (bi != null) {
					data.put("label", bi.getName());
				}
			}
		}
		return data;
	}

	/**
	 * 静态下拉列表中,缺省的值
	 * 
	 * @param property
	 *            TODO
	 * @return
	 */
	protected String getDefaultListValue(DOFormModel property) {

		if (property.getInputConfig() != null) {
			if (property.getInputConfig().indexOf("@") != -1) {
				return property.getInputConfig().substring(
						property.getInputConfig().indexOf("@") + 1);
			}
		}
		return null;
	}

}
