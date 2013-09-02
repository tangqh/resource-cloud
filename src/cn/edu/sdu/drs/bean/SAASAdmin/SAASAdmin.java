package cn.edu.sdu.drs.bean.SAASAdmin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import cn.edu.sdu.drs.bean.Gender;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;

/**
 * 
 * @author join
 *
 */
@Entity
@Table(name="SAAS_ADMIN")
public class SAASAdmin implements Serializable{

	private static final long serialVersionUID = -7019751362683329394L;

	// 用户名,由超管分配的号，不可重复
	@Id
	@Column(name="ID", length = 36)
	private String id;

	@Column(name="PASSWORD", length = 32, nullable = false)
	private String password;// 密码

	// 名字
	@Column(name="REAL_NAME", length = 50, nullable = false)
	private String realname;

	// 联系电话
	@Column(name="PHONE", length = 18)
	private String phone;

	// 电子邮件
	@Column(name="EMAIL", length = 50)
	private String email;

	// 性别
	@Enumerated(EnumType.STRING)
	@Column(name="GENDER", nullable = false, length = 5)
	private Gender gender = Gender.MAN;

	// 入职时间
	@Temporal(TemporalType.DATE)
	private Date createdate = new Date();

	// 员工在职状态,true为在职,false为离职
	@Column(name="VISIBLE", length = 5)
	private Boolean visible = true;

	// 所在权限组
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "SAAS_ADMIN_PRIVILEGE_GROUP", joinColumns = @JoinColumn(name = "SAAS_ADMIN_ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_GROUP_ID"))
	private Set<PrivilegeGroup> privilegeGroups = new HashSet<PrivilegeGroup>();

	public SAASAdmin(){}
	
	public SAASAdmin(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		SAASAdmin other = (SAASAdmin) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
