package cn.edu.sdu.drs.web.bean;

public class Admin {
	
	private String id;
	private String realName;
	private String email;
	private String gender;
	private String phone;
	private String privilege = "";
	
	public Admin(){}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public void appendPrivilegeGroup(String pg){
		if(this.privilege != null && !"".equals(this.privilege)){
			this.privilege += ","+pg;
		}else{
			this.privilege += pg;
		}
	}
}
