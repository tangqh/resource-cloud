package cn.edu.sdu.drs.service.tenant.admin;

import cn.edu.sdu.drs.dao.BaseDao;


/**
 * 
 * @author join
 *
 */

public interface RootAdminService extends BaseDao {
	
	/**
	 * 判断账号是否存在
	 * @param userName 账号
	 * @return
	 */
	public boolean exist(String userName);
	/**
	 * 判断用户名及密码是否正确
	 * @param userName 账号
	 * @param password 密码
	 * @return
	 */
	public boolean validate(String userName, String password);

}
