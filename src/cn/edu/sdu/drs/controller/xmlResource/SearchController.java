package cn.edu.sdu.drs.controller.xmlResource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.util.serarch.WordSplit;
import cn.edu.sdu.drs.web.bean.Resource;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/searchController")
public class SearchController extends BaseController{
	
	/**
	 * 搜索的入口
	 * @param keyWord 关键字
	 * @return 返回展示的页面
	 */
	@RequestMapping("/doSearch")
	public String doSearch(HttpServletRequest request){
		// 接收参数
		String keyWord = request.getParameter("keyword");
		String kind = request.getParameter("kind");
		String type = request.getParameter("type");
		ArrayList<String> splitKW = null;
 		// 开始检索
		if(keyWord != null && keyWord.length()>20){
            keyWord = keyWord.substring(0, 20);
        }
		splitKW = WordSplit.splitNONE(keyWord);
		if(kind == null || "".equals(kind)){
			kind = "all";
		}
		if(type == null || "".equals(type)){
			type = "all";
		}
		//开始检索
		double startTime =System.currentTimeMillis();
		//List<Resource> qus = xmlResourceService.search(splitKW);
		List<Resource> qus = null;
        try {
            qus = xmlResourceService.list(splitKW, kind, type);
        }catch (UnsupportedEncodingException e) {
            System.out.println("There is a exception!");
        }
		double endTime =System.currentTimeMillis();
		//#1: 检索花的时间[单位：秒]
		double totleTime = (endTime - startTime)/1000;
		request.setAttribute("records", qus);
		request.setAttribute("totleTime", totleTime);
		return "../../index/result";
	}
	
}
