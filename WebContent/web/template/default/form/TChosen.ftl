${htmlcode}
<script>
	  $( "#${model.fullColID}" ).chosen({width:"60%"}).change(function(){
		var selection = $('#${model.fullColID}').getSelectionOrder();
		console.log("selection::" + selection);
		}
	  );	
</script>
