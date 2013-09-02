package cn.edu.sdu.drs.controller.init;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.sdu.drs.cluster.RunCluster;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/startInit")
public class StartInit {
	
	/** 
	* <br>函数名：init
	* <br>作  用：servlet的初始化函数
	* <br>参  数： 无
	* <br>返回类型： 空 
	*/
	@RequestMapping("/startCluster")
	public void startCluster() throws ServletException {
		new RunCluster();
		System.out.println("初始化完成");
		//写到日志里面去
	}
	
}
