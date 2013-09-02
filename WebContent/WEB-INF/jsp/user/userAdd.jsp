<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="user_users_userAddForm" method="post">
		<table class="tableForm">
			<tr>
				<th >账号</th>
				<td><input name="loginName" class="easyui-validatebox" data-options="required:true, missingMessage:'账号不能为空'"/>
				</td>
				<th>性别</th>
				<td><input name="gender"class="easyui-combobox" data-options="valueField:'id', textField:'text', data:[{id:'MAN',text:'男'},{id:'WOMEN',text:'女'}]"  style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>邮箱</th>
				<td><input name="email"  style="width: 120px;" /></td>
				<th>密码</th>
				<td><input id="user_users_userAdd_pwd" name="password" type="password" class="easyui-validatebox" data-options="required:true, missingMessage:'密码不能为空'" style="width: 150px;" />
				</td>
			</tr>
		</table>
	</form>
</div>