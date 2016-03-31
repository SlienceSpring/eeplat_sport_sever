<#include "TFormBase.ftl">
<button type="button"   style="${model.style?if_exists}"  id='${model.objUid}' <#compress><@JudgeStyle model/></#compress> > <#compress><@JudgeBootIcon model/></#compress>   ${model.l10n}</button>
<script>
$('#${model.objUid}').bind('click',function(){

		var mainPml = ep.getMainPml();
		ep.myHash.back(mainPml);
  }
);
</script>