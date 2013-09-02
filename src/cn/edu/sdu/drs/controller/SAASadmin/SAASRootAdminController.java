package cn.edu.sdu.drs.controller.SAASadmin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;

@Controller
@RequestMapping("/controller/saasRootAdmin")
public class SAASRootAdminController extends BaseController{
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/saasRootAdminsUI", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="saasAdmin", privilegeValue="select")
	public String rootAdminsUI(){
		return "saasAdmin/sRootAdmins";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saasRootAdmins", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="saasAdmin", privilegeValue="select")
	public void rootAdmins(HttpServletRequest request, HttpServletResponse response){
		QueryResult<SAASRootAdmin> qus = saasRootAdminService.getScrollData(SAASRootAdmin.class, "o.visible=?1", new Object[]{true});
		List<SAASRootAdmin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.RootAdmin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.RootAdmin>();
		for(SAASRootAdmin u : us){
			admins.add(getWebSAASRootAdmin(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin> dg = new DataGrid<cn.edu.sdu.drs.web.bean.RootAdmin>();
		dg.setRows(admins);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}
	
	@RequestMapping(value="/search/{name}", method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="saasAdmin", privilegeValue="select")
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
		QueryResult<SAASRootAdmin> qus = saasRootAdminService.getScrollData(SAASRootAdmin.class, "o.visible=?1 and o.realName like ?2", new Object[]{true, "%" + name + "%"});
		List<SAASRootAdmin> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.RootAdmin> admins = new ArrayList<cn.edu.sdu.drs.web.bean.RootAdmin>();
		for(SAASRootAdmin u : us){
			admins.add(getWebSAASRootAdmin(u));
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
	private cn.edu.sdu.drs.web.bean.RootAdmin getWebSAASRootAdmin(SAASRootAdmin u) {
		cn.edu.sdu.drs.web.bean.RootAdmin ra = new cn.edu.sdu.drs.web.bean.RootAdmin();
		ra.setId(u.getRealName());
		ra.setRealName(u.getRealName());
		for(PrivilegeGroup pg : u.getPrivilegeGroups()){
			ra.addPrivilegeGroup(pg.getName());
		}
		return ra;
	}

}
