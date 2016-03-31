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