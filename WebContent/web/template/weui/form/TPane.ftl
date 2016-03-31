<#include "TFormBase.ftl">
<div class="weui_cell">
	<div class="weui_cell_bd weui_cell_primary">
	<a style="${model.style?if_exists}"     id='${model.objUid}' class="weui_btn weui_btn_primary">${model.l10n}</a>
	</div>
</div>	
<script>
$('#${model.objUid}').bind('click',function(){
        ep.loadPml({
	   			 'pmlName':'${model.linkPaneModel.name}',
	   			 'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}',
	   			 'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}',
		   		 'title':'${model.linkPaneModel.title}',
		         'formName':'${model.targetForms}'
		   		  <#if (model.targetPaneModel)?exists>	         
				,'target':'${model.targetPaneModel.name}'
				 </#if> 
				 <#if (model.echoJs)?exists>	         
		         ,'echoJs':'${model.echoJs}'
		         </#if>
		         });
  }
);
</script>