<#include "TFormBase.ftl">
<div class="weui_cell">
	<div class="weui_cell_bd weui_cell_primary">
	<a style="${model.style?if_exists}"     id='${model.objUid}' class="weui_btn ${model.inputConfig?default('weui_btn_primary')}">${model.l10n}</a>
    </div>
</div>
<script>
$('#${model.objUid}').bind('click',function(){

	$.confirm("${model.echoJs?default('确定要删除吗')}", function(){
        ep.callService({'btn':this,
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
		         })
		       } 
			)
  });
</script>