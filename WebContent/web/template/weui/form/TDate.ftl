<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
		  <div class="weui_cell">
		    <div class="weui_cell_hd">${model.l10n}</div>
		    <div class="weui_cell_bd weui_cell_primary">
		      <input name="${model.colName}" id="${model.fullColID}" ${validStr!!} class="weui_input" type="date" placeholder="${model.toolTip?if_exists}" value="${model.value?if_exists}">
		    </div>
		  </div>