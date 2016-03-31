package com.exedosoft.plat.ui.jquery.grid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.report.GroupLayer;
import com.exedosoft.plat.report.OneGroup;
import com.exedosoft.plat.report.StatisticType;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.util.Escape;
import com.exedosoft.plat.report.StatisticValue;

/**
 * @author aa
 */
public class GridListCrosstab extends DOViewTemplate {

	private static Log log = LogFactory.getLog(GridListCrosstab.class);

	public GridListCrosstab() {

		dealTemplatePath("/grid/GridListCrosstab.ftl");
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

	public void dealListData(DOGridModel gridModel, Map<String, Object> data) {

		// 第一步分组和交叉表格先不支持分页
		List<BOInstance> list = new ArrayList();

		String paras = Escape.unescape(gridModel.getExeDoc());
		try {

			JSONObject reportConfig = new JSONObject(paras);
			String gridType = reportConfig.getString("gridType");

			if (Integer.parseInt(gridType) == DOGridModel.GRID_TYPE_CROSS) {

				int maxLayer = 1;
				GroupLayer groupLayer = new GroupLayer();

				DOService service = gridModel.getService();
				String bakSql = service.getMainSql();

				String group1Uid = reportConfig.getString("group1");
				DOBOProperty pGroup1 = DOBOProperty
						.getDOBOPropertyByID(group1Uid);
				String sort1 = reportConfig.getString("sort1");

				String group2Uid = reportConfig.getString("group2");
				DOBOProperty pGroup2 = DOBOProperty
						.getDOBOPropertyByID(group2Uid);
				String sort2 = reportConfig.getString("sort2");

				String crosstabRowUid = reportConfig.getString("grouprow");
				DOBOProperty pCrosstabRow = DOBOProperty
						.getDOBOPropertyByID(crosstabRowUid);
				String crosstabSortrow = reportConfig.getString("sortrow");

				String crosstabColUid = reportConfig.getString("groupcol");
				DOBOProperty pCrosstabCol = DOBOProperty
						.getDOBOPropertyByID(crosstabColUid);
				String crosstabSortCol = reportConfig.getString("sortcol");

				String crosstabValueUid = reportConfig
						.getString("crosstabvalue");

				DOBOProperty pCrosstabValue = DOBOProperty
						.getDOBOPropertyByID(crosstabValueUid);

				// //////////如果谢了order by 那么就用默认的 ，否则系统组装
				if (crosstabColUid != null
						&& crosstabRowUid != null
						&& service.getMainSql().toLowerCase().indexOf("order ") == -1) {

					StringBuffer sb = new StringBuffer("SELECT * FROM ( ")
							.append(service.getMainSql()).append(
									" ) as childview order by ");

					if (pGroup1 != null) {
						sb.append(pGroup1.getColName());
						if (sort1 != null) {
							sb.append(" ").append(sort1);
						}
					}

					if (pGroup2 != null) {
						if (pGroup1 != null) {
							sb.append(",");
						}
						sb.append(pGroup2.getColName());
						if (sort2 != null) {
							sb.append(" ").append(sort2);
						}
					}

					// //不为空
					if (pCrosstabRow != null) {

						if (pGroup1 != null || pGroup2 != null) {
							sb.append(",");
						}
						sb.append(pCrosstabRow.getColName());
						if (crosstabSortrow != null) {
							sb.append(" ").append(crosstabSortrow);
						}
					}

					if (pCrosstabCol != null) {
						sb.append(",").append(pCrosstabCol.getColName());
						if (crosstabSortCol != null) {
							sb.append(" ").append(crosstabSortCol);
						}

					}
					service.setMainSql(sb.toString());
				}

				if (pGroup1 != null) {
					maxLayer++;
					groupLayer.setGroup1(pGroup1.getColName());
				}

				if (pGroup2 != null) {
					maxLayer++;
					groupLayer.setGroup2(pGroup2.getColName());
				}

				if (maxLayer == 3) {
					groupLayer.setGroup3(pCrosstabRow.getColName());
				} else if (maxLayer == 2) {
					groupLayer.setGroup2(pCrosstabRow.getColName());
				} else {
					groupLayer.setGroup1(pCrosstabRow.getColName());
				}

				list = service.invokeSelect();
				// ////重新置回原来的SQL
				service.setMainSql(bakSql);

				groupLayer.setDeepMax(maxLayer);
				List<OneGroup> listGroup = groupLayer.toGroupList(list, 1);

				// /////////获取动态标题
				List<String> cols = groupLayer.getCrosstabCols(list,
						pCrosstabCol.getColName());

				System.out.println("Cols:::::" + cols);

				String sumtype = reportConfig.getString("sumtype");
				StatisticType st = new StatisticType(
						pCrosstabValue.getColName(), sumtype);
				CrosstabHtml ch = new CrosstabHtml();
				ch.setCol(pCrosstabCol.getColName());
				ch.setRow(pCrosstabRow.getColName());
				ch.setVal(pCrosstabValue.getColName());
				ch.setStatisticType(st);
				ch.setCrosstabCols(cols);
				ch.setListGroup(listGroup);
				ch.setMaxLayer(maxLayer);
				ch.setDatas(list);
				if (pCrosstabValue.isNumberType()) {
					ch.setValNumber(true);
				}

				StringBuffer dynColumns = new StringBuffer();
				int dynCols = ch.getDynColumns(dynColumns);

				StringBuffer crosstabHead = getPreCrosstabHead(gridModel,
						pGroup1, pGroup2, pCrosstabRow, pCrosstabCol, dynCols);

				data.put("crosstabhead", crosstabHead.append(dynColumns));

				data.put("crosstabbody",
						ch.toCrosstabHtml() + ch.getStatisticFooter());

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private StringBuffer getPreCrosstabHead(DOGridModel gridModel,
			DOBOProperty pGroup1, DOBOProperty pGroup2,
			DOBOProperty pCrosstabRow, DOBOProperty pCrosstabCol, int colspan) {

		StringBuffer crosstabHead = new StringBuffer();
		List<DOFormModel> normalFormModels = gridModel.getNormalGridFormLinks();

		DOFormModel group1FM = null;
		DOFormModel group2FM = null;
		DOFormModel crosstabRowFM = null;
		DOFormModel crosstabColFM = null;

		for (DOFormModel aFM : normalFormModels) {

			if (pGroup1 != null) {
				if (pGroup1.getColName().equals(aFM.getColName())) {
					group1FM = aFM;
				}
			}

			if (pGroup2 != null) {
				if (pGroup2.getColName().equals(aFM.getColName())) {
					group2FM = aFM;
				}
			}

			if (pCrosstabRow != null) {
				if (aFM.getColName().equals(pCrosstabRow.getColName())) {
					crosstabRowFM = aFM;
				}
			}

			if (pCrosstabCol != null) {
				if (aFM.getColName().equals(pCrosstabCol.getColName())) {
					crosstabColFM = aFM;
				}
			}

		}

		crosstabHead.append("<tr>");
		if (group1FM != null) {
			crosstabHead.append("<th  class='crossheader' rowspan=2>").append(group1FM.getL10n())
					.append("</th>");
		} else if (pGroup1 != null) {
			crosstabHead.append("<th  class='crossheader' rowspan=2>").append(pGroup1.getColName())
					.append("</th>");
		}

		if (group2FM != null) {
			crosstabHead.append("<th class='crossheader'  rowspan=2>").append(group2FM.getL10n())
					.append("</th>");
		} else if (pGroup2 != null) {
			crosstabHead.append("<th class='crossheader'  rowspan=2>").append(pGroup2.getColName())
					.append("</th>");
		}

		if (crosstabRowFM != null) {
			crosstabHead.append("<th  class='crossheader' rowspan=2>").append(crosstabRowFM.getL10n())
					.append("</th>");
		} else {
			crosstabHead.append("<th  class='crossheader' rowspan=2>").append(pCrosstabRow.getColName())
					.append("</th>");
		}

		if (crosstabColFM != null) {
			crosstabHead.append("<th class='crossheader' ").append("colspan=").append(colspan)
					.append(">").append(crosstabColFM.getL10n())
					.append("</th>");
		} else {
			crosstabHead.append("<th class='crossheader' ").append("colspan=").append(colspan)
					.append(">").append(pCrosstabCol.getL10n()).append("</th>");
		}
		crosstabHead.append("</tr>");
		return crosstabHead;
	}

	class CrosstabHtml {

		String row;
		String col;
		String val;
		List<OneGroup> listGroup;
		List<String> crosstabCols;
		List<BOInstance> datas;
		StatisticType statisticType;
		int maxLayer = 1;

		boolean valNumber = false;

		public String getRow() {
			return row;
		}

		public void setRow(String row) {
			this.row = row;
		}

		public String getCol() {
			return col;
		}

		public void setCol(String col) {
			this.col = col;
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public StatisticType getStatisticType() {
			return statisticType;
		}

		public void setStatisticType(StatisticType st) {
			this.statisticType = st;
		}

		public List<OneGroup> getListGroup() {
			return listGroup;
		}

		public void setListGroup(List<OneGroup> listGroup) {
			this.listGroup = listGroup;
		}

		public List<BOInstance> getDatas() {
			return datas;
		}

		public void setDatas(List<BOInstance> datas) {
			this.datas = datas;
		}

		public List<String> getCrosstabCols() {
			return crosstabCols;
		}

		public void setCrosstabCols(List<String> crosstabCols) {
			this.crosstabCols = crosstabCols;
		}

		public boolean isValNumber() {
			return valNumber;
		}

		public void setValNumber(boolean valNumber) {
			this.valNumber = valNumber;
		}

		public int getMaxLayer() {
			return maxLayer;
		}

		public void setMaxLayer(int maxLayer) {
			this.maxLayer = maxLayer;
		}

		public void recombineList(List<OneGroup> theListGroup,
				List<BOInstance> combineList) {

			for (OneGroup og : theListGroup) {

				if (og.getChildrenGroup() != null
						&& og.getChildrenGroup().size() > 0) {

					recombineList(og.getChildrenGroup(), combineList);
				} else {

					BOInstance newBi = new BOInstance();
					BOInstance oldBi = null;
					for (String crosstabCol : crosstabCols) {

						for (BOInstance bi : og.getChildrenBI()) {
							if (bi.getValue(col).equals(crosstabCol)) {
								newBi.putValue(crosstabCol, bi.getValue(val));
								oldBi = bi;
								break;
							}
						}
					}
					if (oldBi != null) {
						newBi.getMap().putAll(oldBi.getMap());
					}
					combineList.add(newBi);
				}

			}

		}

		public String toCrosstabHtml() {

			StringBuffer parentGroupTds = new StringBuffer();
			StringBuffer sb = new StringBuffer();
			return toCrosstabHtmlHelper(parentGroupTds, sb, this.listGroup);

		}

		public String toCrosstabHtmlHelper(StringBuffer parentGroupTds,
				StringBuffer sb, List<OneGroup> theListGroup) {

			boolean isFirst = true;

			for (OneGroup og : theListGroup) {
				StringBuffer preGroupTd = new StringBuffer();
				if (isFirst) {
					isFirst = false;
					preGroupTd.append(parentGroupTds);
				}
				if (og.getChildrenGroup() != null
						&& og.getChildrenGroup().size() > 0) {

					preGroupTd.append("<td").append(" rowspan=")
							.append(og.getLeafGroupLength()).append(">")
							.append(og.getGroupValue()).append("</td>");
					toCrosstabHtmlHelper(preGroupTd, sb, og.getChildrenGroup());
				} else {

					sb.append("<tr>\n");
					sb.append(preGroupTd);
					sb.append("<td>").append(og.getGroupValue())
							.append("</td>");

					BigDecimal total = BigDecimal.valueOf(0);
					BigDecimal max = BigDecimal.valueOf(0);
					BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);

					for (String crosstabCol : crosstabCols) {
						boolean isHas = false;

						BigDecimal bd = BigDecimal.valueOf(0);
						String tdValue = "";
						for (BOInstance bi : og.getChildrenBI()) {
							if (bi.getValue(col).equals(crosstabCol)) {
								tdValue = bi.getValue(val);
								if (bd != null && this.isValNumber()) {
									try {
										bd = bd.add(new BigDecimal(tdValue));
									} catch (Exception e) {
										bd = null;
										e.printStackTrace();
									}
								} else {// //只有非数字类型才能跳出循环
									break;
								}
								isHas = true;
							}
						}// /end for
						if (isHas) {
							if (bd != null && this.isValNumber()) {
								sb.append("<td>").append(bd.toString())
										.append("</td>");
							} else {
								sb.append("<td>").append(tdValue)
										.append("</td>");
							}
						} else {
							sb.append("<td/>");
						}// ////end append td
						total = total.add(bd);
						max = max.max(bd);
						min = min.min(bd);
					}// end for

					if (this.statisticType.isSum()) {
						sb.append("<td style='color:blue'>")
								.append(total.toString()).append("</td>");
					}
					if (this.statisticType.isAvg()) {
						BigDecimal avg = total.divide(
								BigDecimal.valueOf(crosstabCols.size()),
								BigDecimal.ROUND_HALF_EVEN);
						sb.append("<td style='color:blue'>")
								.append(avg.toString()).append("</td>");
					}
					if (this.statisticType.isMax()) {
						sb.append("<td style='color:blue'>")
								.append(max.toString()).append("</td>");
					}
					if (this.statisticType.isMin()) {
						sb.append("<td style='color:blue'>")
								.append(min.toString()).append("</td>");
					}
				}// end if
				sb.append("</tr>\n");

			}
			return sb.toString();
		}

		public String getStatisticFooter() {

			StringBuffer sb = new StringBuffer();
			if (this.statisticType.isStatistic()) {
				Map<String, StatisticValue> statisticValues = new HashMap<String, StatisticValue>();

				BigDecimal total = BigDecimal.valueOf(0);
				BigDecimal max = BigDecimal.valueOf(0);
				BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);

				for (String name : this.crosstabCols) {
					for (BOInstance bi : this.datas) {

						StatisticValue sv = new StatisticValue(
								this.statisticType, bi);
						// /////统计的列发生了变化，交叉报表本来就是行变列
						if (bi.getValue(col).equals(name)) {
							String baseValue = bi.getValue(this.statisticType
									.getName());
							sv.setBaseValue(baseValue);
							if (statisticValues.containsKey(name)) {
								StatisticValue older = statisticValues
										.get(name);
								older.cal(sv);
							} else {
								statisticValues.put(name, sv);
							}
							// //最后的总数
							BigDecimal bd = new BigDecimal(baseValue);
							total = total.add(bd);
							max = max.max(bd);
							min = min.min(bd);
						}
					}
				}

				sb.append("<tr style='color:blue'>");

				sb.append("<td colspan=").append(maxLayer).append(">")
						.append("合计").append("</td>");

				for (String name : crosstabCols) {
					StatisticValue older = statisticValues.get(name);
					if (this.statisticType.isStatistic()) {
						sb.append("<td>");
						if (this.statisticType.isSum()) {
							sb.append(older.getSum().toString());
						}
						if (this.statisticType.isAvg()) {
							if (this.statisticType.isSum()) {
								sb.append("<br/>");
							}
							sb.append("平均：").append(older.getAvg().toString());
						}
						if (this.statisticType.isMax()) {
							if (this.statisticType.isSum()
									|| this.statisticType.isAvg()) {
								sb.append("<br/>");
							}
							sb.append("最大：").append(older.getMax().toString());
						}
						if (this.statisticType.isMin()) {
							if (this.statisticType.isSum()
									|| this.statisticType.isAvg()
									|| this.statisticType.isMax()) {
								sb.append("<br/>");
							}
							sb.append("最小：").append(older.getMin().toString());
						}

						sb.append("</td>\n");
					}// end if
				}// end for

				if (this.statisticType.isSum()) {
					sb.append("<td>").append(total).append("</td>");
				}
				if (this.statisticType.isAvg()) {
					BigDecimal avg = total.divide(
							BigDecimal.valueOf(this.datas.size()),
							BigDecimal.ROUND_HALF_EVEN);
					sb.append("<td>").append(avg).append("</td>");
				}
				if (this.statisticType.isMax()) {
					sb.append("<td>").append(max).append("</td>");
				}
				if (this.statisticType.isMin()) {
					sb.append("<td>").append(min).append("</td>");
				}
				sb.append("</tr>");
			}

			return sb.toString();
		}

		public int getDynColumns(StringBuffer dynColumns) {

			int i = 0;
			dynColumns.append("<tr>");
			for (String aCol : this.crosstabCols) {
				dynColumns.append("<th>").append(aCol).append("</th>");
				i++;
			}
			// //处理动态类输出之后：统计
			if (this.statisticType.isStatistic()) {
				if (this.statisticType.isSum()) {
					dynColumns.append("<th>").append("合计").append("</th>");
					i++;
				}
				if (this.statisticType.isAvg()) {
					dynColumns.append("<th>").append("平均").append("</th>");
					i++;
				}
				if (this.statisticType.isMax()) {
					dynColumns.append("<th>").append("最大").append("</th>");
					i++;
				}
				if (this.statisticType.isMin()) {
					dynColumns.append("<th>").append("最小").append("</th>");
					i++;
				}

			}
			dynColumns.append("</tr>");
			return i;
		}

	}

}
