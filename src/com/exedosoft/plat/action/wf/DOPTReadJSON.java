package com.exedosoft.plat.action.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.util.I18n;
import com.exedosoft.wf.pt.NodeDenpendency;
import com.exedosoft.wf.pt.PTNode;
import com.exedosoft.wf.pt.ProcessTemplate;

public class DOPTReadJSON extends DOAbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997150272030863948L;

	@SuppressWarnings("unchecked")
	@Override
	public String excute() throws ExedoException {

		DOBO bo = DOBO.getDOBOByName("do_pt_processtemplate");
		BOInstance curPt = bo.getCorrInstance();
		BOInstance echo = new BOInstance();
		if (curPt == null) {
			this.setEchoValue(I18n.instance().get("没有选择工作流模板!"));
			return NO_FORWARD;
		}
		ProcessTemplate pt = ProcessTemplate.getPTByID(curPt.getUid());

		StringBuilder json = new StringBuilder("{\"title\":\"")
		.append(pt.getPtName())
		.append("\"");
		
		
		List<BOInstance> biNodeList = new ArrayList<BOInstance>();
		List<BOInstance> biLineList = new ArrayList<BOInstance>();

		for (Iterator<PTNode> it = pt.retrieveNodes().iterator(); it.hasNext();) {
			PTNode aNode = it.next();
			String autoServiceName = "";
			String deciType = "";
			String authType = "";
			if(aNode.getAutoExcutesService()!=null){
				autoServiceName = aNode.getAutoExcutesService().getName();
			}
			if(aNode.getDecisionType()!=null){
				deciType = aNode.getDecisionType().toString();
			}
			if(aNode.getAuthType()!=null){
				authType = aNode.getAuthType().toString();
			}
			String paneName = "";
			if(aNode.getPane()!=null){
				paneName = aNode.getPane().getName();
			}
			BOInstance aNodeBI = new BOInstance();
			aNodeBI.putValue("id", aNode.getObjUid());
			aNodeBI.putValue("name", aNode.getNodeName());
			aNodeBI.putValue("left", aNode.getX());
			aNodeBI.putValue("top", aNode.getY());
			aNodeBI.putValue("type", aNode.getNodeTypeStr());
			aNodeBI.putValue("nodeStateShow", getDefault(aNode.getNodeStateShow()));
			aNodeBI.putValue("nodeStateShowBack", getDefault(aNode.getNodeStateShowBack()));
			aNodeBI.putValue("accessClass", getDefault(aNode.getAccessClass()));
			aNodeBI.putValue("specName", getDefault(aNode.getSpecName()));
			aNodeBI.putValue("passTxt", getDefault(aNode.getPassTxt()));
			aNodeBI.putValue("rejectTxt", getDefault(aNode.getRejectTxt()));
			aNodeBI.putValue("autoService", autoServiceName);
			aNodeBI.putValue("authType", authType);
			aNodeBI.putValue("paneName", paneName);
			aNodeBI.putValue("subflow", getDefault(aNode.getNodeExt1()));
			aNodeBI.putValue("decisionExpression", getDefault(aNode.getDecisionExpression()));
			aNodeBI.putValue("decisionType", deciType);
			aNodeBI.putValue("nodeDesc", getDefault(aNode.getNodeDesc()));

			biNodeList.add(aNodeBI);

			List postNodes = aNode.getPostNodeDepes();
			if (postNodes != null && postNodes.size() > 0) {

				for (Iterator<NodeDenpendency> itd = postNodes.iterator(); itd
						.hasNext();) {
					NodeDenpendency nd = itd.next();
					//这其实有问题
					
					
					if(nd.getPostNode()==null){
						System.out.println("the dependency has not postnode::" + nd.getPostNode());
						continue;
					}
					BOInstance aLineBI = new BOInstance();
					aLineBI.putValue("id", nd.getObjUid());
					aLineBI.putValue("from", nd.getPreNode().getObjUid());
					aLineBI.putValue("to", nd.getPostNode().getObjUid());
					aLineBI.putValue("type", "sl");
					if (nd.getCondition() != null) {
						aLineBI.putValue("condition", nd.getCondition().replace("&", "&amp;")
								.replace(">", "&gt;").replace("<",
										"&lt;").replace("'", "&apos;")
								.replace("\"", "&quot;"));
					}

					if (nd.getShowValue() != null) {
						aLineBI.putValue("name", nd.getShowValue());
					}

					biLineList.add(aLineBI);

				}
			}
		}
		
		// nodes
		int nodeNums = 1;
		json.append(",\"nodes\":{");
		for (Iterator<BOInstance> it = biNodeList.iterator(); it.hasNext();nodeNums++) {
			BOInstance aNode = it.next();
			if(nodeNums>1){
				json.append(",");
			}
			json.append("\"").append(aNode.getValue("id")).append("\":").append(aNode.getJsonString());
		}
		json.append("}");
		
		// lines
		int lineNums = 1;
		json.append(",\"lines\":{");
		for (Iterator<BOInstance> it = biLineList.iterator(); it.hasNext();lineNums++) {
			BOInstance aNode = it.next();
			if(lineNums>1){
				json.append(",");
			}
			json.append("\"").append(aNode.getValue("id")).append("\":").append(aNode.getJsonString());
		}
		json.append("}}");
		
		System.out.println("PT's JSON:::" + json);
		echo.putValue("jsonStr", json.toString());
		this.setInstance(echo);
		return DEFAULT_FORWARD;
	}

	
	private String getDefault(String src){
		if(src==null){
			return "";
		}
		return src;
	}
}
