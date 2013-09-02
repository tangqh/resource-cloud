package cn.edu.sdu.drs.service.tenant.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.tenant.user.UserType;
import cn.edu.sdu.drs.dao.BaseDaoSupport;



/**
 * 
 * @author join
 *
 */

@Service("userTypeService")
public class UserTypeServiceImpl extends BaseDaoSupport implements UserTypeService {

	@Override
	public Object save(Object entity) {
		UserType mt = ((UserType)entity);
		mt.setId(UUID.randomUUID().toString());
		return super.save(mt);
	}
}
