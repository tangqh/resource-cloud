package cn.edu.sdu.drs.bean.SAASAdmin;

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

import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;

/**
 * 
 * @author join
 *
 */
@Entity
@Table(name="SAAS_ROOT_ADMIN")
public class SAASRootAdmin implements Serializable{

	private static final long serialVersionUID = 2737418123464160758L;
	
	/** fields **/

	/* 登录账号，系统初始化时创建, 默认是root */
	@Id
	@Column(name="USERNAME", length=36)
	private String userName = "saasroot";
	
	/* 登录账号，系统初始化时创建, 默认是root */
	@Column(name="PASSWORD", length=36)
	private String password = "saasroot";
	/* 系统初始化时创建 */
	@Column(name="REAL_NAME", length=36)
	private String realName = "yonggangYuan";
	
	// 员工在职状态,true为在职,false为离职
	@Column(name="VISIBLE", length = 5)
	private Boolean visible = true;
	
	// 所在权限组
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "SAAS_ROOT_ADMIN_PRIVILEGE_GROUP", joinColumns = @JoinColumn(name = "SAAS_ROOT_ADMIN_ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_GROUP_ID"))
	private Set<PrivilegeGroup> privilegeGroups = new HashSet<PrivilegeGroup>();
	
	/** constructions **/
	
	public SAASRootAdmin(){}
	
	public SAASRootAdmin(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public SAASRootAdmin(String realName){
		this.realName = realName;
	}
	
	public SAASRootAdmin(String userName, String password, String realName){
		this.userName = userName;
		this.realName = realName;
		this.password = password;
	}
	
	/** getters and setters **/
	
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
		SAASRootAdmin other = (SAASRootAdmin) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
