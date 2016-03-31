<#include "TFormBase.ftl">
	<a  id='${model.objUid}${model.data.uid}' data-role="button"  style="${model.style?if_exists}"  href='#' value="${model.l10n}"><i class="fa fa-chevron-right"></i></a>
	<script>
	
	  $('#${model.objUid}${model.data.uid}').bind('click',function(event){
		    loadPml({
  				 	'pmlName':'PM_${model.data.map.bo_name}_control_main',
		   			'paras':'dataBus=setContext&contextKey=${model.data.map.bo_name}&contextValue=${model.data.map.item_uid}',
					'target':'_opener_tab'
				 });
			event.stopPropagation(); 
	  });
	</script>
