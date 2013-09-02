package cn.edu.sdu.drs.controller.tenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.dao.bean.QueryResult;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/tenant")
public class TenantIndex extends BaseController{

	@RequestMapping(value="/add",method={RequestMethod.GET, RequestMethod.POST})
	public String add(HttpServletRequest request, HttpServletResponse response){
		String tenantName = request.getParameter("tenant-name");
		String rootAdminId = request.getParameter("root-admin-id");
		String rootAdminRealName = request.getParameter("root-admin-name");
		String rootAdminPwd = request.getParameter("root-admin-pwd");
		String rootAdminRePwd = request.getParameter("root-admin-repwd");
		String tenantNote = request.getParameter("tenant-note");
		if(tenantName==null || "".equals(tenantName)
				|| rootAdminId==null || "".equals(rootAdminId)
				|| rootAdminPwd==null || "".equals(rootAdminPwd)
				|| rootAdminRePwd==null || "".equals(rootAdminRePwd)
				|| rootAdminRealName==null || "".equals(rootAdminRealName)
				|| !rootAdminPwd.equals(rootAdminRePwd)
			){
			request.setAttribute("msg", "注册失败！");
			return "exception/message";
		}
		if(saasRootAdminService.find(SAASRootAdmin.class, rootAdminId)!=null){
			request.setAttribute("msg", "超管账号已经存在！");
			return "exception/message";
		}
		try {
			Tenant t = new Tenant(tenantName);
			t.setNote(tenantNote);
			tenantService.save(t);
			RootAdmin ra = new RootAdmin(rootAdminId, rootAdminPwd, rootAdminRealName);
			QueryResult<PrivilegeGroup> qusp = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"权限服务组"});
			QueryResult<PrivilegeGroup> qusr = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"管理员服务组"});
			PrivilegeGroup privilegeGroups4Userr = null, pg = null;
			if(qusp != null && qusp.getResultList().size() > 0){
				privilegeGroups4Userr = qusp.getResultList().get(0);
			}
			if(privilegeGroups4Userr != null){
				ra.addPrivilegeGroups(privilegeGroups4Userr);
			}
			if(qusr != null && qusr.getResultList().size() > 0){
				pg = qusr.getResultList().get(0);
			}
			if(pg != null){
				ra.addPrivilegeGroups(pg);
			}
			ra.setTenant(t);
			rootAdminService.save(ra);
		} catch (Exception e) {
			request.setAttribute("msg", "出错了！！");
			return "exception/message";
		}
		return "admin/adminIndex";
	}
	
}
