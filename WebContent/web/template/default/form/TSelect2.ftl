<input id="${model.fullColID}" type="hidden" name="${model.colName}"  value="${model.value?if_exists}" />
<script>

$( "#${model.fullColID}" ).select2({
    placeholder: "请选择...",
    width:"300px",
    multiple:true,
    //minimumInputLength: 1,
    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
        url:  globalURL + "servicecontroller?contextServiceName=${model.linkService.name}&callType=ss",
        dataType: 'json',
        data: function (term, page) {
            return {
                ${(model.linkService.bo.valueCol)?if_exists}: term, // search term
                page_limit: 10
            };
        },
        results: function (data, page) { // parse the results into the format expected by Select2.
            // since we are using custom formatting functions we do not need to alter remote JSON data
            console.log(O2String(data));
            return {results: data.items};
        }
    }
});


<#if updateValues??>
  $( "#${model.fullColID}" ).select2("data",${updateValues});
 </#if>  


</script>
