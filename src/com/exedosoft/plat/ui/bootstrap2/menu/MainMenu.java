package com.exedosoft.plat.ui.bootstrap2.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.exedosoft.plat.bo.DOApplication;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.jquery.menu.DOBaseMenu;
import com.exedosoft.plat.util.StringUtil;

public class MainMenu extends DOBaseMenu {

	private static List<String> icons = new ArrayList<String>();

	public String getHtmlCode(DOIModel aModel) {

		DOMenuModel rootMenu = (DOMenuModel) aModel;
		StringBuffer buffer = new StringBuffer();

		buffer.append("       <ul class='nav nav-sidebar'>\n");

		List rootChildMenus = rootMenu.retrieveChildren();

		/**
		 * 对default菜单的映射，如果第一层菜单的名字和应用的名称相同，则直接把该菜单下面的自菜单直接放入第一层，而该菜单不再显示。
		 * 
		 * 或者只有一层菜单
		 */
		try {
			if (rootChildMenus != null && rootChildMenus.size() > 0) {
				DOMenuModel aChildMenu = (DOMenuModel) rootChildMenus.get(0);
				if ((aChildMenu.getName().equals(rootMenu.getCategory()
						.getPakage().getApplication().getName()))
						|| (rootChildMenus.size() < 3)) {
					rootChildMenus.remove(aChildMenu);
					rootChildMenus.addAll(0, aChildMenu.retrieveChildren());
				}
			}
		} catch (Exception e) {

		}

		for (Iterator it = rootChildMenus.iterator(); it.hasNext();) {
			DOMenuModel aMenu = (DOMenuModel) it.next();

			buffer.append("<li style='cursor:pointer'");

			if (aMenu.retrieveChildren() != null
					&& !aMenu.retrieveChildren().isEmpty()) {
				buffer.append(" role='tab'")
				.append(" >\n<a")
				.append(" data-toggle='collapse'  href='#")
				.append(aMenu.getObjUid())
				.append("' aria-expanded='false' aria-controls='")
				.append(aMenu.getObjUid())
				.append("' ");
				
			} else {
				buffer.append("> \n<a ");
			}

			buffer.append("  onclick=\"");
			if (aMenu.getLinkPane() != null || aMenu.getLinkService() != null) {
				buffer.append("");
				this.appendLink(buffer, aMenu, aMenu.getEchoJs());
			}

	buffer.append("\">");
			
	
	buffer.append("\n<span  class='").append(this.getIcon(aMenu))
	.append("'></span>&nbsp;").append(aMenu.getL10n())
	;
	
//			buffer.append("<span class='glyphicon glyphicon-file'>");
//			buffer.append("</span>").append(aMenu.getL10n());

			// ////有子菜单
			if (aMenu.retrieveChildren() != null
					&& !aMenu.retrieveChildren().isEmpty()) {
				buffer.append("  <span class='btn-arrow fa fa-chevron-down' ></span>");
			}

			buffer.append("</a>\n");

			// //submenu
			if (aMenu.retrieveChildren() != null
					&& !aMenu.retrieveChildren().isEmpty()) {
				buffer.append("<ul class='panel-collapse collapse list-unstyled' role='tabpanel'  id='")
				.append(aMenu.getObjUid())
				.append("' >");

				for (Iterator itChild = aMenu.retrieveChildren().iterator(); itChild
						.hasNext();) {
					DOMenuModel aChildMenu = (DOMenuModel) itChild.next();
					buffer.append("	<li><a href='#' onclick=\"");
					if (aChildMenu.getLinkPane() != null
							|| aChildMenu.getLinkService() != null) {
						this.appendLink(buffer, aChildMenu,
								aChildMenu.getEchoJs());
					}
					buffer.append("\">");

					buffer.append("<span class='caret'></span>")
							.append(aChildMenu.getL10n());
					buffer.append("</a></li>\n");
				}
				buffer.append("</ul>");
			}

			buffer.append("</li>");
		}
		buffer.append("</ul>");

		return buffer.toString();
	}

	private String getIcon(DOMenuModel aMenu) {
		if (aMenu.getIcon() != null ) {
			return  StringUtil.getFaIcon(aMenu.getIcon());
		}
		return "fa fa-file-o";
	}

	protected void appendLink(StringBuffer buffer, DOMenuModel aMenu,
			String echoStr) {

		if (aMenu.getMenuType() != null
				&& aMenu.getMenuType().intValue() == DOMenuModel.MENUTYPE_LINK) {

			buffer.append("window.open('")
					.append(aMenu.getNote())
					.append("','','height=760,   width=1012,left=0,top=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')");

			// window.open("/abp/guotu/gt_digiboard.jsp","login","height=760,   width=1012,left=0,top=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no")
		} else if (aMenu.getDoClickJs() != null) {

			buffer.append(aMenu.getDoClickJs());
		} else if (aMenu.getLinkPane() != null) {

			buffer.append("toggleLeftSize(),loadMenuPml(this,'")
					.append(aMenu.getLinkPane().getName()).append("','")
					.append(aMenu.getL10n()).append("')");

		} else if (aMenu.getLinkService() != null) {

		}

		else {
			buffer.append(getDecoration(aMenu));
		}
	}

	public static void main(String[] args) {

		String a = icons.get(Integer.valueOf(String.valueOf("ccccc".hashCode())
				.substring(0, 1)));

		System.out.println("aaaaaaaaaaaaaa::" + a);

	}
}