package com.exedosoft.plat.ui.bootstrap.grid;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.util.StringUtil;

/**
 * @author aa
 */
public class GridSmallBox extends GridList{

	private static Log log = LogFactory.getLog(GridSmallBox.class);
//有的控制器有特别接口约定，比如要求sql语句中的字段名称，这个可以有。
	public GridSmallBox() {

		dealTemplatePath( "/grid/GridSmallBox.ftl" );
	}
	
	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);

		DOGridModel gm = (DOGridModel) doimodel;
		if (gm.getService() == null) {
			return null;
		}

		try {
			JSONObject chartConfig = new JSONObject(gm.getExeDoc());

			String serviceUid = chartConfig.getString("serviceUid");
			String xaxisCol = chartConfig.getString("xaxisCol");
			String yaxisCol = chartConfig.getString("yaxisCol");
			String bgcolor = chartConfig.getString("bgcolor");
			String boxicon = chartConfig.getString("boxicon");
			String linkPaneId = chartConfig.getString("linkPaneId");


			DOBOProperty xAxis = DOBOProperty.getDOBOPropertyByID(xaxisCol);
			DOBOProperty yAxis = DOBOProperty.getDOBOPropertyByID(yaxisCol);
			DOPaneModel linkPane = DOPaneModel.getPaneModelByID(linkPaneId);
			
			DOService service = DOService.getServiceByID(serviceUid);



			if (service != null) {

				String xCol = xAxis.getColName();
				String yCol = yAxis.getColName();

				List<BOInstance> list = service.invokeSelect();
					
				if(list.size() > 0){
					BOInstance oneIns = list.get(0);
					data.put("name", oneIns.getValue(xAxis));
					data.put("value",oneIns.getValue(yAxis));
				}
			}
			
			boxicon = StringUtil.getFaIcon(boxicon);
			
			data.put("bgcolor", bgcolor);
			data.put("boxicon", boxicon);
			data.put("linkPaneName", linkPane.getName());
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}




}
