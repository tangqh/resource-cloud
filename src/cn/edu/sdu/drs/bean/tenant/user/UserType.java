package cn.edu.sdu.drs.bean.tenant.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author join
 *
 */

@Entity
@Table(name="USER_TYPE")
public class UserType implements Serializable {

	private static final long serialVersionUID = 3340363490930698098L;

	/** fields **/
	
	/* 主键 */
	@Id
	@Column(name="USER_TYPE_ID", length=36)
	private String id;
	
	/* 成员类型名字 */
	@Column(name="NAME", length=20, nullable=false)
	private String name;
	
	/* 该类型下的成员 */
	@OneToMany(cascade={CascadeType.REFRESH}, mappedBy="type")
	private Set<User> menbers = new HashSet<User>();
	
	/* 子类别 */
	@OneToMany(cascade={CascadeType.REFRESH}, mappedBy="parent")
	private Set<UserType> childTypes = new HashSet<UserType>();
	
	/* 父类别 */
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="PARENT")
	private UserType parent;
	
	/* 用户类别的url,用于前台显示 */
	@Column(name="URL", length=50)
	private String url; 
	
	/** constructions **/
	
	public UserType(){}
	
	public UserType(String name){
		this.name = name;
	}
	
	/** setter and getters **/
	
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
	
	public Set<User> getMenbers() {
		return menbers;
	}
	
	public void setMenbers(Set<User> menbers) {
		this.menbers = menbers;
	}
	
	public Set<UserType> getChildTypes() {
		return childTypes;
	}

	public void setChildTypes(Set<UserType> childTypes) {
		this.childTypes = childTypes;
	}

	public UserType getParent() {
		return parent;
	}

	public void setParent(UserType parent) {
		this.parent = parent;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		UserType other = (UserType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
