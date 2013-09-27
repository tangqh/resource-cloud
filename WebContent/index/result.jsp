<%@ page language="java" import="java.util.*, cn.edu.sdu.drs.web.bean.Resource" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    List<Resource> resources = (List<Resource>)request.getAttribute("records");
%>

<html>
<head>
<style type="text/css">
html, body {
    height: 100%;
    margin: 0px;
    padding: 0px;
    font-family: Arial,Console,Verdana,Courier New;
    font-size: 16px;
}
.container{
	margin: 30 auto;
	width: 1000px;
	height: 94%;
}
.content{
	height: 110px;
	margin-bottom: 10px;
	padding-bottom: 10px;
}
.wordPic,.xlsPic,.pptPic,.pdfPic,.musicPic,.videoPic{
	height: 110px;
	width: 11%;
	float: left;
}
.wordPic{
	background-image: url('../resources/pictures/word.png');
}
.xlsPic{
	background-image: url('../resources/pictures/xls.png');
}
.pptPic{
	background-image: url('../resources/pictures/ppt.png');
}
.pdfPic{
	background-image: url('../resources/pictures/pdf.png');
}
.musicPic{
	background-image: url('../resources/pictures/music.png');
}
.videoPic{
	background-image: url('../resources/pictures/video.png');
}
.model{
	height: 95px;
	width: 87%;
	float: right;
}
.operation{
	height: 25px;
	width: 89%;
	float: right;
}
.link{
	margin-left: 30px;
}
.time{
	float: right;
	margin-right: 10px;
}

</style>
</head>
<body>
<h2>搜索结果</h2>
 	<div class="container">
            <%
                if(resources != null && resources.size() > 0) {
                    for(Resource r : resources) {
                        //http://127.0.0.1:8081/cjbs-folder/1.doc
                        int idx = r.getUrl().lastIndexOf(':');
                        String url = r.getUrl().substring(0, idx+5);
                        idx = r.getUrl().lastIndexOf('-');
                        String rootFolder = r.getUrl().substring(idx-4, idx+7);
                        idx = r.getTitle().lastIndexOf('.');
                        String tar = url + "/fileManager/view?fileName="+ r.getTitle() + "&rootFolder=" + rootFolder;
                        %>
                        <div class="content">
                        <%
                        if(r.getKind() == null) {
                            continue;
                            }else if(r.getKind().contains("doc")) {
                                %>
                                <div class="wordPic"></div>
                                <%
                            }else if(r.getKind().contains("xls")) {
                                %>
                                <div class="xlsPic"></div>
                                <%
                            }else if(r.getKind().contains("ppt")) {
                                %>
                                <div class="pptPic"></div>
                                <%
                            }else if(r.getKind().contains("pdf")) {
                                %>
                                <div class="pdfPic"></div>
                                <%
                            }else if(r.getKind().contains("mp3") ) {
                                %>
                                <div class="musicPic"></div>
                                <%
                            }else if(r.getKind().contains("flv") || r.getKind().contains("avi") || r.getKind().contains("swf") || r.getKind().contains("application")) {
                                %>
                                <div class="videoPic"></div>
                                <%
                            }else{
                                tar = r.getUrl();
                                %>
                            <div class="pptPic"></div>
                            <%
                            }
                        %>
                            <div class="model">
                                <p><%=r.getTitle() %></p>
                                <%=r.getDescribe() %>
                            </div>
                            <div class="operation">
                                <span class="link"><a href="<%=tar%>" target="_blank">预览</a></span>
                            </div>
                            </div>
                            <%
                    }
                }else{
                    %>
                    <font color="red"><h1>抱歉，没有找到符合你条件的资源！</h1></font>
                    <%
                }
            %>
            </div>
</body>
</html>
