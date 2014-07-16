<%@page import="com.webanalytics.web.dto.MemberProfile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
body {
	background-image: url('../images/home_wallpaper.jpg');
	background-color: #cccccc;
	margin:0;
	padding:0;
	padding-top:10px;
	text-align : center;
}
#bodyPanel{
	width : 800px;
	text-align:left;
	border : 0px;
	padding : 0;
	margin :0 auto;
}
</style>
<title>Anlaytics On Hadoop</title>

<link href="../resources/css/ext-all-neptune.css" rel="stylesheet"
	type="text/css" />


<script src="../extjs/ext-all-debug.js" type="text/javascript"></script>
<script src="../banner/app.js" type="text/javascript"></script>
<script src="/WebAnalyticsConnector/js/collector.js" type="text/javascript"></script>
</head>
<body>


	<div id="bodyPanel"></div>
	<%@ include file="../banner/intro.html" %>
</body>
</html>