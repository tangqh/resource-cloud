package cn.edu.sdu.drs.util;

import java.util.UUID;

/**
 * 主键ID生成器，采用不同的方式生成ID
 * @author join
 *
 */
public class IdGenerator {
	
	/**
	 * 采用UUID生成主键
	 * @return
	 */
	public String getIDByUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 采用日期有序的生成主键；
	 * ****************************************************************
	 * 在高并发的环境下，该方法会导致生成的ID不唯一的情况，从来导致本次创建实体不成功的现象
	 * ****************************************************************
	 * 生成策略：生成的ID说明了该实体是某年某月某日的第几个被创建的，例如：
	 * #1:201306130001  ——>		是2013年06月13日的第一个被创建的实体
	 * #2:201306130100  ——>		是2013年06月13日的第一百个被创建的实体
	 * @return
	 */
	public String getIDByDateOrder(){
		return "";
	}
	
	/**
	 * 采用日期生成主键；将当期的时间戳组成一定形式的ID，【最大16个字符】例如：
	 * #1:20130613HHMMSSMM
	 * @return
	 */
	public String getIDByDate(){
		return "";
	}

}
