<#include "TFormBase.ftl">
<button type="button"   id="${model.objUid}"  style="${model.style?if_exists}"  <#compress><@JudgeStyle model/></#compress> >  <#compress><@JudgeBootIcon model/></#compress>    ${model.l10n} </button>
<script>

  if(ep.pagination._conParas){
  	/// 反序列化
  	ep.pagination._conParas.split('&').forEach(function(param){
		  param = param.split('=');
		  var name = param[0];
		  var val = param[1];
		  if(val!=null && val!=""){
		  	$('#a${model.gridModel.objUid} [name=' + name + ']').val(decodeURIComponent(val));
		  }
 	});
  }
  
  $('#${model.objUid}').bind('click',function(){
   
	    var mainPml = ep.getMainPml();
	    
   	    ep.pagination._conParas = $("#${model.targetForms}").serialize();
  	    ep.pagination[mainPml].pageNo = 1;
   		ep.pagination[mainPml].changePml = null;
   		ep.pagination[mainPml].pagiPml = '${model.linkPaneModel.name}';
   		
  
	    loadPml({
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