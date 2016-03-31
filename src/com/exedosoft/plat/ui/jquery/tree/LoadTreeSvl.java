package com.exedosoft.plat.ui.jquery.tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DAOUtil;
import com.exedosoft.plat.DOServletContext;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOParameter;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOPaneModel;
import com.exedosoft.plat.ui.DOTreeModel;
import com.exedosoft.plat.util.DOGlobals;

/**
 * Servlet implementation class for Servlet: LoadTreeSvl
 * 
 */
public class LoadTreeSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5836729213406076319L;

	private static final String CONTENT_TYPE = "text/xml; charset=utf-8";

	private static Log log = LogFactory.getLog(LoadTreeSvl.class);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoadTreeSvl() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(CONTENT_TYPE);

		// this.getServletConfig(), this
		// .getServletContext(),
		// //////////////////////////初始化全局上下文
		DOGlobals.getInstance().refreshContext(
				new DOServletContext(request, response));

		DOGlobals.getInstance().getSessoinContext().getThreadContext()
				.initDataBus();

		String instanceUid = request.getParameter("contextInstanceUid");
		// System.out.println("instanceUid" + instn)
		String treeModelUid = request.getParameter("treeModelUid");
		String containerPaneUid = request.getParameter("containerPaneUid");

		// System.out.println("Content Pane==========" + containerPaneUid);

		String initOnly = request.getParameter("initOnly");
		String dropDownID = request.getParameter("dropDownID");

		String result = null;
		// HbmDAO dao = new HbmDAO();
		// dao.setAutoClose(false);
		try {

			DOTreeModel treeModel = DAOUtil.INSTANCE().getByObjUid(
					DOTreeModel.class, treeModelUid);
			if (dropDownID != null) {
				treeModel.setDropDownID(dropDownID);
			}

			// //////////////初始化当前上下文环境1
			if (instanceUid != null) {
				if (treeModel.getParent() != null) {
					DOService parentService = null;
					if (treeModel.getParent().getService() != null) {
						parentService = treeModel.getParent().getService();
					} else {
						parentService = this.getIfParentService(treeModel
								.getParent());
					}
					if (parentService != null) {
						parentService.getBo().refreshContext(instanceUid);
					}

				}
			}
			if (initOnly != null) {
				log.info("instanceUid::" + instanceUid);
				log.info("treeModelUid::" + treeModelUid);
				/**
				 * 如果是初始化上下文环境，执行到这儿就返回。
				 */
				return;
			}

			DOPaneModel containerPaneModel = null;
			if (containerPaneUid != null) {
				containerPaneModel = DOPaneModel
						.getPaneModelByID(containerPaneUid);
				treeModel.setContainerPane(containerPaneModel);

			}

			log.info("treeModel.getStatus()::::" + treeModel.getStatus());

			switch (treeModel.getStatus()) {

			case DOTreeModel.STATUS_SELFLINK_ONLY:
				result = dealSelfLinkOnly(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_SELFLINK_CHILD:
				result = this.dealSelfLinkChild(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_NULL:
				result = this.dealChildNull(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_ONE:
				result = this.dealChildOne(treeModel, instanceUid);
				break;
			case DOTreeModel.STATUS_CHILD_MULT:
				result = this.dealChildMult(treeModel, instanceUid);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally {
		// dao.closeSession();
		// }
		PrintWriter out = response.getWriter();
		out.println(result);

	}

	/**
	 * @param treeModel
	 */
	private String dealSelfLinkOnly(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<tree>\n");

		dealSelfLinkOnlyHelper(treeModel, list, buffer, instanceUid);

		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param list
	 * @param buffer
	 */
	private void dealSelfLinkOnlyHelper(DOTreeModel treeModel, List list,
			StringBuffer buffer, String instanceUid) {

		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");

			buffer.append(instance.getName());

			this.appendIconXML(treeModel, buffer);

			List listChilds = treeModel.getService().invokeSelect(
					instance.getUid());

			// ////////执行下面的代码可以有两个条件
			if ((listChilds != null && listChilds.size() > 0) || // //////如果有数据
					(treeModel.getChildren() != null && treeModel.getChildren()
							.size() > 0) // ///下面还有层次
			) {

				appendSrc(treeModel.getObjUid(), treeModel, instance.getUid(),
						buffer);
			}else{
				appendSrcHelper(treeModel.getObjUid(), treeModel, instance.getUid(),
						buffer,false);
			}

			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml 关闭符号
		}

	}

	/**
	 * @param treeModel
	 */
	private String dealSelfLinkChild(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);

		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<tree>\n");
		dealSelfLinkOnlyHelper(treeModel, list, buffer, instanceUid);
		dealMultChildHelper(treeModel, buffer, instanceUid);
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * 处理一个节点拥有多个子节点的情况
	 * 
	 * @param treeModel
	 * @return
	 */
	private String dealChildMult(DOTreeModel treeModel, String instanceUid) {

		// ////如果没有配置Service,展开时没有相关数据库数据
		// ////////仅仅作为分类使用
		if (treeModel.getService() == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\n<tree>\n");
			dealMultChildHelper(treeModel, buffer, instanceUid);
			buffer.append("</tree>\n");
			return buffer.toString();
		}

		// ///////////当前的查询
		List list = treeModel.getService().invokeSelect(instanceUid);

		List children = treeModel.getChildren();

		

		// ///////////判断如果有两个child,并且有一个child是子连接，并且这个child对应的查询没有值，
		// 那么可以作为STATUS_CHILD_ONE处理  这个处理有bug 屏蔽掉
//		if (children.size() == 2 && instanceUid!=null) {
//
//			DOTreeModel commModel = null;
//			DOTreeModel selfModel = null;
//			DOTreeModel childModel0 = (DOTreeModel) children.toArray()[0];
//			DOTreeModel childModel1 = (DOTreeModel) children.toArray()[1];
//
//			if (childModel0.isSelf()) {
//				selfModel = childModel0;
//				commModel = childModel1;
//			}
//			if (childModel1.isSelf()) {
//				selfModel = childModel1;
//				commModel = childModel0;
//			}
//			if (commModel != null && selfModel != null) {
//				List listChilds = selfModel.getService().invokeSelect(
//						instanceUid);
//				if (listChilds.size() == 0) {
//
//					StringBuffer buffer = new StringBuffer();
//					buffer.append("<tree>\n");
//
//					dealChildOneHelper(treeModel, instanceUid, list, buffer,
//							commModel);
//					buffer.append("</tree>\n");
//					return buffer.toString();
//				}
//			}
//		}

		// ///////////////////end 判断

		int i = 0;

		if (list != null) {
			i = list.size();
		}
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");
		for (Iterator it = list.iterator(); it.hasNext();) {

			// //其中一个子节点
			BOInstance instance = (BOInstance) it.next();

			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);
			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\">\n");
			if (i != 0) {
				dealMultChildHelper(treeModel, buffer, instance.getUid());
			}
			buffer.append("</tree>");
		}
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param buffer
	 */
	private void dealMultChildHelper(DOTreeModel treeModel,
			StringBuffer buffer, String instanceUid) {

		for (Iterator itChild = treeModel.getChildren().iterator(); itChild
				.hasNext();) {
			DOTreeModel childModel = (DOTreeModel) itChild.next();
			childModel.setDropDownID(treeModel.getDropDownID());
			if (childModel.isSelf()) {
				log.info("Enter is Self::::" + childModel.getL10n());
				setTempPara(instanceUid, treeModel, childModel);
				List list = childModel.getService().invokeSelect();
				childModel.setContainerPane(treeModel.getContainerPane());
				dealSelfLinkOnlyHelper(childModel, list, buffer, instanceUid);
			} else {

				if (childModel.getService() == null) {
					buffer.append("\n<tree text=\"").append(
							childModel.getL10n());
					this.appendIconXML(treeModel, buffer);

					appendSrc(childModel.getObjUid(), treeModel, instanceUid,
							buffer);
					buffer.append("\"/>\n"); // / xml 关闭符号

				} else {

					List listChilds = childModel.getService().invokeSelect(
							instanceUid);

					if (listChilds != null && listChilds.size() > 0) {

						buffer.append("\n<tree text=\"").append(
								childModel.getL10n());
						this.appendIconXML(treeModel, buffer);

						appendSrc(childModel.getObjUid(), treeModel,
								instanceUid, buffer);
						buffer.append("\"/>\n"); // / xml 关闭符号
					}

				}
			}

		}
	}

	private void setTempPara(String instanceUid, DOTreeModel treeModel,
			DOTreeModel selfLinkModel) {

		for (Iterator it = selfLinkModel.getService()
				.retrieveParaServiceLinks().iterator(); it.hasNext();) {
			DOParameterService dops = (DOParameterService) it.next();
			DOParameter dop = dops.getDop();
			if (dop.getParaBO().getObjUid()
					.equals(treeModel.getService().getBo().getObjUid())) {
				selfLinkModel.getService().addTempParaValue(dops, instanceUid);
				return;
			}
		}

	}

	/**
	 * @param list
	 * @param buffer
	 */
	private String dealChildNull(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");
		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);
			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml 关闭符号
		}
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	/**
	 * @param list
	 * @param buffer
	 */
	private String dealChildOne(DOTreeModel treeModel, String instanceUid) {

		List list = treeModel.getService().invokeSelect(instanceUid);
		addListforNew(list, treeModel);
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tree>\n");

		DOTreeModel childModel = (DOTreeModel) treeModel.getChildren()
				.toArray()[0];
		dealChildOneHelper(treeModel, instanceUid, list, buffer, childModel);
		buffer.append("</tree>\n");
		return buffer.toString();
	}

	private void dealChildOneHelper(DOTreeModel treeModel, String instanceUid,
			List list, StringBuffer buffer, DOTreeModel childModel) {
		childModel.setDropDownID(treeModel.getDropDownID());
		for (Iterator it = list.iterator(); it.hasNext();) {
			BOInstance instance = (BOInstance) it.next();
			buffer.append("\n<tree text=\"");
			buffer.append(instance.getName());
			this.appendIconXML(treeModel, buffer);

			// /这个地方可以放开，但是放开后，如果数据量大了，效率会急剧降低

			// List listChilds = childModel.getService().invokeSelect(
			// instance.getUid());
			// if (listChilds != null && listChilds.size() > 0) {

			List listChilds = childModel.getService().invokeSelect(
					instance.getUid());
			if (listChilds != null && listChilds.size() > 0) {
				appendSrc(childModel.getObjUid(), treeModel, instance.getUid(),
						buffer);
			}else{
				appendSrcHelper(childModel.getObjUid(), treeModel, instance.getUid(),
						buffer,false);
			}
			

			// }

			appendAction(treeModel, buffer, instance, instanceUid);
			buffer.append("\"/>\n"); // / xml 关闭符号
		}
	}

	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param buffer
	 * @param childModel
	 */
	private void appendSrc(String linkTreeModelUid, DOTreeModel treeModel,
			String instanceUid, StringBuffer buffer) {
		this.appendSrcHelper(linkTreeModelUid, treeModel, instanceUid, buffer, true);
	}
	
	
	/**
	 * @param treeModel
	 * @param instanceUid
	 * @param buffer
	 * @param childModel
	 */
	private void appendSrcHelper(String linkTreeModelUid, DOTreeModel treeModel,
			String instanceUid, StringBuffer buffer,boolean isSrc) {
		if (instanceUid == null) {
			return;
		}
		if(isSrc){
			buffer.append("\" src=\"");
		}else{
			buffer.append("\" baksrc=\"");
		}
		appendBaseUrl(linkTreeModelUid, instanceUid, buffer);
		if (treeModel.getContainerPane() != null) {
			buffer.append("&amp;containerPaneUid=").append(
					treeModel.getContainerPane().getObjUid());
		}
		if (treeModel.getDropDownID() != null) {
			buffer.append("&amp;dropDownID=").append(treeModel.getDropDownID());

		}

		appendIconURL(treeModel, buffer, true);
	}

	public static void appendIconURL(DOTreeModel treeModel,
			StringBuffer buffer, boolean isXmlTrans) {

		// if (treeModel.getService() != null) {
		//
		// System.out.println("ICON:::::::::::::::"
		// + treeModel.getService().getBo().getIcon());
		// }

		String andSymbol = "&";
		if (isXmlTrans) {
			andSymbol = "&amp;";
		}

		if (treeModel.getIcon() != null
				&& !treeModel.getIcon().trim().equals("")) {
			buffer.append(andSymbol).append("icon=")
					.append(treeModel.getIcon());
		} 
//		else {
//			if (treeModel.getService() != null
//					&& treeModel.getService().getBo().getIcon() != null
//					&& !treeModel.getService().getBo().getIcon().trim()
//							.equals("")) {
//				buffer.append(andSymbol).append("icon=")
//						.append(treeModel.getService().getBo().getIcon());
//			}
//
//		}

		if (treeModel.getOpenIcon() != null
				&& !treeModel.getOpenIcon().trim().equals("")) {
			buffer.append(andSymbol).append("openIcon=")
					.append(treeModel.getOpenIcon());
		} 
//		else {
//
//			if (treeModel.getService() != null
//					&& treeModel.getService().getBo().getOpenIcon() != null
//					&& !treeModel.getService().getBo().getOpenIcon().trim()
//							.equals("")) {
//				buffer.append(andSymbol).append("openIcon=")
//						.append(treeModel.getService().getBo().getOpenIcon());
//			}
//		}
	}

	private void appendIconXML(DOTreeModel treeModel, StringBuffer buffer) {

		if (treeModel.getIcon() != null
				&& !treeModel.getIcon().trim().equals("")) {
			buffer.append("\" icon=\"").append(treeModel.getIcon());
		}
		
		//另作他用了
//		else {
//			if (treeModel.getService() != null
//					&& treeModel.getService().getBo().getIcon() != null
//					&& !treeModel.getService().getBo().getIcon().trim()
//							.equals("")) {
//
//				buffer.append("\" icon=\"").append(
//						treeModel.getService().getBo().getIcon());
//			}
//
//		}

		if (treeModel.getOpenIcon() != null
				&& !treeModel.getOpenIcon().trim().equals("")) {
			buffer.append("\" openIcon=\"").append(treeModel.getOpenIcon());
		}
//		else {
//
//			if (treeModel.getService() != null
//					&& treeModel.getService().getBo().getOpenIcon() != null
//					&& !treeModel.getService().getBo().getOpenIcon().trim()
//							.equals("")) {
//				buffer.append("\" openIcon=\"").append(
//						treeModel.getService().getBo().getOpenIcon());
//			}
//		}
	}

	/**
	 * @param linkTreeModelUid
	 * @param instanceUid
	 * @param buffer
	 */
	private void appendBaseUrl(String linkTreeModelUid, String instanceUid,
			StringBuffer buffer) {
		buffer.append("/").append(DOGlobals.URL)
				.append("/loadtreesvl?treeModelUid=");
		buffer.append(linkTreeModelUid);
		buffer.append("&amp;contextInstanceUid=").append(instanceUid);
	}

	/**
	 * @param treeModel
	 * @param buffer
	 * @param instance
	 * @param parentUid
	 *            TODO
	 */
	private void appendAction(DOTreeModel treeModel, StringBuffer buffer,
			BOInstance instance, String parentUid) {

		if (treeModel.getActionPane() != null
				&& treeModel.getDropDownID() == null) {

			appendActionUrl(treeModel, buffer, instance, parentUid);

			if (treeModel.getInitPane() != null) {

				String initUrl = treeModel.getInitPane().getFullCorrHref(
						instance, null);
				initUrl = initUrl.replaceAll("&", "&amp;");
				// /////////////////////确定targetPaneID
				String targetPaneId = treeModel.getInitPane().getName();
				if (treeModel.getInitPane().getTargetPane() != null) {
					targetPaneId = treeModel.getInitPane().getTargetPane()
							.getName();
				}
				// //////////////////////确定targetPaneID
				// $("#divMain").load();
				// loadPml({
				// 'pml':'${model.linkPaneModel.name}',
				// 'title':'${model.linkPaneModel.title}',
				// 'formName':'a${model.gridModel.objUid}'
				// <#if (item.targetPaneModel)?exists>
				// ,'target':'${model.targetPaneModel.name}'
				// </#if> }
				// );

				buffer.append(";loadPml({'pml':'")
						.append(treeModel.getActionPane().getName())
						.append("','pmlName':'")
						.append(treeModel.getActionPane().getName())
						.append("','title':'").append(instance.getName())
						.append("','target':'")
						.append(treeModel.getTargetPane().getName())
						.append("'} );");

				// buffer.append("\"   action=\"javascript:$('#"
				// + treeModel.getTargetPane().getName().replace(".", "_")
				// + "').empty().load('"
				// + treeModel.getActionPane().getFullCorrHref(instance,
				// null).replaceAll("&", "&amp;") + "");
				// buffer.append("');");
			}

		} else if (treeModel.getDropDownID() != null) {

			// buffer.append("\"  action=\"javascript:dojo.widget.byId('").append(
			// treeModel.getDropDownID()).append("').onSetValues('")
			// .append(instance.getName()).append("','").append(
			// instance.getUid()).append("','").append(
			// treeModel.getDropDownID()).append("');");

		}
	}

	/**
	 * 当点击树节点时，触发的Action's URL
	 * 
	 * @param treeModel
	 * @param buffer
	 * @param instance
	 * @param parentUid
	 */
	private void appendActionUrl(DOTreeModel treeModel, StringBuffer buffer,
			BOInstance instance, String parentUid) {

		String actionUrl = "";
		String targetPaneId = "";
		if (treeModel.getNoRecordPane() != null && instance != null
				&& instance.getUid().equals("tree.new.record")) {
			actionUrl = treeModel.getNoRecordPane().getFullCorrHref(instance,
					null);
			// /////////////////////确定targetPaneID
			targetPaneId = treeModel.getNoRecordPane().getName();
			if (treeModel.getNoRecordPane().getTargetPane() != null) {
				targetPaneId = treeModel.getNoRecordPane().getTargetPane()
						.getName();
			}

		} else {
			actionUrl = treeModel.getActionPane().getFullCorrHref(instance,
					null);
			// /////////////////////确定targetPaneID
			targetPaneId = treeModel.getTargetPaneID();
		}

		if (treeModel.getParent() != null && !treeModel.isSelf()) {// /更新上一层的主键，理论上讲应该一直更新到最上层(需要界面层处理，更新xtree2.js下一步，只有界面层才清楚的知道上层节点的对应，并且可以为树节点加属性)
			StringBuffer sb = new StringBuffer(actionUrl)
				.append("&dataBus=setContext&contextKey=");
				if(treeModel.getIsNodeSplit()!= null &&
					treeModel.getIsNodeSplit() == 1){
					sb.append(treeModel.getService().getBo()
							.getName()).append("&contextValue=").append(instance.getUid());
				}else{
					sb.append(treeModel.getParent().getService().getBo()
							.getName()).append("&contextValue=").append(parentUid);
				}
		
			actionUrl = sb.toString();
		}

		actionUrl = actionUrl.replaceAll("&", "&amp;");

		buffer.append("\"   action=\"javascript:loadPml({'pml':'")
				.append(actionUrl).append("','pmlName':'")
				.append(treeModel.getActionPane().getName())
				.append("','title':'").append(instance.getName())
				.append("','target':'")
				.append(treeModel.getTargetPane().getName()).append("'} );");

		// buffer.append("\" id='").append(actionUrl).append(
		// "'  action=\"javascript:$('#"
		// + treeModel.getTargetPane().getName().replace(".", "_")
		// + "').empty().load('" + actionUrl);
		// $('#webfx-tree-row:eq(0)').parent('table .gMain').next('tr
		// .gRi').next('div #divMain').next('div #mRight')
		// if (!treeModel.isSelf() && parentUid != null) {
		// buffer.append("','");
		// this.appendBaseUrl(treeModel.getObjUid(), parentUid, buffer);
		// buffer.append("&amp;initOnly=init");
		// }
		// buffer.append("'");
		// // if (treeModel.getActionPane().getIsCache() != null
		// // && !treeModel.getActionPane().getIsCache().booleanValue()) {
		// buffer.append(",false");
		// }
		// buffer.append("');");
	}

	private void addListforNew(List list, DOTreeModel treeModel) {

		if (list != null && list.size() > 0) {
			return;
		}
		if (treeModel.getParent() != null) {
			return;
		}
		DOService service = treeModel.getService();
		if (service == null || service.getBo() == null) {
			return;
		}
		if (list == null) {
			list = new ArrayList();
		}
		BOInstance instance = new BOInstance();
		instance.setUid("tree.new.record");
		instance.setBo(service.getBo());
		instance.putValue(service.getBo().getValueCol(), "Add New");
		list.add(instance);

	}

	public DOService getIfParentService(DOTreeModel treeModel) {

		if (treeModel.getStatus() == DOTreeModel.STATUS_CHILD_MULT) {
			if (treeModel.getService() == null) {
				if (treeModel.getParent() != null) {
					if (treeModel.getParent().getService() != null) {
						return treeModel.getParent().getService();
					} else {
						return getIfParentService(treeModel.getParent());
					}
				}

			}
		}
		return null;
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
