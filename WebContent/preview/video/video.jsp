<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>video</title>
<link rel="stylesheet" type="text/css" href="CuPlayer/Images/common.css">
</head>
<!--myplayer/begin-->
<div id="myplayer">

	<!--酷播迷你 CuPlayerMiniV3.0 代码开始-->
	<script type="text/javascript" src="CuPlayer/Images/swfobject.js"></script>
	<div id="CuPlayer">
		<strong>提示：您的Flash Player版本过低，请<a
			href="http://www.CuPlayer.com/">点此进行播放器升级</a>！
		</strong>
	</div>
	<script type=text/javascript>
		var so = new SWFObject("CuPlayer/CuPlayerMiniV3_Black_S.swf", "CuPlayer", "600",
				"400", "9", "#000000");
		so.addParam("allowfullscreen", "true");
		so.addParam("allowscriptaccess", "always");
		so.addParam("wmode", "opaque");
		so.addParam("quality", "high");
		so.addParam("salign", "lt");
		so.addVariable("CuPlayerFile",
				"http://127.0.0.1:8080/resources/<%=request.getParameter("fileName")%>");
		so.addVariable("CuPlayerImage", "CuPlayer/Images/flashChangfa2.jpg");
		so.addVariable("CuPlayerLogo", "CuPlayer/Images/logo.png");
		so.addVariable("CuPlayerShowImage", "true");
		so.addVariable("CuPlayerWidth", "600");
		so.addVariable("CuPlayerHeight", "400");
		so.addVariable("CuPlayerAutoPlay", "false");
		so.addVariable("CuPlayerAutoRepeat", "false");
		so.addVariable("CuPlayerShowControl", "true");
		so.addVariable("CuPlayerAutoHideControl", "false");
		so.addVariable("CuPlayerAutoHideTime", "6");
		so.addVariable("CuPlayerVolume", "80");
		so.addVariable("CuPlayerGetNext", "false");
		so.write("CuPlayer");
	</script>
</div>

</body>
</html>