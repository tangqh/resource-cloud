<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
<!--
//-->
</script>
<div align="center" onload="init()">
	<form id="admin_privilegeGroups_adminEditForm" method="post">
	<input type="hidden" id="p" name="privilege"/>
	<table id="EtableForm">
			<tr>
				<th style="width: 80px;">权限组名称</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true, missingMessage:'权限组名称不能为空'" />
				</td>
			</tr>
			<tr>
			<th style="width: 80px;">选择权限</th>
			</tr>
			<tr>
				<td><input  type="checkbox" name="userInsert" value="增加用户"/>增加用户</td>
				<td><input  type="checkbox" name="userUpdate" value="更新用户"/>更新用户</td>
				<td><input  type="checkbox" name="userSelect" value="查找用户"/>查找用户</td>
				<td><input  type="checkbox" name="userDelete" value="删除用户"/>删除用户</td>
			</tr>
			<tr>
				<td><input  type="checkbox" name="resourceInsert" value="增加资源"/>增加资源</td>
				<td><input  type="checkbox" name="resourceUpdate" value="更新资源"/>更新资源</td>
				<td><input  type="checkbox" name="resourceSelect" value="查找资源"/>查找资源</td>
				<td><input  type="checkbox" name="resourceDelete" value="删除资源"/>删除资源</td>
			</tr>
			<tr>
				<td><input  type="checkbox" name="adminInsert" value="增加管理员"/>增加管理员</td>
				<td><input  type="checkbox" name="adminUpdate" value="更新管理员"/>更新管理员</td>
				<td><input  type="checkbox" name="adminSelect" value="查找管理员"/>查找管理员</td>
				<td><input  type="checkbox" name="adminDelete" value="删除管理员"/>删除管理员</td>
			</tr>
			<tr>
				<td><input  type="checkbox" name="privilegeInsert" value="增加权限组"/>增加权限组</td>
				<td><input  type="checkbox" name="privilegeUpdate" value="更新权限组"/>更新权限组</td>
				<td><input  type="checkbox" name="privilegeSelect" value="查找权限组"/>查找权限组</td>
				<td><input  type="checkbox" name="privilegeDelete" value="删除权限组"/>删除权限组</td>
			</tr>
		</table>
	</form>
</div>