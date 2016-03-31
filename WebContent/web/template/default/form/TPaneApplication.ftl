<button  type="button" style="${model.style?if_exists}"   id="${model.objUid}"  class='ctlBtn btn' >&nbsp;${model.l10n}&nbsp;</button>
<script>
  <#if (model.inputConfig?exists && model.inputConfig=="direct")>
	  $('#${model.objUid}').bind('click',function(){
	 		window.open('${appPml}.pml?isApp=true');
	  });
  <#elseif (projects?exists && projects=="true")>
  	  $('#${model.objUid}').bind('click',function(){
  		window.open('projects/');
	  });
  <#else>
	  $('#${model.objUid}').bind('click',function(){
	 		window.open('${appName}/');
	  });
   </#if>	  
</script>