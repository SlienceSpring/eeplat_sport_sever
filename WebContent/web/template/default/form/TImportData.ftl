<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<button  type="button"  style="${model.style?if_exists}"  id='${model.objUid}' class='ctlBtn btn btn-default'> <#compress><@JudgeBootIcon model/></#compress> ${i18n('导入')}</button>
<script>
$('#${model.objUid}').bind('click',function(){
			var paras = 'tableName=${model.gridModel.category.name}';
	   		loadPml({'pml':'PM_DO_BO_Import_data','target':'_opener_tab','title':'${i18n('导入数据')}','paras':paras})
  }
)
</script>