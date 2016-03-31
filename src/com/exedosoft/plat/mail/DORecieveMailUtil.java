package com.exedosoft.plat.mail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;

public class DORecieveMailUtil {

	public DORecieveMailUtil() {
		// TODO Auto-generated constructor stub
	}

	private MimeMessage mimeMessage = null;
	private String saveAttachPath = ""; // 附件下载后的存放目录
	private StringBuffer bodytext = new StringBuffer();// 存放邮件内容
	private String dateformat = "yyyy-MM-dd HH:mm"; // 默认的日前显示格式

	public DORecieveMailUtil(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	/**
	 * 获得发件人的地址和姓名
	 */
	public String getFrom() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null)
			from = "";
		String personal = address[0].getPersonal();
		if (personal == null)
			personal = "";
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
	 */
	public String getMailAddress(String type) throws Exception {
		String mailaddr = "";
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		if (addtype.equals("TO") || addtype.equals("CC")
				|| addtype.equals("BCC")) {
			try {
				if (addtype.equals("TO")) {
					address = (InternetAddress[]) mimeMessage
							.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) mimeMessage
							.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) mimeMessage
							.getRecipients(Message.RecipientType.BCC);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String email = address[i].getAddress();
					if (email == null)
						email = "";
					else {
						email = MimeUtility.decodeText(email);
					}
					String personal = address[i].getPersonal();
					if (personal == null)
						personal = "";
					else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + email + ">";
					mailaddr += "," + compositeto;
				}
				if (mailaddr != null && mailaddr.length() > 1) {
					mailaddr = mailaddr.substring(1);
				}
			}
		} else {
			throw new Exception("Error emailaddr type!");
		}
		return mailaddr;
	}

	/**
	 * 获得邮件主题
	 */
	public String getSubject() throws MessagingException {
		String subject = "";
		try {
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null)
				subject = "";
		} catch (Exception exce) {
		}
		return subject;
	}

	/**
	 * 获得邮件发送日期
	 */
	public String getSentDate() throws Exception {
		Date sentdate = mimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(sentdate);

	}

	/**
	 * 获得邮件正文内容
	 */
	public String getBodyText() {
		return bodytext.toString();
	}

	/**
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
	 */
	public void getMailContent(Part part) throws Exception {
		this.getMailContent(part, false);
	}

	public void getMailContent(Part part, boolean isParent) throws Exception {
		String contenttype = part.getContentType();
		int nameindex = contenttype.indexOf("name");
		boolean conname = false;
		if (nameindex != -1)
			conname = true;
		System.out.println("CONTENTTYPE: " + contenttype);

		try {
			if (part.isMimeType("text/plain") && !conname) {
				if (!isParent) {
					bodytext.append((String) part.getContent());
				}
			} else if (part.isMimeType("text/html") && !conname) {
				bodytext.append((String) part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i), true);
				}
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent());
			} else {
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage
				.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * 获得此邮件的Message-ID
	 */
	public String getMessageId() throws MessagingException {
		return mimeMessage.getMessageID();
	}

	/**
	 * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		System.out.println("flags's length: " + flag.length);
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				System.out.println("seen Message.......");
				break;
			}
		}
		return isnew;
	}

	/**
	 * 判断此邮件是否包含附件
	 */
	public boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		String contentType = part.getContentType();
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
								.equals(Part.INLINE))))
					attachflag = true;
				else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * 【保存附件】
	 */
	public String saveAttachMent(Part part) throws Exception {

		StringBuffer fileNameBuffer = new StringBuffer("");
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = null;
				try {
					disposition = mpart.getDisposition();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
								.equals(Part.INLINE)))) {
					String fileName = mpart.getFileName();
					System.out.println("FileName::" + fileName);
					System.out.println("FileName::" + fileName);

					if (fileName != null) {
						// if (fileName.toLowerCase().indexOf("gb2312") != -1
						// || fileName.toLowerCase().indexOf("gbk") != -1
						// || fileName.toLowerCase().indexOf("utf-8") != -1) {
						fileName = MimeUtility.decodeText(fileName);
						// }
						saveFile(fileName, mpart.getInputStream());
						fileNameBuffer.append(fileName).append(";");
					}
				} else if (mpart.isMimeType("multipart/*")) {
					String theFiles = saveAttachMent(mpart);
					fileNameBuffer.append(theFiles);
				} else {
					String fileName = null;
					try {
						fileName = mpart.getFileName();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
					if (fileName != null) {
						// if (fileName.toLowerCase().indexOf("gb2312") != -1
						// || fileName.toLowerCase().indexOf("gbk") != -1
						// || fileName.toLowerCase().indexOf("utf-8") != -1) {
						fileName = MimeUtility.decodeText(fileName);
						// }
						saveFile(fileName, mpart.getInputStream());
						fileNameBuffer.append(fileName).append(";");
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			String theFiles = saveAttachMent((Part) part.getContent());
			fileNameBuffer.append(theFiles);
		}
		return fileNameBuffer.toString();
	}

	/**
	 * 【设置附件存放路径】
	 */

	public void setAttachPath(String attachpath) {
		this.saveAttachPath = attachpath;
	}

	/**
	 * 【设置日期显示格式】
	 */
	public void setDateFormat(String format) throws Exception {
		this.dateformat = format;
	}

	/**
	 * 【获得附件存放路径】
	 */
	public String getAttachPath() {
		return saveAttachPath;
	}

	/**
	 * 【真正的保存附件到指定目录里】
	 */
	private void saveFile(String fileName, InputStream in) throws Exception {
		String osName = System.getProperty("os.name");
		String storedir = getAttachPath();
		String separator = "";
		if (osName == null)
			osName = "";
		if (osName.toLowerCase().indexOf("win") != -1) {
			separator = "\\";
			if (storedir == null || storedir.equals(""))
				storedir = DOGlobals.UPLOAD_TEMP;
		} else {
			separator = "/";
			storedir = "/tmp";
		}
		File storefile = new File(storedir + separator + fileName);

		if (storefile.exists()) {
			return;
		}

		try {
			storefile.createNewFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!storefile.exists()) {
			return;
		}
		System.out.println("storefile's path: " + storefile.toString());
		// for(int i=0;storefile.exists();i++){
		// storefile = new File(storedir+separator+fileName+i);
		// }
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("文件保存失败!");
		} finally {
			bos.close();
			bis.close();
		}
	}

	/**
	 * PraseMimeMessage类测试
	 */
	public int recieveMails(DOMailConfig config, String useruid)
			throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", config.getEmailSmtpServer());
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urln = new URLName("pop3", config.getEmailPop3Server(), 110,
				null, config.getEmailSmtpUser(), config.getEmailSmtpPassword());
		Store store = session.getStore(urln);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		DOService findMailSizes = DOService
				.getService("do_email_content_findsize_by_email_name");
		int resultSize = Integer.parseInt(findMailSizes.getAValue(config
				.getEmailSmtpUser()));
		int msgCount = folder.getMessageCount();

		if (resultSize == 0) {
			resultSize = 1;
		} else if (resultSize >= msgCount) {
			return 0;
		}

		Message message[] = folder.getMessages(resultSize, msgCount);
		DORecieveMailUtil pmm = null;
		for (int i = 0; i < message.length; i++) {

			pmm = new DORecieveMailUtil((MimeMessage) message[i]);

			pmm.getMailContent((Part) message[i]);
			String fileNames = pmm.saveAttachMent((Part) message[i]);

			DOService ci = DOService.getService("do_email_content_insert");
			Map paras = new HashMap();
			paras.put("email_title", pmm.getSubject());
			paras.put("email_date", pmm.getSentDate());
			paras.put("email_name", config.getEmailSmtpUser());
			paras.put("email_from", pmm.getFrom());
			paras.put("email_to", pmm.getMailAddress("to"));
			paras.put("email_cc", pmm.getMailAddress("cc"));
			paras.put("email_bcc", pmm.getMailAddress("bcc"));
			paras.put("email_msg_id", pmm.getMessageId());
			paras.put("email_content", pmm.getBodyText());
			paras.put("email_attachment", fileNames);
			if (pmm.getReplySign()) {
				paras.put("is_reply", "1");
			} else {
				paras.put("is_reply", "0");
			}
			if (pmm.isNew()) {
				paras.put("is_new", "1");
			} else {
				paras.put("is_new", "0");
			}

			int size = (int) message[i].getSize() / 1024;
			if (size == 0) {
				size = 1;
			}
			paras.put("email_size", size + "K");
			paras.put("useruid", useruid);

			try {
				ci.invokeUpdate(paras);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		folder.close(false);
		return msgCount - resultSize;
	}

	/**
	 * PraseMimeMessage类测试
	 */
	public static void main(String args[]) throws Exception {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.ym.163.com");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urln = new URLName("pop3", "pop3.ym.163.com", 110, null,
				"support@eeplat.com", "qweasd90");
		Store store = session.getStore(urln);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		System.out.println("getDeletedMessageCount:::"
				+ folder.getDeletedMessageCount());

		DORecieveMailUtil pmm = null;

		int[] mesids = {};// 827,828,829,830,831
		Message message[] = folder.getMessages(mesids);
		for (int i = 0; i < message.length; i++) {
			System.out.println("======================");

			pmm = new DORecieveMailUtil((MimeMessage) message[i]);
			pmm.getMailContent((Part) message[i]);
			String fileNames = pmm.saveAttachMent((Part) message[i]);

			DOService ci = DOService.getService("do_email_content_insert");
			Map paras = new HashMap();
			paras.put("email_title", pmm.getSubject());
			paras.put("email_date", pmm.getSentDate());
			paras.put("email_from", pmm.getFrom());
			paras.put("email_to", pmm.getMailAddress("to"));
			paras.put("email_cc", pmm.getMailAddress("cc"));
			paras.put("email_bcc", pmm.getMailAddress("bcc"));
			paras.put("email_msg_id", pmm.getMessageId());
			paras.put("email_content", pmm.getBodyText());
			paras.put("email_attachment", fileNames);
			if (pmm.getReplySign()) {
				paras.put("is_reply", "1");
			} else {
				paras.put("is_reply", "0");
			}
			if (pmm.isNew()) {
				paras.put("is_new", "1");
			} else {
				paras.put("is_new", "0");
			}

			int size = (int) message[i].getSize() / 1024;
			if (size == 0) {
				size = 1;
			}
			paras.put("email_size", size + "K");

			try {
				ci.invokeUpdate(paras);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// folder.close(false);
	}

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// }

}
