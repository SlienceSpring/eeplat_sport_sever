package com.exedosoft.plat.weixin.redpacket;

public class RedPacket {

	private String sign;
	private String mch_billno;
	private String mch_id;
	private String sub_mch_id;
	private String wxappid;
	private String nick_name;
	private String send_name;
	private String re_openid;
	private String total_amount;
	private String min_value;
	private String max_value;
	private String total_num;
	private String wishing;
	private String client_ip;
	private String act_name;
	private String act_id;
	private String remark;
	private String logo_imgurl;
	private String share_content;
	private String share_url;
	private String share_imgurl;
	private String nonce_str;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMch_billno() {
		return mch_billno;
	}

	public void setMch_billno(String mchBillno) {
		mch_billno = mchBillno;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mchId) {
		mch_id = mchId;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getWxappid() {
		return wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nickName) {
		nick_name = nickName;
	}

	public String getSend_name() {
		return send_name;
	}

	public void setSend_name(String sendName) {
		send_name = sendName;
	}

	public String getRe_openid() {
		return re_openid;
	}

	public void setRe_openid(String reOpenid) {
		re_openid = reOpenid;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String totalAmount) {
		total_amount = totalAmount;
	}

	public String getMin_value() {
		return min_value;
	}

	public void setMin_value(String minValue) {
		min_value = minValue;
	}

	public String getMax_value() {
		return max_value;
	}

	public void setMax_value(String maxValue) {
		max_value = maxValue;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String totalNum) {
		total_num = totalNum;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String clientIp) {
		client_ip = clientIp;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String actName) {
		act_name = actName;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogo_imgurl() {
		return logo_imgurl;
	}

	public void setLogo_imgurl(String logoImgurl) {
		logo_imgurl = logoImgurl;
	}

	public String getShare_content() {
		return share_content;
	}

	public void setShare_content(String shareContent) {
		share_content = shareContent;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String shareUrl) {
		share_url = shareUrl;
	}

	public String getShare_imgurl() {
		return share_imgurl;
	}

	public void setShare_imgurl(String shareImgurl) {
		share_imgurl = shareImgurl;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}

	public String toXml2() {

		// <xml>
		// <sign><![CDATA[E1EE61A91C8E90F299DE6AE075D60A2D]]></sign>
		// <mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
		// <mch_id><![CDATA[1000888888]]></mch_id>
		// <wxappid><![CDATA[wxcbda96de0b165486]]></wxappid>
		// <nick_name><![CDATA[nick_name]]></nick_name>
		// <send_name><![CDATA[send_name]]></send_name>
		// <re_openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6uI]]></re_openid>
		// <total_amount><![CDATA[200]]></total_amount>
		// <min_value><![CDATA[200]]></min_value>
		// <max_value><![CDATA[200]]></max_value>
		// <total_num><![CDATA[1]]></total_num>
		// <wishing><![CDATA[恭喜发财]]></wishing>
		// <client_ip><![CDATA[127.0.0.1]]></client_ip>
		// <act_name><![CDATA[新年红包]]></act_name>
		// <act_id><![CDATA[act_id]]></act_id>
		// <remark><![CDATA[新年红包]]></remark>
		// <logo_imgurl><![CDATA[https://xx/img/wxpaylogo.png]]></logo_imgurl>
		// <share_content><![CDATA[share_content]]></share_content>
		// <share_url><![CDATA[https://xx/img/wxpaylogo.png]]></share_url>
		// <share_imgurl><![CDATA[https:/xx/img/wxpaylogo.png]]></share_imgurl>
		// <nonce_str><![CDATA[50780e0cca98c8c8e814883e5caa672e]]></nonce_str>
		// </xml>

		StringBuilder sb = new StringBuilder("<xml>\n");
		sb.append("<sign><![CDATA[").append(this.sign).append("]]></sign>\n");
		sb.append("<mch_billno><![CDATA[").append(this.mch_billno)
				.append("]]></mch_billno>\n");
		sb.append("<mch_id><![CDATA[").append(this.mch_id)
				.append("]]></mch_id>\n");
		sb.append("<wxappid><![CDATA[").append(this.wxappid)
				.append("]]></wxappid>\n");
		sb.append("<nick_name><![CDATA[").append(this.nick_name)
				.append("]]></nick_name>\n");
		sb.append("<send_name><![CDATA[").append(this.send_name)
				.append("]]></send_name>\n");
		sb.append("<re_openid><![CDATA[").append(this.re_openid)
				.append("]]></re_openid>\n");
		sb.append("<total_amount><![CDATA[").append(this.total_amount)
				.append("]]></total_amount>\n");
		sb.append("<min_value><![CDATA[").append(this.min_value)
				.append("]]></min_value>\n");
		sb.append("<max_value><![CDATA[").append(this.max_value)
				.append("]]></max_value>\n");
		sb.append("<total_num><![CDATA[").append(this.total_num)
				.append("]]></total_num>\n");
		if (this.wishing != null) {
			sb.append("<wishing><![CDATA[").append(this.wishing)
					.append("]]></wishing>\n");
		}
		if (this.client_ip != null) {
			sb.append("<client_ip><![CDATA[").append(this.client_ip)
					.append("]]></client_ip>\n");
		}
		if (this.act_name != null) {
			sb.append("<act_name><![CDATA[").append(this.act_name)
					.append("]]></act_name>\n");
		}
		if (this.act_id != null) {
			sb.append("<act_id><![CDATA[").append(this.act_id)
					.append("]]></act_id>\n");
		}
		if (this.remark != null) {
			sb.append("<remark><![CDATA[").append(this.remark)
					.append("]]></remark>\n");
		}
		if (this.logo_imgurl != null) {
			sb.append("<logo_imgurl><![CDATA[").append(this.logo_imgurl)
					.append("]]></logo_imgurl>\n");
		}
		if (this.share_content != null) {
			sb.append("<share_content><![CDATA[").append(this.share_content)
					.append("]]></share_content>\n");
		}
		if (this.share_url != null) {
			sb.append("<share_url><![CDATA[").append(this.share_url)
					.append("]]></share_url>\n");
		}
		if (this.share_imgurl != null) {
			sb.append("<share_imgurl><![CDATA[").append(this.share_imgurl)
					.append("]]></share_imgurl>\n");
		}
		sb.append("<nonce_str><![CDATA[").append(this.nonce_str)
				.append("]]></nonce_str>\n");

		sb.append("</xml>");

		return sb.toString();

	}

	public String toXml() {

		StringBuilder sb = new StringBuilder("<xml>\n");
		sb.append("<sign>").append(this.sign).append("</sign>\n");
		sb.append("<mch_billno>").append(this.mch_billno)
				.append("</mch_billno>\n");
		sb.append("<mch_id>").append(this.mch_id).append("</mch_id>\n");
		sb.append("<wxappid>").append(this.wxappid).append("</wxappid>\n");
		sb.append("<nick_name>").append(this.nick_name)
				.append("</nick_name>\n");
		sb.append("<send_name>").append(this.send_name)
				.append("</send_name>\n");
		sb.append("<re_openid>").append(this.re_openid)
				.append("</re_openid>\n");
		sb.append("<total_amount>").append(this.total_amount)
				.append("</total_amount>\n");
		sb.append("<min_value>").append(this.min_value)
				.append("</min_value>\n");
		sb.append("<max_value>").append(this.max_value)
				.append("</max_value>\n");
		sb.append("<total_num>").append(this.total_num)
				.append("</total_num>\n");
		if (this.wishing != null) {
			sb.append("<wishing>").append(this.wishing).append("</wishing>\n");
		}
		if (this.client_ip != null) {
			sb.append("<client_ip>").append(this.client_ip)
					.append("</client_ip>\n");
		}
		if (this.act_name != null) {
			sb.append("<act_name>").append(this.act_name)
					.append("</act_name>\n");
		}
		if (this.act_id != null) {
			sb.append("<act_id>").append(this.act_id).append("</act_id>\n");
		}
		if (this.remark != null) {
			sb.append("<remark>").append(this.remark).append("</remark>\n");
		}
		if (this.logo_imgurl != null) {
			sb.append("<logo_imgurl>").append(this.logo_imgurl)
					.append("</logo_imgurl>\n");
		}
		if (this.share_content != null) {
			sb.append("<share_content>").append(this.share_content)
					.append("</share_content>\n");
		}
		if (this.share_url != null) {
			sb.append("<share_url>").append(this.share_url)
					.append("</share_url>\n");
		}
		if (this.share_imgurl != null) {
			sb.append("<share_imgurl>").append(this.share_imgurl)
					.append("</share_imgurl>\n");
		}
		sb.append("<nonce_str>").append(this.nonce_str)
				.append("</nonce_str>\n");

		sb.append("</xml>");

		return sb.toString();

	}

}
