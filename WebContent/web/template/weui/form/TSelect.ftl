		<div class="weui_cell weui_cell_select weui_select_after">
			<div class="weui_cell_hd">
                 ${model.l10n}
            </div>
		    <div class="weui_cell_bd weui_cell_primary">
		    <select class="weui_select" name="${model.colName}"  ${validStr!!} id="${model.fullColID}">
		     	<option value=''>请选择</option>
			<#list listData as ins>
				 <option value="${ins.value!!}"  ${ins.checkedStr!!}>${ins.label!!} </option>
			</#list>  
			</select>
			</div>
		</div>