 <#if beforScript?exists>
	<script>
	    ${beforScript}
	</script>
 </#if>
<div id='${model.name}' style="width:100%;border-right:1px solid #6678b1" >
	<#if model.navigationTxt?exists>
		<div class='navcontainer'>
		    <div class='navleft'>
		       ${model.navigationTxt}
		    </div>
		 </div>
	 </#if>
 	${items_html}
</div>
 <#if afterScript?exists>
	<script>
	    ${afterScript}
	 </script>
 </#if>