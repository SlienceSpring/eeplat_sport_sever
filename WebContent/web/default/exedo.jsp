<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.bo.DOService"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>
<%@ page import="com.exedosoft.plat.bo.DOResource"%>
<%@ page import="com.exedosoft.plat.bo.DOApplication"%>
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
	
	String mainStyle= "";
	try{

		mainStyle = DOGlobals.getInstance().getSessoinContext().getUser().getValue("style");
	}catch(Exception e){
		
	}
	if(mainStyle=="" || mainStyle==null){
		mainStyle = "_lan";
	}
	
	List apps = null;  
	if("EEPlat Console".equals(paneModelTitle)){
		apps = DOApplication.getApplications();
	}
	System.out.println(mainStyle);
%>
<head>
<title><%= I18n.instance().get(paneModelTitle)%></title>

<script language="javascript">

globalURL = "/<%=DOGlobals.URL%>/";

</script>
<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/css/redmond/jquery-ui-1.10.3.custom.min.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/css/compressed/all.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/metro/MetroJs.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/chosen/chosen.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/select2/select2.css" type="text/css"/>
	
	
<%=DOResource.getAllCssLink()%>

<!-- 插件的js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/wizard/jquery.steps.min.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugin/CodeMirror/codemirror-compressed.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/chosen/chosen.jquery.js" ></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/chosen/chosen.order.jquery.js" ></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/select2/select2.min.js" ></script>		


<% if ("en".equals(DOGlobals.getValue("lang.local"))){ %>	
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
<% }else{ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
<% }%>

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/compressed/all.js"></script>

<%=DOResource.getAllJavascriptLink()%>
<script language="javascript">

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
//窗口大小改变的时候，重新给div限制高度
$(window).resize(function(){
   	resscrEvt($(this).height(),$(this).width());
}); 

$(function(){

	////为第一个Tab 绑定事件
	var tabSelector = "#dvTab table[tabId='workbench_container']";
	var tabBtnSelector = tabSelector+" .btn";
	bindTabClickCss(tabSelector);
	bindTabCloseCss(tabBtnSelector);
	bindTabCloseWindow(tabBtnSelector);
	
	
	
	//初始化左右拖动
	  $(".resizeTd").mousedown(function(e){
		  var oldPageX = e.pageX;
		  var old_gLeW = $(".gLe").width();
		  var old_gRiW = $(".gRi").width();
		  $(document).bind('mousemove',function(e){
//////////////不能太小
			  if(e.pageX > 10){
				  $(".gLe").width(old_gLeW + e.pageX - oldPageX);
				  $(".gRi").width(old_gRiW - e.pageX + oldPageX);
				  $(".gFpage").width(old_gLeW + e.pageX - oldPageX-1);
				  resscrEvt();
			  }
			  window.status = e.pageX;	   
		  }).bind('mouseup',function(e){
			  $(document).unbind('mousemove');
			  $(document).unbind('mouseup');
			  $(".resizeTd")[0].releaseCapture && $(".resizeTd")[0].releaseCapture();
			  }
		  );
		  $(".resizeTd")[0].setCapture && $(".resizeTd")[0].setCapture();

	 });

	  resscrEvt();
});

</script>

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

</head>

<body  lang=zh>
   <script src="<%=request.getContextPath()%>/plugin/echarts/echarts.js"></script>
   <script>
      	    require.config({
                    paths:{ 
                    echarts:'plugin/echarts'
                }
            }); 
   </script>		
<div id='dmLayer'></div>
<input  type="hidden" id="mainStyle" value="<%=mainStyle %>"/> 
<%=paneModelContent%>
</body>
<script>

<%
//if(apps!=null && apps.size() == 1){
//  out.println("loadPml({'pml':'Wizard_New_Application.pml','target':'_opener','title':EELang.wizard,'pmlWidth':910,'pmlHeight':560})");
//}
%>

var cssEditor;
var jsEditor;
var htmlEditor;
var rhinoEditor;
</script>

</html>
