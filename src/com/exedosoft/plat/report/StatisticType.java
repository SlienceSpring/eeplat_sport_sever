package com.exedosoft.plat.report;

import com.exedosoft.plat.ui.DOFormModel;

public class StatisticType {

	private DOFormModel formModel;
	private boolean statistic;
	private boolean sum = false;
	private boolean avg = false;
	private boolean max = false;
	private boolean min = false;
	private String name;

	public StatisticType(DOFormModel pFormModel) {
		this.formModel = pFormModel;
		judgeFormModel();
	}

	public StatisticType(String pName,String statisticType) {
		this.name = pName;
		judgeStatistic(statisticType);
	}

	public void judgeFormModel() {
		if (this.formModel == null) {
			return;
		}
		String statisticType = this.formModel.getStatisticsType();
		judgeStatistic(statisticType);
	}

	private void judgeStatistic(String statisticType) {
		if (statisticType != null) {
			String[] arrayType = statisticType.split(";");

			for (int i = 0; i < arrayType.length; i++) {
				String oneType = arrayType[i];
				if (oneType.equals("1")) {
					this.sum = true;
				}
				if (oneType.equals("2")) {
					this.avg = true;
				}
				if (oneType.equals("3")) {
					this.max = true;
				}
				if (oneType.equals("4")) {
					this.min = true;
				}
			}// end for
		}// end if
		this.statistic = this.sum || this.avg || this.max || this.min;
	}

	public DOFormModel getFormModel() {
		return formModel;
	}

	public void setFormModel(DOFormModel formModel) {
		this.formModel = formModel;
	}

	public boolean isStatistic() {
		return statistic;
	}

	public void setStatistic(boolean statistic) {
		this.statistic = statistic;
	}

	public boolean isSum() {
		return sum;
	}

	public void setSum(boolean sum) {
		this.sum = sum;
	}

	public boolean isAvg() {
		return avg;
	}

	public void setAvg(boolean ave) {
		this.avg = ave;
	}

	public boolean isMax() {
		return max;
	}

	public void setMax(boolean max) {
		this.max = max;
	}

	public boolean isMin() {
		return min;
	}

	public void setMin(boolean min) {
		this.min = min;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}