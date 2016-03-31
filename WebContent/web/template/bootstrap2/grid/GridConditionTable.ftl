			<#--定义dataBinding-->
			<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
			<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
							
                            <div id="w${model.objUid}" class="box" style="margin-bottom:0px">
                                 
                                <div class="box-body  no-padding">
											
											<#assign colNum = model.colNum?default(2)/>
											<#assign i = 0/>
											<form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
										
											<table id='g${model.objUid}' class='table table-bordered' >

  												<thead/>
												<tbody>
													<#assign i = 0/>
													<#if (model.abstractGridFormLinks?size > 0)>
													<tr>
													<#list model.abstractGridFormLinks as item>
														<#--控制换行-->
													  	<#if (i>0 && (((i == colNum) ) ||item.newLine || (item_index > 0 && model.abstractGridFormLinks[item_index-1].newLine) ) )>
														 	<#assign i = 0/>
														 	</tr>
														 	<tr>
														</#if> 	
														
														<#assign htmlValue  = ''/>
														
														<#if dataBind(data,item) ==''> 
															<#assign htmlValue  = item.htmlValue/> 
														</#if>
														<#if (item.nameColspan?exists && item.nameColspan == 0) >
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.abstractGridFormLinks?size  || model.abstractGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}"</#if> > ${htmlValue} </td>
														<#else>                                                 
														    <td  <#if item.noWrapLabel>nowrap='nowrap'</#if>> ${item.l10n}</td> 
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if>   <#if (item.newLine || (item_index+1)==model.abstractGridFormLinks?size  || model.abstractGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> >  ${htmlValue} </td>
														</#if>     
													    <#assign i = i + 1/>    
													</#list>
													</tr>
													</#if>
													
													
													<#if (model.moreGridFormLinks?size > 0) >
														<#if (model.abstractGridFormLinks?size > 0)>   
														<tr>
															<td colspan="${colNum*2}" style="cursor:pointer" ><span style="cursor:pointer" onclick="toggleMore(this);"><b>${i18n('More')}</b></span></td>
														</tr>
														</#if>
													<#macro JudgeDisplay >
													    <#if (model.abstractGridFormLinks?size > 0)>
													    	style="display:none"
													    </#if>
													</#macro>
													
													<tr  <#compress><@JudgeDisplay/></#compress> >
													<#assign i = 0/>
													<#list model.moreGridFormLinks as item>
													     <#--控制换行-->
													  	<#if (i>0 && ((i == colNum) ||item.newLine || (item_index > 0 &&  model.moreGridFormLinks[item_index-1].newLine) ) )>
														 	<#assign i = 0/>
														 	</tr>
														 	<tr  <#compress><@JudgeDisplay/></#compress> >
														</#if> 	
														<#assign htmlValue  = ''/>
														
														<#if dataBind(data,item) ==''> 
															<#assign htmlValue  = item.htmlValue/> 
														</#if>
														<#if (item.nameColspan?exists && item.nameColspan == 0) >
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if> <#if ((i == (colNum-1)) || item.newLine || (item_index+1)==model.moreGridFormLinks?size || model.moreGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+2}" <#else>  colspan="${(colNum-i-1)*2}"  </#if>  > ${htmlValue}</td>
														<#else>
														    <td <#if item.noWrapLabel>nowrap='nowrap'</#if>> ${item.l10n}</td>
														    <td  <#if item.noWrapValue>nowrap='nowrap'</#if> <#if (item.newLine || (item_index+1)==model.moreGridFormLinks?size || model.moreGridFormLinks[item_index+1].newLine )> colspan="${(colNum-i-1)*2+1}"</#if> > ${htmlValue} </td>
													    </#if>
													     
														<#assign i = i + 1/>
													</#list>
													</tr>	
											<#--下面是按钮部分-->
	
												</#if> 	
												</tbody>
											  </table>
											<#if (model.hiddenGridFormLinks?size > 0) > 
														<#list model.hiddenGridFormLinks as item> 
														    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
														</#list>
											</#if>
										</form>	
										</div>
										
										<div class="box-footer clearfix center">
                  
												<#--下面是按钮部分-->
												<#if (model.bottomOutGridFormLinks?size > 0) > 

																<#list model.bottomOutGridFormLinks as item>
																			${item.htmlValue}
																</#list>
												</#if>
										</div>
										
									</div><!--end box-->
	
