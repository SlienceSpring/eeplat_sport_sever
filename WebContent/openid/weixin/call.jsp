<%

   String base = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
   String url = java.net.URLEncoder.encode("weixin.lianlianpin.com","utf-8");
   base =	base.replaceAll("REDIRECT_URI",url);
   base =	base.replaceAll("APPID","wx2916386e58558d06");
   base =	base.replaceAll("SCOPE","snsapi_login");
   base =	base.replaceAll("STATE","myState1");
   System.out.println("Url:::" + base);
   response.sendRedirect(base);
%>
