package cn.edu.sdu.drs.cluster;

import java.rmi.registry.LocateRegistry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author join
 *
 */
public class RunCluster {
	
	public RunCluster() {
		try {
			init();
			System.out.println("本机地址： " + HostRecord.getHostip());
			System.out.println("注册地址： " + HostRecord.getRemoteip());
			LocateRegistry.createRegistry(1099); // 开启 rmi注册服务
			MainServer run = new MainServer();
			run.Server();
		} catch (Exception e) {
			System.out.println("系统错误");
			//写到日志
		}
	}
	
	/**
	 * <br>
	 * 函数名： init <br>
	 * 作 用： 获取XML文件中的信息，并存储在系统中 <br>
	 * 参数： 无
	 */
	public void init() {
		String tomcat = getPath("webapps");
		String filepath = tomcat + "\\" + "test.xml";
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read("file:\\" + filepath);
		} catch (DocumentException e) {
			System.out.println("错误：" + e);
			//写到日志
		}
		Element root = document.getRootElement();
		HostRecord.setHostip(root.attributeValue("host"));
		HostRecord.setRemoteip(root.attributeValue("remote"));
	}

	/**
	 * <br>
	 * 函数名： getPath <br>
	 * 作 用： 获取服务器根目录地址 <br>
	 * 参数： Webapps <br>
	 * 返回类型：String
	 */
	public String getPath(String Webapps) {
		String path = System.getProperty("catalina.home");
		int binindex = path.lastIndexOf("bin");
		if (binindex != -1) {
			path = path.substring(0, binindex - 1);
		}
		path = path + "\\" + Webapps;
		return path;
	}

}
