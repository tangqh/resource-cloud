package cn.edu.sdu.drs.controller.privilege;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASAdmin;
import cn.edu.sdu.drs.bean.SAASAdmin.SAASRootAdmin;
import cn.edu.sdu.drs.bean.privilege.PrivilegeGroup;
import cn.edu.sdu.drs.bean.privilege.SystemPrivilege;
import cn.edu.sdu.drs.bean.privilege.SystemPrivilegePK;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.admin.RootAdmin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.util.WebUtil;
import cn.edu.sdu.drs.web.bean.Json;

/**
 * 
 * @author join
 *
 */

@Aspect
@Component
public class Interceptor extends BaseController{

    @Pointcut("execution(* cn.edu.sdu.drs.controller..*.*())")
    private void controllerMethod(){}

    @Around("controllerMethod()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        //获得拦截到的Method的对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();   
        Method method = methodSignature.getMethod();
        //只对办公平台中的controller进行校验
        if(WebUtil.getRequestURI(request).contains("/controller/")){
            //没有权限的时候执行下面这段代码
            if(!validate(request, method)){
                Json json = new Json("对不起，您的权限不足！！");
                json.setSuccess(false);
                //writeJson(request, response, json);
                request.setAttribute("msg", "对不起，您的权限不足！！");
                return "/exception/error";
            }
        }
        return pjp.proceed();
    }

    /**
     * 权限校验
     * @return
     */
    private boolean validate(HttpServletRequest request, Method method) {
        if(method !=null && method.isAnnotationPresent(Privilege.class)){
            //得到方法上的注解
            Privilege privilege = method.getAnnotation(Privilege.class);
            //下面是得到执行方法需要的权限
            SystemPrivilege methodPrivilege = new SystemPrivilege(new SystemPrivilegePK(privilege.model(), privilege.privilegeValue()));
            if(WebUtil.getRootAdmin(request) == null && WebUtil.getSAASRootAdmin(request) == null){
                if(WebUtil.getAdmin(request) != null){
                    Admin admin = WebUtil.getAdmin(request);
                    for(PrivilegeGroup group : admin.getPrivilegeGroups()){
                        if(group.getPrivileges().contains(methodPrivilege)){
                            return true;
                        }
                    }
                    return false;
                }else if(WebUtil.getSAASAdmin(request) != null){
                    SAASAdmin sadmin = WebUtil.getSAASAdmin(request);
                    for(PrivilegeGroup group : sadmin.getPrivilegeGroups()){
                        if(group.getPrivileges().contains(methodPrivilege)){
                            return true;
                        }
                    }
                    return false;
                }
            }else if(WebUtil.getRootAdmin(request) != null){
                RootAdmin radmin = WebUtil.getRootAdmin(request);
                for(PrivilegeGroup group : radmin.getPrivilegeGroups()){
                    if(group.getPrivileges().contains(methodPrivilege)){
                        return true;
                    }
                }
                return false;
            }else if(WebUtil.getSAASRootAdmin(request) != null){
                SAASRootAdmin sradmin = WebUtil.getSAASRootAdmin(request);
                for(PrivilegeGroup group : sradmin.getPrivilegeGroups()){
                    if(group.getPrivileges().contains(methodPrivilege)){
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
