<%@ page pageEncoding="UTF-8"%><%@ page import="com.exedosoft.plat.ui.DODownLoadFile,com.exedosoft.plat.SessionContext"%><%response.setContentType("application/x-download; charset=UTF-8");  
SessionContext context = (SessionContext) session
.getAttribute("userInfo");
if (null == session.getAttribute("userInfo")
	|| context.getUser() == null) {
	response.sendRedirect(request.getContextPath()
		+ "/web/default/logoff.jsp");
}
String tableName =  request.getParameter("tableName");  response.addHeader("Content-Disposition", "attachment;filename=export.csv");    out.println(DODownLoadFile.outDataTemplate(tableName));%>
