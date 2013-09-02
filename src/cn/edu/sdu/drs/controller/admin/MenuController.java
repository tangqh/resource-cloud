package cn.edu.sdu.drs.controller.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.sdu.drs.bean.tenant.user.UserType;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.web.bean.Menu;

/**
 * 菜单controller
 * 
 * @author join
 * 
 */
@Controller
@RequestMapping("/controller/menu")
public class MenuController extends BaseController{
	
	/**
	 * 获得用户类型菜单树
	 */
	@RequestMapping("/get_user_type_tree")
	public void get_user_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<UserType> userTypes = userTypeService.getScrollData(UserType.class).getResultList();
		Menu menus[] = new Menu[userTypes.size()];
		for(int i=0; i<menus.length; i++){
			Map<String, Object> attributes = new LinkedHashMap<String, Object>();
			Menu m = new Menu();
			UserType ut = userTypes.get(i);
			m.setId(ut.getId());
			m.setText(ut.getName());
			attributes.put("url", ut.getUrl());
			m.setAttributes(attributes);
			if(ut.getParent() != null){
				m.setPid(ut.getParent().getId());
			}
			menus[i] = m;
		}
		writeJsonArray(request, response, getRootMenuArray(menus));
	}
	
	/**
	 * 获得资源类型菜单树
	 */
	@RequestMapping("/get_resource_type_tree")
	public void get_resource_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<Menu> menus = new ArrayList<Menu>();
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		Menu m = new Menu();
		attributes.put("url", "/controller/resource/resourcesUI");
		m.setId("resource");
		m.setPid(null);
		m.setChildren(null);
		m.setText("站点资源");
		m.setAttributes(attributes);
		menus.add(m);
		writeJsonArray(request, response, menus.toArray());
	}
	
	/**
	 * 获得权限类型菜单树
	 */
	@RequestMapping("/get_privilege_type_tree")
	public void get_privilege_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<Menu> menus = new ArrayList<Menu>();
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		Menu m = new Menu();
		attributes.put("url", "/controller/privilegeGroup/privilegeGroupsUI");
		m.setId("privilege");
		m.setPid(null);
		m.setChildren(null);
		m.setText("系统权限");
		m.setAttributes(attributes);
		menus.add(m);
		writeJsonArray(request, response, menus.toArray());
	}
	
	/**
	 * 获得管理员类型菜单树
	 */
	@RequestMapping("/get_admin_type_tree")
	public void get_admin_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(getMenu("rootAdmin", "超级管理员", "/controller/rootAdmin/rootAdminsUI"));
		menus.add(getMenu("admin", "一般管理员", "/controller/admin/adminsUI"));
		writeJsonArray(request, response, menus.toArray());
	}
	
	/**
	 * 获得saas管理员类型菜单树
	 */
	@RequestMapping("/get_saasAdmin_type_tree")
	public void get_saasAdmin_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(getMenu("saasRootAdmin", "超级管理员", "/controller/saasRootAdmin/saasRootAdminsUI"));
		menus.add(getMenu("saasAdmin", "一般管理员", "/controller/saasAdmin/saasAdminsUI"));
		writeJsonArray(request, response, menus.toArray());
	}
	
	/**
	 * 获得saas租户类型菜单树
	 */
	@RequestMapping("/get_tenant_type_tree")
	public void get_tenant_type_tree(HttpServletRequest request, HttpServletResponse response){
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(getMenu("tenant", "服务的租户", "/controller/tenant/tenantsUI"));
		writeJsonArray(request, response, menus.toArray());
	}
	
	/**
	 * @param id:菜单的id
	 * @param text:菜单上显示的文字
	 * @param url:菜单的URL
	 * @return 菜单
	 */
	private Menu getMenu(String id, String text, String url){
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		Menu m = new Menu();
		attributes.put("url", url);
		m.setId(id);
		m.setPid(null);
		m.setChildren(null);
		m.setText(text);
		m.setAttributes(attributes);
		return m;
	}
	
	
	/**
	 * 将所有菜单变成树的形式
	 * @param menus 返回所有根节点
	 * @return
	 */
	private Object[] getRootMenuArray(Menu[] menus){
		List<Menu> rootMenus = new ArrayList<Menu>();
		for(Menu m : menus){
			if(m.getPid() == null || "".equals(m.getPid())){
				rootMenus.add(m);
			}
		}
		for(Menu m : rootMenus){
			List<Menu> tm = new ArrayList<Menu>();
			for(Menu mm : menus){
				if(mm.getPid() == m.getId()){
					tm.add(mm);
				}
			}
			Menu rm[] = new Menu[tm.size()];
			for(int i=0; i<rm.length; i++){
				rm[i] = tm.get(i);
			}
			m.setChildren(rm);
		}
		return rootMenus.toArray();
	}
	
}
