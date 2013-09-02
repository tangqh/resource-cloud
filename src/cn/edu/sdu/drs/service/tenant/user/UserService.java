package cn.edu.sdu.drs.service.tenant.user;

import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.dao.BaseDao;



/**
 * 
 * @author join
 *
 */

public interface UserService extends BaseDao {
	
	/**
	 * 根据会员的真是姓名查找会员
	 * @param realName 会员真是姓名
	 * @return
	 */
	public User findUserByRealName(String realName);
	
	/**
	 * 根据会员的登录名字查找会员
	 * @param loginName 会员的登录名字
	 * @return
	 */
	public User findUserByLoginName(String loginName);
	
	/**
	 * 根据会员的邮箱查找会员
	 * @param email 会员的邮箱
	 * @return
	 */
	public User findUserByEmail(String email);

}
