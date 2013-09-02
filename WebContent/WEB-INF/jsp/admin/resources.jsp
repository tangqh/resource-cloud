<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示资源的datagrid
	     */
		$('#admin_resources_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/resource/resources/1/10',
			fit : true,
			fitColumns : true,
			border : true,
			pagination : true,
			idField : 'id',
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 5, 10, 20, 30, 40],
			sortName : 'title',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [[ 
				{field : 'id', title : '编号', width : 150, checkbox : true, sortable : true}
				,{field : 'title', title : '标题', width : 200, sortable : true}
				]],
			columns : [[
				{field : 'kind', title : '类型', width : 50}
				,{field : 'keyWord', title : '关键字', width : 150}
				,{field : 'url', title : '连接', width : 200}
				,{field : 'describe', title : '描述', width : 200}
				,{field : 'share', title : '是否共享', width : 50}
				]],
			toolbar : [
				{text : '增加', iconCls : 'icon-add', handler : function() {admin_resources_appendFun();}}
				,'-'
				,{text : '批量删除', iconCls : 'icon-remove', handler : function() {admin_resources_removeFun();}}
				,'-'
				,{text : '编辑', iconCls : 'icon-edit', handler : function() {admin_resources_editFun($('#admin_resources_datagrid').datagrid('getChecked')[0]);}}
				,'-'
				,{text : 'PREVIEW', iconCls : 'icon-reload', handler : function() {admin_resources_preview();}}
                ,'-'
				,{text : 'NEXT', iconCls : 'icon-reload', handler : function() {admin_resources_next();}}
				,'-'
				]
		});
		
		document.getElementById("ps").value = 10;
		document.getElementById("pn").value = 1;

	});
	/**
	 *根据过滤条件查找资源
	 */
	function admin_resources_searchFun() {
	    var name = document.getElementById("r_name").value;
	    if(!name){
	        $.messager.alert('提示', '请填写查找信息');
	        return;
	    }
	    var sUrl = '${pageContext.request.contextPath}/controller/resource/search/' + name;
		$('#admin_resources_datagrid').datagrid({url:sUrl});
	}
	
	/**
     *清除过滤条件
     */
    function admin_resources_cleanFun() {
        $('#admin_resources_searchForm input').val('');
        $('#admin_resources_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/resource/resources/1/10'});
    }
	
	function admin_resources_next(){
		var pn = parseInt(document.getElementById("pn").value);
		var ps = parseInt(document.getElementById("ps").value);
		if(!ps || ps == 0){
			ps = 10;
		}
		if(!pn || pn == 0){
			pn = 1;
		}else{
			pn = pn + 1;
		}
		var nextUrl = '${pageContext.request.contextPath}/controller/resource/resources/' + pn  + '/' + ps;
		$('#admin_resources_datagrid').datagrid({url:nextUrl});
		document.getElementById("pn").value = pn;
	}
	
	function admin_resources_preview(){
        var pn = parseInt(document.getElementById("pn").value);
        var ps = parseInt(document.getElementById("ps").value);
        if(!ps || ps == 0){
            ps = 10;
        }
        if(!pn || pn == 0 || pn == 1){
            pn = 1;
        }else{
            pn = pn - 1;
        }
        var preUrl = '${pageContext.request.contextPath}/controller/resource/resources/' + pn  + '/' + ps;
        $('#admin_resources_datagrid').datagrid({url:preUrl});
        document.getElementById("pn").value = pn;
    }
	
	/**
	 * 编辑资源
	 */
	function admin_resources_editFun(obj) {
		if(!obj || !obj.id || obj.id == ""){
			$.messager.alert('提示', '请选择所要编辑的对象');
			return;
		}
		var editUIHref ='${pageContext.request.contextPath}/controller/resource/editUI/' + obj.id;  
		var editHref ='${pageContext.request.contextPath}/controller/resource/edit/' + obj.id;
		$('#admin_resources_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : editUIHref,
			width : 520,
			height : 200,
			modal : true,
			title : '编辑资源',
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_resources_resourceEditForm').form('submit', {
						url : editHref,
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_resources_datagrid').datagrid('updateRow', {
										index : $('#admin_resources_datagrid').datagrid('getRowIndex', obj.id),
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
					$('#admin_resources_resourceEditForm').form('load', {
						describe : obj.describe
						,share : obj.share
					});
					document.getElementById("share").value = obj.share;
					document.getElementById("describe").value = obj.describe;
				} catch (e) {
					$.messager.alert('提示', '同一时间只能编辑一个对象');
				}
			}
		});
	}
	/**
	 * 添加资源的函数
	 */
	function admin_resources_appendFun() {
		$('#admin_resources_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/controller/resource/addUI',
			width : 520,
			height : 200,
			modal : true,
			title : '添加资源',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_resources_resourceAddForm').form('submit', {
						url : '${pageContext.request.contextPath}/controller/resource/add',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_resources_datagrid').datagrid('insertRow', {
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
	 * 删除资源的函数
	 */
	function admin_resources_removeFun() {
		var rows = $('#admin_resources_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/controller/resource/remove',
						data : {
							 ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$('#admin_resources_datagrid').datagrid('load');
								$('#admin_resources_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
		<form id="admin_resources_searchForm">
		<input type="hidden" id="pn"/>
		<input type="hidden" id="ps"/>
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索资源名称(可模糊查询)</th>
					<td><input id="r_name" name="title" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="admin_resources_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="admin_resources_cleanFun();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="admin_resources_datagrid"></table>
	</div>
</div>