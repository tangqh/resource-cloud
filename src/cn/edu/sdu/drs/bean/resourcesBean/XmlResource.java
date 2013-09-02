package cn.edu.sdu.drs.bean.resourcesBean;

import java.io.Serializable;

/**
 * <p>
 * 类 名： xml
 * <p>
 * 作 用： 记录XML文件中的数据
 * 
 */
public class XmlResource implements Serializable {
	
	private static final long serialVersionUID = 7292597590996308151L;

	/**
	 * 数据记录的id信息
	 */
	public String id;

	/**
	 * 数据记录的title信息
	 */
	public String title;

	/**
	 * 数据记录的keywords信息
	 */
	public String keywords;

	/**
	 * 数据记录的kind信息
	 */
	public String kind;

	/**
	 * 数据记录的describe信息
	 */
	public String describe;

	/**
	 * 数据记录的date信息
	 */
	public String date;

	/**
	 * 数据记录的url信息
	 */
	public String url;

	/**
	 * 数据记录的author信息
	 */
	public String author;

	/**
	 * 数据记录的publisher信息
	 */
	public String publisher;

	/**
	 * 数据记录的Price信息， Price记录着数据的价值，后来的数据排序用
	 */
	public double Price;

	/**
	 * 作 用：构造方法
	 */
	public XmlResource() {
	}

	public XmlResource(String a, String b, String c, String d, String e, String f, String g, String h, double i) {
		this.id = a;
		this.title = b;
		this.kind = c;
		this.describe = d;
		this.date = e;
		this.url = f;
		this.author = g;
		this.publisher = h;
		this.Price = i;
	}

	/**
	 * 获取 数据的 价值
	 */
	public double getPrice() {
		return Price;
	}

	/**
	 * 设置 数据的 价值
	 */
	public void setPrice(double price) {
		this.Price = price;
	}

	/**
	 * 获取 数据的 作者
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置 数据的 作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取 数据的 发布日期
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 设置 数据的 发布日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取 数据的 描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * 设置 数据的 描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * 获取 数据的 ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 数据的 ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 数据的 关键字
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * 设置 数据的 关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * 获取 数据的 类型
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * 设置 数据的 类型
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * 获取 数据的 发布单位
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * 设置 数据的 发布单位
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * 获取 数据的 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 数据的 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取 数据的 地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 数据的 地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
