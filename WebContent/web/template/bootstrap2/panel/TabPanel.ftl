<div class="content" id="${model.name}"> 
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <#list items as item>
                <#list items as item>
          <li role="presentation" <#if item_index==0>class="active" <#assign thePml='${item.name}'/></#if> ><a href=href="javascript:loadPml({'pml':'${item.name}','target':'tabcontent'})" aria-controls="orderSearch" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-search"></span>${item.title}
          <#if item.size??> 	<span class="badge">${item.size}</span>	</#if> 
          </a></li>
          </#list>
        </ul>
          <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id='tabcontent'>
         </div>
        </div>
       </div>
        <script>
			<#if thePml!=''>
				loadPml({'pml':'${thePml}','target':'tabcontent'});
			</#if>
			$("#${model.name}  li").click(
				function(){
					$("#${model.name}  li").removeClass('active');
					$(this).addClass('active');	
				}
			)
</script>