package cn.edu.sdu.drs.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.controller.BaseController;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/userTypeController")
public class UserTypeController extends BaseController{
	
	
	/**
	 * 查找会员类型
	 * @return
	 */
	@RequestMapping("/findUserType/{id}")
	public String findUserType(@PathVariable String id){
		return "";
	}
	
	/**
	 * 显示增加会员类型的界面
	 * @return 增加会员类型的界面
	 */
	@RequestMapping("/addUserTypeUI")
	public String addUserTypeUI(){
		return "";
	}
	
	/**
	 * 增加会员类型
	 * @return
	 */
	@RequestMapping("/addUserType")
	public String addUserType(){
		return "";
	}
	
	/**
	 * 显示saveOrUpdate会员类型的界面
	 * @return 增加会员类型的界面
	 */
	@RequestMapping("/saveOrUpdateUserTypeUI")
	public String saveOrUpdateUserTypeUI(){
		return "";
	}
	
	/**
	 * saveOrUpdate会员类型
	 * @param id 会员类型的ID
	 * @return
	 */
	@RequestMapping("/saveOrUpdateUserType")
	public String saveOrUpdateUserType(@PathVariable String id){
		return "";
	}
	
	/**
	 * 逻辑删除会员，修改UserType的字段
	 * @param id 会员类型的ID
	 * @return
	 */
	@RequestMapping("/logicDeleteUserType")
	public String logicDeleteUserType(@PathVariable String id){
		return "";
	}
	
	/**
	 * 物理删除会员[从数据库中删除]
	 * @param id 会员类型的ID
	 * @return
	 */
	@RequestMapping("/physicsDeleteUserType")
	public String physicsDeleteUserType(@PathVariable String id){
		return "";
	}
	
}
