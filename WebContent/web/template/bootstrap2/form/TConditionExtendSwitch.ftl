<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
<div style="display:inline">
<button  type="button"  style="${model.style?if_exists}"  id='${model.objUid}' class='ctlBtn btn btn-default'>${i18n('打开综合查询')}</button>
<script>

$('#${model.objUid}').bind('click',function(){

	var  extendBody = $(this).parent().parent().parent().parent().find(".extendBody").toggle();

	if(extendBody.is(":hidden")){
	   $(this).text("打开综合查询");
	}else{
	   $(this).parent().parent().parent().find("select").val('');
	   $(this).text("关闭综合查询");
	}

  }
);
</script>
</div>