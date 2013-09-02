package cn.edu.sdu.drs.controller.xmlResource;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.sdu.drs.bean.resourcesBean.XmlResource;
import cn.edu.sdu.drs.cluster.DeleteAdvice;
import cn.edu.sdu.drs.cluster.IPlist;
import cn.edu.sdu.drs.cluster.RMIMethod;

/**
 * <p>
 * 类名：SearchMess
 * <p>
 * 作 用： 为每个等待检索的服务器创建检索线程并传入设置的参数，执行检索 统计检索的结果
 * 
 */
@Controller
@RequestMapping("/resource")
public class DistributedSearch {
	
	public DistributedSearch(){}

	/**
	 * <br>
	 * 字段名：finalResult <br>
	 * 作 用：存放检索的结果 <br>
	 * 数据类型：ArrayList
	 */
	public ArrayList<XmlResource> finalResult = new ArrayList<XmlResource>(); // 存放检索结果

	/**
	 * <br>
	 * 字段名：hostNum <br>
	 * 作 用：记录已经检索过的服务器数量 <br>
	 * 数据类型： 整型
	 */
	public int hostNum = 0;
	
	/**
	 * <br>
	 * 类名：DistributedSearch <br>
	 * 作 用： 构造函数 设置检索线程的参数，并开始检索
	 * 
	 */
	public DistributedSearch(ArrayList<Object> ppw, String kind, String type) {
		ArrayList<Object> str = new ArrayList<Object>();
		str = ppw;
		try {
System.out.println("主机数目： " + IPlist.iplist.size());
			// 初始化countDown，控制子线程
			CountDownLatch threadSignal = new CountDownLatch(IPlist.iplist.size());
			for (int i = 0; i < IPlist.iplist.size(); i++) {
System.out.println("执行搜索" + IPlist.iplist.get(i).toString());//写到日志
				Mess m = new Mess(str, kind, type, IPlist.iplist.get(i).toString(), threadSignal);
				m.start();
			}
			//等待所有线程执行完毕
			threadSignal.await();
		} catch (Exception e) {
			System.out.println("检索失败");
		}
	}

	/**
	 * <p>
	 * 类名：Mess
	 * <p>
	 * 作 用： 继承 Tread 为资源的检索分配一个独立的线程 <br>
	 * 此类自身以线程的形式为每一个进行检索的线程设置参数
	 * 
	 */
	class Mess extends Thread {
		ArrayList<Object> txt = null;
		String dizhi = "";
		String kinds = "";
		String types = "";
		
		private CountDownLatch threadsSignal;
		
		/**
		 * <br>
		 * 函数名：Mess <br>
		 * 作 用： 构造函数为执行检索的线程设置参数 <br>
		 * 参 数： ArrayList t,String kind,String type,String path
		 */
		public Mess(ArrayList<Object> t, String kind, String type, String path, CountDownLatch threadsSignal) {
			this.txt = t;
			this.kinds = kind;
			this.types = type;
			this.dizhi = path;
			this.threadsSignal = threadsSignal;
		}

		/**
		 * <br>
		 * 函数名：run <br>
		 * 作 用： 执行检索 统计检索的结果
		 * 
		 */
		public void run() {
			try {
				RMIMethod fa = (RMIMethod) Naming.lookup("rmi://" + dizhi + "/Ser");
				ArrayList<Object> shuju = fa.jiansuo(txt, kinds, types);
				synchronized (finalResult) {
					for (int j = 0; j < shuju.size(); j++) {
						finalResult.add((XmlResource) shuju.get(j));
					}
					hostNum ++;
				}
			} catch (Exception e) {
				System.out.println("检索" + dizhi + "失败,删除相应的地址");
				for (int y = 0; y < IPlist.iplist.size(); y++) {
					if (!(IPlist.iplist.get(y).toString().equals(dizhi))) {
						DeleteAdvice del = new DeleteAdvice(IPlist.iplist
								.get(y).toString(), dizhi);
						del.start();
					}
				}
			}
			//线程计数器减一
			threadsSignal.countDown();
		}
	}
}
