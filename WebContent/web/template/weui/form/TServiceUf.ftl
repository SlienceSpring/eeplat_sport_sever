<#include "TFormBase.ftl">
<div class="weui_cell">
	<div class="weui_cell_bd weui_cell_primary">
	<a style="${model.style?if_exists}"     id='${model.objUid}' class="weui_btn ${model.inputConfig?default('weui_btn_primary')}">${model.l10n}</a>
    </div>
</div>
<script>
$('#${model.objUid}').bind('click',function(){
        ep.callService({'btn':this,
                 <#if (model.note)?exists>
                 'msg':'${model.note}',
                 </#if>
        		 'callType':'uf',
        		 'serviceUid':'${model.linkService.objUid}',
        		 'paras':'invokeButtonUid=${model.objUid}',
		         'formName':'${model.targetForms}'
		         <#if (model.linkPaneModel)?exists>
		         ,'pmlName':'${model.linkPaneModel.name}'
		         	<#if (model.linkPaneModel.title)?exists>
		         		,'title':'${model.linkPaneModel.title}'
		         	 </#if>
		         ,'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}'
	   			 ,'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}'
	   			 <#elseif parentPaneName?exists>
		         ,'pmlName':'${parentPaneName}'
		         <#else>
		         ,'pmlName':'${model.gridModel.containerPane.name}'
		         </#if>
		         <#if (model.gridModel.containerPane.hiddenPane.gridModel)?exists>
			         ,'conFormName':'a${model.gridModel.containerPane.hiddenPane.gridModel.objUid}'
			     </#if> 
		         
		         <#if (model.targetPaneModel)?exists>	         
		         ,'target':'${model.targetPaneModel.name}'
		         <#elseif parentPaneName?exists>
		         ,'target':'${parentPaneName}'
		         <#else>
		          ,'target':'${model.gridModel.containerPane.name}'	
		         </#if>
		         <#if (model.echoJs)?exists>	         
		         ,'echoJs':'${model.echoJs}'
		         </#if>
		         });
  }
);
</script>