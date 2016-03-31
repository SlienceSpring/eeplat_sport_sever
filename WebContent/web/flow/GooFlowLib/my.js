(function() {
	var property = {
		// width:1200,
		// height:600,
		toolBtns : [ "activity", "autoNode", "andDecision", "xorDecision",
				"andConjuction", "subFlow", "startNode", "endNode" ],
		haveHead : true,
		headBtns : ["save", "undo", "redo", "reload"],// [ "new", "open",  ],
		haveTool : true,
		haveGroup : false,
		useOperStack : true
	};
	var remark = {
		activity : '人工节点',
		autoNode : "自动节点",
		andDecision : "分支节点",
		xorDecision : "条件节点",
		andConjuction : "汇合节点",
		subFlow : "子流程",
		startNode : "开始节点",
		endNode : "结束节点"
	/*
	 * cursor : "选择指针", direct:"转换连线", start : "开始结点", "end round" : "结束结点",
	 * "task round" : "任务结点", node : "自动结点", chat : "决策结点", state : "状态结点", plug :
	 * "附加插件", fork : "分支结点", "join" : "联合结点", "complex mix" : "复合结点",
	 * group:"组织划分框编辑开关"
	 */
	};
	var gooflowdiv;
	$(document).ready(function() {
		$('#nodepropertydiv').hide();
		$('#linepropertydiv').hide();
		
		property.width = $('.main .left').width();
		property.height = $('.main .left').height();
		gooflowdiv = $.createGooFlow($("#gooflowdiv"), property);
		gooflowdiv.setNodeRemarks(remark);
		loadWfXml();
		$(window).resize(function(){
			gooflowdiv.reinitSize($('.main .left').width(),$('.main .left').height());
		});
		$('#nodepropertydiv input').on('change',function(){
			var nodeid = $('#nodepropertydiv').data('nodeid');
			if(nodeid){
				gooflowdiv.getItemInfo(nodeid,'node')[this.name] = this.value || '';
				gooflowdiv.getItemInfo(nodeid,'node').alt = true;
			}
		});
		$('#linepropertydiv input').on('change',function(){
			var lineid = $('#linepropertydiv').data('lineid');
			if(nodeid){
				gooflowdiv.getItemInfo(lineid,'line')[this.name] = this.value || '';
				gooflowdiv.getItemInfo(lineid,'line').alt = true;
			}
		});
		//console.log(gooflowdiv);
	});
	
	function addEvent(){
		// head
		gooflowdiv.onBtnSaveClick = handleBtnSaveClick;
		
		// item
		gooflowdiv.onItemAdd = handleItemAddEvent;
		gooflowdiv.onItemFocus = handleItemFocusEvent;
	}
	
	function handleItemFocusEvent(id,type){
		var json = gooflowdiv.getItemInfo(id,type);
		if(type == 'node'){
			$('#nodepropertydiv').show();
			$('#linepropertydiv').hide();
			$('#nodepropertydiv').data('nodeid',id);
			_shownode();
		}else if(type == 'line'){
			$('#nodepropertydiv').hide();
			$('#linepropertydiv').show();
			$('#linepropertydiv').data('lineid',id);
			_showline();
		}else{
			$('#nodepropertydiv').hide();
			$('#linepropertydiv').hide();
		}
		
		function _shownode(){
			$('#nodepropertydiv input').each(function(){
				var value = json[this.name];
				if(value){
					this.value = value;
				}else{
					this.value = '';
				}
			});
		}
		
		function _showline(){
			$('#linepropertydiv input').each(function(){
				var value = json[this.name];
				if(value){
					this.value = value;
				}else{
					this.value = '';
				}
			});
		}
		return true;
	}
	function handleItemAddEvent(id,type,json){
		json.id = id;
		return true;
	}
	var issaving = false;
	function handleBtnSaveClick(){
		if(issaving) {
			alert('正在保存!');
			return;
		}
		issaving = true;
		var jsondata = gooflowdiv.exportAlter();
		var jsondatastr = JSON.stringify(jsondata);
		callAction({
			'actionName' : "com.exedosoft.plat.action.wf.DOPTSaveJSON",
			'paras':'jsondata=' + jsondatastr,
			'callback' : cbSave,
			'btn':{}
		});
	}
	function cbSave(data){
		if(data.success == 'success'){
			alert('保存成功!');
		}else{
			alert('保存失败!');
			alert(data.echo_msg);
		}
		issaving = false;
	}
	
	function cbXml(data) {
		try{
			gooflowdiv.loadData(JSON.parse(data.jsonstr));
			gooflowdiv.onFreshClick = function(){
				gooflowdiv.clearData();
				gooflowdiv.loadData(JSON.parse(data.jsonstr));
			};
			addEvent();
		}catch(e){
			alert(e);
		}
	}
	function loadWfXml() {
		callAction({
			'actionName' : "com.exedosoft.plat.action.wf.DOPTReadJSON",
			'callback' : cbXml
		});
	}
})();