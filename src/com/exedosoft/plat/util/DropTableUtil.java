package com.exedosoft.plat.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;

/**
 * 类型就是收敛到一定的程度，比如就是现在的4个类型。 但是还要支持扩展的类型。
 * 
 * @author anolesoft
 * 
 */

public class DropTableUtil {

	private static Log log = LogFactory.getLog(DropTableUtil.class);

	private Connection getConnection(String aDataSource) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"
					+ aDataSource
					+ "?useUnicode=true&amp;characterEncoding=utf-8", "wkx", "123asd");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}

	private static List<String> getTables(Connection con, String pre) {

		List<String> list = new ArrayList<String>();

		try {

			DatabaseMetaData meta = con.getMetaData();
			String[] tblTypes = new String[] { "TABLE", "VIEW" };

			String schema = null;

			ResultSet rs = meta.getTables(null, schema, null, tblTypes);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				// ////////////增强更新功能
				// //////首先要跟现有的tableName比较

				if (!"dtproperties".equals(tableName)) {
					BOInstance bi = new BOInstance();
					String aTable = rs.getString("TABLE_NAME").toLowerCase();
					if (!aTable.startsWith("bin$")) {

						if (aTable.startsWith(pre)) {

							list.add(aTable);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//

	public  void dropPreTables(String  dbName,String pre) {

		try {
			
			Connection conn = this.getConnection(dbName);
			List<String> tables = this.getTables(conn, pre);
			
			Statement stmt = conn.createStatement();
			for(String aTable : tables){
				stmt.executeUpdate("drop table " + aTable);
				System.out.println("Drop table " + aTable);
			}
			
			stmt.close();
			conn.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// DBTransUtil.makInsertSql("SEA_CLASSIFY", "\"ArticleClass\"");

		int i = BigDecimal.valueOf((12.00 * 80) / 100)
		.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		
		System.out.println("FFFFFFFFFFFF::" + i);
		
		System.out.println("IIIIIIIIIIIIIIIII::" + i);
		// System.out.print(DBTransUtil.changeBitType());

		// System.out.print(DBTransUtil.changeTextType());

	}

}
