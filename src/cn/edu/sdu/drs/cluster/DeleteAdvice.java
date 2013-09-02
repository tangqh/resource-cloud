package cn.edu.sdu.drs.cluster;

import java.rmi.Naming;

/**
 * <p>
 * 类名：Del
 * <p>
 * 作 用：此类继承 Tread 用来通知远程主机删除特定的ip地址
 * @author join
 */

public class DeleteAdvice extends Thread {
	String LocalIP = HostRecord.getHostip(); // 后期改动
	String path = "";
	String Message = "";

	/**
	 * <br>
	 * 函数名：Del <br>
	 * 作 用：构造函数，设置参数用来创建删除指定ip地址的线程
	 */
	public DeleteAdvice(String ip, String str) {
		path = ip;
		Message = str; // 通知指定的ip
	}

	/**
	 * <br>
	 * 函数名：run <br>
	 * 作 用：执行删除指定主机地址的线程
	 */
	public void run() {
		try {
			System.out.println("通知绑定端口" + path);
			//写到日志
			RMIMethod diaoyong = (RMIMethod) Naming.lookup("rmi://" + path + "/Ser");
			if (diaoyong.delip(Message)) {
				System.out.println("向远程机器" + path + "删除" + Message + "成功");
				//写到日志
			}
		} catch (Exception e) {
			System.out.println("连接失败");
			//写到日志
		}
	}

}
