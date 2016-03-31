 <!-js befor loading-->
 <#if model.beforScript?exists>
	<script>
	    ${beforScript}
	</script>
 </#if>
 
<#assign nopadding="">
<#if wrapperCol!='col-xs-12'>
  	<#assign nopadding="nopadding">
</#if>
 
 
<#if (wrapperRow?? && wrapperRow!= "")>
 	<div  class="row">
</#if>
	 <div id='${model.name}' class="${wrapperCol} ${nopadding}">	
	 
	 <#if model.navigationTxt?? >
	   <ol class="breadcrumb">
	   	${model.navigationTxt}
      </ol>
	 </#if>
	 
	 	
		${items_html}
	 </div>	
<#if wrapperRow!= "">
	</div>
</#if>

 	
 <!-js after loading-->
 <#if model.afterScript?exists>
	<script>
	    ${afterScript}
	 </script>
 </#if>