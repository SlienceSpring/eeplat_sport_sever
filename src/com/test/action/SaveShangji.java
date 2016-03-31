package com.test.action;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.exedosoft.plat.DOSendEMail;
import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOService;

public class SaveShangji extends DOAbstractAction {

	public SaveShangji() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String excute() throws ExedoException {
		
		DOService insertSJ = DOService.getService("mycrm_potential_insert");
		this.actionForm.putValue("description", "自定义保存");
		insertSJ.store(this.actionForm);
		
		try {
			DOSendEMail.sendEmail("103315216@qq.com", "招聘", "招聘的就是你");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
