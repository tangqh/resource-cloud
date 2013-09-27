package cn.edu.sdu.drs.controller.preview;

// http://localhost:8080/resource-cloud/tenant/controller/preview/video.flv/

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.edu.sdu.drs.util.converter.Excel2Html;
import cn.edu.sdu.drs.util.converter.Mp3Util;
import cn.edu.sdu.drs.util.converter.PPT2PNG;
import cn.edu.sdu.drs.util.converter.Pdf2Swf;
import cn.edu.sdu.drs.util.converter.Word2Html;

@Controller
@RequestMapping("/tenant/controller")
public class PreviewController {

    @RequestMapping("/preview")
    public void preview(HttpServletRequest request, HttpServletResponse response) {

        // 源文件路径
        String srcFilePath = request.getParameter("url");
        int lstidx = srcFilePath.lastIndexOf("/");
        String fileName = srcFilePath.substring(lstidx+1);
        // 输出文件目录
        String webapp = request.getSession().getServletContext()
                .getRealPath("/");
        final String targetDir;

        final String appName = request.getSession().getServletContext()
                .getContextPath();

        try {
            if (fileName.endsWith(".doc")) {
                targetDir = webapp + "/preview/word/";
                Word2Html.convert(srcFilePath, targetDir, fileName);
                response.sendRedirect(appName
                        + "/preview/word/word.jsp?fileName=" + fileName);
            } else if (fileName.endsWith(".ppt")) {
                targetDir = webapp + "/preview/ppt/";
                int pageNum = PPT2PNG.convert(srcFilePath, targetDir);
                response.sendRedirect(appName
                        + "/preview/ppt/ppt.jsp?fileName=" + fileName
                        + "&pageNum=" + pageNum);
            } else if (fileName.endsWith(".xls")) {
                targetDir = webapp + "/preview/excel/";
                Excel2Html.getInstance().convert(srcFilePath, targetDir,
                        fileName, "utf-8");
                response.sendRedirect(appName
                        + "/preview/excel/excel.jsp?fileName=" + fileName);
            } else if (fileName.endsWith(".pdf")) {
                targetDir = webapp + "/preview/pdf/";
                Pdf2Swf.convert(srcFilePath, targetDir, fileName);
                response.sendRedirect(appName
                        + "/preview/pdf/pdf.jsp?fileName=" + fileName);
            } else if (fileName.endsWith(".flv") || fileName.endsWith(".mp4")) {
                response.sendRedirect(appName
                        + "/preview/video/video.jsp?fileName=" + fileName);
            } else if (fileName.endsWith(".mp3")) {
                targetDir = webapp + "/preview/mp3/";
                Mp3Util.playerXml(targetDir, "player_S.xml",
                        "http://127.0.0.1:8080/resources/" + fileName);
                response.sendRedirect(appName + "/preview/mp3/mp3.html");
            } else {
                response.sendRedirect(appName + "/preview/error.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
