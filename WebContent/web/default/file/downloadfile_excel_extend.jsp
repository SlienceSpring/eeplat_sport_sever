<%@ page pageEncoding="UTF-8"%><%@ page import="com.exedosoft.plat.ui.DODownLoadFile,com.exedosoft.plat.SessionContext"%><%response.setContentType("application/vnd.ms-excel; charset=UTF-8");  
SessionContext context = (SessionContext) session
.getAttribute("userInfo");
if (null == session.getAttribute("userInfo")
	|| context.getUser() == null) {
	response.sendRedirect(request.getContextPath()
		+ "/web/default/logoff.jsp");
}
String pmlName =  request.getParameter("pmlName");  response.addHeader("Content-Disposition", "attachment;filename=export.xls");
String htmls = "<html> " 
 + "<head> "
 + "<meta charset='utf-8'>"
 + "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body>";

 htmls = htmls + DODownLoadFile.outHtmlCodeByName(pmlName); 
 if(htmls.indexOf("<!--End box body-->")!=-1){
	htmls =  htmls.substring(0,htmls.indexOf("<!--End box body-->"));
 }
htmls = htmls.replaceAll("Please select", "").replace("<th  style='display:none' ></th>", "");
htmls = htmls + "</body></html>";
System.out.println("htmls::" + htmls);
out.println(htmls);%>