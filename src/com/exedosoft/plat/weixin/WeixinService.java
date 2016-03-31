package com.exedosoft.plat.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.weixin.passivity.DefaultSession;
import com.exedosoft.plat.weixin.passivity.HandleMessageAdapter;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Event;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Location;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Text;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Voice;
import com.exedosoft.plat.weixin.redpacket.RedPacketUtil;

public class WeixinService {

	public String connect(HttpServletRequest request, String token) {

		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		System.out.println("收到验证请求：");
		System.out.println("　　signature=" + signature);
		System.out.println("　　timestamp=" + timestamp);
		System.out.println("　　nonce=" + nonce);
		System.out.println("　　echostr=" + echostr);
		if (signature == null || timestamp == null || nonce == null
				|| echostr == null) {
			return "parameter is null!";
		} else {
			// 重写totring方法，得到三个参数的拼接字符串
			List<String> list = new ArrayList<String>(3) {
				private static final long serialVersionUID = 2621444383666420433L;

				public String toString() {
					return this.get(0) + this.get(1) + this.get(2);
				}
			};
			list.add(token);
			list.add(timestamp);
			list.add(nonce);
			Collections.sort(list);// 排序
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] digest = md.digest(list.toString().getBytes());

			String tmpStr = bytes2Hex(digest);

			System.out.println("　　tmpStr=" + tmpStr);

			if (signature.equals(tmpStr)) {
				return echostr;
			} else {
				return "check error！";
			}
		}

	}

	public String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
	 * 
	 * 
	 */
	public void postMessage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		System.out.println("getParameterMap:::" + request.getParameterMap());

		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();

		final DefaultSession session = DefaultSession.newInstance();

		session.addOnHandleMessageListener(new HandleMessageAdapter() {

			@Override
			public void onTextMsg(Msg4Text msg) {

				System.out.println("Message Content::" + msg.getContent());

				if ("lhb".equals(msg.getContent())) {// 菜单选项1

					String c = "";
					DOService findAccount = DOService
							.getService("llp_account_findbymmopenid");
					BOInstance accountBI = findAccount.getInstance(msg
							.getFromUserName());
					if (accountBI == null) {
						c = "你还没绑定帐号，请先进行绑定。";
					} else if ("1".equals(accountBI
							.getValue("is_send_redpacket"))) {
						c = "已经参加过抽奖，感谢你的参与！";
					} else {

						DOService wzdService = DOService
								.getService("p_integrity_get");
						BOInstance wzdBi = wzdService.getInstance(accountBI
								.getValue("linkuid"));
						if (wzdBi != null) {
							String wzd = wzdBi.getValue("wzd");
							if (Double.parseDouble(wzd) > 80.0) {

								int red = getRandodRedPacket();
								if (red == 0) {
									c = "很遗憾，你没有中奖。";
								} else {
									RedPacketUtil rpu = new RedPacketUtil();
									try {
										rpu.sendRedPack(msg.getFromUserName(),
												"" + red, "连连聘祝您工作顺利，升职加薪！");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								// 更新状态，已领红包
								DOService llp_account_update_redpacket = DOService
										.getService("llp_account_update_helper_is_send_redpacket");
								try {
									llp_account_update_redpacket
											.invokeUpdate(accountBI.getUid());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								c = "你的简历完整度小于80%，请继续完善简历！";
							}
						} else {
							c = "帐号绑定错误，请与连连聘客服联系！";
						}
					}

					if (!"".equals(c)) {
						Msg4Text reMsg = new Msg4Text();
						reMsg.setFromUserName(msg.getToUserName());
						reMsg.setToUserName(msg.getFromUserName());
						reMsg.setCreateTime(msg.getCreateTime());
						reMsg.setContent(c);
						session.callback(reMsg);// 回传消息
					}
				}

				//
				// if("1".equals(msg.getContent())){// 菜单选项1
				// Msg4Text reMsg = new Msg4Text();
				// reMsg.setFromUserName(msg.getToUserName());
				// reMsg.setToUserName(msg.getFromUserName());
				// reMsg.setCreateTime(msg.getCreateTime());
				// reMsg.setContent("【菜单】\n" +
				// "1. 功能菜单\n" +
				// "2. 图文消息测试\n" +
				// "3. 图片消息测试\n");
				// session.callback(reMsg);//回传消息
				// }else if("2".equals(msg.getContent())){
				// //回复一条消息
				// Data4Item d1 = new Data4Item("蘑菇建站系统", "CMS不解释",
				// "http://cms.yl-blog.com/themes/blue/images/logo.png",
				// "cms.yl-blog.com");
				// Data4Item d2 = new Data4Item("雨林博客", "发布各种技术文章",
				// "http://www.yl-blog.com/template/ylblog/images/logo.png",
				// "www.yl-blog.com");
				//
				// Msg4ImageText mit = new Msg4ImageText();
				// mit.setFromUserName(msg.getToUserName());
				// mit.setToUserName(msg.getFromUserName());
				// mit.setCreateTime(msg.getCreateTime());
				// mit.addItem(d1);
				// mit.addItem(d2);
				// session.callback(mit);
				// }else if("3".equals(msg.getContent())){
				// Msg4Image rmsg = new Msg4Image();
				// rmsg.setFromUserName(msg.getToUserName());
				// rmsg.setToUserName(msg.getFromUserName());
				// rmsg.setPicUrl("http://www.yl-blog.com/template/ylblog/images/logo.png");
				// session.callback(rmsg);
				// }else {
				// Msg4Text reMsg = new Msg4Text();
				// reMsg.setFromUserName(msg.getToUserName());
				// reMsg.setToUserName(msg.getFromUserName());
				// reMsg.setCreateTime(msg.getCreateTime());
				//
				// reMsg.setContent("消息命令错误，谢谢您的支持！@wuweiit");
				//
				//
				// session.callback(reMsg);//回传消息
				// }
			}
		});

		// 语音识别消息
		session.addOnHandleMessageListener(new HandleMessageAdapter() {

			public void onVoiceMsg(Msg4Voice msg) {
				Msg4Text reMsg = new Msg4Text();
				reMsg.setFromUserName(msg.getToUserName());
				reMsg.setToUserName(msg.getFromUserName());
				reMsg.setCreateTime(msg.getCreateTime());
				reMsg.setContent("识别结果: " + msg.getRecognition());
				session.callback(reMsg);// 回传消息
			}
		});
		
		// 处理事件
		session.addOnHandleMessageListener(new HandleMessageAdapter() {
			public void onEventMsg(Msg4Event msg) {
				String eventType = msg.getEvent();
				String eventKey = msg.getEventKey();

				if (Msg4Event.SUBSCRIBE.equalsIgnoreCase(eventType)) {// 订阅
					System.out.println("UserName：" + msg.getFromUserName());
					System.out.println("EventKey：" + msg.getEventKey());

					Msg4Text reMsg = new Msg4Text();
					reMsg.setFromUserName(msg.getToUserName());
					reMsg.setToUserName(msg.getFromUserName());
					reMsg.setCreateTime(msg.getCreateTime());

					// reMsg.setContent("【菜单】\n" +
					// "1. 功能菜单\n" +
					// "2. 图文消息测试\n" +
					// "3. 图片消息测试\n");
					
					String c = DOGlobals.getValue("weixin.subscribe.welcome") ;
					//String c = "欢迎关注" ;
					System.out.println("====================" + c) ;
					// qrscene_867176152
					if (eventKey != null && eventKey.startsWith("qrscene_")
							&& !eventKey.startsWith("qrscene_A_")) {
						c = c + "绑定帐号成功！";

						WUser u = WUser.getWUser(msg.getFromUserName());
						WeixinService.linkWeixinID(msg.getFromUserName(),
								u.getNickname(), msg.getEventKey().substring(8));
					}
					reMsg.setContent(c);

					session.callback(reMsg);// 回传消息
				} else if (Msg4Event.SCAN.equalsIgnoreCase(eventType)) {

					System.out.println("UserName：" + msg.getFromUserName());

					System.out.println("EventKey：" + msg.getEventKey());
					if (eventKey != null && eventKey.startsWith("A_")) {

						DOService findAccount = DOService
								.getService("llp_account_findbymmopenid");
						BOInstance accountBI = findAccount.getInstance(msg
								.getFromUserName());

						System.out.println("AccountBI::" + accountBI);

						DOService findInterview = DOService
								.getService("weixin_hr_interview_hb_helper_select");
						// persinuid , eventkey
						List<BOInstance> interviewBIs = findInterview.select(
								accountBI.getValue("linkuid"),
								msg.getEventKey());

						System.out.println("interviewBIs::" + interviewBIs);

						DOService findPosition = DOService
								.getService("llp_company_position_browse");

						DOService storeHB = DOService
								.getOldService("llp_red_record_insert");

						DOService findRed = DOService
								.getService("llp_red_record_findbylinkuid");

						DOService interview_to_yms = DOService
								.getService("llp_interview_update_to_yms_byobjuid");

						boolean isInterview = false;

						for (BOInstance inteviewBI : interviewBIs) {

							if (inteviewBI.getValue("interviewstate").equals(
									"0")) {// 待面试
								isInterview = true;
							} else {
								continue;
							}

							BOInstance bi = findRed.getInstance(inteviewBI
									.getUid());

							if (bi != null) {
								System.out.println("BI:::::" + bi);
								continue;
							}
							// //没有发过红包才可以

							Map paras = new HashMap();
							paras.put("itemtype", "1"); // 面试红包
							BOInstance biPosition = findPosition
									.getInstance(inteviewBI
											.getValue("positionuid"));

							int interviewBonus = 0;
							try {
								interviewBonus = Integer.parseInt(biPosition
										.getValue("interviewbonus"));
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							paras.put("money", interviewBonus); // 面试红包
							paras.put("hruid", inteviewBI.getValue("hruid")); // hruid
							paras.put("personuid",
									inteviewBI.getValue("personuid")); // personuid

							paras.put("positionuid",
									inteviewBI.getValue("positionuid")); // positionuid
							paras.put("linkuid", inteviewBI.getValue("objuid")); // positionuid
							paras.put("companyuid",
									biPosition.getValue("companyuid")); // positionuid
							try {
								if(interviewBonus!=0){
									storeHB.store(paras);
								}
								interview_to_yms.invokeUpdate(inteviewBI
										.getUid());
							} catch (ExedoException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// //面试表状态改为已面试 

						}

						Msg4Text reMsg = new Msg4Text();
						reMsg.setFromUserName(msg.getToUserName());
						reMsg.setToUserName(msg.getFromUserName());
						reMsg.setCreateTime(msg.getCreateTime());

						if (isInterview) {
							reMsg.setContent("签到成功，面试红包会在72小时内发放！");
						} else {
							reMsg.setContent(DOGlobals
									.getValue("inteview.norecord"));
						}
						session.callback(reMsg);// 回传消息

					} else if (eventKey != null) {
						WUser u = WUser.getWUser(msg.getFromUserName());

						WeixinService.linkWeixinID(msg.getFromUserName(),
								u.getNickname(), msg.getEventKey());

						Msg4Text reMsg = new Msg4Text();
						reMsg.setFromUserName(msg.getToUserName());
						reMsg.setToUserName(msg.getFromUserName());
						reMsg.setCreateTime(msg.getCreateTime());

						reMsg.setContent("绑定帐号成功！");
						session.callback(reMsg);// 回传消息
					}

				} else if (Msg4Event.UNSUBSCRIBE.equalsIgnoreCase(eventType)) {// 取消订阅
					System.out.println("取消关注：" + msg.getFromUserName());

				} else if (Msg4Event.CLICK.equalsIgnoreCase(eventType)) {// 点击事件
					System.out.println("用户：" + msg.getFromUserName());
					System.out.println("点击Key：" + msg.getEventKey());
				}
			}
		});

		// 处理地理位置
		session.addOnHandleMessageListener(new HandleMessageAdapter() {
			public void onLocationMsg(Msg4Location msg) {
				System.out.println("收到地理位置消息：");
				System.out.println("X:" + msg.getLocation_X());
				System.out.println("Y:" + msg.getLocation_Y());
				System.out.println("Scale:" + msg.getScale());
			}

		});

		session.process(is, os);// 处理微信消息
		session.close();// 关闭Session

	}

	public static void linkWeixinID(String wxId, String wx_nickname,
			String sceneId) {

		DOService linkService = DOService.getService("p_openid_mbind_put");
		try {
			linkService.invokeUpdate(wxId, wx_nickname, sceneId);
		} catch (ExedoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {

			System.out.println("==================::" + getRandodRedPacket());

			getRandodRedPacket();

		}

		if (Double.parseDouble("82.5") > 80.0) {
			System.out.println("82.5");

		}
	}

	private static int getRandodRedPacket() {

		Random r = new Random();

		int r1 = r.nextInt(2);
		if (r1 == 0) {
			return 0;
		} else {
			return (r.nextInt(3) + 1);
		}
	}

}
