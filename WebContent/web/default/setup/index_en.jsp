<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>
<%@ page import="com.exedosoft.plat.bo.DOApplication"%>
<%@ page import="java.util.List"%>
<script language="javascript">
  globalURL = "/<%=DOGlobals.URL%>/";
</script> 


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="en">
 <head>
<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<!-- Jquery插件的css -->

<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/style.css" type="text/css"/>
<link href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/css/estop/estop.css" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.min.js" ></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
 


<script type="text/javascript" 	src="<%=request.getContextPath()%>/web/default/js/main/main.js" ></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/web/default/js/main/platAjax.js" ></script>

 

    <meta charset="utf-8">

    <title>EEPlat</title>
    
	<meta name="viewport" content="width=device-width">

  </head>
  <body>
		<div class="container">
		    <div class="page-header">
			    <h1 id="logo" ><img src="../images/logo_300X90.jpg" alt="EEPlat" width="300" height="90"></h1>
			    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Original, agile & proven</p>
		    </div>
				<div class="row">
				<div class="col-xs-8">
					<form action="" id="contact-form" class="form-horizontal">
					  <fieldset>
					    <legend> Welcome to EEPlat.Please take a look at our introduction documents or videos presentations,or just fill out the form below, and start using the world's most scalable and powerful platform of enterprise applications.
						
						<br/>Create the first project below.
						</legend>
						
				
                                             <div class="form-group">
                                                <label for="name" class="col-sm-2 control-label">Project Name</label> 
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" name="name" id="name">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="username">User Name</label>
					      <div class="col-sm-10">
					        <input type="text" class="form-control" name="username" id="username">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="password">Password</label>
					      <div class="col-sm-10">
					        <input type="password" class="form-control" name="password" id="password">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="password2">Re-enter password</label>
					      <div class="col-sm-10">
					        <input type="password" class="form-control" name="password2" id="password2">
					      </div>
					    </div>
                                              
                                            <div class="form-actions">
                                             <button type="button" id="createprjbtn" class="btn btn-primary btn-lg col-sm-offset-2">Create Project</button>
                                            </div>
					</form>
				</div><!-- .span -->
				<div class="col-xs-offset-1 col-xs-3">
					<div class="well">
						<h2>Links</h2>
						<h3>Document</h3>
						<ul>
							<li><a href="/<%=DOGlobals.URL%>/web/helper/firstprj.htm">The First Project</a></li>
							<li><a href="https://github.com/weikexin/eeplat/tree/wiki/Introduce.md">Introduction</a></li>
							<li><a href="https://github.com/weikexin/eeplat/blob/wiki/ConfigTools.md">Configuration Panel</a></li>
							<li><a href="http://www.eeplat.com">Official Website</a></li>
						</ul>
					</div>
				</div><!-- .span -->
			</div><!-- .row -->

      <hr>

      <footer>
      </footer>


    </div> <!-- .container -->

		<script src="script.js"></script>

  </body>
</html>
