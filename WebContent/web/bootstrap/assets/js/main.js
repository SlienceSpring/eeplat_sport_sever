/** ***************************************主页面框架代码***************************************** */
var isMobi = isMobile();// /是否为手机环境
var position = "first"; // tab页显示顺序，first是显示在前面，last是显示在后面
var isHome = 1; // 是否有首页 有是1 没有是0
var globalService = globalURL + 'servicecontroller';
var globalPml = globalURL + 'mvccontroller';
var workbenchPath = "";
var ep = {};

function loadWorkbench(path) {

	if ($("#tab_workbench_container").size() > 0) {
		$(".mRight").hide();
		$("#tab_workbench_container").show();
	} else {
		if (path == null || path == undefined || path == "") {
			return;
		}
		workbenchPath = path;
		$("#mRight").clone().attr("id", 'tab_workbench_container').insertAfter(
				"#mRight");
		$("#tab_workbench_container").load(globalURL + workbenchPath);
		$("#mRight").hide();
		$("#tab_workbench_container").show();

		// $("#mRight").load(globalURL + "web/default/workbench/workbench.jsp");
	}

	$(".gTab .oneTab").removeClass("on");
	$(".oneTab[tabid='workbench_container']").addClass("on");
}

function closeWin() {
	// $("#fullBg").css("display","none");
	// $(".alertClose").parent("#msg").css("display","none");
	try {
		$("#main_msg").remove();
		$("#fullBg").remove();
	} catch (e) {

	}
}
/** ***************************************遮罩层************************************************** */
/*
 * position:遮罩层显示在哪个区域，例如显示在aa div里面：#aa 就可以 msgW:弹出框的宽 msgH:弹出框的高 align:显示位置
 * left,right,center 目前有三个值
 * type:类型，loading和loadingImg,登录现在用的是loading,loadingImg是一个图片，代表加载内容.如果自定义则给空值.
 * content:弹出框里面得内容，自定义 isClose:是否有关闭按钮
 */
// 开启遮罩
function loading(aMsg, position) {

	var aPos = "body";
	if (position) {
		aPos = position;
	}
	var msgLen = 150;
	if (aMsg) {
		msgLen = aMsg.length * 15;
	}
	if (aMsg != null) {
		showMainMsg(aPos, msgLen, 16, "center", "sef_defined",
				"<div>&nbsp;&nbsp;" + aMsg + "</div>", "n");
	} else {
		showMainMsg(aPos, msgLen, 16, "center", "loading", "", "n");
	}

}

function showMainMsg(position, msgW, msgH, align, type, content, isClose) {

	if ($("#main_msg").size() == 0) {
		$("body")
				.append(
						"<DIV id=fullBg style='z-index: 199999;position:absolute'></DIV><DIV style='position: fixed;z-index: 299999;margin:0;padding:2px;' id=main_msg></DIV>");
	} else {
		$("#main_msg").empty();
	}
	// if(type=="loading"){
	content = "<div > <img src='" + globalURL
			+ "web/default/images/ajax-loader.gif'></img></div>";
	// }else if(type=="loadingImg"){
	// content= "<div class=index-loading></div>";
	// }

	if (align == "left") {
		$("#main_msg").css({
			top : $(position).offset().top,
			left : $(position).offset().left,
			width : msgW,
			height : msgH
		});
	} else if (align == "center") {
		$("#main_msg").css(
				{
					top : $(position).offset().top
							+ ($(position).height() - msgH) / 2,
					left : $(position).offset().left
							+ ($(position).width() - msgW) / 2,
					width : msgW,
					height : msgH
				});
	} else if (align == "right") {
		$("#main_msg").css({
			top : $(position).offset().top,
			left : $(position).offset().left + $(position).width() - msgW - 5,
			width : msgW,
			height : msgH
		});
	}

	$("#fullBg").css({
		top : $(position).offset().top,
		left : $(position).offset().left,
		width : $(position).width(),
		height : $(position).height()
	});
	// 显示关闭按钮
	if (isClose == "y" || isClose == "Y") {
		var closeImg = "<div class='alertClose'></div>";
		$("#main_msg").append(closeImg);
	}

	$("#main_msg").append(content);

	// 关闭按钮事件
	if (isClose == "y" || isClose == "Y") {
		$(".alertClose").bind("click", function() {
			closeWin();
		})
	}
}

