package cn.edu.sdu.drs.web.bean;

public class RootAdmin {
	
	private String id;
	private String realName;
	private String privilege = "";
	
	public RootAdmin(){}

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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public void addPrivilegeGroup(String pg){
		if(this.privilege != null && !"".equals(this.privilege)){
			this.privilege += ","+pg;
		}else{
			this.privilege += pg;
		}
	}

}
