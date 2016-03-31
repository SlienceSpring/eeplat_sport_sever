			<#--定义dataBinding-->
			<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
			<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
                  <div id="w${model.objUid}" class="box boxextend">
                  
          				<div class="box-header">
                            <h3 class="box-title">${model.caption?if_exists}</h3>
                        </div><!-- /.box-header -->
                  
						<div class="box-body  no-padding">

								<form  method='post' id='a${model.objUid}' name ='a${model.objUid}' class="form-horizontal">
									<#if (model.normalGridFormLinks?size > 0)>
										<#list model.normalGridFormLinks as item>
		
											<#assign htmlValue  = ''/>
											
											<#if '${dataBind(data,item)}' ==''> 
												<#assign htmlValue  = item.htmlValue/>
											
												<#if (item.nameColspan?exists && item.nameColspan == 0) >
													${htmlValue}
												<#else>	
														<div class="form-group">
																<label class="col-sm-3 col-xs-3 control-label no-padding-right" for="${item.fullColID}">${item.l10n}</label>
																
																<div class="col-sm-6 col-xs-8">
																	${htmlValue}
																</div>
														</div>
												</#if>		
	
											</#if>
									
										</#list>
									</#if>
									<!--Hidden-->
									<#if (model.hiddenGridFormLinks?size > 0) > 
												<#list model.hiddenGridFormLinks as item> 
												    <#if '${dataBind(data,item)}' ==''>  ${item.htmlValue} </#if>  &nbsp; 
												</#list>
									</#if>
									
								  <div class="form-group"  style="margin-bottom: 0px;">
								    <div class="col-sm-offset-3 col-sm-9">
										<#--下面是按钮部分-->
										<#if (model.bottomOutGridFormLinks?size > 0) > 
											<#list model.bottomOutGridFormLinks as item>
													${item.htmlValue}
											</#list>
										</#if>
								    </div>
								  </div>
								<#if (model.hiddenGridFormLinks?size > 0) > 
								<#list model.hiddenGridFormLinks as item> 
								    ${item.htmlValue}  &nbsp; 
								</#list>
					 			</#if>
								</form>	

						</div><!--End Box Body-->
			</div>
			<script>
			 	$("#a${model.objUid}").validate({ignoreTitle: true});  
			</script>