// ///////////////////////////////////////////////////////////////////////
/** ***************************编码处理************************************************************** */
// UrlCode 处理代码
function urlCodeDeal(str) {
	if (str == undefined || str.length == 0) {
		return "";
	}
	var paras = new Array();
	paras = str.split('&');
	var result = "";
	for (var i = 0; i < paras.length; i++) {
		var name_V = new Array();
		name_V = paras[i].split('=');
		if (name_V[0] == '') {
			continue;
		}
		if (i == 0) {
			result += name_V[0] + "=";
		} else {
			result += "&" + name_V[0] + "=";
		}
		if (name_V.length > 1) {
			// 之前空格被用+替换了, 参数中加号用空格替换回来
			result += encodeURIComponent(escape(decodeURIComponent(name_V[1]
					.split("+").join("%20"))));
		}
	}
	return result;
}

function pageSplitDeal(str, pageNo, pageSize) {

	if (pageNo == null && pageSize == null) {
		return str;
	}
	if (str == null || str == undefined) {
		str = "";
	}
	if (pageNo) {
		if (/pageNo=[0-9]*/gi.test(str)) {
			str = str.replace(/pageNo=[0-9]*/gi, "pageNo=" + pageNo);
		} else {
			str = str + "&pageNo=" + pageNo;
		}
	}
	if (pageSize) {
		if (/pageSize=[0-9]*/gi.test(str)) {
			str = str.replace(/pageSize=[0-9]*/gi, "pageSize=" + pageSize);
		} else {
			str = str + "&pageSize=" + pageSize;
		}
	}
	return str;

}

/** ***************************************弹出层代码***************************************** */
function popupDialog(id, title, href, width, height,callback) {

	if (title == undefined) {
		title = "";
	}
	if ($('#F' + id).size() == 0) {
		createFloatDiv(id, title);
	} else {
		if (title) {
			$('#F' + id).find("modal-title").html(title);
		}
	}
	// //////加载content
	$('#' + id).empty().load(href + "&eeplatdialog=true",function() {
		if (callback) {
			callback();
		}
	});
	$('#F' + id).modal('show');

}

function createFloatDiv(id, title) {

	var htmlStr = "<div id='F"
			+ id
			+ "' class='modal fade' role='dialog' aria-labelledby='gridSystemModalLabel' aria-hidden='true'> \n";

	htmlStr = htmlStr + "<div class='modal-dialog modal-lg'> \n";
	htmlStr = htmlStr + " <div class='modal-content'> \n";
	htmlStr = htmlStr + "  <div class='modal-header'>\n";
	htmlStr = htmlStr
			+ "    <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>\n";
	htmlStr = htmlStr + "    <h4 class='modal-title' >" + title + "</h4>\n";
	htmlStr = htmlStr + "  </div>\n";
	htmlStr = htmlStr + "  <div class='modal-body' id='" + id + "'>";
	;
	htmlStr = htmlStr + "  </div> \n";
	htmlStr = htmlStr + "  </div>\n";
	htmlStr = htmlStr + "</div>\n</div>\n";

	$(document.body).append(htmlStr);
}

/**
 * 刷新树
 * 
 * @param type
 *            =1 刷新 选中的节点，否则刷新 选中节点的上层
 * @return
 */
function reloadTree(type) {
	try {
		if (webFXTreeHandler == null || webFXTreeHandler.selected == null) {
			return;
		}
		oldSelectName = webFXTreeHandler.selected.text;
		var isLoad = false;
		if (type == 1) {
			var theSrc = webFXTreeHandler.selected.src;
			if (theSrc != null && theSrc != '') {
				isLoad = true;
				webFXTreeHandler.selected.open = true;
				webFXTreeHandler.selected.reload();
			} else {
				if (webFXTreeHandler.selected.baksrc) {
					isLoad = true;
					webFXTreeHandler.selected.open = true;
					webFXTreeHandler.selected
							.setSrc(webFXTreeHandler.selected.baksrc);
				}
			}
		}
		if (!isLoad) {
			if (webFXTreeHandler.selected.parentNode.src != null
					&& webFXTreeHandler.selected.parentNode.src != '') {
				webFXTreeHandler.selected.parentNode.open  = true;
				webFXTreeHandler.selected.parentNode.reload();
			} else {
				if (webFXTreeHandler.selected.parentNode.parentNode.src != null
						&& webFXTreeHandler.selected.parentNode.parentNode.src != '') {
					webFXTreeHandler.selected.parentNode.parentNode.open = true;
					webFXTreeHandler.selected.parentNode.parentNode.reload();
				}
			}
		}
	} catch (e) {

	}
}

