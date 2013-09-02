package cn.edu.sdu.drs.service.tenant.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.dao.bean.QueryResult;
import cn.edu.sdu.drs.util.MD5;

/**
 * 
 * @author join
 *
 */

@Service("userService")
public class UserServiceImpl extends BaseDaoSupport implements UserService {
	
	/**
	 * 重载的保存，加上MD5加密后的密码
	 */
	@Override
	public Object save(Object entity) {
		User u = ((User)entity);
	    u.setId(UUID.randomUUID().toString());
		u.setPassword(MD5.MD5Encode(u.getPassword()));
		return super.save(u);
	}
	
	/**
	 * 根据会员的真是姓名查找会员
	 * @param realName 会员真是姓名
	 * @return
	 */
	public User findUserByRealName(String realName){
		return getScrollData(User.class, "o.memberInfo.realName=?", new Object[]{realName}).getResultList().get(0);
	}
	
	/**
	 * 根据会员的登录名字查找会员
	 * @param loginName 会员的登录名字
	 * @return
	 */
	public User findUserByLoginName(String loginName){
		QueryResult<User> users = getScrollData(User.class, "o.loginName=?", new Object[]{loginName});
		if(users == null){
			return null;
		}
		if(users.getResultList() == null || users.getTotleResult() <= 0){
			return null;
		}
		return getScrollData(User.class, "o.loginName=?", new Object[]{loginName}).getResultList().get(0);
	}
	
	/**
	 * 根据会员的邮箱查找会员
	 * @param email 会员的邮箱
	 * @return
	 */
	public User findUserByEmail(String email){
		return getScrollData(User.class, "o.email=?", new Object[]{email}).getResultList().get(0);
	}

}
