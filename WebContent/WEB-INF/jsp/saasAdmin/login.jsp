<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

     <script type="text/javascript">
    	$(function(){
	    	var loginForm = $('#saasRootAdmin_login_loginForm');
	    	/** 初始化form **/
	    	loginForm.form({
    			url : '${pageContext.request.contextPath}/saasAdmin/login',
    			success: function(data){
    				var obj = jQuery.parseJSON(data);
    				if (obj.success) {
    					$('#saasRootAdmin_login_loginDialog').dialog('close');
    					document.getElementById("saas_north_welcome").innerHTML = '<font color="red">' + obj.msg + '</font>';
    				} 
    				$.messager.show({
    					title : '提示',
    					msg : obj.msg
    				});
    			}
			 });
			 /** 绑定enter键自动登录 **/
    		$('#saasAdmin_login_loginForm input').bind('keyup',function(event){
    			if(event.keyCode == '13'){
    				loginForm.submit();
    			}
    		});
    	});
    </script>
  <body>
  <!-- 登录 -->
  	<div class="easyui-dialog"  id="saasRootAdmin_login_loginDialog" data-options="title:'登录', modal:true, close:true, closable:false, buttons:[{
				text:'退出',
				handler:function(){
					$('saasRootAdmin_login_loginDialog').dialog('close');
				}
			},{
				text:'登录',
				handler:function(){
					$('#saasRootAdmin_login_loginForm').submit();
				}
			}]">
	<form id="saasRootAdmin_login_loginForm" action="post">
		<table>
			<tr><th>登录名：</th><td><input name="id" class="easyui-validatebox" data-options="required:true, missingMessage:'用户名不能为空'" /></td></tr>
			<tr><th>密&nbsp;&nbsp;码：</th><td><input type="password" name="password" class="easyui-validatebox" data-options="required:true, missingMessage:'密码不能为空'" /></td></tr>
		</table>
	</form>
  	</div>
  </body>
