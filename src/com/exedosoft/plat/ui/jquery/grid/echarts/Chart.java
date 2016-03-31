package com.exedosoft.plat.ui.jquery.grid.echarts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;

public class Chart extends DOViewTemplate {

	private static Log log = LogFactory.getLog(Chart.class);

	public Chart() {

		dealTemplatePath("/grid/echarts/chart.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		Map<String, Object> data = super.putData(doimodel);

		DOGridModel gm = (DOGridModel) doimodel;
		if (gm.getService() == null) {
			return null;
		}

		try {
			JSONObject chartConfig = new JSONObject(gm.getExeDoc());

			String name = chartConfig.getString("name");
			String l10n = chartConfig.getString("l10n");
			String serviceUid = chartConfig.getString("serviceUid");
			String chartType = chartConfig.getString("chartType");
			String xaxisCol = chartConfig.getString("xaxisCol");
			String yaxisCol = chartConfig.getString("yaxisCol");
			String categoryCol = chartConfig.getString("categoryCol");
			String mainText = chartConfig.getString("mainText");
			String subText = chartConfig.getString("subText");
			String otheroptions = chartConfig.getString("otheroptions");
			String height = null;
			String width = null;

			if (chartConfig.has("height")) {
				height = chartConfig.getString("height");
			} else {
				height = "350px";
			}
			if (chartConfig.has("width")) {
				width = chartConfig.getString("width");
			} else {
				width = "100%";
			}

			String legendShow = "false";
			String legendData = "";

			DOService service = DOService.getServiceByID(serviceUid);

			DOBOProperty xAxis = DOBOProperty.getDOBOPropertyByID(xaxisCol);
			DOBOProperty yAxis = DOBOProperty.getDOBOPropertyByID(yaxisCol);
			DOBOProperty pCategory = DOBOProperty
					.getDOBOPropertyByID(categoryCol);

			if (chartType.equals("bar1") || chartType.equals("line1")
					|| chartType.equals("gauge1")) {
				legendShow = "false";
			} else {
				legendShow = "true";
			}
			String requireChartType = "bar";

			if (chartType.startsWith("line")) {
				requireChartType = "line";
			} else if (chartType.startsWith("pie")) {
				requireChartType = "pie";
			} else if (chartType.startsWith("funnel")) {
				requireChartType = "funnel";
			} else if (chartType.startsWith("gauge")) {
				requireChartType = "gauge";
			}

			Set<String> categorySet = new LinkedHashSet<String>();

			if (service != null) {

				String xCol = xAxis.getColName();
				String yCol = yAxis.getColName();

				// //////////如果谢了order by 那么就用默认的 ，否则系统组装
				if (!categoryCol.equals("")
						&& service.getMainSql().toLowerCase().indexOf("order ") == -1) {

					StringBuffer sb = new StringBuffer("SELECT * FROM ( ")
							.append(service.getMainSql())
							.append(" ) as childview order by ")
							.append(pCategory.getColName());
					if (!xaxisCol.equals("")) {
						sb.append(",").append(xCol);
					}
					service.setMainSql(sb.toString());

				}

				List<BOInstance> list = service.invokeSelect();

				if (!categoryCol.equals("")) {
					for (Iterator<BOInstance> it = list.iterator(); it
							.hasNext();) {
						BOInstance anBI = it.next();
						categorySet.add(anBI.getValue(pCategory.getColName()));
					}
				} else {// //没有分类的情况，把l10n作为默认分类，没有分类是最简单的情况
					categorySet.add(l10n);
				}

				// /分类的legendData
				StringBuffer sbLegendData = new StringBuffer("[");
				for (Iterator<String> it = categorySet.iterator(); it.hasNext();) {
					sbLegendData.append("'").append(it.next()).append("'");
					if (it.hasNext()) {
						sbLegendData.append(",");
					}
				}
				sbLegendData.append("]");

				legendData = sbLegendData.toString();

				int categorySize = categorySet.size();

				int valuesSize = list.size() / categorySize;

				List<SerieValue> listSv = new ArrayList<SerieValue>();

				log.info("categorySize::::" + categorySize);

				for (int i = 0; i < categorySize; i++) {

					SerieValue sv = new SerieValue();
					List<BOInstance> subList = list.subList(i * valuesSize,
							(i + 1) * valuesSize);

					if (!categoryCol.equals("")) {
						sv.setCategory(subList.get(0).getValue(
								pCategory.getColName()));
					} else {
						sv.setCategory(l10n);
					}
					sv.setChatType(requireChartType);
					StringBuffer values = new StringBuffer("[");
					StringBuffer names = new StringBuffer("[");
					StringBuffer nameValues = new StringBuffer("[");

					for (Iterator<BOInstance> it = subList.iterator(); it
							.hasNext();) {
						BOInstance anBI = it.next();
						values.append("'").append(anBI.getValue(yCol))
								.append("'");
						names.append("'").append(anBI.getValue(xCol))
								.append("'");
						nameValues.append("{ name:'")
								.append(anBI.getValue(xCol))
								.append("',  value: '")
								.append(anBI.getValue(yCol)).append("'}");

						if (it.hasNext()) {
							nameValues.append(",");
							values.append(",");
							names.append(",");
						}
					}
					values.append("]");
					names.append("]");
					nameValues.append("]");

					sv.setData(values.toString());
					listSv.add(sv);

					data.put("names", names.toString());
					data.put("nameValues", nameValues.toString());
				}

				data.put("listSv", listSv);

			}

			data.put("requireChartType", requireChartType);

			data.put("mainText", mainText);
			data.put("subText", subText);
			data.put("width", width);
			data.put("height", height);

			data.put("legendShow", legendShow);

			if (requireChartType.equals("bar")
					|| requireChartType.equals("line")) {
				data.put("legendData", legendData);
			} else {
				data.put("legendData", data.get("names"));
			}

			data.put("otheroptions", otheroptions);

			if (gm.getContainerPane() != null) {
				data.put("pmlName", gm.getContainerPane().getName());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

}
