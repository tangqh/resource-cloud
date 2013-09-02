<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<div style="margin-top: 100px; margin-left: 500px;">
 <h1><font color="red">  提示：<% String str =(String) request.getAttribute("msg");
                                                                 if(str != null && !"".equals(str)){
                                                                     %>
                                                                     <%= str %>
                                                                 <%
                                                                 }else{
                                                                      %>
                                                                      对不起，出错了!!!
                                                                      <%
                                                                 }
 %></font></h1></div>