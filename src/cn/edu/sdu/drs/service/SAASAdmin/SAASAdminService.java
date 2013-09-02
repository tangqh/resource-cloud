package cn.edu.sdu.drs.service.SAASAdmin;

import cn.edu.sdu.drs.dao.BaseDao;

/**
 * 
 * @author join
 *
 */
public interface SAASAdminService extends BaseDao{
	
	/**
	 * 判断账号是否存在
	 * @param id 账号
	 * @return
	 */
	public boolean exist(String id);
	/**
	 * 判断用户名及密码是否正确
	 * @param id 账号
	 * @param password 密码
	 * @return
	 */
	public boolean validate(String id, String password);

}
