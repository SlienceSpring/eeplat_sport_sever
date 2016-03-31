<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html lang="en">
 <head>
<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" /> 
<!-- Jquery插件的css -->

<script language="javascript">
  globalURL = "/<%=DOGlobals.URL%>/";
</script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/bootstrap/assets/css/bootstrap-responsive.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/web/default/css/estop/estop.css" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.js" ></script>


<script type="text/javascript" 	src="<%=request.getContextPath()%>/web/default/js/main/main.js" ></script>
<% if ("en".equals(DOGlobals.getValue("lang.local"))){ %>	
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
<% }else{ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
<% }%>
<script language="javascript">
  globalURL = "/<%=DOGlobals.URL%>/";
</script>  

<script language="javascript">

  var cnmsg = {  

	    required: "必填字段",   

	    remote: "请修正该字段",   

	    email: "请输入正确格式的电子邮件",   

	    url: "请输入合法的网址",  

	    date: "请输入合法的日期",   

	    dateISO: "请输入合法的日期 (ISO).",  

	    number: "请输入合法的数字",   

	    digits: "只能输入整数",   

	    creditcard: "请输入合法的信用卡号",   

	    equalTo: "请再次输入相同的值",   

	    accept: "请输入拥有合法后缀名的字符串",   

	    maxlength: jQuery.format("请输入一个长度最多是 {0} 的字符串"),   

	    minlength: jQuery.format("请输入一个长度最少是 {0} 的字符串"),   

	    rangelength: jQuery.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),   

	    range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),   

	    max: jQuery.format("请输入一个最大为 {0} 的值"),  

	    min: jQuery.format("请输入一个最小为 {0} 的值")

	};

	jQuery.extend(jQuery.validator.messages, cnmsg); 
