	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
	
	<#macro editable item>
		<div style="float:right;visibility:hidden;cursor:pointer" id="${item.objUid}_editor">
			<a onclick="ep.loadWidget('${item.objUid}')"><i class="fa fa-edit"></i></a>
		</div>
    </#macro>
	
	<#macro visibleEditor item>
		onmouseover="if(ep.isWidgeteditable){$('#${item.objUid}_editor').css('visibility','visible')}"  onmouseout="if(ep.isWidgeteditable){$('#${item.objUid}_editor').css('visibility','hidden')}"
    </#macro>
	
    <div class="box boxextend">
 		<div class="box-header" style="padding-left:15px">
 			
 			<div class="row">
 			<div class="col-md-6 col-xs-12">
	 			<h3>
	 			  <#if  data??>
		 			 <#if (data.bo.icon)??><i class='${data.bo.icon}'></i> </#if>  ${data.name} 
	 			  <#else>
	 			  	 ${model.caption?if_exists}
	 			  </#if>
	 			 </h3>
 			</div>
 			 <div class="col-md-6 col-xs-12" style="text-align:right;padding-top:17px">
 			  	<div style="float:right;margin-right:20px;">
	 			  		<#if (model.topOutGridFormLinks?size > 0) > 
							  <#list model.topOutGridFormLinks as item>
									${item.htmlValue}
							  </#list>
						</#if>
				</div>		
			</div>
			</div>			

		</div>	

	<#assign colNum = colNum?default(2)/>
	<#assign i = 0/>

     <div class="box-body table-responsive">
	
	<form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
											
	 <table id='g${model.objUid}' class='table  table-bordered ' >
	
		<tbody>
		
		 <#list mapForms?keys as aKey>
		 
		 	<tr>
		 	  <th  colspan="${(colNum-1)*2+2}" style='${aKey.style?default('height:25px;background-color:#337ab7;color:white')}'> <span onclick="$('.c${aKey.objUid}').toggle()"  style="cursor:pointer">${aKey.htmlValue}</span> </th>
		 	</tr>
			<#assign i = 0/>
			<tr class="c${aKey.objUid}">

			<#assign  theItems = mapForms?api.get(aKey) />
			
			<#list theItems as item>
				<#assign htmlValue  = ''/>
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
			
				<#--控制换行-->
			  	<#if (i>0 && (((i == colNum) ) ||item.newLine || (item_index > 0 && theItems[item_index-1].newLine) ) )>
				 	<#assign i = 0/>
				 	</tr>
				 	<tr class="c${aKey.objUid}">
				</#if> 	
				<#if (item.nameColspan?exists && item.nameColspan == 0) >
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==theItems?size  || theItems[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}"</#if> style='${item.style?if_exists}' <@visibleEditor item/> name='${item.colName}'> ${htmlValue} <@editable item/></td>
				<#else>                                                 
				    <th class="tdkey text-nowrap"  style="width:100px"> ${item.l10n}</th> 
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==theItems?size  || theItems[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> style='${item.style?if_exists}' <@visibleEditor item/> name='${item.colName}'>  ${htmlValue} <@editable item/></td>
				</#if>     
			    <#assign i = i + 1/>
			    </#if>
			</#list>
			</tr>
			</#list>
		
	<#--下面是按钮部分-->
			<#if  (model.bottomOutGridFormLinks?size > 0)>
				<tr class="buttonMore" > <td  style="text-align:center;align:center"  colspan="${colNum*2}" >
				<#list model.bottomOutGridFormLinks as item> 
			          <#if '${dataBind(data,item)}' ==''> ${item.htmlValue} </#if> &nbsp; 
				</#list>
				</td></tr>
			</#if>		
		</tbody>
	  </table>
	<#if (model.hiddenGridFormLinks?size > 0) > 
				<#list model.hiddenGridFormLinks as item> 
				    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
				</#list>
	</#if>
	  	</form>	
			</div><!--End box-body-->

 	</div><!--End Box-->

	
<script>
 	<#if model.category ??>
 	    $(".sidebar-menu li").css("background-color","");
    	$("#menu_${model.category.name}").css("background-color","white");   
	</#if>

ep.isWidgeteditable = true;

ep.loadWidget = function(widgetId){
	$('#' + widgetId + '_editor').parent()
	.load(
		   globalURL +	"web/default/htmlform.jsp?compare=true&formUid=" + widgetId + "&instanceuid=${data.uid}&bouid=${data.bo.objUid}", 
		   function(res){
		   		if(res!="" && res.length > 32){
			   		ep.isWidgeteditable = false;
			   		var htmls = "<a style='cursor:pointer' onclick=\"ep.widgetSave(this,'"
			   		+ widgetId + 
			   		"')\">保存</a>或<a style='cursor:pointer' onclick=\"ep.widgetCancel(this,'"
			   		+ widgetId + 
			   		"')\">取消</a>"
					$(this).append(htmls);
				}else{
					alert("不可修改！");
				}	
		   }
	);
}
ep.widgetSave = function(o,widgetId){

	<#if update_onevalue_service??>
	var inputName = $(o).parent().find(":input").attr("name");
	var inputValue = encodeURIComponent( $(o).parent().find(":input").val() );
	
	if($(o).parent().find(":input").valid()){
		callService({
			  'serviceName':'${update_onevalue_service}',
			   'callType' : 'uf',
		  	   'paras':'onecol=' + inputName + '&oneValue=' + inputValue,
		  	   'callback': function(){
		  	   	    ep.widgetCancel(o,widgetId);
		  	   }
		 	}
		);
		<#else>
			alert("No update service！");
		</#if>
	}
}
ep.widgetCancel = function(o,widgetId){

	$(o).parent()
	.load(
		   globalURL +	"web/default/htmlform.jsp?formUid=" + widgetId + "&instanceuid=${data.uid}&bouid=${data.bo.objUid}", 
		   function(){
			   var htmls = "<div style='float:right;visibility:hidden;cursor:pointer' id='"
			   + widgetId + "_editor'>"
			   + " <a onclick=\"ep.loadWidget('"
			   + widgetId + "')\"><i class='fa fa-edit'></i></a></div>";
			   $(this).append(htmls);
			   ep.isWidgeteditable = true;
		   }
	);
}


ep.oneValueSave("g${model.objUid}");
 
<#if  (model.name?contains("Browse")) >
 $('#g${model.objUid} tbody  tr').bind('click',function(){
			$('#g${model.objUid} tbody  tr.selected').removeClass("selected");//去掉原来的选中selected
			$(this).addClass("selected");
 });
</#if>
//validate
$("#a${model.objUid}").validate({ignoreTitle: true});
</script>
 

