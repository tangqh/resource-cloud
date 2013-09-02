<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
		<form id="admin_resources_resourceEditForm" method="post">
			<table class="tableForm">
				<tr>
					<th style="width: 80px;">是否共享：</th>
					<td>
							<select id="share" name="share">
								<option value="YES">YES</option>
								<option value="NO">NO</option>
							</select>
					</td>
				</tr>
				<tr>
					<th style="width: 80px;">描述：</th>
					<td><textarea  id="describe" name="describe"></textarea></td>
				</tr>
			</table>
		</form>
</div>