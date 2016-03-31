package com.exedosoft.plat.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.util.I18n;




public class DealSelectedsPara extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4798265805984034747L;
	private static Log log = LogFactory.getLog(DealSelectedsPara.class);

	public DealSelectedsPara() {
	}

	public String excute()  {

		if (this.service==null || this.service.getTempSql() == null) {
			setEchoValue(I18n.instance().get("未配置SQL 语句"));
		}
		
		String[] checks = this.actionForm.getValueArray("checkinstance");
		
		
		if (checks == null || checks.length==0 ) {
			setEchoValue(I18n.instance().get("没有数据"));
			return NO_FORWARD;
		}
		
		if(checks[0]==null || checks[0].equals("")){
			return NO_FORWARD;
		}
		
		if(checks[0].indexOf(",")!=-1){
			checks = checks[0].split(","); 
		}
		
		try {
			this.service.beginBatch();
			for(int i = 0; i < checks.length ; i++){
				if(checks[i]==null || checks[i].equals("")){
					continue;
				}
				Map aMap = new HashMap();
				aMap.put("checkinstance", checks[i]);
				this.service.addBatch(aMap);			
			}
			this.service.endBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DEFAULT_FORWARD;
	}

}