		<div class="weui_cells weui_cells_checkbox">
		<#list listData as ins>
		  <label class="weui_cell weui_check_label" >
		    <div class="weui_cell_hd">
		      <input type="checkbox" class="weui_check" name="${model.colName}"  ${validStr!!} ${ins.checkedStr} value="${ins.value}">
		      <i class="weui_icon_checked"></i>
		    </div>
		    <div class="weui_cell_bd weui_cell_primary">
		      <p>${ins.label}</p>
		    </div>
		  </label>
		</#list>  
		</div>