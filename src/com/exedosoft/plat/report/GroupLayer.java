package com.exedosoft.plat.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.exedosoft.plat.bo.BOInstance;

public class GroupLayer {

	private int deepMax = 3;

	private String group1;
	private String group2;
	private String group3;
	
	boolean isSum1 = false;
	boolean isSum2 = false;
	boolean isSum3 = false;


	public List<OneGroup> toGroupList(List<BOInstance> list, int layerNum) {

		String groupName = group1;
		boolean isSum = isSum1;
		if (layerNum == 2) {
			groupName = group2;
			isSum = isSum2;
		} else if (layerNum == 3) {
			groupName = group3;
			isSum = isSum3;
		}

		int ogSize = 1;
		String groupValue = null;

		OneGroup og = null;
		List<OneGroup> groupList = new ArrayList<OneGroup>();
		for (BOInstance bi : list) {
			groupValue = bi.getValue(groupName);
			if(groupValue==null){
				groupValue = "未定义";
			}
			if ((og != null && og.getGroupValue().equals(groupValue))
					&& groupList.contains(og)) {
				ogSize++;
				og.setGroupSize(ogSize);
				og.getChildrenBI().add(bi);
			} else {
				ogSize = 1;
				og = new OneGroup();
				og.setGroupSize(ogSize);
				og.setStatistics(isSum);
				og.setGroupValue(groupValue);
				og.setLayerNum(layerNum);
				og.getChildrenBI().add(bi);
				groupList.add(og);
			}
		}

		layerNum++;
		if (layerNum <= deepMax) {
			for (OneGroup oGroup : groupList) {

				if (oGroup.getChildrenBI() != null) {

					List<OneGroup> childGroup = toGroupList(
							oGroup.getChildrenBI(), layerNum);
					oGroup.setChildrenGroup(childGroup);
				}
			}
		}
		return groupList;
	}

	public List<String> getCrosstabCols(List<BOInstance> list,
			String groupRowName) {

		List<String> crosstabCols = new ArrayList<String>();
		for (BOInstance bi : list) {
			String theCol = bi.getValue(groupRowName);
			if (!crosstabCols.contains(theCol)) {
				crosstabCols.add(theCol);
			}
		}
		return crosstabCols;
	}

	public String getGroup1() {
		return group1;
	}

	public void setGroup1(String group1) {
		this.group1 = group1;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public String getGroup3() {
		return group3;
	}

	public void setGroup3(String group3) {
		this.group3 = group3;
	}

	public int getDeepMax() {
		return deepMax;
	}

	public void setDeepMax(int deepMax) {
		this.deepMax = deepMax;
	}



	public boolean isSum1() {
		return isSum1;
	}

	public void setSum1(boolean isSum1) {
		this.isSum1 = isSum1;
	}

	public boolean isSum2() {
		return isSum2;
	}

	public void setSum2(boolean isSum2) {
		this.isSum2 = isSum2;
	}

	public boolean isSum3() {
		return isSum3;
	}

	public void setSum3(boolean isSum3) {
		this.isSum3 = isSum3;
	}

	public String toString() {

		return ToStringBuilder.reflectionToString(this) + "\n";
	}

}