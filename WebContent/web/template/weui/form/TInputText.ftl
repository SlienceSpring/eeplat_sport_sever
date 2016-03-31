		  <div class="weui_cell">
		    <div class="weui_cell_hd">${model.l10n}</div>
		    <div class="weui_cell_bd weui_cell_primary">
		      <input name="${model.colName}" ${validStr!!} id="${model.fullColID}" class="weui_input" type="text" placeholder="${model.toolTip?default('请输入'+model.l10n)}" value="${model.value?if_exists}">
		    </div>
		  </div>