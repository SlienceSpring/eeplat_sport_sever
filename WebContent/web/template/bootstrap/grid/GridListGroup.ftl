<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/> 
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()/>  
 <div class="box boxextend">
      		<div class="box-header">
      			<#if model.caption?exists>
 				<h3 class="box-title">${model.caption}</h3>
 				</#if>
 			</div>	
	 <form  method='post' id='a${model.objUid}' name ='a${model.objUid}'>
 	
	 <div class="box-body table-responsive">
	 
	  	<#if (model.topOutGridFormLinks?size > 0) > 
		<div  class='mytoolbar'  > 
		<#list model.topOutGridFormLinks as item>
				${item.htmlValue}
		</#list>
		 </div>
		</#if>
	 
		<table id='g${model.objUid}'  class="table table-bordered" >
			<thead>
			  <tr>
				<#--隐藏列，数据部分输出记录的主键-->
				<th  style='display:none' ></th>
				<#if model.NO><#--是否有数字序列-->
					<th   nowrap='nowrap'>${i18n('序号')}</th>
				</#if>
			<#if model.checkBox><#--定义CheckBox-->
				<th  nowrap='nowrap'" width="15px">
					<input type ='checkbox'   name='checkinstanceheader' 
					id="check_${model.objUid}"/>
				</th>
			</#if>
			<#--定义宏 判断输出什么类型的align-->
			<#macro JudgeAlign item>
			    <#if item.align?exists>
			    	align='${item.align}'
			    <#else>
			        align='center' 
			    </#if>
			</#macro>
			<#--输出其它的头标题-->
			<#list model.normalGridFormLinks as item>
	            <th id='${item.colName}'  <#if item.width?exists> width='${item.width}'</#if>  <#if item.noWrapLabel>nowrap='nowrap'</#if> <#compress><@JudgeAlign item/> </#compress>>${item.l10n} </th> 
			</#list>
			</tr>
			</thead>
		<#--Table Header部分输出完毕-->
		<tbody>
	      ${groupTableHtmls} 
		</tbody>

	</table>
	 </div> <!--End box body-->	
									
	</form>	
	</div><!--END BOX-->


<script language="javascript">

		$('#check_${model.objUid}').bind('click',function(){
			var check = $('#check_${model.objUid}').attr("checked");
			if(typeof check == "undefined") {
				check = false;
			}
			$('#g${model.objUid} .list_check').attr('checked',check);
			///同时把第一条记录selected
			if($('#check_${model.objUid}').attr("checked")){
				$('#g${model.objUid} tbody  tr').eq(0).addClass("selected");
			}else{
				$('#g${model.objUid} tbody  tr').eq(0).removeClass("selected");
			}
		});
		
		$('#g${model.objUid} .list_check').bind('click',function(e){
			if(!$(this).attr('checked')){
				$(this).parent().parent().removeClass("selected");
				if($('#g${model.objUid} .selected').size()==0){
					$('#g${model.objUid} .list_check:checked:first').parent().parent().addClass("selected");				
				}
				e.stopPropagation();
			}
		});

		$('#g${model.objUid} tbody  tr').bind('click',function(){
			$('#g${model.objUid} tbody  tr.selected').removeClass("selected");//去掉原来的选中selected
			$(this).addClass("selected");
//			$(this).find(".list_check").attr("checked",true);//点击就选中，容易出现问题
		});

		$('#g${model.objUid} tbody  tr').bind('mouseover',function(){
			$(this).addClass("mover");
		}).bind('mouseout',function(){
			$('#g${model.objUid} tbody  tr').removeClass("mover");
		});
		

</script>
