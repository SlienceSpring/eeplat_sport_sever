 <#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>
 <style>
 	
 	.importheader{
 		padding-top:20px;
 		margin-left:20px;
 		font-size:30px;
 	}
 	
 	.importcontent{
 		margin-top:30px;
 		margin-left:20px;
 		font-size:13px;
 		line-height:20px;
 	}
 	
 	.importleft{
 		float: left;
 		width:60%;
 	}
 	.importright{
 		margin-left:60%;
 		width:40%;
 		font-size:13px;
 		line-height:20px;
 	}
 	.importright .h2{
		font-size:20px;
 	}
 	

 
    .btnImport{
    	margin-top:25px;
    	padding-left:30px;
    
    }
 
 </style> 
 <div>
 	<div class="importheader">
 		<span style="font-size:30px">${i18n('导入数据')}</span>
 	</div>
 
 
 	<div class="importcontent">
 		
 		<div class="importleft">
	
			您可以通过下载模板，根据模板文件来创建导入文件。
			<p>
				<span style="font-weight:bold"><a target='_blank' href="web/default/file/downloadfile_csv.jsp?tableName=${tablename}">下载模板</a>
			</p>
			<br/>
			
			<div class="myhtml5file span12"> 
				<input type="text" class="span6" id="fileName" name="fileName" title="${i18n('未选择文件')}" value="${i18n('未选择文件')}">
				<button  type="button" id="fileName_btn" class="span3">${i18n('浏览')}</button>
				<input type="file" id="fileName_show" name="fileName_show" value=""> 
			</div>
			
			<div class="btnImport">
				<button  type="button" id="btn_import" class="span3">${i18n('导入')}</button>
				<button  type="button" id="btn_cancel" class="span3">${i18n('取消')}</button>
			</div>
 		  
 		    <script>
			   $("#fileName_show").html5_upload({
			   				autoclear:false,
		                    url: "web/default/upload_action_uploadify.jsp",
		                    sendBoundary: window.FormData || $.browser.mozilla,
		                    onFinishOne : function(event, progress, name, number, total) {
		                    }
		        });
		                
				$("#fileName_btn").bind("click",
					 function(){
					 	$("#fileName_show").click();
					 }
				)
				
				$('#btn_cancel').bind('click',function(){
								var tabBtnSelector = "#dvTab  .on .btnTab";
								if($(tabBtnSelector).size() > 0){		
							  		tabCloseWindow(tabBtnSelector);
							  	}else{
							  		console.log("Bootstrap mode!");
							  		reloadMenuPml();
							  	}
				  }
				);
				
				
				 $('#btn_import').bind('click',function(){
				 	 var fileName = $("#fileName").val();
				 	 console.log("FileName::" + fileName);
				 	 if(fileName==null || fileName=="" || fileName=='未选择文件'){
				 	    alert(EELang.noSelectFile)
				 	 	return;
				 	 }
					 callAction(
						 {
						 	'btn': $('#btn_import')[0],
						 	'actionName':"com.exedosoft.plat.action.DOCSVImport",
						 	'paras':'tableName=${tablename}&csvfile=' + fileName
						 }
					 );		
				  }
				);
		  </script>  
 		
 		</div>
 		
 		<div class="importright">
	       <ul>
		        <b class="h2">重要提示</b> 
		        <p>&nbsp;</p> 
		        <li>只支持CSV格式(逗号分隔符文件)；Excel格式的数据您可以另存为CSV文件，然后再导入。</li>
		        <li>给定文件的第一行将视为字段名；可以先下载模板，根据模板文件的结构创建导入文件。 </li>
		        <li>每次导入最多支持2000条记录。</li>
		        <li>复选框值应该是1或者0。</li>
		        <li>日期值必须为yyyy-MM-dd格式。任何其它格式的日期都将被忽略。</li>
		        <li>日期时间必须符合yyyy-MM-dd hh:mm:ss的格式，其它格式的日期时间将被忽略。</li>
		        <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
		        <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
	       </ul>
 		</div><!--end right-->
 	
 	</div><!--end content-->
 
 <div><!--end-->
  