package com.exedosoft.plat.ui.bootstrap.form;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.template.HtmlTemplateGenerator;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOIViewTemplate;
import com.exedosoft.plat.ui.jquery.form.DOValueResultList;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DOResultListPopup extends DOValueResultList implements
		DOIViewTemplate {

	private static Log log = LogFactory.getLog(DOResultListPopup.class);

	public DOResultListPopup() {
		dealTemplatePath("/form/DOResultListPopup.ftl");
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

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = new HashMap<String, Object>();
		DOFormModel property = (DOFormModel) doimodel;
		data.put("model", doimodel);
		data.put("contextPath", DOGlobals.PRE_FULL_FOLDER);
		data.put("webmodule", DOGlobals.URL);
		data.put("validRules", this.appendValidateConfig(property));
		
		Random rd = new Random();
		data.put("randomid", property.getFullColID() + rd.nextInt(10000));

		BOInstance biData = property.getData();
		BOInstance biForm = DOGlobals.getInstance().getSessoinContext().getFormInstance();
		if(biForm!=null && biForm.getValue("_conParas")!=null){
			if(biData==null){
				biData = biForm;
				property.setData(biForm);
			}
		}
		
		if (biData != null) {

			String theValue = property.getValue();
			data.put("thevalue", theValue);

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
		

		
		
		DOService linkService = property.getLinkService();

		if (linkService != null) {
			
			data.put("searchColName",linkService.getBo().getValueCol());
			int cnt = linkService.getResultSize();
			if(cnt > 10){
				data.put("splitpage", "true");
//				List results = linkService.invokeSelect(1,20);
				int resultSize = linkService.getResultSize();
				int pageSize = StringUtil.getPageSize(resultSize, 10);
				data.put("pageSize", pageSize);
//				data.put("results", results);

			}
//			else{
//			
//			List results = linkService.invokeSelect();
//			data.put("results", results);
//			}
		}

		return data;
	}
	
	public static void main(String[] args){
		
		Random rd = new Random();
		
		System.out.println("Random:::" + rd.nextInt(10000));
		
		
		
	}

}
