package com.exedosoft.plat.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.exedosoft.plat.bo.BOInstance;

public class OneGroup {

	private String groupValue;
	private List<OneGroup> childrenGroup;
	private int groupSize;
	private int layerNum;
	private boolean statistics;
	private String statisticsTitle;
	private List<BOInstance> childrenBI = new ArrayList<BOInstance>();
	private StatisticValue crossStatistic;
	private Map<String, StatisticValue> statisticValues = new HashMap<String, StatisticValue>();

	public int getLeafGroupLength() {

		int totalLength = 0;
		totalLength = getLeafGroupLengthHelper(childrenGroup, totalLength);
		return totalLength;

	}

	private int getLeafGroupLengthHelper(List<OneGroup> theChildrenGroup,
			int totalLength) {
		if (theChildrenGroup != null) {
			for (OneGroup og : theChildrenGroup) {
				if (og.getChildrenGroup() == null
						|| og.getChildrenGroup().size() == 0) {
					totalLength++;
				} else {
					totalLength = getLeafGroupLengthHelper(og.getChildrenGroup(),
							totalLength);
				}
			}
		}
		return totalLength;
	}

	public int getStatisticsGroupLength() {

		int totalLength = 0;
		totalLength = getStatisticsGroupLengthHelper(childrenGroup, totalLength);
		return totalLength;

	}

	private int getStatisticsGroupLengthHelper(List<OneGroup> theChildrenGroup,
			int totalLength) {
		
		if (theChildrenGroup != null) {
			for (OneGroup og : theChildrenGroup) {
				if (og.isStatistics()) {
					totalLength++;
				}
				if (og.getChildrenGroup() != null
						&& og.getChildrenGroup().size() > 0) {
					totalLength = getStatisticsGroupLengthHelper(og.getChildrenGroup(),
							totalLength);
				}

			}
		}
		return totalLength;
	}

	public int getGroupDeep() {

		int deep = 1;

		if (this.getChildrenGroup() != null
				&& this.getChildrenGroup().size() > 0) {
			deep++;
			for (OneGroup og : this.getChildrenGroup()) {
				if (og.getChildrenGroup() != null
						&& og.getChildrenGroup().size() > 0) {
					for (OneGroup child : og.getChildrenGroup()) {
						if (child.getChildrenGroup() != null
								&& child.getChildrenGroup().size() > 0) {
							deep++;
							break;
						}
					}
					deep++;
					break;
				}
			}

		}
		return deep;

	}

	private int getTotalBILengthHelper(List<OneGroup> theChildrenGroup,
			int totalLength) {
		if (theChildrenGroup != null) {
			for (OneGroup og : theChildrenGroup) {
				if (og.getChildrenBI() != null && og.getChildrenBI().size() > 0) {
					totalLength = totalLength + og.getChildrenBI().size();
				} else if (og.getChildrenGroup() != null
						&& og.getChildrenGroup().size() > 0) {
					getTotalBILengthHelper(og.getChildrenGroup(), totalLength);
				}
			}
		}
		return totalLength;
	}

	public int getTotalBILength() {
		int totalLength = 0;
		if (this.getChildrenBI() != null && this.getChildrenBI().size() > 0) {
			totalLength = totalLength + this.getChildrenBI().size();
		} else {
			totalLength = getTotalBILengthHelper(this.getChildrenGroup(),
					totalLength);
		}
		return totalLength;
	}

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupName) {
		this.groupValue = groupName;
	}

	public List<OneGroup> getChildrenGroup() {
		return childrenGroup;
	}

	public void setChildrenGroup(List<OneGroup> childrenGroup) {
		this.childrenGroup = childrenGroup;
	}

	public int getLayerNum() {
		return layerNum;
	}

	public void setLayerNum(int layerNum) {
		this.layerNum = layerNum;
	}

	public List<BOInstance> getChildrenBI() {
		return childrenBI;
	}

	public void setChildrenBI(List<BOInstance> childrenBI) {
		this.childrenBI = childrenBI;
	}

	public void setStatistics(boolean sum) {
		this.statistics = sum;
	}

	public boolean isStatistics() {
		return statistics;
	}

	public String getStatisticsTitle() {
		return statisticsTitle;
	}

	public void setStatisticsTitle(String statisticsTitle) {
		this.statisticsTitle = statisticsTitle;
	}

	public StatisticValue getCrossStatistic() {
		return crossStatistic;
	}

	public Map<String, StatisticValue> getStatisticValues() {
		return statisticValues;
	}

	public void setStatisticValues(Map<String, StatisticValue> statisticCols) {
		if (statisticCols != null) {
			this.statisticValues = statisticCols;
		}
	}

	public StatisticValue getStatisticValue(String name) {

		return this.statisticValues.get(name);
	}

	public BigDecimal sum(String name) {

		BigDecimal bdTotal = BigDecimal.valueOf(0);
		if (this.getChildrenGroup() != null
				&& this.getChildrenGroup().size() > 0) {
			for (OneGroup og : this.getChildrenGroup()) {
				bdTotal = bdTotal.add(og.sum(name));
			}
			return bdTotal;
		} else

		if (statisticValues.containsKey(name)) {
			StatisticValue older = statisticValues.get(name);
			return older.getSum();
		}

		return bdTotal;

	}

	public BigDecimal avg(String name) {

		return this.sum(name).divide(
				BigDecimal.valueOf(this.getTotalBILength()),
				BigDecimal.ROUND_HALF_EVEN);
	}

	public BigDecimal max(String name) {

		BigDecimal bdTotal = BigDecimal.valueOf(0);
		if (this.getChildrenGroup() != null
				&& this.getChildrenGroup().size() > 0) {
			for (OneGroup og : this.getChildrenGroup()) {
				bdTotal = bdTotal.max(og.max(name));
			}
			return bdTotal;
		} else

		if (statisticValues.containsKey(name)) {
			StatisticValue older = statisticValues.get(name);
			return older.getMax();
		}
		return bdTotal;
	}

	public BigDecimal min(String name) {

		BigDecimal bdTotal = BigDecimal.valueOf(Integer.MAX_VALUE);
		if (this.getChildrenGroup() != null
				&& this.getChildrenGroup().size() > 0) {
			for (OneGroup og : this.getChildrenGroup()) {
				bdTotal = bdTotal.min(og.min(name));
			}
			return bdTotal;
		} else

		if (statisticValues.containsKey(name)) {
			StatisticValue older = statisticValues.get(name);
			return older.getMin();
		}
		return bdTotal;
	}

	/**
	 * 主要是单个group 针对BOInstance的统计。
	 * @param name
	 * @param sv
	 */
	public void addStatisticValue(String name, StatisticValue sv) {
		if (sv != null) {
			if (statisticValues.containsKey(name)) {
				StatisticValue older = statisticValues.get(name);
				older.cal(sv);
			} else {
				this.statisticValues.put(name, sv);
			}

		}
	}

	public boolean equals(Object other) {
		OneGroup castOther = (OneGroup) other;
		return new EqualsBuilder().append(this.getGroupValue(),
				castOther.getGroupValue()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getGroupValue()).toHashCode();
	}

	public String toString() {

		return ToStringBuilder.reflectionToString(this) + "\n";
	}

}