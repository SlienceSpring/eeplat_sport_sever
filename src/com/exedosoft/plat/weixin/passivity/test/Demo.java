package com.exedosoft.plat.weixin.passivity.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.exedosoft.plat.weixin.passivity.DefaultSession;
import com.exedosoft.plat.weixin.passivity.HandleMessageAdapter;
import com.exedosoft.plat.weixin.passivity.HandleMessageListener;
import com.exedosoft.plat.weixin.passivity.msg.Data4Item;
import com.exedosoft.plat.weixin.passivity.msg.Msg;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Event;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Image;
import com.exedosoft.plat.weixin.passivity.msg.Msg4ImageText;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Link;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Location;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Text;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Video;
import com.exedosoft.plat.weixin.passivity.msg.Msg4Voice;

/**
 * Demo
 * 这个是最新版的例子程序，需要结合旧版里的Servlet使用。
 * @author marker
 * */
public class Demo {

	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		while(true){
		//这里要说明下下：a.txt是微信的xml消息格式文件
		InputStream is  = new FileInputStream("c://a.txt");
		OutputStream os = new FileOutputStream("d://a.txt"); 
		
		//创建Session，默认没有任何监听器的。要使用功能必须添加监听器
		final DefaultSession session = DefaultSession.newInstance();
		 //1. 使用HandleMessageListener接口实现添加
        session.addOnHandleMessageListener(new HandleMessageListener() {

			public void onTextMsg(Msg4Text msg) {
				// TODO Auto-generated method stub
				
			}

			public void onImageMsg(Msg4Image msg) {
				// TODO Auto-generated method stub
				
			}

			public void onEventMsg(Msg4Event msg) {
				// TODO Auto-generated method stub
				
			}

			public void onLinkMsg(Msg4Link msg) {
				// TODO Auto-generated method stub
				
			}

			public void onLocationMsg(Msg4Location msg) {
				// TODO Auto-generated method stub
				
			}

			public void onErrorMsg(int errorCode) {
				// TODO Auto-generated method stub
				
			}

			public void onVoiceMsg(Msg4Voice msg) {
				// TODO Auto-generated method stub
				
			}

			public void onVideoMsg(Msg4Video msg) {
				// TODO Auto-generated method stub
				
			}});
	 

		
		//2. 使用HandleMessageAdapter适配器添加监听器（推荐使用此方案）
		session.addOnHandleMessageListener(new HandleMessageAdapter(){
			@Override
			public void onTextMsg(Msg4Text msg) {
				System.out.println("收到消息："+msg.getContent());
//				//回复一条消息
//				Msg4Text reMsg = new Msg4Text();
//				reMsg.setFromUserName(msg.getToUserName());
//				reMsg.setToUserName(msg.getFromUserName());
//				reMsg.setCreateTime(msg.getCreateTime());
//				reMsg.setMsgType(Msg.MSG_TYPE_TEXT);//设置消息类型
//				reMsg.setContent("呵呵,谢谢您给我发消息");
//				reMsg.setFuncFlag("0");
				
				Data4Item d1 = new Data4Item("测试标题", "测试描述", "http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg", " www.baidu.com"); 
				Data4Item d2 = new Data4Item("测试标题", "测试描述", "http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg", " www.baidu.com"); 
				      
				Msg4ImageText mit = new Msg4ImageText(); 
				mit.setFromUserName(msg.getToUserName()); 
				mit.setToUserName(msg.getFromUserName()); 
				mit.setCreateTime(msg.getCreateTime()); 
			 
				mit.addItem(d1); 
				mit.addItem(d2);  
				
				
				session.callback(mit);
			}
			
		});
		
		
		//处理消息（必须调用此方法才能处理微信消息）
		session.process(is, os);
		Thread.sleep(50);
		}
	}
}
