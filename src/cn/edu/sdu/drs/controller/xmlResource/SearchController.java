package cn.edu.sdu.drs.controller.xmlResource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.bean.resourcesBean.XmlResource;
import cn.edu.sdu.drs.cluster.IPlist;
import cn.edu.sdu.drs.util.serarch.SortData;
import cn.edu.sdu.drs.util.serarch.WordSplit;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/searchController")
public class SearchController {
	
	/* 代表任意 */
	private static final int NONE = 0;
	/* 代表拼音 */
	private static final int PIN_YIN = 1;
	/* 代表汉字 */
	private static final int CHN = 2;
	/* 代表英语 */
	private static final int ENG = 3;
	
	/**
	 * 搜索的入口
	 * @param keyWord 关键字
	 * @return 返回展示的页面
	 */
	@RequestMapping("/doSearch")
	public String doSearch(HttpServletRequest request){
		// 接收参数
		String keyWord = request.getParameter("keyWord");
		String kind = request.getParameter("kind");
		String type = request.getParameter("type");
		ArrayList<Object> splitKW = null;
		//转码
		try {
			byte[] tem = keyWord.getBytes("iso8859-1");
			keyWord = new String(tem, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
 		// 开始检索
		switch(validate(keyWord)){
			case PIN_YIN:
				splitKW = WordSplit.splitPINYIN(keyWord);
				break;
			case CHN:
				splitKW = WordSplit.splitCHN(keyWord);
				break;
			case ENG:
				splitKW = WordSplit.splitENG(keyWord);
				break;
			case NONE:
				splitKW = WordSplit.splitNONE(keyWord);
				break;
		}
System.out.println("keyWord: " + keyWord + " kind: " + kind + "type:　" + type);
System.out.println("分词之后的结果------------------------------------------------");
for(int i=0; i<splitKW.size(); i++){
	System.out.println(splitKW.get(i));
}
		if(kind == null || "".equals(kind)){
			kind = "all";
		}
		if(type == null || "".equals(type)){
			type = "all";
		}
		//开始检索
		double startTime =System.currentTimeMillis();
		DistributedSearch ds = new DistributedSearch(splitKW,kind,type);
		double endTime =System.currentTimeMillis();
		//#1: 检索花的时间[单位：秒]
		double totleTime = (endTime - startTime)/1000;
		//#2：对finalResult排序
		SortData.maoPao(ds.finalResult);
		//#3: 检索的服务器的数量
		int hostNum = ds.hostNum>IPlist.iplist.size() ? IPlist.iplist.size() : ds.hostNum;
		//#4: 检索之后的最终的结果
		ArrayList<XmlResource> finalResult = ds.finalResult;
		//#5: 将排好序的结果放在web域里面
		request.setAttribute("finalResult", finalResult);
		request.setAttribute("hostNum", hostNum);
		request.setAttribute("totleTime", totleTime);
		return "result";
	}
	
	/**
	 * 是汉字？英语？拼音？【用的分词器不一样】
	 * 长度限制【20个字符，超过20个字符的丢掉】
	 * @param keyWord
	 * @return 返回关键字的语种
	 */
	private int validate(String keyWord){
		// #1:验证长度
		if(keyWord != null && keyWord.length()>20){
			keyWord = keyWord.substring(0, 20);
		}
		// #2:判断是汉字？英语？拼音？
		return NONE;
	}
	
}
