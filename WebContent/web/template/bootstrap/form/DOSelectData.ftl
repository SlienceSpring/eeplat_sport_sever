<#include "TFormBase.ftl">
<#--参数通过formName传递-->
<button  type="button"  class="btn btn-primary" id='${model.objUid}' style="${model.style?if_exists}"   <#compress><@JudgeStyle model/></#compress> >${model.l10n}</button>
<script>
$('#${model.objUid}').bind('click',function(){
     
     var selects = $('#g${model.gridModel.objUid} tbody  tr.selected')
     if(selects.length==0){
     	alert("你还没有选择数据");
     	return ;
     }
     
    
     if(invokeDomRef){
     		invokeDomRef.value = selects.eq(0).attr("title");
			if(invokeDomRef.previousSibling!=null){
				invokeDomRef.previousSibling.value= selects.eq(0).attr("value");
			}
     }else if($('#' + invokeDomId).length > 0){
     
	     $('#' + invokeDomId).val(selects.eq(0).attr("value"));
	     $('#' + invokeDomId +"_show").val(selects.eq(0).attr("title"));
	     var jsonstr = selects.eq(0).attr("json");
	 	 console.log("jsonstr::" + jsonstr);
	 	 var insJson = $.parseJSON(jsonstr);
	 	 var curFormId = $('#' + invokeDomId).parents("form").attr("id");
	 	 
	 	 $.each(insJson,
	 	 	function(key,value){
	 	 		$("#" + curFormId + " :input[name='" + key + "']").val(value)
	 	 	}
	 	 )
	 	 
	 	 
	 	     
     }else if(window.opener){
	     window.opener.$('#' +window.opener. invokeDomId).val(selects.eq(0).attr("value"));
	     window.opener.$('#' + window.opener.invokeDomId +"_show").val(selects.eq(0).attr("title"));
     	
     }
     
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