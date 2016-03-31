<a  id='${model.objUid}${model.data.uid}' data-role="button"   href='#' value="${model.l10n}">${model.l10n}</a>
<script>
      console.log("chartPml:::${chartPml}");
	  $('#${model.objUid}${model.data.uid}').bind('click',function(){
	 		window.open('${chartPml}.pml?isApp=true','_opener');
	  });
</script>