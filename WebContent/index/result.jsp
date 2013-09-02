<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<div class="content">
			<div class="wordPic"></div>
			<div class="model">
				<p>操作系统习题集.doc</p>
				进程与应用程序的区别在于应用程序作为一个静态文件存储在计算机系统的硬盘等存储空间中,
				而进程则是处于动态条件下由操作系统维护的系统资源管理实体。
			</div>
			<div class="operation">
				<span class="link"><a href="">下载</a></span>
				<span class="link"><a href="../tenant/controller/preview/1.doc/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
		<div class="content">
			<div class="xlsPic"></div>
			<div class="model">
				<p>学期选课表</p>
				大二下学期选课表 - 2010-2011学年第二学期三水校区公共选修课表 序号 课程性质 开课学院 课程名称 
				教师姓名 学分 1 容量 上课时间 面向对象 1 公共限选 公共...
			</div>
			<div class="operation">
				<span class="link">下载</span>
				<span class="link"><a href="../tenant/controller/preview/c.xls/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
		<div class="content">
			<div class="pptPic"></div>
			<div class="model">
				<p>面试题系列</p>
				对每个程序员来说，没有学不会的技术，只是没有那么多的时间与精力！
				如果能用最少的时间学透自己想要的技术，是程序员感到最幸福的事情！
			</div>
			<div class="operation">
				<span class="link">下载</span>
				<span class="link"><a href="../tenant/controller/preview/1.ppt/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
		<div class="content">
			<div class="pdfPic"></div>
			<div class="model">
				<p>jqueryeasyui中文培训文档</p>
				jquery easyui 中文培训文档 1.1.3 扩展 实例 html 代码中 aa id="aa border="true" 
				aa" 此行也可写成  id="aa" class="easyui-accordion...
			</div>
			<div class="operation">
				<span class="link">下载</span>
				<span class="link"><a href="../tenant/controller/preview/3.pdf/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
		<div class="content">
			<div class="musicPic"></div>
			<div class="model">
				<p>周杰伦-花海</p>
				大二下学期选课表 - 2010-2011学年第二学期三水校区公共选修课表 序号 课程性质 开课学院 课程名称 
				教师姓名 学分 1 容量 上课时间 面向对象 1 公共限选 公共...
			</div>
			<div class="operation">
				<span class="link">下载</span>
				<span class="link"><a href="../tenant/controller/preview/mp3.mp3/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
		<div class="content">
			<div class="videoPic"></div>
			<div class="model">
				<p>特仑苏广告</p>
				大二下学期选课表 - 2010-2011学年第二学期三水校区公共选修课表 序号 课程性质 开课学院 课程名称 
				教师姓名 学分 1 容量 上课时间 面向对象 1 公共限选 公共...
			</div>
			<div class="operation">
				<span class="link">下载</span>
				<span class="link"><a href="../tenant/controller/preview/video.flv/">预览</a></span>
				<span class="time">发布时间:2013-09-23</span>
			</div>
		</div>
	</div>
</body>
</html>
