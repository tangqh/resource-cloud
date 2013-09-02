package cn.edu.sdu.drs.service.tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.service.tenant.user.UserService;

/**
 * 
 * @author join
 *
 */

@Service("tenantService")
public class TenantServiceImpl extends BaseDaoSupport implements TenantService {

	@Resource private UserService userService;
	
	@Override
	public Object save(Object entity) {
		Tenant t = (Tenant)entity;
		t.setId(UUID.randomUUID().toString());
		return super.save(entity);
	}
	
	
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids){
		if(entityids != null && entityids.length > 0){
			for(Object o : entityids){
				Tenant t = find(Tenant.class, o);
				List<Object> us = new ArrayList<Object>();
				for(User u : t.getUsers()){
					u.removeTenant(t);
					if(u.getTenants() == null || u.getTenants().size() == 0){
						us.add(u.getId());
					}
				}
				userService.delete(User.class, us.toArray());
				t.setVisible(false);
				update(t);
			}
		}
	}

}
