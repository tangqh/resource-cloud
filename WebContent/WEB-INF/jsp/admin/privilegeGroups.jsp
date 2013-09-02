<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示权限组的datagrid
	     */
		$('#admin_privilegeGroups_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/privilegeGroup/privilegeGroups',
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
				{field : 'id', title : '编号', width : 150, checkbox : true, sortable : true}
				]],
			columns : [[
				{field : 'name', title : '权限组名称', width : 200}
				,{field : 'privilege', title : '包括的权限', width : 200}
				]],
			toolbar : [
				{text : '增加', iconCls : 'icon-add', handler : function() {admin_privilegeGroups_appendFun();}}
				,'-'
				,{text : '批量删除', iconCls : 'icon-remove', handler : function() {admin_privilegeGroups_removeFun();}}
				,'-'
				,{text : '编辑', iconCls : 'icon-edit', handler : function() {admin_privilegeGroups_editFun($('#admin_privilegeGroups_datagrid').datagrid('getChecked')[0]);}}
				,'-'
				]
		});

	});
	/**
	 *根据过滤条件查找权限组
	 */
	function admin_privilegeGroups_searchFun() {
	    var name = document.getElementById("p_name").value;
        if(!name){
            $.messager.alert('提示', '请填写查找信息');
            return;
        }
        var sUrl = '${pageContext.request.contextPath}/controller/privilegeGroup/search/' + name;
        $('#admin_privilegeGroups_datagrid').datagrid({url:sUrl});
	}
	/**
	 *清除过滤条件
	 */
	function admin_privilegeGroups_cleanFun() {
 		$('#admin_privilegeGroups_searchForm input').val('');
		$('#admin_privilegeGroups_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/privilegeGroup/privilegeGroups'});
	}
	/**
	 * 编辑权限组
	 */
	function admin_privilegeGroups_editFun(obj) {
		if(!obj || !obj.id || obj.id == ""){
			$.messager.alert('提示', '请选择所要编辑的对象');
			return;
		}
		var editUIHref = '${pageContext.request.contextPath}/controller/privilegeGroup/editUI/' + obj.id;
		var editHref = '${pageContext.request.contextPath}/controller/privilegeGroup/edit/' + obj.id;
		$('#admin_privilegeGroups_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : editUIHref,
			width : 520,
			height : 250,
			modal : true,
			title : '编辑权限组',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_privilegeGroups_adminEditForm').form('submit', {
						url : editHref,
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_privilegeGroups_datagrid').datagrid('updateRow', {
										index : $('#admin_privilegeGroups_datagrid').datagrid('getRowIndex', obj.id),
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
					$('#admin_privilegeGroups_adminEditForm').form('load', {
						id : obj.id ,
						name : obj.name ,
						privilege : obj.privilege
					});
				} catch (e) {
					$.messager.alert('提示', '同一时间只能编辑一个对象');
				}
				var p = document.getElementById("p").value;
				var ps = p.split(",");
				var ckeckArr = $(":checkbox");
				for(var j=0; j<ps.length; j++){
					for(var i=0; i < ckeckArr.length; i++){
						if(ps[j] == ckeckArr[i].value){
							ckeckArr[i].checked = true;
							break;
						}
					}			
				}
			}
		});
	}
	/**
	 * 添加权限组的函数
	 */
	function admin_privilegeGroups_appendFun() {
		$('#admin_privilegeGroups_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/controller/privilegeGroup/addUI',
			width : 520,
			height : 250,
			modal : true,
			title : '添加权限组',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_privilegeGroups_adminAddForm').form('submit', {
						url : '${pageContext.request.contextPath}/controller/privilegeGroup/add',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_privilegeGroups_datagrid').datagrid('insertRow', {
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
	 * 删除权限组的函数
	 */
	function admin_privilegeGroups_removeFun() {
		var rows = $('#admin_privilegeGroups_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/controller/privilegeGroup/remove',
						data : {
							 ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#admin_privilegeGroups_datagrid').datagrid('load');
								$('#admin_privilegeGroups_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
		<form id="admin_privilegeGroups_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索权限组名称(可模糊查询)</th>
					<td><input id="p_name" name="name" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="admin_privilegeGroups_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="admin_privilegeGroups_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="admin_privilegeGroups_datagrid"></table>
	</div>
</div>