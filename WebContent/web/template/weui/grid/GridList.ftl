<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/> 
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>  
<#if onlyData == false >
<div class="weui_panel weui_panel_access">
 <div class="weui_panel_bd">
  <form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
  <div class="weui_cells" id="${model.objUid}">
</#if> 
	   <#list data as ins>
	   		<a class="weui_cell" href="javascript:ep.loadPml({'pmlName':'${model.containerPane.category.mainPaneModel.name}','paras':'dataBus=setContext&contextKey=${ins.bo.name}&contextValue=${ins.uid}'})">
			<#list model.normalGridFormLinks as item> 
				<#if '${dataBind(ins,item)}' ==''> ${item.htmlValue} </#if>
			</#list>
			</a>
	    </#list>
		<#if (pageNo == pageSize)> 
		<script>
		   $("#more${model.objUid}").remove();
		</script>
		</#if>
<#if onlyData == false >
	</div>
		<#if (model.hiddenGridFormLinks?size > 0) > 
					<#list model.hiddenGridFormLinks as item> 
					    ${item.htmlValue}  &nbsp; 
					</#list>
		</#if>
  </form>
</div>
<#if (pageNo < pageSize)> 
<a class="weui_panel_ft" id="more${model.objUid}" pageNo="${pageNo}"  pageSize="${pageSize}" href="javascript:ep.moreLoad('${model.objUid}','${model.containerPane.name}','more${model.objUid}');">查看更多</a>	
</#if>
</div>
</#if>