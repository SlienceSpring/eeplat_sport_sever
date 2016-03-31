 <#assign nopadding="">
<#if wrapperCol!='col-xs-12'>
  	<#assign nopadding="nopadding">
</#if>
  
<#if (wrapperRow?? && wrapperRow!= "")>
 	<div id='${model.name}'  class="row">
 	<div  class="${wrapperCol} leftmenupane ${nopadding}">
<#else>
 	<div id='${model.name}' class="${wrapperCol} leftmenupane ${nopadding}">		
</#if>
 <#if model.beforScript?exists>
	<script>
	   ${beforScript}
	</script>
 </#if>
		${items_html}
 <#if model.afterScript?exists>
	<script>
	    ${afterScript}
	 </script>
 </#if>
<#if  (wrapperRow?? && wrapperRow!= "")>
	</div>	</div>
<#else>
	</div>	
</#if>
