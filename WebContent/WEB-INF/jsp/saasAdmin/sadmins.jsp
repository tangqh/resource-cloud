<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示管理员的datagrid
	     */
		$('#saasAdmin_admins_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/saasAdmin/saasAdmins',
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
				{field : 'realName', title : '真实姓名', width : 200}
				,{field : 'gender', title : '性别', width : 200}
				,{field : 'email', title : '邮箱', width : 200}
				,{field : 'phone', title : '电话', width : 200}
				,{field : 'privilege', title : '权限组', width : 200}
				]],
			toolbar : [
				{text : '增加', iconCls : 'icon-add', handler : function() {saasAdmin_admins_appendFun();}}
				,'-'
				,{text : '批量删除', iconCls : 'icon-remove', handler : function() {saasAdmin_admins_removeFun();}}
				,'-'
				,{text : '编辑', iconCls : 'icon-edit', handler : function() {saasAdmin_admins_editFun($('#saasAdmin_admins_datagrid').datagrid('getChecked')[0]);}}
				,'-'
				]
		});

	});
	/**
	 *根据过滤条件查找管理员
	 */
	function saasAdmin_admins_searchFun() {
	    var name = document.getElementById("sa_name").value;
        if(!name){
            $.messager.alert('提示', '请填写查找信息');
            return;
        }
        var sUrl = '${pageContext.request.contextPath}/controller/saasAdmin/search/' + name;
        $('#saasAdmin_admins_datagrid').datagrid({url:sUrl});
	}
	/**
	 *清除过滤条件
	 */
	function saasAdmin_admins_cleanFun() {
 		$('#saasAdmin_admins_searchForm input').val('');
		$('#saasAdmin_admins_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/saasAdmin/saasAdmins'});
	}
	/**
	 * 编辑管理员
	 */
	function saasAdmin_admins_editFun(obj) {
		if(!obj || !obj.id || obj.id == ""){
			$.messager.alert('提示', '请选择所要编辑的对象');
			return;
		}
		var editUIHref = '${pageContext.request.contextPath}/controller/saasAdmin/editUI/' + obj.id;
		var editHref = '${pageContext.request.contextPath}/controller/saasAdmin/edit/' + obj.id;
		$('#saasAdmin_admins_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : editUIHref,
			width : 520,
			height : 200,
			modal : true,
			title : '编辑管理员',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#saasAdmin_admins_adminEditForm').form('submit', {
						url : editHref,
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#saasAdmin_admins_datagrid').datagrid('updateRow', {
										index : $('#saasAdmin_admins_datagrid').datagrid('getRowIndex', obj.id),
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
					$('#saasAdmin_admins_adminEditForm').form('load', {
						id : obj.id ,
						realName : obj.realName ,	
						gender : obj.gender,
						email : obj.email,
						phone : obj.phone,
						privilege : obj.privilege
					});
				} catch (e) {
					$.messager.alert('提示', '同一时间只能编辑一个对象');
				}
				document.getElementById("pgn").value = obj.privilege;
			}
		});
	}
	/**
	 * 添加管理员的函数
	 */
	function saasAdmin_admins_appendFun() {
		$('#saasAdmin_admins_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/controller/saasAdmin/addUI',
			width : 520,
			height : 200,
			modal : true,
			title : '添加管理员',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#saasAdmin_admins_adminAddForm').form('submit', {
						url : '${pageContext.request.contextPath}/controller/saasAdmin/add',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#saasAdmin_admins_datagrid').datagrid('insertRow', {
										index : 0,
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
			}
		});
	}
	/**
	 * 删除管理员的函数
	 */
	function saasAdmin_admins_removeFun() {
		var rows = $('#saasAdmin_admins_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					var currentUserId = '${sessionInfo.userId}';/*当前登录管理员的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
						} else {
							flag = true;
						}
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/controller/saasAdmin/remove',
						data : {
							 ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#saasAdmin_admins_datagrid').datagrid('load');
								$('#saasAdmin_admins_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}
							if (flag) {
								$.messager.alert('提示', '不可以删除自己！');
							} else {
								$.messager.show({
									title : '提示',
									msg : result.msg
								});
							}
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
		<form id="saasAdmin_admins_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索管理员名称(可模糊查询)</th>
					<td><input id="sa_name" name="name" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="saasAdmin_admins_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="saasAdmin_admins_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="saasAdmin_admins_datagrid"></table>
	</div>
</div>