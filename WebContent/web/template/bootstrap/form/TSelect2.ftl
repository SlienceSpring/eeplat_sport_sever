<div class="input-group">
<#if model.note ??>
<span class="input-group-addon" >${model.note}</span>
</#if>
<select id="${model.fullColID}" name="${model.colName}" multiple="multiple" >
  <#if updateValues??>
  	${updateValues}
  </#if>
</select>
</div>

<script>
$( "#${model.fullColID}" ).select2({
    placeholder: "请选择...",
    width:"300px",
    multiple:true,
    tags: true,
    minimumInputLength: 0,
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
            return {
                ${(model.linkService.bo.valueCol)?if_exists}: qvalue, // search term
                sc_page_no: params.page || 1,
                sc_page_size : 10
            };
        },
        processResults: function (data, params) { // parse the results into the format expected by Select2.
            // since we are using custom formatting functions we do not need to alter remote JSON data
            params.page = params.page || 1;
            return {
            	results: data.items,
            	pagination: {
		          more: (params.page * 10) < data.sc_result_size
		        }
            };
        }
    },
    escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
});
</script>