/**
 * is mobile
 */

function isMobile() {

	// If the screen orientation is defined we are in a modern mobile OS
	var mobileOS = typeof orientation != 'undefined' ? true : false;

	if (mobileOS) {
		return true;
	}

	var android = (navigator.platform.indexOf("android") >= 0);

	var iPhone = (navigator.platform.indexOf("iPhone") >= 0);

	var iPod = (navigator.platform.indexOf("iPod") >= 0);

	var iPad = (navigator.platform.indexOf("iPad") >= 0);

	var symbian = (navigator.platform.indexOf("symbian") >= 0);

	var series60 = (navigator.platform.indexOf("series60") >= 0);

	var BlackBerry = (navigator.platform.indexOf("BlackBerry") >= 0);

	return android || iPhone || iPod || iPad || symbian || series60
			|| BlackBerry;
}

/**
 * 工作流节点权限相关
 */
function insertAuthPt() {
	var selectedNode = window.opener.selectedNodeBak;
	var whatuid = selectedNode.getAttribute('id');
	var ouuid = $("#gm_do_authorization_insert_ptnode_role_ouuid").val();
	callService({
		'serviceName' : 'u_role_ptnode',
		paras : "whatuid=" + whatuid + "&ouuid=" + ouuid,
		'pml' : 'PM_do_org_role_of_ptnode',
		'target' : 'PM_do_org_role_of_ptnode'
	});

	$('#FPM_do_authorization_insert_ptnode_role').jqmHide();

}

/**
 * 代码编辑相关
 */
function insertAceCode() {

	var name = $("input[name=propertyuid]").val();

	if (name == null || name == '') {
		alert(EELang.nameRequired);
		return;
	}
	var isValid = 1;
	var validChecks = $("input[name=icon]:checked");
	if (validChecks.length > 0) {
		isValid = $("input[name=icon]:checked").val();
	}

	var theCode = '';
	var hidden_type = $("input[name=mVersion]").val();

	if (hidden_type == null) {
		hidden_type = 'js';
	}

	if ($.browser.msie) {

		if (mirrorEditor) {
			mirrorEditor.save();
			theCode = mirrorEditor.getValue();
		} else if (mirrorEditor2) {
			mirrorEditor2.save();
			theCode = mirrorEditor2.getValue();
		}
	} else {

		if (hidden_type == 'js') {
			theCode = jsEditor.getSession().getValue();
		} else if (hidden_type == 'css') {
			theCode = cssEditor.getSession().getValue();
		} else if (hidden_type == 'rhino') {
			theCode = rhinoEditor.getSession().getValue();
		} else if (hidden_type == 'html') {
			theCode = htmlEditor.getSession().getValue();
		}
	}

	var objuid = $("input[name=objuid]").val();

	var serviceName = "DO_BO_Icon_Insert";
	if (objuid) {
		serviceName = "DO_BO_Icon_Update";
	}
	callService({
		'serviceName' : serviceName,
		'callType' : 'uf',
		'callback' : alert(EELang.saveSuccess),
		paras : "propertyuid=" + name + "&formulascript="
				+ encodeURIComponent(theCode) + "&icon=" + isValid
				+ "&mVersion=" + hidden_type
	});
}

function confirmDelete() {

	return confirm(EELang.confirmDelete);
}

function confirmShare() {

	return confirm(EELang.confirmShare);

}

function confirmSetup() {

	return confirm(EELang.confirmSetup);
}

function confirmCopy() {

	return confirm(EELang.confirmCopy);
}

function confirmInit() {

	return confirm(EELang.confirmInit);
}

function confirmGenerate() {

	return confirm(EELang.confirmGenerate);
}

function confirmServiceToRule() {

	return confirm(EELang.confirmServiceToRule);
}

function confirmImport() {
	return confirm(EELang.confirmImport);
}

function confirmRemove() {

	return confirm(EELang.confirmRemove);
}

function toDecimal2(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x * 100) / 100;
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
	return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>"
			+ label + "<br/>" + Math.round(series.percent) + "%</div>";
}

function activeNavLi(obj, isChild) {

	$("#sidebar .nav >li").removeClass("active");
	if (isChild) {
		$("#sidebar .submenu >li").removeClass("active");
		$(obj).parent().parent().parent().addClass("active");
	}
	$(obj).parent().addClass("active");

}

