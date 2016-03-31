/*****************************************主页面框架代码******************************************/
var position = "last"; //tab页显示顺序，first是显示在前面，last是显示在后面
var isHome = 1; //是否有首页   有是1   没有是0
var globalService = globalURL + 'servicecontroller';
var globalPml= globalURL + 'mvccontroller';
var baseFlowImage="";
var ep = {};

///修复jquery clone bug
(function (original) {
	jQuery.fn.clone = function () {
	var result = original.apply(this, arguments),
	my_textareas = this.find('textarea').add(this.filter('textarea')),
	result_textareas = result.find('textarea').add(result.filter('textarea')),
	my_selects = this.find('select').add(this.filter('select')),
	result_selects = result.find('select').add(result.filter('select'));
	 
	for (var i = 0, l = my_textareas.length; i < l; ++i) $(result_textareas[i]).val($(my_textareas[i]).val());
	for (var i = 0, l = my_selects.length; i < l; ++i) result_selects[i].selectedIndex = my_selects[i].selectedIndex;
	 
	return result;
	};
	}) (jQuery.fn.clone);

/**
 * jQuery JSON plugin v2.5.1
 * https://github.com/Krinkle/jquery-json
 *
 * @author Brantley Harris, 2009-2011
 * @author Timo Tijhof, 2011-2014
 * @source This plugin is heavily influenced by MochiKit's serializeJSON, which is
 *         copyrighted 2005 by Bob Ippolito.
 * @source Brantley Harris wrote this plugin. It is based somewhat on the JSON.org
 *         website's http://www.json.org/json2.js, which proclaims:
 *         "NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.", a sentiment that
 *         I uphold.
 * @license MIT License <http://opensource.org/licenses/MIT>
 */
