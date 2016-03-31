package com.exedosoft.plat.weixin;

import com.exedosoft.plat.util.DOGlobals;

/**
 * 配置文件
 * 
 * @author marker
 * @date 2014年8月30日
 * @version 1.0
 */
public interface Config {

	// 赋权类型
	String grant_type = "client_credential";

	// 修改为开发者申请的appid
	String APPID = DOGlobals.getValue("weixin.appid");

	// 修改为开发者申请的secret密钥
	String SECRET = DOGlobals.getValue("weixin.secret");

	String MCH_ID = DOGlobals.getValue("weixin.mch.id");

	String PAY_SECRET = DOGlobals.getValue("weixin.pay.secret");
}
