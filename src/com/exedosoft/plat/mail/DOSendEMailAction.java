package com.exedosoft.plat.mail;

import java.util.HashMap;
import java.util.Map;

import com.exedosoft.plat.DOSendEMail;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

public class DOSendEMailAction extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6705145558166117161L;

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		if (this.actionForm == null) {
			return NO_FORWARD;
		}

		BOInstance user = DOGlobals.getInstance().getSessoinContext().getUser();

		if (user == null) {
			this.setEchoValue("当前Session出错！");
			return NO_FORWARD;
		}

		String emailConfigUid = user.getValue("emailconfig_uid");

		if (emailConfigUid == null) {
			this.setEchoValue("当前用户体系不支持邮件配置!");
			return NO_FORWARD;
		}

		DOService findMailConfig = DOService
				.getService("do_email_config_browse");

		if (findMailConfig == null) {
			this.setEchoValue("没有相关邮件配置!");
			return NO_FORWARD;
		}

		BOInstance<DOMailConfig> biConfig = findMailConfig
				.getInstance(emailConfigUid);

		if (biConfig == null) {
			this.setEchoValue("没有相关邮件配置!");
			return NO_FORWARD;
		}

		DOMailConfig mailConifg = biConfig.toObject(DOMailConfig.class);

		DOSendEMail se = new DOSendEMail();

		String email_to = this.actionForm.getValue("email_to");
		String email_title = this.actionForm.getValue("email_title");
		String email_content = this.actionForm.getValue("email_content");

		try {
			se.sendEmail(email_to, email_title, email_content, mailConifg);

			DOService ci = DOService.getService("do_email_content_insert");
			Map paras = new HashMap();
			paras.put("email_title", email_title);
			paras.put("email_date",
					new java.sql.Date(System.currentTimeMillis())
							.toLocaleString());
			paras.put("email_name", mailConifg.getEmailSmtpUser());
			paras.put("email_from", mailConifg.getEmailSmtpUser());
			paras.put("email_to", email_to);

			paras.put("email_content", email_content);

			paras.put("box_type", "2");

			paras.put("useruid", user.getUid());

			try {
				ci.invokeUpdate(paras);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
		}
		return DEFAULT_FORWARD;
	}

}
