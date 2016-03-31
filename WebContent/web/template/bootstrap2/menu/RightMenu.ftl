<div style="margin-top:65px;padding-right:5px" class="rightmenu">
	<ul class="list-group">
		<h4>
		  快捷操作
		</h4>
	  <#list data as item>
	  	<li class="list-group-item"  paneid="${(item.paneid)!!}" panename="${(item.panename)!!}" >
	  	<#if item.size??>
	  	<span class="badge">${item.size}</span>
	  	</#if>
	  	${item.l10n}</li>
	  </#list>	
	</ul>
</div>

<script>


   $(".rightmenu .list-group li").bind(
   'click',
   		function(){
   			  if($("#" + $(this).attr('panename')).size() > 0){
			  	window.location = "#" + $(this).attr('panename');
			  }else{
			  	loadPml({'pml':$(this).attr('panename'),'target':'_opener_tab'});
			  }
   		}
   );


    if ($(window).width() <= 992) {
        $(".rightmenu").parent().remove();
    }
</script>