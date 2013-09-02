package cn.edu.sdu.drs.service.SAASAdmin;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.util.MD5;

/**
 * 
 * @author join
 *
 */

@Service("saasRootAdminService")
public class SAASRootAdminServiceImpl  extends BaseDaoSupport implements SAASRootAdminService  {
	
	/**
	 * 重载save方法，保存MD5加密后密码 
	 */
	@Override
	public Object save(Object entity) {
		SAASRootAdmin admin = ((SAASRootAdmin)entity);
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
			Query query = em.createQuery("update SAASRootAdmin o set o.visible=?1 where o.userName in ("+ sb + ")");
			query.setParameter(1, false);
			for(int i=0 ; i < entityids.length ; i++){
				query.setParameter(i+2, entityids[i]);
			}
			query.executeUpdate();
		}
	}

	public boolean exist(String userName){
		long count = (Long)em.createQuery("select count(o) from SAASRootAdmin o where o.userName=?1")
			.setParameter(1, userName).getSingleResult();
		return count>0;
	}
	
	public boolean validate(String userName, String password){
		long count = (Long)em.createQuery("select count(o) from SAASRootAdmin o where o.userName=?1 and o.password=?2 and o.visible=?3")
		.setParameter(1, userName).setParameter(2, password).setParameter(3, true).getSingleResult();
		return count>0;
	}

}
