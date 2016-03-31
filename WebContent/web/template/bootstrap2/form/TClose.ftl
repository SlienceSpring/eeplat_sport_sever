<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<button type="button" class='btn btn-default'  data-dismiss='modal'  style="${model.style?if_exists}"  id='${model.objUid}' <#compress><@JudgeStyle model/></#compress> >    <#compress><@JudgeBootIcon model/></#compress >
${i18n('Close')}</button>
<script>
$('#${model.objUid}').bind('click',function(){
	<#if (model.gridModel.containerPane.name)?exists>
		 <#if (model.linkPaneModel)?exists>
			loadPml({
				   		 'pml':'${model.linkPaneModel.name}',
				   		 'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}',
	   			 		 'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}',
				   		 'title':'${model.linkPaneModel.title}'
				   		  <#if (model.linkPaneModel.hiddenPane)?exists>
				   		 ,'formName':'a${model.linkPaneModel.hiddenPane.gridModel.objUid}'
				   		 </#if>
				   		  <#if (model.targetPaneModel)?exists>	         
						,'target':'${model.targetPaneModel.name}'
						 </#if> }
		    );
		    
		  </#if>  

  	</#if>
  	
  }
);
</script>