package cn.edu.sdu.drs.service.tenant.admin;

import javax.persistence.Query;
import org.springframework.stereotype.Service;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.util.MD5;


/**
 * 
 * @author join
 *
 */

@Service("adminService")
public class AdminServiceImpl extends BaseDaoSupport implements AdminService {
	/**
	 * 重载save方法，保存MD5加密后密码 
	 */
	@Override
	public Object save(Object entity) {
		Admin admin = ((Admin)entity);
		admin.setPassword(MD5.MD5Encode(admin.getPassword()));
		return super.save(admin);
	}
	
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		if(entityids!=null && entityids.length>0){
			StringBuilder sb = new StringBuilder();
			for(int i=0 ; i < entityids.length ; i++){
				sb.append("'").append((String)entityids[i]).append("'").append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			Query query = em.createQuery("update Admin o set o.visible=?1 where o.id in ("+ sb + ")");
			query.setParameter(1, false);
			query.executeUpdate();
		}
	}

	public boolean exist(String id){
		long count = (Long)em.createQuery("select count(o) from Admin o where o.id=?1")
			.setParameter(1, id).getSingleResult();
		return count>0;
	}
	
	public boolean validate(String id, String password){
		long count = (Long)em.createQuery("select count(o) from Admin o where o.id=?1 and o.password=?2 and o.visible=?3")
		.setParameter(1, id).setParameter(2, password).setParameter(3, true).getSingleResult();
		return count>0;
	}

}
