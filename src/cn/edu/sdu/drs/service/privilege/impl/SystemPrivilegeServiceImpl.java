package cn.edu.sdu.drs.service.privilege.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.dao.BaseDaoSupport;
import cn.edu.sdu.drs.service.privilege.SystemPrivilegeService;
/**
 * 
 * @author join
 *
 */
@Service("systemPrivilegeService")
public class SystemPrivilegeServiceImpl extends BaseDaoSupport implements SystemPrivilegeService {
	
	public void batchSave(List<SystemPrivilege> privileges){
		for(SystemPrivilege p : privileges){
			save(p);
		}
	}
}
