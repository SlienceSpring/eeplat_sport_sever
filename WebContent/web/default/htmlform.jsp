<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.ui.DOFormModel"%>
<%@ page import="com.exedosoft.plat.ui.DOGridModel"%>
<%@ page import="com.exedosoft.plat.bo.DOBO"%>
<%@ page import="com.exedosoft.plat.bo.BOInstance"%>

<%
	String compare = request.getParameter("compare");
	
	String formUid =   request.getParameter("formUid");
	
	DOFormModel form = DOFormModel.getFormModelByID(formUid);
	
	boolean isShow = true;
	
	if("true".equals(compare)){
		
		String updateGridName = "GM_" + form.getGridModel().getCategory().getName() + "_update";
		DOGridModel gridModel = DOGridModel.getGridModelByName(updateGridName);
		if(gridModel!=null){
			DOFormModel form2 = 	DOFormModel.getFormModelByProperty(gridModel,form.getRelationProperty().getObjUid());
			if(form2!=null){
				form = form2;
			}else{
				isShow = false;
			}
		}
		
	}
	
	String instanceuid = request.getParameter("instanceuid");
	String bouid = request.getParameter("bouid");
	if(bouid!=null && instanceuid!=null){
		DOBO bo = DOBO.getDOBOByID(bouid);
		if(bo!=null){
			BOInstance bi = bo.getInstance(instanceuid);
			form.setData(bi);
		}
	}else if(instanceuid!=null){
		BOInstance bi = new BOInstance();
		bi.putValue(form.getColName(), instanceuid);
		form.setData(bi);
	}
	String html = "";
	if(isShow){
		html = form.getHtmlValue();
	}
%>
<%=html%>






