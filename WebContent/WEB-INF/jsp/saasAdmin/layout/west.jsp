<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" charset="UTF-8">
	var saasAdminManagerTree, tenantManagerTree;
	function onClickTree(node) {
		addTabs(node);
	}
	function onDblClickTree(node) {
		if (node.state == 'closed') {
			$(this).tree('expand', node.target);
		} else {
			$(this).tree('collapse', node.target);
		}
	}
	$(function() {
			saasAdminManagerTree = $('#saasAdminManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_saasAdmin_type_tree',
			lines : true,
			onClick : function (node) {
							var url = '${pageContext.request.contextPath}' + node.attributes.url;
							if(node.attributes.url && "" != node.attributes.url){
								addTabs({title:node.text,closable:true,href:url});
							}
					   }
		});
			tenantManagerTree = $('#tenantManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_tenant_type_tree',
			lines : true,
			onClick : function (node) {
							var url = '${pageContext.request.contextPath}' + node.attributes.url;
							if(node.attributes.url && "" != node.attributes.url){
								addTabs({title:node.text,closable:true,href:url});
							}
					   }
		});
	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="租户管理" data-options="iconCls:'icon-reload'">
		<ul id="tenantManagerTree" style="margin-top: 5px;"></ul>
	</div>
	<div title="服务平台管理员" data-options="iconCls:'icon-reload'" style="background:#eee;">
		<ul id="saasAdminManagerTree" style="margin-top: 5px;"></ul>  
    </div>
</div>