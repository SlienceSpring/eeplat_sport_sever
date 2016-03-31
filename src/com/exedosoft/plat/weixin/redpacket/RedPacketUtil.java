package com.exedosoft.plat.weixin.redpacket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;
import com.exedosoft.plat.util.id.UUIDHex;
import com.exedosoft.plat.weixin.Config;

@SuppressWarnings("deprecation")
public class RedPacketUtil {

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String input_charset) {
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String input_charset) {
		String mysign = DigestUtils
				.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}

	/**
	 * 生成6位或10位随机数 param codeLength(多少位)
	 * 
	 * @return
	 */
	private String createCode(int codeLength) {
		String code = "";
		for (int i = 0; i < codeLength; i++) {
			code += (int) (Math.random() * 9);
		}
		return code;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 发送现金红包
	 * 
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws DocumentException
	 */
	public void sendRedPack(String openid, String totalAmount, String wishing)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, KeyManagementException,
			UnrecoverableKeyException, DocumentException {
		// 获取uuid作为随机字符串
		// 分改为元
		totalAmount = totalAmount + "00";

		String nonceStr = UUIDHex.getInstance().generate();
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String code = createCode(10);
		String mch_id = Config.MCH_ID;// 商户号
		String appid = Config.APPID;
		RedPacket sendRedPackPo = new RedPacket();

		sendRedPackPo.setNonce_str(nonceStr.toUpperCase());// nonceStr.toUpperCase()
		sendRedPackPo.setMch_billno(mch_id + today + code);//
		sendRedPackPo.setMch_id(mch_id);
		sendRedPackPo.setWxappid(appid);
		sendRedPackPo.setNick_name("连连聘");
		sendRedPackPo.setSend_name("连连聘");
		sendRedPackPo.setRe_openid(openid);
		sendRedPackPo.setTotal_amount(totalAmount);
		sendRedPackPo.setMin_value(totalAmount);
		sendRedPackPo.setMax_value(totalAmount);
		sendRedPackPo.setTotal_num("1");
		sendRedPackPo.setWishing(wishing);
		sendRedPackPo.setClient_ip("127.0.0.1"); // IP
		sendRedPackPo.setAct_name("LLP");
		sendRedPackPo.setAct_id("LLP");
		sendRedPackPo.setRemark("连连聘");

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("nonce_str", sendRedPackPo.getNonce_str());
		sParaTemp.put("mch_billno", sendRedPackPo.getMch_billno());
		sParaTemp.put("mch_id", sendRedPackPo.getMch_id());
		sParaTemp.put("wxappid", sendRedPackPo.getWxappid());
		sParaTemp.put("nick_name", sendRedPackPo.getNick_name());
		sParaTemp.put("send_name", sendRedPackPo.getSend_name());
		sParaTemp.put("re_openid", sendRedPackPo.getRe_openid());
		sParaTemp.put("total_amount", sendRedPackPo.getTotal_amount());
		sParaTemp.put("min_value", sendRedPackPo.getMin_value());
		sParaTemp.put("max_value", sendRedPackPo.getMax_value());
		sParaTemp.put("total_num", sendRedPackPo.getTotal_num());
		sParaTemp.put("wishing", sendRedPackPo.getWishing());
		sParaTemp.put("client_ip", sendRedPackPo.getClient_ip());
		sParaTemp.put("act_name", sendRedPackPo.getAct_name());
		sParaTemp.put("act_id", sendRedPackPo.getAct_id());
		sParaTemp.put("remark", sendRedPackPo.getRemark());

		// 除去数组中的空值和签名参数
		Map<String, String> sPara = paraFilter(sParaTemp);

		String prestr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

		String key = "&key=" + Config.PAY_SECRET; // 商户支付密钥

		String mysign = RedPacketUtil.sign(prestr + key, "utf-8").toUpperCase();

		sendRedPackPo.setSign(mysign);

		String respXml = sendRedPackPo.toXml();

		KeyStore keyStore = KeyStore.getInstance("PKCS12");

		File certFile = new File(DOGlobals.getValue("weixin.apiclient.cert")
				+ File.separator + "apiclient_cert.p12");
		InputStream instream = new FileInputStream(certFile);
		try {
			keyStore.load(instream, mch_id.toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();

		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		try {

			HttpPost httpPost = new HttpPost(
					"https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");

			StringEntity reqEntity = new StringEntity(respXml, "utf-8");

			// 设置类型
			// reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println(response.getStatusLine());
				if (entity != null) {

					// 从request中取得输入流
					InputStream inputStream = entity.getContent();
					// 读取输入流
					SAXReader reader = new SAXReader();
					Document document = reader.read(inputStream);
					// 得到xml根元素
					Element root = document.getRootElement();
					// 得到根元素的所有子节点
					List<Element> elementList = root.elements();

					// 遍历所有子节点
					for (Element e : elementList)
						map.put(e.getName(), e.getText());

					// 释放资源
					inputStream.close();
					inputStream = null;

				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}

		System.out.println(map);

	}

	public static void main(String[] args) throws KeyManagementException,
			UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			DocumentException {

		 RedPacketUtil rpu = new RedPacketUtil();
		 rpu.sendRedPack("oBy_Mvk29ISZFDV16Dn2sPBvddqI", "1", "祝您工作顺利，升职加薪！");

//		DOService red48 = DOService.getService("hr_interview_redpacket_48+");
//		List<BOInstance> listBIs = red48.invokeSelect();
//
//		DOService findAccountByPersonUid = DOService
//				.getService("llp_account_findbylinkuid");
//		
//		DOService updateInterviewStatus = DOService.getService("llp_interview_red_helper_set_interviewredstate_1");
//	
//		for (Iterator<BOInstance> it = listBIs.iterator(); it.hasNext();) {
//
//			BOInstance bi = it.next();
//			String money = bi.getValue("money");
//			String personuid = bi.getValue("personuid");
//			BOInstance account = findAccountByPersonUid.getInstance(personuid);
//
//			if (account.getValue("mmopenid") != null) {
//				RedPacketUtil rpu = new RedPacketUtil();
//				rpu.sendRedPack(account.getValue("mmopenid"), money,
//						"祝您工作顺利，升职加薪！");
//				try {
//					Map paras = new HashMap();
//					paras.put("linkuid", bi.getValue("linkuid"));
//					updateInterviewStatus.invokeUpdate(paras);
//				} catch (ExedoException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}

	}
}