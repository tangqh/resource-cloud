<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>分布式资源检索系统管理平台</title>
    <jsp:include page="../../share/include.jsp"></jsp:include>
    
    <script type="text/javascript">
	    var centerTabs;
		var tabsMenu;
    	function addTabs(opts) {
    		var t = $('#centerTabs');
    		if(t.tabs('exists', opts.title)){
    			t.tabs('select', opts.title);
    		}else{
    			t.tabs('add', opts);
    		}
		}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'north', href:'${pageContext.request.contextPath}/admin/layout/north'" style="height:100px; background:#eee;"></div>
  	<div data-options="region:'south'" style="height:50px; background:#eee;"></div>
  	<div data-options="region:'west', title:'功能导航', split:true,href:'${pageContext.request.contextPath}/admin/layout/west'" style="width:200px; background:#eee;"></div>
  	<div data-options="region:'east', title:'小功能',href:'${pageContext.request.contextPath}/admin/layout/east', split:true" style="width:200px; background:#eee;"></div>
  	<div data-options="region:'center', title:'业务功能',href:'${pageContext.request.contextPath}/admin/layout/center'"  style="background:#eee;"></div>
  	<jsp:include page="login.jsp"></jsp:include>
  </body>
</html>
