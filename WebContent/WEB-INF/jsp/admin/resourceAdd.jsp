<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
		<form action="<%=request.getAttribute("submit-url") %>"
   id="admin_resources_resourceAddForm" name="admin_resources_resourceAddForm" enctype="multipart/form-data" method="post" >
			<table class="tableForm">
				<tr>
					<th>请选择文件:</th>
					<td><input name="resource" type="file" size="100" 	onKeyDown="javascript:alert('此信息不能手动输入');return false;" />	</td>
				</tr>
				<tr>
					<th style="width: 80px;">描述：</th>
					<td><input name="describe" /></td>
				</tr>
				<tr>
					<th style="width: 80px;">是否共享：</th>
					<td><input name="share" class="easyui-combobox" data-options="valueField:'id', textField:'text', data:[{id:'YES',text:'YES'},{id:'NO',text:'NO'}]" style="width: 120px;" /></td>
				</tr>
			</table>
		</form>
</div>