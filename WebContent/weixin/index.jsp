<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.weixin.WeixinService"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<%  
	System.out.println("================================================");
	Map formValues = request.getParameterMap();
	for (Iterator it = formValues.keySet().iterator(); it.hasNext();) {
		String key = (String) it.next();
		String[] values = (String[]) formValues.get(key);
		if (values == null) {
			continue;
		}
		System.out.println(key  + ":" + values[0] );
	}
	

	WeixinService dw = new WeixinService();
	if(request.getMethod().equals("GET")){
		String echostr = dw.connect(request, "eeplat");
		System.out.println("In jsp outer:" + echostr);
		response.getWriter().print(echostr);
	}else{
		dw.postMessage(request, response);	
	}
%>