	<#include "../form/TFormBase.ftl">
	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 

		
    <div class="box boxextend">
    	<#if (model.topOutGridFormLinks?size > 0) > 
 		<div class="box-header" style="padding-left:15px">
 			<div class="row">
 			 <div class="col-md-offset-5 col-md-7 col-xs-12" style="text-align:left;padding-top:17px">
							  <#list model.topOutGridFormLinks as item>
									${item.htmlValue}
							  </#list>
			</div>
			</div>			
		</div>	
		</#if>

	<#assign i = 0/>

     <div class="box-body table-responsive">
	
	<form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
											
	 <table id='g${model.objUid}' class='table  table-bordered table-edit' >
	
		<tbody>
		
		 <#list mapForms?keys as aKey>
		 
		 	<tr>
		 	  <th  colspan="${(colNum-1)*2+2}" style="${aKey.style?default('height:25px;background-color:#337ab7;color:white')}"> <span onclick="$('.c${aKey.objUid}').toggle()"  style="cursor:pointer">${aKey.htmlValue}</span> </th>
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
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==theItems?size  || theItems[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}"</#if> style='${item.style?if_exists}'> ${htmlValue} </td>
				<#else>                                                 
				    <th class="tdkey text-nowrap"  style="width:100px"> <@JudgeIsNull item/> ${item.l10n}</th> 
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==theItems?size  || theItems[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> style='${item.style?if_exists}'>  ${htmlValue} </td>
				</#if>     
			    <#assign i = i + 1/>
			   </#if>
			</#list>
			</tr>
			</#list>
		
		</tbody>
	  </table>
	<#if (model.hiddenGridFormLinks?size > 0) > 
				<#list model.hiddenGridFormLinks as item> 
				    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
				</#list>
	</#if>
	  	</form>	
			</div><!--End box-body-->
			
			<div class="box-footer">
			 	<div class="col-md-offset-5 col-md-7 col-xs-12" style="text-align:left;">
			
				<#list model.bottomOutGridFormLinks as item> 
			          <#if '${dataBind(data,item)}' ==''> ${item.htmlValue} </#if> 
				</#list>
				</div>
			</div>

 	</div><!--End Box-->

	
<script>

 
<#if  (model.name?contains("Browse")) >
 $('#g${model.objUid} tbody  tr').bind('click',function(){
			$('#g${model.objUid} tbody  tr.selected').removeClass("selected");//去掉原来的选中selected
			$(this).addClass("selected");
 });
</#if>
//validate
$("#a${model.objUid}").validate({ignoreTitle: true});

</script>
 

