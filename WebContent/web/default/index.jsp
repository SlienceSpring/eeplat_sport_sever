<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%@ page import="com.exedosoft.plat.bo.DOResource"%>
<!DOCTYPE html>
<html>
<head>
<%

Object trysize =  session.getAttribute("trysize");

if( request.getLocale().getLanguage().startsWith("zh")){
	DOGlobals.changeLocal("cn");
}else{
	DOGlobals.changeLocal("en");
}
String loginService = "do_org_account_findbynameAndPassword_md5";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=I18n.instance().get("Welcome Login")%></title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>

<script language="javascript">
  globalURL = "/<%=DOGlobals.URL%>/";
</script>  


<%
  String url = 	DOResource.getSpecialPath(DOResource.TYPE_LOGO_LOGIN);
  if(url==null){
	  url = request.getContextPath() + "/web/default/images/logo_console.png";

  }
%>


<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />

<link rel="stylesheet"  href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css"   type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/font-awesome.min.css"    type="text/css"/>


<style type="text/css">
	body {
		background-color: #fff;
	}
	.header{
		text-align:center;
	}
	
	.form-box{
		padding:20px;
	}

	#loginform{
		margin-top:10px;
	}
	
	.panel-body{
		padding-top:30px;
	}
</style>

</head>

  <body class="bg-white">
  	<div class="form-box">  
        <div class="header "  >
        	<img border=0   class="img-responsive center-block"  src="<%=url%>"/>
        </div>
        <form name="loginform" id="loginform"  method="post">
		
		<div class="row">
		<div class="col-xs-12  col-md-4  col-md-offset-4">
			
        <div class="panel panel-primary">
            <div class="panel-heading"><%=I18n.instance().get("Login")%></div>
            <div class="panel-body">
                    <div class="row form-group">
                     	<div class="col-xs-12 ">
                        	<input type="text" name="name" class="form-control" placeholder="<%=I18n.instance().get("UserName")%>"/>
                        </div>
                    </div>
                    <div class="row form-group">
                     	<div class="col-xs-12 ">
                        <input type="password" name="password" class="form-control" placeholder="<%=I18n.instance().get("Paasword")%>"/>
                        </div>
                    </div>

                   <div class="row form-group random">
                     <div class="col-xs-8 ">
					    <input type="text" name="randcode"   class="form-control  " placeholder="<%=I18n.instance().get("Verification")%>" />
					 </div>
                     <div class="col-xs-4" style="padding:0px">
				        <img src='<%=request.getContextPath()%>/web/default/image.jsp' height="25px" style="margin-top:4px"  border=0 id="numImg"  />
				     </div>   
					</div>	


	            </div> <!--end panel-body -->   
	            
	           	<div class="panel-footer">
	                    <button type="button" class="btn btn-primary btn-block"><%=I18n.instance().get("Login")%></button>
	            </div><!-- end footer -->
	            
        </div><!-- end panel -->
        </div>
        </div><!-- wrapper row -->
        </form>
        </div>
  </body>      



<script src='<%=request.getContextPath()%>/web/bootstrap/assets/js/jquery.min.js'></script>
<script src="<%=request.getContextPath()%>/web/bootstrap/assets/js/bootstrap.min.js"></script>
<% if ("en".equals(DOGlobals.getValue("lang.local"))){ %>	
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
<% }else{ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
<% }%>
<script type="text/javascript"  src="<%=request.getContextPath()%>/web/bootstrap/assets/js/main.js"></script>

<script language="javascript">

$(function(){
//回车事件

	var  mobileclient="false";
		   
	if ($(window).width() <= 992) {
	   mobileclient = "true";
	}
	
	if(mobileclient=="false"){
		$(".form-box").css("margin-top","50px");
	}

	<%if (trysize==null || ((Integer)trysize).intValue() < 4  ) {%>
		$(".random").hide();
	<%}%>	

  	$(document).keypress(function (e) {
		     var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		     if(keyCode==13){
		        submitForm();
		     }
	});

	 

  	$("body").css("height",$(window).height());
  	
  	$(".btn:first").bind("click",function(){
 
  	  		submitForm();
  	  });

  	
	$("#numImg").bind("click",function(){
  			imgChange(this);
	});
	
	 
	  //换验证码
	  function imgChange(obj){

	  		$(obj).attr("src","<%=request.getContextPath()%>/web/default/image.jsp?"+ Math.random());

	  }

	 
	  //登录
	  function submitForm(){

	 		var userName = $("input:eq(0)").val();
		  		var passWord = $("input:eq(1)").val();
		  		var randCode = $("input:eq(2)").val();
			if(userName==""){
				$("input:eq(0)").focus();
				alert(EELang.accountRequired);
				return;
			}
			if(passWord==""){
				$("input:eq(1)").focus();
				alert(EELang.pwdRequired);
				return;
			}
			if($(".random").is(":visible") && randCode==""){
				$("input:eq(2)").focus();
				alert(EELang.verificationRequired);
				return;
			}
		    loading(EELang.loading);

		   var paras =  $('#loginform').serialize();

		   paras = paras + "&contextServiceName=<%=loginService%>&mobileclient=" + mobileclient ;
		   
		   console.log("paras::" + paras);

		   $.post(globalURL + "ssocontroller",paras,

				function (data, textStatus){

				   var retValue = unescape(data.returnValue);
				   var echoStr = unescape(data.echoStr);
				   
				   if('success'==retValue){
				        window.location= globalURL + "console.pml?isApp=true";
				   }
				   else if('overtry'==retValue){
					   $(".random").show();
					   	closeWin();
				   }
				   else{
					  	alert(echoStr);
					   	imgChange($("#numImg"));
					   	closeWin();
				   }
		  },"json");
	  }
	 
});

 
</script>
</html>