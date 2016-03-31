<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.exedosoft.plat.bo.org.DOAuthSuite"%>
<%@page import="com.exedosoft.plat.ui.DOMenuModel"%>
<%@page import="com.exedosoft.plat.ui.DOGridModel"%>
<%@page import="com.exedosoft.plat.ui.DOFormModel"%>
<%@page import="com.exedosoft.plat.bo.DOBO"%>
<%@page import="com.exedosoft.plat.bo.BOInstance"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.exedosoft.plat.util.DOGlobals"%>
<%@ page import="com.exedosoft.plat.util.I18n"%>
<%@ page import="com.exedosoft.plat.SessionContext"%>
<%@ page import="com.exedosoft.plat.bo.DOApplication"%>

<%
String roleUid = request.getParameter("contextValue");
DOBO boBO = DOBO.getDOBOByName("do_org_role");
BOInstance roleBI = boBO.getInstance(roleUid);
String projectUid =request.getParameter("projectUid");
DOApplication  dop = null;
if(projectUid == null){
	dop = DOApplication.getDefaultApplication();
	if(dop==null){
		dop = DOApplication.getCurrentUsedApplication();
	}
	if(dop!=null){
		projectUid = dop.getObjUid();
	}
}else{
	dop = DOApplication.getApplicationByID(projectUid);
}
if(dop==null){
	out.print("没有找到赋权的工程！");
	return;
}

String projectChecked = "";
if(dop.isAccessByRole(roleUid)){
	projectChecked = " checked='checked' ";
}

String projectDisabled = "";
if(!dop.isFilter()){
	projectDisabled = " disabled='disabled' ";
}

%>

<html>
<head>

<script language="javascript">
  globalURL = "/<%=DOGlobals.URL%>/";
  function changeApp(obj){
	  if(obj!=null && obj.value!=null){ 
	  	window.location = "DO_Auth_Role.jsp?contextValue=<%=roleUid%>&projectUid=" + obj.value;
	  }
  }
</script>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<% if ("en".equals(DOGlobals.getValue("lang.local"))){ %>	
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_en.js"  ></script>
<% }else{ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/lang_zh.js"  ></script>
<% }%>

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.metadata.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/jquery-plugin/validate/jquery.validate.js" ></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/web/default/js/main/platAjax.js"  ></script>
<script>
  function checkThis(anItem){
	  $("input[parentid='" + anItem + "']").attr("checked",true)
  }
  function cancelThis(anItem){
	  $("input[parentid='" + anItem + "']").attr("checked",false)
  }
  function authManager(){
	  var parterUid = $('#parterUid').val();///角色ID
	  var menuAuth = getAuthStr("menuUid");
	  var fmAuth = getAuthStr("formUid");
	  var appAuth = getAuthStr("appUid");

	  var boNames = "";
	  var boAuths = "";
	  $(".bouid").each(
           function(i){
				var boName = $(this).val();
				if(i > 0){
					boNames = boNames + "," + boName;
				}else{
					boNames = boNames + boName;
				}
				var anAuth = getAuthStr(boName);
				boAuths =   boAuths + "&" + boName + "=" + anAuth ; 
           }
	  );
	  var paras = "parterUid=" + parterUid + "&menuAuth=" + menuAuth  + "&appAuth=" + appAuth
	            + "&fmAuth=" + fmAuth + "&boNames=" + boNames + boAuths;

      if(confirm('<%= I18n.instance().get("确定要修改权限吗？")%>')){
    		callAction({'actionName':'com.exedosoft.plat.action.DOAuthRoleSave',
    			'paras':paras,
    			'callback':cbkAuthManager});
      }
  }

  function cbkAuthManager(data){
	   alert('<%= I18n.instance().get("权限修改成功！")%>');
  }
  function getAuthStr(authType){

	  var menuAuth = "";
	  $("."+ authType).each(
			  function(i){
				  menuAuth = menuAuth + $(this).val();
				  if($(this).attr('checked')=='checked'){
					  menuAuth = menuAuth + ",1;";
				  }else{
					  menuAuth = menuAuth + ",0;";
				  }
			}
	  )
	  return menuAuth;
  }
</script>
<style> 
html,body,td,p,select,input,div,table,span{font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif,"宋体"}

input,button { height:20px;vertical-align:middle; }

input.ctlBtn,button {

	text-align:center;
  
    font-family:Verdana;*font-family:Georgia;_font-family:Tahoma;

    padding:0 10px 1px;*padding:3px 3px 1px;_padding:0 4px 1px;

    line-height:18px;*line-height:14px;_line-height:16px;

    height:25px;

}

</style>


