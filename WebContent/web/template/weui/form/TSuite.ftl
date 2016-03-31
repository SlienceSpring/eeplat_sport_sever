<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/>  
   <#list model.linkForms as item>
      <#if '${dataBind(model.data,item)}' ==''>
  		 <#if item.newLine><br/></#if>  ${item.htmlValue} </#if>  
   </#list>