        <div id="${model.name}" class='row'>
          <div class='col-xs-12'>
            <div class="box boxextend">
            <#assign thePml=''/>
            <ul class="nav nav-pills">
            	<#list items as item>
                	<li role="presentation"  <#if item_index==0>class='active' <#assign thePml='${item.name}'/></#if>  ><a href="javascript:loadPml({'pml':'${item.name}','target':'${model.name}tabcontent'})"><span>${item.title}</span>
                	<#if item.size??> 	<span class="badge">${item.size}</span>	</#if> </a>
                		
                	</li>
                </#list>
            </ul>
            <div id='${model.name}tabcontent'>
            
            </div>
            </div>
           </div> 
        </div>
        <script>
			<#if thePml!=''>
				loadPml({'pml':'${thePml}','target':'${model.name}tabcontent'});
			</#if>
			$("#${model.name}  li").click(
				function(){
					$("#${model.name}  li").removeClass('active');
					$(this).addClass('active');	
				}
			)
        </script>
