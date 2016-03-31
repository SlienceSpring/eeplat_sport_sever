	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
	
	
	<#macro editable item>
		
		<div style="float:right;visibility:hidden" id="${item.fullColID}_editor">
			<a onclick="console.log('click me:${item.objUid} ')"><i class="fa fa-edit"></i></a>
		</div>

    </#macro>
	
	
	<#macro visibleEditor item>
		
		onmouseover="$('#${item.fullColID}_editor').css('visibility','visible')"  onmouseout="$('#${item.fullColID}_editor').css('visibility','hidden')"

    </#macro>
	
	
    <div class="box boxextend">
 		<div class="box-header" style="padding-left:15px">
 			
 			<div class="row">
 			<div class="col-md-6 col-xs-12">
	 			<h3>
	 			  <#if  data??>
		 			  ${data.name} 
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
			<#assign i = 0/>
			<#if (model.abstractGridFormLinks?size > 0)>
			<tr>
			<#list model.abstractGridFormLinks as item>
				<#--控制换行-->
			  	<#if (i>0 && (((i == colNum) ) ||item.newLine || (item_index > 0 && model.abstractGridFormLinks[item_index-1].newLine) ) )>
				 	<#assign i = 0/>
				 	</tr>
				 	<tr>
				</#if> 	
				
				<#assign htmlValue  = ''/>
				
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
				</#if>
				<#if (item.nameColspan?exists && item.nameColspan == 0) >
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.abstractGridFormLinks?size  || model.abstractGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}"</#if> style='${item.style?if_exists}' <@visibleEditor item/> > ${htmlValue} <@editable item/></td>
				<#else>                                                 
				    <th class="tdkey text-nowrap"  style="width:100px"> ${item.l10n}</th> 
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.abstractGridFormLinks?size  || model.abstractGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> style='${item.style?if_exists}' <@visibleEditor item/>>  ${htmlValue} <@editable item/></td>
				</#if>     
			    <#assign i = i + 1/>    
			</#list>
			</tr>
			</#if>
			
			
			<#if (model.moreGridFormLinks?size > 0) >
				<#if (model.abstractGridFormLinks?size > 0)>   
				<tr>
					<td colspan="${colNum*2}" style="cursor:pointer" ><span style="cursor:pointer" onclick="toggleMore(this);"><b>${i18n('More')}</b></span></td>
				</tr>
				</#if>
			<#macro JudgeDisplay >
			    <#if (model.abstractGridFormLinks?size > 0)>
			    	style="display:none"
			    </#if>
			</#macro>
			
			<tr  <#compress><@JudgeDisplay/></#compress> >
			<#assign i = 0/>
			<#list model.moreGridFormLinks as item>
			     <#--控制换行-->
			  	<#if (i>0 && ((i == colNum) ||item.newLine || (item_index > 0 &&  model.moreGridFormLinks[item_index-1].newLine) ) )>
				 	<#assign i = 0/>
				 	</tr>
				 	<tr  <#compress><@JudgeDisplay/></#compress> >
				</#if> 	
				<#assign htmlValue  = ''/>
				
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
				</#if>
				<#if (item.nameColspan?exists && item.nameColspan == 0) >
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if> <#if ((i == (colNum-1)) || item.newLine || (item_index+1)==model.moreGridFormLinks?size || model.moreGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}" <#else>  colspan="${(colNum-i-1)*2}"  </#if> style='${item.style?if_exists}' <@visibleEditor item/>> ${htmlValue} <@editable item/></td>
				<#else>
				    <th class="tdkey text-nowrap"   style="width:100px"> ${item.l10n}</th>
				    <td  <#if item.noWrapValue>nowrap='nowrap'</#if> <#if (item.newLine || (item_index+1)==model.moreGridFormLinks?size || model.moreGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> style='${item.style?if_exists}' <@visibleEditor item/>> ${htmlValue} <@editable item/></td>
			    </#if>
			     
				<#assign i = i + 1/>
			</#list>
			</tr>	
	<#--下面是按钮部分-->
			<#if  (model.bottomOutGridFormLinks?size > 0)>
				<tr class="buttonMore" > <td  style="text-align:center;align:center"  colspan="${colNum*2}" >
				<#list model.bottomOutGridFormLinks as item> 
			          <#if '${dataBind(data,item)}' ==''> ${item.htmlValue} </#if> &nbsp; 
				</#list>
				</td></tr>
			</#if>		
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

 
<#if  (model.name?contains("Browse")) >
 $('#g${model.objUid} tbody  tr').bind('click',function(){
			$('#g${model.objUid} tbody  tr.selected').removeClass("selected");//去掉原来的选中selected
			$(this).addClass("selected");
 });
</#if>
//validate
$("#a${model.objUid}").validate({ignoreTitle: true});

</script>
 

