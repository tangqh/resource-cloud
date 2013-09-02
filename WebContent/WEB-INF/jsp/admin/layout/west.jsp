<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" charset="UTF-8">
	var userManagerTree, bookManagerTree, resourceManagerTree,  privilegeManagerTree, adminManagerTree;
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
			userManagerTree = $('#userManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_user_type_tree',
			lines : true,
			onClick : function (node) {
							var url = '${pageContext.request.contextPath}' + node.attributes.url;
							if(node.attributes.url && "" != node.attributes.url){
								addTabs({title:node.text,closable:true,href:url});
							}
					   }
		});
		resourceManagerTree = $('#resourceManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_resource_type_tree',
			lines : true,
			onClick : function (node) {
							var url = '${pageContext.request.contextPath}' + node.attributes.url;
							if(node.attributes.url && "" != node.attributes.url){
								addTabs({title:node.text,closable:true,href:url});
							}
					   }
		});
		privilegeManagerTree = $('#privilegeManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_privilege_type_tree',
			lines : true,
			onClick : function (node) {
				var url = '${pageContext.request.contextPath}' + node.attributes.url;
				if(node.attributes.url && "" != node.attributes.url){
					addTabs({title:node.text,closable:true,href:url});
				}
		   }
		});
		adminManagerTree = $('#adminManagerTree').tree({
			url : '${pageContext.request.contextPath}/controller/menu/get_admin_type_tree',
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
	<div title="用户管理" data-options="iconCls:'icon-reload'">
		<ul id="userManagerTree" style="margin-top: 5px;"></ul>
	</div>
	<div title="资源管理" data-options="iconCls:'icon-reload'" style="background:#eee;">
		<ul id="resourceManagerTree" style="margin-top: 5px;"></ul>  
    </div>
    <div title="权限管理" data-options="iconCls:'icon-reload'" style="background:#eee;">
    	<ul id="privilegeManagerTree" style="margin-top: 5px;"></ul>  
    </div>
    <div title="管理员管理" data-options="iconCls:'icon-reload'" style="background:#eee;">
    	<ul id="adminManagerTree" style="margin-top: 5px;"></ul>  
    </div>
</div>