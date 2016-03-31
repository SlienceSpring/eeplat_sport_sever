package com.exedosoft.plat.action.zidingyi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.SSOController;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import zk.jni.JavaToBiokey;
/**
 * JSONObject 创建一个JSON对象并out.write();
 * @author Dana·Li
 */
public class CompareOneServlet extends DOAbstractAction{

	/***
	 * 接收json格式：{type:"1",data:["11111","22222","33333","44444"]}
	 * 返回json格式：{"results":"200","details":["X","XX","XXX","XXXX"]}
	 * 
	 * //type = 1,全国比;=2,本省比;不填写,全国比	本省的没做	
	 */
	public String excute() {
		

		Map outmap = new HashMap(); 
		StringBuffer Sb = new StringBuffer();
		//获取参数，并处理.json数据格式::

		String type = DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("type");
		String id = DOGlobals.getInstance().getSessoinContext().getFormInstance().getValue("id");

		System.out.println(type);
		System.out.println(id);
		if(type  == null || id == null){
			return null;
		}
		DOService service;
		if(type.equals("1")){
			service = DOService.getService("sa_athlete_info_list");
		}else{
			service = DOService.getService("sa_athlete_info_list");
		}
			
		DOService service1 = DOService.getService("sa_athlete_info_browse");
		
		
		StringBuffer sb1 = new StringBuffer();
		
		List<BOInstance> ls = service1.invokeSelect(id);
		List<BOInstance> ls2 = service.invokeSelect();
		List list = new ArrayList();
		org.json.JSONObject jsonObj1 = new org.json.JSONObject();
		BOInstance bi = null;
		//2传过来的与库中比较
		try {
			jsonObj1=toolCompareList(ls,ls2);
			
			jsonObj1.put("results", "200");	
			System.out.println(jsonObj1.toString());
			bi = BOInstance.fromJSONObject(jsonObj1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setInstance(bi);
		
		
//		this.setInstances(ls);
		
		return DEFAULT_FORWARD;

    }  
	/***
	 * 指纹比对算法
	 * {"diff1":[{"id":"1111","data":["X","XX","XXX","XXXX"]},{"id":"22222","data":["X","XX","XXX","XXXX"]}]}
	 * @param ls1
	 * @param ls2
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject toolCompareList(List<BOInstance> ls1,List<BOInstance> ls2) throws JSONException{
		JSONObject js = new JSONObject();
		
		if (ls1 != null && ls1.size() > 0) {
			BOInstance bi1=new BOInstance();
			for (int i = 0; i < ls1.size(); i++) {
				JSONArray jsonStrs = new JSONArray();
				BOInstance bi2=new BOInstance();
				bi1 = ls1.get(i);
				for(int j=0;j<ls2.size(); j++){
					bi2 =ls2.get(j);
					if(compare(bi1,bi2)){
						jsonStrs.put(bi2.getValue("id"));//data:["X","XX","XXX","XXXX"]
					}
				}
				if(jsonStrs.length()>0){
					js.put("details", jsonStrs);//{"id":"1111","data":["X","XX","XXX","XXXX"]}
				
				}
				
			}
			
		}
		
		return js;	
	}
	
	
	
	
	public static String toolJsonObject2String(JSONArray ja) throws JSONException{
		String str = "";
		if(ja.length()==0)
			return "";
		if(ja.length()==1){
			str = "\'"+ja.getJSONObject(0)+"\'";
		}else {
			for(int i=0;i<ja.length();i++){
				 if(i>0 && i< ja.length()){
			            str += ",";
			        }
				str+="\'"+ja.getJSONObject(i).get("id")+"\'";
			}
			
		}
		
		return str;
	}
//		
//		Map map = new HashMap(); 
//		
//		map.put("name", "Dana、Li"); 
//		map.put("age", new Integer(22)); 
//		map.put("Provinces", new String("广东省")); 
//		map.put("citiy", new String("珠海市")); 
//		map.put("Master", new String("C、C++、Linux、Java"));
//		JSONObject json = JSONObject.fromObject(map); 
//		  
//		out.write(json.toString());
//		out.flush();
//		out.close();
//    }
    
    
	/**
	 * 
	 * @param bi1
	 * @param bi2
	 * @return
	 */
	public static boolean compare(BOInstance bi1,BOInstance bi2) {
		//判断为同一人，不比对指纹，直接返回
		if(bi1.getValue("id").equals(bi2.getValue("id"))) return false;
		//判断性别不一致，不对比指纹，直接返回
		if(bi1.getValue("sex").equals(bi2.getValue("sex")) 
				&& bi1.getValue("feature_extract")!=null && bi2.getValue("feature_extract")!=null){
				if (JavaToBiokey.NativeToProcess(bi1.getValue("feature_extract"), bi2.getValue("feature_extract"))){
					return true;
				}else{
					return false;
				}	
		}
		return false;
	}
	
	
	/**
	  * 写入日志
	  * filePath 日志文件的路径
	  * code 要写入日志文件的内容
	  */
	public static boolean print(String filePath,String code) {
		try {
			File tofile=new File(filePath);
			FileWriter fw=new FileWriter(tofile,true);
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间
			
			System.out.println(date +"==="+code);
			pw.println(date +"==="+code);
			pw.close();
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
    
    
    
    
    
    
    
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    /*	StringBuffer sb=new StringBuffer();
    	sb.append("{")
    	.append("type:\"1\",")
    	.append("data:[")
    	.append("\'11111\',")
    	.append("\'22222\',")
    	.append("\'333333\',")
    	.append("]}");
    	StringBuffer sb2=new StringBuffer();
		sb2.append("{")
		.append("type:\"1\",")
		.append("data:[")
		.append("{")
		.append("id:\"11111\"")
		.append("},{")
		.append("id:\"22222\"")
		.append("},{")
		.append("id:\"33333\"")
		.append("},{")
		.append("id:\"44444\"")
		.append("}")
		.append("]")
		.append("}");
		System.out.println("json数据格式::"+sb.toString());
		JSONObject tencyObj=JSONObject.fromObject(sb.toString());
		JSONObject tencyObj2=JSONObject.fromObject(sb2.toString());
		JSONObject tencyObj3 = new JSONObject();
		tencyObj3.put("differ1", tencyObj);
		tencyObj3.put("differ2", tencyObj2);
		System.out.println(tencyObj3.toString());
		
		String type = tencyObj.getString("type");  
		String str = tencyObj.getJSONArray("data").toString();
//		String data= tencyObj.getString("data");
		System.out.println(type);
//		String str = toolJsonObject2String(ja);
		System.out.println(str.replace("[","").replace("]",""));
			  */
	/*	JSONArray jsonStrs = new JSONArray();
		jsonStrs.add(0, "cat");
		jsonStrs.add(1, "dog");
		jsonStrs.add(2, "bird");
		jsonStrs.add(3, "duck"); 
		System.out.println(jsonStrs.toString());*/
		
	        
	}

}

