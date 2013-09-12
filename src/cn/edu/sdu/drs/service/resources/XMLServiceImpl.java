package cn.edu.sdu.drs.service.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.edu.sdu.drs.bean.resourcesBean.XmlResource;
import cn.edu.sdu.drs.bean.tenant.Tenant;
import cn.edu.sdu.drs.bean.tenant.admin.Admin;
import cn.edu.sdu.drs.bean.tenant.user.User;
import cn.edu.sdu.drs.util.WebUtil;
import cn.edu.sdu.drs.util.serarch.HeighLight;
import cn.edu.sdu.drs.web.bean.Resource;

@Service("XMLService")
public class XMLServiceImpl implements XMLService{
    XmlResource ji;
    ArrayList<Object> dataSet = new ArrayList<Object>();
    
    private Document doc = null;
    private Element root = null;
    private Element resourceitem = null;
    private String filepath = "\\test.xml";

    int ii = 0;

    String staytime;
    String id;
    String title;
    String kind;
    String describe;
    String date;
    String url;
    String author;
    String tenant;
    String share;

    ArrayList<Object> searchContent;

    private String[] kinds = new String[] { "all" };
    
    /**
     * 更新一条纪录<br>
     * 函数名：update<br>
     * 作用：将指定id的元素更新为新传入元素<br>
     * 参数：String id-所要更新的元素编号<br>
     * 参数：XML_Element element-所传入的新元素
     * 返回值：boolean 是否更新成功<br>
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    public void update(String id,XmlResource element) throws Exception {
        Element e;
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            e = (Element)i.next();
            if(e.getName().equals("resourceitem")&&(e.elementText("id").equals(id))){
                e.element("describe").setText(element.getDescribe());
                e.element("date").setText(element.getDate());
                break;
            }
        }     
        resouceWrite(getPath("webapps")+filepath);
    }

    /**
     * <br>
     * 函数名：getString <br>
     * 作 用： 资源检索的主方法，能够对XML执行检索并根据需求生成结果记录集返回请求检索的主机 <br>
     * 参 数： ArrayList Txt -- 搜索文本【用户输入的文本】
     * 参 数：String searchKind -- 搜索类型【doc,ppt,gif....】
     * 参 数：String type <br> -- 搜索方式【普通搜索，高级搜索】
     * 返回类型 ArrayList 结果记录集
     * 
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> list(ArrayList<Object> Txt, String searchKind, String type) throws UnsupportedEncodingException {
System.out.println("检索方式为：" + type);//写到日志
        String tomcat = getPath("webapps");
        String filepath = tomcat + "\\" + "test.xml";
        this.searchContent = Txt;
        if(searchKind != null){
            if(searchKind.equals("document")) {
                kinds = new String[] { "doc", "ppt" };
            } else if (searchKind.equals("mp3")) {
                kinds = new String[] { "mp3", "wma" };
            } else if (searchKind.equals("video")) {
                kinds = new String[] { "asf", "avi", "rm", "swf", "wmv" };
            } else if (searchKind.equals("picture")) {
                kinds = new String[] { "jpg", "gif", "bmp", "jpeg", "psd", "png", "pcx", "tiff" };
            } else{
                kinds = new String[] { "all" };
            }
        }
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(filepath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Element foo = null;
        Iterator<Object> it = root.elementIterator("resourceitem");
        // 遍历检索XML文档内容
        while (it.hasNext()) {
            foo = (Element) it.next();
            String keywords = foo.elementText("keywords");
            kind = foo.elementText("kind");
            id = foo.elementText("id");
            title = foo.elementText("title");
            describe = foo.elementText("describe");
            date = foo.elementText("date");
            url = foo.elementText("url");
            author = foo.elementText("author");
            tenant = foo.elementText("tenant");
            share = foo.elementText("share");
            if(share!=null && share.equals("NO")){
                //拿到当前session中的用户，判断：
                //如果该资源不是是该用户所在租户内，而且该资源不是共享类型的资源，那么该条记录不能被返回
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                User currentUser = WebUtil.getUser(request);
                boolean flag = false;
                if(currentUser == null){
                    continue;
                }else{
                    for(Tenant t : currentUser.getTenants()){
                        if(tenant!=null && tenant.equals(t.getName())){
                            flag = true;
                            break;
                        }
                    }
                }
                if(!flag){
                    continue;
                }
            }
            if (keywords != null) {
                // 循环对比查询关键字是否符合要求
                for (int index = 0; index < searchContent.size(); index++) {
                    // 对检索类型的检索处理
                    boolean ok = false;
                    if ((type.equals("every")) && (keywords.indexOf(searchContent.get(index).toString()) != -1)) {
                        ok = true;
                    } else {
                        if (type.equals("all")) {
                            ok = true;
                            for (int jj = 0; jj < searchContent.size(); jj++) {
                                if (!keywords.equals(searchContent.get(jj).toString())) {
                                    ok = false;
                                    break;
                                }
                            }
                        } else {
                            if (type.equals("complete")) {
                                String ss = "";
                                for (int jj = 0; jj < searchContent.size(); jj++) {
                                    ss += searchContent.get(jj).toString();
                                }
                                if (keywords.indexOf(ss) != -1) {
                                    ok = true;
                                } else {
                                    ok = false;
                                }
                            } else {
                                if (type.equals("same")) { // 与关键字完全匹配
                                    // 多关键字以逗号拆分
                                    String[] chai;
                                    if (keywords.indexOf(",") != -1) {
                                        chai = keywords.split(",");
                                    } else {
                                        chai = keywords.split("，");
                                    }
                                    // 多关键字以逗号拆分
                                    String sss = "";
                                    for (int jj = 0; jj < searchContent.size(); jj++) {
                                        sss += searchContent.get(jj).toString();
                                    }
                                    ok = false;
                                    for (int jj = 0; jj < chai.length; jj++) {
                                        if (sss.equals(chai[jj])) {
                                            ok = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 检索类型处理完毕
                    // 符合要求
                    if (ok) { // 符合要求
                        // 判断 当前记录的 类别 是否和 搜索类别相同，
                        boolean KindCom = false;
                        if (kinds[0].equals("all")) {
                            KindCom = true; // 如果类型为空 表示支持任何格式的类型
                        }else{
                            for (int j = 0; j < kinds.length; j++) {
                                if (kind.equals(kinds[j])) {
                                    KindCom = true; // 如果属于要搜索的类型 KindCom 为 true
                                    break;
                                }
                            }
                        }
                        if (KindCom) {
                            // 判断关键字中使用英文逗号还是中文逗号
                            // 本模块的目的是为了给数据打分 获取他的价值性
                            /*
                             * 此块为按照title进行数据排序
                             */
                            DecimalFormat nf = new DecimalFormat("##0.##");
                            double price = 0;
                            String key = title;

