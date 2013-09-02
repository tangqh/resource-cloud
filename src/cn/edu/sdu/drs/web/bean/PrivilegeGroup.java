package cn.edu.sdu.drs.web.bean;

public class PrivilegeGroup {
	
	private String id;
	private String name;
	private String privilege = "";
	
	public PrivilegeGroup(){}

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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public void addPrivilege(String p){
		if(this.privilege != null && !"".equals(this.privilege)){
			this.privilege += ","+p;
		}else{
			this.privilege += p;
		}
	}
	
	

}
