package com.exedosoft.plat.weixin;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.weixin.util.HttpUtil;

public class WUser {
	
	
	public final static String user_get_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";

	public final static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


	public WUser() {
		// TODO Auto-generated constructor stub
	}

	private String subscribe;
	private String openid;
	private String nickname;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private String subscribe_time;
	private String unionid;
	private String remark;
	private String groupid;

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	
	public static WUser getWUser(String openid){
		
		
		
		String token = AccessToken.getAccessToken().getToken();

		String requestUrl = user_info_url.replace("ACCESS_TOKEN",
				token).replace("OPENID", openid);

		String retMsg = HttpUtil.sendHttpsGET(requestUrl);
		
		System.out.println("retMsg::" + retMsg) ;
		
		WUser u = new WUser();
		try {
			JSONObject jo = new JSONObject(retMsg);
			u.setCity(jo.getString("city"));
			u.setCountry(jo.getString("country"));
			u.setGroupid(jo.getString("groupid"));
			u.setHeadimgurl(jo.getString("headimgurl"));
			u.setLanguage(jo.getString("language"));
			u.setNickname(jo.getString("nickname"));
			u.setOpenid(jo.getString("openid"));
			u.setProvince(jo.getString("province"));
			u.setRemark(jo.getString("remark"));
			u.setSubscribe(jo.getString("subscribe"));
			u.setSubscribe_time(jo.getString("subscribe_time"));
		//	u.setUnionid(jo.getString("unionid"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return u;
	}
	
	public String toString() {

		return ToStringBuilder.reflectionToString(this) + "\n";
	}
	
	
	public static void main(String[] args){
		
		
		DOGlobals.getInstance();
		
		WUser u = WUser.getWUser("oBy_Mvk29ISZFDV16Dn2sPBvddqI");
		
		System.out.println("Weixin User::" + u);
		
		
		
	}
	
}
