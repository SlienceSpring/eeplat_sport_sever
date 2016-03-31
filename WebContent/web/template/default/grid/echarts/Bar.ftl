<#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/>  
	 
 <div id="d${model.objUid}" style="width:100%;height:350px;"></div>
 <script>

    console.log("values:::::${values}")

    require(
        ['echarts', 'echarts/chart/bar'],
        function(ec) {
            var myChart = ec.init(document.getElementById('d${model.objUid}'));
            
			option = {
			    title : {
			        text: '${model.caption}',
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data : ['业务量']
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : true,
			            dataView : {readOnly: true},
			            magicType:['line', 'bar'],
			            restore : true,
			            saveAsImage : true
			        }
			    },
			    calculable : false,

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
			    series : [
			        {
			            name:'业务量',
			            type:'bar',
			            data: ${values}
			        }
			    ]
			};
                    
            
            myChart.setOption(option);
        }
    );
    </script>