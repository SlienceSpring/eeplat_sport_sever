package com.exedosoft.plat.weixin.initiative;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.template.HtmlTemplateGenerator;
import com.exedosoft.plat.weixin.AccessToken;
import com.exedosoft.plat.weixin.Config;
import com.exedosoft.plat.weixin.util.HttpUtil;

public class TemplateMessage {

	private static Log log = LogFactory.getLog(TemplateMessage.class);

	public final static String message_template_send_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	public TemplateMessage() {
		// TODO Auto-generated constructor stub
	}

	public static void sendTemplateMessage() {

		String ftl = "/weixin/interview_invite.ftl";

		Map<String, String> data = new HashMap();
		data.put("opendid", "oBy_Mvk29ISZFDV16Dn2sPBvddqI");
		data.put("company", "北京云荷素科技有限公司");
		data.put("datetime", "2015年12月1日上午");
		data.put("address", "北京昌平回龙观");
		data.put("contact", "魏可鑫");
		data.put("tel", "13910733521");

		try {
			String text = HtmlTemplateGenerator.getContentFromTemplate(ftl,
					data);

			String retMsg = sendWeixin(text);
			JSONObject jsonObject = new JSONObject(retMsg);
			// JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
			// "POST",
			// text);
			if (jsonObject != null) {
				if ("0".equals(jsonObject.getString("errcode"))) {
					System.out.println("发送模板消息成功！");
				} else {
					System.out.println(jsonObject.getString("errcode"));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String sendWeixin(String text) {
		String token = AccessToken.getAccessToken().getToken();

		String requestUrl = message_template_send_url.replace("ACCESS_TOKEN",
				token);

		String retMsg = HttpUtil.sendHttpsPOST(requestUrl, text);
		return retMsg;
	}



	public static void main(String[] args) {

		// String token = Message.getAccessToken().getToken();
		//
		// System.out.println("token::" + token);
		// Message.sendTemplateMessage();

		// JSONObject userList =
		// WeixinUtil.httpRequest(user_get_url.replaceAll("ACCESS_TOKEN",
		// token), "GET",null);
		//
		// try {
		// JSONArray ja = userList.getJSONObject("data").getJSONArray("openid");
		//
		// for(int i = 0; i < ja.length() ; i++){
		// String theOpenId = ja.getString(i);
		// JSONObject user = WeixinUtil.httpRequest(user_info_url
		// .replace("ACCESS_TOKEN", token)
		// .replace("OPENID", theOpenId), "GET",null);
		//
		// System.out.println("OpenId::" + theOpenId);
		// System.out.println("User::" + user);
		//
		// System.out.println("==========================================");
		// System.out.println("==========================================");
		//
		//
		// }
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// JSONObject jsonObject = WeixinUtil.httpRequest(user_get_url
		// .replace("ACCESS_TOKEN", token)
		// .replace("OPENID", "oBy_Mvm6BniZgYVMOMGPxZ-S81oU"), "GET",null);

		// token::P0Fd6zStwqHvI-_v2WKOwt9C8kaFchfEfvoRH4oqqZpxnXBMq8vkOk5nMDHpwxzZ61E8XmhiqwILTIGHozyQP0ElW7CNF-HzQw4XJcUDDeo
		// {"total":13,"count":13,"data":{"openid":["oBy_MvqsIct2QWb8nHDlICBP39Rs","oBy_Mvm6BniZgYVMOMGPxZ-S81oU","oBy_MviUdbxz_5bOj7-pkpBYFa3o","oBy_MvjL8ED1qT67_u3Z6eUYI2Dc","oBy_MvjBs9UcjoAnkszFBmecAmHA","oBy_MvjO0Th1ClqkeHJsC6R1p4E8","oBy_MvgLVjRxdad8CNVFEiH2Hf2U","oBy_Mvk29ISZFDV16Dn2sPBvddqI","oBy_MvicMP9t_WW5tuO9l58a28EM","oBy_Mvk8d0-a5Ab0FKTQbxdLqTnY","oBy_Mvv2TEfrpIpEuu-REC1z7u3A","oBy_MvqULwuun2sqEGz7-A0BpcMU","oBy_Mvlt-mnqXgUcwJ10W58SzJYo"]},"next_openid":"oBy_Mvlt-mnqXgUcwJ10W58SzJYo"}

		// System.out.println(jsonObject);

		TemplateMessage.sendTemplateMessage();
		
		//System.out.println(System.currentTimeMillis());

	}

}
