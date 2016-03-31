<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<div style="display:inline">
<button  type="button"  style="${model.style?if_exists}"  id='conditonExtendSwitch' class='ctlBtn btn btn-default'>${i18n('打开综合查询')}</button>
<script>

$('#conditonExtendSwitch').bind('click',function(){

	ep.conditonStatus();
	var  extendBody = $("#_opener_tab").find(".extendBody").toggle();

	if(extendBody.is(":hidden")){
	   $(this).text("打开综合查询");
	   ep.pagination._conShow = false;
	   ep.pagination._conParas = null;
	}else{
	   ep.pagination._conShow = true;
	   $(this).parent().parent().parent().find("select").val('');
	   $(this).text("关闭综合查询");
	}


  }
);
</script>
</div>