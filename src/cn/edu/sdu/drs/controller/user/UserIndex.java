package cn.edu.sdu.drs.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.controller.BaseController;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/user")
public class UserIndex extends BaseController{
	
	/**
	 * 登录界面的渲染
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginUI(){
		return "user/login";
	}
	
	/**
	 * 处理登录请求
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request){
		String userName=request.getParameter("name");
		String pwd=request.getParameter("password");
		String remember=request.getParameter("remember");
		System.out.println(userName+pwd+remember);
		if(userName==null || userName.trim().length()==0 || pwd==null || pwd.trim().length()==0){
			return "user/login";
		}
		User euser = userService.findUserByEmail(userName);//通过email获取的user
		User lnuser = euser==null?userService.findUserByLoginName(userName):null;//通过loginName获取的user
		
		if(euser!=null){
			if(euser.getPassword().equals(pwd)){
				request.getSession().setAttribute("user", euser);
			}else{
				return "user/login";
			}
		}else if(lnuser!=null){
			if(lnuser.getPassword().equals(pwd)){
				request.getSession().setAttribute("user", lnuser);
			}else{
				return "user/login";
			}
		}else{
			return "user/login";
		}
		if(null!=remember){
			//将该用户的用户名和密码写到cookie中
		}
		return "redirect:/index.jsp";
	}

}
