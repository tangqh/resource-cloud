package cn.edu.sdu.drs.web.bean;

/**
 * 
 * @author join
 *
 */
public class Json {

	/** fields **/
	
	/* 是否成功 */
	private Boolean success = true;
	/* 后台返回的信息 */
	private String msg;
	/* 备用对象 */
	private Object obj;
	
	private String tenantName;
	
	/** constructions **/
	
	public Json(){}
	
	public Json(String msg){
		this.msg = msg;
	}
	
	/** methods **/
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public String getTenantName() {
      return tenantName;
    }

    public void setTenantName(String tenantName) {
      this.tenantName = tenantName;
    }

}