<title><%= I18n.instance().get("权限设置")%></title>
</head>
<!--Other Scripts  -->
<body   leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br/>
                    <TABLE align=center bgColor=C6EBDE border=0 cellPadding=4 cellSpacing=1 width=780>
                      <TBODY> 
                      
                      <!-------------------------------- 工程权限定义 ----------------------------------------------------->                      
                      <TR bgcolor="#e6EEEE" > 
                        <TD colspan="2" ><b>:: <%= I18n.instance().get("请选择工程")%> ::</b></TD>
                      </TR>
  <%
 
 			 List filterApps = DOApplication.getApplicationsNoAuth();
			
 %>
                      <TR> 
                        <TD bgColor=#ffffff width="141" height="32">
                        
                        	<select style="height:25px" onchange="changeApp(this)" >
						   <%for(Iterator it  = filterApps.iterator(); it.hasNext();) { DOApplication doa = (DOApplication)it.next(); 
						   %>
						   <option value="<%=doa.getObjUid()%>" 
						   <% if(doa.getObjUid().equals(projectUid)) { %>selected<%}%> > <%=doa.getL10n()%>    </option>
						   
							<%} %>
						   </select>
                        
                        </TD>
                        <TD  bgColor=#ffffff width="620" height="32"> 
                             <input type="checkbox" name="appUid"  class="appUid"  id="a<%=dop.getObjUid()%>" <%=projectChecked%> <%=projectDisabled%>  value="<%=dop.getObjUid()%>">
                        </TD>

<!-------------------------------- 工程权限定义 ----------------------------------------------------->                      
                                               
                      
                      
                      <TR height=25 bgcolor="#e6EEEE"> 
                        <Td height="22" colspan="2"><b></>:: <%= I18n.instance().get("角色信息")%> ::</b></font></b></FONT>
                        <input type='hidden' id='parterUid' value='<%=roleUid %>' ></input>
                        </td>
                      </TR>
                      <TR > 
                        <TD bgColor=#ffffff width="141"><%= I18n.instance().get("角色ID")%>：</TD>
                        <TD  bgColor=#ffffff width="620"><%=roleBI.getValue("roleId") %>
                         </TD>
                      </TR>                      
                      <TR > 
                        <TD bgColor=#ffffff width="141"><%= I18n.instance().get("角色名称")%>：</TD>
                        <TD  bgColor=#ffffff width="620"><%=roleBI.getName()%></TD>
                      </TR>
                      <TR > 
                        <TD bgColor=#ffffff width="141"><%= I18n.instance().get("描述")%>：</TD>           
                        <TD  bgColor=#ffffff width="620"><%=roleBI.getValue4Null("description")%></TD>
                      </TR>
                          
 
                   
                      
<!-------------------------------- 数据权限定义 ----------------------------------------------------->                      
                      <TR bgcolor="#e6EEEE" > 
                        <TD colspan="2" ><b>:: <%= I18n.instance().get("数据权限定义")%> ::</b></TD>
                      </TR>
  <%
 			 List filterBOs = DOBO.getFilterDOBOsByProjectId(projectUid);
			 for(Iterator it = filterBOs.iterator(); it.hasNext();){
				 DOBO aBO = (DOBO)it.next();
 %>
                      <TR> 
                        <TD bgColor=#ffffff width="141" height="32"><%=aBO.getL10n() %>
                        <input type='hidden' class='bouid' value='<%=aBO.getName()%>' ></input>
                        </TD>
                        <TD  bgColor=#ffffff width="620" height="32"> 
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td> 
                             <%
								 List childrens =  aBO.getDSeleAllService().invokeSelect();
								 for(Iterator itChild = childrens.iterator(); itChild.hasNext();){
									 BOInstance aBI = (BOInstance)itChild.next(); 
									 String checked = "";
									 if(aBI.isAccessByRole(roleUid)){
										 checked = " checked='checked' ";
									 }
									 %>
                              <input type="checkbox" name="biUid" class="<%=aBO.getName()%>"  parentid="a<%=aBO.getObjUid()%>" id="a<%=aBI.getUid()%>" <%=checked%>  value="<%=aBI.getUid()%>">
                                <%=aBI.getName() %>

                               <%} %>
                              </td>
                            </tr>
                            <tr> 
                              <td align="right"> 
                                <input type="button" name="Button" value="<%= I18n.instance().get("全选")%>" class="ctlBtn" onClick="checkThis('a<%=aBO.getObjUid()%>')">
                                <input type="button" name="Submit3" value="<%= I18n.instance().get("取消")%>" class="ctlBtn" onClick="cancelThis('a<%=aBO.getObjUid()%>')" >
                              </td>
                            </tr>
                               </table>
                        </TD>
                      </TR>
           <%} %>
<!-------------------------------- 数据权限定义 ----------------------------------------------------->                      
                                                  
                      
                      
