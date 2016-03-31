	<form class="form-inline">	

			<select class='form-control' id='${model.objUid}' style="max-height:33px">
				<option value=''></option>
				${htmls}	    
			</select>
		<div style="display:inline" class='ctrldefiledview'>
			<a style="margin-left:10px;" class="extendadisable" id="extendModifyView"  href="#">编辑</a>
			|
			<a class="extendadisable" id="extendDeleteView"  href="#">删除</a>
			|
			<a class="extendadisable" id="extendCopyView"  href="#" >复制视图</a>
			|
			<a class="extenda" id="extendNewView" href="#">新建视图</a>
			|
			<a class="extenda" id="extendRefreshView" href="#">刷新</a>
		</div>	
	</form>	



<script>

//	$( "#${model.objUid}" ).select2({
//		minimumResultsForSearch: Infinity
//	}
//	);

   //////////保留上次点击
   var mPmlName =  ep.myHash.mainPmlName;        //$("body").data("mainPmlName"); 
   
    if(!ep.pagination._conShow){
	   if(ep.pagination[mPmlName]){
	   		if(ep.pagination[mPmlName].changePml){
	   			$("#${model.objUid}").val(ep.pagination[mPmlName].changePml);
	   			ep.conditonStatus("#${model.objUid}");
	   		}
	   }
   }
   

   $("#${model.objUid}").bind('change',function(){
   
   
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
       console.log("change==================================================");
  
   		var changePml = $(this).val();
   		
   		if(changePml=='' || changePml=='null'){
   			return;
   		}
   		
   		var mPmlName = ep.myHash.mainPmlName; // $("body").data("mainPmlName"); 
   		///改变后，重新赋值
   		if(mPmlName){
   			ep.pagination[mPmlName].changePml = changePml;
   			////设为第一页
   			if(ep.pagination[changePml]){
   				ep.pagination[changePml].pageNo = 1;
   			}
   		}
   		
   		//查询条件置为null
   		ep.pagination._conParas = null;
   		ep.pagination._conShow = false;
   		$("#_opener_tab").find(".extendBody").toggle(false);
   		$('#conditonExtendSwitch').text("打开综合查询");
   		
   		
   		///////////判断是用户定义的视图
   		ep.conditonStatus("#${model.objUid}");
 	    loadPml({
   			 	'pmlName':changePml,
				'target': '${model.linkPaneModel.name}'
			}
		);
   	}
   );
   
   
   $("#extendRefreshView").bind('click',function(){
       	var selectedPml =  $( "#${model.objUid}" ).val();
       	if(selectedPml==null || $.trim(selectedPml)==''){
       		return;
       	}
    	loadPml({
   			 	'pml':selectedPml,
				'target':'${model.linkPaneModel.name}'
			}
		);
    })
   
   
    $("#extendNewView").bind('click',function(){
    	$('body').data('tableName','${model.gridModel.category.name}');
    	$('body').data('extendFormUid','${model.objUid}');
    	$('body').data('extendGridUid','${model.gridModel.objUid}');
    	var paras = "tableName=${model.gridModel.category.name}&gridModelUid=${model.gridModel.objUid}";
    	loadPml({'pml':'PM_Extend_config','target':'_opener_tab','title':'新建视图','paras':paras});
    })
   
   
   $("#extendModifyView").bind('click',function(){
   
        if($(this).hasClass('extendadisable')){
        	return;
        }
        
        var selectedPml =  $( "#${model.objUid}" ).val();
        
        console.log("selectedPml::" + selectedPml);
   
   		$('body').data('tableName','${model.gridModel.category.name}');
   		$('body').data('extendFormUid','${model.objUid}');
   		$('body').data('extendGridUid','${model.gridModel.objUid}');
   		var paras = 'method=modify&selectedPml=' + selectedPml + "&tableName=${model.gridModel.category.name}&gridModelUid=${model.gridModel.objUid}";
   		loadPml({'pml':'PM_Extend_config','target':'_opener_tab','title':'编辑视图','paras':paras})
  
    });
    
    
     $("#extendCopyView").bind('click',function(){
   
        if($(this).hasClass('extendadisable')){
        	return;
        }
        var selectedPml = $( "#${model.objUid}" ).val();
   
   		$('body').data('tableName','${model.gridModel.category.name}');
   		$('body').data('extendFormUid','${model.objUid}');
   		$('body').data('extendGridUid','${model.gridModel.objUid}');
   		var paras = 'method=copy&selectedPml=' + selectedPml  + "&tableName=${model.gridModel.category.name}&gridModelUid=${model.gridModel.objUid}";
   		loadPml({'pml':'PM_Extend_config','target':'_opener_tab','title':'复制视图','paras':paras})
  
    });
    
    
   $("#extendDeleteView").bind('click',function(){

        if($(this).hasClass('extendadisable')){
        	return;
        }
        
        ///////
        if(confirmDelete()){
        
	        var selectedPml =   $( "#${model.objUid}" ).val();
	
	   		var paras = 'selectedPml=' + selectedPml + "&extendFormUid=${model.objUid}&extendGridUid=${model.gridModel.objUid}" ;
	   		
	   		var o = this;
	   		
			callAction({	'actionName':"com.exedosoft.plat.action.customize.tools.DORemoveExtendConfig",
					   		'callback':function(data){
					   			 $( "#${model.objUid}" ).find("option:selected").remove();
					   			 $( "#${model.objUid}" ).find("option:eq(1)").attr("selected",true).change();
					   		},
					   		'paras': paras });
	      }
    });
    
    if ($(window).width() <= 992) {
        $(".ctrldefiledview").hide();
    }
    $(".extendswitch").hide();
   

</script>