/** ***************************************主页面框架代码***************************************** */
ep.globalService = ep.globalURL + 'servicecontroller';
ep.globalPml = ep.globalURL + 'mvccontroller';

// ///////////////////////////////////////////////////////////////////////
/** ***************************编码处理************************************************************** */
// UrlCode 处理代码
ep.urlCodeDeal = function(str) {
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

ep.pageSplitDeal = function(str, pageNo, pageSize) {

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
ep.popupDialog = function(id, title, href, width, height,callback) {

	if (title == undefined) {
		title = "";
	}
	if ($('#F' + id).size() == 0) {
		ep.createFloatDiv(id, title);
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

ep.createFloatDiv = function(id, title) {

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


function labelFormatter(label, series) {
	return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>"
			+ label + "<br/>" + Math.round(series.percent) + "%</div>";
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

ep.loading = function(aMsg,position){
	$.showLoading("正在加载...");
}

ep.showMainMsg =function(position,msgW,msgH,align,type,content,isClose){
	
}

ep.closeWin = function(){
	$.hideLoading();
}

ep.confirmDelete =function(){
	var c =  $.confirm("您确定要删除吗");
	console.log("cccccccccccc::::" + c);
	return c;
}

