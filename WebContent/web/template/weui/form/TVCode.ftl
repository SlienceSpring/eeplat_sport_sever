		 <div class="weui_cell weui_vcode">
		    <div class="weui_cell_hd">${model.l10n}</div>
		    <div class="weui_cell_bd weui_cell_primary">
		      <input name="${model.colName}" id="${model.fullColID}" ${validStr!!} class="weui_input"  value="${model.value?if_exists}" placeholder="请输入验证码">
		    </div>
		    <div class="weui_cell_ft">
		      <img src="web/default/image.jsp" onclick="javascript:$(this).attr('src','web/default/image.jsp?'+ Math.random())">
		    </div>
		  </div>
 