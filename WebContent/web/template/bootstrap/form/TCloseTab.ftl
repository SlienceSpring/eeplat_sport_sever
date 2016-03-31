<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<button type="button"  class='btn btn-default' style="${model.style?if_exists}"  id='${model.objUid}' <#compress><@JudgeStyle model/></#compress>   <#compress><@JudgeBootIcon model/></#compress>  
 <i class="fa fa-arrow-left"></i> ${i18n('BACK')} </button>
 
<script>

$('#${model.objUid}').bind('click',function(){
	<#if (model.gridModel.containerPane.name)?exists>
	
		var mainPmlName = ep.myHash.mainPmlName; //$("body").data('mainPmlName');
		var linkPmlName = '';
		var targetPmlName = '';
		
		console.log("mainPmlName:::" + mainPmlName);
		
		
		<#if (model.linkPaneModel?exists) >
			linkPmlName =  '${model.linkPaneModel.name}';
		</#if>
		
		<#if (model.targetPaneModel?exists) >
			targetPmlName =  '${model.targetPaneModel.name}';
		</#if>

		
		try{
		   if(linkPmlName!=mainPmlName && targetPmlName!=''){
				loadPml(
				{
					'pmlName': targetPmlName,
					'target':'${model.gridModel.containerPane.name}'
				}
				);		   
		   }else{
		   		reloadMenuPml();
		   }
	  	}catch(e){
	  	}

  	</#if>
  }
);
</script>