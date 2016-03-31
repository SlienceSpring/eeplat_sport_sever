<#include "TFormBase.ftl">
	<a  id='${model.objUid}${model.data.uid}' data-role="button"  style="${model.style?if_exists}"  href='#' value="${model.l10n}"><i class="fa fa-folder-o"></i></a>
	<script>
	
	  $('#${model.objUid}${model.data.uid}').bind('click',function(event){
	  
	       var recentParas = "bo_name=${model.data.map.bo_name}&bo_l10n=${model.data.map.bo_l10n}&bo_uid=${model.data.map.bo_uid}&item_name=${model.data.map.item_name}&item_uid=${model.data.map.item_uid}";
	       callService(
	       {
	        'callType' : 'uf',
	        'serviceName' : 'do_recent_item_insert_@browse',
	        'paras' : recentParas
	       }
	       );
		    loadPml({
  				 	'pmlName':'PM_${model.data.map.bo_name}_control_main',
		   			'paras':'dataBus=setContext&contextKey=${model.data.map.bo_name}&contextValue=${model.data.map.item_uid}',
					'target':'_opener_tab'
				 });
			event.stopPropagation(); 
	  });
	</script>