(function ($) {
	'use strict';

	var escape = /["\\\x00-\x1f\x7f-\x9f]/g,
		meta = {
			'\b': '\\b',
			'\t': '\\t',
			'\n': '\\n',
			'\f': '\\f',
			'\r': '\\r',
			'"': '\\"',
			'\\': '\\\\'
		},
		hasOwn = Object.prototype.hasOwnProperty;

	/**
	 * jQuery.toJSON
	 * Converts the given argument into a JSON representation.
	 *
	 * @param o {Mixed} The json-serializable *thing* to be converted
	 *
	 * If an object has a toJSON prototype, that will be used to get the representation.
	 * Non-integer/string keys are skipped in the object, as are keys that point to a
	 * function.
	 *
	 */
	$.toJSON = typeof JSON === 'object' && JSON.stringify ? JSON.stringify : function (o) {
		if (o === null) {
			return 'null';
		}

		var pairs, k, name, val,
			type = $.type(o);

		if (type === 'undefined') {
			return undefined;
		}

		// Also covers instantiated Number and Boolean objects,
		// which are typeof 'object' but thanks to $.type, we
		// catch them here. I don't know whether it is right
		// or wrong that instantiated primitives are not
		// exported to JSON as an {"object":..}.
		// We choose this path because that's what the browsers did.
		if (type === 'number' || type === 'boolean') {
			return String(o);
		}
		if (type === 'string') {
			return $.quoteString(o);
		}
		if (typeof o.toJSON === 'function') {
			return $.toJSON(o.toJSON());
		}
		if (type === 'date') {
			var month = o.getUTCMonth() + 1,
				day = o.getUTCDate(),
				year = o.getUTCFullYear(),
				hours = o.getUTCHours(),
				minutes = o.getUTCMinutes(),
				seconds = o.getUTCSeconds(),
				milli = o.getUTCMilliseconds();

			if (month < 10) {
				month = '0' + month;
			}
			if (day < 10) {
				day = '0' + day;
			}
			if (hours < 10) {
				hours = '0' + hours;
			}
			if (minutes < 10) {
				minutes = '0' + minutes;
			}
			if (seconds < 10) {
				seconds = '0' + seconds;
			}
			if (milli < 100) {
				milli = '0' + milli;
			}
			if (milli < 10) {
				milli = '0' + milli;
			}
			return '"' + year + '-' + month + '-' + day + 'T' +
				hours + ':' + minutes + ':' + seconds +
				'.' + milli + 'Z"';
		}

		pairs = [];

		if ($.isArray(o)) {
			for (k = 0; k < o.length; k++) {
				pairs.push($.toJSON(o[k]) || 'null');
			}
			return '[' + pairs.join(',') + ']';
		}

		// Any other object (plain object, RegExp, ..)
		// Need to do typeof instead of $.type, because we also
		// want to catch non-plain objects.
		if (typeof o === 'object') {
			for (k in o) {
				// Only include own properties,
				// Filter out inherited prototypes
				if (hasOwn.call(o, k)) {
					// Keys must be numerical or string. Skip others
					type = typeof k;
					if (type === 'number') {
						name = '"' + k + '"';
					} else if (type === 'string') {
						name = $.quoteString(k);
					} else {
						continue;
					}
					type = typeof o[k];

					// Invalid values like these return undefined
					// from toJSON, however those object members
					// shouldn't be included in the JSON string at all.
					if (type !== 'function' && type !== 'undefined') {
						val = $.toJSON(o[k]);
						pairs.push(name + ':' + val);
					}
				}
			}
			return '{' + pairs.join(',') + '}';
		}
	};

	/**
	 * jQuery.evalJSON
	 * Evaluates a given json string.
	 *
	 * @param str {String}
	 */
	$.evalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse : function (str) {
		/*jshint evil: true */
		return eval('(' + str + ')');
	};

	/**
	 * jQuery.secureEvalJSON
	 * Evals JSON in a way that is *more* secure.
	 *
	 * @param str {String}
	 */
	$.secureEvalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse : function (str) {
		var filtered =
			str
			.replace(/\\["\\\/bfnrtu]/g, '@')
			.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
			.replace(/(?:^|:|,)(?:\s*\[)+/g, '');

		if (/^[\],:{}\s]*$/.test(filtered)) {
			/*jshint evil: true */
			return eval('(' + str + ')');
		}
		throw new SyntaxError('Error parsing JSON, source is not valid.');
	};

	/**
	 * jQuery.quoteString
	 * Returns a string-repr of a string, escaping quotes intelligently.
	 * Mostly a support function for toJSON.
	 * Examples:
	 * >>> jQuery.quoteString('apple')
	 * "apple"
	 *
	 * >>> jQuery.quoteString('"Where are we going?", she asked.')
	 * "\"Where are we going?\", she asked."
	 */
	$.quoteString = function (str) {
		if (str.match(escape)) {
			return '"' + str.replace(escape, function (a) {
				var c = meta[a];
				if (typeof c === 'string') {
					return c;
				}
				c = a.charCodeAt();
				return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
			}) + '"';
		}
		return '"' + str + '"';
	};

}(jQuery));


function createNewTab(menuId,menuName,paneUrl,isMenu){
	
	
	if($('#' + menuId).size() > 0){
		$('#_opener_tab').tabs('select', menuName);
	}else{
		$('#_opener_tab').tabs('add',{
			id:menuId,
		    title:menuName,
		    href:paneUrl,
		    closable:true
		});
	}
	
}



function closeWin(){
//	$("#fullBg").css("display","none");
//	$(".alertClose").parent("#msg").css("display","none");
	try{
		$("#main_msg").remove();
		$("#fullBg").remove();
	}catch(e){
		
	}	
}
/*****************************************遮罩层***************************************************/
/*
position:遮罩层显示在哪个区域，例如显示在aa  div里面：#aa 就可以
msgW:弹出框的宽
msgH:弹出框的高
align:显示位置  left,right,center  目前有三个值
type:类型，loading和loadingImg,登录现在用的是loading,loadingImg是一个图片，代表加载内容.如果自定义则给空值.
content:弹出框里面得内容，自定义
isClose:是否有关闭按钮
*/
//开启遮罩

function loading(aMsg,position){

    var aPos = "body";
    if(position){
    	aPos = position;
    }
    var msgLen = 150;
    if(aMsg){
    	 msgLen = aMsg.length * 15 ;
    }
	if(aMsg!=null){
		showMainMsg(aPos,msgLen,16,"center","sef_defined","<div>&nbsp;&nbsp;" +aMsg + "</div>","n");
	}else{
		showMainMsg(aPos,msgLen,16,"center","loading","","n");
	}	

}

function showMainMsg(position,msgW,msgH,align,type,content,isClose){
	
	if($("#main_msg").size() == 0){
		$("body").append("<DIV id=fullBg style='z-index: 199999;position:absolute'></DIV><DIV style='z-index: 299999;margin:0;padding:2px;' id=main_msg></DIV>");
	}else{
		$("#main_msg").empty();
	}
	if(type=="loading"){
		content="<div >&nbsp;&nbsp;" + EELang.loading + "</div>";
	}else if(type=="loadingImg"){
		content= "<div class=index-loading></div>";
	}
	
	
	if(align=="left"){
		$("#main_msg").css({top:$(position).offset().top,left:$(position).offset().left,width:msgW,height:msgH});
	}else if(align=="center"){
		$("#main_msg").css({top:$(position).offset().top+($(position).height()-msgH)/2,left:$(position).offset().left+($(position).width()-msgW)/2,width:msgW,height:msgH});
	}else if(align=="right"){
		$("#main_msg").css({top:$(position).offset().top,left:$(position).offset().left+$(position).width()-msgW-5,width:msgW,height:msgH});
	}
	
	
	$("#fullBg").css({top:$(position).offset().top,left:$(position).offset().left,width:$(position).width(),height:$(position).height()});
	//显示关闭按钮
	if(isClose=="y"||isClose=="Y"){
		var closeImg="<div class='alertClose'></div>";
		$("#main_msg").append(closeImg);
	}
	
	$("#main_msg").append(content);
	
	//关闭按钮事件
	if(isClose=="y"||isClose=="Y"){
		$(".alertClose").bind("click",function(){
			closeWin();
		})
	}
}



/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
/*****************************编码处理***************************************************************/
//UrlCode 处理代码
function urlCodeDeal(str){
	if(str==null || str.length==0){
		return "";
	}
	var paras = new Array();
	paras = str.split('&');
	var result ="";
	for(var i = 0; i < paras.length; i++){
	    var name_V =   new Array();
	    name_V = paras[i].split('=');
	    if(i==0){
	    	result += name_V[0]+"=";
	    }else{
	    	result +="&" + name_V[0]+"=";
	    }
	    if(name_V.length>1){
	    	//之前空格被用+替换了, 参数中加号用空格替换回来 
	    	result += encodeURIComponent(escape(decodeURIComponent(name_V[1].split("+").join("%20"))));  
	    }
	}
	return result;
}

function pageSplitDeal(str,pageNo,pageSize){
	if(pageNo==null || pageSize==null){
		return "";
	}
	if(str == null || str==undefined){
		str = "";
	}
	if(/pageNo=[0-9]*/gi.test(str)){
		str = str.replace(/pageNo=[0-9]*/gi,"pageNo=" + pageNo);
	}else{
		str = str + "&pageNo=" + pageNo;
	}
	if(/pageSize=[0-9]*/gi.test(str)){
		str = str.replace(/pageSize=[0-9]*/gi,"pageSize=" + pageSize);
	}else{
		str = str + "&pageSize=" + pageSize;
	}
	return str;

}


/*****************************************弹出层代码******************************************/
function popupDialog(id,title,href,width,height){


	    if(width==null || width==""){
	    	width = 700;
	    }
	    if(height==null || height==""){
	    	height = 400;
	    }
	    
	    if($('#F' + id).size()==0){
			createFloatDiv(id,title);
	    }

		
		$('#F' + id).dialog({
			autoOpen: false,
			height: height,
			width: width,
			modal: true,
			resizable:true,
			href:href,
			close: function(event, ui) {
				 $("#dmLayer").hide();
				 $('#F' + id).empty();
			}
		}); 

		$('#F' + id).load(href);
		$( '#F' + id ).dialog( "open" );
}

function createFloatDiv(id,title) {

    var htmlStr = "<div id='F" + id  + "' title='"
	+ title + "'></div> \n";
	$(document.body).append(htmlStr); 
}



/**
 * 刷新树 
 * @param type =1 刷新 选中的节点，否则刷新 选中节点的上层
 * @return
 */
function reloadTree(type){
	  try{
		if(webFXTreeHandler==null || webFXTreeHandler.selected==null){
			return;
		}
		oldSelectName = webFXTreeHandler.selected.text;
	    if(type == 1){  
	    	if(webFXTreeHandler.selected.src!=null && webFXTreeHandler.selected.src!=''){
	    			webFXTreeHandler.selected.reload();
	    	}
	    }else{
	    	
//	    	if(webFXTreeHandler.selected.src!=null && webFXTreeHandler.selected.src!=''){
//    			webFXTreeHandler.selected.reload();
//	    	}
	    	
	    	if(webFXTreeHandler.selected.parentNode.src!=null && webFXTreeHandler.selected.parentNode.src!=''){
	    		webFXTreeHandler.selected.parentNode.reload();
	    	}else{
	    		if(webFXTreeHandler.selected.parentNode.parentNode.src!=null
	    				&& webFXTreeHandler.selected.parentNode.parentNode.src!=''){
	    			webFXTreeHandler.selected.parentNode.parentNode.reload();
	    		}	
	    	}
	    }
	  }catch(e){
	  
	  }
}


/**
 * is mobile
 */

function isMobile(){
	

	// If the screen orientation is defined we are in a modern mobile OS
	var mobileOS = typeof orientation != 'undefined' ? true : false;
	
	if(mobileOS){
		return true;
	}
	
	var android = (navigator.platform.indexOf("android")>=0);
	
	var iPhone = (navigator.platform.indexOf("iPhone")>=0);
	
	var iPod = (navigator.platform.indexOf("iPod")>=0);
	
	var iPad = (navigator.platform.indexOf("iPad")>=0);
	
	var symbian = (navigator.platform.indexOf("symbian")>=0);
	
	var series60 = (navigator.platform.indexOf("series60")>=0);	
	
	var BlackBerry = (navigator.platform.indexOf("BlackBerry")>=0);	


	return android || iPhone || iPod || iPad || symbian || series60 || BlackBerry;
}



/**
 * 工作流节点权限相关
 */
function insertAuthPt(){
	var selectedNode = window.opener.selectedNodeBak;
	var whatuid = selectedNode.getAttribute('id');
	var ouuid = $("#gm_do_authorization_insert_ptnode_role_ouuid").val();
	callService({'serviceName':'u_role_ptnode',paras:"whatuid=" + whatuid + "&ouuid="+ouuid,'pml':'PM_do_org_role_of_ptnode','target':'PM_do_org_role_of_ptnode'}  );
	
	$('#FPM_do_authorization_insert_ptnode_role').jqmHide();
	
}

/**
 * 代码编辑相关
 */
function insertAceCode(){
	
	var name = $("input[name=propertyuid]").val();
	
	if(name==null || name==''){
		alert(EELang.nameRequired);
		return;
	}
	var isValid = 1;
	var validChecks = $("input[name=icon]:checked");
	if(validChecks.length > 0){
		isValid = $("input[name=icon]:checked").val();
	}
	
	var theCode  = '';
	var hidden_type = $("input[name=mVersion]").val();
	
	if(hidden_type==null){
		hidden_type = 'js';
	}
	
	if($.browser.msie){
		
	    if(mirrorEditor){
		    mirrorEditor.save();
		    theCode = mirrorEditor.getValue();
	    }else   if(mirrorEditor2){
	    	mirrorEditor2.save();
	    	theCode = mirrorEditor2.getValue();
	    }
	}else{
		
		console.log("hidden_type::" + hidden_type);
		
		if(hidden_type=='js'){
			theCode = jsEditor.getSession().getValue();
		}else if(hidden_type=='css'){
			theCode = cssEditor.getSession().getValue();
		}else if(hidden_type=='rhino'){
			theCode = rhinoEditor.getSession().getValue();
		}else if(hidden_type=='html'){
			theCode = htmlEditor.getSession().getValue();
		}
	}
	
	
	var objuid = $("input[name=objuid]").val();
	

	var serviceName = "DO_BO_Icon_Insert";
	if(objuid){
		serviceName = "DO_BO_Icon_Update";
	}
	callService({'serviceName':serviceName,'callType':'uf', 'callback':alert(EELang.saveSuccess), 
		paras:"propertyuid=" + name + "&formulascript=" + encodeURIComponent(theCode) + "&icon=" + isValid + "&mVersion="+hidden_type}  );
}


function  confirmDelete(){
	  
	return confirm(EELang.confirmDelete);
}

function  confirmDeleteWithGrid(){
	  
	return confirm(EELang.confirmDeleteAndGrid);
}




function  confirmModify(){
	  
	return confirm(EELang.confirmModfiy);
}

function confirmShare(){
	
	return confirm(EELang.confirmShare);
	
}

function confirmSetup(){
	
	return confirm(EELang.confirmSetup);
}

function confirmCopy(){
	
	return confirm(EELang.confirmCopy);
}


function confirmInit(){
	
	return confirm(EELang.confirmInit);
}

function confirmGenerate(){
	
	return confirm(EELang.confirmGenerate);
}

function confirmServiceToRule(){
	
	return  confirm(EELang.confirmServiceToRule);
}

function confirmImport(){
	return confirm(EELang.confirmImport);
}

function confirmRemove(){
	
	return confirm(EELang.confirmRemove);
}
function toDecimal2(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {    
        return false;    
    }    
    var f = Math.round(x*100)/100;    
    var s = f.toString();    
    var rs = s.indexOf('.');    
    if (rs < 0) {    
        rs = s.length;    
        s += '.';    
    }    
    while (s.length <= rs + 2) {    
        s += '0';    
    }    
    return s;    
}  

function labelFormatter(label, series) {
	return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
}

function generateUUID(){
    var d = new Date().getTime();
    var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (d + Math.random()*16)%16 | 0;
        d = Math.floor(d/16);
        return (c=='x' ? r : (r&0x7|0x8)).toString(16);
    });
    return uuid;
}

function  resourceChange(obj){

	if(obj){
		
		if(obj.value == 32){
			$(obj).siblings("span").css("color","red").html("&nbsp;&nbsp;height:41px");
		}

		else if(obj.value == 33){
			$(obj).siblings("span").css("color","red").html("&nbsp;&nbsp;height:120px");
		}

		else if(obj.value == 35){
			$(obj).siblings("span").css("color","red").html("&nbsp;&nbsp;height:200px");
		}

		else if(obj.value == 36){
			$(obj).siblings("span").css("color","red").html("&nbsp;&nbsp;Size:425X114(px)");
		}

		else{
			$(obj).siblings("span").css("color","red").html("");
		}
	}
}



function  gridCheck(isCheck,o,selector,e){
	
		
	if(!isCheck){
		$(o).parent().parent().removeClass("selected");
		if($(selector +  ' .selected').size()==0){
			$(selector + ' .list_check:checked:first').parent().parent().addClass("selected");				
		}
		if( $(o).attr("eeplatselected")){
			console.log("unselected!");
			 $(o).addClass("removevaluee");				
		}else{
			 $(o).removeClass("addvaluee");				
		}				
		e.stopPropagation();
	}
	else{
		if( $(o).attr("eeplatselected") == null ){
			console.log("selected!");
			 $(o).addClass("addvaluee");				
		}else{
			$(o).removeClass("removevaluee");
		}
	}

}


var O2String = function (O) {
	return $.toJSON(O);
};

function toggleMore(obj){

    var a = $(obj).parent().parent().nextAll(":not(.buttonMore)");
	a.toggle(a.css('display') == 'none');
	var html = $(obj).text()==EELang.more ? "<b>" + EELang.less +"</b>" : "<b>" +  EELang.more  + "</b>";
	$(obj).html(html);
}

function judgeSave(){
	
	  var isFailure = 0;
	    $.ajax({
			type : "GET",
			url : globalService + "?dataBus=getGlobalContext&r=" +  Math.random() ,
			async : false,
	        success : function(b, textStatus) {
	              try{
	                    var i1w = eval("b.a" + $("#gm_do_resource_change_logo_resourcepath").data("uuid")+"_w");
	                    var i1h = eval("b.a" + $("#gm_do_resource_change_logo_resourcepath").data("uuid")+"_h");
	                    var i2w = eval("b.a" + $("#loginimage").data("uuid")+"_w");
	                    var i2h = eval("b.a" + $("#loginimage").data("uuid")+"_h");
	                    
	                    if((i1w == undefined && i1h == undefined) &&
	                    (i2w == undefined && i2h == undefined)){
	                        isFailure = -1;
	                        console.log("Error::i2w == undefined && i2h == undefined" );
	                        return ;
	                    }
	                      
	                    if(i1h>41 || i1h<=30){
	                        isFailure = 1;
	                    }
	        
	                    if(i2h>180 || i2h<100){
	                        isFailure = 1;
	                    }
	                    if(i2w > 450 || i2w < 400){
	                        isFailure = 1;
	                    }
	              }catch(e){
	                  isFailure = -1;
	                  console.log("Error::" + e);
	                  return;
	              }

	        }
		});

	    if(isFailure == -1){
	        return false;
	    } 
	    if(isFailure == 1){
	        alert(EELang.logoEcho);
	        return false;
	    }
	   return true;
	}

////判断是否存在ShareID ，如果不存在则弹出录入框。
	function judgeShareID(){
		$.ajax({
			type : "get",
			url : globalUrl + "servicecontroller?dataBus=getGlobalContext",
			async : false,
			success: function(data, textStatus) {
				if(data.sharesecret == null){
				    loadPml({
					    'title':EELang.shareAppConfirm,
					    'pml':'appshare_enter_login',
					    'target':'_opener'
					        }
					);
				}
			}
		});
	    
	}

	
	function checkBracket(algorithms){    

	       var bracket = [];
	       var algorithms= algorithms ||'';
	       for(var i=0; i<algorithms.length; i++){
	        if(algorithms.charAt(i)=="("){
	         bracket.push('(');
	        }
	        if(algorithms.charAt(i)==")"){
	         if(bracket.length){
	          bracket.pop();
	         }else{
	          console.log('公式括号不配对，缺少左括号！')
	          return false;
	         }
	        }
	       }
	       if(bracket.length){
	        console.log('公式括号不配对，缺少右括号！')
	        return false;
	       }

	       return true;
	}
	
	ep.chartConfig = function(){
		
		var isValid = $("#a40289f0b4cda05f2014cda05f2af0000,#a40289f0b4d1ddda2014d1ddda2db0000").valid();
		
		
		if(!isValid){
			return;
		}
		var values = {};
		values.name = $("#gm_chart_config_name,#gm_chart_config_edit_name").val();
		values.l10n = $("#gm_chart_config_l10n,#gm_chart_config_edit_l10n").val();
		values.serviceUid = $("#gm_chart_config_serviceuid,#gm_chart_config_edit_serviceuid").val();
		values.chartType = $("table.chooseChartType tr.selected").attr("icon_");
		values.xaxisCol = $("#xaxiscol").val();
		values.yaxisCol = $("#yaxiscol").val();
		values.categoryCol = $("#categorycol").val();
		values.mainText = $("#maintext").val();
		values.subText = $("#subtext").val();
		values.otheroptions = $("#otheroptions").val();
		values.gridModelName = $("#gridModelName").val();
		values.bgcolor = $("#bgcolor").val();
		values.boxicon = $("#boxicon").val();
		values.linkPaneId = $("#linkPane").val();
		values.height = $("#height").val();
		values.width = $("#width").val();
		
		
		if(	values.xaxisCol=="" ){
			alert("Please select name(xAxis) column!");
			return ;
		}
		
		if(	values.yaxisCol=="" ){
			alert("Please select value(yAxis) column!");
			return;
		}
		
		
	  	if( (values.chartType=='bar2' || values.chartType=='line2') &&  values.categoryCol=="") {
	  		alert("Please select category column!");
	  		return;
      	}

	  	if( (values.chartType=='box') &&  values.linkPaneId=="") {
	  		alert("Please select box linked pane!");
	  		return;
      	}

	  	
	  	if( (values.chartType=='box') &&  values.boxicon=="") {
	  		alert("Please input box icon!");
	  		return;
      	}
	  	
	  	console.log("values::" + O2String(values));

	  	
	  	
	 	callAction({
			'actionName':"com.exedosoft.plat.action.customize.tools.DOSaveChartConfig",
	   		'callback':function(data){
	   			var target = 'PM_Chart_Config';
	   			if($('#PM_Chart_Config').size()==0){
	   				target = 'PM_Chart_Config_Edit';
	   			}
	   			loadPml({'pml':'PM_DO_UI_GridModel_chart_list','target':target});
	   		},
	   		'paras': "paras="+encodeURIComponent(escape(O2String(values))) 


		});

		
	}
	
	ep.conditonStatus = function(selectStr){
		
		
   		if($(selectStr).find('option:selected').parent().length > 0 &&  $(selectStr).find('option:selected').parent().attr('stag') == 'cust'){
   	    	
   			$('#extendModifyView').removeClass('extendadisable').addClass('extenda');
   			$('#extendDeleteView').removeClass('extendadisable').addClass('extenda');
   			$('#extendCopyView').removeClass('extendadisable').addClass('extenda');
   		}else{
   			$('#extendModifyView').removeClass('extenda').addClass('extendadisable');
   			$('#extendDeleteView').removeClass('extenda').addClass('extendadisable');
   			$('#extendCopyView').removeClass('extenda').addClass('extendadisable');
   		}
	}
	
	ep.reportConfig = function(){
		
		console.log("Report Config======================");
		
		var values = {};
		values.gridType = $("#gridType").val();
		values.group1 = $("#group1").val();
		values.sort1 = $("#sort1").val();
		values.issum1 = $("#issum1").val();
		
		values.group2 = $("#group2").val();
		values.sort2 = $("#sort2").val();
		values.issum2 = $("#issum2").val();
		
		
		if(values.gridType=='3'){
			values.groupcol = $("#groupcol").val();
			values.sortcol = $("#sortcol").val();
			values.grouprow = $("#grouprow").val();
			values.sortrow = $("#sortrow").val();
			values.crosstabvalue = $("#crosstabvalue").val();
			values.sumtype = '';
			var sumChecks = $("#a40289f0b4d2735f4014d2738607c0005 input:checked"); 
			sumChecks.each(
				function(i,o){
					values.sumtype= values.sumtype + $(o).val();
					if(i < (sumChecks.size()-1)){
						values.sumtype = values.sumtype + ';';
					}
				}
			);
			
			console.log("sumtype:::" + values.sumtype );
			
			if(values.groupcol=="" || values.grouprow=="" || values.crosstabvalue==""){
				$("#grouprow").focus();
				alert(EELang.required);
				return;
			}
			
		}else{
			values.group3 = $("#group3").val();
			values.sort3 = $("#sort3").val();
			values.issum3 = $("#issum3").val();
			if(values.group1==""){
				$("#group1").focus();
				alert(EELang.required);
				return;
			}
		}
	  	
		
		console.log("values::::::" + O2String(values));
		
	 	callAction({
			'actionName':"com.exedosoft.plat.action.customize.tools.DOSaveReportConfig",
	   		'paras': "paras="+encodeURIComponent(escape(O2String(values))) 

		});

	}	
