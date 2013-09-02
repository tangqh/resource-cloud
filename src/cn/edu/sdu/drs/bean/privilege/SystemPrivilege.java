package cn.edu.sdu.drs.bean.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统权限
 * @author join
 *
 */

@Entity
@Table(name="SYSTEM_PRIVILEGE")
public class SystemPrivilege implements Serializable{

	private static final long serialVersionUID = -8161547170959037059L;
	
	/** fields **/
	
	/* 主键 */
	@EmbeddedId
	private SystemPrivilegePK id;
	
	/* 权限名称  */
	@Column(length=30,nullable=false)
	private String name;
	
	/** constructions **/
	
	public SystemPrivilege(){}
	
	public SystemPrivilege(SystemPrivilegePK id) {
		this.id = id;
	}
	
	public SystemPrivilege(SystemPrivilegePK id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public SystemPrivilege(String model, String privilegeValue, String name) {
		this.id = new SystemPrivilegePK(model, privilegeValue);
		this.name = name;
	}
	
	public SystemPrivilege(String model, String privilegeValue) {
		this.id = new SystemPrivilegePK(model, privilegeValue);
	}
	
	/** getters and setters **/
	
	public SystemPrivilegePK getId() {
		return id;
	}
	public void setId(SystemPrivilegePK id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		final SystemPrivilege other = (SystemPrivilege) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
