<select  id='${model.objUid}' style="height:28px;width:200px">
	<option value=''></option>
	${htmls}	    
</select>
<a style="margin-left:10px;" class="extendadisable" id="extendModifyView"  href="#">编辑</a>
|
<a class="extendadisable" id="extendDeleteView"  href="#">删除</a>
|
<a class="extendadisable" id="extendCopyView"  href="#" >复制视图</a>
|
<a class="extenda" id="extendNewView" href="#">新建视图</a>

<script>

   $("#${model.objUid}").bind('change',function(){
   
   		var changePml = $(this).val();
   		
   		if(changePml==''){
   			return;
   		}
   		
   		console.log("default style changePml::::" + changePml);
     		
   		///////////判断是用户定义的视图
   		
   		ep.conditonStatus("#${model.objUid}");

 	    loadPml({
   			 	'pml':changePml,
				'target':'${model.linkPaneModel.name}'
			}
		);
	 	   
   	}
   );
   
   
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
        var selectedPml =   $(this).prevAll("select").val();
   
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
        var selectedPml =   $(this).prevAll("select").val();
   
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
        
	        var selectedPml =   $(this).prevAll("select").val();
	
	   		var paras = 'selectedPml=' + selectedPml + "&extendFormUid=${model.objUid}&extendGridUid=${model.gridModel.objUid}" ;
	   		
	   		var o = this;
	   		
			callAction({	'actionName':"com.exedosoft.plat.action.customize.tools.DORemoveExtendConfig",
					   		'callback':function(data){
					   		   
					   			 $(o).prevAll("select").find("option:selected").remove();
					   			 $(o).prevAll("select").find("option:eq(1)").attr("selected",true).change();
					   			 
					   		},
					   		'paras': paras });
				   		
	      }
        


    })
   
   
   
   

</script>