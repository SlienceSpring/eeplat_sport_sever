package com.exedosoft.plat.ui.jquery.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.report.GroupLayer;
import com.exedosoft.plat.report.OneGroup;
import com.exedosoft.plat.report.StatisticType;
import com.exedosoft.plat.report.StatisticValue;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.Escape;

/**
 * 
 */
public class GridListGroup extends DOViewTemplate {

	private static Log log = LogFactory.getLog(GridListGroup.class);

	public GridListGroup() {

		dealTemplatePath("/grid/GridListGroup.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		DOGridModel gm = (DOGridModel) doimodel;
		if (gm.getService() == null) {
			return null;
		}
		Map<String, Object> data = super.putData(doimodel);
		dealListData(gm, data);
		if (gm.getContainerPane() != null) {
			data.put("pmlName", gm.getContainerPane().getName());
		}
		String formName = "a" + gm.getObjUid();

		if (gm.getContainerPane() != null
				&& gm.getContainerPane().getParent() != null) {

			// //自动判断条件面板
			List children = gm.getContainerPane().getParent()
					.retrieveChildren();

			if (children != null && children.size() == 2) {
				DOPaneModel conditionPane = (DOPaneModel) children.get(0);
				DOPaneModel resultModel = (DOPaneModel) children.get(1);
				if (conditionPane != null) {
					if (conditionPane.getDOGridModel() != null) {
						formName = "a"
								+ conditionPane.getDOGridModel().getObjUid();
					}
				}

			}
		}
		// //如果配置了查询条件面板（这里的含义是 拥有表单的面板）
		DOPaneModel hpm = null;
		if (gm.getContainerPane() != null) {
			hpm = gm.getContainerPane().getHiddenPane();
		}
		if (hpm != null) {
			if (hpm.getDOGridModel() != null) {
				formName = "a" + hpm.getDOGridModel().getObjUid();
			}
		}
		if (gm.getBottomOutGridFormLinks().size() > 0) {
			DOFormModel firstFm = (DOFormModel) gm.getBottomOutGridFormLinks()
					.get(0);
			if (firstFm.isHidden()) {
				formName = firstFm.getTargetForms();
			}
		}
		data.put("formName", formName);
		if (gm.getService() != null
				&& gm.getService().getBo().getMainPaneModel() != null) {
			String mainPaneName = gm.getService().getBo().getMainPaneModel()
					.getName();
			data.put("mainPaneName", mainPaneName);
		}
		return data;
	}

	public void dealListData(DOGridModel gridModel,
			Map<String, Object> data) {

		String paras = Escape.unescape(gridModel.getExeDoc());
		try {
			JSONObject reportConfig = new JSONObject(paras);
			String gridType = reportConfig.getString("gridType");


			if (Integer.parseInt(gridType) == DOGridModel.GRID_TYPE_GROUP) {
				// ////group grid
				String group1Uid = reportConfig.getString("group1");
				DOBOProperty pGroup1 = DOBOProperty.getDOBOPropertyByID(group1Uid);
				GroupLayer groupLayer = new GroupLayer();

				if (pGroup1 != null) {
					
					List<OneGroup> listGroup = getListGroup(gridModel,
							reportConfig, pGroup1, groupLayer);
	
					List<DOFormModel> normalFormModels = gridModel
							.getNormalGridFormLinks();
					List<DOFormModel> remainFormModels = new ArrayList<DOFormModel>();
//					List<StatisticFormModel> statisticFormModels = new ArrayList<StatisticFormModel>();

					// ///去掉group 的字段
					for (DOFormModel aFM : normalFormModels) {

						if (!aFM.getColName().equals(groupLayer.getGroup1())
								&& !aFM.getColName().equals(
										groupLayer.getGroup2())
								&& !aFM.getColName().equals(
										groupLayer.getGroup3())) {
							remainFormModels.add(aFM);
						}
					}

					StringBuffer sb = new StringBuffer();
					StringBuffer parentGroupTds = new StringBuffer();
					getGroupTable(remainFormModels, listGroup, sb,
							parentGroupTds);
					OneGroup  ogMain = new OneGroup();
					ogMain.setChildrenGroup(listGroup);
					ogMain.setStatistics(true);
					ogMain.setStatisticsTitle("总计");
					appendStatistic(remainFormModels, sb, ogMain);

					data.put("groupTableHtmls", sb);

					List<DOFormModel> newOrderFormModels = new ArrayList<DOFormModel>();
					newOrderFormModels.addAll(remainFormModels);
					// /////为normalFormModels排序。
					for (DOFormModel aFM : normalFormModels) {

						if (aFM.getColName().equals(groupLayer.getGroup1())) {
							newOrderFormModels.add(0, aFM);

						}
						if (aFM.getColName().equals(groupLayer.getGroup2())) {
							newOrderFormModels.add(1, aFM);
						}

						if (aFM.getColName().equals(groupLayer.getGroup3())) {
							newOrderFormModels.add(2, aFM);
						}

					}
					data.put("normalFormModels", newOrderFormModels);

					}// /判断有group的情况

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private List<OneGroup> getListGroup(DOGridModel gridModel,
			JSONObject reportConfig, DOBOProperty pGroup1, GroupLayer groupLayer)
			throws JSONException {

		int maxLayer = 1;
		DOService service = gridModel.getService();
		
		String bakSql = service.getMainSql();
		groupLayer.setGroup1(pGroup1.getColName());
		String sort1 = reportConfig.getString("sort1");

		String group2Uid = reportConfig.getString("group2");
		DOBOProperty pGroup2 = DOBOProperty
				.getDOBOPropertyByID(group2Uid);
		String sort2 = reportConfig.getString("sort2");

		String group3Uid = reportConfig.getString("group3");
		DOBOProperty pGroup3 = DOBOProperty
				.getDOBOPropertyByID(group3Uid);
		String sort3 = reportConfig.getString("sort3");

		// //////////如果谢了order by 那么就用默认的 ，否则系统组装
		if (service.getMainSql().toLowerCase().indexOf("order ") == -1) {

			StringBuffer sb = new StringBuffer("SELECT * FROM ( ")
					.append(service.getMainSql())
					.append(" ) as childview order by ")
					.append(pGroup1.getColName());
			if (sort1 != null) {
				sb.append(" ").append(sort1);
			}

			if (pGroup2 != null) {
				sb.append(",").append(pGroup2.getColName());
				if (sort2 != null) {
					sb.append(" ").append(sort2);
				}
			}

			if (pGroup3 != null) {
				sb.append(",").append(pGroup3.getColName());
				if (sort3 != null) {
					sb.append(" ").append(sort3);
				}
			}

			service.setMainSql(sb.toString());
		}// //////////end 处理sql

		
		if (pGroup2 != null) {
			maxLayer++;
			groupLayer.setGroup2(pGroup2.getColName());
		}
		if (pGroup3 != null) {
			maxLayer++;
			groupLayer.setGroup3(pGroup3.getColName());
		}
		String issum1 = reportConfig.getString("issum1");
		String issum2 = reportConfig.getString("issum2");
		String issum3 = reportConfig.getString("issum3");

		if("YES".equals(issum1)){
			groupLayer.setSum1(true);
		}
		if("YES".equals(issum2)){
			groupLayer.setSum2(true);
		}
		if("YES".equals(issum3)){
			groupLayer.setSum3(true);
		}
		
		List<BOInstance> list = service.invokeSelect();
		service.setMainSql(bakSql);
		
		groupLayer.setDeepMax(maxLayer);
		List<OneGroup> listGroup = groupLayer.toGroupList(list, 1);
		return listGroup;
	}

	private void getGroupTable(List<DOFormModel> remainFormModels,
			List<OneGroup> listGroup, StringBuffer sb,
			StringBuffer parentGroupTds) {

		boolean isFirst = true;
		for (OneGroup og : listGroup) {
			StringBuffer preGroupTd = new StringBuffer();
			if (isFirst) {
				isFirst = false;
				preGroupTd.append(parentGroupTds);
			}
			
			int rowspanSize = getRowspanSize(og);
			
			preGroupTd.append("<td").append(" rowspan=")
					.append(rowspanSize).append(">")
					.append(og.getGroupValue()).append("</td>");

			if (og.getChildrenGroup() != null
					&& og.getChildrenGroup().size() > 0) {

				getGroupTable(remainFormModels, og.getChildrenGroup(), sb,
						preGroupTd);
				////////输出统计
				if(og.isStatistics()){
					appendStatistic(remainFormModels, sb, og);
				}
				//////////输出统计
			} else {
				isFirst = true;
				for (BOInstance bi : og.getChildrenBI()) {
					sb.append("<tr>\n");
					if (isFirst) {
						isFirst = false;
						sb.append(preGroupTd);
					}
					for (DOFormModel aFM : remainFormModels) {
						aFM.setData(bi);
						
						StatisticType st = new StatisticType(aFM);
						if(st.isStatistic()){
							StatisticValue  sv = new StatisticValue(st,bi);

							og.addStatisticValue(aFM.getColName(), sv);							
						}
						String theValue = aFM.getHtmlValue();
						///modify by weikexin@20160122,原来是getValue
//						if(theValue==null){
//							theValue = "&nbsp;";
//						}
						sb.append("<td>").append(theValue)
								.append("</td>\n");
					}
					sb.append("</tr>\n");
				}
				////////输出统计
				if(og.isStatistics()){
					appendStatistic(remainFormModels, sb, og);
				}
			}
		}
	}

	private int getRowspanSize(OneGroup og) {
		int rowspanSize = og.getGroupSize();

		if(og.getChildrenGroup()!=null && og.getChildrenGroup().size()>0){
			rowspanSize = rowspanSize + og.getStatisticsGroupLength();
		}
		if(og.isStatistics()){
			rowspanSize = rowspanSize + 1;
		}
		return rowspanSize;
	}

	private void appendStatistic(List<DOFormModel> remainFormModels,
			StringBuffer sb, OneGroup og) {
		sb.append("<tr style='color:blue'>\n");
		sb.append("<td colspan=")
		.append(og.getGroupDeep())
		.append(">");
		if(og.getStatisticsTitle()==null){
			sb.append("小计");
		}else{
			sb.append(og.getStatisticsTitle());
		}
		sb.append("</td>");
		boolean isFirst = true;
		for (DOFormModel aFM : remainFormModels) {

			/////////第一个跳出
			if(isFirst){
				isFirst = false;
				continue;
			}
		
			StatisticType st = new StatisticType(aFM);
			if(st.isStatistic()){
				sb.append("<td>");
				if(st.isSum()){
					sb.append(og.sum(aFM.getColName()));
				}
				if(st.isAvg()){
					if(st.isSum()){
						sb.append("<br/>");
					}
					sb.append("平均：").append(og.avg(aFM.getColName()));
				}
				if(st.isMax()){
					if(st.isSum() || st.isAvg()){
						sb.append("<br/>");
					}
					sb.append("最大：").append(og.max(aFM.getColName()));
				}
				if(st.isMin()){
					if(st.isSum() || st.isAvg() || st.isMax()){
						sb.append("<br/>");
					}
					sb.append("最小：").append(og.min(aFM.getColName()));
				}
				
				sb.append("</td>\n");
			}else{
				sb.append("<td/>");
			}
		}
		sb.append("</tr>");
	}


	public static void main(String[] args) {

		DOService service = DOService.getService("do_report_demo_list");
		List list = service.invokeSelect();

		GridListGroup gridListGroup = new GridListGroup();
		GroupLayer groupLayer = new GroupLayer();
		groupLayer.setGroup1("province");
		groupLayer.setGroup2("city");
		groupLayer.setGroup3("store");
		groupLayer.setDeepMax(3);

		List<OneGroup> listGroup = groupLayer.toGroupList(list, 1);

		System.out.println("listGroup::" + listGroup);
	}
}