</script>  

    <meta charset="utf-8">

    <title>EEPlat</title>
    
	<meta name="viewport" content="width=device-width">

  </head>
  <body>
		<div class="container">
		    <div class="page-header">
			    <h1 id="logo" ><a href="http://www.eeplat.com"><img src="<%=request.getContextPath()%>/web/default/images/logo_300X90.jpg" alt="EEPlat" width="300" height="90"></img></a></h1>
			    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Original, agile & proven</p>
		    </div>
				<div class="row">
				<div class="span8">
					<form action="" id="a402881e731d64aac0131d661d8530002" class="form-horizontal">
					  <fieldset>
					    <legend>欢迎加入EEPlat AppShare, 填写完下列信息后，您就可以从AppShare中安装或发布应用了，AppShare不是普通的应用商店，应用间的信息是互联互通的。
						</legend>
				
				
				<div class="control-group">
				       <label class="control-label"  for="user_type" > &nbsp;&nbsp;您是 </label> 
				        <div class="controls">
				        	<label class="radio"  for="user_type_1">
				        	 	<input type="radio" name="user_type" id="user_type_1" value="1" checked="checked"/>公司
				        	</label> 	
				        	<label class="radio"  for="user_type_2">
				        		<input type="radio" name="user_type" id="user_type_2" value="2"/>个人
				        	</label>	
				        </div>
				 	</div>
				
					<div class="control-group">
				       <label class="control-label" for="gm_multi_tenancy_register_name"> &nbsp;&nbsp;名称 </label> 
				        <div class="controls">
				        	<input  style='border:#B3B3B3 1px solid;'  onclick="this.style.borderColor='#406B9B'" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'"  type='text' name='user_name' id='gm_multi_tenancy_register_l10n' title='&nbsp;&nbsp;公司名称'  class="input-xlarge"  maxlength='255' size="25"/>&nbsp;<span><font color='red'>*</font></span>
				        </div>
				 	</div>
				
				
				    <div class="control-group">
				       <label class="control-label" for="user_id"> &nbsp;&nbsp;简称 </label> 
				        <div class="controls">
				        	<input  style='border:#B3B3B3 1px solid;'  onclick="this.style.borderColor='#406B9B'" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'"  type='text' name='user_id' id='user_id' title='&nbsp;&nbsp;英文简称'    maxlength='32' size="25"/>&nbsp;<span><font color='red'>*</font></span>（简称，如国际商用机器公司简称IBM，没有简称请输入拼音，不要加空格。）
				        </div>
				 	</div>
				
				

				 	<div class="control-group">
				       <label class="control-label" for="user_email"> &nbsp;&nbsp;邮箱 </label> 
				        <div class="controls">
				        	<input  style='border:#B3B3B3 1px solid;'  onclick="this.style.borderColor='#406B9B'" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'"  type='text' name='user_email' id='user_email' title='&nbsp;&nbsp;账号（邮箱）'  class="input-xlarge"  maxlength='50' size="25"/>&nbsp;<span><font color='red'>*</font></span>
				        </div>
				 	</div>

				 	<div class="control-group">
				       <label class="control-label" for="gm_multi_tenancy_register_password"> &nbsp;&nbsp;密码 </label> 
				        <div class="controls">
				        	<input type='password' name='password' id='password' title='&nbsp;&nbsp;密码' class="input-xlarge"   maxlength='50' size="25"/>&nbsp;<font color='red'>*</font>
				        </div>
				 	</div>
				 	<div class="control-group">
				
				
			       <label class="control-label" for="password2"> &nbsp;&nbsp;确认密码 </label> 
				        <div class="controls">
				        	<input type='password' name='password2' id='password2' title='&nbsp;&nbsp;确认密码'  class="input-xlarge"   maxlength='50'  size="25"/>&nbsp;<font color='red'>*</font>
				        </div>
				 	</div>
				 	<div class="control-group">
				
				
			       <label class="control-label" for="gm_multi_tenancy_register_note"> &nbsp;&nbsp;备注 </label> 
				        <div class="controls">
				        	<textarea  name='note' id='gm_multi_tenancy_register_note' title='&nbsp;&nbsp;备注'  class="input-xlarge"   cols="50" rows="5"></textarea>
				        </div>
				 	</div>
				 	

			       <label class="control-label" for="randcode"> &nbsp;&nbsp;验证码 </label> 
				    <div class="controls">
						<input type="text" id="randcode" name="randcode"  class="input" style="height:18px; width:60px; border:solid 1px #cadcb2; font-size:12px; color:#81b432;" />
			        	<img src='<%=request.getContextPath()%>/web/default/image.jsp'  height="25px" style="cursor:pointer" onclick="this.src='<%=request.getContextPath()%>/web/default/image.jsp?' + Math.random()"   border=0 id="numImg" title="看不清，换一张!" />
					</div>				 	
						
						
					<div class="form-actions">
			           <button  type="submit" class="btn" id='402881e731d64aac0131d661de000009' >&nbsp;注册&nbsp;</button>
					</div>
					
					  </fieldset>
					</form>
				</div><!-- .span -->
			</div><!-- .row -->

      <hr/>




    </div> <!-- .container -->

		<script>
   $("#a402881e731d64aac0131d661d8530002").validate({

	   rules: {
		      user_name: {
		        minlength: 2,
		        required: true,
		        remote:'../web/default/checkParameter.jsp?parauid=402981e742bccdc20142bd0dff04035a',
		      },
		      user_email:{
		    	  required:true,
		    	  email:true,
		    	  remote:'../web/default/checkParameter.jsp?parauid=402981e742bccdc20142bd0dfef3034e',
		      },
		      user_id:{
		    	  required:true,
		    	  remote:'../web/default/checkParameter.jsp?parauid=402981e742bccdc20142bd0dfef60350',
		      },
		      password: {
		      	minlength: 6,
		        required: true
		      },
		      password2: {
		        minlength: 6,
		        equalTo:"#password", 
		        required: true
		      },
		      randcode: {
			        required: true
		      }
		    },
		    messages: {
		    	   user_name:{
		    		   minlength:"名称最少为2位。",
		    		   remote:"名称已经注册过，不能重复注册。"
		    	   },
		    	   user_id:{
		    		   remote:"简称已经被注册，请更换！",
		    		   NoSBCDot:true
		    	   },
		    	   user_email:{
		    		  email: "邮箱格式不合法。", 
		    		  remote:"邮件已经注册过，不能重复注册。"
		    	   },
		    	   password: {
		    		   minlength:"密码最少为6位。"
		    	   },
		    	   password2: {
		    	    equalTo: "两次录入的密码不一致。"
		    	   }
		    },
		    highlight: function(label) {
		    	$(label).closest('.control-group').addClass('error');
		    },
		    success: function(label) {
		    	label
		    		.text('OK!').addClass('valid')
		    		.closest('.control-group').addClass('success');
		    },
		    submitHandler:function(form){

			 	if(confirm("请检查邮箱是否正确，提交后您需要到邮箱进行激活，是否提交注册？")){
		           loading(EELang.loading);
		     	   var paras =  $('#a402881e731d64aac0131d661d8530002').serialize();
		     	   paras = paras + "&callType=uf&contextServiceName=eeplat_user_insert_md5";
		    	   ////为了防止多次提交,可以加验证码
		     	   $.post(globalURL + "servicecontroller",paras,
		     			function (data){
		     		   		if(data!=null && data.msg!=null && data.msg.indexOf('randcoderr')!=-1){
		     		   			alert("验证码错误！");
		    				   	closeWin();
		     		   		}else{
			     		   		var email = $('#user_email').val();
			     		   	    window.location = '<%=request.getContextPath()%>/web/default/register_success.jsp?email='+email;
		     		   		}
		     	  });
		        }
		    }
 });
</script>


  </body>
</html>