function changeDefaultApp(appId) {
	var url = globalURL
			+ "/servicecontroller?dataBus=setUserContext&contextKey=default_app_uid&contextValue="
			+ appId;
	$.get(url);
}

function generateUUID() {
	var d = new Date().getTime();
	var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = (d + Math.random() * 16) % 16 | 0;
		d = Math.floor(d / 16);
		return (c == 'x' ? r : (r & 0x7 | 0x8)).toString(16);
	});
	return uuid;
}

function gridCheck(isCheck, o, selector, e) {

	if (!isCheck) {
		$(o).parent().parent().removeClass("selected");
		if ($(selector + ' .selected').size() == 0) {
			$(selector + ' .list_check:checked:first').parent().parent()
					.addClass("selected");
		}
		if ($(o).attr("eeplatselected")) {
			console.log("unselected!");
			$(o).addClass("removevaluee");
		} else {
			$(o).removeClass("addvaluee");
		}
		e.stopPropagation();
	} else {
		if ($(o).attr("eeplatselected") == null) {
			console.log("selected!");
			$(o).addClass("addvaluee");
		} else {
			$(o).removeClass("removevaluee");
		}
	}

}

var O2String = function(O) {

	if (JSON) {
		return JSON.stringify(O);
	}

	var S = [];
	var J = "";
	if (Object.prototype.toString.apply(O) === '[object Array]') {
		for (var i = 0; i < O.length; i++)
			S.push(O2String(O[i]));
		J = '[' + S.join(',') + ']';
	} else if (Object.prototype.toString.apply(O) === '[object Date]') {
		J = "new Date(" + O.getTime() + ")";
	} else if (Object.prototype.toString.apply(O) === '[object RegExp]'
			|| Object.prototype.toString.apply(O) === '[object Function]') {
		J = O.toString();
	} else if (Object.prototype.toString.apply(O) === '[object Object]') {
		for ( var i in O) {
			O[i] = typeof (O[i]) == 'string' ? '"' + O[i] + '"'
					: (typeof (O[i]) === 'object' ? O2String(O[i]) : O[i]);
			S.push(i + ':' + O[i]);
		}
		J = '{' + S.join(',') + '}';
	}
	return J;
};

function toggleMore(obj) {

	var a = $(obj).parent().parent().nextAll(":not(.buttonMore)");
	a.toggle(a.css('display') == 'none');
	var html = $(obj).text() == EELang.more ? "<b>" + EELang.less + "</b>"
			: "<b>" + EELang.more + "</b>";
	$(obj).html(html);
}

function loadMenuPml(obj, pmlName, menuTitle, isReload, callback) {

	$(obj).parent().siblings().css('background-color', '');
	$(obj).parent().css('background-color', 'white');

	ep.myHash.mainMenuObj = obj;
	ep.myHash.mainPmlName = pmlName;
	ep.myHash.mainPmlTitle = menuTitle;

	var p = {
		'pml' : pmlName,
		'pmlName' : pmlName,
		'target' : '_opener_tab',
		'title' : menuTitle,
		'callback' : callback
	};

	if (!(pmlName in ep.pagination)) {
		ep.pagination[pmlName] = {};
	}
	if (isReload == undefined) {
		// /清空导航数据
		ep.myHash.empty();
		// 清空条件
		ep.pagination._conParas = null;
		ep.pagination._conShow = false;

	}
	if (ep.pagination[pmlName].changePml) {
		p.changePml = ep.pagination[pmlName].changePml;
		if (ep.pagination[ep.pagination[pmlName].changePml]) {// 不同的changePml
																// 对应不同的页码
			p.pageNo = ep.pagination[ep.pagination[pmlName].changePml].pageNo;
		}
	} else if (ep.pagination[pmlName].pageNo) {
		p.pageNo = ep.pagination[pmlName].pageNo;
	}

	// if(ep.myHash.map[pmlName]){
	// p = ep.myHash.map[pmlName];
	// }

	loadPml(p);
}

function toggleLeftSize() {

	// If window is small enough, enable sidebar push menu
	if ($(window).width() <= 992) {
		$('.row-offcanvas').toggleClass('active');
		$('.left-side').removeClass("collapse-left");
		$(".right-side").removeClass("strech");
		$('.row-offcanvas').toggleClass("relative");
	}

}

