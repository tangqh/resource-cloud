<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示用户的datagrid
	     */
		$('#user_usres_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/user/users',
			fit : true,
			fitColumns : true,
			border : true,
			pagination : true,
			idField : 'id',
			pageNumber : 1,
			pageSize : 5,
			pageList : [ 5, 10, 20, 30, 40 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [[ 
				{field : 'id', title : 'ID', width : 150, checkbox : true, sortable : true}
				,{field : 'loginName', title : '账号', width : 200, sortable : true}
				]],
			columns : [[
				{field : 'gender', title : '性别', width : 200}
				,{field : 'email', title : '邮箱', width : 200}
				,{field : 'realName', title : '真实姓名', width : 200}
				]],
			toolbar : [
				{text : '增加', iconCls : 'icon-add', handler : function() {user_usres_appendFun();}}
				,'-'
				,{text : '批量删除', iconCls : 'icon-remove', handler : function() {user_usres_removeFun();}}
				,'-'
				,{text : '编辑', iconCls : 'icon-edit', handler : function() {user_usres_editFun($('#user_usres_datagrid').datagrid('getChecked')[0]);}}
				,'-'
				]
		});

	});
	/**
	 *根据过滤条件查找用户
	 */
	function user_usres_searchFun() {
	    var name = document.getElementById("u_name").value;
        if(!name){
            $.messager.alert('提示', '请填写查找信息');
            return;
        }
        var sUrl = '${pageContext.request.contextPath}/controller/user/search/' + name;
        $('#user_usres_datagrid').datagrid({url:sUrl});
	}
	/**
	 *清除过滤条件
	 */
	function user_usres_cleanFun() {
 		$('#user_usres_searchForm input').val('');
		$('#user_usres_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/user/users'});
	}
	/**
	 * 编辑用户
	 */
	function user_usres_editFun(obj) {
		if(!obj || !obj.id || obj.id==""){
			$.messager.alert('提示', '请选择所要编辑的对象');
			return;
		}
		var editUIHref =  '${pageContext.request.contextPath}/controller/user/editUI/'+obj.id;
		var editHref =  '${pageContext.request.contextPath}/controller/user/edit/'+obj.id;
		$('#user_usres_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : editUIHref,
			width : 520,
			height : 200,
			modal : true,
			title : '编辑用户',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_user_userEditForm').form('submit', {
						url : editHref,
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#user_usres_datagrid').datagrid('updateRow', {
										index : $('#user_usres_datagrid').datagrid('getRowIndex', obj.id),
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
					$('#admin_user_userEditForm').form('load', {
						id : obj.id ,
						loginName : obj.loginName ,	
						realName : obj.realName ,
						gender : obj.gender,
						email : obj.email
					});
				} catch (e) {
					$.messager.alert('提示', '同一时间只能编辑一个用户');
				}
			}
		});
	}
	/**
	 * 添加用户的函数
	 */
	function user_usres_appendFun() {
		$('#user_usres_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/controller/user/addUI',
			width : 520,
			height : 200,
			modal : true,
			title : '添加用户',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#user_users_userAddForm').form('submit', {
						url : '${pageContext.request.contextPath}/controller/user/add',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#user_usres_datagrid').datagrid('insertRow', {
										index : 0,
										row : r.obj
									});
									d.dialog('destroy');
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								}else{
									$.messager.alert('提示', r.msg);									
								}
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
	 * 删除用户的函数
	 */
	function user_usres_removeFun() {
		var rows = $('#user_usres_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					var currentUserId = '${sessionInfo.userId}';/*当前登录用户的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
						} else {
							flag = true;
						}
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/controller/user/remove',
						data : {
							 ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#user_usres_datagrid').datagrid('load');
								$('#user_usres_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
		<form id="user_usres_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索用户名称(可模糊查询)</th>
					<td><input id="u_name" name="name" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="user_usres_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="user_usres_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="user_usres_datagrid"></table>
	</div>
</div>