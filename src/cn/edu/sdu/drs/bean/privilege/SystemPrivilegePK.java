package cn.edu.sdu.drs.bean.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 系统权限的主键
 * @author JOIN
 *
 */

@Embeddable
public class SystemPrivilegePK implements Serializable{
	
	private static final long serialVersionUID = 4320319030992248798L;
	
	/** fields **/
	
	/* 模块的名字，例如：资源  */
	@Column(length=30,name="MODEL")
	private String model;
	
	/* 权限值，例如：删除权限 */
	@Column(length=20,name="PRIVILEGE_VALUE")
	private String privilegeValue;
	
	/** constructions **/
	
	public SystemPrivilegePK(){}
	
	public SystemPrivilegePK(String model, String privilegeValue) {
		this.model = model;
		this.privilegeValue = privilegeValue;
	}
	
	/** getters and setters **/
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getPrivilegeValue() {
		return privilegeValue;
	}
	public void setPrivilegeValue(String privilegeValue) {
		this.privilegeValue = privilegeValue;
	}
	
	/** override hashCode and equals **/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result
				+ ((privilegeValue == null) ? 0 : privilegeValue.hashCode());
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
		final SystemPrivilegePK other = (SystemPrivilegePK) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (privilegeValue == null) {
			if (other.privilegeValue != null)
				return false;
		} else if (!privilegeValue.equals(other.privilegeValue))
			return false;
		return true;
	}
	
}
