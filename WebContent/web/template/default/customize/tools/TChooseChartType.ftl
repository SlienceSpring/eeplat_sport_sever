<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>

 <table class='chooseChartType' width="90%" border="0" align="center" cellpadding="0" cellspacing="2">
   <tbody>
    <tr style="height:30px">
     <td width="25%" class="">选择图表类型:</td> 
     <td style="width:5px">&nbsp;</td> 
     <td width="75%" class="">图表信息:</td>
    </tr>
    <tr>
     <td valign="top" style="border: 1px solid #DDD"> 
       <table width="100%" border="0" cellspacing="0" cellpadding="4"  class="chartType">
        <tbody>
        
         <tr icon_='bar1' class="selected">
          <td width="50"> 
           <i class="ec-icon ec-icon-bar"></i>
          <td nowrap=""  class="chartType">柱状图</td>
         </tr>
         
         <tr icon_='bar2'>
          <td width="50">
           <i class="ec-icon ec-icon-bar"></i>
          </td> 
          <td nowrap=""  class="chartType">柱状图-分类</td>
         </tr> 
         
         <tr icon_='pie1'>
          <td width="50">
           <i class="ec-icon ec-icon-pie"></i>
          <td nowrap=""  class="chartType">饼图</td>
         </tr>
         
         <tr icon_='line1'>
          <td width="50">
            <i class="ec-icon ec-icon-line"></i>
            <td nowrap=""  class="chartType">折线图</td>
         </tr>
           
        <tr icon_='line2'>
          <td width="50">
            <i class="ec-icon ec-icon-line"></i>
            <td nowrap=""  class="chartType">折线图-分类</td>
         </tr>
           
           
         <tr icon_='funnel1'>
          <td width="50">
            <i class="ec-icon ec-icon-funnel"></i>
            <td nowrap=""  class="chartType">漏斗图</td>
         </tr>       
             
         <tr icon_='gauge1'>
          <td width="50">
            <i class="ec-icon ec-icon-gauge"></i>
            <td nowrap=""  class="chartType">仪表盘</td>
         </tr>
         
        <tr icon_='box'>
          <td width="50">
            <i class="ec-icon  ec-icon-theme"></i>
            <td nowrap=""  class="chartType">BOX</td>
         </tr>        
        
        </tbody>
       </table><!--End Table-->
      </td>
       
     <td valign="top">&nbsp;<input id="gridModelName" type='hidden'> </td> 
     <td style="border: 1px solid #DDD">
     <br />
      <table width="90%" border="0" align="center" cellpadding="0" cellspacing="2">
       <tbody>
        <tr>
         <td style='width:135px'>
          <div id="yaxischoose" style="visibility: visible;">
				<label class="">Value列(Y轴):</label><br/>
				${yaxiscol.htmlValue}
          </div>
          </td> 
         <td width="150"> 
          <div align="center">
           <img id="charticon" src="web/default/images/chart/bar1.png"  height="180" />
          </div></td> 
         <td>
          <div id="categorychoose" style="display: none;">
          	<label class="">分类:</label>
				${categorycol.htmlValue}
               	<br/>
				<img src="web/default/images/chart/legend.png" style="margin-top:5px"/>
          </div>
          
          <div id="chartlinkpane" style="display: none;text-align:left;align:left">
          		<label  style='white-space:nowrap'>链接的面板:</label><br/>
				${(linkPane.htmlValue)!!}

			<br/>	<label  style='white-space:nowrap'>背景颜色:</label>
			<br/>
				${(bgcolor.htmlValue)!!}	
						<br/>
				<label  style='white-space:nowrap'>图标:</label><br/>
				${(boxicon.htmlValue)!!}	
						
          </div>
          
          </td>
        </tr>
        <tr>
         <td>&nbsp;</td> 
         <td valign="top"> 
	          <div id="xaxischoose" style="visibility: visible;margin-top:10px">
					<label  class="">名称列(X轴):</label>
					${xaxiscol.htmlValue}
	          </div>

          </td> 
         <td>&nbsp;</td>
        </tr>
       </tbody>
      </table><br /></td>
    </tr>
    <tr>
     <td>&nbsp;</td> 
     <td>&nbsp;</td> 
    </tr>
   </tbody>
  </table>
  
  <script>
  	 $(".chartType tr").mouseover(function(){ 
           		//如果鼠标移到class为stripe的表格的tr上时，执行函数 
          		$(this).addClass("over");
          }).mouseout(function(){ 
                //给这行添加class值为over，并且当鼠标一出该行时执行函数 
                $(this).removeClass("over");
          }).click(function(){
          	$(".chartType tr").removeClass("selected");
          	$(this).addClass("selected");
          	
          	var chartType = $(this).attr('icon_');
          	$("#charticon").attr('src',"web/default/images/chart/" + chartType + ".png"); 
          	
          	
          	if(chartType=='bar2' || chartType=='line2'){
          		$("#categorychoose").show();
          	}else{
          		$("#categorychoose").hide();
          	
          	}
          	
          	if(chartType=='box'){
          		$("#chartlinkpane").show();
          	}else{
          		$("#chartlinkpane").hide();
          	}
          	
          	
          }); 
          
          
      $(".chartType tr:even").addClass("alt"); 
      
      <#if jsonData??>
          var values = ${jsonData};
          console.log("values:::" + O2String(values));
          
        $("#gm_chart_config_edit_name").val(values.name);
		$("#gm_chart_config_edit_l10n").val(values.l10n);
		 
		
		
		$("table.chooseChartType tr[icon_='" + values.chartType + "']").click();

		$("#xaxiscol").val(values.xaxisCol);
		$("#yaxiscol").val(values.yaxisCol);
	 	$("#categorycol").val(values.categoryCol);
		$("#maintext").val(values.mainText);
		$("#subtext").val(values.subText);
		$("#otheroptions").val(values.otheroptions);
		$("#bgcolor").val(values.bgcolor);
		$("#boxicon").val(values.boxicon);
		$("#linkPane").val(values.linkPaneId);
		$("#xaxiscol").next().val("${xaxisCol_show!!}");
		$("#yaxiscol").next().val("${yaxisCol_show!!}");
		$("#categorycol").next().val("${categoryCol_show!!}");
		$("#linkPane").next().val("${linkPane_show!!}");
		$("#gridModelName").val("${gridModelName!!}");
		
      
      </#if>
  
  </script>