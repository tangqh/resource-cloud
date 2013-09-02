<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
	$(function() {
		/**
	     * 用于显示管理员的datagrid
	     */
		$('#saasAdmin_rootAdmins_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/controller/saasRootAdmin/saasRootAdmins',
			fit : true,
			fitColumns : true,
			border : true,
			pagination : true,
			idField : 'id',
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'realName',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			frozenColumns : [[ 
				{field : 'id', title : '账号', width : 150, checkbox : true, sortable : true}
				]],
			columns : [[
				{field : 'realName', title : '真实姓名', width : 200}
				,{field : 'privilege', title : '权限组', width : 200}
				]]
		});

	});
	/**
     *根据过滤条件查找管理员
     */
    function saasRootAdmin_saasRootAdmins_searchFun() {
        var name = document.getElementById("sra_name").value;
        if(!name){
            $.messager.alert('提示', '请填写查找信息');
            return;
        }
        var sUrl = '${pageContext.request.contextPath}/controller/saasRootAdmin/search/' + name;
        $('#saasAdmin_rootAdmins_datagrid').datagrid({url:sUrl});
    }
    /**
     *清除过滤条件
     */
    function saasRootAdmin_saasRootAdmins_clean() {
        $('#saasAdmin_saasRootAdmins_searchForm input').val('');
        $('#saasAdmin_rootAdmins_datagrid').datagrid({url:'${pageContext.request.contextPath}/controller/saasRootAdmin/saasRootAdmins'});
    }
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 120px;overflow: hidden;" align="center">
		<form id="saasAdmin_saasRootAdmins_searchForm">
			<table class="tableForm">
				<tr>
					<th style="width: 170px;">检索管理员名称(可模糊查询)</th>
					<td><input id="sra_name" name="name" style="width: 120px;" /></td>
				</tr>
			</table>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="saasRootAdmin_saasRootAdmins_searchFun();">过滤条件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="saasRootAdmin_saasRootAdmins_clean();">清空条件</a>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="saasAdmin_rootAdmins_datagrid"></table>
	</div>
</div>