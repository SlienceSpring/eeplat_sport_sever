 <!-js befor loading-->
 <#if model.beforScript?exists>
	<script>
	   ${model.beforScript}
	</script>
 </#if>
 
 <#assign nopadding="">
<#if wrapperCol!='col-xs-12'>
  	<#assign nopadding="nopadding">
</#if>
  
<#if (wrapperRow?? && wrapperRow!= "")>
 	<div  class="row">
</#if>
	 <div id='${model.name}' class="${wrapperCol} leftmenupane  ${nopadding}">		
		${items_html}
	 </div>	
<#if wrapperRow!= "">
	</div>
</#if>

 	
 <!-js after loading-->
 <#if model.afterScript?exists>
	<script>
	    ${model.afterScript}
	 </script>
 </#if>