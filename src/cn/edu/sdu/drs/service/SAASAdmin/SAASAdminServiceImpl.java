package cn.edu.sdu.drs.service.SAASAdmin;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.util.MD5;

/**
 * 
 * @author join
 * 
 */

@Service("saasAdminService")
public class SAASAdminServiceImpl extends BaseDaoSupport implements SAASAdminService  {
	
	/**
	 * 重载save方法，保存MD5加密后密码 
	 */
	@Override
	public Object save(Object entity) {
		SAASAdmin admin = ((SAASAdmin)entity);
		admin.setPassword(MD5.MD5Encode(admin.getPassword()));
		return super.save(admin);
	}
	
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		if(entityids!=null && entityids.length>0){
			StringBuilder sb = new StringBuilder();
			for(int i=0 ; i < entityids.length ; i++){
				sb.append('?').append(i+2).append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			Query query = em.createQuery("update SAASAdmin o set o.visible=?1 where o.id in ("+ sb + ")");
			query.setParameter(1, false);
			for(int i=0 ; i < entityids.length ; i++){
				query.setParameter(i+2, entityids[i]);
			}
			query.executeUpdate();
		}
	}

	public boolean exist(String id){
		long count = (Long)em.createQuery("select count(o) from SAASAdmin o where o.id=?1")
			.setParameter(1, id).getSingleResult();
		return count>0;
	}
	
	public boolean validate(String id, String password){
		long count = (Long)em.createQuery("select count(o) from SAASAdmin o where o.id=?1 and o.password=?2 and o.visible=?3")
		.setParameter(1, id).setParameter(2, password).setParameter(3, true).getSingleResult();
		return count>0;
	}
	

}
