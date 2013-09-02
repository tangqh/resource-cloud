package cn.edu.sdu.drs.service.resources;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import cn.edu.sdu.drs.web.bean.Resource;

/**
 * 对XML的增删改查
 * @author join
 *
 */

public interface XMLService{
	
	public ArrayList<Object> list(ArrayList<Object> Txt, String searchKind, String type) throws UnsupportedEncodingException;
	
	public List<Resource> list(int pageSize, int pageNumber);
	
	//删除一条记录
	public void remove(String[] ids) throws Exception ;

	//更新一条记录
	public boolean update(String id, String desc, String share) ;
	
	public Resource findById(String id);
	
	public List<Resource> findByTitle(String title);
	
}
