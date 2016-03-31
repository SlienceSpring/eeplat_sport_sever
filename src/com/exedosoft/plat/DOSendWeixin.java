package com.exedosoft.plat;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOServiceRedirect;
import com.exedosoft.plat.weixin.initiative.TemplateMessage;

public class DOSendWeixin extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4182943988305938731L;
	public static final String WEIXIN_APPID = "weixin.appid";
	public static final String WEIXIN_SECRET = "weixin.secret";
	
	private static Log log = LogFactory.getLog(DOSendWeixin.class);


	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		if (this.service == null) {
			return NO_FORWARD;
		}

		if (this.actionForm == null) {
			return NO_FORWARD;
		}

		/**
		 * 首先实现的是更新操作
		 */
		if(this.service.getMainSql()!=null){
			if(this.service.isUpdate()){
				long currentDate = System.currentTimeMillis();
				this.service.invokeUpdate();
			}
		}

		List<DOServiceRedirect> list = this.service.retrieveServiceRedirect();
		if (list != null && list.size() > 0) {

			for (DOServiceRedirect dsr : list) {
				if (dsr.getPaneModel() != null) {

					String context = dsr.getPaneModel().getHtmlCode();
					try {
						String retMsg = TemplateMessage.sendWeixin(context);
						JSONObject jsonObject = new JSONObject(retMsg);
						if (jsonObject != null) {
							if ("0".equals(jsonObject.getString("errcode"))) {
								log.info("Sucess:发送模板消息成功！");
							} else {
								log.info(jsonObject.toString());
							}
						}
					}  catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}
		return DEFAULT_FORWARD;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
