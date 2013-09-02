<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>wpe</title>
<style type="text/css">
html, body {
    height: 100%;
    margin: 0px;
    padding: 0px;
    background-color:#C0C0C0; 
    font-family: Arial,Console,Verdana,Courier New;
    font-size: 16px;
}
#top{
	height: 5%;
	background-color: black;
}
#top a{
	text-decoration: none;
	color:white;
}
#top a:hover{
	border: 1px solid #eee;
}
#top span{
	float: right;
	margin-right: 40px;
	margin-top: 6px;
}
.frame{
	margin: 0 auto;
	width: 800px;
	height: 94%;
	background-color: white;
}
</style>
</head>
<body>
<div id="top">
	<span><a href="">下载<%=request.getParameter("fileName") %></a></span>
</div>
<div class="frame">
 <iframe src= "<%=request.getParameter("fileName") %>.html" width= "800px"   height= "100%"> </iframe>
</div>
</body>
</html>