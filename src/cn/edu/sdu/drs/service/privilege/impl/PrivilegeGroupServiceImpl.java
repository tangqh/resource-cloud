package cn.edu.sdu.drs.service.privilege.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.service.privilege.PrivilegeGroupService;
/**
 * 
 * @author join
 *
 */

@Service("privilegeGroupService")
public class PrivilegeGroupServiceImpl extends BaseDaoSupport implements PrivilegeGroupService {

	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		for(Object id : entityids){
			PrivilegeGroup group = find(PrivilegeGroup.class, id);
			/* 在所有相关的saas平台的一般管理员中移除该权限组 */
			for(SAASAdmin saasAdmin : group.getSaasAdmin()){
				saasAdmin.getPrivilegeGroups().remove(group);
			}
			/* 在所有相关的租户内部的一般管理员中移除该权限组 */
			for(Admin admin : group.getAdmin()){
				admin.getPrivilegeGroups().remove(group);
			}
			/* 在所有相关的租户内部的超级管理员中移除该权限组 */
			for(RootAdmin rootAdmin : group.getRootAdmin()){
				rootAdmin.getPrivilegeGroups().remove(group);
			}
			/* 在所有相关的saas平台的超级管理员中移除该权限组 */
			for(SAASRootAdmin saasRootAdmin : group.getSaasRootAdmin()){
				saasRootAdmin.getPrivilegeGroups().remove(group);
			}
			em.remove(group);
		}
	}

	@Override
	public Object save(Object entity) {
		((PrivilegeGroup)entity).setId(UUID.randomUUID().toString());
		return super.save(entity);
	}
	
}
