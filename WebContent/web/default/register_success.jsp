<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap-responsive.min.css" type="text/css"/>
    
    <%
       String email = request.getParameter("email");
    
    %>
</head>
<body>

	<div class="container">
		    <div class="page-header">
			    <h1 id="logo" ><a href="http://www.eeplat.com"><img src="<%=request.getContextPath()%>/web/default/images/logo_300X90.jpg" alt="EEPlat" width="300" height="90"></img></a></h1>
			    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Original, agile & proven</p>
		    </div>
		    <div>
		       <h3 >注册成功，已发送验证邮件到<span class="email"><%=email%>。</span></h3>
               <p>请您进入邮箱中完成最后一步。</p>
		    </div>
		    <div class="row"></div> 
		     <div>
           		 <h4>收不到邮件？</h4>
             	 <span>请检查你的垃圾箱或广告箱，邮件有可能被误认为垃圾或广告邮件。</span>
      		</div>
		    
	</div>	    



   

</body>
</html>
