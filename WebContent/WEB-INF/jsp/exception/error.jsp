<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<h1><font color="red">	提示：<% String str = request.getParameter("msg");
																if(str != null && !"".equals(str)){
																	%>
																	<%= str	%>
																<%
																}else{
																	 %>
																	 对不起，您的权限不足！！
																	 <%
																}
%></font></h1>