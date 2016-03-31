<#macro JudgeIsNull a>
  <#if (a?api.isNotNull())>
  	<font color='red'>*</font>
  </#if>
</#macro>
<#macro JudgeStyle a>

	<#if (a.isOutGridAction?exists && ( a.isOutGridAction == 1)) >
		class='ctlBtn btn btn-default'   <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif (a.style?exists ) >
		class='${a.style} btn btn-default'   <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif  (a.l10n?exists && (a.l10n?contains('新增') || a.l10n?contains('新建') || a.l10n?contains('创建')
	                            || a.l10n?lower_case?contains('create') ||  a.l10n?lower_case?contains('new') ||  a.l10n?contains('insert')
	       ) ) >
		class='new btn btn-default'   <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif (a.l10n?exists && (a.l10n?contains('修改') || a.l10n?contains('编辑')
		                            || a.l10n?lower_case?contains('modify') ||  a.l10n?lower_case?contains('edit')
	
	  	)) >
		class='edit btn btn-default'  <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif (a.l10n?exists &&( a.l10n?contains('删除') 
							|| a.l10n?lower_case?contains('delete') ||  a.l10n?lower_case?contains('remove')
		)) >
		class='delete btn btn-default'  <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif (a.l10n?exists &&( a.l10n?contains('复制') 
							|| a.l10n?lower_case?contains('copy') 
		)) >
		class='copy btn btn-default'  <#compress><@JudgeMobileIcon a/> </#compress>	
	<#elseif (a.l10n?exists && (a.l10n?contains('查看') 
							|| a.l10n?lower_case?contains('browse') 
	)) >
		class='btn-all btn btn-default'  <#compress><@JudgeMobileIcon a/> </#compress>
	<#elseif (a.linkPaneModel?exists && (a.linkPaneModel.cssStyle)?exists ) >
		class='${a.linkPaneModel.cssStyle} btn btn-default'  <#compress><@JudgeMobileIcon a/> </#compress>
	<#else>
	    class='role-setup btn btn-default'	  <#compress><@JudgeMobileIcon a/> </#compress>
	</#if>
</#macro>


<#macro JudgeMobileIcon a>
	<#if (a.image?exists )>
		data-icon='${a.image}' data-theme='c' 
	<#elseif  (a.l10n?exists &&( a.l10n?contains('新增') || a.l10n?contains('新建')
	                          || a.l10n?lower_case?contains('create') ||  a.l10n?lower_case?contains('new') ||  a.l10n?contains('insert')
	       ) ) >
		 data-icon='plus'  data-theme='b'
	<#elseif (a.l10n?exists &&(a.l10n?contains('修改')   
						|| a.l10n?lower_case?contains('modify') ||  a.l10n?lower_case?contains('edit')
	  	)) >
		 data-icon='gear' data-theme='c'
	<#elseif (a.l10n?exists &&(a.l10n?contains('删除')
						|| a.l10n?lower_case?contains('delete') ||  a.l10n?lower_case?contains('remove')
		)) >
		 data-icon='delete' data-theme='c'
	<#elseif (a.l10n?exists &&(a.l10n?contains('查看') 
		|| a.l10n?lower_case?contains('browse') 
		)) >
		 data-icon='info' data-theme='c'
	<#elseif (a.l10n?exists &&(a.l10n?contains('查询')
		|| a.l10n?lower_case?contains('search') 
		)) >
		 data-icon='search' data-theme='b'
	<#elseif (a.l10n?exists &&(a.l10n?contains('保存')
		|| a.l10n?lower_case?contains('save') 
		|| a.l10n?lower_case?contains('store') 
	)) >
		 data-icon='gear' data-theme='b'
	<#else>
	   data-icon='gear' data-theme='c'
	</#if>
</#macro>




<#macro JudgeBootIcon a>
	<#if (a.image?exists )>
		 <i class="${a.image}"></i>
	<#elseif  (a.l10n?exists &&( a.l10n?contains('新增') || a.l10n?contains('新建')
	                          || a.l10n?lower_case?contains('create') ||  a.l10n?lower_case?contains('new') ||  a.l10n?contains('insert')
	       ) ) >
		 <i class="fa fa-plus"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('修改')   
						|| a.l10n?lower_case?contains('modify') ||  a.l10n?lower_case?contains('edit')
	  	)) >
		  <i class="fa fa-edit"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('删除')
						|| a.l10n?lower_case?contains('delete') ||  a.l10n?lower_case?contains('remove') 
						||  a.l10n?lower_case?contains('close') ||  a.l10n?lower_case?contains('关闭')
		)) >
		  <i class="fa fa-remove"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('查看') 
		|| a.l10n?lower_case?contains('browse') 
		)) >
		  <i class="fa fa-info"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('查询')
		|| a.l10n?lower_case?contains('search') 
		)) >
		 <i class="fa fa-search"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('保存')
		|| a.l10n?lower_case?contains('save') 
		|| a.l10n?lower_case?contains('store') 
	)) >
		  <i class="fa fa-save"></i>
 
	<#elseif (a.l10n?exists &&(a.l10n?contains('复制')
		|| a.l10n?lower_case?contains('copy') 
	)) >
		  <i class="fa fa-copy"></i>
	<#elseif (a.l10n?exists &&(a.l10n?contains('导入')
		|| a.l10n?lower_case?contains('import') 
	)) >
		  <i class="fa fa-upload"></i>	  
	<#elseif (a.l10n?exists &&(a.l10n?contains('导出')
		|| a.l10n?lower_case?contains('export') 
	)) >
		  <i class="fa fa-download"></i>	  
	<#else>   
	   
	</#if>
</#macro>


