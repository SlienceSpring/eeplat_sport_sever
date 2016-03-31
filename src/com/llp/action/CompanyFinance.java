package com.llp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.weixin.redpacket.RedPacketUtil;

public class CompanyFinance {

	
	private String companyuid;

	public CompanyFinance(String aCompanyUid) {
		this.companyuid = aCompanyUid;
	}

	public void sendRedPack(String openid, String totalAmount, String wishing) {

		int money = Integer.parseInt(totalAmount);
		RedPacketUtil rpu = new RedPacketUtil();
		try {
			rpu.sendRedPack(openid, totalAmount, wishing);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.afterRedpacket(money);
	}

	/**
	 * 发红包后计算公司余额
	 * 
	 * @param money
	 */
	public void afterRedpacket(int money) {

		// /查询公司余额的服务
		DOService findBalance = DOService.getService("c_select_redbalance");
		Transaction t = findBalance.getBo().getDataBase().getTransaction();
		t.begin();
		try {
			// /得到公司余额
			String balance = findBalance.getAValue(this.companyuid);
			int iBalance = 0;
			try {
				iBalance = Integer.parseInt(balance);
			} catch (Exception e) {
			}

			// //余额不足，启用信用额度
			if (money > iBalance) {
				int creditValue = money;
				if (iBalance > 0) {
					creditValue = money - iBalance;
				}
				DOService creditDecrease = DOService
						.getService("c_update_creditvalue_decrease");
				creditDecrease.invokeUpdate(creditValue + "", companyuid);
			}
			// 直接扣除余额，余额可以为负, 并且更新发红包总额
			DOService balanceDecrease = DOService
					.getService("c_update_redbalance_decrease");
			balanceDecrease.invokeUpdate("" + money,"" + money, companyuid);

			// 撤销预授权额度
			DOService preAuthIncrease = DOService
					.getService("c_update_pre_auth_money_decrease");
			preAuthIncrease.invokeUpdate("" + money, companyuid);

		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
		t.end();
	}

	/**
	 * 预授权。
	 * 
	 * @param money
	 * @return true 预授权成功，false 无法预授权，预授权额度过大。
	 */
	public boolean preAuth(int money) {

		// 判断preauth

		// //得到预授权额度，余额，信用余额
		// /查询公司余额的服务
		
		///金额* 2 ,一半给求职者，一半给LLP公司
		money = money * 2;
		
		DOService findCompany = DOService.getService("llp_company_browse");
		BOInstance aComp = findCompany.getInstance(this.companyuid);
		// 信用额度
		String creditvalue = aComp.getValue("creditvalue");
		// 余额
		String redbalance = aComp.getValue("redbalance");
		// 预授权额度
		String pre_auth_money = aComp.getValue("pre_auth_money");
		if (pre_auth_money == null) {
			pre_auth_money = "0";
		}
		if (redbalance == null) {
			redbalance = "0";
		}
		if (creditvalue == null) {
			creditvalue = "0";
		}

		try {
			int iCreditValue = Integer.parseInt(creditvalue);
			int iBalance = Integer.parseInt(redbalance);
			int iPreAuth = Integer.parseInt(pre_auth_money);

			int totalAuth = iCreditValue + iBalance;
			if (iBalance < 0) {
				totalAuth = iCreditValue;
			}
			if ((iPreAuth + money) > totalAuth) {
				return false;
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			return false;
		}

		// 增加预授权额度
		DOService preAuthIncrease = DOService
				.getService("c_update_pre_auth_money_increase");
		try {
			preAuthIncrease.invokeUpdate("" + money, companyuid);
		} catch (ExedoException e) {
			return false;
		}
		return true;
	}

	/**
	 * 充值接口
	 * 
	 * @param money
	 * @param filloperater
	 */
	public void recharge(int money, String filloperater,String otherParas) {

		// //得到预授权额度，余额，信用余额
		// /查询公司余额的服务
		DOService findCompany = DOService.getService("llp_company_browse");
		BOInstance aComp = findCompany.getInstance(this.companyuid);

		// 信用额度
		String creditvalue = aComp.getValue("creditvalue");
		if (creditvalue == null) {
			creditvalue = "0";
		}

		// 信用额度余额
		String companycredit = aComp.getValue("companycredit");
		if (companycredit == null) {
			companycredit = "0";
		}

		// 充值表中加入数据
		DOService llp_company_fill_insert = DOService
				.getService("llp_company_fill_insert");
		Map<String, String> paras = new HashMap();
		
		BOInstance  formParas = null; 
		try {
			formParas =	DOGlobals.getInstance().getSessoinContext().getFormInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(formParas!=null){
			paras.putAll(formParas.getMap());
		}
		paras.put("filloperater", filloperater);
		paras.put("fillmoney", "" + money);
		paras.put("companyuid", this.companyuid);

		Transaction t = llp_company_fill_insert.getBo().getDataBase()
				.getTransaction();
		t.begin();
		try {
			// /充值表保存
			llp_company_fill_insert.invokeUpdate(paras);

			int iCompanycredit = Integer.parseInt(companycredit);
			int baseCompanycredit = getCompanycredit(money);
			// /如果比当前值大，那么更新公司额度companycredit
			if (baseCompanycredit > iCompanycredit) {
				DOService c_update_companycredit = DOService
						.getService("c_update_companycredit");
				c_update_companycredit.invokeUpdate(baseCompanycredit + "",
						this.companyuid);
				iCompanycredit = baseCompanycredit;
			}

			// //充值改变余额,直接增加余额
			DOService balanceDecrease = DOService
					.getService("c_update_redbalance_increase");
			aComp = balanceDecrease.invokeUpdate("" + money, companyuid);

			// 更新完新的余额
			String newRedbalance = aComp.getValue("redbalance");
			int iNewBalance = Integer.parseInt(newRedbalance);

			// //充值完，余额为正，则恢复信用额度。
			if (iNewBalance > 0) {
				DOService c_update_creditvalue = DOService
						.getService("c_update_creditvalue");
				c_update_creditvalue.invokeUpdate(iCompanycredit + "",
						this.companyuid);
			} else {// /不恢复，但是信用额度增加
				DOService c_update_creditvalue_increase = DOService
						.getService("c_update_creditvalue_increase");
				c_update_creditvalue_increase.invokeUpdate(money + "",
						this.companyuid);
			}

		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.rollback();
		}
		t.end();
	}

	int getCompanycredit(int money) {

		DOService llp_company_credit_list_order_fillmoney = DOService
				.getService("llp_company_credit_list_order_fillmoney");
		List<BOInstance> listCredit1 = llp_company_credit_list_order_fillmoney
				.invokeSelect();

		DOService llp_company_credit_listbycompanyuid = DOService
				.getService("llp_company_credit_listbycompanyuid");

		List<BOInstance> listCredit2 = llp_company_credit_listbycompanyuid
				.invokeSelect(this.companyuid);

		List<CreditValue> listCredit = new ArrayList<CreditValue>();
		for (BOInstance bi1 : listCredit1) {

			CreditValue cv1 = new CreditValue();
			cv1.fromBOInstance(bi1);

			if (listCredit2 != null && listCredit2.size() > 0) {
				for (BOInstance bi2 : listCredit2) {
					CreditValue cv2 = new CreditValue();
					cv2.fromBOInstance(bi2);

					if (cv1.getLevel().equals(cv2.getLevel())) {
						cv1 = cv2;
					}
				}
			}
			listCredit.add(cv1);
		}

		System.out.println("listCredit::" + listCredit);
		
		if(listCredit.size() == 0){
			return 0;
		}

		CreditValue cvBack = listCredit.get(0);
		for (CreditValue cv : listCredit) {

			if (money < cv.getFillMoney()) {
				return cvBack.getCredit();
			}
			cvBack = cv;
		}
		return cvBack.getCredit();
	}

	public void batchDowloadBonus(String objs, String createtype) {

		DOService batchHelper = DOService
				.getService("hr_resumesbox_updateresumestate_helper");
		DOService resumebox_browse = DOService
				.getService("llp_hr_resumebox_browse");
		DOService person_browse = DOService.getService("llp_person_browse");
		DOService resumebox_red_record = DOService
				.getService("llp_resumebox_helper_red_record_insert");

		DOService downresume_insert_helper = DOService
				.getService("Rule_json_llp_hr_downresume_insert_helper");

		String[] arrayObjs = objs.split(",");
		List<BOInstance> rets = new ArrayList<BOInstance>();
		try {
			for (int i = 0; i < arrayObjs.length; i++) {
				String aObj = arrayObjs[i];
				aObj = aObj.replace("'", "");
				// ////////
				BOInstance aResumebox = resumebox_browse.getInstance(aObj);
				BOInstance aPerson = person_browse.getInstance(aResumebox
						.getValue("personuid"));
				boolean isAuth = this.preAuth(Integer.parseInt(aPerson
						.getValue("downloadbonus")));
				if (isAuth) {
					aResumebox = batchHelper.invokeUpdate(createtype, aObj);
					rets.add(aResumebox);

					// //增加下载表
					downresume_insert_helper.invokeUpdate();
					// //增加红包
					resumebox_red_record.invokeUpdate();
				} else {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOGlobals.getInstance().getRuleContext().setInstances(rets);
	}

	private class CreditValue {

		int fillMoney;
		int credit;
		String level;

		public int getFillMoney() {
			return fillMoney;
		}

		public void setFillMoney(int fillMoney) {
			this.fillMoney = fillMoney;
		}

		public int getCredit() {
			return credit;
		}

		public void setCredit(int credit) {
			this.credit = credit;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String toString() {

			return ToStringBuilder.reflectionToString(this) + "\n";
		}

		public void fromBOInstance(BOInstance bi) {
			this.setCredit((int) bi.getDoubleValue("credit"));
			this.setFillMoney((int) bi.getDoubleValue("fillmoney"));
			this.setLevel(bi.getValue("fillname"));
		}
	}

	public static void main(String[] args) throws ExedoException {

//		DOGlobals.getInstance();
//		CompanyFinance cf = new CompanyFinance(
//				"8af4e49d4f45c1f2014f49c028a9011c");
//		cf.afterRedpacket(1);
		// System.out.println("CreditValue::" + cv);
		
		DOService ss = DOService.getService("llp_resumebox_helper_red_record_2");
		
		Map map = new HashMap();
		map.put("personmoney", "502");
		map.put("personuid", "memememe");
		
		ss.invokeUpdate(map);

	}

}
