		 <div class="weui_cell weui_cell_switch">
		    <div class="weui_cell_hd weui_cell_primary">${model.l10n}</div>
		    <div class="weui_cell_ft">
		      <input name="${model.colName}" id="${model.fullColID}"  ${validStr!!} class="weui_switch" type="checkbox" value="${model.value?if_exists}">
		    </div>
		  </div>