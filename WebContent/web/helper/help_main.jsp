<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" charset="UTF-8" />
<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico"
	type="image/x-icon" />
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />
<title>EEPlat Share</title>
<!-- Bootstrap core CSS -->
<link	href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css"	rel="stylesheet">

	<link rel="stylesheet" type="text/css" media="all"
		href="./eeplatfiles/style.css" />
	<title>如何注册EEPlat</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script type='text/javascript'>
		function loadifram(htmlsrc) {
			document.getElementById('iframeload').src = htmlsrc + '?random='
					+ Math.random();
		}
		function setiframheight(obj) {

			var win = obj.contentDocument ? obj.contentDocument : obj.Document;
			if (document.getElementById) {
				if (win && !window.opera) {
					obj.style.height = win.body.scrollHeight + 'px';
					//					if (win.contentDocument && win.contentDocument.body.offsetHeight) 
					//						{alert(0);win.height = win.contentDocument.body.offsetHeight;} 
					//					else if(win.Document && win.Document.body.scrollHeight) 
					//					{alert(1);win.height = win.Document.body.scrollHeight; }
				}
			}
		}
	</script>
</head>
<body>
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
			<a class="navbar-brand" href="http://share.eeplat.com/eeplatshare/web/site"><img style="margin-top: -15px"
				src="images/logo_appshare.png"></img></a>
		</div>


		<div class="collapse navbar-collapse" id="eeplatnav">

			<ul class="nav navbar-nav">
				<li><a href="appshare_about.htm">关于AppShare</a></li>
				<li><a href="#">帮助</a></li>
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

	<div class="body">
		<div id="zhongdakuang">
			<div id="baokuang">
				<div class="zuo1kuang">
					<a href="#"> <img src="./eeplatfiles/b1-150x2.gif" /></a>
				</div>
				<iframe frameBorder='0' scrolling='no' onload='setiframheight(this)'
					id='iframeload' style='width: 100%; float: left; border: 0px;'
					src="how_register.htm"> </iframe>
			</div>
			<div id="zhongyoukuang">
				<div class="youzi1">
					<h2>使用帮助</h2>
					<div class="textwidget">
						<table width="100%" border="0">
							<tbody>
								<tr>
									<td height="25"><a href='javascript:void(0)'
										onclick="loadifram('how_register.htm')">注册登录eeplat</a></td>
								</tr>
								<tr>
									<td height="25"><a href='javascript:void(0)'
										onclick="loadifram('how_register_appshare.htm');">注册登录eeplat_appshare</a></td>
								</tr>
								<tr>
									<td height="25"><a href='javascript:void(0)'
										onclick="loadifram('how_install_appshare.htm');">从appshare安装应用</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!-- <div class="youzi1">
					<h2>注册登录流程</h2>
					<div class="textwidget">
						<table width="100%" border="0">
							<tbody>
								<tr>
									<td height="25"><a
										href="#">修改个人资料</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div> -->
				<div id="kongbai"></div>
			</div>
		</div>
	</div>
	<div class="c"></div>
	<div class="b-foot">
		<div class="b-foot-menu">
			<ul
				id="menu-%e9%a1%b6%e9%83%a8%e5%b0%be%e9%83%a8%e5%af%bc%e8%88%aa-1"
				class="nav-menu-list">
				<li
					class="menu-item menu-item-type-post_type menu-item-object-page menu-item-69">
					<a href="#">加入EEPlat</a>
				</li>
				<li
					class="menu-item menu-item-type-post_type menu-item-object-page menu-item-42">
					<a href="#">使用协议</a>
				</li>
				<li
					class="menu-item menu-item-type-post_type menu-item-object-page menu-item-45">
					<a href="#">关于EEPlat</a>
				</li>
				<li
					class="menu-item menu-item-type-post_type menu-item-object-page menu-item-59">
					<a href="#">首页</a>
				</li>
			</ul>
		</div>
		<div class="c"></div>
		<div class="b-foot-menu">
			www.eeplat.com 2014 © All Rights Reserved. &nbsp;&nbsp; <a
				href="http://www.eeplat.com/" target="_blank">京ICP备1336890-1</a>
		</div>
		<div class="c"></div>

	</div>
</body>
</html>