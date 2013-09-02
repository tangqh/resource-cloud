package cn.edu.sdu.drs.cluster;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class WriteIPlist {
	public static void go() {
		FileOutputStream out;
		PrintStream p;
		try {
System.out.println("-----------------------" + RealPath.Path + "\\webapps\\distributeResourseSearch\\data\\iplist.txt");
			out = new FileOutputStream(RealPath.Path + "\\webapps\\distributeResourseSearch\\data\\iplist.txt");
			p = new PrintStream(out);
			for (int hh = 0; hh < IPlist.iplist.size(); hh++) {
				p.println(IPlist.iplist.get(hh));
			}
			p.close();
		} catch (Exception e) {
e.printStackTrace();
			System.err.println("ip列表写入失败");
		}
	}
}
