<#include "TFormBase.ftl">
<#--参数通过formName传递-->
<button  type="button" class="btn btn-primary"  id='${model.objUid}' style="${model.style?if_exists}"   <#compress><@JudgeStyle model/></#compress> >${model.l10n}</button>
<script>

////可以json 存储，然后根据name mapping

$('#${model.objUid}').bind('click',function(){
     
     var selects = $("#g${model.gridModel.objUid} tbody  tr input:checked");
     if(selects.length==0){
     	alert("你还没有选择数据");
     	return ;
     }
     var values = '';
     var titles = '';
     for(var i = 0; i < selects.length; i++){
        if(i > 0){
        	values = values + ',' + selects.eq(i).attr("value");
	        titles = titles + ',' +selects.eq(i).attr("title");
        }else{
	        values = values + selects.eq(i).attr("value");
	        titles = titles + selects.eq(i).attr("title");
	    }
     }
     $('#' + invokeDomId).val(values);
     $('#' + invokeDomId +"_show").val(titles);

	 <#if (model.doClickJs?exists)>	  
      	eval("${model.doClickJs}");
     </#if>     
     ///关闭弹出页面
     <#if (model.gridModel.containerPane.name)?exists>
		try{

	  		$('#' + '${model.gridModel.containerPane.name}').parents(".modal").modal('hide');
		  	  	
	  	}catch(e){
	  	}		
	 </#if>
  }
);
</script>