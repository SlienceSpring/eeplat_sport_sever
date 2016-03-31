package com.exedosoft.plat.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.exedosoft.plat.CacheFactory;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.id.UUIDHex;

import net.sf.ehcache.CacheManager;

public class JDBCTester {

	//

	public static StringBuffer changeBitType() {

		StringBuffer buffer = new StringBuffer();

//		Connection con = DODataSource.getDefaultCon_Busi();

		

		String driverUrl ="jdbc:postgresql://localhost/default_data";

		Connection con = null;
	
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			////postgres的管理员密码 123asd
			con = DriverManager.getConnection(driverUrl, "eeplat",
					"eeplat123");
			
			
			System.out.println("Connection:::" + con);

//			con.setAutoCommit(false);
//			PreparedStatement pstmt = con
//					.prepareStatement();
			
			PreparedStatement stmt = con.prepareStatement("select whatuid from do_authorization,do_org_user_role ur  where  do_authorization.ouuid = ur.role_uid and  whattype = 16 and  ur.user_uid = ?");
			stmt.setString(1, "40288031278ed91501278ed915b30000");
			
			ResultSet rs = stmt.executeQuery();
			
			
			while(rs.next()){
				
				System.out.println("WhatUid::" + rs.getString("whatuid"));
				
			}
			
			rs.close();
			stmt.close();
//			for(int i = 10; i < 1000000; i++){
//				StringBuffer sql = new StringBuffer("insert into t_expense  values ('");
//				sql.append(UUIDHex.getInstance().generate())
//				.append("',")
//				.append("'")
//				.append(i)
//				.append("',")
//				.append("'")
//				.append(i)
//				.append("',null,")
//				.append("'")
//				.append(i)
//				.append("',")
//				.append("'")
//				.append(i)
//				.append("')");
//				
//				stmt.execute(sql.toString());
//				
//				
//				
//			}
			
			
			
//			PreparedStatement pstmt2 = con.prepareStatement("update do_log set  userName='1111'  where objuid=? ");
		//	PreparedStatement pstmt

//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
				//rs.updateString("userName", "bb");
			//	System.out.println(rs.getString("name"));
//				pstmt2.setString(1, rs.getString("objuid"));
//				pstmt2.executeUpdate();
//			}

//			pstmt.close();
	//		con.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}

		}
		return buffer;

	}

	public static void main(String[] args) {
		//changeBitType();
		
		CacheFactory.getCacheData().fromSerialObject();
		
		DOBO bo = DOBO.getDOBOByName("crm_lead_wx");
		
		
		System.out.println("Parent::" + bo.getParent());
		
		
		 bo = DOBO.getDOBOByName("crm_lead");
		
		
		System.out.println("Children::" + bo.getChildrenBOs());
		
		//JDBCTester.changeBitType();
		
		
//		BOInstance formInstance = new BOInstance();
//		formInstance.putValue("objuid", "'1','2'");
//		DOGlobals.getInstance().getSessoinContext().setFormInstance(formInstance);
//		DOService aService = DOService.getService("crm_account_browse_multi");
//		List list = aService.invokeSelect();
		
//		DOService aService = DOService.getService("crm_account_browse_multi");
//		List list = aService.invokeSelect("'1','2'");
//
//		
//		System.out.println("List::" + list);
//		Random r = new Random();
//		
//		int salesSize = list.size();
//		
//		int randomInt = r.nextInt(salesSize);
//		
//		BOInstance bi =  (BOInstance)list.get(randomInt);
//		
//		System.out.println(bi.getUid());
//		
//		System.out.print("==================================");
//		
//		System.out.println(bi);
//		
//		
//		System.out.println("List:::::::" + list);

		//[0,n)
		//System.out.println(r.nextInt(10));

//		Map clauseValueIns = new HashMap();
//		//aMap.put(key, value)
//		
//		
//		clauseValueIns.put("sendstatus", "1");// 要加上是否发送，对于可以发送的数据
//		clauseValueIns.put("msgowneruid", "2");// 消息权限过滤，老师是classid，家长是ownerid
//		clauseValueIns.put("owneruid", "3");
//		clauseValueIns.put("babyuid", "4");
//		clauseValueIns.put("modifytime", "2012-10-25");
//			clauseValueIns.put("modifytimebasedata", "2012-10-25");
//			clauseValueIns.put("modifieruid", "5");
//		
//		clauseValueIns.put("classuid", "7");
//
//		
//		List list = aService.invokeSelect(clauseValueIns);
//		System.out.println("ServiceName::" + list);
		
		//DOService  testService = DOService.getService("t_a1_list");
		
//		testService.getBo().getDataBase().getTransaction()
		
		
    //    System.out.println(testService.select(2,2));	
        
      // testService.invokeU
        
        //List list = testService.select();
        
//		DecimalFormat df=new DecimalFormat(".00000");
//		double d=12345.000000000;
//		String st=df.format(d);
//		st = st.substring(0,st.indexOf(".00000"));
//		System.out.println(st);
//		
//		char endNum = "wei0".charAt("wei0".length() - 1);
//		
//		System.out.println(endNum);
//		System.out.println((int)endNum);
//        
	 
	}

}
