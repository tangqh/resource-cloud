package cn.edu.sdu.drs.bean.tenant.admin;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.tenant.Tenant;

/**
 * 
 * @author join
 *
 */
@Entity
@Table(name="ROOT_ADMIN")
public class RootAdmin implements Serializable{

	private static final long serialVersionUID = 2737418123464160758L;
	
	/** fields **/

	/* 登录账号，系统初始化时创建, 默认是root */
	@Id
	@Column(name="USERNAME", length=36)
	private String userName = "root";
	
	/* 登录账号，系统初始化时创建, 默认是root */
	@Column(name="PASSWORD", length=36)
	private String password = "root";
	/* 系统初始化时创建 */
	@Column(name="REAL_NAME", length=36)
	private String realName = "root";
	
	// 员工在职状态,true为在职,false为离职
	@Column(name="VISIBLE", length = 5)
	private Boolean visible = true;
	
	// 所在权限组
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "ROOT_ADMIN_PRIVILEGE_GROUP", joinColumns = @JoinColumn(name = "ROOT_ADMIN_ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_GROUP_ID"))
	private Set<PrivilegeGroup> privilegeGroups = new HashSet<PrivilegeGroup>();
	
	//所在的租户
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="TENANT")
	private Tenant tenant;
	
	/** constructions **/
	
	public RootAdmin(){}
	
	public RootAdmin(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public RootAdmin(String realName){
		this.realName = realName;
	}
	
	public RootAdmin(String userName, String password, String realName){
		this.userName = userName;
		this.realName = realName;
		this.password = password;
	}
	
	/** setters and getters **/
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public Set<PrivilegeGroup> getPrivilegeGroups() {
		return privilegeGroups;
	}

	public void setPrivilegeGroups(Set<PrivilegeGroup> privilegeGroups) {
		this.privilegeGroups = privilegeGroups;
	}
	
	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public void addPrivilegeGroups(PrivilegeGroup privilegeGroup){
		if(this.privilegeGroups != null && !this.privilegeGroups.contains(privilegeGroup)){
			this.privilegeGroups.add(privilegeGroup);
		}
	}
	
	public void removePrivilegeGroups(PrivilegeGroup privilegeGroup){
		if(this.privilegeGroups != null && this.privilegeGroups.contains(privilegeGroup)){
			this.privilegeGroups.remove(privilegeGroup);
		}
	}

	/** override hashCode and equals **/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		RootAdmin other = (RootAdmin) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
