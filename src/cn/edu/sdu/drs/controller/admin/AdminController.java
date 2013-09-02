package cn.edu.sdu.drs.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.edu.sdu.drs.bean.Gender;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;
import cn.edu.sdu.drs.web.bean.Json;

/**
 * 主要负责资源的CRUD
 *	功能						方法			url			返回页面<br>
 *	添加管理员界面				addUI		addUI		admin/adminAdd<br>
 *	添加管理员					add			add			json<br>
 *	删除管理员					remove		remove		json<br>
 *	编辑管理员界面				editUI		editUI/id	admin/adminEdit<br>
 *	编辑管理员					edit		edit/id		json<br>
 *	列出管理员					admins		admins		json<br>
 *
 * 	@author join
 */
@Controller
@RequestMapping("/controller/admin")
public class AdminController extends BaseController{
	
	/**
	 * 显示增加管理员的界面
	 * @return 增加管理员的界面
	 */
	@RequestMapping(value="/addUI", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="insert")
	public String addUI(){
		return "admin/adminAdd";
	}
	
	/**
	 * 增加管理员
	 * @return
	 */
	@RequestMapping("/add")
	@Privilege(model="admin", privilegeValue="insert")
	public void add(HttpServletRequest request, HttpServletResponse response){
		String pgn = request.getParameter("pgn");//权限组的名字
		Admin admin = new Admin();
		Json json = new Json("管理员添加成功");
		try {
			admin.setId(request.getParameter("id"));
			admin.setRealname(request.getParameter("realName"));
			admin.setEmail(request.getParameter("email"));
			admin.setPassword(request.getParameter("password"));
			admin.setGender(Gender.valueOf(request.getParameter("gender")));
			admin.setPhone(request.getParameter("phone"));
			QueryResult<PrivilegeGroup> qusb = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{pgn});
			PrivilegeGroup privilegeGroups4Userb = null;
			if(qusb != null && qusb.getResultList().size() > 0){
				privilegeGroups4Userb = qusb.getResultList().get(0);
			}
			if(privilegeGroups4Userb != null){
				admin.addPrivilegeGroups(privilegeGroups4Userb);
			}
			adminService.save(admin);
		} catch (Exception e) {
			json.setMsg("该管理员已经存在！");
			json.setSuccess(false);
		}
		admin = adminService.find(Admin.class, admin.getId());
		json.setObj(getWebAdmin(admin));
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/adminsUI", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="select")
	public String adminsUI(){
		return "admin/admins";
	}
	
	@RequestMapping(value="/admins", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="select")
	public void admins(HttpServletRequest request, HttpServletResponse response){
		QueryResult<Admin> qus = adminService.getScrollData(Admin.class, "o.visible=?1", new Object[]{true});
		List<Admin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.Admin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.Admin>();
		for(Admin u : us){
			admins.add(getWebAdmin(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.Admin> dg = new DataGrid<cn.edu.sdu.drs.web.bean.Admin>();
		dg.setRows(admins);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}
	

	/**
	 * 显示edit管理员的界面
	 * @return 增加管理员的界面
	 */
	@RequestMapping(value="/editUI/{id}", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="insert")
	public String editUI(@PathVariable String id){
		return "admin/adminEdit";
	}
	
	/**
	 * edit管理员
	 * @param id 管理员的ID
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	@Privilege(model="admin", privilegeValue="insert")
	public void edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id == null || "".equals(id.trim())){
			return;
		}
		String pgn = request.getParameter("pgn");//权限组的名字
		Admin admin = adminService.find(Admin.class, id);
		Json json = new Json("管理员更新成功！！");
		try {
			if(admin != null){
				admin.setRealname(request.getParameter("realName"));
				admin.setEmail(request.getParameter("email"));
				String te = request.getParameter("gender");
				if(te != null){
					if(te.equals("男")){
						te = "MAN";
					}else if(te.equals("女")){
						te = "WOMEN";
					}
				}
				admin.setGender(Gender.valueOf(te));
				admin.setPhone(request.getParameter("phone"));
				QueryResult<PrivilegeGroup> qusb = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{pgn});
				PrivilegeGroup privilegeGroups4Userb = null;
				admin.getPrivilegeGroups().clear();
				if(qusb != null && qusb.getResultList().size() > 0){
					privilegeGroups4Userb = qusb.getResultList().get(0);
				}
				if(privilegeGroups4Userb != null){
					admin.addPrivilegeGroups(privilegeGroups4Userb);
				}
				adminService.update(admin);
			}
		} catch (Exception e) {
			json.setMsg("管理员更新失败！！");
			json.setSuccess(false);
			writeJson(request, response, json);
		}
		json.setObj(getWebAdmin(admin));
		writeJson(request, response, json);
	}
	
	/**
	 * 逻辑删除管理员，修改Admin的字段
	 * @param id 管理员的ID
	 * @return
	 */
	@RequestMapping("/remove")
	@Privilege(model="admin", privilegeValue="delete")
	public void remove(HttpServletRequest request, HttpServletResponse response){
		String strs = request.getParameter("ids");
		String ids[] = strs.split(",");
		Json json = new Json("删除管理员成功");
		try {
			adminService.delete(Admin.class, ids);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("SORRY! 删除管理员失败！");
		}
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/search/{name}", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="select")
	public void search(@PathVariable String name, HttpServletRequest request, HttpServletResponse response){
		if(name == null || name.trim().length()==0){
			return;
		}
		try {
			byte c[] = name.getBytes("iso8859-1");
			name = new String(c, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		QueryResult<Admin> qus = adminService.getScrollData(Admin.class, "o.visible=?1 and o.realname like ?2", new Object[]{true, "%" + name + "%"});
		List<Admin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.Admin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.Admin>();
		for(Admin u : us){
			admins.add(getWebAdmin(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.Admin> dg = new DataGrid<cn.edu.sdu.drs.web.bean.Admin>();
		dg.setRows(admins);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}
	
}
