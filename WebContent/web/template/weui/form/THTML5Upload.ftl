 <#--定义dataBinding-->
<#assign dataBind = "com.exedosoft.plat.template.BindData2FormModel"?new()> 
<#assign i18n = "com.exedosoft.plat.template.TPLI18n"?new()> 
  
  <div class="weui_cell">
    <div class="weui_cell_bd weui_cell_primary">
      <div class="weui_uploader">
        <div class="weui_uploader_hd weui_cell">
          <div class="weui_cell_bd weui_cell_primary">${model.l10n?default('图片上传')}</div>
         </div>
        <div class="weui_uploader_bd">
          <ul class="weui_uploader_files">
            <li class="weui_uploader_file" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
            <li class="weui_uploader_file" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
            <li class="weui_uploader_file weui_uploader_status" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)">
              <div class="weui_uploader_status_content">50%</div>
            </li>
          </ul>
          <div class="weui_uploader_input_wrp">
                  <input type="file"  id="${model.fullColID}_show"  name="${model.colName}_show"  class="weui_uploader_input" type="file" accept="image/jpg,image/jpeg,image/png,image/gif" multiple=""/> 
           </div>
        </div>
      </div>
    </div>
  </div>


  <script>
////multiple="multiple" 这个属性可以支持多个文件上传	
	   $("#${model.fullColID}_show").html5_upload({
	   				autoclear:false,
                    url: "web/default/upload_action_uploadify_aliyun.jsp",
                    sendBoundary: window.FormData || $.browser.mozilla,
                    onFinishOne : function(event, progress, name, number, total) {
                    }
                });

  </script>  