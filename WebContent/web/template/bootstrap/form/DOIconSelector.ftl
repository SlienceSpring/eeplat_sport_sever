<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()>  
<div class="dropdown form-group" id="d${randomid}" style="margin-left:0px">
   <input  class="resultlistpopup" id="${randomid}" type="hidden" name="${model.colName}"  serviceName="${(model.linkService.name)!!}" searchColName="${searchColName?if_exists}"  value="${thevalue?if_exists}" />
  <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
    <span class="btnlabel"> <i class="${thevalue?if_exists}"></i>${label?default(i18n("Please select"))} </span>
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu" style="min-width:200px" >
  
     <#if splitpage??>
        <div class="input-group input-group-sm" style="margin-left:5px;margin-top:2px">
	      <input type="text" class="form-control" id="input${randomid}" placeholder="Search for...">
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="button" id="btn${randomid}">Go!</button>
	      </span>
	    </div>
	 </#if> 
   
    <#if splitpage??>
    
    		<div id="split${randomid}" nowrap='nowrap'>
	            <span id="pre${randomid}" nowrap='nowrap'  pageno=1 pageSize=${pageSize}  class='disable' style="margin-left:15px;cursor:pointer">上一页</span>
	            
	             &nbsp;&nbsp;&nbsp;&nbsp;<span id="next${randomid}" nowrap='nowrap' class="enable" style="cursor:pointer">下一页</span>
            </div> 
             

    </#if>
  </ul>
</div>
<script>


    var eventTargetElememntDropdown = 'span';
    $("#d${randomid} .dropdown-menu").delegate("li","click",function(event){

        $("#${randomid}").val($(this).attr('idvalue'));
        $("#d${randomid} .btnlabel").html($(this).children("a").html());
        eventTargetElememntDropdown = 'li';

    });
    
    
    $("#pre${randomid}").on("click",function(event){
	
		eventTargetElememntDropdown = 'span';
		
		if($("#pre${randomid}").hasClass("disable")){
			return;
		}
		
		$("#next${randomid}").removeClass("disable").addClass("enable");
		

		
		var pageSize = parseInt($("#pre${randomid}").attr("pageSize"));
		var curPage =  parseInt($("#pre${randomid}").attr("pageno"));
		if(curPage > 1){
			var pageDe = curPage - 1;
			$("#pre${randomid}").attr("pageno",pageDe);
			if(pageDe==1){
				$("#pre${randomid}").removeClass("enable").addClass("disable");
			}
			 ep.selectLis("${randomid}",pageDe);
		}else{
			$("#pre${randomid}").removeClass("enable").addClass("disable");
		}
			
			
    });
    
    
     $("#next${randomid}").on("click",function(event){
	
		eventTargetElememntDropdown = 'span';
		
		if($("#next${randomid}").hasClass("disable")){

			return;
		}
		
		var pageSize = parseInt($("#pre${randomid}").attr("pageSize"));
		var curPage =  parseInt($("#pre${randomid}").attr("pageno"));
		var pageIncre = curPage+1;
		if(curPage < pageSize){
			 $("#pre${randomid}").attr("pageno",pageIncre);
			 if(curPage == (pageSize - 1)){
			 	$("#next${randomid}").removeClass("enable").addClass("disable");
			 }
			 $("#pre${randomid}").removeClass("disable").addClass("enable");
			 
			 ep.selectLis("${randomid}",pageIncre);
			        
       }
 			
    });
    
    
    /////////////search
    $("#btn${randomid}").on("click",function(event){
       
       	eventTargetElememntDropdown ='btn';
    	
      	$("#pre${randomid}").attr("pageno",1);
       	ep.selectLis("${randomid}");
   
     });
     
     
     $("#input${randomid}").on("click",function(event){
       
        eventTargetElememntDropdown ='input';
     });
    
     
    
    $("#d${randomid}").on("hide.bs.dropdown",function(e){
	     
	     	if(eventTargetElememntDropdown == 'li'){
	     		return true;
	     	}
	     	return false;
     });
     
     $("#d${randomid}").on("show.bs.dropdown",function(e){

    	ep.selectLis("${randomid}");
	     
	
     });
     
     
     
     
  
</script>
