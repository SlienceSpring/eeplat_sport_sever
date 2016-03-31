<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="AppShare 应用商店是基于EEPlat应用的商店，和传统商店不同的是，应用下载后所有的应用会融为一体，不存在互联互通的问题
">
<meta name="keywords" content="EEPlat、应用商店、互联互通">
<meta name="author" content="toweikexin@gmail.com">
<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico"
	type="image/x-icon" />
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />
<title>EEPlat Share</title>
<script language="javascript">
		globalURL = "/<%=DOGlobals.URL%>/";
</script>
<!-- Bootstrap core CSS -->
<link	href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css"	rel="stylesheet">
<style>
body {
	padding-top: 55px;
}

.jumbotron {
	margin-bottom: 5px;
}

/**
*
*  APPS
*
*/
.font14 {
	font-family: Arial;
	font-size: 14px;
}

.font12 {
	font-family: Arial;
	_font-family: 宋体;
	font-size: 12px;
}

.list_box {
	padding: 0;
	width: 800px;
}

.list_box ul {
	list-style: none;
}

.list_box li {
	vertical-align: middle;
}

.list_box .list_item {
	border-bottom: 1px solid #D8D8D8;
	padding: 12px 10px 5px 0px;
	overflow: hidden;
	margin: 0px;
}

.left {
	float: left;
}

.list_box .list_content {
	padding: 0 0 0 10px;
	margin: 0px;
	float: left;
	width: 465px;
	display: inline;
	word-break: normal;
	word-wrap: normal;
}

.list_box .list_content dt {
	height: 20px;
	line-height: 20px;
}

.list_box .list_title {
	display: inline-block;
	float: left;
	padding-top: 2px;
}

.list_box .list_title a {
	color: #333333;
	text-decoration: none;
}

.list_box .list_version {
	color: #528b00;
	display: inline-block;
	float: left;
	margin: 0px 10px;
	font-weight: bold;
}

.list_box .list_describe {
	line-height: 20px;
	margin: 5px 0px 8px 0px;
	color: #777777;
}

.list_r_w {
	width: 102px;
	float: right;
	valign: middle;
}

.list_star {
	float: left;
	margin: 4px 0px 0px 0px;
}

.star_4_s {
	width: 75px;
	height: 13px;
	overflow: hidden;
	background-position: -19px -52px;
}

.star_bg {
	background-repeat: no-repeat;
	display: block;
	float: left;
}

.official_tips_list {
	background-image: url("good.png");
	background-repeat: no-repeat;
	width: 67px;
	height: 24px;
	margin-left: 5px;
	float: left;
}

.sub_seach_time {
	color: #999999;
	display: inline;
	font-size: 12px;
	float: left;
}

.sub_right_9 {
	color: #999999;
	margin-left: 25px;
	font-size: 12px;
	float: left;
}

.z {
	clear: both;
	overflow: hidden;
	font-size: 0px;
	height: 0px;
	_width: 0;
}

dd {
	display: block;
	-webkit-margin-start: 40px;
}

.ui-tabs .ui-tabs-panel {
	padding: 0px;
}
</style>
</head>

<body>

	<!-- Collect the nav links, forms, and other content for toggling -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#eeplatnav">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img style="margin-top: -15px"
					src="images/logo_appshare.png"></img></a>
			</div>


			<div class="collapse navbar-collapse" id="eeplatnav">

				<ul class="nav navbar-nav">
					<li><a href="appshare_about.htm">关于AppShare</a></li>
					<li><a href="help_main.jsp">帮助</a></li>
					<!-- 
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
          <li class="divider"></li>
          <li><a href="#">One more separated link</a></li>
        </ul>
      </li>-->
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group ">
						<label></label> <input type="text" class="form-control"
							id="enterAppName" placeholder="请输入你要搜索的应用名称">
					</div>
					<button type="button" onclick="findApp()" class="btn btn-default">搜索</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="http://www.eeplat.com" target="_opener">EEPlat官网</a></li>
					<li><a
						href="http://share.eeplat.com/eeplatshare/appshare/register.jsp">注册</a></li>
					<li><a href="http://share.eeplat.com/eeplatshare">登录</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
	</nav>

	<!--  主体内容 -->
	<div class="container">

		<div class="jumbotron" style="background: url(images/spanner.jpg)">
			<h1>
				商店中应用被下载后会融合在一起，<br /> 天然的互联互通！
			</h1>
		</div>

		<div class="row">

			<div class="col-sm-3">
				<div class="list-group">
					<a href="#" class="list-group-item active"> 全部 </a>
					<a href="#"
						class="list-group-item">全部系统</a> 
				</div>
			</div>
			<div class="col-sm-9" id="mainapps"></div>
		</div>
	</div>

	、


	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">EEPlat Engine</h4>
				</div>
				<div class="modal-body">
					应用程序须运行在EEPlat之上，您可以通过以下方式使用EEPlat：<br /> 1，war方式。 请<a
						href="http://www.eeplat.com/eeplat.war" target="_opener"> 下载 </a>。<br />
					2，源代码方式。请fork： <a href="https://github.com/weikexin/eeplat"
						target="_opener">git@github.com:weikexin/eeplat.git</a>。<br />
					3，PaaS方式。请注册使用： <a href="http://cloud.eeplat.com/multimanager/exedo/webv3/setup/"
						target="_opener">http://cloud.eeplat.com/multimanager/exedo/webv3/setup/</a>。<br />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
================================================== -->

        
        <script src='<%=request.getContextPath()%>/web/bootstrap/assets/js/jquery.min.js'></script>
	<script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.js" ></script>		

        <%
                if ("en".equals(DOGlobals.getValue("lang.local"))) {
        %>
        <script type="text/javascript"
                src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"></script>
        <%
                } else {
        %>
        <script type="text/javascript"
                src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"></script>
        <%
                }
        %>

        <script type="text/javascript"  src="<%=request.getContextPath()%>/web/bootstrap/assets/js/main.js"></script>
	<script type="text/javascript"  src="<%=request.getContextPath()%>/web/bootstrap/assets/js/platAjax.js"></script>

	<script>
		listApp("");

		function listApp(appName) {
			$("#mainapps").load(
					"/eeplatshare/eeplat_appshare.pml?app_name="
							+ appName);
		}

		function findApp() {
			listApp(encodeURIComponent(escape($('#enterAppName').val())));

		}
	</script>


</body>
</html>
