<style type="text/css">
	.cursor_pointer{
		cursor:pointer;
	}
	
	.div_selectcols{
		margin-left:115px;
		margin-top:13px
	}
	
	.select_cols{
		width:200px;
	}
	
	
	.btn_extend_add{
		margin-top:85px;
		margin-left:6px;
		margin-right:6px;
	}
	
	
	.select_title{
		font-weight:bold;
		margin-top:3px;
		margin-left:20px;
		margin-bottom:3px;
	}
	
	.div_float_left{
		float:left;
	}
	
	.div_controll{
		padding-left:5px;
		height:200px;
	}
	
	.onecriteria{
		float:left;
		margin-top:5px;
	}
	.criteriaindex{
		 display:inline-block;
		 width:20px;
		 height:20px;
		 margin:2px;
	}
		
	
	
</style>
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>

<div class="box" style="border:0" id="PM_criteria_manager">
  	<div class="box-body">
	<div class='row'>
	<div class="col-xs-12">
	
	<form class="form-horizontal">
	
	  <div class="form-group">
	    <label for="inputEmail3" class="col-xs-1 control-label">${i18n('视图名')}</label>
	    <div class="col-xs-6">
	      <input type="viewName" class="form-control" id="viewName" >
      	</div>
 	  </div>
 	
 	  <div class="form-group">  
 	  	<label class="col-xs-1 control-label">${i18n('选择列')}</label>
 	  </div>  
 	  
 	  <div class="form-group">
 	  
 	  		 <div id="div_showtexts" style="margin-left:115px">
 
			 	<span>可用列：</span>
			    <span style="margin-left:205px">已选择列：</span>
			 </div>
			
			 <div class="div_selectcols" >
			 
			
			 	<div class="div_float_left">
					<select id="select_allcols" class="select_cols form-control" style="height:200px" multiple=true  >
						<#list showForms as item>
							 <option value="${item.objUid}">${item.l10n}</option>
						</#list>
					</select>
				</div>
				
				<div  class="div_float_left">
					<input type="button" id="btn_extend_add" class="ctlBtn btn btn-info btn_extend_add" type="button" value="添加"/>
				</div>
				
				
				<div class="div_float_left">
					<select id="select_cols"  multiple=true class="select_cols forn-control"  style="height:200px">
					</select>
				</div>
				
				
				<div  class="div_float_left div_controll ">
					<div style="margin-top:60px"><img class="cursor_pointer"  id="opt_up" border=0  src='web/default/images/arrow_g_up_s.png'/></div>
					<div style="margin-top:15px"><img class="cursor_pointer" id="opt_down" border=0  src='web/default/images/arrow_g_down_s.png'/></div>	
					<div style="margin-top:15px"><img class="cursor_pointer" id="opt_del" border=0   src='web/default/images/arrow_g_del_s.png'/></div>
				</div>
				
			</div>	<!--end this section-->
		</div><!--End form group-->
		
		<div class="form-group">  
 	  		<label class="col-xs-1 control-label">${i18n('指定条件')}</label>
 	  	</div>  
 	  	
 	  	<div class="form-group">  
 	  	
 	  		<div class="div_selectcols">

			  <div class="row onecriteria form-inline" >
			  	
			  	<div class="col-xs-12 ">
			  	
			  	<span  class="criteriaindex">
			  	  1
			  	</span>
			  	
			  	<select class="criterialogic form-control"  style="visibility:hidden">
			  		<option value="and">与(AND)</option>
			  		<option value="or">或(OR)</option>
			  	</select>
			  	
			  	
			  	<select  class="select_cols cols_for_criteria form-control " >
			  		<option value="none">无</option>  
			  		<#list formTypes as item>
							 <option value="${item.formModel.objUid}" formType="${item.type}">${item.formModel.l10n}</option>
					</#list>
			  	</select>
			  	
			  	<select class="selectvarchar  selectoperator form-control">
			  		<option value="none">无</option>
			  		<option value="=">=</option>
			  		<option value="!=">!=</option>
			  		<option value="like">包含</option>
			  		<option value="not like">不包含</option>
			  		<option value="start">开始字符</option>
			  		<option value="end">结束字符</option>
			  		<option value="is null">为空</option>
			  		<option value="is not null">不为空</option>
			  	</select>
			  	
			  	
			  	 <select  class="selectnumber selectoperator form-control " style="display:none;">
			  		<option value="none">无</option>
			  		<option value="=">=</option>
			  		<option value="!=">!=</option>
			  		
			  		<option value="<">&lt;</option>
			  		<option value="<=">&lt;=</option>
			  		<option value=">">&gt;</option>
			  		<option value=">=">&gt;=</option>
			
			  		<option value="is null">为空</option>
			  		<option value="is not null">不为空</option>
			  	</select>
			  	
			    <select  class="selectdate selectoperator form-control " style="display:none;">
			  		<option value="none">无</option>
			
			  		<option value="=">=</option>
			  		<option value="!=">!=</option>
			  		<option value=">">早于</option>
			  		<option value="<">晚于</option>
			  		<option value="now">今天</option>
			  		<option value="befornow">早于今天</option>
			  		<option value="afternow">晚于今天</option>
			  		<option value="is null">为空</option>
			  		<option value="is not null">不为空</option>
			  	</select>
			  	
			  	 <select  class="selectscope selectoperator form-control " style="display:none;">
			  		<option value="none">无</option>
			  		<option value="=">=</option>
			  		<option value="!=">!=</option>
			  		<option value="is null">为空</option>
			  		<option value="is not null">不为空</option>
			  	</select>
			  	
			  	
			  	<div style="display:inline" class="uicomponent">
			  		<input type="input"  class="form-control" size="25"/>
			  	</div>	
			  	
			  	
	   		    <img class="cursor_pointer onecriteria_del" style="margin-left:5px;padding-top:0px" border="0" src="web/default/images/arrow_g_del_s.png">
	   		    
			  </div>
			  </div>
			  <div  style='clear:both'/>
			  
			  <div style="float:left">	 
			  	<img id="cridiria_add" style="margin-left:5px;padding-top:7px" class="cursor_pointer" id="opt_add_criteria" border=0  src='web/default/images/arrow_g_add_s.png'/>
			  </div>
			  
			  
			  <div id="combination_div" style="margin-top:10px;background: #f6f6f6;width:95%;height:60px;visibility:hidden">
			  
			  
			  	  <div style="float:left;margin:20px;with:45px" >
				      <div style="color:#808080;float:left">  组合方式= </div>
					  <div id="combination_label" style="float:left">
					 
					  </div>
					  <div id="combination_textarea" style="float:left;display:none;width:500px;height:60px;margin-top:-10px">
						  <textarea  class="form-control"   style="margin-left:10px;width:90%;height:35px;">
						  
						  </textarea>
					  </div>
					  
					  
				  </div>
				  
				  <div  id="combination_change_label" style="float:right;margin:20px;">
					  <a href="#" style="color:#6D84B4"  id="changecombination">
					  	更改组合方式</a>
				  	</div>
				  	
				  <div  id="combination_change_button"  style="float:right;margin:20px;display:none;color:#6D84B4">
				  	<a id="saveChangeCombination" href="#">保存</a>
					<span>|</span> 
					<a id="cancelChangeCombination" href="#" >取消</a>
				  </div>
				  
			  </div>
 	  		
 	  		</div>
 	  	</div><!--End form group-->

		<div class="form-group">
		  <div class="col-xs-offset-3">
			<input type="button" id="btn_extend_config_save" class="ctlBtn btn btn-primary" type="button" value="保存"/>
			<input type="button" id="btn_extend_config_cancel" class="ctlBtn btn btn-cancel" type="button" value="取消"/>
		  </div>		
		</div>	
	  
			
    </form>
    </div></div></div></div>



