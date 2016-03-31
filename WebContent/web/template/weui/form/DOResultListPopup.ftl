<select id="${model.fullColID}" name="${model.colName}"  value="${thevalue?if_exists}" ${validRules!!}>
	<option value="${thevalue?if_exists}">${label?if_exists}</option>	
</select>

<script>
$( "#${model.fullColID}" ).select2({
    placeholder: "请选择...",
    width:"${model.width?default('230px')}",
    minimumInputLength: 0,
    <#if (model.inputConstraint?? && model.inputConstraint=="hiddensearch")>    
    minimumResultsForSearch: Infinity,
    </#if>
    allowClear: true,
    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
        url:  globalURL + "servicecontroller?contextServiceName=${model.linkService.name}&callType=ssext",
        dataType: 'json',
        data: function (params) {
        	var qvalue = '';
        	if(params!=undefined && params.term!=undefined){
        		qvalue = params.term;
        		qvalue = escape(qvalue);
        	}
            var ret = {
                ${(searchColName)?if_exists}: qvalue, // search term
                sc_page_no: params.page || 1,
                sc_page_size : 10
            };
            <#if (model.gridModel.objUid)?? >
               var fields;
               <#if  (model.inputConstraint)?? && (model.inputConstraint=="allform") >
                	fields = $("#a${model.gridModel.objUid}").serializeArray();
               <#else>
					fields = $("#a${model.gridModel.objUid}").find("select").serializeArray();               
               </#if> 
			    $.each( fields, function(i, field){
			      if(field.value!=''){
				  	ret[field.name] = escape(field.value);
				  }
				});
            </#if> 
            return ret;
        },
        processResults: function (data, params) { // parse the results into the format expected by Select2.
            params.page = params.page || 1;
            return {
            	results: data.items,
            	pagination: {
		          more: (params.page * 10) < data.sc_result_size
		        }
            };
        }
    },
    escapeMarkup: function (markup) { return markup; } // let our custom formatter work
}).on("select2:select",
 function (e) {
  	 
  	<#if (model.onChangeJs)?? >
  		eval("${model.onChangeJs}");
  	<#else>
    	$.each(e.params.data,
	 	 	function(key,value){
	 	 		$("#a${model.gridModel.objUid} :input[name='" + key + "']").val(value)
	 	 	}
	 	 )
  	</#if>
  	
  	<#if ( !((model.inputConfig)?? && model.inputConfig=='keepNext')  ) >
  	 	$( "#${model.fullColID}" ).nextAll("select").val("").trigger("change");;
  	</#if> 	
  	 
 });

</script>
