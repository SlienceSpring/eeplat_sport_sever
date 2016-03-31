<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.bo.DOService"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%
	String type = request.getParameter("type");
	if ("reg".equals(type)) {
		String userId = request.getParameter("userid");
		if (userId != null) {
			String setValidName = DOGlobals
					.getValue("account.setvalid.service.name");
			if (setValidName == null) {
				setValidName = "llp_account_setvalid";
			}
			DOService theService = DOService.getService(setValidName);
			theService.invokeUpdate(userId);
		}
		String setValidNote = DOGlobals
				.getValue("account.setvalid.note");
		if (setValidNote == null) {
			setValidNote = "账号被成功激活！<a href='http://www.llp.com'>前往连连聘官网！</a>";
		}
		out.println(setValidNote);
		return;
	}
	if ("reset".equals(type)) {
		try {

			String email = request.getParameter("u");
			String resetEMailCode = request.getParameter("from");
			String findByEMail = DOGlobals
					.getValue("account.findbyemail.service.name");
			if (findByEMail == null) {
				findByEMail = "llp_account_findbyemail";
			}
			DOService findAccountByEMail = DOService
					.getService(findByEMail);
			BOInstance acccount = findAccountByEMail.getInstance(email);
			if (acccount == null) {
				out.println("您并没有在规定时间内激活或邮箱错误！");
				return;
			}

			java.sql.Date fromDate = acccount
					.getDateValue("sendEMailDate");
			if (fromDate != null) {
				if ((System.currentTimeMillis() - fromDate.getTime()) / 1000 / 60 / 60 > 24) {
					out.println("您并没有在规定时间内激活！");
					return;
				}
			}

			if (email != null && resetEMailCode != null) {
				String resetPwdURL = DOGlobals
						.getValue("account.resetPwdURL");
				if (resetPwdURL == null) {
					resetPwdURL = request.getContextPath()
							+ "/projects/forgetpwd_reset.jsp";
				}
				response.sendRedirect(resetPwdURL + "?email=" + email
						+ "&resetEMailCode=" + resetEMailCode);
			}

		} catch (Exception e) {

		}

	}
%>