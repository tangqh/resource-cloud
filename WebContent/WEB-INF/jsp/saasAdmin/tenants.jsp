<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示租户的datagrid
	     */
		$('#saasAdmin_tenants_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/tenant/tenants',
			fit : true,
			fitColumns : true,
			border : true,
			pagination : true,
			idField : 'id',
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [[ 
				{field : 'id', title : '账号', width : 150, checkbox : true, sortable : true}
				]],
			columns : [[
				{field : 'name', title : '租户名称', width : 200}
				,{field : 'note', title : '租户描述', width : 200}
				,{field : 'createTime', title : '创建日期', width : 200}
				,{field : 'host', title : '主机IP', width : 200}
				,{field : 'port', title : '主机端口', width : 200}
				,{field : 'rootFolder', title : '资源根文件夹', width : 200}
				]],
			toolbar : [
				{text : '批量删除', iconCls : 'icon-remove', handler : function() {saasAdmin_tenants_removeFun();}}
				,'-'
				,{text : '编辑', iconCls : 'icon-edit', handler : function() {saasAdmin_tenants_editFun($('#saasAdmin_tenants_datagrid').datagrid('getChecked')[0]);}}
				,'-'
				]
		});

	});
	/**
	 *根据过滤条件查找租户
	 */
	function saasAdmin_tenants_searchFun() {
	    var name = document.getElementById("t_name").value;
        if(!name){
            $.messager.alert('提示', '请填写查找信息');
            return;
        }
        var sUrl = '${pageContext.request.contextPath}/controller/tenant/search/' + name;
        $('#saasAdmin_tenants_datagrid').datagrid({url:sUrl});
	}
	/**
	 *清除过滤条件
	 */
	function saasAdmin_tenants_cleanFun() {
 		$('#saasAdmin_tenants_searchForm input').val('');
		$('#saasAdmin_tenants_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/tenant/tenants'});
	}
	/**
	 * 编辑租户
	 */
	function saasAdmin_tenants_editFun(obj) {
		if(!obj || !obj.id || obj.id == ""){
			$.messager.alert('提示', '请选择所要编辑的对象');
			return;
		}
		var editUIHref = '${pageContext.request.contextPath}/controller/tenant/editUI/' + obj.id;
		var editHref = '${pageContext.request.contextPath}/controller/tenant/edit/' + obj.id;
		$('#saasAdmin_tenants_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : editUIHref,
			width : 520,
			height : 200,
			modal : true,
			title : '编辑租户',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#saasAdmin_tenants_tenantEditForm').form('submit', {
						url : editHref,
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#saasAdmin_tenants_datagrid').datagrid('updateRow', {
										index : $('#saasAdmin_tenants_datagrid').datagrid('getRowIndex', obj.id),
										row : r.obj
									});
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				try {
					$('#saasAdmin_tenants_tenantEditForm').form('load', {
						id : obj.id ,
						name : obj.name ,	
						note : obj.note
					});
				} catch (e) {
					$.messager.alert('提示', '同一时间只能编辑一个对象');
				}
			}
		});
	}
	/**
	 * 删除租户的函数
	 */
	function saasAdmin_tenants_removeFun() {
		var rows = $('#saasAdmin_tenants_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					var currentUserId = '${sessionInfo.userId}';/*当前登录租户的ID*/
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
						}
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/controller/tenant/remove',
						data : {
							 ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#saassaasAdmin_tenants_datagrid').datagrid('load');
								$('#saassaasAdmin_tenants_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请勾选要删除的记录！');
		}
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 120px;overflow: hidden;" align="center">
		<form id="saasAdmin_tenants_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索租户名称(可模糊查询)</th>
					<td><input id="t_name" name="name" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="saasAdmin_tenants_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="saasAdmin_tenants_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="saasAdmin_tenants_datagrid"></table>
	</div>
</div>