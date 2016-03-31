package com.exedosoft.plat.mail;

import com.exedosoft.plat.bo.BaseObject;

public class DOMailConfig extends BaseObject{
	

	private static final long serialVersionUID = -8181294798961651747L;
	
	public static final String EMAIL_SENDER_NAME = "email.sender.name";
	public static final String EMAIL_SENDER_ADDRESS = "email.sender.address";
	public static final String EMAIL_STMP_SERVER = "email.smtp.server";
	public static final String EMAIL_STMP_SERVER_PORT = "email.smtp.server.port";
	public static final String EMAIL_STMP_USER = "email.smtp.user";
	public static final String EMAIL_STMP_PASSWORD = "email.smtp.password";
	public static final String EMAIL_STMP_AUTH = "email.smtp.auth";
	public static final String EMAIL_STMP_SSL = "email.smtp.ssl";

	public static final String EMAIL_POP3_SERVER = "email.pop3.user";
	public static final String EMAIL_POP3_SERVER_PORT = "email.pop3.password";
	public static final String EMAIL_POP3_SSL = "email.pop3.ssl";



	public DOMailConfig() {
		// TODO Auto-generated constructor stub
	}

	private String emailSenderName;
	private String emailSenderAddress;
	private String emailSmtpServer;
	private String emailSmtpServerPort;
	private String emailSmtpUser;
	private String emailSmtpPassword;
	private String emailSmtpSsl;

	private String emailPop3Server;
	private String emailPop3ServerPort;
	private String emailPop3Ssl;

	public String getEmailSenderName() {
		return emailSenderName;
	}

	public void setEmailSenderName(String emailSenderName) {
		this.emailSenderName = emailSenderName;
	}

	public String getEmailSenderAddress() {
		return emailSenderAddress;
	}

	public void setEmailSenderAddress(String emailSenderAddress) {
		this.emailSenderAddress = emailSenderAddress;
	}

	public String getEmailSmtpServer() {
		return emailSmtpServer;
	}

	public void setEmailSmtpServer(String emailSmtpServer) {
		this.emailSmtpServer = emailSmtpServer;
	}

	public String getEmailSmtpServerPort() {
		return emailSmtpServerPort;
	}

	public void setEmailSmtpServerPort(String emailSmtpServerPort) {
		this.emailSmtpServerPort = emailSmtpServerPort;
	}

	public String getEmailSmtpUser() {
		return emailSmtpUser;
	}

	public void setEmailSmtpUser(String emailSmtpUser) {
		this.emailSmtpUser = emailSmtpUser;
	}

	public String getEmailSmtpPassword() {
		return emailSmtpPassword;
	}

	public void setEmailSmtpPassword(String emailSmtpPassword) {
		this.emailSmtpPassword = emailSmtpPassword;
	}

	public String getEmailSmtpSsl() {
		return emailSmtpSsl;
	}

	public void setEmailSmtpSsl(String emailSmtpSsl) {
		this.emailSmtpSsl = emailSmtpSsl;
	}

	public String getEmailPop3Server() {
		return emailPop3Server;
	}

	public void setEmailPop3Server(String emailPop3Server) {
		this.emailPop3Server = emailPop3Server;
	}

	public String getEmailPop3ServerPort() {
		return emailPop3ServerPort;
	}

	public void setEmailPop3ServerPort(String emailPop3ServerPort) {
		this.emailPop3ServerPort = emailPop3ServerPort;
	}

	public String getEmailPop3Ssl() {
		return emailPop3Ssl;
	}

	public void setEmailPop3Ssl(String emailPop3Ssl) {
		this.emailPop3Ssl = emailPop3Ssl;
	}
	
//	public static DOMailConfig fromMailConfig() {
//
//		DOMailConfig dmc = new DOMailConfig();
//
//		dmc.setEmailSmtpServer(DOGlobals.getValue(EMAIL_STMP_SERVER));
//		dmc.setEmailSmtpUser(DOGlobals.getValue(EMAIL_STMP_USER));
//		dmc.setEmailSmtpPassword(DOGlobals.getValue(EMAIL_STMP_PASSWORD));
//
//		dmc.setEmailSenderName(DOGlobals.getValue(EMAIL_SENDER_NAME));
//		dmc.setEmailSenderAddress(DOGlobals.getValue(EMAIL_SENDER_ADDRESS));
//
//		return dmc;
//
//	}


}
