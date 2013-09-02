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

import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;

/**
 *	主要负责admin的CRUD<br>
 *	功能						方法			url			请求		返回页面<br>
 *	登录界面					login		login		get		rootAdmin/login<br>
 *	登录验证					login		login		post	admins.jsp<br>
 *******************************************************************<br>
 *	添加admin界面			add			add			get		rootAdmin/adminAdd<br>
 *	添加admin				add			add			post	rootAdmin/admins<br>
 *	删除admin				delete		delete/id	get		rootAdmin/admins<br>
 *	编辑admin				edit		edit/id		get		rootAdmin/adminEdit<br>
 *	返回所有admin			admins		admins		get		rootAdmin/admins<br>
 *	根据Id返回指定admin		findById	id/id		get		rootAdmin/adminInfo<br>
 *	根据用户名返回指定admin	findByName	name/name	get		rootAdmin/adminInfo<br>
 */

@Controller
@RequestMapping("/controller/rootAdmin")
public class RootAdminController extends BaseController {
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/rootAdminsUI", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="select")
	public String rootAdminsUI(){
		return "admin/rootAdmins";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/rootAdmins", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="admin", privilegeValue="select")
	public void rootAdmins(HttpServletRequest request, HttpServletResponse response){
		QueryResult<RootAdmin> qus = rootAdminService.getScrollData(RootAdmin.class, "o.visible=?1", new Object[]{true});
		List<RootAdmin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.RootAdmin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.RootAdmin>();
		for(RootAdmin u : us){
			admins.add(getWebRootAdmin(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin> dg = new DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin>();
		dg.setRows(admins);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
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
		QueryResult<RootAdmin> qus = rootAdminService.getScrollData(RootAdmin.class, "o.visible=?1 and o.realName like ?2", new Object[]{true, "%" + name + "%"});
		List<RootAdmin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.RootAdmin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.RootAdmin>();
		for(RootAdmin u : us){
			admins.add(getWebRootAdmin(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin> dg = new DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin>();
		dg.setRows(admins);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}

	/**
	 * 
	 * @param u
	 * @return
	 */
	private cn.edu.sdu.drs.web.bean.RootAdmin getWebRootAdmin(RootAdmin u) {
		cn.edu.sdu.drs.web.bean.RootAdmin ra = new cn.edu.sdu.drs.web.bean.RootAdmin();
		ra.setId(u.getRealName());
		ra.setRealName(u.getRealName());
		for(PrivilegeGroup pg : u.getPrivilegeGroups()){
			ra.addPrivilegeGroup(pg.getName());
		}
		return ra;
	}
}
