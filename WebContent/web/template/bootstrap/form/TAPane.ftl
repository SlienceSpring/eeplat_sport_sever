<#include "TFormBase.ftl">

<#if (model.data?exists)>

	<a  id='${model.objUid}${model.data.uid}' data-role="button"  style="${model.style?if_exists}"   value="${model.l10n}">
	 <#compress><@JudgeBootIcon2 model/></#compress>
	</a>
	<#if (model.linkPaneModel?exists) >
	<script>
	
	  $('#${model.objUid}${model.data.uid}').bind('click',function(event){
	  
	        var mainPml = ep.getMainPml();
	        if(mainPml!=null &&  ep.endWith(mainPml,'_control_main') ){
	        	if(ep.pagination[mainPml]==null){
        			ep.pagination[mainPml] = {};
        		}
	        	ep.pagination[mainPml].invokePml = '${(model.gridModel.containerPane.name)?if_exists}';
	        }
	  
		    loadPml({
  			 <#if (model.linkPaneModel?exists && model.linkPaneModel.linkType==5)>
	   			 	'resourcePath':'${model.linkPaneModel.resource.resourcePath}',
	   			 </#if>
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
	<a  id='${model.objUid}'  style="${model.style?if_exists}"   value="${model.l10n}">${model.l10n}</a>
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