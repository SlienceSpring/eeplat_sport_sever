<div class=tree id='${model.nodeName}'></div>
<script language="javascript">
	webFXTreeHandler.resetContext();
	<#if (actionUrl?exists)>
		var tree${model.nodeName} = new WebFXLoadTree('${model.l10n}','${treeModelUrl}',"${actionUrl?if_exists}");
		$("#${model.nodeName}").append(tree${model.nodeName}.toHtml());
	<#else>
		var tree${model.nodeName} = new WebFXLoadTree('${model.l10n}','${treeModelUrl}');
		$("#${model.nodeName}").append(tree${model.nodeName}.toHtml());
	</#if>
	resscrEvt();
</script>
 

	
	
