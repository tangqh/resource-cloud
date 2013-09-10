package cn.edu.sdu.drs.controller.SAASadmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.util.MD5;
import cn.edu.sdu.drs.util.WebUtil;
import cn.edu.sdu.drs.web.bean.Json;

@Controller
@RequestMapping("/saasAdmin")
public class SAASAdminIndex extends BaseController{
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response){
		if(WebUtil.getAdmin(request) != null){
			request.getSession().removeAttribute("saasAdmin");
		}
		if(WebUtil.getRootAdmin(request) != null){
			request.getSession().removeAttribute("saasRootAdmin");
		}
		String id=request.getParameter("id");
		String pwd=request.getParameter("password");
		Json json = new Json();
		if(id==null || id.trim().length()==0 || pwd==null || pwd.trim().length()==0){
			json.setObj(null);
			json.setMsg("登录名和密码不能为空！");
			json.setSuccess(false);
			writeJson(request, response, json);
			return;
		}
		SAASAdmin admin = saasAdminService.find(SAASAdmin.class, id);
		if(admin != null){
			if(admin.getPassword().equals(MD5.MD5Encode(pwd))){
				request.getSession().setAttribute("saasAdmin", admin);
				json.setMsg(admin.getRealname() + ", 欢迎您的登录！");
				writeJson(request, response, json);
				return;
			}else{
				json.setObj(null);
				json.setMsg("密码错误！");
				json.setSuccess(false);
				writeJson(request, response, json);
				return;
			}
		}else{
			SAASRootAdmin ra = saasRootAdminService.find(SAASRootAdmin.class, id);
			if(ra != null){
				if(ra.getPassword().equals(MD5.MD5Encode(pwd))){
					request.getSession().setAttribute("saasRootAdmin", ra);
					json.setMsg(ra.getRealName() + ", 欢迎您的登录！");
					writeJson(request, response, json);
					return;
				}else{
					json.setObj(null);
					json.setMsg("密码错误！");
					json.setSuccess(false);
					writeJson(request, response, json);
					return;
				}
			}
		}
		json.setObj(null);
		json.setMsg("登录失败！");
		json.setSuccess(false);
		writeJson(request, response, json);
	}
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		Json json = new Json("已登出！");
		if(WebUtil.getAdmin(request) != null){
			request.getSession().removeAttribute("saasAdmin");
		}
		if(WebUtil.getRootAdmin(request) != null){
			request.getSession().removeAttribute("saasRootAdmin");
		}
		writeJson(request, response, json);
	}
	
	@RequestMapping("/index")
	public String index(){
		return "saasAdmin/sadminIndex";
	}
	
	@RequestMapping("/layout/north")
	public String north(){
		return "saasAdmin/layout/north";
	}
	
	@RequestMapping("/layout/south")
	public String south(){
		return "saasAdmin/layout/south";
	}
	
	@RequestMapping("/layout/west")
	public String west(){
		return "saasAdmin/layout/west";
	}
	
	@RequestMapping("/layout/center")
	public String center(){
		return "saasAdmin/layout/center";
	}
	
	@RequestMapping("/layout/portal")
	public String portal(){
		return "saasAdmin/layout/portal";
	}

}
