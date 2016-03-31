			<#--定义dataBinding-->
			<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
			<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
                         <div id="w${model.objUid}" class="panel panel-default gdjs" style="margin-bottom:0px">
								<form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
                                <div class="panel-heading">
                                
                                	<#assign colNum = model.colNum?default(2)/>
									<#assign i = 0/>
									<#assign divColXs = 'col-xs-6'/>
									<#if (colNum==1)>
										<#assign divColXs = 'col-xs-12'/>
									<#elseif (colNum==3)>
										<#assign divColXs = 'col-xs-4'/>
									<#elseif (colNum==4)>
										<#assign divColXs = 'col-xs-3'/>
									</#if>
									<div class="row gdjs-form-search">		
									<#assign i = 0/>
									<#list model.normalGridFormLinks as item>
												  	<#if (i>0 && ((i % colNum)==0) )>
														 	</div>
														 	<div class="row gdjs-form-search">	
													</#if> 	
														
														<#assign htmlValue  = ''/>
														
														<#if dataBind(data,item) ==''> 
															<#assign htmlValue  = item.htmlValue/> 
														</#if>
														
														<#if (item.nameColspan?exists && item.nameColspan == 0) >
														   <div class="${divColXs}">  > ${htmlValue} </div>
														<#else>                        
														   <div class="${divColXs}">                          
														    <label> ${item.l10n}</label> 
														     ${htmlValue}
														   </div>   
														</#if>     
													    <#assign i = i + 1/>    
									</#list>
										
											<#if (model.hiddenGridFormLinks?size > 0) > 
														<#list model.hiddenGridFormLinks as item> 
														    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
														</#list>
											</#if>
										</div><!--end gdjs-form-search-->	
										
										<#if (model.bottomOutGridFormLinks?size > 0) > 
										<ul class="gdjs-search list-unstyled clearfix">
											<#--下面是按钮部分-->

																<#list model.bottomOutGridFormLinks as item>
																			${item.htmlValue}
																</#list>
										</ul>
										</#if>
									</div><!--end panel-heading-->
									</form>
								</div><!--end gdjs-->
	