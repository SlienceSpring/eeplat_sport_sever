package com.exedosoft.plat.weixin.qrcode;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.util.sequence.DOMAXIDBuilder;
import com.exedosoft.plat.weixin.AccessToken;
import com.exedosoft.plat.weixin.util.HttpUtil;

public class QRCode {

	public final static String qrcode_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	public static String createTempQRCode(String screne_id) {

		String token = AccessToken.getAccessToken().getToken();

		System.out.println("Token::" + token);

		String requestUrl = qrcode_url.replace("TOKEN", token);

		JSONObject jo = new JSONObject();
		try {
			jo.put("expire_seconds", "604800");
			jo.put("action_name", "QR_SCENE");

			JSONObject joScene = new JSONObject();
			joScene.put("scene_id", screne_id);

			JSONObject joInfo = new JSONObject();
			joInfo.put("scene", joScene);

			jo.put("action_info", joInfo);

			System.out.println("jo::" + jo.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String retMsg = HttpUtil.sendHttpsPOST(requestUrl, jo.toString());

		String ticket = "";
		try {
			JSONObject ret = new JSONObject(retMsg);
			ticket = ret.getString("ticket");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}

	public static String createPermanentQRCode(String screne_id) {

		String token = AccessToken.getAccessToken().getToken();

		System.out.println("Token::" + token);

		String requestUrl = qrcode_url.replace("TOKEN", token);

		JSONObject jo = new JSONObject();
		try {
			jo.put("action_name", "QR_LIMIT_STR_SCENE");

			JSONObject joScene = new JSONObject();
			joScene.put("scene_str", screne_id);

			JSONObject joInfo = new JSONObject();
			joInfo.put("scene", joScene);

			jo.put("action_info", joInfo);

			System.out.println("jo::" + jo.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String retMsg = HttpUtil.sendHttpsPOST(requestUrl, jo.toString());
		System.out.println("retMsg::" + retMsg);

		String ticket = "";
		try {
			JSONObject ret = new JSONObject(retMsg);
			ticket = ret.getString("ticket");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}

	public static int getSceneId() {
		String code = "";
		for (int i = 0; i < 9; i++) {
			code += (int) (Math.random() * 9);
		}
		return Integer.parseInt(code);
	}

	public static String getQRCodeURL(String ticket) {
		try {
			return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
					+ java.net.URLEncoder.encode(ticket, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return ticket;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGR8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1MwVi1oZUxtRVV0bzBMVlFlMnZEAAIE09q6VQMEgDoJAA==

//		String screne_id = "" + getSceneId();
//		String ticket = createTempQRCode(screne_id);
//
//		System.out.println(QRCode.getQRCodeURL(ticket));
		
		int aID = DOMAXIDBuilder.getInstance().getID(
				"QRCODE");
		
		String screne_id = "A_" + aID;
		String ticket = createPermanentQRCode(screne_id);

		System.out.println(QRCode.getQRCodeURL(ticket));


	}

}
