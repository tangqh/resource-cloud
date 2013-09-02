package cn.edu.sdu.drs.cluster;

/**
 * 
 * @author join
 *
 */

public class HostRecord {
	
	/**
	 * <br>
	 * 变量名：Hostip <br>
	 * 作 用：记录本机ip地址 <br>
	 * 类型： String
	 */
	public static String Hostip; // 记录本机 IP 地址

	/**
	 * <br>
	 * 变量名：Remoteip <br>
	 * 作 用：记录服务器ip地址 <br>
	 * 类型： String
	 */
	public static String Remoteip; // 记录需要连接的服务器地址

	public HostRecord() {}

	/**
	 * <br>
	 * 函数名：getHostip <br>
	 * 作 用： 获取本机ip地址 <br>
	 * 参 数： 无 <br>
	 * 返回类型： String
	 */
	public static String getHostip() {
		return Hostip;
	}

	/**
	 * <br>
	 * 函数名：setHostip <br>
	 * 作 用： 设置本机ip地址 <br>
	 * 参 数： hostip <br>
	 * 返回类型： 空
	 */
	public static void setHostip(String hostip) {
		Hostip = hostip;
	}

	/**
	 * <br>
	 * 函数名：getRemoteip <br>
	 * 作 用： 获取服务器ip地址 <br>
	 * 参 数： 无 <br>
	 * 返回类型： String
	 */
	public static String getRemoteip() {
		return Remoteip;
	}

	/**
	 * <br>
	 * 函数名：setRemoteip <br>
	 * 作 用： 获取本机ip地址 <br>
	 * 参 数： remoteip <br>
	 * 返回类型： 空
	 */
	public static void setRemoteip(String remoteip) {
		Remoteip = remoteip;
	}

}
