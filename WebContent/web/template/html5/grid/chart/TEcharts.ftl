<#assign datatojson = "com.exedosoft.plat.template.Data2Json"?new()/> 
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()/>  
<div id='${model.objUid}'  style="height:400px">

</div>

<script>
  
      console.log("test echarts111111111111111");

        require(
            [
                'echarts',
                'echarts/chart/bar'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('${model.objUid}')); 
                
                var option = {
                    tooltip: {
                        show: true
                    },
                    legend: {
                        data:['销量']
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            "name":"销量",
                            "type":"bar",
                            "data":[5, 20, 40, 10, 10, 20]
                        }
                    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        
        console.log("testecharts222222222222222222222222222")

</script>