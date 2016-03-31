package com.exedosoft.plat.weixin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.weixin.util.HttpUtil;

/**
 * 微信通用接口凭证
 * 
 * @date 2013-08-08
 */
public class AccessToken {
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ Config.APPID + "&secret=" + Config.SECRET;

	private static Log log = LogFactory.getLog(AccessToken.class);

	
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = null;

		String retMsg = HttpUtil.sendHttpsGET(access_token_url);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(retMsg);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// JSONObject jsonObject = WeixinUtil.httpRequest(access_token_url,
		// "GET",
		// null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.equals("获取token失败 ::" + jsonObject.toString());
			}
		}
		return accessToken;
	}
}