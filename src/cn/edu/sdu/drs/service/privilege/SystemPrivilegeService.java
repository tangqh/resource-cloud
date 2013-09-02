package cn.edu.sdu.drs.service.privilege;

import java.util.List;

import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.dao.BaseDao;

/**
 * 
 * @author join
 *
 */

public interface SystemPrivilegeService extends BaseDao{
	/**
	 * 批量保存系统权限
	 * @param privileges
	 */
	public void batchSave(List<SystemPrivilege> privileges);
}
