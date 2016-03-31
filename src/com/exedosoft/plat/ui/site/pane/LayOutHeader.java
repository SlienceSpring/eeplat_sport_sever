package com.exedosoft.plat.ui.site.pane;

import java.util.Map;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.pane.TPaneTemplate;
import com.exedosoft.plat.util.DOGlobals;


public class LayOutHeader extends TPaneTemplate {

	public LayOutHeader() {
		dealTemplatePath(  "/panel/LayOutHeader.ftl" );
	}

	public Map<String, Object> putData(DOIModel doimodel) {
		
		Map<String,Object> data = super.putData(doimodel);
		if(DOGlobals.getInstance().getSessoinContext()!=null && DOGlobals.getInstance().getSessoinContext().getUser()!=null){
			
			BOInstance biUser = DOGlobals.getInstance().getSessoinContext().getUser(); 
			String theValue = biUser.getName();
			String isMember = biUser.getValue("is_member");
	
			System.out.println("isMemberisMember:::" + isMember);
			
			data.put("loginName", theValue);
			if("true".equals(isMember)){
				data.put("isMember", "true");
			}else{
				data.put("isMember", "false");
			}
		}
		
		return data;
		
	}
}
