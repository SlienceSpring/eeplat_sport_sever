var invokeDomId = "";
var invokeDomRef;
var mirrorEditor;
var mirrorEditor2;
var kindEditor;
var isSupportHistory = false;
if ("onhashchange" in window) {
	isSupportHistory = true;
}

// 在js里面直接调用action类
/**
 * p的定义 p.btn p.actionName p.formName p.paras p.callback
 * 
 * @param p
 * @return
 */
function callAction(p) {

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
		p.pml = globalURL + p.pml + ".pml?1=1";
	}
	// 如果没有设置参数，自动从表单中获取
	var paras = "";
	if (p.paras) {
		paras = p.paras;
		if (p.formName != null && $.trim(p.formName) != "") {
			paras = paras + "&" + getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = getParasOfForms(p.formName);
	}

	var callType = "as";
	if (p.callType) {
		callType = p.callType;
	}

	if (p.actionName) {
		paras = "callType=" + callType + "&greenChannel=true&userDefineClass="
				+ p.actionName + "&" + urlCodeDeal(paras);
	}
	if (p.actionConfigName) {
		paras = "callType="
				+ callType
				+ "&contextServiceName=do_auth_owner_browse&greenChannel=true&actionConfigName="
				+ p.actionConfigName + "&" + urlCodeDeal(paras);
	}

	loading(aMsg);
	$
			.ajax({
				type : "post",
				url : globalService,
				data : paras,
				async : async,
				success : function(data, textStatus) {

					closeWin();
					
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
						p.paras = getParasOfForms(p.conFormName);
					}
					else if(ep.pagination._conParas){
						p.paras = ep.pagination._conParas + "&_conParas=true";
					}else{
						p.paras = "";
					}
					p.formName = null;
					p.callback = null;
					loadPmlHelper(p);


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
ep.callAction = callAction;

// 在js里面直接调用action类
/**
 * p的定义
 * 
 * p.btn p.serviceUid p.serviceName p.formName p.paras p.title p.pml p.pmlHeight
 * p.pageNo p.pmlWidth p.target p.echoJs p.async 缺省 true p.callType //触发类别
 * default: us ; others: uf p.callback //回调函数
 * 
 */

function callService(p) {

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
		p.pml = globalURL + p.pml + ".pml?1=1";
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
	// 初始化FckEditor值
	updateEditorFormValue();
	// 只要设置了formName，就从表单中获取
	var paras = "";
	if (p.paras) {
		paras = p.paras;
		if (p.formName != null && $.trim(p.formName) != "") {
			paras = paras + "&" + getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = getParasOfForms(p.formName);
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

	paras = callServStr + "&callType=" + callType + "&" + urlCodeDeal(paras);

	loading(aMsg);
	$
			.ajax({
				type : "post",
				url : globalService,
				data : paras,
				async : async,
				success : function(data, textStatus) {

					closeWin();
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
															+ getShorterParas(paras),
													aTitle,
													'height=650,width=1012,left=0,top=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no');
								} else if (target == '_opener_location') {
									window.location = aPath + "&"
											+ getShorterParas(paras)
											+ "&isApp=true";
								} else if (target == '_opener_tab') {

									$("#_opener_tab")
											.empty()
											.load(
													p.pml,
													getShorterParas(urlCodeDeal(paras)),
													function() {
														closeWin();
													});
								} else if (target == '_opener') {
									popupDialog(pmlName, aTitle, aPath,
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
							p.paras = getParasOfForms(p.conFormName);
						}
						else if(ep.pagination._conParas){
							p.paras = ep.pagination._conParas + "&_conParas=true";
						}else{
							p.paras = "";
						}
						p.echoJs = null;
						p.callback = null;
						p.formName = null;
						loadPmlHelper(p);
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
ep.callService = callService;

/**
 * p.paras
 * 
 * @param p
 * @return
 */
function callPlatBus(p) {

	if (p == null) {
		return;
	}
	if (p.paras == null) {
		return;
	}
	$.ajax({
		type : "POST",
		url : globalService,
		data : p.paras,
		async : false
	});
}
ep.callPlatBus = callPlatBus;

/**
 * p的定义 p.formName p.paras p.target p.pml p.pmlName p.showLoadMsg 是否loading进度
 * p.pmlWidth 弹出Dialog方式，宽度(px) p.pmlHeight 弹出Dialog方式，高度(px) urlCodeDeal(paras)
 * p.pageNo 第几页 p.pageNum 每页多少条 改写可以加快速
 * 
 * @param p
 * @return
 */

function loadPml(p) {

	if (p == null) {
		return;
	}
	if (p.pml == null && p.pmlName == null) {
		return;
	}
	loadPmlHelper(p);
	if (p.pmlName) {
		p.platCall = true;
		ep.myHash.put(p.pmlName, p);
		window.location.hash = "#" + p.pmlName;
	}
}
ep.loadPml = loadPml;
function loadPmlHelper(p) {

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
		p.pml = globalURL + p.pml + ".pml?1=1";
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
			paras = paras + "&" + getParasOfForms(p.formName);
		}
	} else if (p.paras == null && p.formName != null
			&& $.trim(p.formName) != "") {
		paras = getParasOfForms(p.formName);
	}

	// /处理pageNo pageSize
	if (p.pageNo != null || p.pageSize != null) {
		paras = pageSplitDeal(paras, p.pageNo, p.pageSize);
	}
	
	//改变目标面板
	if(p.pmlName){
		if(p.target != '_opener'){
			if(ep.endWith(p.pmlName,'_Main')  ||
					ep.endWith(p.pmlName,'_control_main')){
				p.target = "_opener_tab";
			} 
		}
	}
	// 传递目标面板
	if (p.target!=null & p.target!="_opener_tab") {
		paras = paras + "&target=" + p.target;
	}
	
	///改变的pml 只用于主面板，这个参数的赋值，只有在loadMainMenuPml 和 TCondtionExtend中，其它地方不能用，有冲突。
	if(p.changePml){
		paras = paras + "&changePml=" + p.changePml;
	}
	
    if(p.pagiPml){
    	paras = paras + "&pagiPml=" + p.pagiPml;
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

		if (p.target == '_opener_window') {
			window
					.open(
							resourcePath + "&" + getShorterParas(paras),
							title,
							'height=650,width=1012,left=0,top=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no');
		} else if (p.target == '_opener_location') {
			window.location = resourcePath + "&" + getShorterParas(paras)
					+ "&isApp=true";
		} else if (p.target == '_opener_tab') {

			$("#_opener_tab").empty().load(p.pml,
					getShorterParas(urlCodeDeal(paras)), function() {
						if (p.callback) {
							p.callback();
						}
						if (showLoadMsg) {
							closeWin();
						}
					////定位子数据集合
						if(p.invokePml!=null  && ep.endWith(p.pmlName,'_control_main')){
							if(!ep.endWith(p.invokePml,'_control')){
								window.location = "#" + p.invokePml;
							}
						}else{
							window.location = "#";
						}
					});
		} else if (p.target == '_opener') {
			var title = "";
			if (p.title) {
				title = p.title;
			}
			popupDialog(pmlName, title, p.pml + "&"
					+ getShorterParas(urlCodeDeal(paras)), p.pmlWidth,
					p.pmlHeight,p.callback);
		} else if (p.target && $("#" + p.target).size() > 0) {// &&
			// p.target!='_opener_tab'

			if (showLoadMsg) {
				loading();
			}
			if (p.target == pmlName) {
				$("#" + p.target).empty().wrap("<div></div>").parent().load(
						p.pml, getShorterParas(urlCodeDeal(paras)), function() {
							$("#" + p.target).unwrap();
							if (p.callback) {
								p.callback();
							}
							if (showLoadMsg) {
								closeWin();
							}
						});

			} else {
				$("#" + p.target).empty().load(p.pml,
						getShorterParas(urlCodeDeal(paras)), function() {
							if (p.callback) {
								p.callback();
							}
							if (showLoadMsg) {
								closeWin();
							}
						});
			}
		} else {

			if (pmlName != "") {

				if ($("#" + pmlName).size() > 0) {

					if (showLoadMsg) {
						loading();
					}

					$("#" + pmlName).empty().wrap("<div></div>").parent().load(
							p.pml, getShorterParas(urlCodeDeal(paras)),
							function() {
								$("#" + pmlName).unwrap();
								if (p.callback) {
									p.callback();
								}
								if (showLoadMsg) {
									closeWin();
								}
							});
				} else {
					if (showLoadMsg) {
						loading();
					}
					$("#_opener_tab").empty().load(p.pml,
							getShorterParas(urlCodeDeal(paras)), function() {
								if (p.callback) {
									p.callback();
								}
								if (showLoadMsg) {
									closeWin();
								}
								////定位子数据集合
								if(p.invokePml!=null  && ep.endWith(p.pmlName,'_control_main')){
									if(!ep.endWith(p.invokePml,'_control')){
										window.location = "#" + p.invokePml;
									}
								}else{
									window.location = "#";
								}
								
							});
					// $(document.body).data(pmlName, paras);
				}
			} else {
				// alert(EELang.noDialogName);
			}
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
function getParasOfForms(targetForms) {

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

/**
 * 
 * @param paras
 * @return
 */
function getShorterParas(paras) {
	
	if(paras===null){
		return null;
	}
	if (paras.length > 1200) {
		return paras.substr(0, 1200);
	}
	return paras;
}

function updateEditorFormValue() {
	try {
		// /fckeditor
		for (var i = 0; i < parent.frames.length; ++i)
			if (parent.frames[i].FCK)
				parent.frames[i].FCK.UpdateLinkedField();
	} catch (e) {
	}

	try {
		// //codemirror
		if (mirrorEditor) {
			mirrorEditor.save();
		}
		if (mirrorEditor2) {
			mirrorEditor2.save();
		}
		if (kindEditor) {
			kindEditor.sync();
		}
	} catch (e) {
	}

}

// //////////////////////设计器相关的函数
function sortUp(o, boName, serviceName) {

	var trO = $(o).parent().parent();
	var base = trO.attr('value');
	var trPre = trO.prev();
	var pre = trPre.attr('value');

	if (trPre.attr('value')) {
		var trOClone = trO.clone(true);
		trO.remove();
		trPre.before(trOClone);
	}
	var paras = "dataBus=setContext&contextKey=" + boName + "&contextValue="
			+ pre + '&base=' + base + '&pre=' + pre;

	callService({
		serviceName : serviceName,
		paras : paras,
		callType : 'uf'
	});

}
function sortDown(o, boName, serviceName) {

	var trO = $(o).parent().parent();
	var pre = trO.attr('value');
	var trNext = trO.next();
	var base = trNext.attr('value');
	if (trNext.attr('value')) {
		var trOClone = trO.clone(true);
		trO.remove();
		trNext.after(trOClone);
	}

	var paras = "dataBus=setContext&contextKey=" + boName + "&contextValue="
			+ pre + '&base=' + base + '&pre=' + pre;

	callService({
		serviceName : serviceName,
		paras : paras,
		callType : 'uf'
	});

}

// //////////////////////////////无刷新 flash上传
function uploadify(uploadifyID, uploadifyQueueID, fileDesc, fileExt,
		autoUpload, sessionid, uploadActionFile) {

	if (fileDesc == null || $.trim(fileDesc) == "") {
		fileDesc = 'All Files';// EELang.onlySelectFile;
	}
	if (fileExt == null || $.trim(fileExt) == "") {
		fileExt = '*.*'; // '*.jpg;*.gif;*.bmp';
	}
	if (autoUpload == null) {
		autoUpload = true;
	}
	if (uploadActionFile == null || uploadActionFile == "") {
		uploadActionFile = "upload_action_uploadify.jsp";
	}

	var o = $("#" + uploadifyID).prev().find(":only-child");
	$("#" + uploadifyID)
			.uploadify(
					{
						'swf' : globalURL
								+ 'web/default/js/jquery-plugin/fileuploader/uploadify.swf',
						'formData' : {
							'jsessionid' : sessionid
						},
						'uploader' : globalURL + 'web/default/'
								+ uploadActionFile + ';jsessionid=' + sessionid, // /
						// /eestorage/
						// ec2
						// upload
						'checkExisting' : globalURL
								+ 'web/default/upload_action_uploadify_exists.jsp;jsessionid='
								+ sessionid,
						'auto' : autoUpload,
						'multi' : false,
						'simUploadLimit' : 2,
						'buttonText' : EELang.browse,
						'wmode' : 'transparent',
						'width' : 75,
						'height' : 25,
						'fileTypeDesc' : fileDesc,
						'fileTypeExts' : fileExt,
						'onSelect' : function(fileObj) {
							o.val(fileObj.name);
						},
						'onProgress' : function(fileObj) {
							o.val(fileObj.name);
						},
						'onCheck' : function(file, exists) {
							if (exists) {
								alert('The file ' + file.name
										+ ' exists on the server.');
							}
						}

					});
}

// //////////////////////////////无刷新 flash上传 ,可以上传多个文件
function uploadifyMulti(uploadifyID, uploadifyQueueID, fileDesc, fileExt,
		autoUpload, sessionid, uploadActionFile) {

	if (fileDesc == null || $.trim(fileDesc) == "") {
		fileDesc = 'All Files';// EELang.onlySelectFile;
	}
	if (fileExt == null || $.trim(fileExt) == "") {
		fileExt = '*.*'; // '*.jpg;*.gif;*.bmp';
	}
	if (autoUpload == null) {
		autoUpload = true;
	}
	if (uploadActionFile == null || uploadActionFile == "") {
		uploadActionFile = "upload_action_uploadify.jsp";
	}

	var o = $("#" + uploadifyID).prev().find(":only-child");
	$("#" + uploadifyID)
			.uploadify(
					{
						'swf' : globalURL
								+ 'web/default/js/jquery-plugin/fileuploader/uploadify.swf',
						'formData' : {
							'jsessionid' : sessionid
						},
						'uploader' : globalURL + 'web/default/'
								+ uploadActionFile + ';jsessionid=' + sessionid,
						'checkExisting' : globalURL
								+ 'web/default/upload_action_uploadify_exists.jsp;jsessionid='
								+ sessionid,
						'queueID' : uploadifyQueueID,
						'auto' : autoUpload,
						'multi' : true,
						'simUploadLimit' : 2,
						'buttonText' : EELang.browse,
						'wmode' : 'transparent',
						'width' : 75,
						'height' : 25,
						'fileTypeDesc' : fileDesc,
						'fileTypeExts' : fileExt,
						'onSelect' : function(fileObj) {
							if (o.val() == "") {
								o.val(o.val() + fileObj.name);
							} else {
								o.val(o.val() + ";" + fileObj.name);
							}
						},
						'onCancel' : function(fileObj) {
							o.val(o.val().replace(fileObj.name, ""));
						}
					});
}

/**
 * 分页
 * 
 * @param dataKey
 * @param pmlName
 * @param formName
 */
function pageSplit(dataKey, pmlName, formName) {

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
				var pmlUrl = getPmlUrl(pmlName, 1, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				loadPml({
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
				var pmlUrl = getPmlUrl(pmlName, curPageNo, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				loadPml({
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
				var pmlUrl = getPmlUrl(pmlName, curPageNo, $(
						"#" + dataKey + " .rowSize").text().replace(",", ""));
				loadPml({
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
						var pmlUrl = getPmlUrl(pmlName, $(
								"#" + dataKey + " .pageSize").text().replace(
								",", ""), $("#" + dataKey + " .rowSize").text()
								.replace(",", ""));
						loadPml({
							'pml' : pmlUrl,
							'target' : pmlName,
							'formName' : formName
						});
					});
}

function getPmlUrl(pmlName, pageNo, pageSize) {
	return globalURL + pmlName + ".pml?pageSize=" + pageSize + "&pageNo="
			+ pageNo;
}

/**
 * jquery validator 扩展
 */
try {
	// 手机号码验证
	$.validator.addMethod("mobile", function(value, element) {
		var length = value.length;
		var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, EELang.vMobile);

	// 电话号码验证
	$.validator.addMethod("phoneChina", function(value, element) {
		var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
		return this.optional(element) || (tel.test(value));
	}, EELang.vPhone);

	// 兼容老版本的校验类型
	$.validator.addMethod("telphone", function(value, element) {
		var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
		return this.optional(element) || (tel.test(value));
	}, EELang.vPhone);

	// 兼容老版本的校验类型
	$.validator.addMethod("Integer", function(value, element) {
		return this.optional(element) || (!checkErrNum(value));
	}, EELang.vInt);

	// 兼容老版本的校验类型
	$.validator.addMethod("RealNumber", function(value, element) {
		return this.optional(element) || (!checkErrFloat(value));
	}, EELang.vNumber);

	// 兼容老版本的校验类型
	$.validator.addMethod("nochin", function(value, element) {
		return this.optional(element) || (!funcChin(value));
	}, EELang.vSBC);

	// 兼容老版本的校验类型
	$.validator.addMethod("NoSBCDot", function(value, element) {
		return this.optional(element) || (!funcSBCDot(value));
	}, EELang.vSBCDOT);

	// 兼容老版本的校验类型
	$.validator.addMethod("idcard", function(value, element) {
		var checkResult = checkIdCard(value);
		return this.optional(element) || (checkResult == "yes");
	}, "请输入正确的身份证号码");

	$.validator.addMethod("gt", function(value, element, param) {
		if ($(param).size() > 0 && $(param).val() != '') {
			return value > $(param).val();
		} else {
			return true;
		}
	}, $.validator.format("输入值必须大于{0}!"));
	

	$.validator.addMethod("lt", function(value, element, param) {
		if ($(param).size() > 0 && $(param).val() != '') {
			return value < $(param).val();
		} else {
			return true;
		}
	}, $.validator.format("输入值必须小于{0}!"));

	$.validator.addMethod("gtdate", function(value, element, param) {
		if ($(param).size() > 0 && $(param).val() != '') {
			var date1 = new Date(value.replace('-', '/'));
			var date2 = new Date($(param).val().replace('-', '/'));
			return date1 > date2;
		} else {
			return true;
		}
	}, $.validator.format("输入值必须大于{0}!"));

	$.validator.addMethod("ltdate", function(value, element, param) {
		if ($(param).size() > 0 && $(param).val() != '') {
			var date1 = new Date(value.replace('-', '/'));
			var date2 = new Date($(param).val().replace('-', '/'));
			return date1 < date2;
		} else {
			return true;
		}
	}, $.validator.format("输入值必须小于{0}!"));
	
	
	$.validator.addMethod("gtavalue", function(value, element, param) {
		if (param!=null) {
			return value > param;
		} else {
			return true;
		}
	}, $.validator.format("输入值必须大于{0}!"));
	
	
	$.validator.addMethod("ltavalue", function(value, element, param) {
		if (param!=null) {
			return value < param;
		} else {
			return true;
		}
	}, $.validator.format("输入值必须小于{0}!"));
	
	
} catch (e) {

	alert(e);
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

ep.selectLis = function(modelUid, varPageNo) {

	var serviceName = $("#" + modelUid).attr("serviceName");
	var pageNo = 1;
	if (varPageNo) {
		pageNo = varPageNo;
	}

	$("#d" + modelUid + " .dropdown-menu li").remove();

	var paras = "";
	if ($("#input" + modelUid).size() > 0) {
		paras = $("#" + modelUid).attr("searchColName") + "="
				+ $("#input" + modelUid).val() + "&sc_page_no=" + pageNo
				+ "&sc_page_size=" + ep.selectPageSize;
	}
	try {
		var obj = $("#" + modelUid);
		var rs = obj.parent().parent().find('.resultlistpopup');
		if (rs.length == 1) {
			rs = obj.parent().parent().parent().find('.resultlistpopup');
		}
		var not2This = true;
		if (rs.length > 1) {
			rs.each(function(i) {

				if (not2This) {
					if ($(rs.get(i)).attr("id") == obj.attr("id")) {
						not2This = false;
					}

					var o = $(rs.get(i));
					paras = paras + "&" + o.attr('name') + "=" + o.val();
				}
			});

		}
	} catch (e) {
		alert(e);
	}

	callService({
		'serviceName' : serviceName,
		'callType' : 'ss',
		'paras' : paras,
		'callback' : function(data) {

			if ($("#pre" + modelUid).size() > 0) {

				if (pageNo == 1) {
					$("#pre" + modelUid).removeClass("enable").addClass(
							"disable");
				} else if (pageNo == 1 && data.sc_pages == 1) {
					$("#pre" + modelUid).removeClass("enable").addClass(
							"disable");
					$("#next" + modelUid).removeClass("enable").addClass(
							"disable");
				} else if ($(data.items).size() < ep.selectPageSize) {
					$("#next" + modelUid).removeClass("enable").addClass(
							"disable");
				} else if (pageNo == data.sc_pages) {
					$("#next" + modelUid).removeClass("enable").addClass(
							"disable");
				} else {
					$("#pre" + modelUid).removeClass("disable").addClass(
							"enable");
					$("#next" + modelUid).removeClass("disable").addClass(
							"enable");
				}
			}

			$(data.items)
					.each(
							function(i, o) {

								var ovalue = "";
								if (o.objuid) {
									ovalue = o.objuid;
								} else if (o.value) {
									ovalue = o.value;
								}

								var liNodeStr = "<li role='presentation'  idvalue='"
										+ ovalue
										+ "'><a role='menuitem' tabindex='-1'><i class='"
										+ ovalue
										+ "'></i>"
										+ o.name
										+ "</a></li>";

								if ($("#split" + modelUid).size() > 0) {
									$("#split" + modelUid).before(liNodeStr);
								} else {
									$("#d" + modelUid + " .dropdown-menu")
											.append(liNodeStr);
								}
							});

			if ($("#d" + modelUid + " .dropdown-menu .input-group").size() > 0) {
				$("#d" + modelUid + " .dropdown-menu .input-group")
						.after(
								"<li role='presentation'  idvalue=''><a role='menuitem' tabindex='-1'>&nbsp;&nbsp;</a></li>");
			} else {
				$("#d" + modelUid + " .dropdown-menu")
						.prepend(
								"<li role='presentation'  idvalue=''><a role='menuitem' tabindex='-1'>&nbsp;&nbsp;</a></li>");
			}
		}
	});// /end callService
}

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
							p.paras = getParasOfForms(p.conFormName);
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
				loadPmlHelper(p);
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
//		loadPmlHelper(p);
//	});
//}