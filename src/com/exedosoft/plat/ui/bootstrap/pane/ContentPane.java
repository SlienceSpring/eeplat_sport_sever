package com.exedosoft.plat.ui.bootstrap.pane;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.SessionContext;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOMenuModel;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOTreeModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.ui.jquery.pane.TPaneTemplate;
import com.exedosoft.plat.util.DOGlobals;

public class ContentPane extends DOViewTemplate {

	private TPaneTemplate tpt = new TPaneTemplate();
	private static Log log = LogFactory.getLog(ContentPane.class);

	public ContentPane() {

		dealTemplatePath("/panel/ContentPane.ftl");
	}

	public Map<String, Object> putData(DOIModel doimodel) {

		String changePml = "";

		Map<String, Object> data = super.putData(doimodel);

		DOPaneModel pm = (DOPaneModel) doimodel;

		if (pm.retrieveChildren() != null && pm.retrieveChildren().size() > 0) {
			try {
				changePml = DOGlobals.getInstance().getSessoinContext()
						.getFormInstance().getValue("changePml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// /风格处理
		tpt.dealScripts(pm, data);

		StringBuffer buffer = new StringBuffer();

		dealRowAndCol(data, pm);

		genePaneContext(buffer, pm, changePml);
		data.put("items_html", buffer.toString());

		return data;
	}

	private void dealRowAndCol(Map<String, Object> data, DOPaneModel pm) {

		String wrapperRow = "";

		// /请求的是该面板

		String target = SessionContext.getInstance().getFormInstance()
				.getValue("target");
		
		///  由于是面板是递归的结构，第一次主面板利用完target 之后就删除。
		SessionContext.getInstance().getFormInstance().remove("target");
		
		DOPaneModel targetPm = null;
		if (target != null) {
			targetPm = DOPaneModel.getPaneModelByName(target);
		}

		String colSize = "col-xs-12";
		if (targetPm != null) {
			colSize = getColSize(targetPm, true);
		} else {
			colSize = getColSize(pm, false);
		}

		if (colSize.equals("col-xs-12")) {
			wrapperRow = "row";
		}
		data.put("wrapperRow", wrapperRow);

		data.put("wrapperCol", colSize);
	}

	private String getColSize(DOPaneModel pm, boolean isTarget) {

		// / 请求的面板是否是该面板
		String paneModelUid = SessionContext.getInstance().getFormInstance()
				.getValue("paneModelUid");

		String colSize = "col-xs-12";

		if (!isTarget) {
			if (pm.getObjUid().equals(paneModelUid)) {
				return colSize;
			}
		}

		if (pm.getParent() != null
				&& pm.getParent().getLayOutType() != null
				&& pm.getParent().getLayOutType().intValue() == DOPaneModel.LAYOUT_HORIZONTAL) {
			List children = pm.getParent().retrieveChildren();
			if (children.size() > 1) {

				if (children.size() == 2) {
					colSize = "col-md-6 col-xs-12";
				}

				else if (children.size() == 3) {
					colSize = "col-md-4 col-xs-12";
				}

				else if (children.size() == 4) {
					colSize = "col-md-3 col-xs-6";
				}

				else if (children.size() > 4) {
					colSize = "col-md-2 col-xs-6";
				}

				if (pm.getSizerWidth() != null) {

					log.info("pm.getSizerWidth():::" + pm.getSizerWidth());
					log.info("abc:::"
							+ BigDecimal
									.valueOf(12.00 * pm.getSizerWidth() / 100)
									.setScale(0, BigDecimal.ROUND_HALF_UP)
									.intValue());

					int iSize = BigDecimal
							.valueOf(12.00 * pm.getSizerWidth() / 100)
							.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					colSize = "col-md-" + iSize;
					if (iSize >= 4) {
						colSize = colSize + " col-xs-12";
					}

				}
			}

		}
		return colSize;
	}

	static void genePaneContext(StringBuffer buffer, DOPaneModel pm,
			String changePml) {

		List children = pm.retrieveChildren();
		if (children != null && children.size() > 0) {
			int i = 0;
			for (Iterator it = children.iterator(); it.hasNext();) {
				DOPaneModel model = (DOPaneModel) it.next();
				// ////////////把第二个改为pmStatus里面得pml
				boolean isChange = false;
				DOPaneModel changeModel = null;
				if (i == 1 ) {////限定第二个
					if (changePml!=null && !changePml.equals("") && !changePml.equals(model.getName())) {
						changeModel = DOPaneModel.getPaneModelByName(changePml);
						if (!changeModel.equals(model)) {
							isChange = true;
						}
					}
				}
				if (isChange && changeModel != null) {
					buffer.append("<div  class='row'>\n");
					buffer.append("<div id='").append(model.getName())
							.append("' class='col-xs-12'>\n");
					buffer.append(changeModel.getHtmlCode());
					buffer.append("</div></div>");
				} else {
					buffer.append(model.getHtmlCode());
				}
				i++;
			}
		} else {// 没有子面板

			if (pm.getLinkType() != null) {
				switch (pm.getLinkType().intValue()) {

				case DOPaneModel.LINKTYPE_MENU:
					if (pm.getLinkUID() != null
							&& !"".equals(pm.getLinkUID().trim())) {
						try {
							DOMenuModel mm = pm.getMenuModel();
							// if (pm.getLinkController() != null) {
							// mm.setController(pm.getLinkController());
							// }
							mm.setContainerPane(pm);
							buffer.append(mm.getHtmlCode());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;

				case DOPaneModel.LINKTYPE_GRIDMODEL:
					if (pm.getLinkUID() != null
							&& !"".equals(pm.getLinkUID().trim())) {
						try {
							DOGridModel gm = pm.getDOGridModel();
							// if (pm.getLinkController() != null) {
							// gm.setController(pm.getLinkController());
							// }
							gm.setContainerPane(pm);
							buffer.append(gm.getHtmlCode());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case DOPaneModel.LINKTYPE_TREEMODEL:

					if (pm.getLinkUID() != null
							&& !"".equals(pm.getLinkUID().trim())) {

						try {
							DOTreeModel tm = pm.getTreeModel();
							// if (pm.getLinkController() != null) {
							// tm.setController(pm.getLinkController());
							// }
							tm.setContainerPane(pm);
							if (pm.getDropDownID() != null) {
								tm.setDropDownID(pm.getDropDownID());
							}
							buffer.append(tm.getHtmlCode());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;

				}
			}

		}
	}

}
