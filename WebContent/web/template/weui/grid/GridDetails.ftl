	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
		<div class="weui_cells">
			<#list model.normalGridFormLinks as item>
				<#assign htmlValue  = ''/>				
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
				</#if> 
				<div class="weui_cell">
				  <div class="weui_cell_bd">
				      <p>${item.l10n} </p>
				    </div>
				   <div class="weui_cell_ft   weui_cell_primary" style='${item.style?if_exists}'>
				     ${htmlValue}
				   </div>
				 </div>  
			</#list>

			<#list model.bottomOutGridFormLinks as item>
				<#assign htmlValue  = ''/>				
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
				</#if> 

				 	 ${htmlValue}

			</#list>

		</div>