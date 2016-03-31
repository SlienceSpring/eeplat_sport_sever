package com.exedosoft.plat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.DOServiceRedirect;
import com.exedosoft.plat.mail.DOMailConfig;
import com.exedosoft.plat.util.DOGlobals;

public class DOSendEMail extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4182943988305938731L;

	public static final String EMAIL_SENDER_NAME = "email.sender.name";
	public static final String EMAIL_SENDER_ADDRESS = "email.sender.address";
	public static final String EMAIL_STMP_SERVER = "email.smtp.server";
	public static final String EMAIL_STMP_SERVER_PORT = "email.smtp.server.port";
	public static final String EMAIL_STMP_USER = "email.smtp.user";
	public static final String EMAIL_STMP_PASSWORD = "email.smtp.password";
	public static final String EMAIL_STMP_AUTH = "email.smtp.auth";
	public static final String EMAIL_STMP_SSL = "email.smtp.ssl";
	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@Override
	public String excute() throws ExedoException {
		// TODO Auto-generated method stub

		if (this.service == null) {
			return NO_FORWARD;
		}

		if (this.actionForm == null) {
			return NO_FORWARD;
		}

		String emailTo = this.getEMailTo();
		
		if(emailTo==null || "".equals(emailTo.trim())){
			return NO_FORWARD;
		}

		List<DOServiceRedirect> list = this.service.retrieveServiceRedirect();
		if (list != null && list.size() > 0) {

			for (DOServiceRedirect dsr : list) {
				if (dsr.getPaneModel() != null) {

					String title = dsr.getPaneModel().getTitle();
					String context = dsr.getPaneModel().getHtmlCode();

					String email_to = SessionContext.getInstance()
							.getThreadContext().getInstance()
							.getValue("email_to");
					if (email_to != null) {
						emailTo = email_to;
					}

					if (emailTo == null) {
						return NO_FORWARD;
					}

					String email_title = SessionContext.getInstance()
							.getThreadContext().getInstance()
							.getValue("email_title");
					if (email_title != null) {
						title = email_title;
					}
					try {
						sendEmail(emailTo, title, context);
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

				}

			}

		}

		return DEFAULT_FORWARD;
	}

	private String getEMailTo() {

		if (this.actionForm.getValue("email") != null) {
			return this.actionForm.getValue("email");
		}
		if (this.actionForm.getValue("user_email") != null) {
			return this.actionForm.getValue("user_email");
		}

		if (this.actionForm.getValue("useremail") != null) {
			return this.actionForm.getValue("useremail");
		}

		if (this.actionForm.getValue("emaildress") != null) {
			return this.actionForm.getValue("emaildress");
		}
		return null;
	}

	// //////////////////////////////////////////////////////////////
	// 发送邮件
	public static void sendEmail(String smtpServer, String senderAdress,
			String senderName, String user, String password, String to,
			String title, String text) throws AddressException,
			MessagingException, IOException {

		final Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");

		if (DOGlobals.getValue(EMAIL_STMP_AUTH) != null) {
			props.put("mail.smtp.auth", DOGlobals.getValue(EMAIL_STMP_AUTH));
		} else {
			props.put("mail.smtp.auth", "true");
		}
		if (DOGlobals.getValue(EMAIL_STMP_SERVER_PORT) != null) {
			props.put("mail.smtp.port",
					DOGlobals.getValue(EMAIL_STMP_SERVER_PORT));
		}

		Session myMailSession = Session.getInstance(props);

		myMailSession.setDebug(true); // 打开DEBUG模式
		Message msg = new MimeMessage(myMailSession);
		msg.setFrom(new InternetAddress(senderAdress, senderName));

		List<InternetAddress> listIA = new ArrayList<InternetAddress>();
		String[] tos = to.split(";");
		for (int i = 0; i < tos.length; i++) {
			String oneTo = tos[i];
			if (oneTo != null && !oneTo.trim().equals("")) {
				listIA.add(new InternetAddress(oneTo));
			}
		}

		msg.setRecipients(Message.RecipientType.TO,
				listIA.toArray(new InternetAddress[listIA.size()]));

		msg.setContent("I have a email!", "text/html");
		msg.setSentDate(new java.util.Date());
		msg.setSubject(title);
		// msg.setText(text);

		msg.setDataHandler(new DataHandler(new ByteArrayDataSource(text,
				"text/html ")));
		System.out.println("1.Please wait for sending to...");

		// 发送邮件
		Transport myTransport = myMailSession.getTransport("smtp");
		myTransport.connect(smtpServer, user, password);
		myTransport.sendMessage(msg,
				msg.getRecipients(Message.RecipientType.TO));
		myTransport.close();
		System.out.println("2.Your message had send!");
	}

	// 发送邮件
	public static void sendEmailSSL(String smtpServer, String senderAdress,
			String senderName, String user, String password, String to,
			String title, String text) throws AddressException,
			MessagingException, IOException {

		System.out.println("use ssl===================");

		final Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.starttls.enable", "true");

		if (DOGlobals.getValue(EMAIL_STMP_SERVER_PORT) != null) {
			props.put("mail.smtp.port",
					DOGlobals.getValue(EMAIL_STMP_SERVER_PORT));
			props.put("mail.smtp.socketFactory.port",
					DOGlobals.getValue(EMAIL_STMP_SERVER_PORT));
		}

		DOSendEMail dm = new DOSendEMail();
		Authenticator auth = dm.getMailAuthenticator(user, password);

		Session myMailSession = Session.getInstance(props, auth);

		myMailSession.setDebug(true); // 打开DEBUG模式
		Message msg = new MimeMessage(myMailSession);
		msg.setFrom(new InternetAddress(senderAdress, senderName));

		List<InternetAddress> listIA = new ArrayList<InternetAddress>();
		String[] tos = to.split(";");
		for (int i = 0; i < tos.length; i++) {
			String oneTo = tos[i];
			if (oneTo != null && !oneTo.trim().equals("")) {
				listIA.add(new InternetAddress(oneTo));
			}
		}

		msg.setRecipients(Message.RecipientType.TO,
				listIA.toArray(new InternetAddress[listIA.size()]));

		// InternetAddress[] tos = InternetAddress.parse(toAddr);
		// msg.setRecipients(Message.RecipientType.TO, tos);
		// if(ccAddr != null){
		// InternetAddress[] cc = InternetAddress.parse(ccAddr);
		// msg.setRecipients(Message.RecipientType.CC, cc);
		// }

		msg.setContent("I have a email!", "text/html");
		msg.setSentDate(new java.util.Date());
		msg.setSubject(title);
		// msg.setText(text);

		msg.setDataHandler(new DataHandler(new ByteArrayDataSource(text,
				"text/html ")));
		System.out.println("1.Please wait for sending to...");
		Transport.send(msg);

		System.out.println("2.Your message had send!");
	}

	// //////////////////////////////////////////////////////////////
	// 发送邮件
	public static void sendEmail(String to, String title, String text)
			throws AddressException, MessagingException, IOException {

		// **************************************************8
		// 测试用
		// to = "yaoyx@bst.org.cn";
		// *****************************************************8

		String smtpServer = DOGlobals.getValue(EMAIL_STMP_SERVER);
		String user = DOGlobals.getValue(EMAIL_STMP_USER);
		String password = DOGlobals.getValue(EMAIL_STMP_PASSWORD);

		if (smtpServer == null || user == null || password == null) {
			return;
		}

		String senderName = DOGlobals.getValue(EMAIL_SENDER_NAME);
		String senderAddress = DOGlobals.getValue(EMAIL_SENDER_ADDRESS);

		if ("true".equals(DOGlobals.getValue(EMAIL_STMP_SSL))) {
			sendEmailSSL(smtpServer, senderAddress, senderName, user, password,
					to, title, text);

		} else {
			sendEmail(smtpServer, senderAddress, senderName, user, password,
					to, title, text);
		}

	}

	// //////////////////////////////////////////////////////////////
	// 发送邮件
	public static void sendEmail(String to, String title, String text,
			DOMailConfig config) throws AddressException, MessagingException,
			IOException {

		// **************************************************8
		// 测试用
		// to = "yaoyx@bst.org.cn";
		// *****************************************************8

		String smtpServer = config.getEmailSmtpServer();
		String user = config.getEmailSmtpUser();
		String password = config.getEmailSmtpPassword();

		if (smtpServer == null || user == null || password == null) {
			return;
		}

		String senderName = config.getEmailSenderName();
		String senderAddress = config.getEmailSenderAddress();

		if ("true".equals(config.getEmailSmtpSsl())) {
			sendEmailSSL(smtpServer, senderAddress, senderName, user, password,
					to, title, text);

		} else {
			sendEmail(smtpServer, senderAddress, senderName, user, password,
					to, title, text);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			sendEmail("toweikexin@126.com", "招聘", "招聘的就是你");
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

	}

	private MailAuthenticator getMailAuthenticator(String userName,
			String password) {

		return new MailAuthenticator(userName, password);
	}

	class MailAuthenticator extends Authenticator {
		private String userName;
		private String password;

		public MailAuthenticator(String userName, String password) {
			super();
			this.userName = userName;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password);
		}

	}

}