<script>



   $("#btn_extend_add").bind("click", function(){
   
   ////////////把已选择的选中状态全部
      	$("#select_cols option:selected").attr("selected",false);
   	
   		$("#select_allcols option:selected").each(
   		   function(i,e){
   		   	var  isExists =false;
   		   	$("#select_cols option").each(
   		   		function(ni,ne){
   		   			if($(e).val()==$(ne).val()){
   		   				isExists = true;
   		   				$(ne).attr("selected",true);
   		   			}	
   		   		}
   		   	);

   		   if(!isExists){
   		    	$("#select_cols").append($(e).clone().attr("selected",true));
   		    }
   		   
   		   }
   		);
   	}
   )
   
   
   
   /////up按钮
   
   $("#opt_up").bind("click",function(){
   
 		  var optionIndex = $('#select_cols').get(0).selectedIndex;
 		  
 		   
 		   if($('#select_cols option:selected').size() > 1){
 		   		return;
 		   }
 		  
 		  if(optionIndex > 0){ 
			$('#select_cols option:selected').insertBefore($('#select_cols option:selected').prev('option'));
		 } 
   
   });
   
   ////down按钮
      
   $("#opt_down").bind("click",function(){
   
    	 if($('#select_cols option:selected').size() > 1){
 		   		return;
 		 }
   
   		 var optionLength = $('#select_cols')[0].options.length; 
 		  var optionIndex = $('#select_cols').get(0).selectedIndex; 
 		  
 		  if(optionIndex < (optionLength-1)){ 
			$('#select_cols option:selected').insertAfter($('#select_cols option:selected').next('option'));
		 } 
   });
   
   
      ////delete按钮
   $("#opt_del").bind("click",function(){
  		$('#select_cols option:selected').remove();
	
   });
   
   /////
   $("#cridiria_add").bind("click",function(){
   
	   		if( $("#combination_textarea").is(":visible") ){
				alert("请先保存组合条件");
				return;
			}
 
  			$('div.onecriteria:last').clone(true).insertAfter(
  			  
  			   	$("<div  style='clear:both'/>")
  				.insertAfter($('.onecriteria:last'))

  			);
  			
  			
  			var lastIndex =1;
  			$(".criteriaindex").each(
  			  function(i,e){
  			  	lastIndex = i+1;
  			  	$(e).html(lastIndex);
  			  }
  			);
  			
  			////第一个隐藏and or
  			$(".criterialogic:eq(0)").css("visibility","hidden");
  			////后续的打开
  			$(".criterialogic:gt(0)").css("visibility","visible");
  			
  			///当只有一个的时候，是隐藏状态，所以设为可见
  			$("#combination_div").css("visibility","visible");

			///计算表达式
            calCombitionLogic(lastIndex);
   });
   
     
   function calCombitionLogic(lastIndex){
   
               ///计算组合逻辑
            
            var combinationExpression = "";

            var labelLogic = "与"; 
            if($(".criterialogic:last").val()=='or'){
            	labelLogic ="或";
            }
            
            if($.trim($("#combination_label").text()) == ""){
	
            	combinationExpression = "( 1 "  + labelLogic + " " + lastIndex + " )";
             
            }else{
            	
            	combinationExpression = "( " +$("#combination_label").text() + " "  + labelLogic + " " + lastIndex + " )";
            }
            $("#combination_label").text(combinationExpression);
   
   }
   
   
    $(".div_selectcols").delegate(".onecriteria_del","click", function() {
    
    	if( $("#combination_textarea").is(":visible") ){
			alert("请先保存组合条件");
			return;
		}
		
		///当条件只有一个时不删除
		
		if($(".criteriaindex").length == 1){
		   $(this).prevAll(":input").val("");
		    $(this).prevAll("select").val("none");
			return;
		}
		
     
     	var delNum = $(this).prevAll(".criteriaindex").text();
     	var delLogic = $(this).prevAll(".criterialogic").val();
     	var delLogicTxt = "与";
     	
     	if(delLogic == 'or'){
     		delLogicTxt = "或";
     	}
    	$(this).parent().parent().remove();
    	
    	var lastIndex =1;
  		$(".criteriaindex").each(
  			  function(i,e){
  			  	lastIndex = i+1;
  			  	$(e).html(lastIndex);
  			  }
  			);
    	$(".criterialogic:eq(0)").css("visibility","hidden");
  		$(".criterialogic:gt(0)").css("visibility","visible");
  		
  		if($(".onecriteria").size()==1){
  			$("#combination_div").css("visibility","hidden");
  			///表达式清空
  			$("#combination_label").text('');
  		}
  		

        var delCriteriaReg = new RegExp(delLogicTxt + ' ' + delNum + ' ');
        

        if(delNum == '1'){
			delCriteriaReg = new RegExp(' 1 [或与]');        
        }
        
        var combinationExpression = $.trim($("#combination_label").text());
        
        combinationExpression = combinationExpression.replace(delCriteriaReg,'');
        
        var ri = parseInt(delNum) + 1;
        for(; ri <= (lastIndex+1); ri++){
        	var riReg = new RegExp(' ' + ri + ' ');
      	    combinationExpression = combinationExpression.replace(riReg,' ' + (ri-1) + ' ');
        }
        
      ////把 ( 1 ) 替换为1
        while(/\( 1 \)/.test(combinationExpression)){     
        	combinationExpression = combinationExpression.replace(/\( 1 \)/,'1');
        }

     	$("#combination_label").text(combinationExpression);

   });
	
	////改变或、与
	$(".div_selectcols").delegate(".criterialogic","change",function(){
	
		if( $("#combination_textarea").is(":visible") ){
			alert("请先保存组合条件");
			if($(this).val() == 'or'){
				$(this).val('and');
			}else{
				$(this).val('or');
			}
			return false;
		}
		
		var changeNum = $(this).prev().text();

		
     	var repLogicTxt = "与";
     	var oldLogicTxt = "或";
     	
     	if($(this).val() == 'or'){
     		repLogicTxt = "或";
     		oldLogicTxt = "与";
     	}
     	
        var combinationExpression = $.trim($("#combination_label").text());
        var repCriteriaReg = new RegExp(oldLogicTxt + ' ' + changeNum + ' ');
        combinationExpression = combinationExpression.replace(repCriteriaReg,repLogicTxt + ' ' + changeNum + ' ');
        
        $("#combination_label").text(combinationExpression);
	});
   
   
   	////改变列
	$(".div_selectcols").delegate(".cols_for_criteria","change",criteriaColChange);
	
	
	function criteriaColChange(thiso,vflag){
	    
	    if(vflag==null){
	    	thiso = this;
	    }

 		
		var formUid = $(thiso).val();

        var vv  = $(thiso).nextAll(".uicomponent").find("input").val();
        
        var vhidden = $(thiso).nextAll(".uicomponent").data('hiddenvalue');

		$(thiso).nextAll(".uicomponent").load(
		   globalURL +	"web/default/htmlform.jsp?formUid=" + formUid, 
		   function(){
		       if(vflag){
			   		$(thiso).nextAll(".uicomponent").find(":input").val(vv);
			   		$(thiso).nextAll(".uicomponent").find("select").val(vv);
			   		if(vhidden){
				   		$(thiso).nextAll(".uicomponent").find("input:hidden").val(vhidden);
			   		}
					if(vv=='%SYSTEM%'){
						$(thiso).nextAll(".uicomponent").find("input").attr("disabled", true);
					}
			   }
		   }
		);
		
		var formType = $(thiso).find("option:selected").attr("formType") ;
		
		if(formType=='number'){
			$(thiso).nextAll(".selectoperator").css("display","none");
			$(thiso).nextAll(".selectnumber").css("display","inline");
		}else if(formType=='date'){
			$(thiso).nextAll(".selectoperator").css("display","none");
			$(thiso).nextAll(".selectdate").css("display","inline");
		}else if(formType=='select'){
			$(thiso).nextAll(".selectoperator").css("display","none");
			$(thiso).nextAll(".selectscope").css("display","inline");
		}else {
			$(thiso).nextAll(".selectoperator").css("display","none");
			$(thiso).nextAll(".selectvarchar").css("display","inline");
		}

	}
   
    
    ////列的操作，如> < =等
	$(".div_selectcols").delegate(".selectoperator","change",function(){
	
		if($(this).val()=='is null'
		|| $(this).val()=='is not null'
		|| $(this).val()=='now'
		|| $(this).val()=='befornow'
		|| $(this).val()=='afternow'
		){
			$(this).nextAll(".uicomponent").find(":input").val('%SYSTEM%');
			$(this).nextAll(".uicomponent").find(":input").attr("disabled", true);
		} else{
			$(this).nextAll(".uicomponent").find(":input").attr("disabled", false);
			if($(this).nextAll(".uicomponent").find(":input").val() =='%SYSTEM%' ){
				$(this).nextAll(".uicomponent").find("input").val('');
			}
		}
	
	});
    
    
    
   
      $("#changecombination").bind("click",function(){
      
      	 $("#combination_textarea").css("display","inline");
      	 $("#combination_label").css("display","none");
      	 $("#combination_change_label").css("display","none");
      	 $("#combination_change_button").css("display","inline");
      	 $("#combination_textarea").children("textarea").val($("#combination_label").text());
      });
      
      
       

      $("#saveChangeCombination").bind("click",function(){
    
		 var  areaVal = $("#combination_textarea").children("textarea").val();
		 if(!checkBracket(areaVal)){
		 	alert("括弧不合法");
		 	return;
		 }
		 
		 if(!/^(\s|\(|\d)(\s|\)|\(|或|or|与|and|\d)*(\s|\)|\d)$/.test(areaVal)){
		 	alert("表达式不合法");
		 	return;
		 }
		 
		 
		 		 
		 //格式化表达式
	    var re = /(\)|\(|或|or|与|and|\d+)/gi;
	
		var arr =areaVal.match(re); 

        var formatVal = "";		
		for(var i = 0; i < arr.length ; i++){
			formatVal = formatVal +  arr[i]  + " ";
		}
		
		areaVal = formatVal;
		 
		 
		 var labelIndex = $(".criteriaindex:last").text();
		 var indexMatch = areaVal.match(/ [0-9]+ /g)
		 var ti = 1;
		 for(; ti<=indexMatch.length; ti++){
		 	if(ti != indexMatch[ti-1]){
		 		alert("表达式的顺序需要一致");
		 		return;
		 	}
		 }

		 if(parseInt(labelIndex) != (ti-1)){
		 	alert("表达式的条件数量和条件编辑器中的条件数量不一致");
		 	return;
		 }
		 
		 
		 var logicMatch = areaVal.match(/或|or|与|and/gi);
		 if(parseInt(labelIndex) != (logicMatch.length+1)){
		 	alert("表达式的条件数量和条件编辑器中的条件数量不一致");
		 	return;
		 }
		 
		 for(var i = 1 ;i < logicMatch.length+1; i++){
		 	var oneMatch = $.trim(logicMatch[i-1]);
		 	var oneVal = $(".criterialogic:eq(" + i + ")").val();
		 	if( (oneMatch=="与" || oneMatch=="and") && oneVal=="or" ){
		 		$(".criterialogic:eq(" + i + ")").val("and");
		 	}else if ( (oneMatch=="或" || oneMatch=="or") && oneVal=="and" ){
		 		$(".criterialogic:eq(" + i + ")").val("or");
		 	}

		 }
		 
		 
		 $("#combination_textarea").css("display","none");
      	 $("#combination_label").css("display","inline");
      	 $("#combination_change_label").css("display","inline");
      	 $("#combination_change_button").css("display","none");
      	 
      	 $("#combination_label").text(  areaVal );
      });

      
      $("#cancelChangeCombination").bind("click",function(){
      
      	 $("#combination_textarea").css("display","none");
      	 $("#combination_label").css("display","inline");
      	 $("#combination_change_label").css("display","inline");
      	 $("#combination_change_button").css("display","none");
      });
      
      
     $("#btn_extend_config_cancel").bind("click",function(){
			reloadMenuPml();
      });
      
      
     $("#btn_extend_config_save").bind("click",function(){
     
			var viewName = $("#viewName").val();
			if(viewName==null || $.trim(viewName)==""){
				alert("视图名称不能为空");
				return;
			}

			
			
			var selectedCols = $("#select_cols option").size();
			if(selectedCols==0){
				alert("还没有选择列！");
				return;
			}
			
			var isValid = true;			
			if( $(".cols_for_criteria").size() > 1 ){///如果条件大于1的情况
			
				$(".cols_for_criteria").each(
					function(i,o){
				   		if($(o).val()=='none' ){
				   			alert("第" + (i+1) + "行没有选择列");
				   			isValid = false;
				   			return false;
				   		}
				   		
				   		if($.trim($(o).nextAll("select:visible").val()) =='none'){
				   			alert("第" + (i+1) + "行没有选择比较操作符");
				   			isValid = false;
				   			return false;
				   		}
				   		
				   		if($.trim($(o).nextAll(".uicomponent").find(":input").val()) == ""){
				   			alert("第" + (i+1) + "行没有输入值");
				   			$(o).nextAll(".uicomponent").find(":input").focus();
				   			isValid = false;
				   			return false;
				   		}
					}
				);
				
				if(!isValid){
					return;
				}
			
			}else{/////条件只有一个的情况
				
				if( !( $(".cols_for_criteria").val()=='none'  
						&& $.trim( $(".cols_for_criteria ~ select:visible").val() ) =='none'
						&& $.trim( $(".uicomponent input").val()=='' )
					 ) 
				  ){
					 
					if(  $(".cols_for_criteria").val()=='none'  
						|| $.trim( $(".cols_for_criteria ~ select:visible").val() ) =='none'
						|| $.trim( $(".uicomponent input").val() )=='' 
					 )
					 
					{
						alert("请完成条件指定");
						return;
					}
				}
			}
			
			var  assemblyJson = {};
			assemblyJson.tableName = $("body").data('tableName');
			assemblyJson.viewName =  $("#viewName").val();
			
			
			var selectCols = [];
			$("#select_cols option").each(
			  function(){
			  	selectCols.push($(this).val());			  
			  }
			
			);
			
			
			assemblyJson.showColNames = selectCols;
			assemblyJson.criterias = [];
			assemblyJson.criteriaStatement = $.trim( $("#combination_label").text() );
			

			assemblyJson.extendFormUid = $("body").data('extendFormUid');
			assemblyJson.extendGridUid = $("body").data('extendGridUid');
			
			assemblyJson.method = '${method!('new')}';
			
			<#if  selectedPml?? >
				///copy后台是新增，在后台中 selectedPml作为判断新增和修改的标志。			
			   if(assemblyJson.method!='copy'){
					assemblyJson.selectedPml = "${selectedPml}";
					console.log("selectPML:::${selectedPml}")
				}
			</#if>
			
			
			
			/////没有制定条件 
			if( $(".cols_for_criteria").val()!='none' ){
			 
				$(".cols_for_criteria").each(
					function(i,o){
						var oneCriteria = {};
						
						if($(o).prev().length>0 && $(o).prev().val()!=''){
							oneCriteria.pre = $(o).prev().val();
						}
						oneCriteria.col = $(o).val();
						oneCriteria.operator = $.trim($(o).nextAll("select:visible").val());
						var typeClasses = $(o).nextAll("select:visible").attr('class');
						
						oneCriteria.typeClass = typeClasses.substring(0,typeClasses.indexOf(' '));
						
						oneCriteria.value = $.trim($(o).nextAll(".uicomponent").find(":input").val());
						
						////加上一个valueLabel
						if($(o).nextAll(".uicomponent").children(".resultlistpopupshow").size() >  0){
							oneCriteria.valueLabel = $.trim($(o).nextAll(".uicomponent").children(".resultlistpopupshow").val());
						}
						
				   		assemblyJson.criterias.push(oneCriteria);
					}
				)
			}
			
			console.log("assemblyJson::" + O2String(assemblyJson));
		

		 	callAction({'btn':$("#btn_extend_config_save")[0],
		    			'actionName':"com.exedosoft.plat.action.customize.tools.DOSaveExtendConfig",
				   		'callback':function(data){
				   		
				   		///////////加载
				   		
				   			console.log("Hhhhh::" + O2String(data));
				   			
				   			reloadMenuPml(function(){

						   			var newpanename = data.newpanename;
						   			var newpanetitle = data.newpanetitle;///=$("#viewName").val()
						   			var paneChangeSelector = "#" + assemblyJson.extendFormUid ;
						   			
						   			////如果是修改删除原来的。
						   			<#if  selectedPml?? >
						   			    //////////修改情况下
						   			   if($(paneChangeSelector + " option:selected").val() != newpanename){
											$(paneChangeSelector + " option:selected").remove();
										}
									</#if>
									
									console.log("newpanename::::::" + newpanename);
									console.log("paneChangeSelector::" + paneChangeSelector);
									console.log($(paneChangeSelector).size());
						   			$(paneChangeSelector).val(newpanename).change();
				   				});
				   				
				   			///刷新前面的tab页面，并加载新建立的视图
				   		},
				   		'paras': "paras="+encodeURIComponent(escape(O2String(assemblyJson))) });
		
			
      });
      
      
     ///////////////////////////////////////////////////修改的情况////////////////////////////////////////////////////////////////////////////////
     
   <#if configJson?? >
   

   		var configJson = ${configJson}; 	

		 $("#viewName").val(configJson.viewName);
		 
		 for(var i = 0; i < configJson.showColNames.length ; i++ ){
		 
		     var aCol = configJson.showColNames[i];
		 	 $("#select_allcols option").each(
		   		   function(ii,e){
	  		   			if($(e).val() == aCol){
			   		    	$("#select_cols").append($(e).clone());
			   		    }
		   		   }
   			);
		 }
		 
		 
		
		$.each(configJson.criterias,function(i,v){
			
			
			////第一个不用clone
			if(i == 0){

 				if(v.valueLabel){
					$(".uicomponent  input").val(v.valueLabel);
					$(".uicomponent").data('hiddenvalue',v.value);
 				}else{
					$(".uicomponent  input").val(v.value);
				}
								
				var criteriaO =  $(".cols_for_criteria").val(v.col);
				$(".selectoperator").not("." + v.typeClass).css("display","none");
				$("." + v.typeClass).css("display","inline").val(v.operator);
				
				            /////////自动赋值
  				criteriaColChange(criteriaO,true);
			
			}else{
			
////////////begin deal criteria

			var otr = $('div.onecriteria:last').clone().insertAfter(
  			     
  			
    	$("<div  style='clear:both'/>")
  				.insertAfter($('.onecriteria:last'))
  			);
  			
  			
  			if(v.valueLabel){
					otr.find(".uicomponent  input").val(v.valueLabel);
					otr.find(".uicomponent").data('hiddenvalue',v.value);
 			}else{
					otr.find(".uicomponent  input").val(v.value);
			}
  			
  			var criteriaO = otr.find(".cols_for_criteria").val(v.col);
  			otr.find(".selectoperator").not("." + v.typeClass).css("display","none");
  			otr.find("." + v.typeClass).css("display","inline").val(v.operator);
  			otr.find(".criterialogic").val(v.pre);

            /////////自动赋值
  			criteriaColChange(criteriaO,true);

//end deal criteria
			}///end each
		
		    if(configJson.criterias.length > 1){			
				var lastIndex =1;
	  			$(".criteriaindex").each(
	  			  function(i,e){
	  			  	lastIndex = i+1;
	  			  	$(e).html(lastIndex);
	  			  }
	  			);
	
	  			////后续的打开
	  			$(".criterialogic:gt(0)").css("visibility","visible");
	  			///表达式打开
	  			$("#combination_div").css("visibility","visible")
	  			$("#combination_label").html(configJson.criteriaStatement);
  			}

		});
		
   
   </#if>
      
      

</script>
