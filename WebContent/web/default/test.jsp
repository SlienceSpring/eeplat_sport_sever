<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>

<html>
<head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-1.6.2.min.js"></script>
</head>
<body>

  <div id="dz">
  
  
  </div>

</body>

<script>

	function load_ls(type,expect){
		//var lots = {1:1,2:17,3:15};
		console.log("1111");
		$.get('http://zx.500.com/zc/inc/ls_duizhen.php',{'gt':'ajax','lotid':1,'expect':'14051'},function(data){
			console.log(data);
		$('#dz').html(data);
		},'html');
	}

	load_ls();
</script>


</html>