package cn.edu.sdu.drs.controller.xmlResource;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.controller.BaseController;
import cn.edu.sdu.drs.controller.privilege.Privilege;
import cn.edu.sdu.drs.service.resources.XMLServiceImpl;
import cn.edu.sdu.drs.util.WebUtil;
import cn.edu.sdu.drs.util.serarch.WordSplit;
import cn.edu.sdu.drs.web.bean.DataGrid;
import cn.edu.sdu.drs.web.bean.Json;
import cn.edu.sdu.drs.web.bean.Resource;

/**
 * 
 * @author join
 * 
 */

@Controller
@RequestMapping("/controller/resource")
public class ResourceController extends BaseController{

    /**
     * 显示增加资源的界面
     * @return 增加资源的界面
     */
    @RequestMapping(value="/addUI", method={RequestMethod.GET, RequestMethod.POST})
    @Privilege(model="resource", privilegeValue="insert")
    public String addUI(HttpServletRequest request, HttpServletResponse response){
        Admin admin = WebUtil.getAdmin(request);
        if(admin == null){
            return "admin/resourceAdd";
        }
        Tenant t = admin.getTenant();
        if(t == null) {
            return "admin/resourceAdd";
        }
        String submitUrl = "http://" + t.getHost() + ":" + t.getPort() + "/fileManager/upload?rootFolder=" + t.getRootFolder();
        request.setAttribute("submit-url", submitUrl);
        return "admin/resourceAdd";
    }

    /**
     * 增加资源
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping("/add")
    @Privilege(model="resource", privilegeValue="insert")
    public void add(@RequestParam("resource") MultipartFile resource, HttpServletRequest request, HttpServletResponse response)throws Exception {
        String date=(new Date()).toLocaleString();
        String title = resource.getOriginalFilename();
        int idx = title.lastIndexOf('.');
        String kind = title.substring(idx+1);
        Admin admin = WebUtil.getAdmin(request);
        String author = "anonymous", tenant = "public", keyWord = "";
        if(admin != null){
            author = admin.getRealname();
            tenant = admin.getTenant()==null ? "public" : admin.getTenant().getName();
        }
        ArrayList<String> keyWords=WordSplit.splitCHN(request.getParameter("describe"));//对describe进行分词
        for(Object o : keyWords){
            if(keyWord != null && !keyWord.equals("")){
                keyWord += "," + (String)o;
            }else{
                keyWord += (String)o;
            }
        }
        //获得本机的URL
        String url = InetAddress.getLocalHost().getHostAddress().toString();
        url = "http://" + url + ":8080/resources/" + title;
        XMLServiceImpl xs = (XMLServiceImpl)xmlResourceService;
        String path=xs.getPath("webapps");
        Tenant t = null;
        if(admin != null && admin.getTenant() != null) {
            t = admin.getTenant();
            String foo = t.getHost();
            if(foo != null && !"".equals(foo)) {
                url = "http://" + foo + ":" + t.getPort() + "/" + t.getRootFolder() + "/" + title;
            }
        }
        Json json = new Json("资源添加成功");
        String id = "";
        try {
            Element e = xs.addResourceitem();
            id = xs.addId(e);
            xs.addTitle(title, e);
            xs.addKeyWords(keyWord, e);
            xs.addKind(kind, e);
            xs.addDescribe(request.getParameter("describe"), e);
            xs.addDate(date, e);
            xs.addUrl(url, e);
            xs.addAuthor(author, e);
            xs.addtenant(tenant, e);
            xs.addShare(request.getParameter("share"), e);
            xs.resouceWrite(path+"\\test.xml");
            json.setObj(xmlResourceService.findById(id));
            writeJson(request, response, json);
        } catch (Exception e) {
e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("资源添加失败");
            writeJson(request, response, json);
        }
        if(id == null || id.equals("")){
            json.setSuccess(false);
            json.setMsg("资源添加失败");
            writeJson(request, response, json);
            return;
        }
        json.setObj(xmlResourceService.findById(id));
        writeJson(request, response, json);
        return;
    }

    @RequestMapping(value="/resourcesUI", method={RequestMethod.GET, RequestMethod.POST})
    @Privilege(model="resource", privilegeValue="select")
    public String resourcesUI(){
        return "admin/resources";
    }

    @RequestMapping(value="/resources/{pageNumber}/{pageSize}", method={RequestMethod.GET, RequestMethod.POST})
    @Privilege(model="resource", privilegeValue="select")
    public void resources(@PathVariable String pageNumber, @PathVariable String pageSize, HttpServletRequest request, HttpServletResponse response){
        int ps = 0, pn = 0;
        if(pageSize != null && !pageSize.equals("")){
            ps = Integer.parseInt(pageSize);
        }

        if(pageNumber != null && !pageNumber.equals("")){
            pn = Integer.parseInt(pageNumber);
        }
        int webpn = pn;
        List<Resource> qus = xmlResourceService.list(ps, pn);
        if(qus.size() < 1) {
            qus = xmlResourceService.list(ps, pn-1);
            webpn = pn-1;
        }
        DataGrid<Resource> dg = new DataGrid<Resource>();
        dg.setRows(qus);
        dg.setTotal((long) qus.size());
        request.setAttribute("pn", webpn);
        writeJson(request, response, dg);
    }

    /**
     * 显示edit资源的界面
     * @return 增加资源的界面
     */
    @RequestMapping(value="/editUI/{id}", method={RequestMethod.GET, RequestMethod.POST})
    @Privilege(model="resource", privilegeValue="update")
    public String editUI(@PathVariable String id){
        return "admin/resourceEdit";
    }

    /**
     * edit资源
     * @param id 资源的ID
     * @return
     */
    @RequestMapping(value="/edit/{id}", method={RequestMethod.GET, RequestMethod.POST})
    @Privilege(model="resource", privilegeValue="update")
    public void edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        String desc = request.getParameter("describe");
        String share = request.getParameter("share");
        Json json = new Json("更新资源成功！");
        if(!xmlResourceService.update(id, desc, share)){
            json.setSuccess(false);
            json.setMsg("更新资源失败！！");
        }
        json.setObj(xmlResourceService.findById(id));
        writeJson(request, response, json);
    }
    
    @RequestMapping("/remove")
    @Privilege(model="resource", privilegeValue="delete")
    public void remove(HttpServletRequest request, HttpServletResponse response){
        String strs = request.getParameter("ids");
        String ids[] = strs.split(",");
        Json json = new Json("删除资源成功");
        try {
            xmlResourceService.remove(ids);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("SORRY! 删除资源失败！");
        }
        writeJson(request, response, json);
    }
    
    @RequestMapping("/search/{name}")
    @Privilege(model="resource", privilegeValue="select")
    public void search(@PathVariable String name, HttpServletRequest request, HttpServletResponse response){
        if(name == null || name.trim().length()==0){
            return;
        }
        try {
            byte c[] = name.getBytes("iso8859-1");
            name = new String(c, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return;
        }
        List<Resource> qus = xmlResourceService.findByTitle(name);
        DataGrid<Resource> dg = new DataGrid<Resource>();
        dg.setRows(qus);
        dg.setTotal((long) qus.size());
        writeJson(request, response, dg);
    }
    
    private String getCurrentTenantPath(Tenant t, String title) {
        return t.getHost() + ":" + t.getPort() + "\\" + "webapps" + "\\" + t.getRootFolder() + "\\" + title;
    }

}
