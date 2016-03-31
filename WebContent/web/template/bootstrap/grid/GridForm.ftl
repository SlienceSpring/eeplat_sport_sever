	<#--定义dataBinding-->
	<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
	<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 

    <div class="box boxextend">
 		<div class="box-header" style="padding-left:15px">
 			<h3>
	 			  <#if  data??>
		 			<#if (data.bo.icon)??><i class='${data.bo.icon}'></i> </#if>  ${data.name} 
	 			  <#else>
	 			  	 ${model.caption?if_exists}
	 			  </#if>
 			 </h3>
		</div>
	
     <div class="box-body">
     
     
   <form  method='post' id='a${model.objUid}' name ='a${model.objUid}' class='form-horizontal'>
     
     <div class="panel-group"  role="tablist" aria-multiselectable="true">
     
     
      <#list mapForms?keys as aKey>
  	<div class="panel panel-default">
  	
  	 <div class="panel-heading" role="tab" >
        <a data-toggle="collapse" href="#${aKey.objUid}" aria-expanded="true" >
          ${aKey.htmlValue}
        </a>
    </div>
    <div id="${aKey.objUid}" class="panel-collapse collapse in" role="tabpanel">
      <div class="panel-body">
			
		<#assign  theItems = mapForms?api.get(aKey) />
		<#assign i = 0/>
     	<#assign colNum = model.colNum?default(2)/>

			<#list theItems as item>
				
				
			  <#if ( ((item_index % 2) == 0)  || item.newLine || (item_index>0 && theItems[item_index-1].newLine)) >	
				  <div class="form-group">
			  </#if><!--逢双数使用formgroup-->	
				<#assign htmlValue  = ''/>
				
				<#if dataBind(data,item) ==''> 
					<#assign htmlValue  = item.htmlValue/> 
				</#if>
				<#if (item.nameColspan?exists && item.nameColspan == 0) >
				  <div class='col-xs-8'>   ${htmlValue}</div>
				<#else>                                                 
				    <label class='control-label col-xs-4  col-md-2' for="${item.fullColID}">${item.l10n}  </label>
				    <div class='col-xs-8 col-md-4 form-inline'>
				      ${htmlValue} 
				    </div>
				</#if>  
				
			  <#if ( ( (item_index % 2) == 1)  ||   (item_index== (theItems?size-1)  || item.newLine  || (item_index>0 && theItems[item_index-1].newLine) )   )>	
				 </div>	
  			  </#if><!end --逢双数使用formgroup-->	
			</#list>
		</div></div></div>	
		</#list>
					
	  	</div>
	  	
	  	
	  				<#--下面是按钮部分-->
		<#if  (model.bottomOutGridFormLinks?size > 0)>
				<div class="form-group">
				<div class="col-xs-offset-4 " > 
				<#list model.bottomOutGridFormLinks as item> 
			          <#if '${dataBind(data,item)}' ==''> ${item.htmlValue} </#if> &nbsp; 
				</#list>
				</div>
				</div>
			</#if> 	
			<#if (model.hiddenGridFormLinks?size > 0) > 
						<#list model.hiddenGridFormLinks as item> 
						    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
						</#list>
			</#if>
	
  		</form>	
	  	
		</div><!--End box-body-->
	
 	</div><!--End Box-->
	
<script>
//validate
$("#a${model.objUid}").validate({ignoreTitle: true,highlight: function(label) {
		    	$(label).closest('.control-group').addClass('error');
		    }});

</script>
 

