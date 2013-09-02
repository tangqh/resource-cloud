<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>wpe</title>
<script type="text/javascript" src="jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="turn.js"></script>
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
	height: 30px;
	background-color: black;
}
#top a{
	text-decoration: none;
}
#top span{
	float: right;
	margin-right: 40px;
	margin-top: 5px;
}
#top a:hover{
	border: 1px solid #eee;
}
#magazine{
	margin: 30px auto;
}
#magazine .turn-page{
	background-color:#ccc;
	background-size:100% 100%;
}
</style>
<% String fileName=request.getParameter("fileName");
	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
%>
</head>
<body>

<div id="top">
	<span><a href=""><font color="white">下载<%=fileName%></font></a></span>
</div>

<div id="magazine">
	<div style="background-image:url(index.jpg);"></div>
	<% for(int i=1;i<=pageNum;i++){
		out.print("<div style='background-image:url("+fileName+"-"+i+".png);'></div>");
	} %>
	<div style="background-image:url(index.jpg);"></div>
</div>


<script type="text/javascript">
	
	$('#magazine').bind('turned', function(e, page) {

		console.log('Current view: ', $('#magazine').turn('view'));

	})

	$('#magazine').turn({width: 1000, height: 550, acceleration: true, shadows: !$.isTouch});


</script>
</body>
</html>