                            if (key.indexOf(searchContent.get(index).toString()) != -1) {
                                price = ((float) searchContent.get(index).toString().length() / (float) key.length());
                                price = Double.parseDouble(nf.format(price)) * 100;
                            } else {
                                price = 0;
                            }
                            // 设置关键字高亮
                            String title2 = HeighLight.turn(title, searchContent);
                            String describe2 = HeighLight.turn(describe, searchContent);
                            // 关键字高亮处理完毕
                            ji = new XmlResource(id, title2, kind, describe2, date, url, author, tenant, price);
                            dataSet.add(ji);
                            break;
                        }
                    }
                }
            }
        }
        return dataSet;
    }
    
    /**
     * <br>
     * 函数名：addResourceitem <br>
     * 作 用： 建立对test.xml的解析，并添加resourceitem元素节点 <br>
     * 参 数：无 <br>
     * 返回类型：无
     */
    public Element addResourceitem() throws DocumentException {
        // 得到test.xml的存放地址
        String path = getPath("webapps");
        // test.xml的访问路径
        String filepath = path + "\\" + "test.xml";
        // 构建一个 解析器
        SAXReader xmlReader = new SAXReader();
        doc = xmlReader.read(filepath);
        root = doc.getRootElement();
        resourceitem = root.addElement("resourceitem");
        return resourceitem;
    }

    // 为resourceitem添加id元素节点
    public String addId(Element resourceitem) {
        if(resourceitem == null){
                return null;
        }
        String idText = UUID.randomUUID().toString();
        Element idNode = resourceitem.addElement("id");
        idNode.addText(idText);
        return idText;
    }
    
    // 为resourceitem添加title元素节点
    public void addTitle(String titleText, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element titleNode = resourceitem.addElement("title");
        titleNode.addText(titleText);
    }

    // 为resourceitem添加keywords元素节点
    public void addKeyWords(String keywords, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element keyWordsNode = resourceitem.addElement("keywords");
        keyWordsNode.addText(keywords);
    }

    // 为resourceitem添加kind元素节点
    public void addKind(String kind, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element kindNode = resourceitem.addElement("kind");
        kindNode.addText(kind);
    }

    // 为resourceitem添加describe元素节点
    public void addDescribe(String describe, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element describeNode = resourceitem.addElement("describe");
        describeNode.addText(describe);
    }

    // 为resourceitem添加date元素节点
    public void addDate(String date, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element dateNode = resourceitem.addElement("date");
        dateNode.addText(date);
    }

    // 为resourceitem添加url元素节点
    public void addUrl(String url, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element urlNode = resourceitem.addElement("url");
        urlNode.addText(url);
    }

    // 为resourceitem添加author元素节点
    public void addAuthor(String author, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element authorNode = resourceitem.addElement("author");
        authorNode.addText(author);
    }

    // 为resourceitem添加tenant元素节点
    public void addtenant(String tenant, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element tenantNode = resourceitem.addElement("tenant");
        tenantNode.addText(tenant);
    }
    
    // 为resourceitem添加share元素节点
    public void addShare(String share, Element resourceitem) {
        if(resourceitem == null){
            return;
        }
        Element shareNode = resourceitem.addElement("share");
        shareNode.addText(share);
    }

    public void resouceWrite(String newfilename) throws IOException {
        // 格式化输出,类型IE浏览一样
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 指定XML字符集编码
        format.setEncoding("GBK");
        XMLWriter writer = new XMLWriter(new FileOutputStream(new File(
                newfilename)));
        if(doc != null){
            writer.write(doc);
            writer.flush();
            writer.close();
        }
    }


    @Override
    public List<Resource> list(int pageSize, int pageNumber) {
        List<Resource> rs = new ArrayList<Resource>();
        if(doc == null){
            doc = getDoc();
        }
        if(doc == null){
            return null;
        }
        Element root = doc.getRootElement();
        Element foo = null;
        Iterator it = root.elementIterator("resourceitem");
        int c = 0;
        int pn = 0;
        if(pageSize == 0 || pageNumber==0){
            pageSize = 10;
        }else{
            pn = (pageSize)*(pageNumber-1);
        }
        while (it.hasNext() && c<pageSize) {
            foo = (Element) it.next();
            String str = foo.elementText("tenant");
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            Admin admin = WebUtil.getAdmin(request);
            if(admin.getTenant() == null || str == null || !str.equals(admin.getTenant().getName())) {
                continue;
            }
            if(pn==0){
                Resource r = new Resource();
                r.setId(foo.elementText("id"));
                r.setTitle(foo.elementText("title"));
                r.setDescribe(foo.elementText("describe"));
                r.setKind(foo.elementText("kind"));
                r.setUrl(foo.elementText("url"));
                r.setShare(foo.elementText("share"));
                r.setKeyWord(foo.elementText("keywords"));
                rs.add(r);
                c++;
            }else{
                pn--;
            }
        }
        return rs;
    }

    @Override
    public void remove(String[] ids) throws Exception {
        if(doc == null){
            doc = getDoc();
        }
        if(doc == null || ids == null || ids.length<1){
            return;
        }
        Element root = doc.getRootElement();
        for(String rid : ids){
            Element e;
            for(Iterator i = root.elementIterator("resourceitem"); i.hasNext();){
                e = (Element)i.next();
                if(rid.equals(e.elementText("id"))){
                    root.remove(e);
                    break;
                }
            }
        }
        resouceWrite(getPath("webapps")+filepath);
    }
    
    /**
     * 
     * @return
     */
    private Document getDoc(){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(getPath("webapps")+filepath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
    
    /**
     * <br>
     * 函数名： getPath <br>
     * 作 用： 获取服务器地址 <br>
     * 参 数： Webapps <br>
     * 返回类型 String
     * 
     */
    public String getPath(String Webapps) {
        String path = System.getProperty("catalina.home");
        int binindex = path.lastIndexOf("bin");
        if (binindex != -1) {
            path = path.substring(0, binindex - 1);
        }
        path = path + "\\" + Webapps;
        return path;
    }

    @Override
    public boolean update(String id, String desc, String share) {
        if(doc == null){
            doc = getDoc();
        }
        if(doc == null || id == null || id.equals("")){
            return false;
        }
        Element root = doc.getRootElement();
        Element e;
        for(Iterator i = root.elementIterator("resourceitem"); i.hasNext();){
            e = (Element)i.next();
            if(id.equals(e.elementText("id"))){
                e.element("describe").setText(desc);
                e.element("share").setText(share);
                break;
            }
        }
        try {
            resouceWrite(getPath("webapps")+filepath);
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    @Override
    public List<Resource> findByTitle(String title) {
        if(doc == null){
            doc = getDoc();
        }
        if(doc == null || title == null || title.equals("")){
            return null;
        }
        List<Resource> resources = new ArrayList<Resource>();
        Element root = doc.getRootElement();
        Element foo = null;
        Iterator it = root.elementIterator("resourceitem");
        Resource r = new Resource();
        int n = 0;
        while (n<10 && it.hasNext()) {
            foo = (Element) it.next();
            if(foo.elementText("title").contains(title)){
                r.setId(foo.elementText("id"));
                r.setTitle(foo.elementText("title"));
                r.setDescribe(foo.elementText("describe"));
                r.setKind(foo.elementText("kind"));
                r.setUrl(foo.elementText("url"));
                r.setShare(foo.elementText("share"));
                r.setKeyWord(foo.elementText("keywords"));
                resources.add(r);
                n++;
            }
        }
        return resources;
    }
    
    @Override
    public Resource findById(String id) {
        if(doc == null){
            doc = getDoc();
        }
        if(doc == null || id == null || id.equals("")){
            return null;
        }
        Element root = doc.getRootElement();
        Element foo = null;
        Iterator it = root.elementIterator("resourceitem");
        Resource r = new Resource();
        while (it.hasNext()) {
            foo = (Element) it.next();
            if(id.equals(foo.elementText("id"))){
                r.setId(foo.elementText("id"));
                r.setTitle(foo.elementText("title"));
                r.setDescribe(foo.elementText("describe"));
                r.setKind(foo.elementText("kind"));
                r.setUrl(foo.elementText("url"));
                r.setShare(foo.elementText("share"));
                r.setKeyWord(foo.elementText("keywords"));
                return r;
            }
        }
        return null;
    }
}
