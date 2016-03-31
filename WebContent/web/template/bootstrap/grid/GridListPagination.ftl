<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/> 
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>  

  <div class="box boxextend">
  <!--
      		<div class="box-header">
      			<#if model.caption?exists>
 				<h3 class="box-title">${model.caption}</h3>
 				</#if>
 			</div>
 	-->		 	
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

			<#if model.checkBox><#--定义CheckBox-->
				<th  nowrap='nowrap'" width="15px">
					<input type ='checkbox'   name='checkinstanceheader' 
					id="check_${model.objUid}"/>
				</th>
			</#if>
			<#if model.NO><#--是否有数字序列-->
					<th   nowrap='nowrap' width="50px">${i18n('序号')}</th>
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
		   <#list data as ins>
				<tr  value='${ins.uid?if_exists}'  title='${ins.name?if_exists}'>

				<#if model.checkBox><#--定义CheckBox-->
					<th><input type ='checkbox'  title='${ins.name?if_exists}' value='${ins.uid}' class='list_check'  name='checkinstance'/> <input type ='hidden' value='${ins.uid}' name='checkinstance_hidden'/> </th>
				<#elseif model.radio>
					<th align='center'><input type ='radio' title='${ins.name?if_exists}' value='${ins.uid}'  name='checkinstance'/>   <input type ='hidden' value='${ins.uid}' name='checkinstance_hidden'/>  </th>
				</#if>
				
				<#if model.NO><#--是否有数字序列-->
					<td align='center'>#{ins_index+1}</td>
				</#if>
				
				<#list model.normalGridFormLinks as item> 
			            <td  	<#if item.pageStatistics > class="${item.objUid}"</#if>  <#if item.noWrapValue>nowrap='nowrap'</#if> <#compress>  <@JudgeAlign item/></#compress>  style="${item.style?if_exists}" > <#if '${dataBind(ins,item)}' ==''> ${item.htmlValue} </#if> </td> 
				</#list>
				</tr>
		     </#list>
		     
		    <#--本页统计--> 
		    <#if (model.statisticPageOutGridFormLinks?size > 0) >
			    <tr>
					<#if model.NO><#--是否有数字序列-->
						<td align='center'>&nbsp;</td>
					</#if>
					<#if model.checkBox><#--定义CheckBox-->
						<td style="align: center" >&nbsp; </td>
					<#elseif model.radio>
						<td align='center'>&nbsp;</td>
					</#if>
					
					<#list model.normalGridFormLinks as item>
						
							<#if item.pageStatistics >
					            <td  <#if item.noWrapValue>nowrap='nowrap'</#if> <#compress>  <@JudgeAlign item/></#compress>  style="${item.style?if_exists}" >
					               <span style="font-weight:bold" id = "${item.objUid}"> </span>
					               <script>
					                   var total = 0;
					                   $(".${item.objUid}").each(function(i,o){
					                   	     if($(o).text()!=null){
						                   	    try{
						                   	      total = total + parseFloat($(o).text());
						                   	     }catch(e){}
					                   	   }
					                   
					                   });
					                   $("#${item.objUid}").html(toDecimal2(total));
					               </script>
					             </td>
					        <#else>
					        	<td align='center'>&nbsp;</td>			        
					        </#if>      
					</#list>
					</tr>
		    </#if>
		     <#if statistics?exists>
		      	<tr>
			      	<#if model.NO><#--是否有数字序列-->
						<td align='center'>统计</td>
					</#if>
					<td colspan="${model.normalGridFormLinks?size + 1}"> ${statistics_details?if_exists}   </td>
		        </tr>
		     </#if>
			</tbody>

	
		</table>

		<#if (model.hiddenGridFormLinks?size > 0) > 
					<#list model.hiddenGridFormLinks as item> 
					    ${item.htmlValue}  &nbsp; 
					</#list>
		</#if>


	 </div> <!--End box body-->	
	 
	 <div class="gridmainfooter">

		   		<#if (model.bottomOutGridFormLinks?size > 0) > 
				<div style="float:left;"  > 
				<#list model.bottomOutGridFormLinks as item>
						${item.htmlValue}
				</#list>
				 </div>
				</#if>

		   		<#if (model.rowSize?exists && model.rowSize > 0) >
		   		<div style="float:right;margin-top:7px"  class="cntgridfooter" >
	    		共<span style="color:blue">${resultSize}</span>条记录，<span style="color:blue">${pageSize}</span>页。
	    		</div> 				
		   		
		     	<div id="Pagination${model.objUid}" style='float:right'></div>
		     	</#if>
		     	 
	 </div>
		</form>	
	 
	</div><!--END BOX-->


