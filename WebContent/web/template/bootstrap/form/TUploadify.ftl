 <#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
 
 
  <div class="myhtml5file form-inline"> 
	<input type="text"  class="form-control span6"  id="${model.fullColID}"  name="${model.colName}" title="${model.value?default(i18n('未选择文件'))}"  value="${model.value?default(i18n('未选择文件'))}" />
	<button  type="button" id="${model.fullColID}_btn" class="span3">${i18n('浏览')}</button>
	<input type="file"  id="${model.fullColID}_show"  name="${model.colName}_show"  value="${model.value?if_exists}"/> 
</div> 

  <script>
////multiple="multiple" 这个属性可以支持多个文件上传	
	   $("#${model.fullColID}_show").html5_upload({
	   				autoclear:false,
                    url: "web/default/upload_action_uploadify.jsp",
                    sendBoundary: window.FormData || $.browser.mozilla,
                    onFinishOne : function(event, progress, name, number, total) {
                    }
                });
                
	$("#${model.fullColID}_btn").bind(
	 "click",
	 function(){
	 	$("#${model.fullColID}_show").click();
	 }
	)
  </script>  