<#include "TFormBase.ftl">
<button type="button"   id="${model.objUid}"  style="${model.style?if_exists}"  <#compress><@JudgeStyle model/></#compress> >  <#compress><@JudgeBootIcon model/></#compress>    ${model.l10n} </button>
<script>

  var dealBus = "parentPaneName=${model.gridModel.containerPane.name}";
  $('#${model.objUid}').bind('click',function(){
	    loadPml({
				 'paras': dealBus,
	   			 <#if (model.linkPaneModel?exists &&  model.linkPaneModel.linkType?exists && model.linkPaneModel.linkType==5)>
	   			 	'resourcePath':'${model.linkPaneModel.resource.resourcePath}',
	   			 </#if>
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
				 }
		);
  });
</script>