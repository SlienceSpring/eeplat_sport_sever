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

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
 


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
					    <legend>欢迎使用EEPlat。您可以先看看介绍文档或视频，或者只需简单地填写下面的表格，开始使用这个世界上最具扩展性、最强大的信息系统在线开发平台。
						
						<br/>在下面你可以创建第一个工程。
						</legend>
						
                                             <div class="form-group">
                                                <label for="name" class="col-sm-2 control-label">工程名称</label> 
                                                <div class="col-sm-10">
                                                    <input  class="form-control" type="NoSBCDot" name="name" id="name">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="username">用户名</label>
					      <div class="col-sm-10">
					        <input type="text" class="form-control" name="username" id="username">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="password">密码</label>
					      <div class="col-sm-10">
					        <input type="password" class="form-control" name="password" id="password">
					      </div>
					    </div>
                                              
					    <div class="form-group">
					      <label class="control-label col-sm-2" for="password2">再次输入</label>
					      <div class="col-sm-10">
					        <input type="password" class="form-control" name="password2" id="password2">
					      </div>
					    </div>
                                              
                                            <div class="form-actions">
                                             <button type="button" id="createprjbtn" class="btn btn-primary btn-lg col-sm-offset-2">创建工程</button>
                                            </div>
					  </fieldset>
					</form>
				</div><!-- .span -->
				<div class="col-xs-offset-1 col-xs-3">
					<div class="well">
						<h2>Links</h2>
						<h3>文档</h3>
						<ul>
							<li><a href="/<%=DOGlobals.URL%>/web/helper/firstprj.htm">第一个工程</a></li>
							<li><a href="https://github.com/weikexin/eeplat/tree/wiki/Introduce.md">平台简介</a></li>
							<li><a href="https://github.com/weikexin/eeplat/blob/wiki/ConfigTools.md">配置界面</a></li>
							<li><a href="http://www.eeplat.com">官方网站</a></li>
						</ul>
						<h3>视频</h3>
						<ul>
							<li><a href="http://v.youku.com/v_playlist/f5709117o1p0.html ">EEPlat培训专辑</a></li>
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