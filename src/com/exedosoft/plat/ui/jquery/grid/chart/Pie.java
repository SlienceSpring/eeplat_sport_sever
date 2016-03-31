package com.exedosoft.plat.ui.jquery.grid.chart;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.StringUtil;

/**
 * @author aa
 */
public class Pie extends DOViewTemplate {

	private static Log log = LogFactory.getLog(Pie.class);

	public Pie() {

		dealTemplatePath("/grid/chart/Pie.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		DOGridModel gm = (DOGridModel) doimodel;
		if (gm.getService() == null) {
			return null;
		}
		Map<String, Object> data = super.putData(doimodel);
		
		if(gm.getService()!=null){
			
			String note = gm.getService().getNote();
			String keyCol = "name";
			String valueCol = "value";
			
			List forms = gm.getAllGridFormLinks();
			
			List configs = null;
			if(forms!=null && forms.size() >= 2){
				DOFormModel fm1 = (DOFormModel)forms.get(0);
				keyCol = fm1.getColName();
				if(fm1.getInputConfig()!=null){
					configs = StringUtil.getStaticList(fm1.getInputConfig());
				}
				DOFormModel fm2 = (DOFormModel)forms.get(1);
				valueCol = fm2.getColName();
			}
			else if (note != null && note.indexOf(",") != -1) {
				String[] cols = note.split(",");
				keyCol = cols[0];
				valueCol = cols[1];
			}
		
			List<BOInstance> list = gm.getService().invokeSelect();
			StringBuffer  str = new StringBuffer( "<graph  baseFontSize='12' caption=' ")
			.append(gm.getCaption())
			.append("' showhovercap='1' showNames='1'   formatNumber='0' formatNumberScale='0' >");
			for(BOInstance anIns:list){
				
				String labelName = anIns.getValue(keyCol);
				if(configs!=null){
					labelName = StringUtil.getValueByKey(configs, labelName);
				}
				
			    
			    str.append("<set name='")
			    .append( labelName )
			    .append("' value='")
			    .append(anIns.getValue(valueCol))
			    .append( "' />");
			    
			}
			str.append( "</graph>" );
			data.put("gXml",str);
		}
		
		if (gm.getContainerPane() != null) {
			data.put("pmlName", gm.getContainerPane().getName());
		}
		return data;
	}


}
