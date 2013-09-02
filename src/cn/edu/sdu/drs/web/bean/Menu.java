package cn.edu.sdu.drs.web.bean;

import java.util.Map;

/**
 * 
 * @author join
 *
 */
public class Menu{

	/* 菜单ID */
	private String id;
	/* 菜单PID */
	private String pid;
	/* 菜单名称 */
	private String text;
	/* 是否展开(open,closed) */
	private String state = "open";
	/* 前面的小图标样式 */
	private String iconCls;
	/* 子菜单 */
	private Menu[] children;
	/* 根据easyui的API，用来存放url */
	private Map<String, Object> attributes;
	
	public Menu(){}
	
	public Menu(String text){
		this.text = text;
	}
	
	public Menu(String text, String id, String pid, String iconCls){
		this.text = text;
		this.id = id;
		this.pid = pid;
		this.iconCls = iconCls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Menu[] getChildren() {
		return children;
	}

	public void setChildren(Menu[] children) {
		this.children = children;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
