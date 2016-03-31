<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title>Fonts</title>
<style>
body {font-size:8pt;}
ol {line-height:18px;}
</style>
</head>
<body>
<strong>java.home:</strong>
<ul>
<li><%=System.getProperty("java.home")%></li>
</ul>
<br/>
<strong>Memory:</strong>
<ol>
<li>freeMemory=<%=Runtime.getRuntime().freeMemory()/(1024*1024)%>M</li>
    <li>totalMemory=<%=Runtime.getRuntime().totalMemory()/(1024*1024)%>M</li>
    <li>maxMemory=<%=Runtime.getRuntime().maxMemory()/(1024*1024)%>M</li>
</ol>
<br/>
<strong>Fonts:</strong>
<ol>
<%for(String s : getFonts()){%>
<li><%=s%></li>
<%}%>
</ol>
<%!
public static String[]  getFonts(){
	java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
			String[] fontFamilies = ge.getAvailableFontFamilyNames();
			return fontFamilies;
		}

%>
</body>
</html>