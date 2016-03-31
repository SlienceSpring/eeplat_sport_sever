package com.exedosoft.plat.mail;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

public class DORecieveMailAction extends DOAbstractAction {

	private static final long serialVersionUID = 4532618875243524297L;

	public DORecieveMailAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String excute() throws ExedoException {

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

		DORecieveMailUtil r = new DORecieveMailUtil();
		try {
			int i = r.recieveMails(mailConifg, user.getUid());

			this.setEchoValue("收取完成！共收到" + i + "封邮件");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("mailConifg::" + mailConifg);

		return DEFAULT_FORWARD;
	}

}
