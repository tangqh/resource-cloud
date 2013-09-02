package cn.edu.sdu.drs.controller.privilege;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;
import cn.edu.sdu.drs.web.bean.Json;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/controller/privilegeGroup")
public class PrivilegeGroupController extends BaseController {
	
	/**
	 * 增加权限组组的界面渲染,增加权限组组的界面最好支持增加的同时选择权限组
	 * @return
	 */
	@RequestMapping("/addUI")
	@Privilege(model="privilegeGroup", privilegeValue="insert")
	public String addUI(){
		return "admin/privilegeGroupAdd";
	}
	
	/**
	 * 增加权限组组
	 * @return
	 */
	@RequestMapping("/add")
	@Privilege(model="privilegeGroup", privilegeValue="insert")
	public void add(HttpServletRequest request, HttpServletResponse response){
		PrivilegeGroup pg = new PrivilegeGroup();
		Json json = new Json("权限组添加成功");
		try {
			pg.setName(request.getParameter("name"));
			List<String> params = getHttpParmas(request, response);
			for(String p : params){
				QueryResult<SystemPrivilege> qrs = systemPrivilegeService.getScrollData(SystemPrivilege.class, "o.name=?1", new Object[]{p});
				if(qrs != null && qrs.getResultList() != null){
					SystemPrivilege sp = qrs.getResultList().get(0);
					pg.addPrivilege(sp);
				}
			}
			privilegeGroupService.save(pg);
		} catch (Exception e) {
			json.setMsg("该权限组已经存在！");
			json.setSuccess(false);
		}
		pg = privilegeGroupService.find(PrivilegeGroup.class, pg.getId());
		json.setObj(getWebPrivilegeGroup(pg));
		writeJson(request, response, json);
	}
	
	/**
	 * 删除权限组组
	 */
	@RequestMapping("/remove")
	@Privilege(model="privilegeGroup", privilegeValue="delete")
	public void remove(HttpServletRequest request, HttpServletResponse response){
		String strs = request.getParameter("ids");
		String ids[] = strs.split(",");
		Json json = new Json("删除权限组组成功");
		try {
			privilegeGroupService.delete(PrivilegeGroup.class, ids);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("SORRY! 删除权限组组失败！");
		}
		writeJson(request, response, json);
	}
	
	/**
	 * 修改权限组组
	 * @return
	 */
	@RequestMapping("/editUI/{id}")
	@Privilege(model="privilegeGroup", privilegeValue="update")
	public String editUI(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		return "/admin/privilegeGroupEdit";
	}
	
