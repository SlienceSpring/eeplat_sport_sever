<#include "TFormBase.ftl">
<#--参数通过formName传递-->
<button  type="button"  style="${model.style?if_exists}"  id='${model.objUid}'  <#compress><@JudgeStyle model/></#compress> >     <#compress><@JudgeBootIcon model/></#compress>  ${model.l10n}</button>
<script>

 function fnCB${model.objUid}(){
  	<#if (!((model.inputConstraint)?exists && model.inputConstraint=='noCloseOpener'))>
	    <#if (model.gridModel.containerPane.name)?exists>
		try{
			if($('#F' + '${model.gridModel.containerPane.name}').size()>0){
	  			$('#F' + '${model.gridModel.containerPane.name}').dialog('close');
	  		}else{
	  			$('#' + '${model.gridModel.containerPane.name}').parents(".ui-dialog-content").dialog('close');
	  			<#if (model.gridModel.containerPane.parent)?exists>	
	  				$('#F' + '${model.gridModel.containerPane.parent.name}').dialog('close');
	  			</#if>
		  	}  	
	  	}catch(e){
	  	}
	  	
	  		  	///bootstrap model
		try{
			if($('#F' + '${model.gridModel.containerPane.name}').size()>0){
	  			$('#F' + '${model.gridModel.containerPane.name}').modal('hide');
	  		}else{
	  			$('#' + '${model.gridModel.containerPane.name}').parents(".ui-dialog-content").dialog('close');
	  			<#if (model.gridModel.containerPane.parent)?exists>	
	  				$('#F' + '${model.gridModel.containerPane.parent.name}').modal('hide');
	  			</#if>
		  	}  	
	  	}catch(e){
	  	}		
	  	
	  	</#if>
	 </#if>
	 
		<#if ((model.inputConfig)?exists && model.inputConfig=='closeTab')>
				var tabBtnSelector = "#dvTab  .on .btnTab";
		  		tabCloseWindow(tabBtnSelector);
		 </#if>	
	  
  	 <#if ((model.inputConfig)?exists && model.inputConfig=='loadParent')>
  		reloadTree();     
  	</#if>
   
 }

$('#${model.objUid}').bind('click',function(){
        callService({'btn':this,
		         'serviceUid':'${model.linkService.objUid}',
		         'callback':fnCB${model.objUid},
		         'paras':'invokeButtonUid=${model.objUid}',
		         'formName':'${model.targetForms}'
		         <#if (model.linkPaneModel)?exists>
		         ,'pml':'${model.linkPaneModel.name}'
		         ,'pmlWidth':'${model.linkPaneModel.paneWidth?if_exists}'
	   			 ,'pmlHeight':'${model.linkPaneModel.paneHeight?if_exists}'
	   			  <#elseif parentPaneName?exists>
		         ,'pml':'${parentPaneName}'
		         <#else>
		         ,'pml':'${model.gridModel.containerPane.name}'
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
		         </#if> })
		         
  }
);
  	<#if (!((model.inputConstraint)?exists && model.inputConstraint=='noEnter'))>
	  $('#a${model.gridModel.objUid}').keydown(function(e){
			if(e.keyCode==13){
				console.log("Service=====================");			
			   $('#${model.objUid}').click();
			    e.stopPropagation();
			}
	  });
	</#if>  
</script>