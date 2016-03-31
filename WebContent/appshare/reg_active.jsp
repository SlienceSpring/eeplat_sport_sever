<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.bo.DOService"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<% 

  String type = request.getParameter("type");
  if("reg".equals(type)){
	  String userId = request.getParameter("userId");
	  if(userId!=null){
		  DOService theService = DOService.getService("eeplat_user_updatesetvalid");
		  theService.invokeUpdate(userId);
	  }
	  out.println("账号被成功激活！<a href='hhttp://share.eeplat.com/eeplatshare'>前往EEPlat AppShare！</a>");
	  return;
  }
  if("reset".equals(type)){
	 String fromDate = request.getParameter("from");
	 
	 try{
		 if(fromDate!=null){
			 
			if( (System.currentTimeMillis() - Long.parseLong(fromDate.replace(",", "")))/1000/60/60 > 24){
				  out.println("您并没有在规定时间内激活！");
				  return;
			}
			String email = request.getParameter("u");
			if(email!=null){
				DOGlobals.getInstance().getSessoinContext().getGlobal().putValue("sendEMailDate", fromDate.replace(",", ""));
				DOGlobals.getInstance().getSessoinContext().getGlobal().putValue("user_email", email);
				response.sendRedirect(request.getContextPath() + "/appshare/forgetpwd_reset.pml?isApp=true");
			}
		 }
	 }catch(Exception e){
		 
	 }
	  
  }


%>