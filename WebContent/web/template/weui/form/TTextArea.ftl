<#include "TFormBase.ftl">
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
		<div class="weui_cells weui_cells_form">
		  <div class="weui_cell">
		    <div class="weui_cell_bd weui_cell_primary">
		      <textarea name="${model.colName}" id="${model.fullColID}" ${validStr!!} class="weui_textarea" placeholder="${model.toolTip?default('请输入'+model.l10n)}" rows="3" >${model.value?if_exists}</textarea>
		    </div>
		  </div>
		</div>