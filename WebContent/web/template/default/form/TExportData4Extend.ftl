<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<button  type="button"  style="${model.style?if_exists}"  id='${model.objUid}' class='ctlBtn btn btn-default'> <#compress><@JudgeBootIcon model/></#compress> ${i18n('导出')}</button>
<script>
$('#${model.objUid}').bind('click',function(){
	var  selectedPml = $(this).parent().parent().parent().find("select").val();
	console.log("selectedPml::" + selectedPml);
	console.log("excelurl:${excelurl}");
	
	window.open('${excelurl}' + "&pmlName=" + selectedPml,'_opener');     
	}
)
</script>
