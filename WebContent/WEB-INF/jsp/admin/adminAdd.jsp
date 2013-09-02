<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="admin_admins_adminAddForm" method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">真是姓名</th>
				<td><input name="realName" class="easyui-validatebox" data-options="required:true, missingMessage:'姓名不能为空'" />
				</td>
				<th style="width: 100px;">账号</th>
				<td><input name="id" class="easyui-validatebox" data-options="required:true, missingMessage:'账号不能为空'"/>
				</td>
			</tr>
			<tr>
				<th style="width: 170px;">性别</th>
				<td><input name="gender" class="easyui-combobox" data-options="valueField:'id', textField:'text', data:[{id:'MAN',text:'男'},{id:'WOMEN',text:'女'}]" style="width: 120px;" /></td>
				<th>密码</th>
				<td><input id="admin_admins_adminAdd_pwd" name="password" type="password" class="easyui-validatebox" data-options="required:true, missingMessage:'密码不能为空'" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 170px;">邮箱</th>
				<td><input name="email" style="width: 120px;" /></td>
				<th>电话</th>
				<td><input name="phone" type="password" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 170px;">权限组</th>
				<td><input name="pgn" class="easyui-combobox" data-options="valueField:'id', textField:'text', data:[{id:'管理员服务组',text:'管理员服务组'}
										,{id:'权限服务组',text:'权限服务组'}
										,{id:'用户服务组',text:'用户服务组'}
										,{id:'资源服务组',text:'资源服务组'}]" style="width: 120px;" /></td>
			</tr>
		</table>
	</form>
</div>