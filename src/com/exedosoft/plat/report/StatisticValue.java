package com.exedosoft.plat.report;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.exedosoft.plat.bo.BOInstance;

public class StatisticValue {

	private BigDecimal sum = BigDecimal.valueOf(0);
	private BigDecimal avg = BigDecimal.valueOf(0);
	private BigDecimal max = BigDecimal.valueOf(0);
	private BigDecimal min = BigDecimal.valueOf(0);

	private StatisticType statisType;

	private BOInstance data;
	
	private String baseValue;

	public StatisticValue(StatisticType pST, BOInstance pBI) {
		this.statisType = pST;
		this.data = pBI;
	}

	public StatisticType getStatisType() {
		return statisType;
	}

	public void setStatisType(StatisticType statisFM) {
		this.statisType = statisFM;
	}

	public BigDecimal getSum() {
		if (sum.intValue() == 0) {
			return this.getValue();
		}
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getAvg() {
		if (avg.intValue() == 0) {
			return this.getValue();
		}
		return avg;
	}

	public void setAvg(BigDecimal ave) {
		this.avg = ave;
	}

	public BigDecimal getMax() {
		if (max.intValue() == 0) {
			return this.getValue();
		}
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		if (min.intValue() == 0) {
			return this.getValue();
		}
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BOInstance getData() {
		return data;
	}

	public void setData(BOInstance data) {
		this.data = data;
	}

	public String getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(String baseValue) {
		this.baseValue = baseValue;
	}

	public String getName() {
		if (this.statisType != null) {
			if (this.statisType.getName() != null) {
				return this.statisType.getName();
			} else {
				return this.statisType.getFormModel().getColName();
			}
		}
		return "";
	}

	public BigDecimal getValue() {
		if (this.statisType != null) {
			try {
				String value = "0";
				if(this.baseValue!=null){
					value = this.baseValue;
				}else{
					value = this.data.getValue(this.getName());
				}
				if(value==null){
					value = "0";
				}
				return new BigDecimal(value);
			} catch (Exception e) {
				e.printStackTrace();
				return BigDecimal.valueOf(0);
			}
		}
		return BigDecimal.valueOf(0);
	}

	public void cal(StatisticValue sv) {

		if (this.getStatisType() == null) {
			System.err.println("StatisticValue has not StatisticFormModel");
			return;
		}
		if (!this.getStatisType().isStatistic()) {
			return;
		}
		if (this.getStatisType().isSum()) {
			this.setSum(this.getSum().add(sv.getSum()));
		}
		// 平均值不能这么干
		// if (this.getStatisFM().isAve()) {
		// System.out.println("this.getAve()::" + this.getAve());
		// System.out.println("sv.getAve()::" + sv.getAve());
		//
		// this.setAve((this.getAve().add(sv.getAve())
		// .divide(BigDecimal.valueOf(2),BigDecimal.ROUND_HALF_EVEN)));
		// }
		if (this.getStatisType().isMax()) {
			this.setMax(this.getMax().max(sv.getMax()));
		}
		if (this.getStatisType().isMin()) {
			this.setMin(this.getMin().min(sv.getMin()));
		}

	}

	public String toString() {

		return ToStringBuilder.reflectionToString(this) + "\n";
	}

	public static void main(String[] args) {

		BigDecimal bd = BigDecimal.valueOf(0);
		bd = bd.add(new BigDecimal("100"));

		System.out.println(bd);

	}

}