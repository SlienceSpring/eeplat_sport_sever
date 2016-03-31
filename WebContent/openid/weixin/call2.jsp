<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
   String url = java.net.URLEncoder.encode("weixin.lianlianpin.com","utf-8");
%>

<html>
<head>
<title>Wexin Login</title>
<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
</head>
<body>
<div id="login_container"></div>
<script>
var obj = new WxLogin({
    id:"login_container", 
    appid: "wx2916386e58558d06", 
    scope: "snsapi_login", 
    redirect_uri: "<%=url%>",
    state: "myState1"
  });
</script>
</body>
</html>

