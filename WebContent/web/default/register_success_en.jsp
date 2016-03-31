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
		       <h3 >Registration successful, already send the validation email to <span class="email"><%=email%>.</span></h3>
               <p>Please enter the email to complete the last step.</p>
		    </div>
		    <div class="row"></div> 
		     <div>
           		 <h4> Not receiving the activation email? </h4>
             	 <span>Please check your trash or advertising box.</span>
      		</div>
		    
	</div>	    



   

</body>
</html>
