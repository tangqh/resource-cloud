package cn.edu.sdu.drs.cluster;

import java.io.UnsupportedEncodingException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import cn.edu.sdu.drs.service.resources.XMLService;
import cn.edu.sdu.drs.service.resources.XMLServiceImpl;

import java.util.ArrayList;

/**
 * <p>
 * 类名：RMIMethodImpl
 * <p>
 * 作 用： RMI接口方法的实现类，实现 接口类 RMIMethod 里面的所有方法 <br>
 * 供远程调用的方法
 * @author join
 */
public class RMIMethodImpl extends UnicastRemoteObject implements RMIMethod {

	private static final long serialVersionUID = -8240463405536947718L;
	
	String Host = HostRecord.getHostip();

	public RMIMethodImpl() throws RemoteException {
		super();
	}

	/**
	 * <br>
	 * 函数名：Loginin <br>
	 * 作 用： 远程调用的实现方法，实现了 服务器登录操作 <br>
	 * 参 数： String ip <br>
	 * 返回类型 boolean
	 * 
	 */
//此函数有bug
	public boolean Loginin(String ip) throws RemoteException {
		String inip = ip;
		if (!(IPlist.iplist.contains(ip))) {
			IPlist.iplist.add(ip);
		}
		System.out.println("当前在线服务器数量：" + IPlist.iplist.size());
		System.out.println();
		//写到日志
		for (int shu = 0; shu < IPlist.iplist.size(); shu++) {
			String dizhi = IPlist.iplist.get(shu).toString();
			if (!(dizhi.equals(inip) || dizhi.equals(Host))) {
				AddAdvice tong = new AddAdvice(IPlist.iplist.get(shu).toString(), ip);
				tong.start();
			}
		}
		WriteIPlist.go(); // 把IP列表写入文件中去
		return true;
	}

	/**
	 * <br>
	 * 函数名：Loginout <br>
	 * 作 用： 远程调用的实现方法，实现了 服务器退出操作 <br>
	 * 参 数： String ip <br>
	 * 返回类型 boolean
	 * 
	 */
	public boolean Loginout(String ip) throws RemoteException {

		try {
			for (int p = 0; p < IPlist.iplist.size(); p++) {
				if (IPlist.iplist.get(p).toString().equals(ip)) {
					IPlist.iplist.remove(p);
// 测试点
for (int y = 0; y < IPlist.iplist.size(); y++) {
	System.out.println("Del "
			+ IPlist.iplist.get(y).toString());
}
// 测试点
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * <br>
	 * 函数名：getIPlist <br>
	 * 作 用： 远程调用的实现方法，从服务器上获取在线主机列表 <br>
	 * 参 数： 无 <br>
	 * 返回类型 ArrayList
	 * 
	 */
	public ArrayList<Object> getIPlist() throws RemoteException {
		return IPlist.iplist;
	}
	
	/**
	 * <br>
	 * 函数名：addip <br>
	 * 作 用： 远程调用的实现方法，增加新增主机的ip地址 <br>
	 * 参 数： String ip <br>
	 * 返回类型 布尔型
	 * 
	 */
	public boolean addip(String ip) throws RemoteException {
		if (!(IPlist.iplist.contains(ip))) {
			IPlist.iplist.add(ip);
			System.out.println("本地" + ip + "client添加成功");
			WriteIPlist.go(); // 把IP列表写入文件中去
		}
		return true;
	}

	/**
	 * <br>
	 * 函数名：delip <br>
	 * 作 用： 远程调用的实现方法，从ip列表删除指定的ip <br>
	 * 参 数： String ip <br>
	 * 返回类型 布尔型
	 * 
	 */
	public boolean delip(String ip) throws RemoteException {
		try {
			for (int p = 0; p < IPlist.iplist.size(); p++) {
				if (IPlist.iplist.get(p).toString().equals(ip)) {
					IPlist.iplist.remove(p);
					for (int y = 0; y < IPlist.iplist.size(); y++) {
						System.out.println("Del: "
								+ IPlist.iplist.get(y).toString());
					}
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * 实现检索模块
	 */
	/**
	 * <br>
	 * 函数名：jiansuo <br>
	 * 作 用： 远程调用的实现方法，主要方法（检索方法）的实现，通过参数完成远程资源的检索 <br>
	 * 参 数： ArrayList str,String kind,String type <br>
	 * 返回类型 ArrayList
	 * 
	 */
	public ArrayList<Object> jiansuo(ArrayList<Object> str, String kind, String type)
			throws RemoteException {
		try {
			XMLService xm = new XMLServiceImpl();
			//return xm.list(str, kind, type); // 检索参数包括 检索内容、类型、检索方式
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * <br>
	 * 函数名：getPath <br>
	 * 作 用： 远程调用的实现方法，获取远程服务器的真实目录，为了防止其更改了目录名而找不到的BUG <br>
	 * 参 数： 没有 <br>
	 * 返回类型 String
	 * 
	 */
	public String getPath() throws RemoteException {

		// 返回服务器真实站点地址
		return RealPath.Path
				.substring(RealPath.Path.lastIndexOf("\\",
						RealPath.Path.length() - 2) + 1,
						RealPath.Path.length() - 1);

	}

}
