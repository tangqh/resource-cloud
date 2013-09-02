package cn.edu.sdu.drs.cluster;

import java.rmi.Naming;

/**
 * <p>
 * 类名：Advice
 * <p>
 * 作 用： 继承 Thread 类 通知其他主机有新主机加入 并告知新主机的ip
 * @author join
 */

public class AddAdvice extends Thread {
	String LocalIP = HostRecord.getHostip(); // 后期改动
	String path = "";
	String Message = "";

	public AddAdvice(String ip, String str) {
		path = ip;
		Message = str; // 通知指定的ip
	}

	/**
	 * <br>
	 * 函数名：run <br>
	 * 作 用： 线程的执行方法，通知其他主机有新主机加入 <br>
	 * 参 数： 没有 <br>
	 * 返回类型 void
	 * 
	 */
	public void run() {
		try {
			System.out.println("通知绑定端口" + path);
			//写到日志
			RMIMethod invoke = (RMIMethod) Naming.lookup("rmi://" + path + "/Ser");
			if (invoke.addip(Message)) {
				System.out.println("向远程机器" + path + "添加" + Message + "成功");
				//写到日志
			}
		} catch (Exception e) {
			System.out.println("连接失败");
			//写到日志
		}
	}

}
