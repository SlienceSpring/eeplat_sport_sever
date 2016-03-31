<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%
	DOBO bo = DOBO.getDOBOByName("do_pt_processtemplate");
//"contextKey=do_pt_processtemplate&contextValue=40288031286b14a901286b14a9160000"
	String contextkey = request.getParameter("contextKey");
	String contextValue = request.getParameter("contextValue");
	if(contextkey != null && contextValue != null
			&& contextkey.equals("do_pt_processtemplate")){
		bo.refreshContext(contextValue);
	}
	BOInstance curPt = bo.getCorrInstance();

	String ptName = "test";
	if (curPt != null) {
		ptName = curPt.getName();
	}

	String enPrix = "";
	if ("en".equals(DOGlobals.getValue("lang.local"))) {
		enPrix = "_en";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><%=I18n.instance().get("EEPlat工作流建模工具")%></title>
<script type="text/javascript" src="GooFlowLib/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.metadata.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.js"></script>

<script type="text/javascript">
  var globalURL = "/<%=DOGlobals.URL%>/";
</script>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/web/default/js/main/main.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/web/default/js/main/platAjax.js"></script>

<!-- GooFlow Lib -->
<link rel="stylesheet" type="text/css" href="GooFlowLib/default.css" />
<link rel="stylesheet" type="text/css" href="GooFlowLib/GooFlow_eeplat.css" />
<link rel="stylesheet" type="text/css" href="GooFlowLib/my.css" />
<script type="text/javascript" src="GooFlowLib/GooFlow_eeplat.js"></script>
<script type="text/javascript" src="GooFlowLib/my.js"></script>

</head>
<body>
	<div class="main">
		<div class='top'>
			<h2 style="text-align:center;"><%=I18n.instance().get("EEPlat工作流建模工具")%></h2>
		</div>
		<div class='left'>
			<div id='gooflowdiv' style='width:100%;height:100%;'>
			
			</div>
		</div>
		<div class='right'>
			<div id='nodepropertydiv'>
				<div><span>流程状态</span><input name='nodestateshow'></input></div>
				<div><span>回退时流程状态</span><input name='nodestateshowback'></input></div>
				<div><span>权限控制类</span><input name='accessclass'></input></div>
				<!-- <div><span>名称</span><input name='specname'></input></div> -->
				<div><span>通过意见界面表单字段</span><input name='passtxt'></input></div>
				<div><span>驳回意见界面表单字段</span><input name='rejecttxt'></input></div>
				<div><span>自动服务</span><input name='autoservice'></input></div>
				<div><span>权限验证类型</span><input name='authtype'></input></div>
				<div><span>审批面板名称</span><input name='panename'></input></div>
				<div><span>关联流程</span><input name='subflow'></input></div>
				<div><span>条件判断表达式</span><input name='decisionexpression'></input></div>
				<div><span>条件判断类型</span><input name='decisiontype'></input></div>
				<div><span>节点描述</span><input name='nodedesc'></input></div>
			</div>
			<div id='linepropertydiv'>
				<div><span>条件</span><input name='condition'></input></div>
			</div>
		</div>
	</div>
</body>
</html>