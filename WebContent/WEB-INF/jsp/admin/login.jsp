<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>分布式检索系统后台登陆</title>
    <jsp:include page="../../share/include.jsp"></jsp:include>
     <script type="text/javascript">
    	$(function(){
	    	var loginForm = $('#admin_login_loginForm');
	    	/** 初始化form **/
	    	loginForm.form({
    			url : '${pageContext.request.contextPath}/admin/login',
    			success: function(data){
    				var obj = jQuery.parseJSON(data);
    				if (obj.success) {
    					$('#admin_login_loginDialog').dialog('close');
    					document.getElementById("north_welcome").innerHTML = '<font color="red">' + obj.msg + '</font>';
    					document.getElementById("tenant-name").innerHTML = '我的资源云 — ' +obj.tenantName;
    				} 
    				$.messager.show({
    					title : '提示',
    					msg : obj.msg
    				});
    			}
			 });
			 /** 绑定enter键自动登录 **/
    		$('#admin_login_loginForm input').bind('keyup',function(event){
    			if(event.keyCode == '13'){
    				loginForm.submit();
    			}
    		});
    	});
    </script>
  </head>
  
  <body>
  <!-- 登录 -->
  	<div class="easyui-dialog" id="admin_login_loginDialog" data-options="title:'登录', modal:true, close:true, closable:false, buttons:[{
				text:'退出',
				handler:function(){
					$('#admin_login_loginDialog').dialog('close');
				}
			},{
				text:'登录',
				handler:function(){
					$('#admin_login_loginForm').submit();
				}
			}]">
	<form id="admin_login_loginForm" action="post">
		<table>
			<tr><th>登录名：</th><td><input name="id" class="easyui-validatebox" data-options="required:true, missingMessage:'用户名不能为空'" /></td></tr>
			<tr><th>密&nbsp;&nbsp;码：</th><td><input type="password" name="password" class="easyui-validatebox" data-options="required:true, missingMessage:'密码不能为空'" /></td></tr>
		</table>
	</form>
  	</div>
  </body>
</html>