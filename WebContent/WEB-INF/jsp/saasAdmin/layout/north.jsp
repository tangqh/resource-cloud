<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	function logout(b) {
		$.ajax({
			url : '${pageContext.request.contextPath}/saasAdmin/logout',
			dataType : 'json',
			success : function(result) {
				if (result.success) {
					if (b) {
						if (sy.isLessThanIe8()) {
							$('#admin_login_loginDialog').dialog('open');
						} else {
							location.replace('${pageContext.request.contextPath}/saasAdmin/index');
						}
					} else {
						location.replace('${pageContext.request.contextPath}/saasAdmin/index');
					}
				}
				document.getElementById("saas_north_welcome").innerHTML = "";
			}
		});
	}
</script>
<div style="position: absolute; right: 0px; bottom: 0px; ">
	<span id="saas_north_welcome"></span>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="logout();">重新登录</div>
	<div onclick="logout(true);">退出系统</div>
</div>
