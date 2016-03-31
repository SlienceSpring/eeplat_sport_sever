package com.exedosoft.plat.ui.bootstrap.pane;

import java.util.List;
import java.util.Map;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOApplication;
import com.exedosoft.plat.bo.DOResource;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.pane.TPaneTemplate;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.I18n;

public class LayOutHeader extends TPaneTemplate {

    public LayOutHeader() {
        dealTemplatePath("/panel/LayOutHeader.ftl");
    }

    public Map<String, Object> putData(DOIModel doimodel) {

        Map<String, Object> data = super.putData(doimodel);

        BOInstance user = DOGlobals.getInstance().getSessoinContext().getUser();

        if (user != null) {
            boolean isDev = false;

            String default_app_uid = user.getValue("default_app_uid");

            if ("1".equals(user.getValue("asrole")) || "2".equals(user.getValue("asrole"))) {
                isDev = true;
            }

            String title = "Applications";

            if (default_app_uid != null) {
                DOApplication defaultApp = DOApplication.getApplicationByID(default_app_uid);
                if (defaultApp != null) {
                    title = defaultApp.getL10n();
                }
            }

            List<DOApplication> apps = DOApplication.getApplications();

            StringBuffer buffer = new StringBuffer("<ul class='dropdown-menu'>\n");
            buffer.append("  <li class='header'>\n");
            buffer.append("You have " + apps.size()).append(" Applications. \n");
            buffer.append(" </li> \n");

            buffer.append("<li>\n   <ul class='menu'>\n");

            for (DOApplication app : apps) {
              
                if (app.isDefault() && default_app_uid == null) {
                    title = app.getL10n();
                }
                buffer.append("<li>\n");

                buffer.append("<a href=\"javascript:changeDefaultApp('")
                        .append(app.getObjUid())
                        .append("'),window.location='")
                        .append(app.getOuterLinkPaneStr())
                        .append("'\">\n");

                buffer.append("		<i class='ion ion-folder'></i> \n");
                buffer.append(app.getL10n());

                buffer.append("	</a>\n");
                buffer.append("</li>\n");
            }

            buffer.append("   </ul>\n </li>\n");

            buffer.append("<li class='footer'>\n");
            buffer.append("	<a href=\"javascript:loadPml({'pml':'appshare','target':'_opener_tab'})\"> \n");
            buffer.append(I18n.instance().get("从AppShare安装"));
            //buffer.append("		<i class='ion '></i> \n");
            buffer.append("	</a>\n");
            buffer.append("</li>\n");

            buffer.append("</ul>\n");

            String url = DOResource.getSpecialPath(DOResource.TYPE_LOGO_SMALL);

            if(url==null){
            	url = "web/default/images/logo_small_l.png";
            }
            
            if (url != null) {
                data.put("logoheader", url);
            }
//
//
//			System.out.println("appdropdown:::::::::::" + buffer);

            data.put("title", title);

            data.put("appdropdown", buffer);

            data.put("isdev", isDev);

        }else{
            data.put("title", "");

            data.put("appdropdown", "");

            data.put("isdev", false);

            
        }
        return data;

    }
}
