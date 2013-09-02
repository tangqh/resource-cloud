package cn.edu.sdu.drs.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.edu.sdu.drs.bean.Gender;
import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.bean.tenant.user.UserType;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;
import cn.edu.sdu.drs.web.bean.Json;

/**
 *	主要负责用户的CRUD<br>
 *	功能						方法			url			请求		返回页面<br>
 *	登录界面					login		login		get		user/login<br>
 *	登录验证					login		login		post	index.jsp<br>
 *******************************************************************<br>
 *	添加用户界面				add			add			get		user/userAdd<br>
 *	添加用户					add			add			post	user/users<br>
 *	删除用户					delete		delete/id	get		user/users<br>
 *	编辑用户					edit		edit/id		get		user/userEdit<br>
 *	返回所有用户				users		users		get		user/users<br>
 *	根据Id返回指定用户			findById	id/id		get		user/userInfo<br>
 *	根据用户名返回指定用户		findByName	name/name	get		user/userInfo<br>
 */
@Controller
@RequestMapping("/controller/user")
public class UserController extends BaseController {
	
	/**
	 * 显示增加会员的界面
	 * @return 增加会员的界面
	 */
	@RequestMapping(value="/addUI",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="insert")
	public String addUI(){
		return "user/userAdd";
	}
	
	/**
	 * 增加会员
	 * @return
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="insert")
	public void add(HttpServletRequest request, HttpServletResponse response){
		User user = new User();
		Json json = new Json("用户添加成功");
		try {
			user.setLoginName(request.getParameter("loginName"));
			user.setRealName(request.getParameter("loginName"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			user.setGender(Gender.valueOf(request.getParameter("gender")));
			user.setType(userTypeService.getScrollData(UserType.class).getResultList().get(0));
		} catch (Exception e) {
			json.setMsg("用户添加失败");
			json.setSuccess(false);
		}
		userService.save(user);
		user = userService.findUserByLoginName(user.getLoginName());
		json.setObj(getWebUser(user));
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/usersUI",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="select")
	public String usersUI(){
		return "user/users";
	}
	
	@RequestMapping(value="/users",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="select")
	public void users(HttpServletRequest request, HttpServletResponse response){
		QueryResult<User> qus = userService.getScrollData(User.class);
		List<User> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.User> users = new ArrayList<cn.edu.sdu.drs.web.bean.User>();
		for(User u : us){
			users.add(getWebUser(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.User> dg = new DataGrid<cn.edu.sdu.drs.web.bean.User>();
		dg.setRows(users);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}
	
	@RequestMapping(value="/editUI/{id}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="update")
	public String editUI(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id != null && !"".equals(id.trim())){
			return "user/userEdit";
		}
		return null;
	}
	
	@RequestMapping(value="/edit/{id}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="update")
	public void edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id == null || "".equals(id.trim())){
			return;
		}
		User user = userService.find(User.class, id);
		Json json = new Json("用户更新成功！！");
		try {
			if(user != null){
				user.setLoginName(request.getParameter("loginName"));
				user.setRealName(request.getParameter("realName"));
				user.setEmail(request.getParameter("email"));
				String te = request.getParameter("gender");
				if(te != null){
					if(te.equals("男")){
						te = "MAN";
					}else if(te.equals("女")){
						te = "WOMEN";
					}
				}
				user.setGender(Gender.valueOf(te));
				userService.update(user);
			}
		} catch (Exception e) {
			json.setMsg("用户更新失败！！");
			json.setSuccess(false);
			writeJson(request, response, json);
		}
		json.setObj(getWebUser(user));
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/remove",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="delete")
	public void remove(HttpServletRequest request, HttpServletResponse response){
		String strs = request.getParameter("ids");
		String ids[] = strs.split(",");
		Json json = new Json("删除用户成功");
		try {
			userService.delete(User.class, ids);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("SORRY! 删除用户失败！");
		}
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/name/{loginName}",method=RequestMethod.GET)
	public String findByName(@PathVariable String loginName, Model model){
		if(null==loginName || loginName.trim().length()==0){
			model.addAttribute("user", null);
		}else{
			User u = userService.findUserByLoginName(loginName);
			model.addAttribute("user", u);
		}
		return "user/userInfo";
	}
	
	@RequestMapping(value="/search/{name}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="user", privilegeValue="select")
	public void search(@PathVariable String name, HttpServletRequest request, HttpServletResponse response){
		if(null==name || name.trim().length()==0){
			return;
		}else{
			try {
				byte c[] = name.getBytes("iso8859-1");
				name = new String(c, "utf-8");
			} catch (UnsupportedEncodingException e) {
				return;
			}
			QueryResult<User> qus = userService.getScrollData(User.class, "o.loginName like ?1", new Object[]{"%" + name + "%"});
			List<User> us = qus.getResultList();
			List<cn.edu.sdu.drs.web.bean.User> users = new ArrayList<cn.edu.sdu.drs.web.bean.User>();
			for(User u : us){
				users.add(getWebUser(u));
			}
			DataGrid<cn.edu.sdu.drs.web.bean.User> dg = new DataGrid<cn.edu.sdu.drs.web.bean.User>();
			dg.setRows(users);
			dg.setTotal(qus.getTotleResult());
			writeJson(request, response, dg);
		}
	}
	
}