	/**
	 * 修改权限组
	 */
	@RequestMapping("/edit/{id}")
	@Privilege(model="privilegeGroup", privilegeValue="update")
	public void edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id == null || "".equals(id.trim())){
			return;
		}
		PrivilegeGroup pg = privilegeGroupService.find(PrivilegeGroup.class, id);
		Json json = new Json("权限组更新成功");
		try {
			if(pg != null){
				pg.setName(request.getParameter("name"));
				List<String> params = getHttpParmas(request, response);
				pg.getPrivileges().clear();
				for(String p : params){
					QueryResult<SystemPrivilege> qrs = systemPrivilegeService.getScrollData(SystemPrivilege.class, "o.name=?1", new Object[]{p});
					if(qrs != null && qrs.getResultList() != null){
						SystemPrivilege sp = qrs.getResultList().get(0);
						pg.addPrivilege(sp);
					}
				}
				privilegeGroupService.update(pg);
			}
		} catch (Exception e) {
			json.setMsg("权限组更新失败！");
			json.setSuccess(false);
		}
		json.setObj(getWebPrivilegeGroup(pg));
		writeJson(request, response, json);
	}
	
	@RequestMapping("/privilegeGroupsUI")
	@Privilege(model="privilegeGroup", privilegeValue="select")
	public String privilegeGroupsUI(){
		return "admin/privilegeGroups";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/privilegeGroups")
	@Privilege(model="privilegeGroup", privilegeValue="select")
	public void privilegeGroups(HttpServletRequest request, HttpServletResponse response){
		QueryResult<PrivilegeGroup> qus = privilegeGroupService.getScrollData(PrivilegeGroup.class);
		List<PrivilegeGroup> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.PrivilegeGroup> users = new ArrayList<cn.edu.sdu.drs.web.bean.PrivilegeGroup>();
		for(PrivilegeGroup u : us){
			if(u.getName() != null && !u.getName().equals("租户服务组") && !u.getName().equals("saas平台管理员服务组"))
			users.add(getWebPrivilegeGroup(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.PrivilegeGroup> dg = new DataGrid<cn.edu.sdu.drs.web.bean.PrivilegeGroup>();
		dg.setRows(users);
		dg.setTotal((long) users.size());
		writeJson(request, response, dg);
	}
	
	@RequestMapping("/search/{name}")
	@Privilege(model="privilegeGroup", privilegeValue="select")
	public void search(@PathVariable String name, HttpServletRequest request, HttpServletResponse response){
		if(name == null || name.trim().length() == 0){
			return;
		}
		try {
			byte c[] = name.getBytes("iso8859-1");
			name = new String(c, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		QueryResult<PrivilegeGroup> qus = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name like ?1", new Object[]{"%"+name+"%"});
		List<PrivilegeGroup> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.PrivilegeGroup> users = new ArrayList<cn.edu.sdu.drs.web.bean.PrivilegeGroup>();
		for(PrivilegeGroup u : us){
			if(u.getName() != null && !u.getName().equals("租户服务组") && !u.getName().equals("saas平台管理员服务组"))
			users.add(getWebPrivilegeGroup(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.PrivilegeGroup> dg = new DataGrid<cn.edu.sdu.drs.web.bean.PrivilegeGroup>();
		dg.setRows(users);
		dg.setTotal((long) users.size());
		writeJson(request, response, dg);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private List<String> getHttpParmas(HttpServletRequest request, HttpServletResponse response) {
		List<String> params = new ArrayList<String>();
		/*-------------------user--------------------*/
		if(request.getParameter("userInsert") != null && !request.getParameter("userInsert").equals("")){
			params.add(request.getParameter("userInsert"));
		}
		if(request.getParameter("userUpdate") != null && !request.getParameter("userUpdate").equals("")){
			params.add(request.getParameter("userUpdate"));
		}
		if(request.getParameter("userSelect") != null && !request.getParameter("userSelect").equals("")){
			params.add(request.getParameter("userSelect"));
		}
		if(request.getParameter("userDelete") != null && !request.getParameter("userDelete").equals("")){
			params.add(request.getParameter("userDelete"));
		}
		/*-------------------admin--------------------*/
		if(request.getParameter("adminInsert") != null && !request.getParameter("adminInsert").equals("")){
			params.add(request.getParameter("adminInsert"));
		}
		if(request.getParameter("adminUpdate") != null && !request.getParameter("adminUpdate").equals("")){
			params.add(request.getParameter("adminUpdate"));
		}
		if(request.getParameter("adminSelect") != null && !request.getParameter("adminSelect").equals("")){
			params.add(request.getParameter("adminSelect"));
		}
		if(request.getParameter("adminDelete") != null && !request.getParameter("adminDelete").equals("")){
			params.add(request.getParameter("adminDelete"));
		}
		/*-------------------resource--------------------*/
		if(request.getParameter("resourceInsert") != null && !request.getParameter("resourceInsert").equals("")){
			params.add(request.getParameter("resourceInsert"));
		}
		if(request.getParameter("resourceUpdate") != null && !request.getParameter("resourceUpdate").equals("")){
			params.add(request.getParameter("resourceUpdate"));
		}
		if(request.getParameter("resourceSelect") != null && !request.getParameter("resourceSelect").equals("")){
			params.add(request.getParameter("resourceSelect"));
		}
		if(request.getParameter("resourceDelete") != null && !request.getParameter("resourceDelete").equals("")){
			params.add(request.getParameter("resourceDelete"));
		}
		/*-------------------privilege--------------------*/
		if(request.getParameter("privilegeInsert") != null && !request.getParameter("privilegeInsert").equals("")){
			params.add(request.getParameter("privilegeInsert"));
		}
		if(request.getParameter("privilegeUpdate") != null && !request.getParameter("privilegeUpdate").equals("")){
			params.add(request.getParameter("privilegeUpdate"));
		}
		if(request.getParameter("privilegeSelect") != null && !request.getParameter("privilegeSelect").equals("")){
			params.add(request.getParameter("privilegeSelect"));
		}
		if(request.getParameter("privilegeDelete") != null && !request.getParameter("privilegeDelete").equals("")){
			params.add(request.getParameter("privilegeDelete"));
		}
		return params;
	}
	
}
