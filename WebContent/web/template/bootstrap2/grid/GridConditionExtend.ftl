<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/>  
                  <div id="w${model.objUid}" class="box boxextend_top" >
						<div class="box-body ">
						
    						<div class="row">
	    						 <div class="col-xs-7">
	    							${extendControl.htmlValue}
	    						</div>
	    						 <div class="col-xs-5 extendRest" style="text-align:right;max-height:40px">
	    						   <#if extendRests??>
							    	<#list extendRests as item>
										<#if '${dataBind(null,item)}' ==''>${item.htmlValue}</#if>
									</#list>
								   </#if>
	    						 </div>
    						</div>
    						
    						<div class="row extendBody" style="margin-top:5px;display:none">
<!------------------------->
								 <div class="col-xs-12 table-responsive">		
								 <form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
								<table id='g${model.objUid}' class='table  table-bordered table-edit' >

  												<thead/>
												<tbody>
													<#assign i = 0/>
													<#assign colNum = colNum?default(2)/>
													<#if (model.normalGridFormLinks?size > 0)>
													<tr>
													<#list model.normalGridFormLinks as item>
														<#--控制换行-->
													  	<#if (i>0 && (((i == colNum) ) ||item.newLine || (item_index > 0 && model.normalGridFormLinks[item_index-1].newLine) ) )>
														 	<#assign i = 0/>
														 	</tr>
														 	<tr>
														</#if> 	
														
														<#assign htmlValue  = ''/>
														
														<#if dataBind(data,item) ==''> 
															<#assign htmlValue  = item.htmlValue/> 
														</#if>
														<#if (item.nameColspan?exists && item.nameColspan == 0) >
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.normalGridFormLinks?size  || model.normalGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}"</#if> > ${htmlValue} </td>
														<#else>                                                 
														    <th class="tdkey text-nowrap"  style="width:100px"> ${item.l10n}</th> 
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.normalGridFormLinks?size  || model.normalGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> >  ${htmlValue} </td>
														</#if>     
													    <#assign i = i + 1/>    
													</#list>
													</tr>
													</#if>
													
													<tr >
														<td colspan="${(colNum)*2}" style="text-align:center">
													 		<#list model.bottomOutGridFormLinks as item> 
														          <#if '${dataBind(data,item)}' ==''> ${item.htmlValue} </#if> 
															</#list>
														</td>	
													</tr>
			
												</tbody>
											  </table>
								<#if (model.hiddenGridFormLinks?size > 0) > 
								<#list model.hiddenGridFormLinks as item> 
								    ${item.htmlValue}  &nbsp; 
								</#list>
					 			</#if> 
											  
										</form>	  
								</div>			  
<!---->
    						</div>
    						
    						
 						</div>
 				  </div>
 <script>				
 	$("#a${model.objUid}").validate({ignoreTitle: true});  
     if ($(window).width() <= 992) {
        $(".extendRest button:gt(0)").css("margin-top","6px");
        
        
    }
 
 </script>   
