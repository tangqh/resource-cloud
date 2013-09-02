package cn.edu.sdu.drs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.util.WebUtil;

/**
 * 权限控制的过滤器：过滤不登录的SAAS平台的一般管理员
 * @author join
 *
 */
public class PrivilegeFilter4SaasAdminLogin implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		SAASAdmin saasAdmin = WebUtil.getSAASAdmin(request);
		/* 如果SAAS平台的一般管理员未登录，重定向到SAAS平台的一般管理员登陆界面 */
		if(saasAdmin==null){
			HttpServletResponse response = (HttpServletResponse)res;
			response.sendRedirect("/controller/saasAdmin/loginUI");
		}else{
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
