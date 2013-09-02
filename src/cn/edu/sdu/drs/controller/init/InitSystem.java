package cn.edu.sdu.drs.controller.init;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.sdu.drs.bean.Gender;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.bean.tenant.user.UserType;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.dao.bean.QueryResult;

@Controller
@RequestMapping("/initSystem")
public class InitSystem extends BaseController{

	/**
	 * 全部初始化
	 */
	@RequestMapping("/initAll")
	public String init() {
		initUser();
		initTenant();
		initPrivilege();
		initAdmin("好学网");
		initSaasAdmin();
		return "initOK";
	}
	
	@RequestMapping("/initTenant")
	public void initTenant() {
		Tenant t1 = new Tenant("长江报社");
		t1.setNote("传播中外信息，争取世界和平！");
		
		Tenant t2 = new Tenant("好学网");
		t2.setNote("积累知识的网站，增长智慧的源泉！");
		
		Tenant t3 = new Tenant("test");
		t3.setNote("test");
		
		tenantService.save(t1);
		tenantService.save(t2);
		tenantService.save(t3);
	}

	/**
	 * 初始化管理员模块
	 */
	@RequestMapping("/initAdmin")
	public void initAdmin(String tenantName) {
		/* ----------------------admin----------------------------- */
		Admin a = new Admin("userAdmin");
		a.setRealname("yonggangYuan");
		a.setEmail("sdcsyyg@163.com");
		a.setPassword(a.getId());
		a.setPhone("18063420612");
		QueryResult<PrivilegeGroup> qus = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"用户服务组"});
		PrivilegeGroup privilegeGroups4User = null;
		if(qus != null && qus.getResultList().size() > 0){
			privilegeGroups4User = qus.getResultList().get(0);
		}
		if(privilegeGroups4User != null){
			a.addPrivilegeGroups(privilegeGroups4User);
		}
		Admin b = new Admin("resourceAdmin");
		b.setRealname("shaozeMi");
		b.setEmail("sdumsz@163.com");
		b.setPassword(b.getId());
		b.setPhone("18063420630");
		QueryResult<PrivilegeGroup> qusb = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"资源服务组"});
		PrivilegeGroup privilegeGroups4Userb = null;
		if(qusb != null && qusb.getResultList().size() > 0){
			privilegeGroups4Userb = qusb.getResultList().get(0);
		}
		if(privilegeGroups4Userb != null){
			b.addPrivilegeGroups(privilegeGroups4Userb);
		}
		
		QueryResult<Tenant> qt =  tenantService.getScrollData(Tenant.class, "o.name=?1", new Object[]{tenantName});
		Tenant t = null;
		if(qt !=null && qt.getResultList().size() > 0){
			t = qt.getResultList().get(0);
		}
		if(t != null){
			a.setTenant(t);
			b.setTenant(t);
		}
		adminService.save(a);
		adminService.save(b);
		
		/* ----------------------rootAdmin----------------------------- */
		RootAdmin ra = new RootAdmin();
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
		
		if(t != null){
			ra.setTenant(t);
		}
		rootAdminService.save(ra);
	}
	
	/**
	 * 初始化管理员模块
	 */
	@RequestMapping("/initSaasAdmin")
	public void initSaasAdmin() {
		
		/* ----------------------saasAdmin----------------------------- */
		SAASAdmin a = new SAASAdmin("tenantAdmin");
		a.setRealname("yonggangYuan");
		a.setEmail("sdcsyyg@163.com");
		a.setPassword(a.getId());
		a.setPhone("18063420612");
		QueryResult<PrivilegeGroup> qus = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"租户服务组"});
		PrivilegeGroup privilegeGroup = null;
		if(qus != null && qus.getResultList().size() > 0){
			privilegeGroup = qus.getResultList().get(0);
		}
		if(privilegeGroup != null){
			a.addPrivilegeGroups(privilegeGroup);
		}
		saasAdminService.save(a);
		
		/* ----------------------saasRootAdmin----------------------------- */
		SAASRootAdmin ra = new SAASRootAdmin();
		QueryResult<PrivilegeGroup> qusp = privilegeGroupService.getScrollData(PrivilegeGroup.class, "o.name=?1", new Object[]{"saas平台管理员服务组"});
		PrivilegeGroup privilegeGroups4Userr = null;
		if(qusp != null && qusp.getResultList().size() > 0){
			privilegeGroups4Userr = qusp.getResultList().get(0);
		}
		if(privilegeGroups4Userr != null){
			ra.addPrivilegeGroups(privilegeGroups4Userr);
		}
		saasRootAdminService.save(ra);
	}
	
	/**
	 * 初始化用户模块
	 */
	@RequestMapping("/initUser")
	public void initUser() {
		UserType ut = new UserType("普通会员");
		ut.setUrl("/controller/user/usersUI");
		userTypeService.save(ut);
		for (int i = 0; i < 10; i++) {
			User u = new User("user" + i);
			u.setPassword(u.getLoginName());
			u.setType(ut);
			u.setRealName(u.getLoginName());
			u.setGender(Gender.MAN);
			userService.save(u);
		}
	}
	
	@RequestMapping("/initPrivilege")
	public void initPrivilege(){
		/* -----------------user---------------- */
		SystemPrivilege ui = new SystemPrivilege("user", "insert", "增加用户");
		SystemPrivilege ud = new SystemPrivilege("user", "delete", "删除用户");
		SystemPrivilege uu = new SystemPrivilege("user", "update", "更新用户");
		SystemPrivilege us = new SystemPrivilege("user", "select", "查找用户");
		systemPrivilegeService.save(ui);
		systemPrivilegeService.save(ud);
		systemPrivilegeService.save(uu);
		systemPrivilegeService.save(us);
		PrivilegeGroup pgu = new PrivilegeGroup("用户服务组");
		pgu.addPrivilege(ui);
		pgu.addPrivilege(ud);
		pgu.addPrivilege(uu);
		pgu.addPrivilege(us);
		privilegeGroupService.save(pgu);
		
		/* -----------------privilegeGroup---------------- */
		SystemPrivilege pi = new SystemPrivilege("privilegeGroup", "insert", "增加权限组");
		SystemPrivilege pd = new SystemPrivilege("privilegeGroup", "delete", "删除权限组");
		SystemPrivilege pu = new SystemPrivilege("privilegeGroup", "update", "更新权限组");
		SystemPrivilege ps = new SystemPrivilege("privilegeGroup", "select", "查找权限组");
		systemPrivilegeService.save(pi);
		systemPrivilegeService.save(pd);
		systemPrivilegeService.save(pu);
		systemPrivilegeService.save(ps);
		PrivilegeGroup pgp = new PrivilegeGroup("权限服务组");
		pgp.addPrivilege(pi);
		pgp.addPrivilege(pd);
		pgp.addPrivilege(pu);
		pgp.addPrivilege(ps);
		privilegeGroupService.save(pgp);
		
		/* -----------------resource---------------- */
		SystemPrivilege ri = new SystemPrivilege("resource", "insert", "增加资源");
		SystemPrivilege rd = new SystemPrivilege("resource", "delete", "删除资源");
		SystemPrivilege ru = new SystemPrivilege("resource", "update", "更新资源");
		SystemPrivilege rs = new SystemPrivilege("resource", "select", "查找资源");
		systemPrivilegeService.save(ri);
		systemPrivilegeService.save(rd);
		systemPrivilegeService.save(ru);
		systemPrivilegeService.save(rs);
		PrivilegeGroup pgr = new PrivilegeGroup("资源服务组");
		pgr.addPrivilege(ri);
		pgr.addPrivilege(rd);
		pgr.addPrivilege(ru);
		pgr.addPrivilege(rs);
		privilegeGroupService.save(pgr);
		
		/* -----------------admin---------------- */
		SystemPrivilege ai = new SystemPrivilege("admin", "insert", "增加管理员");
		SystemPrivilege ad = new SystemPrivilege("admin", "delete", "删除管理员");
		SystemPrivilege au = new SystemPrivilege("admin", "update", "更新管理员");
		SystemPrivilege as = new SystemPrivilege("admin", "select", "查找管理员");
		systemPrivilegeService.save(ai);
		systemPrivilegeService.save(ad);
		systemPrivilegeService.save(au);
		systemPrivilegeService.save(as);
		PrivilegeGroup pga = new PrivilegeGroup("管理员服务组");
		pga.addPrivilege(ai);
		pga.addPrivilege(ad);
		pga.addPrivilege(au);
		pga.addPrivilege(as);
		privilegeGroupService.save(pga);
		
		/* -----------------saassadmin---------------- */
		SystemPrivilege sai = new SystemPrivilege("saasAdmin", "insert", "增加saas管理员");
		SystemPrivilege sad = new SystemPrivilege("saasAdmin", "delete", "删除saas管理员");
		SystemPrivilege sau = new SystemPrivilege("saasAdmin", "update", "更新saas管理员");
		SystemPrivilege sas = new SystemPrivilege("saasAdmin", "select", "查找saas管理员");
		systemPrivilegeService.save(sai);
		systemPrivilegeService.save(sad);
		systemPrivilegeService.save(sau);
		systemPrivilegeService.save(sas);
		PrivilegeGroup spga = new PrivilegeGroup("saas平台管理员服务组");
		spga.addPrivilege(sai);
		spga.addPrivilege(sad);
		spga.addPrivilege(sau);
		spga.addPrivilege(sas);
		privilegeGroupService.save(spga);
		
		/* -----------------tenant---------------- */
		SystemPrivilege ti = new SystemPrivilege("tenant", "insert", "增加租户");
		SystemPrivilege td = new SystemPrivilege("tenant", "delete", "删除租户");
		SystemPrivilege tu = new SystemPrivilege("tenant", "update", "更新租户");
		SystemPrivilege ts = new SystemPrivilege("tenant", "select", "查找租户");
		systemPrivilegeService.save(ti);
		systemPrivilegeService.save(td);
		systemPrivilegeService.save(tu);
		systemPrivilegeService.save(ts);
		PrivilegeGroup t = new PrivilegeGroup("租户服务组");
		t.addPrivilege(ti);
		t.addPrivilege(td);
		t.addPrivilege(tu);
		t.addPrivilege(ts);
		privilegeGroupService.save(t);
	}
	
}
