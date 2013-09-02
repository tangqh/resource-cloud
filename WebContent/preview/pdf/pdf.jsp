<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String fileName=request.getParameter("fileName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pdf在线预览</title>
<style type="text/css" media="screen">    
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
#flashContent { display:none; } 
</style> 
<script type="text/javascript" src="flexpaper/js/jquery.js"></script> 
<script type="text/javascript" src="flexpaper/js/flexpaper_flash.js"></script>
</head>
<body>
<div id="top">
	<span><a href="">下载<%=fileName %></a></span>
</div>
<div style="position:relative;left:0px;top:5px;">   
<center>
<a id="viewerPlaceHolder" style="width:1000px;height:800px;display:block">努力加载中............</a>
</center>  
<script type="text/javascript">          
$(document).ready(function(){  
	var fp = new FlexPaperViewer(
			'flexpaper/FlexPaperViewer',
			'viewerPlaceHolder', { config : {  
				SwfFile : escape('<%=fileName%>.swf'),        
				Scale : 0.6,  
       			ZoomTransition : 'easeOut',        
				ZoomTime : 0.5,        
				ZoomInterval : 0.2,        
				FitPageOnLoad : true,        
				FitWidthOnLoad : false,        
				PrintEnabled : false, 
       			FullScreenAsMaxWindow : false,        
				ProgressiveLoading : true,        
				MinZoomSize : 0.2,        
				MaxZoomSize : 5, 
       			SearchMatchAll : false,        
				InitViewMode : 'Portrait',        
       			ViewModeToolsVisible : true,        
				ZoomToolsVisible : true,        
				NavToolsVisible : true,  
 				CursorToolsVisible : true,
				SearchToolsVisible : true,        
       			localeChain: 'zh_CN'      
				}}); 
         });   
</script> 
</div>
</body>
</html>