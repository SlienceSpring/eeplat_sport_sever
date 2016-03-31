<script type="text/javascript" src="web/default/js/main/raphael-min.js"  ></script>
<script type="text/javascript" src="web/flow/monitor.js"></script>
<style type="text/css">
	#canvas {
	    height: 1000px;
	    margin: 0 auto;
	    text-align: left;
	    width: 680px;
	}
</style>

 <div id="canvas"></div>

<script>
    baseFlowImage="web/flow/"; 
	var paper = Raphael("canvas");
	loadWfMonitor(paper);


  
</script>