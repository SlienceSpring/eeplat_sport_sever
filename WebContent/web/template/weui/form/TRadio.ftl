			<div class="weui_cells weui_cells_radio">
			<#list listData as ins>
			  <label class="weui_cell weui_check_label" >
			  	<div class="weui_cell_bd weui_cell_primary">
			      <p>${ins.label}</p>
			    </div>
			    <div class="weui_cell_ft">
			      <input type="radio" class="weui_check" name="${model.colName}" ${validStr!!} id="${model.fullColID}"  ${ins.checkedStr} value="${ins.value}">
			      <i class="weui_icon_checked"></i>
			    </div>
			  </label>
			</#list>  
			</div>