package com.exedosoft.plat.action.wf;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DODataSource;
import com.exedosoft.plat.util.I18n;

public class DOPTSaveJSON extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997150272030863948L;

	@SuppressWarnings("unchecked")
	@Override
	public String excute() throws ExedoException {
		DOBO bo = DOBO.getDOBOByName("do_pt_processtemplate");
		BOInstance curPt = bo.getCorrInstance();
		BOInstance echo = new BOInstance();
		if (curPt == null) {
			this.setEchoValue(I18n.instance().get("没有选择工作流模板!"));
			return NO_FORWARD;
		}
		String curPtUid = curPt.getUid();
		String strAlertJSON = this.actionForm.getValue("jsondata");
		JSONObject jsondata = null;
		List<String> delNodes = new ArrayList<String>();
		List<String> delLines = new ArrayList<String>();
		Map<String,Map<String,String>> UpNodes = new HashMap<String,Map<String,String>>();
		Map<String,Map<String,String>> UpLines = new HashMap<String,Map<String,String>>();
		try {
			jsondata = new JSONObject(strAlertJSON);
			JSONObject jsondataDeletetItems = jsondata.getJSONObject("deletedItem");
			JSONObject jsondataUpNodes = jsondata.getJSONObject("nodes");
			JSONObject jsondataUpLines = jsondata.getJSONObject("lines");
			
			if(jsondataUpLines!= null){
				for(Iterator<String> key = jsondataUpLines.keys();key.hasNext();){
					String aKey = key.next();
					JSONObject valuejson = jsondataUpLines.getJSONObject(aKey);
					if(valuejson != null){
						Map<String,String> oneRecord = new HashMap<String,String>();
						oneRecord.put("id", aKey);
						for(Iterator<String> key2 = valuejson.keys();key2.hasNext();){
							String bKey = key2.next();
							String value = valuejson.getString(bKey);
							oneRecord.put(bKey, value);
						}
						UpLines.put(aKey, oneRecord);
					}
				}
			}
			
			if(jsondataUpNodes!= null){
				for(Iterator<String> key = jsondataUpNodes.keys();key.hasNext();){
					String aKey = key.next();
					JSONObject valuejson = jsondataUpNodes.getJSONObject(aKey);
					if(valuejson != null){
						Map<String,String> oneRecord = new HashMap<String,String>();
						oneRecord.put("ptuid", curPtUid);
						for(Iterator<String> key2 = valuejson.keys();key2.hasNext();){
							String bKey = key2.next();
							String value = valuejson.getString(bKey);
							oneRecord.put(bKey, value);
						}
						UpNodes.put(aKey, oneRecord);
					}
				}
			}
			
			if(jsondataDeletetItems!= null){
				for(Iterator<String> key = jsondataDeletetItems.keys();key.hasNext();){
					String aKey = key.next();
					String value = jsondataDeletetItems.getString(aKey);
					if(value == null) continue;
					if(value.equals("node")){
						delNodes.add(aKey);
					}else if(value.equals("line")){
						delLines.add(aKey);
					}
				}
			}
			
		} catch (JSONException e) {
			this.setEchoValue("json parse error: " + e.toString());
			return NO_FORWARD;
		}

		DODataSource aDS = bo.getDataBase();
		Transaction aTran = aDS.getTransaction();

		aTran.begin();
		try {
			if(UpNodes.keySet().size()>0){
				updateNodes(UpNodes,aDS);
			}
			if(UpLines.keySet().size()>0){
				updateLines(UpLines,aDS);
			}
			 
			if(delNodes.size() >0 ){
				delNodes(delNodes,aDS);
			}
			
			if(delLines.size() >0 ){
				delLines(delLines,aDS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.setEchoValue("excute sql error!");
			aTran.rollback();
		}
		aTran.end();

		return DEFAULT_FORWARD;
	}
	
	private void delLines(List<String> delLines, DODataSource aDS) throws SQLException  {
		PreparedStatement aps = aDS.getContextConnection().prepareStatement(strSqlDelLine);
		for(Iterator<String> keys = delLines.iterator();keys.hasNext();){
			aps.setString(1, keys.next());
			aps.addBatch();
			aps.clearParameters();
		}
		aps.executeBatch();
	}

	private void delNodes(List<String> delNodes, DODataSource aDS) throws SQLException  {
		PreparedStatement aps = aDS.getContextConnection().prepareStatement(strSqlDelNode);
		for(Iterator<String> keys = delNodes.iterator();keys.hasNext();){
			aps.setString(1, keys.next());
			aps.addBatch();
			aps.clearParameters();
		}
		aps.executeBatch();
	}

	private void updateLines(Map<String, Map<String, String>> upLines,
			DODataSource aDS) throws SQLException  {
		PreparedStatement aps = aDS.getContextConnection().prepareStatement(strSqlAddLine);
		for(Iterator<String> keys = upLines.keySet().iterator();keys.hasNext();){
			String key = keys.next();
			for(int i=0,len = strsSqlLineInsert.length;i<len;i++){
				String idName = strsSqlLineInsert[i];
				Map<String,String> record = upLines.get(key);
				String colName = mapLineColName.get(idName);
				if(colName!=null){
					String colvalue = record.get(colName);
					if(colvalue!=null && colvalue.trim().equals("")){
						colvalue = null;
					}
					aps.setString(i+1,colvalue);
				}else{
					aps.setString(i+1,null);
				}
				System.out.println((i+1) + ": " + colName);
			}
			aps.addBatch();
			aps.clearParameters();
		}
		aps.executeBatch();
	}

	private void updateNodes(Map<String, Map<String, String>> upNodes,
			DODataSource aDS) throws SQLException {
		PreparedStatement aps = aDS.getContextConnection().prepareStatement(strSqlAddNode);
		for(Iterator<String> keys = upNodes.keySet().iterator();keys.hasNext();){
			String key = keys.next();
			for(int i=0,len = strsSqlNodeInsert.length;i<len;i++){
				String idName = strsSqlNodeInsert[i];
				Map<String,String> record = upNodes.get(key);
				String colName = mapNodeColName.get(idName);
				if(colName!=null){
					String colvalue = record.get(colName);
					if(idName.equals("nodetype")){
						Integer ii = mapNodeType.get(colvalue);
						if(ii!=null){
							colvalue = ii.toString();
						}else{
							colvalue = "1";
						}
					}else if(colvalue!=null && colvalue.trim().equals("")){
						colvalue = null;
					}
					aps.setString(i+1,colvalue);
				}else{
					aps.setString(i+1,null);
				}
				System.out.println((i+1) + ": " + colName);
			}
			aps.addBatch();
			aps.clearParameters();
		}
		aps.executeBatch();
	}
	
	
	private static Map<String,Integer> mapNodeType = new HashMap<String,Integer>();
	private static Map<String,String> mapLineColName = new HashMap<String,String>();
	private static Map<String,String> mapNodeColName = new HashMap<String,String>();
	static{
		mapNodeType.put("activity", 1);
		mapNodeType.put("autoNode", 21);
		mapNodeType.put("andDecision", 4);
		mapNodeType.put("xorDecision", 5);
		mapNodeType.put("andConjuction", 6);
		mapNodeType.put("subFlow", 8);
		mapNodeType.put("startNode", 2);
		mapNodeType.put("endNode", 3);
		
		mapLineColName.put("objuid", "id");
		mapLineColName.put("pre_n_uid", "from");
		mapLineColName.put("post_n_uid", "to");
		mapLineColName.put("do_condition", "condition");
		mapLineColName.put("show_value", "name");
//		mapLineColName.put("creator", "id");
//		mapLineColName.put("creatdate", "id");
//		mapLineColName.put("modifier", "id");
//		mapLineColName.put("modifydate", "id");
//		mapLineColName.put("mversion", "id");
		
		mapNodeColName.put("objuid","id");
		mapNodeColName.put("nodename","name");
		mapNodeColName.put("nodedesc","nodedesc");
		mapNodeColName.put("nodestateshow","nodestateshow");
		mapNodeColName.put("nodestateshowback","nodestateshowback");
		mapNodeColName.put("pt_uid","ptuid");
		mapNodeColName.put("nodetype","type");
		mapNodeColName.put("authtype","authtype");
		mapNodeColName.put("spec_name","specname");
//		mapNodeColName.put("xml_schema","id");
		mapNodeColName.put("decisiontype","decisiontype");
		mapNodeColName.put("decisionexpression","decisionexpression");
//		mapNodeColName.put("subexecutorstatus","id");
		mapNodeColName.put("autoserviceuid","autoservice");
//		mapNodeColName.put("paneuid","id");
//		mapNodeColName.put("donepaneuid","id");
//		mapNodeColName.put("resultpaneuid","id");
		mapNodeColName.put("accessclass","accessclass");
		mapNodeColName.put("pass_txt","passtxt");
		mapNodeColName.put("reject_txt","rejecttxt");
//		mapNodeColName.put("retnodeuid","id");
		mapNodeColName.put("node_ext1","subflow");
//		mapNodeColName.put("node_ext2","id");
		mapNodeColName.put("x","left");
		mapNodeColName.put("y","top");
//		mapNodeColName.put("creator","id");
//		mapNodeColName.put("creatdate","id");
//		mapNodeColName.put("modifier","id");
//		mapNodeColName.put("modifydate","id");
//		mapNodeColName.put("mversion","id");
	}
	
	private static final String[] strsSqlLineInsert = {"objuid","pre_n_uid"
		,"post_n_uid"
		,"do_condition"
		,"show_value"
		,"creator"
		,"creatdate"
		,"modifier"
		,"modifydate"
		,"mversion"};
	private static final String[] strsSqlNodeInsert = {"objuid","nodename"
		,"nodedesc"
		,"nodestateshow"
		,"nodestateshowback"
		,"pt_uid"
		,"nodetype"
		,"authtype"
		,"spec_name"
		,"xml_schema"
		,"decisiontype"
		,"decisionexpression"
		,"subexecutorstatus"
		,"autoserviceuid"
		,"paneuid"
		,"donepaneuid"
		,"resultpaneuid"
		,"accessclass"
		,"pass_txt"
		,"reject_txt"
		,"retnodeuid"
		,"node_ext1"
		,"node_ext2"
		,"x"
		,"y"
		,"creator"
		,"creatdate"
		,"modifier"
		,"modifydate"
		,"mversion"};
	private static final String strSqlAddNode = "replace into do_pt_node(objuid"
			+ ",nodename"
			+ ",nodedesc"
			+ ",nodestateshow"
			+ ",nodestateshowback"
			+ ",pt_uid"
			+ ",nodetype"
			+ ",authtype"
			+ ",spec_name"
			+ ",xml_schema"
			+ ",decisiontype"
			+ ",decisionexpression"
			+ ",subexecutorstatus"
			+ ",autoserviceuid"
			+ ",paneuid"
			+ ",donepaneuid"
			+ ",resultpaneuid"
			+ ",accessclass"
			+ ",pass_txt"
			+ ",reject_txt"
			+ ",retnodeuid"
			+ ",node_ext1"
			+ ",node_ext2"
			+ ",x"
			+ ",y"
			+ ",creator"
			+ ",creatdate"
			+ ",modifier"
			+ ",modifydate"
			+ ",mversion) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String strSqlDelNode = "delete from do_pt_node where objuid = ?";
	private static final String strSqlUpNode = "update do_pt_node set nodename=?"
			+ ",nodedesc=?"
			+ ",nodestateshow=?"
			+ ",nodestateshowback=?"
			+ ",pt_uid=?"
			+ ",nodetype=?"
			+ ",authtype=?"
			+ ",spec_name=?"
			+ ",xml_schema=?"
			+ ",decisiontype=?"
			+ ",decisionexpression=?"
			+ ",subexecutorstatus=?"
			+ ",autoserviceuid=?"
			+ ",paneuid=?"
			+ ",donepaneuid=?"
			+ ",resultpaneuid=?"
			+ ",accessclass=?"
			+ ",pass_txt=?"
			+ ",reject_txt=?"
			+ ",retnodeuid=?"
			+ ",node_ext1=?"
			+ ",node_ext2=?"
			+ ",x=?"
			+ ",y=?"
			+ ",creator=?"
			+ ",creatdate=?"
			+ ",modifier=?"
			+ ",modifydate=?"
			+ ",mversion=? "
			+ "where objuid = ?";

	private static final String strSqlAddLine = "replace into do_pt_node_denpendency(objuid"
			+ ",pre_n_uid"
			+ ",post_n_uid"
			+ ",do_condition"
			+ ",show_value"
			+ ",creator"
			+ ",creatdate"
			+ ",modifier"
			+ ",modifydate"
			+ ",mversion)"
			+ " values(?,?,?,?,?,?,?,?,?,?)";
	private static final String strSqlDelLine = "delete from do_pt_node_denpendency where objuid = ?";
	private static final String strSqlUpLine = "update do_pt_node_denpendency set pre_n_uid=?"
			+ ",post_n_uid=?"
			+ ",do_condition=?"
			+ ",show_value=?"
			+ ",creator=?"
			+ ",creatdate=?"
			+ ",modifier=?"
			+ ",modifydate=?"
			+ ",mversion=?"
			+ " where objuid = ?";
}
