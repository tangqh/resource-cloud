package cn.edu.sdu.drs.cluster;

import java.rmi.Naming;


public class MainServer {
	
	/**
	 * <br>
	 * 函数名：Server <br>
	 * 作 用：获取 xml 文件里面的 信息 并执行 Loginin 操作 向主服务器注册
	 */
	public void Server() {
		String Host = HostRecord.getHostip();
		String Remo = HostRecord.getRemoteip();
		RMIMethod invoke = null;
		try {
			RMIMethodImpl rmi = new RMIMethodImpl();
			Naming.rebind("Ser", rmi);
			invoke = (RMIMethod) Naming.lookup("rmi://" + Remo + "/Ser");
			if (invoke.Loginin(Host)) {
				System.out.println("主机加入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("主服务器连接失败，开始选举服务器。。。。。");
				boolean ok = false;
				ReadIPlist.go();
				for (int sele = 0; sele < Selectlist.Select.size(); sele++) {
					if ((!Selectlist.Select.get(sele).equals(HostRecord.Hostip))
							&& (ok == false)) {
						invoke = (RMIMethodImpl) Naming.lookup("rmi://"
								+ Selectlist.Select.get(sele) + "/Ser");
						if (invoke.Loginin(Host)) {
							System.out.println("主机加入成功");
							ok = true;
							break;
						}
					}
				}

				if (ok == false) {
					invoke = (RMIMethodImpl) Naming.lookup("rmi://" + Host + "/Ser");
					if (invoke.Loginin(Host)) {
						System.out.println("主机加入成功");
						ok = true;
					}
				}
			} catch (Exception ee) {
				System.out.println("错误" + ee);
			}
		}
		try {
			IPlist.iplist = invoke.getIPlist();
			System.out.println("当前的IP地址列表数量为：" + IPlist.iplist.size());
			for (int i = 0; i < IPlist.iplist.size(); i++) {
				System.out.println(IPlist.iplist.get(i).toString());
			}
			WriteIPlist.go();
			System.out.println("IP地址列表打印完毕");
		} catch (Exception e) {
			System.out.println("错误: " + e);
		}
	}

}
