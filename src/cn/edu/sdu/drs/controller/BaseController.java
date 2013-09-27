package cn.edu.sdu.drs.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.SimpleFormController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.service.SAASAdmin.SAASAdminService;
import cn.edu.sdu.drs.service.SAASAdmin.SAASRootAdminService;
import cn.edu.sdu.drs.service.privilege.PrivilegeGroupService;
import cn.edu.sdu.drs.service.privilege.SystemPrivilegeService;
import cn.edu.sdu.drs.service.resources.XMLService;
import cn.edu.sdu.drs.service.tenant.TenantService;
import cn.edu.sdu.drs.service.tenant.admin.AdminService;
import cn.edu.sdu.drs.service.tenant.admin.RootAdminService;
import cn.edu.sdu.drs.service.tenant.user.UserService;
import cn.edu.sdu.drs.service.tenant.user.UserTypeService;

/**
 * 
 * @author join
 *
 */

public class BaseController extends SimpleFormController{
	
	/** 用于管理用户和用户类型 **/
	@Resource protected UserService userService;
	@Resource protected UserTypeService userTypeService;
	
	/** 用于管理管理员 **/
	@Resource protected AdminService adminService;
	@Resource protected RootAdminService rootAdminService;
	
	@Resource protected SAASAdminService saasAdminService;
	@Resource protected SAASRootAdminService saasRootAdminService;
	
	@Resource protected SystemPrivilegeService systemPrivilegeService;
	@Resource protected PrivilegeGroupService privilegeGroupService;
	
	@Resource protected XMLService xmlResourceService;
	
	@Resource protected TenantService tenantService;
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 */
	public void writeJson(HttpServletRequest request, HttpServletResponse response, Object object) {
		try {
			String json = JSONObject.fromObject(object).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象数组转换成JSON字符串，并响应回前台
	 */
	public void writeJsonArray(HttpServletRequest request, HttpServletResponse response, Object[] objects) {
		try {
			String json = JSONArray.fromObject(objects).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得用于json的admin
	 * @param admin
	 * @return
	 */
	protected cn.edu.sdu.drs.web.bean.Admin getWebAdmin(Admin admin) {
		if(admin == null){
			return null;
		}
		cn.edu.sdu.drs.web.bean.Admin wu = new cn.edu.sdu.drs.web.bean.Admin();
		wu.setId(admin.getId());
		wu.setRealName(admin.getRealname());
		wu.setEmail(admin.getEmail());
		wu.setGender(admin.getGender().getName());
		wu.setPhone(admin.getPhone());
		for(PrivilegeGroup pg : admin.getPrivilegeGroups()){
			wu.appendPrivilegeGroup(pg.getName());
		}
		return wu;
	}
	
	/**
	 * 获得用于json的admin
	 * @param admin
	 * @return
	 */
	protected cn.edu.sdu.drs.web.bean.Admin getWebSAASAdmin(SAASAdmin admin) {
		if(admin == null){
			return null;
		}
		cn.edu.sdu.drs.web.bean.Admin wu = new cn.edu.sdu.drs.web.bean.Admin();
		wu.setId(admin.getId());
		wu.setRealName(admin.getRealname());
		wu.setEmail(admin.getEmail());
		wu.setGender(admin.getGender().getName());
		wu.setPhone(admin.getPhone());
		for(PrivilegeGroup pg : admin.getPrivilegeGroups()){
			wu.appendPrivilegeGroup(pg.getName());
		}
		return wu;
	}
	
	/**
	 * 
	 * @param u
	 * @return
	 */
	protected cn.edu.sdu.drs.web.bean.User getWebUser(User u){
		if(u == null){
			return null;
		}
		cn.edu.sdu.drs.web.bean.User wu = new cn.edu.sdu.drs.web.bean.User();
		wu.setId(u.getId());
		wu.setLoginName(u.getLoginName());
		wu.setRealName(u.getRealName());
		wu.setEmail(u.getEmail());
		wu.setGender(u.getGender().getName());
		return wu;
	}
	
	/**
	 * 
	 * @param u
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected cn.edu.sdu.drs.web.bean.Tenant getWebTenant(Tenant u){
		if(u == null){
			return null;
		}
		cn.edu.sdu.drs.web.bean.Tenant t = new cn.edu.sdu.drs.web.bean.Tenant();
		t.setId(u.getId());
		t.setName(u.getName());
		t.setNote(u.getNote());
		t.setCreateTime(u.getCreateTime().toLocaleString());
		t.setHost(u.getHost());
		t.setPort(u.getPort());
		t.setRootFolder(u.getRootFolder());
		return t;
	}
	
	/**
	 * 
	 * @param u
	 * @return
	 */
	protected cn.edu.sdu.drs.web.bean.PrivilegeGroup getWebPrivilegeGroup(
			PrivilegeGroup u) {
		if(u == null){
			return null;
		}
		cn.edu.sdu.drs.web.bean.PrivilegeGroup pg = new cn.edu.sdu.drs.web.bean.PrivilegeGroup();
		pg.setId(u.getId());
		pg.setName(u.getName());
		for(SystemPrivilege sp : u.getPrivileges()){
			pg.addPrivilege(sp.getName());
		}
		return pg;
	}
	
}
