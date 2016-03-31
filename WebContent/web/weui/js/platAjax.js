ep.invokeDomId = "";

// 在js里面直接调用action类
/**
 * p的定义 p.btn p.actionName p.formName p.paras p.callback
 * 
 * @param p
 * @return
 */
ep.callAction = function(p) {

	if (p.btn) {
		p.btn.disabled = true;
	}
	if (p.actionName == null && p.actionConfigName == null) {
		if (p.btn) {
			p.btn.disabled = false;
		}
		return;
	}

	var async = true;

	if (p.async == false) {
		async = false;
	}
	var aMsg = EELang.dealDaemon;
	if (p.msg) {
		aMsg = p.msg;
	}

	// 表单验证
	if (!ep.validate(p.formName)) {
		if (p.btn) {
			if (p.btn.nodeName == 'A') {
				p.btn.flag = false;
			} else {
				p.btn.disabled = false;
			}
		}
		return;
	}

	// /支持pml的两种形式
	var pmlName = "";
	if (p.pmlName) {
		pmlName = p.pmlName;
	}
	if (p.pml == null) {
		p.pml = p.pmlName;
	}
	if (p.pml != null && p.pml.indexOf('mvccontroller') == -1
			&& p.pml.indexOf('.pml') == -1) {
		pmlName = p.pml;
		p.pml = ep.globalURL + p.pml + ".pml?1=1";
	}
	// 如果没有设置参数，自动从表单中获取
	var paras = "";
	if (p.paras) {
		paras = p.paras;
		if (p.formName != null && $.trim(p.formName) != "") {
			paras = paras + "&" + ep.getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = ep.getParasOfForms(p.formName);
	}

	var callType = "as";
	if (p.callType) {
		callType = p.callType;
	}

	if (p.actionName) {
		paras = "callType=" + callType + "&greenChannel=true&userDefineClass="
				+ p.actionName + "&" + ep.urlCodeDeal(paras);
	}
	if (p.actionConfigName) {
		paras = "callType="
				+ callType
				+ "&contextServiceName=do_auth_owner_browse&greenChannel=true&actionConfigName="
				+ p.actionConfigName + "&" + ep.urlCodeDeal(paras);
	}

	$
			.ajax({
				type : "post",
				url : ep.globalService,
				data : paras,
				async : async,
				success : function(data, textStatus) {

					ep.closeWin();
					
					if (p.callback) {
						p.callback(data);
					}

					if (data != null && data.echo_msg != null
							&& $.trim(data.echo_msg) != '') {
						var echo_msg = unescape(data.echo_msg);
						if (data.success
								&& (data.success == 'success' || data.success == 'true')) {// /成功也有可能含有提示
							if (echo_msg != 'success' && echo_msg != 'null'
									&& echo_msg != 'undefined') {
								alert(echo_msg);
							}
						} else {
							if (echo_msg != 'success' && echo_msg != 'null'
									&& echo_msg != 'undefined') {
								alert(echo_msg);
							}
							if (p.btn) {
								if (p.btn.nodeName == 'A') {
									p.btn.flag = false;
								} else {
									p.btn.disabled = false;
								}
							}
							return;
						}
					}
					
					if (ep.pagination[pmlName]) {
						p.pageNo = ep.pagination[pmlName].pageNo;
						p.pageSize = ep.pagination[pmlName].pageSize;
						if(ep.pagination[pmlName].pagiPml){
							p.pagiPml = ep.pagination[pmlName].pagiPml;
						}
					////定位子数据集合
						if(ep.pagination[pmlName].invokePml){
							p.invokePml = ep.pagination[pmlName].invokePml;
						}
					}
					p.echoJs = null;
					
					if (p.conFormName) {
						p.paras = ep.getParasOfForms(p.conFormName);
					}
					else if(ep.pagination._conParas){
						p.paras = ep.pagination._conParas + "&_conParas=true";
					}else{
						p.paras = "";
					}
					p.formName = null;
					p.callback = null;
					ep.loadPml(p);


					if (p.btn) {
						if (p.btn.nodeName == 'A') {
							p.btn.flag = false;
						} else {
							p.btn.disabled = false;
						}
					}
				},
				dataType : "json"
			});
}

// 在js里面直接调用action类
/**
 * p的定义
 * 
 * p.btn p.serviceUid p.serviceName p.formName p.paras p.title p.pml p.pmlHeight
 * p.pageNo p.pmlWidth p.target p.echoJs p.async 缺省 true p.callType //触发类别
 * default: us ; others: uf p.callback //回调函数
 * 
 */

ep.callService = function(p) {

	if (p == null) {
		return;
	}

	var aMsg = EELang.dealDaemon;
	if (p.msg) {
		aMsg = p.msg;
	}

	if (p.btn) {
		if (p.btn.nodeName == 'A') {
			if (p.btn.flag) {
				alert(EELang.clickRepeat);
				return;
			}
			p.btn.flag = true;
		} else {
			p.btn.disabled = true;
		}
	}
	var async = true;

	if (p.async == false) {
		async = false;
	}
	if (p.serviceUid == null && p.serviceName == null) {
		if (p.btn) {
			if (p.btn.nodeName == 'A') {
				p.btn.flag = false;
			} else {
				p.btn.disabled = false;
			}
		}
		return;
	}

	if (!validate(p.formName)) {
		if (p.btn) {
			if (p.btn.nodeName == 'A') {
				p.btn.flag = false;
			} else {
				p.btn.disabled = false;
			}
		}
		return;
	}

	// /支持pml的两种形式
	var pmlName = "";
	if (p.pmlName) {
		pmlName = p.pmlName;
	}
	if (p.pml == null) {
		p.pml = p.pmlName;
	}
	if (p.pml != null && p.pml.indexOf('mvccontroller') == -1
			&& p.pml.indexOf('.pml') == -1) {
		pmlName = p.pml;
		p.pml = ep.globalURL + p.pml + ".pml?1=1";
	}

	// ///提示性问题
	if (p.echoJs != null && !eval(unescape(p.echoJs))) {
		if (p.btn) {
			if (p.btn.nodeName == 'A') {
				p.btn.flag = false;
			} else {
				p.btn.disabled = false;
			}
		}
		return;
	}
	// 只要设置了formName，就从表单中获取
	var paras = "";
	if (p.paras) {
		paras = p.paras;
		if (p.formName != null && $.trim(p.formName) != "") {
			paras = paras + "&" + ep.getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = ep.getParasOfForms(p.formName);
	}
	var callType = "us";
	if (p.callType) {
		callType = p.callType;
	}

	var callServStr = "";
	if (p.serviceUid) {
		callServStr = "contextServiceUid=" + p.serviceUid;
	} else {
		callServStr = "contextServiceName=" + p.serviceName;
	}

	paras = callServStr + "&callType=" + callType + "&" + ep.urlCodeDeal(paras);

	ep.loading(aMsg);
	$
			.ajax({
				type : "post",
				url : ep.globalService,
				data : paras,
				async : async,
				success : function(data, textStatus) {

					ep.closeWin();
					if (p.callback) {
						p.callback(data);
					}
					
					if (data != null && data.echo_msg != null
							&& $.trim(data.echo_msg) != '') {
						var errmsg = unescape(data.echo_msg);

						if (data.success
								&& (data.success == 'success' || data.success == 'true')) {// /成功也有可能含有提示
							if (errmsg != 'success' && errmsg != 'null'
									&& errmsg != 'undefined') {
								alert(errmsg);
							}
						} else {
							if (errmsg != 'success' && errmsg != 'null'
									&& errmsg != 'undefined') {
								alert(errmsg);
							}
							if (p.btn) {
								if (p.btn.nodeName == 'A') {
									p.btn.flag = false;
								} else {
									p.btn.disabled = false;
								}
							}
							return;
						}
					}

					var title = EELang.newDialog;
					if (p.title != null && p.title != "") {
						title = p.title;
					}

					if (data.returnPath != null && data.returnPath != null) {

						var arrayPath = data.returnPath.split(",");
						var arrayTarget = data.targetPane.split(",");
						var arrayTitle = data.returnTitle.split(",");
						for (ai = 0; ai < arrayPath.length; ai++) {
							var aPath = arrayPath[ai];
							var target = arrayTarget[ai];
							var aTitle = arrayTitle[ai];
							if (aTitle == null) {
								aTitle = EELang.infoDialog;
							}
							if (aPath != null && aPath != "" && target != null
									&& target != "") {
								if (target == '_opener_window') {
									window
											.open(
													aPath
															+ "&"
															+ ep.getShorterParas(paras),
													aTitle,
													'height=650,width=1012,left=0,top=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no');
								} else if (target == '_opener_location') {
									window.location = aPath + "&"
											+ ep.getShorterParas(paras)
											+ "&isApp=true";
								} else if (target == '_opener_tab') {

									$("#_opener_tab")
											.empty()
											.load(
													p.pml,
													ep.getShorterParas(ep.urlCodeDeal(paras)),
													function() {
														ep.closeWin();
													});
								} else if (target == '_opener') {
									ep.popupDialog(pmlName, aTitle, aPath,
											p.pmlWidth, p.pmlHeight);
								} else {
									$("#" + target).empty().load(aPath);
								}
							}
						}
					} else {
						if (ep.pagination[pmlName]) {
							p.pageNo = ep.pagination[pmlName].pageNo;
							p.pageSize = ep.pagination[pmlName].pageSize;
							if(ep.pagination[pmlName].pagiPml){
								p.pagiPml = ep.pagination[pmlName].pagiPml;
							}
						////定位子数据集合
							if(ep.pagination[pmlName].invokePml){
								p.invokePml = ep.pagination[pmlName].invokePml;
							}
						}
						
						if (p.conFormName) {
							p.paras = ep.getParasOfForms(p.conFormName);
						}
						else if(ep.pagination._conParas){
							p.paras = ep.pagination._conParas + "&_conParas=true";
						}else{
							p.paras = "";
						}
						p.echoJs = null;
						p.callback = null;
						p.formName = null;
						ep.loadPml(p);
					}

					if (p.btn) {
						if (p.btn.nodeName == 'A') {
							p.btn.flag = false;
						} else {
							p.btn.disabled = false;
						}
					}
				},
				dataType : "json"
			});
}

/**
 * p.paras
 * 
 * @param p
 * @return
 */
ep.callPlatBus = function(p) {

	if (p == null) {
		return;
	}
	if (p.paras == null) {
		return;
	}
	$.ajax({
		type : "POST",
		url : ep.globalService,
		data : p.paras,
		async : false
	});
}

/**
 * p的定义 p.formName p.paras p.target p.pml p.pmlName p.showLoadMsg 是否ep.loading进度
 * p.pmlWidth 弹出Dialog方式，宽度(px) p.pmlHeight 弹出Dialog方式，高度(px) ep.urlCodeDeal(paras)
 * p.pageNo 第几页 p.pageNum 每页多少条 改写可以加快速
 * 
 * @param p
 * @return
 */

ep.loadPml = function(p) {

	if (p == null) {
		return;
	}
	if (p.pml == null && p.pmlName == null) {
		return;
	}
	var pmlName = "";

	if (p.pmlName) {
		pmlName = p.pmlName;
	}
	if (p.pml == null) {
		p.pml = p.pmlName;
	}
	// ///提示性问题
	if (p.echoJs != null && !eval(unescape(p.echoJs))) {
		if (p.btn) {
			if (p.btn.nodeName == 'A') {
				p.btn.flag = false;
			} else {
				p.btn.disabled = false;
			}
		}
		return;
	}

	/**
	 * 是否为简化配置 如果直接把pml配置为面板的名称即为简化配置
	 */
	var simpleConfig = false;
	if (p.pml != null && p.pml.indexOf('mvccontroller') == -1
			&& p.pml.indexOf('.pml') == -1 && p.pml.indexOf('.jsp') == -1
			&& p.pml.indexOf('.htm') == -1) {
		pmlName = p.pml;
		p.pmlName = p.pml;
		simpleConfig = true;
		p.pml = ep.globalURL + p.pml + ".pml?1=1";
	}

	if (p.pml != null && p.pml.indexOf("?") == -1) {
		p.pml = p.pml + "?1=1";
	}

	var showLoadMsg = true;

	if (p.showLoadMsg != null && p.showLoadMsg == false) {
		showLoadMsg = false;
	}

	var paras = "";
	if (p.paras) {
		paras = p.paras;
		if (p.formName != null && $.trim(p.formName) != "") {
			paras = paras + "&" + ep.getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = ep.getParasOfForms(p.formName);
	}

	// /处理pageNo pageSize
	if (p.pageNo != null || p.pageSize != null) {
		paras = pageSplitDeal(paras, p.pageNo, p.pageSize);
	}
	
	// //引入_opener_dialog
	try {
		var resourcePath = p.resourcePath;

		if (resourcePath == null) {
			resourcePath = p.pml + "&isApp=true";
		}
		if (resourcePath != null && resourcePath.indexOf("?") == -1) {
			resourcePath = resourcePath + "?1=1";
		}
		
		if (p.target == '_opener') {
			var title = "";
			if (p.title) {
				title = p.title;
			}
			ep.popupDialog(pmlName, title, p.pml + "&"
					+ ep.getShorterParas(ep.urlCodeDeal(paras)), p.pmlWidth,
					p.pmlHeight,p.callback);
		} else if (p.target && $("#" + p.target).size() > 0) {// &&
			// p.target!='_opener_tab'

			if (showLoadMsg) {
				ep.loading();
			}
			if (p.target == pmlName) {
				$("#" + p.target).empty().wrap("<div></div>").parent().load(
						p.pml, ep.getShorterParas(ep.urlCodeDeal(paras)), function() {
							$("#" + p.target).unwrap();
							if (p.callback) {
								p.callback();
							}
							if (showLoadMsg) {
								ep.closeWin();
							}
						});

			} else {
				$("#" + p.target).empty().load(p.pml,
						ep.getShorterParas(ep.urlCodeDeal(paras)), function() {
							if (p.callback) {
								p.callback();
							}
							if (showLoadMsg) {
								ep.closeWin();
							}
						});
			}
		}else{
			window.location = resourcePath + "&" + ep.getShorterParas(paras);
		}
	} catch (e) {

	}
}

/**
 * 根据form转换为字符串形式的paras
 * 
 * @param targetForms
 * @return
 */
ep.getParasOfForms = function(targetForms) {

	var paras = "";
	if (targetForms.indexOf(" ") != -1) {
		var forms = targetForms.split(" ");
		for (var i = 0; i < forms.length; i++) {
			var aForm = forms[i];
			if (paras == "") {
				paras = $("#" + aForm).serialize();
			} else {
				paras = paras + "&" + $("#" + aForm).serialize();
			}
		}
	} else {
		paras = $("#" + targetForms).serialize();
	}
	return paras;
}

ep.moreLoad = function(gridId,paneName,moreBtn){
	
	console.log($("#" + moreBtn).attr('pageNo'))
	var pageNo = parseInt($("#" + moreBtn).attr('pageNo')) + 1;
	$("#" + moreBtn).attr('pageNo',pageNo);
	
	var paras = 'pageNo=' + pageNo + '&onlyData=true';
	$
	.ajax({
		type : "post",
		url :  ep.globalURL + paneName + ".pml",
		data : paras,
		success : function(data, textStatus) {
			if($.trim(data)!=""){
				$("#" + gridId).append(data);
			}else{
				$("#" + moreBtn).remove();
			}
			var changeUrl =  ep.globalURL + paneName + ".pml" + "?isApp=true&pageNoStatus=" + pageNo + '&pageNo=' + pageNo ;
			 var state = {
		                title: document.title,
		                url:  changeUrl,
		                otherkey: null
		        };
		     history.replaceState(state, document.title,  changeUrl);
		}
	}
	);
}

/**
 * 
 * @param paras
 * @return
 */
ep.getShorterParas = function(paras) {
	
	if(paras===null){
		return null;
	}
	if (paras.length > 1200) {
		return paras.substr(0, 1200);
	}
	return paras;
}
/**
 * 分页
 * 
 * @param dataKey
 * @param pmlName
 * @param formName
 */
ep.pageSplit =function(dataKey, pmlName, formName) {

	if ($(document.body).data(dataKey) == null) {
		$(document.body).data(dataKey, 1);
	}

	$("#" + dataKey + " .firstPage").bind(
			'click',
			function() {
				if ($(document.body).data(dataKey) == "1") {
					return;
				}
				$(document.body).data(dataKey, "1");
				var pmlUrl = ep.getPmlUrl(pmlName, 1, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				ep.loadPml({
					'pml' : pmlUrl,
					'target' : pmlName,
					'formName' : formName
				});
			});

	$("#" + dataKey + " .prevPage").bind(
			'click',
			function() {
				if ($(document.body).data(dataKey) == "1") {
					return;
				}
				var curPageNo = parseInt($(document.body).data(dataKey)
						.replace(",", "")) - 1;
				$(document.body).data(dataKey, "" + curPageNo);
				var pmlUrl = ep.getPmlUrl(pmlName, curPageNo, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				ep.loadPml({
					'pml' : pmlUrl,
					'target' : pmlName,
					'formName' : formName
				});
			});

	$("#" + dataKey + " .nextPage").bind(
			'click',
			function() {

				if (parseInt($("#" + dataKey + " .pageNo").text().replace(",",
						"")) >= parseInt(($("#" + dataKey + " .pageSize")
						.text().replace(",", "")))) {
					return;
				}
				var curPageNo = parseInt($("#" + dataKey + " .pageNo").text()
						.replace(",", "")) + 1;
				$(document.body).data(dataKey, "" + curPageNo);
				var pmlUrl = ep.getPmlUrl(pmlName, curPageNo, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				ep.loadPml({
					'pml' : pmlUrl,
					'target' : pmlName,
					'formName' : formName
				});
			});

	$("#" + dataKey + " .lastPage")
			.bind(
					'click',
					function() {
						if (parseInt($("#" + dataKey + " .pageNo").text()
								.replace(",", "")) == $(
								"#" + dataKey + " .pageSize").text().replace(
								",", "")) {
							return;
						}
						$(document.body).data(
								dataKey,
								$("#" + dataKey + " .pageSize").text().replace(
										",", ""));
						var pmlUrl = ep.getPmlUrl(pmlName, $(
								"#" + dataKey + " .pageSize").text().replace(
								",", ""), $("#" + dataKey + " .rowSize").text()
								.replace(",", ""));
						ep.loadPml({
							'pml' : pmlUrl,
							'target' : pmlName,
							'formName' : formName
						});
					});
}

ep.getPmlUrl = function(pmlName, pageNo, pageSize) {
	return ep.globalURL + pmlName + ".pml?pageSize=" + pageSize + "&pageNo="
			+ pageNo;
}


// 检查是否含有汉字
function funcChin(value) {
	return (/.*[\u4e00-\u9fa5]+.*$/.test(value));
}

// 检查是否含有全角和点
function funcSBCDot(value) {

	if (value == null) {
		return false;
	}

	if (value.indexOf(".") > -1) {
		return true;
	}
	for (var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 128)
			return true;
	}

}
// 检查Email
function checkErrEmail(value) {
	return !/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
			.test(value);
}
// 检查Url
function checkErrUrl(value) {
	return !/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
			.test(value);
}

// 检查Integer
function checkErrNum(value) {
	return !/^\d+$/.test(value);
}
// 检查RealNumber
function checkErrFloat(value) {
	return !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}
// 检查IDCard
function checkIdCard(idcard) {
	// 判断身份证是否合法
	var Errors = new Array("yes", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
			"身份证号码校验错误!", "身份证地区非法!");
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	}
	var idcard, Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	if (area[parseInt(idcard.substr(0, 2))] == null)
		return Errors[4];
	switch (idcard.length) {
	case 15:
		if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
				|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
						.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
		}
		if (ereg.test(idcard))
			return Errors[0];
		else
			return Errors[2];
		break;
	case 18:
		if (parseInt(idcard.substr(6, 4)) % 4 == 0
				|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
						.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// ???????????????
		} else {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// ???????????????
		}
		if (ereg.test(idcard)) {
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
					* 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
					* 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
					* 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
					* 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
					* 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
					* 2 + parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
					* 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1);
			if (M == idcard_array[17])
				return Errors[0];
			else
				return Errors[3];
		} else
			return Errors[2];
		break;
	default:
		return Errors[1];
		break;
	}
}
// 检查MoBile
function checkNotMobile(value) {
	return !/(\d){11}/.test(value);
}
// 检查固定电话 Telphone
function checkNotTelphone(value) {
	return !/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)/
			.test(value);
}

// 检查是否含有空格

function checkBlank(value) {
	return /(\s)/.test(value);
}

function toggleContent(obj1) {

	var isShow = $(obj1).parents('.box').find('.box-content').toggle();

	if ($(obj1).find("i").attr('class') == 'icon-chevron-up') {
		$(obj1).find("i").attr('class', 'icon-chevron-down');
	} else {
		$(obj1).find("i").attr('class', 'icon-chevron-up');
	}

}

function validate(formName) {
	if (formName != null && formName != "") {
		var ay = formName.split(" ");
		for (var ii = 0; ii < ay.length; ii++) {
			var aFName = $.trim(ay[ii]);
			var formJquery = $("#" + aFName);
			try {
				var validator = formJquery.validate({
					ignoreTitle : true,
				});
				if (!formJquery.valid()) {
					validator.focusInvalid();
					return false;
				}
			} catch (e) {
				// alert(e);
			}
		}
	}
	return true;
}

ep.selectPageSize = 10;

ep.mainUrl = "";

ep.reloaMain = function(){
	
	
	// /清空导航数据
	ep.myHash.empty();
	ep.pagination = {};
	// 清空条件
	ep.pagination._conParas = null;
	ep.pagination._conShow = false;
	if(ep.mainUrl!=""){
		$("#_opener_tab").empty().load(ep.mainUrl);
	}else{
		window.location.reload();
	}
	
}

ep.getMainPml = function(){
    var mainPml = $("#_opener_tab :first-child").attr("id");
	if(mainPml == undefined){
	      	mainPml = $("#_opener_tab :first-child").find(":first-child").attr("id");
	}
	return mainPml;
}

ep.endWith =function(src, endStr){
	if(src==null){
		return false;
	}
	var d=src.length-endStr.length;
	return (d>=0&&src.lastIndexOf(endStr)==d)
}
ep.pagination = {};
ep.myHash = new function(aSize) {
	this.map = {};
	this.limit = 20;
	if (aSize != undefined) {
		this.limit = aSize;
	}
	this.array = new Array();
	
	this.empty = function(){
		this.map = {};
		this.array = new Array();
	}
	
	this.put = function(aKey, p) {

		if (this.array.length > 0) {
			if (this.array[this.array.length - 1] === aKey) {
				return;
			}
		}
		if(p.target==='_opener'){
			return;
		}
		if (!(ep.endWith(p.pmlName,'_Main')  ||
				ep.endWith(p.pmlName,'_control_main') 		
				) 
				){
			return;
		}
		this.map[aKey] = p;
		this.array.push(aKey);
		if (this.size() > this.limit) {
			var first = this.array.shift();
			this.del(first);
		}
	}

	this.back = function(mainPml) {
		if (this.size() > 0) {
			
			var aKey = '';
			
			var lastPml = this.array[this.size() - 1];
			if(lastPml === mainPml){
				if(this.size() > 1){
					aKey = this.array[this.size() - 2];
					this.array.pop();
					this.del(lastPml);
				}else{
					ep.reloaMain();
					return;
				}
			}else{
				aKey = lastPml;
			}
			
			var p = this.map[aKey];
			if (p) {

				try{
					if (ep.pagination[aKey]) {
						p.pageNo = ep.pagination[aKey].pageNo;
						p.pageSize = ep.pagination[aKey].pageSize;
						if(ep.pagination[aKey].pagiPml){
							p.pagiPml = ep.pagination[aKey].pagiPml;
						}
						if(ep.pagination[aKey].invokePml){
							p.invokePml = ep.pagination[aKey].invokePml;
						}else{
							p.invokePml = null;
						}
						//////////返回的页面只有两种，main 对应多条记录，control_main对应单体记录，多疑可以处理对应的参数
						if (p.conFormName) {
							p.paras = ep.getParasOfForms(p.conFormName);
						}
						else if(ep.pagination._conParas){
							p.paras = ep.pagination._conParas + "&_conParas=true";
						}else{
							p.paras = "";
						}

						var changePml = ep.pagination[aKey].changePml; 
						if(changePml){
							p.changePml = changePml;
							if(ep.pagination[changePml]){
								p.pageNo = ep.pagination[changePml].pageNo;
								p.pageSize = ep.pagination[changePml].pageSize;
							}else{
								ep.pagination[changePml] = {'pageNo':1};
							}
						}
						
					}
				}catch(e){
					
				}
				ep.loadPml(p);
			}
		} else {
			ep.reloaMain();
		}
	}

	this.get = function(aKey) {
		return this.map[aKey];
	}

	this.has = function(aKey) {
		return aKey in this.map;
	}

	this.del = function(aKey) {
		// delete this.map[aKey];
	}

	this.size = function() {
		return this.array.length;
	}
}

//if (isSupportHistory) {
//
//	$(window).bind('hashchange', function(event) {
//
//		var hash = location.hash;
//		var pml = hash.substring(1);
//		var p = ep.myHash.get(pml);
//		if (p === undefined || p.platCall === undefined) {
//			return;
//		}
//		if (p.platCall) {
//			p.platCall = false;
//			return;
//		}
//		ep.loadPml(p);
//	});//}