<script language="javascript">


////selectall checkbox click
		$('#check_${model.objUid}').bind('click',function(){
		    
			var check = $('#check_${model.objUid}').prop("checked");
			if(typeof check == "undefined") {
				check = false;
			}
			$('#g${model.objUid} tbody .list_check').prop('checked',check);
			///同时把第一条记录selected
			if($('#check_${model.objUid}').attr("checked")){
				$('#g${model.objUid} tbody  tr').eq(0).addClass("selected");
			}else{
				$('#g${model.objUid} tbody  tr').eq(0).removeClass("selected");
			}

		});
		
/// checkbox click		
		$('#g${model.objUid}').delegate('tbody .list_check','click',function(e){
			if(!$(this).prop('checked')){
				$(this).parent().parent().removeClass("selected");
				if($('#g${model.objUid} .selected').size()==0){
					$('#g${model.objUid} .list_check:checked:first').parent().parent().addClass("selected");				
				}
				e.stopPropagation();
			}
		});

/////tr click
		$('#g${model.objUid}').delegate('tbody tr','click',function(e){
	
			$('#g${model.objUid}  tbody tr.selected').removeClass("selected");//去掉原来的选中selected
			$(this).addClass("selected");
		});
		
	
		<#if (model.rowSize?exists && model.rowSize > 0 && pmlName?exists)>
			 var  iResultSize = parseInt( "${resultSize}".replace(/,/g,"") ); 
			 var ipageNo = parseInt("${pageNo}".replace(/,/g,""));
			 $("#Pagination${model.objUid}").pagination(iResultSize,{  
	            callback: PageCallback,  
	            <#if (langlocal=='zh') >
	            prev_text: '上一页',       //上一页按钮里text  
	            next_text: '下一页',       //下一页按钮里text
	            </#if>  
	            items_per_page: ${rowSize},  //显示条数  
	            num_display_entries: 6,    //连续分页主体部分分页条目数  
	            current_page: ipageNo-1,   //当前页索引  
	            num_edge_entries: 2        //两侧首尾分页条目数  
	        });  
	        
	        if(ipageNo == 1){
	        	if(ep.pagination['${model.containerPane.name}']){
	        	 	ep.pagination['${model.containerPane.name}'].pageNo = 1;
	        	}
	        }
	        
	      	function PageCallback(index, containers){
	      	
				  //////////记录当前面板	
		      	  var mPmlName = '${model.containerPane.name}'; 
	   	     	  if(!(mPmlName in ep.pagination)){
			   	  		ep.pagination[mPmlName] = {};
			   	  }
	   			  ep.pagination[mPmlName].pageNo = index+1;
			   	  ep.pagination[mPmlName].pageSize = '${model.rowSize}';		      	  
		      	  
				  var mainPml = ep.getMainPml();
		      	  if(mainPml!=null && mainPml!=mPmlName){
		      	      	  mPmlName = mainPml;
			   	     	  if(!(mPmlName in ep.pagination)){
					   	  		ep.pagination[mPmlName] = {};
					   	  }
			   			  ep.pagination[mPmlName].pageNo = index+1;
					   	  ep.pagination[mPmlName].pageSize = '${model.rowSize}';
					   	  ep.pagination[mPmlName].pagiPml = '${model.containerPane.name}';
				   	  }
			   	  
				   if($('#${pmlName}').size() > 0){
					   	loadPml({'pmlName':'${pmlName}',
					   	'pageNo':index+1,
					   	'pageSize': '${model.rowSize}',
					   	'target': '${pmlName}',
					   	'formName':'${formName}'
					   	});
				   }
			}
		</#if>
				
		   if ($(window).width() <= 992) {
		        $(".cntgridfooter").hide();
		    }
</script>
