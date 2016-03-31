<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.bo.DOService"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>
<%@ page import="com.exedosoft.plat.bo.DOResource"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<html>
<%

	String paneModelContent = (String) request
			.getAttribute("paneModelContent");
	String paneModelTitle = (String) request
			.getAttribute("paneModelTitle");
	SessionContext context = (SessionContext) session
			.getAttribute("userInfo");
	if (null == session.getAttribute("userInfo")
			|| context.getUser() == null) {
		response.sendRedirect(request.getContextPath()
		+ "/web/default/logoff.jsp");
	}
	if (paneModelTitle == null) {
		paneModelTitle   = "Welcome to use EEPlat!";
	}

	if (paneModelContent == null) {
		paneModelContent = "Welcome to use EEPlat!";
	}

%>
	<head>
	<title><%= I18n.instance().get(paneModelTitle)%></title>
	<script language="javascript">
		globalURL = "/<%=DOGlobals.URL%>/";
	</script>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	
	<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 


		<!--basic styles-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css"   type="text/css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/font-awesome.min.css"    type="text/css"/>

		<link rel="stylesheet"  href="<%=request.getContextPath()%>/web/bootstrap/assets/css/jquery-ui-1.10.3.full.min.css"  type="text/css"/>

		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/ionicons.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/AdminLTE.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/datepicker/datepicker3.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/daterangepicker/daterangepicker-bs3.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/select2/select2.min.css" type="text/css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/plugins/fullcalendar/fullcalendar.min.css" type="text/css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/plugins/fullcalendar/fullcalendar.print.css"  type="text/css" media='print' />

		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/css/xtree2.css" type="text/css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/pagination/pagination.css" type="text/css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/style.css" type="text/css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/main.css" />  
		<%=DOResource.getAllCssLink()%>
		
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/html5shiv.js"></script>
          <script src="<%=request.getContextPath()%>/web/bootstrap/assets/js//respond.min.js"></script>
        <![endif]-->
		
				<style type="text/css">
			<%
			//加载自定义css
			DOService aService = DOService.getService("DO_BO_Icon_List_css_valid");
			if(aService!=null){
				  List list = aService.invokeSelect();
				  for(Iterator it = list.iterator(); it.hasNext();){
					   BOInstance bi = (BOInstance)it.next();
					   if(bi!=null && bi.getValue("formulaScript")!=null)
					   out.println(bi.getValue("formulaScript"));
				  }
			}
			%>
		</style>


		<!--inline styles if any-->
		
		
	
		<script src='<%=request.getContextPath()%>/web/bootstrap/assets/js/jquery.min.js'></script>
		<script src='<%=request.getContextPath()%>/web/bootstrap/assets/js/jquery-ui.js'></script>
		<script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/bootstrap.min.js"></script>

        <script src="<%=request.getContextPath()%>/web/bootstrap/assets/plugins/fullcalendar/moment.min.js"></script>
        <script src="<%=request.getContextPath()%>/web/bootstrap/assets/plugins/fullcalendar/fullcalendar.js"></script>
        <script src="<%=request.getContextPath()%>/web/bootstrap/assets/plugins/fullcalendar/zh-cn.js"></script>

		<script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/AdminLTE/app.js"></script>
		<script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/tokenfield/bootstrap-tokenfield.min.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/toolbar/toolbar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/pagination/jquery.pagination.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/fileuploader/jquery.uploadify.min.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/fileuploader/jquery.html5_upload.js" ></script>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/treetable/jquery.treeTable.min.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/facebook/jquery.elastic.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.js" ></script>		
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/web/bootstrap/assets/js/select2/select2.full.min.js" ></script>		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/treev2/xtree2.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/treev2/xloadtree2.js" ></script> 


		<script type="text/javascript" src="<%=request.getContextPath() %>/plugin/kindeditor/kindeditor-min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/plugin/kindeditor/lang/zh_CN.js"></script>
		
		<% if ("en".equals(DOGlobals.getValue("lang.local"))){ %>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
		<% }else{ %>
		<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
		<% }%>
		
		<script type="text/javascript"  src="<%=request.getContextPath()%>/web/bootstrap/assets/js/main.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath()%>/web/bootstrap/assets/js/platAjax.js"></script>
		
		<%=DOResource.getAllJavascriptLink()%>

		<script>

		var cnmsg = {

			    required: "不能为空",   

			    remote: "不合法，请修改",   

			    email: "请输入正确格式的电子邮件",   

			    url: "请输入合法的网址",  

			    date: "请输入合法的日期",   

			    dateISO: "请输入合法的日期 (ISO).",  

			    number: "请输入合法的数字",   

			    digits: "只能输入整数",   

			    creditcard: "请输入合法的信用卡号",   

			    equalTo: "请再次输入相同的值",   

			    accept: "请输入拥有合法后缀名的字符串",   

			    maxlength:  jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),   

			    minlength:  jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),   

			    rangelength:  jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),   

			    range:  jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),   

			    max:  jQuery.validator.format("请输入一个最大为 {0} 的值"),  

			    min:  jQuery.validator.format("请输入一个最小为 {0} 的值")

		};
		jQuery.extend(jQuery.validator.messages, cnmsg);    
			var cssEditor;
			var jsEditor;
			var htmlEditor;
			var rhinoEditor;
		</script>

	</head>

	<body class="skin-blue">
            		<!-- ECharts -->
                <script src="<%=request.getContextPath()%>/plugin/echarts/echarts.js"></script>
                <script>
                  	    require.config({
                                paths:{ 
                                    echarts:'plugin/echarts'
                                }
                            }); 
                </script>		
				
	  <%=paneModelContent%>   
		<div id='dmLayer'></div>
			
	</body>
</html>
