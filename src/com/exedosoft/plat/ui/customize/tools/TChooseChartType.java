package com.exedosoft.plat.ui.customize.tools;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;

public class TChooseChartType extends DOViewTemplate {

	private static Log log = LogFactory.getLog(TChooseChartType.class);
	
	public TChooseChartType(){
		
		dealTemplatePath( "/customize/tools/TChooseChartType.ftl");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);

		DOFormModel fModel = (DOFormModel) doimodel;
		List<DOFormModel> allForms = fModel.getLinkForms();
		
		
		System.out.println("selected gridModel:::" +fModel.getData());

		DOFormModel xaxiscol = null;
		DOFormModel yaxiscol = null;
		DOFormModel categorycol = null;
		DOFormModel linkPane = null;
		DOFormModel bgcolor = null;
		DOFormModel boxicon = null;

		
		for (DOFormModel aFm : allForms) {

			if (aFm.getRename().equals("xaxiscol")) {
				xaxiscol = aFm;
			} else if (aFm.getRename().equals("yaxiscol")) {
				yaxiscol = aFm;
			} else if (aFm.getRename().equals("categorycol")) {
				categorycol = aFm;
			} else if (aFm.getRename().equals("linkPane")) {
				linkPane = aFm;
			} else if (aFm.getRename().equals("bgcolor")) {
				bgcolor = aFm;
			} else if (aFm.getRename().equals("boxicon")) {
				boxicon = aFm;
			}
		}
		
		data.put("xaxiscol", xaxiscol);
		data.put("yaxiscol", yaxiscol);
		data.put("categorycol", categorycol);
		data.put("linkPane", linkPane);
		data.put("bgcolor", bgcolor);
		data.put("boxicon", boxicon);
		
		BOInstance biData = fModel.getData();
		if(biData!=null){
			String jsonData = biData.getValue("exedoc");
			try {
				JSONObject chartConfig = new JSONObject(jsonData);
				
				data.put("gridModelName", biData.getValue("name"));
				
				if(!chartConfig.getString("xaxisCol").equals("")){
					DOBOProperty propery = DOBOProperty.getDOBOPropertyByID(chartConfig.getString("xaxisCol"));
					if(propery!=null){
						data.put("xaxisCol_show", propery.getL10n());
					}
				}

				if(!chartConfig.getString("yaxisCol").equals("")){
					DOBOProperty propery = DOBOProperty.getDOBOPropertyByID(chartConfig.getString("yaxisCol"));
					if(propery!=null){
						data.put("yaxisCol_show", propery.getL10n());
					}
				}

				if(chartConfig.has("categoryCol") && !chartConfig.getString("categoryCol").equals("")){
					DOBOProperty propery = DOBOProperty.getDOBOPropertyByID(chartConfig.getString("categoryCol"));
					if(propery!=null){
						data.put("categoryCol_show", propery.getL10n());
					}
				}

				if(chartConfig.has("linkPaneId") && !chartConfig.getString("linkPaneId").equals("")){
					DOPaneModel aPm = DOPaneModel.getPaneModelByID(chartConfig.getString("linkPaneId"));
					if(aPm!=null){
						data.put("linkPane_show", aPm.getL10n());
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

					
			data.put("jsonData", jsonData);
			

		}

		return data;

	}



}
