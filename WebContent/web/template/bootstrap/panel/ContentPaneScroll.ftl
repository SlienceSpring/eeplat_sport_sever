<#assign nopadding="">
<#if wrapperCol!='col-xs-12'>
  	<#assign nopadding="nopadding">
</#if>
 
 
<#if (wrapperRow?? && wrapperRow!= "")>
 	<div id='${model.name}'  class="row">
 	<div  class="${wrapperCol} ${nopadding}">
<#else>
 	<div id='${model.name}' class="${wrapperCol} ${nopadding}">		
</#if>
 <#if beforScript?exists>
	<script>
	    ${beforScript}
	</script>
 </#if>
		${items_html}
 <#if afterScript?exists>
	<script>
	    ${afterScript}
	 </script>
 </#if>		
<#if  (wrapperRow?? && wrapperRow!= "")>
	</div>	</div>
<#else>
	</div>	
</#if>
