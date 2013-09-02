<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="saasAdmin_tenants_tenantEditForm" method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">租户名称</th>
				<td><input name="name" class="easyui-validatebox" data-options="required:true, missingMessage:'租户名称不能为空'" />
				</td>
				<th style="width: 170px;">租户描述</th>
				<td><input name="note" class="easyui-validatebox" data-options="required:true, missingMessage:'租户描述不能为空'"  /></td>
			</tr>
		</table>
	</form>
</div>