<!-------------------------------- 操作权限定义 ----------------------------------------------------->                      
                      <TR bgcolor="#e6EEEE" > 
                        <TD colspan="2" ><b>:: <%= I18n.instance().get("操作权限定义")%>  ::</b></TD>
                      </TR>
  <%
 
 			 List allFilters = DOGridModel.getFilterGridModelsByProjectUid(projectUid);
			 for(Iterator it = allFilters.iterator(); it.hasNext();){
				 DOGridModel grid = (DOGridModel)it.next();
 %>
                      <TR> 
                        <TD bgColor=#ffffff width="141" height="32"><%=grid.getCaption() %></TD>
                        <TD  bgColor=#ffffff width="620" height="32"> 
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td> 
                             <%
								 List childrens =  grid.getFilterGridFormLinks();
								 for(Iterator itChild = childrens.iterator(); itChild.hasNext();){
									 DOFormModel aFm = (DOFormModel)itChild.next(); 
									 String checked = "";
									 if(aFm.isAccessByRole(roleUid)){
										 checked = " checked='checked' ";
									 }
									 %>
                              <input type="checkbox" name="formUid"  class="formUid" parentid="a<%=grid.getObjUid()%>" id="a<%=aFm.getObjUid()%>" <%=checked%>  value="<%=aFm.getObjUid()%>">
                                <%=aFm.getL10n() %>
                                <br/>
                               <%} %>
                              </td>
                            </tr>
                            <tr> 
                              <td align="right"> 
                                <input type="button" name="Button" value="<%= I18n.instance().get("全选")%>" class="ctlBtn" onClick="checkThis('a<%=grid.getObjUid()%>')">
                                <input type="button" name="Submit3" value="<%= I18n.instance().get("取消")%>" class="ctlBtn" onClick="cancelThis('a<%=grid.getObjUid()%>')" >
                              </td>
                            </tr>
                               </table>
                        </TD>
                      </TR>
           <%} %>
<!-------------------------------- 操作权限定义 ----------------------------------------------------->                      
                            
                      
                      
                      
                      
<!-------------------------------- 菜单权限定义 ----------------------------------------------------->                      
                      <TR bgcolor="#e6EEEE" > 
                        <TD colspan="2" ><b>:: <%= I18n.instance().get("菜单权限定义")%> ::</b></TD>
                      </TR>
  <%
	 		List parents  = DOMenuModel.retrieveNoAuthMenusByProjectID(projectUid); //getMenuModelByName(doaName +  "_root");
			 for(Iterator it = parents.iterator(); it.hasNext();){
				 DOMenuModel pMenu = (DOMenuModel)it.next();
				 
 %>
                      <TR> 
                        <TD bgColor=#ffffff width="141" height="32"><%=pMenu.getL10n() %></TD>
                        <TD  bgColor=#ffffff width="620" height="32" style="padding:0px"> 
                          <table width="100%" bgColor=C6EBDE border=0 cellSpacing=1>
                       		<%
                       			StringBuffer buffer = new StringBuffer();
                       			getMenuAuthHtmls(buffer,roleUid,pMenu,pMenu,0);
                       		%>
                       		<%=buffer%>
                            <tr> 
                              <td bgColor=#ffffff align="right"> 
                                <input type="button" name="Button" value="<%= I18n.instance().get("全选")%>" class="ctlBtn" onClick="checkThis('a<%=pMenu.getObjUid()%>')">
                                <input type="button" name="Submit3" value="<%= I18n.instance().get("取消")%>" class="ctlBtn" onClick="cancelThis('a<%=pMenu.getObjUid()%>')" >
                              </td>
                            </tr>
                               </table>
                        </TD>
                      </TR>
           <%} %>
           <!-------------------------------- 菜单权限定义 ----------------------------------------------------->                      
                            
                       <TR > 
                       
                        <TD  bgColor=#ffffff colspan="2" align="center"> 
                          <input type="button" name="Button2" value=" <%=I18n.instance().get("修改权限")%>  " class="ctlBtn" onClick="authManager();">
                          </TD>
                      </TR>
                      </TBODY> 
                    </TABLE>
                    <br/>
                  
</body>
</html>
<%!
   void getMenuAuthHtmls(StringBuffer buffer,String roleUid, DOMenuModel pMenu,DOMenuModel rootMenu, int layer){
	 List childrens =  pMenu.retrieveChildrenNoAuth();
	 layer++;
	 for(Iterator itChild = childrens.iterator(); itChild.hasNext();){
		 DOMenuModel cMenu = (DOMenuModel)itChild.next(); 

		 String checked = "";
		 if(cMenu.isAccessByRole(roleUid)){
			 checked = " checked='checked' ";
		 }
		
		 buffer.append("<tr><td bgColor=#ffffff>");
		 for(int i = 1; i < layer ; i ++){
		 	buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 }
		 
		 buffer.append("<input type='checkbox' name='menuUid'  class='menuUid' parentid='a")
		 .append(rootMenu.getObjUid())
		 .append("' id='a")
		 .append(cMenu.getObjUid())
		 .append("' ")
		 .append(checked);
		 
		 if(!cMenu.isFilter()){
			 buffer.append(" disabled='disabled' ");
		 }
		 
		 buffer.append(" value='")
		 .append(cMenu.getObjUid())
		 .append("' >")
		 .append(cMenu.getL10n());
		 buffer.append("</td></tr>");

		 List deeps = cMenu.retrieveChildrenNoAuth();
		 if(deeps.size() > 0){
		 	getMenuAuthHtmls(buffer,roleUid,cMenu,pMenu,layer);
		 }
	 }
}
%>