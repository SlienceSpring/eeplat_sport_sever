<#include "TFormBase.ftl">
<#if (model.data?exists)>
	<a  id='${model.objUid}${model.data.uid}' data-role="button"  style="${model.style?if_exists}"  href='#' value="${model.l10n}">${model.value}</a>
	<#if (model.linkPaneModel?exists) >
	<script>
	
	  $('#${model.objUid}${model.data.uid}').bind('click',function(event){
	  
	       var recentParas = "bo_name=${model.data.bo.name}&bo_l10n=${model.data.bo.l10n}&bo_uid=${model.data.bo.objUid}&item_name=${model.data.name}&item_uid=${model.data.uid}";
	       callService(
	       {
	        'callType' : 'uf',
	        'serviceName' : 'do_recent_item_insert_@browse',
	        'paras' : recentParas
	       }
	       );
	       
		    loadPml({
  				 	'pmlName':'${model.linkPaneModel.name}',
		   		 	'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}',
		   		 	'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}',
		   			 'paras':'dataBus=setContext&contextKey=${model.data.bo.name}&contextValue=${model.data.uid}&contextNIUid=${(model.data.map.contextniuid)?if_exists}&contextPIUid=${model.data.map.contextpiuid?if_exists}',
			   		 'title':'${model.linkPaneModel.title}',
		  	       'formName':'${model.targetForms}'
			   		  <#if (model.targetPaneModel)?exists>	         
					,'target':'${model.targetPaneModel.name}'
					 </#if> });
			event.stopPropagation(); 
	  });
	</script>
	 </#if>
<#else>
	<a  id='${model.objUid}'  style="${model.style?if_exists}"  href='#' value="${model.l10n}">${model.l10n}</a>
	<#if (model.linkPaneModel?exists) >
	<script>
	
	  $('#${model.objUid}').bind('click',function(event){
		    loadPml({
						'paras':'parentPaneName=${model.gridModel.containerPane.name}',
		   			 	'pmlName':'${model.linkPaneModel.name}',
		   			 	'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}',
		   			 	'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}',

			   		 'title':'${model.linkPaneModel.title}',
			   		 'formName':'a${model.gridModel.objUid}'
			   		  <#if (model.targetPaneModel)?exists>	         
					,'target':'${model.targetPaneModel.name}'
					 </#if> }
			);
			event.stopPropagation(); 
	  });

	</script>
	 </#if>
</#if>