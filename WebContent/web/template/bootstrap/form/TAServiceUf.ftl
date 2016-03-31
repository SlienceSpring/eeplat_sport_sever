<#include "TFormBase.ftl">
<#--参数通过本条记录的主键传递，如果是弹出窗口，并不关闭-->
<a  id='${model.objUid}${model.data.uid}' data-role="button"  style="${model.style?if_exists}"  value="${model.l10n}">
  	 <#compress><@JudgeBootIcon2 model/></#compress>
</a>
<script>

$('#${model.objUid}${model.data.uid}').bind('click',function(event){
        callService({'btn':this,
          		 <#if (model.note)?exists>
                 'msg':'${model.note}',
                 </#if>
        		 'callType':'uf',
		         'serviceUid':'${model.linkService.objUid}',
		         'formName':'${model.targetForms}',
		         'paras':'dataBus=setContext&contextKey=${(model.data.bo.name)!}&contextValue=${model.data.uid}&invokeButtonUid=${model.objUid}&contextNIUid=${(model.data.map.contextniuid)?if_exists}&contextPIUid=${model.data.map.contextpiuid?if_exists}'
		         <#if (model.linkPaneModel)?exists>
		         ,'pmlName':'${model.linkPaneModel.name}'
		         ,'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}'
	   			 ,'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}'
	   			 <#elseif parentPaneName?exists>
		         ,'pmlName':'${parentPaneName}'
		         <#elseif (model.gridModel.containerPane)?exists>
		         ,'pmlName':'${(model.gridModel.containerPane.name)!}'
		         </#if>
		         <#if (model.gridModel.containerPane.hiddenPane.gridModel)?exists>
			         ,'conFormName':'a${model.gridModel.containerPane.hiddenPane.gridModel.objUid}'
			     </#if>
		         <#if (model.targetPaneModel)?exists>	         
		         ,'target':'${model.targetPaneModel.name}'
		         <#elseif parentPaneName?exists>
		         ,'target':'${parentPaneName}'
		         <#elseif (model.gridModel.containerPane)?exists>
		          ,'target':'${model.gridModel.containerPane.name}'	
		         </#if>
		          <#if (model.echoJs)?exists>	         
		         ,'echoJs':'${model.echoJs}'
		         </#if>
		         });
		        event.stopPropagation(); 
});
</script>