function reloadMenuPml(callback) {

	// var obj = $("body").data('mainMenuObj');
	// var pmlName = $("body").data('mainPmlName');
	// var menuTitle = $("body").data('mainPmlTitle');

	var obj = ep.myHash.mainMenuObj;
	var pmlName = ep.myHash.mainPmlName;
	var menuTitle = ep.myHash.mainPmlTitle

	if (obj != null && pmlName != null) {
		loadMenuPml(obj, pmlName, menuTitle, true, callback);
	}
}

function judgeSave() {

	var isFailure = 0;
	$.ajax({
		type : "GET",
		url : globalService + "?dataBus=getGlobalContext&r=" + Math.random(),
		async : false,
		success : function(b, textStatus) {
			try {
				var i1w = eval("b.a"
						+ $("#gm_do_resource_change_logo_resourcepath").data(
								"uuid") + "_w");
				var i1h = eval("b.a"
						+ $("#gm_do_resource_change_logo_resourcepath").data(
								"uuid") + "_h");
				var i2w = eval("b.a" + $("#loginimage").data("uuid") + "_w");
				var i2h = eval("b.a" + $("#loginimage").data("uuid") + "_h");

				if ((i1w == undefined && i1h == undefined)
						&& (i2w == undefined && i2h == undefined)) {
					isFailure = -1;
					console.log("Error::i2w == undefined && i2h == undefined");
					return;
				}

				if (i1h > 41 || i1h <= 30) {
					isFailure = 1;
				}
				if (i1w > 200) {
					isFailure = 1;
				}

				if (i2h > 180 || i2h < 100) {
					isFailure = 1;
				}
				if (i2w > 450 || i2w < 400) {
					isFailure = 1;
				}
			} catch (e) {
				isFailure = -1;
				console.log("Error::" + e);
				return;
			}

		}
	});

	if (isFailure == -1) {
		return false;
	}
	if (isFailure == 1) {
		alert(EELang.logoEcho);
		return false;
	}
	return true;
}

// //判断是否存在ShareID ，如果不存在则弹出录入框。
function judgeShareID() {
	$.ajax({
		type : "get",
		url : globalURL + "servicecontroller?dataBus=getGlobalContext",
		async : false,
		success : function(data, textStatus) {
			if (data.sharesecret == null) {
				loadPml({
					'title' : EELang.shareAppConfirm,
					'pml' : 'appshare_enter_login',
					'target' : '_opener'
				});
			}
		}
	});

}

function checkBracket(algorithms) {

	var bracket = [];
	var algorithms = algorithms || '';
	for (var i = 0; i < algorithms.length; i++) {
		if (algorithms.charAt(i) == "(") {
			bracket.push('(');
		}
		if (algorithms.charAt(i) == ")") {
			if (bracket.length) {
				bracket.pop();
			} else {
				console.log('公式括号不配对，缺少左括号！')
				return false;
			}
		}
	}
	if (bracket.length) {
		console.log('公式括号不配对，缺少右括号！')
		return false;
	}

	return true;
}

ep.conditonStatus = function(selectStr) {

	if (selectStr == null) {
		$('#extendModifyView').removeClass('extenda')
				.addClass('extendadisable');
		$('#extendDeleteView').removeClass('extenda')
				.addClass('extendadisable');
		$('#extendCopyView').removeClass('extenda').addClass('extendadisable');
		$('#extendRefreshView').removeClass('extenda').addClass(
				'extendadisable');
		return;
	}

	if ($(selectStr).find('option:selected').parent().length > 0
			&& $(selectStr).find('option:selected').parent().attr('stag') == 'cust') {

		$('#extendModifyView').removeClass('extendadisable')
				.addClass('extenda');
		$('#extendDeleteView').removeClass('extendadisable')
				.addClass('extenda');
		$('#extendCopyView').removeClass('extendadisable').addClass('extenda');
	} else {
		$('#extendModifyView').removeClass('extenda')
				.addClass('extendadisable');
		$('#extendDeleteView').removeClass('extenda')
				.addClass('extendadisable');
		$('#extendCopyView').removeClass('extenda').addClass('extendadisable');
	}

}

ep.oneValueSave = function(tableId) {

	$("#" + tableId).delegate("input,select", "keydown", function(event) {
		if (event.keyCode == "13") {
			$(this).next().click();
			event.stopPropagation();
			event.preventDefault();
			console.log("the next element text::" + $(this).next().text());
		}
	});
}