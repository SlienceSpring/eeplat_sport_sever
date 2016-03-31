	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
 	<form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
 <#list mapForms?keys as aKey>
${aKey.htmlValue}
	<#assign  theItems = mapForms?api.get(aKey) />
	<#if aKey.category>	
	<div class="weui_cells weui_cells_form">
	</#if>
	<#list theItems as item>
			  <#assign htmlValue  = ''/>
			  <#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
			  </#if>		
${htmlValue}
	</#list>
	<#if aKey.category>	
	</div>
	</#if>
</#list>	
		<#if (model.hiddenGridFormLinks?size > 0) > 
					<#list model.hiddenGridFormLinks as item> 
${item.htmlValue}  &nbsp; 
					</#list>
		</#if>
	</form>	
<script>
$("#a${model.objUid}").validate({ignoreTitle: true,highlight: function(label) {
				$(label).closest(".weui_cell").addClass('weui_cell_warn');
				$(label).closest(".weui_cells_checkbox").prev().addClass('weui_cell_warn_title');
				$(label).closest(".weui_cells_radio").prev().addClass('weui_cell_warn_title');

		    },
		    success: function(label) {
			    $(label).closest(".weui_cell").removeClass('weui_cell_warn');
			    $(label).closest(".weui_cell_warn_title").removeClass('weui_cell_warn_title');
		    },
            errorPlacement:function(error,element){
		            if ( element.is(":radio") ){
							error.appendTo( element.closest(".weui_cells").prev() );
					}
					else if ( element.is(":checkbox") ){
							error.appendTo( element.closest(".weui_cells").prev() );
					}
					else{
							error.appendTo( element.parent() );
					}
	            }
		    }
		    )
</script>