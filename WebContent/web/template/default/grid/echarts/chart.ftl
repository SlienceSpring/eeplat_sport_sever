<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/>  
	 <#--开始输出空行-->
<#if model.numTopP?exists>
	<#list 1..model.numTopP as x>  
		<br/>
    </#list>
<#else>  <#--没有定义的话，输出一个空行-->  
	<br/>
</#if>
 <div id="d${model.objUid}" style="width:${width?default('100%')};height:${height?default('350px')};"></div>
 <script>
    
    require(
        ['echarts', 'echarts/chart/${requireChartType}'],
        function(ec) {
            var myChart = ec.init(document.getElementById('d${model.objUid}'));
            
			option = {
			    title : {
			        text: '${mainText}',
			        subtext : '${subText}',
			        x:'center'
			    },
			    <#if ('${requireChartType}'=='line' || '${requireChartType}'=='bar')>
			    tooltip : {
			        trigger: 'axis'
			    },
			    <#else>
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    </#if>
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			    	show : ${legendShow},
			        data : ${legendData}
			    },
			    calculable : false,
			    <#if ('${requireChartType}'=='line' || '${requireChartType}'=='bar')>
			    xAxis : [
			        {
			            type : 'category',
			            data : ${names},
			            axisLabel:{
			            	margin:5,
			            	interval:0,
			            	formatter:function(value){if(value.length>12){ return value.substring(0,10)+"..." } else return value; }
			            }
			        }
			    ],

			    yAxis : [
			        {
			            type : 'value',
			        }
			    ],
			    </#if>
			    series : [<#list listSv as sv>
			        {
			            name:'${sv.category}',
			            type:'${sv.chatType}',
			    		<#if ('${requireChartType}'=='line' || '${requireChartType}'=='bar')>
			            data: ${sv.data}
			            <#else>
			            data: ${nameValues}
			            </#if>
			        }
			       <#if (sv_index < (listSv?size-1)) >,</#if>	    
			       </#list>
			    ]

			};
			
			<#if (otheroptions??) >
				${otheroptions};
			</#if>
                    
            
            myChart.setOption(option);
        }
    );
    </script>