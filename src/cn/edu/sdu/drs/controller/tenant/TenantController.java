package cn.edu.sdu.drs.controller.tenant;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.web.bean.DataGrid;
import cn.edu.sdu.drs.web.bean.Json;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/controller/tenant")
public class TenantController extends BaseController{
	
	@RequestMapping(value="/tenantsUI",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="select")
	public String tenantsUI(){
		return "saasAdmin/tenants";
	}
	
	@RequestMapping(value="/tenants",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="select")
	public void tenants(HttpServletRequest request, HttpServletResponse response){
		QueryResult<Tenant> qus = tenantService.getScrollData(Tenant.class, "o.visible=?1", new Object[]{true});
		List<Tenant> us = qus.getResultList();
		List<cn.edu.sdu.drs.web.bean.Tenant> tenants = new ArrayList<cn.edu.sdu.drs.web.bean.Tenant>();
		for(Tenant u : us){
			tenants.add(getWebTenant(u));
		}
		DataGrid<cn.edu.sdu.drs.web.bean.Tenant> dg = new DataGrid<cn.edu.sdu.drs.web.bean.Tenant>();
		dg.setRows(tenants);
		dg.setTotal(qus.getTotleResult());
		writeJson(request, response, dg);
	}
	
	@RequestMapping(value="/editUI/{id}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="update")
	public String editUI(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id != null && !"".equals(id.trim())){
			return "saasAdmin/tenantEdit";
		}
		return null;
	}
	
	@RequestMapping(value="/edit/{id}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="update")
	public void edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
		if(id == null || "".equals(id.trim())){
			return;
		}
		Tenant t = tenantService.find(Tenant.class, id);
		Json json = new Json("租户更新成功！！");
		try {
			if(t != null){
				t.setName(request.getParameter("name"));
				t.setNote(request.getParameter("note"));
				tenantService.update(t);
			}
		} catch (Exception e) {
			json.setMsg("租户更新失败！！");
			json.setSuccess(false);
			writeJson(request, response, json);
		}
		json.setObj(getWebTenant(t));
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/remove",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="delete")
	public void remove(HttpServletRequest request, HttpServletResponse response){
		String strs = request.getParameter("ids");
		String ids[] = strs.split(",");
		Json json = new Json("删除租户成功");
		try {
			tenantService.delete(Tenant.class, ids);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("SORRY! 删除租户失败！");
		}
		writeJson(request, response, json);
	}
	
	@RequestMapping(value="/search/{name}",method={RequestMethod.GET, RequestMethod.POST})
	@Privilege(model="tenant", privilegeValue="select")
	public void search(@PathVariable String name, HttpServletRequest request, HttpServletResponse response){
		if(null==name || name.trim().length()==0){
			return;
		}else{
			QueryResult<Tenant> qus = userService.getScrollData(Tenant.class, "o.name like ?1", new Object[]{"%" + name + "%"});
			List<Tenant> us = qus.getResultList();
			List<cn.edu.sdu.drs.web.bean.Tenant> tenants = new ArrayList<cn.edu.sdu.drs.web.bean.Tenant>();
			for(Tenant u : us){
				tenants.add(getWebTenant(u));
			}
			DataGrid<cn.edu.sdu.drs.web.bean.Tenant> dg = new DataGrid<cn.edu.sdu.drs.web.bean.Tenant>();
			dg.setRows(tenants);
			dg.setTotal(qus.getTotleResult());
			writeJson(request, response, dg);
		}
	}

}
