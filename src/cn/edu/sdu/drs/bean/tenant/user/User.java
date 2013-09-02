package cn.edu.sdu.drs.bean.tenant.user;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.edu.sdu.drs.bean.Gender;
import cn.edu.sdu.drs.bean.tenant.Tenant;

/**
 * 
 * @author join
 *
 */

@Entity
@Table(name="USER")
public class User implements Serializable{
	
	private static final long serialVersionUID = 4232591434483505541L;

	/** fields **/
	
	/* 主键 */
	@Id
	@Column(name="USER_ID", length=36)
	private String id;
	
	/* 登录名 */
	@Column(name="LOGIN_NAME", length=20)
	private String loginName;
	
	/* 登录邮箱 */
	@Column(name="LOGIN_EMAIL", length=36)
	private String email;
	
	/* 密码 */
	@Column(name="PASSWORD", nullable=false, length=36)
	private String password;
	
	/* 会员的真实姓名 */
	@Column(name="REAL_NAME", nullable=false, length=36)
	private String realName;
	
	/* 用户类型 */
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="TYPE")
	private UserType type;
	
	// 性别
	@Enumerated(EnumType.STRING)
	@Column(name="GENDER", nullable = false, length = 5)
	private Gender gender;
	
	//所在的租户
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "TENANT_USER", joinColumns = @JoinColumn(name = "TENANT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Set<Tenant> tenants = new HashSet<Tenant>();
	
	/** constructions **/
	
	public User(){}
	
	public User(String loginName){
		this.loginName = loginName;
	}
	
	/** setter and getters **/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Set<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(Set<Tenant> tenants) {
		this.tenants = tenants;
	}
	
	public void addTenant(Tenant t){
		if(this.tenants != null && !this.tenants.contains(t)){
			this.tenants.add(t);
		}
	}
	
	public void removeTenant(Tenant t){
		if(this.tenants != null && this.tenants.contains(t)){
			this.tenants.remove(t);
		}
	}

	/** override hashCode and equals  **/

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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
