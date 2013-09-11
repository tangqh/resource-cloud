package cn.edu.sdu.drs.controller.admin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.util.MD5;
import cn.edu.sdu.drs.util.WebUtil;
import cn.edu.sdu.drs.web.bean.Json;

/**
 * 
 * @author join
 *
 */

@Controller
@RequestMapping("/admin")
public class AdminIndex extends BaseController{

    /**
     * 登录
     * @param request
     * @param response
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        if(WebUtil.getAdmin(request) != null){
            request.getSession().removeAttribute("admin");
        }
        if(WebUtil.getRootAdmin(request) != null){
            request.getSession().removeAttribute("rootAdmin");
        }
        String id=request.getParameter("id");
        String pwd=request.getParameter("password");
        Json json = new Json();
        if(id==null || id.trim().length()==0 || pwd==null || pwd.trim().length()==0){
            json.setObj(null);
            json.setMsg("登录名和密码不能为空！");
            json.setSuccess(false);
            writeJson(request, response, json);
            return;
        }
        Admin admin = adminService.find(Admin.class, id);
        if(admin != null){
            if(admin.getPassword().equals(MD5.MD5Encode(pwd))){
                request.getSession().setAttribute("admin", admin);
                json.setMsg(admin.getRealname() + ", 欢迎您的登录！");
                writeJson(request, response, json);
                return;
            }else{
                json.setObj(null);
                json.setMsg("密码错误！");
                json.setSuccess(false);
                writeJson(request, response, json);
                return;
            }
        }else{
            RootAdmin ra = rootAdminService.find(RootAdmin.class, id);
            if(ra != null){
                if(ra.getPassword().equals(MD5.MD5Encode(pwd))){
                    request.getSession().setAttribute("rootAdmin", ra);
                    json.setMsg(ra.getRealName() + ", 欢迎您的登录！");
                    writeJson(request, response, json);
                    return;
                }else{
                    json.setObj(null);
                    json.setMsg("密码错误！");
                    json.setSuccess(false);
                    writeJson(request, response, json);
                    return;
                }
            }
        }
        json.setObj(null);
        json.setMsg("登录失败！");
        json.setSuccess(false);
        writeJson(request, response, json);
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Json json = new Json("已登出！");
        if(WebUtil.getAdmin(request) != null){
            request.getSession().removeAttribute("admin");
        }
        if(WebUtil.getRootAdmin(request) != null){
            request.getSession().removeAttribute("rootAdmin");
        }
        writeJson(request, response, json);
    }

    @RequestMapping("/index")
    public String index(){
        return "admin/adminIndex";
    }

    @RequestMapping("/layout/north")
    public String north(){
        return "admin/layout/north";
    }

    @RequestMapping("/layout/south")
    public String south(){
        return "admin/layout/south";
    }

    @RequestMapping("/layout/west")
    public String west(){
        return "admin/layout/west";
    }

    @RequestMapping("/layout/center")
    public String center(){
        return "admin/layout/center";
    }

    @RequestMapping("/layout/portal")
    public String portal(){
        return "admin/layout/portal";
    }
}
