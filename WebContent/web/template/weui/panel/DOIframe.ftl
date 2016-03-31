<iframe  id="if${model.name}"  frameborder='0' src="${resPath}" style="width:${model.paneWidth?default('100%')};"/>
<script>

	var	height = $(window).height();
	var top2 = $(".navbar-static-top").offset().top;
	$("#if${model.name}").css("height",height-top2);
</script>

