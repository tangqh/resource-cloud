<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="saasAdmin_admins_adminEditForm" method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">真是姓名</th>
				<td><input name="realName" class="easyui-validatebox" data-options="required:true, missingMessage:'姓名不能为空'" />
				</td>
				<th style="width: 170px;">性别</th>
				<td><input name="gender" class="easyui-combobox" data-options="valueField:'id', textField:'text', data:[{id:'MAN',text:'男'},{id:'WOMEN',text:'女'}]" style="width: 120px;" /></td>
			</tr>
			<tr>
				<th style="width: 170px;">邮箱</th>
				<td><input name="email" style="width: 120px;" /></td>
				<th>电话</th>
				<td><input name="phone"  style="width: 150px;" />
				</td>
			</tr>
		</table>
	</form>
</div>