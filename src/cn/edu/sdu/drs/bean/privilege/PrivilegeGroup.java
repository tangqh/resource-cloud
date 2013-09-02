package cn.edu.sdu.drs.bean.privilege;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;

/**
 * 权限组
 * @author join
 *
 */

@Entity
@Table(name="PRIVILEGE_GROUP")
public class PrivilegeGroup implements Serializable {

	private static final long serialVersionUID = -7075886049927166718L;
	
	/** fields **/

	/* 权限组的ID */
	@Id
	@Column(name="ID", length=36)
	private String id;

	/* 权限组的名字 */
	@Column(name="NAME", length = 30, nullable = false)
	private String name;

	/* 系统的权限 */
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "PRIVILEGE_GROUP_SYSTEM_PRIVILEGE", joinColumns = @JoinColumn(name = "PRIVILEGE_GROUP_ID"), inverseJoinColumns = {
			@JoinColumn(name = "MODEL", referencedColumnName = "MODEL"),
			@JoinColumn(name = "PRIVILEGE_VALUE", referencedColumnName = "PRIVILEGE_VALUE") })
	private Set<SystemPrivilege> privileges = new HashSet<SystemPrivilege>();
	
	/* saas平台一般管理员 */
	@ManyToMany(mappedBy="privilegeGroups", cascade=CascadeType.REFRESH)
	private Set<SAASAdmin> saasAdmin = new HashSet<SAASAdmin>();
	
	/* 租户内部一般管理员 */
	@ManyToMany(mappedBy="privilegeGroups", cascade=CascadeType.REFRESH)
	private Set<Admin> admin = new HashSet<Admin>();
	
	/* saas平台超级管理员 */
	@ManyToMany(mappedBy="privilegeGroups", cascade=CascadeType.REFRESH)
	private Set<SAASRootAdmin> saasRootAdmin = new HashSet<SAASRootAdmin>();
	
	/* 租户内部一般管理员 */
	@ManyToMany(mappedBy="privilegeGroups", cascade=CascadeType.REFRESH)
	private Set<RootAdmin> rootAdmin = new HashSet<RootAdmin>();
	
	/** constructions **/

	public PrivilegeGroup() {
	}

	public PrivilegeGroup(String name) {
		this.name = name;
	}
	
	/** getters and setters **/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SystemPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<SystemPrivilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<SAASAdmin> getSaasAdmin() {
		return saasAdmin;
	}

	public void setSaasAdmin(Set<SAASAdmin> saasAdmin) {
		this.saasAdmin = saasAdmin;
	}
	
	public Set<Admin> getAdmin() {
		return admin;
	}

	public void setAdmin(Set<Admin> admin) {
		this.admin = admin;
	}
	
	public Set<SAASRootAdmin> getSaasRootAdmin() {
		return saasRootAdmin;
	}

	public void setSaasRootAdmin(Set<SAASRootAdmin> saasRootAdmin) {
		this.saasRootAdmin = saasRootAdmin;
	}

	public Set<RootAdmin> getRootAdmin() {
		return rootAdmin;
	}

	public void setRootAdmin(Set<RootAdmin> rootAdmin) {
		this.rootAdmin = rootAdmin;
	}

	public void addPrivilege(SystemPrivilege privilege) {
		if (!this.privileges.contains(privilege))
			this.privileges.add(privilege);
	}
	
	/** override hashCode and equals **/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PrivilegeGroup other = (PrivilegeGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
