package cn.edu.sdu.drs.bean.tenant;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.bean.tenant.user.User;

/**
 * 
 * @author join
 *
 */

@Entity
@Table(name="TENANT")
public class Tenant implements Serializable{

	private static final long serialVersionUID = -594239431759330002L;
	
	/* 租户编号，主键 */
	@Id
	@Column(name="ID", length=36)
	private String id;
	
	/* 租户的名称 */
	@Column(nullable=false, name="NAME", length=36)
	private String name;
	
	/* 租户的描述 */
	@Column(name="NOTE", length=100)
	private String note;
	
	/* 租户logo图片的路劲 */
	@Column(name="PHOTO_PATH", length=100)
	private String photoPath;
	
	/* 租户的创建时间 */
	@Column(name="CREATE_TIME")
	private Date createTime = new Date();
	
	/* 可见性 */
	@Column(name="VISIBLE", length=5)
	private Boolean visible = true;
	
	/* 租户内的用户 */
	@ManyToMany(mappedBy="tenants", cascade=CascadeType.REFRESH)
	private Set<User> users = new HashSet<User>();
	
	/* 租户捏的管理员 */
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy="tenant")
	private Set<Admin> admins = new HashSet<Admin>();
	
	/* 租户内的超级管理员 */
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy="tenant")
	private Set<RootAdmin> rootAdmins = new HashSet<RootAdmin>();
	
	/** constructions **/
	
	public Tenant(){}
	
	public Tenant(String name){
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}

	public Set<RootAdmin> getRootAdmins() {
		return rootAdmins;
	}

	public void setRootAdmins(Set<RootAdmin> rootAdmins) {
		this.rootAdmins = rootAdmins;
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
		Tenant other = (Tenant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
