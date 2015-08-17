<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${staticResPath}/styles/default/normal/ui.errorpage.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${staticResPath}/images/favicon.png" type="image/png"/>
<title>500 出错啦！</title>
</head>
<body>
	<div class="ui-alert-panel">
		<a href="#" class="ui-logo">
			<img src="${staticResPath}/images/logo_transparent.png"></img>
		</a>
		<h1>- 出错啦！</h1>
		<div class="ui-alert-info">
			<p><strong>处理您的请求时发生错误！请确认您通过正确途径操作。</strong></p>
			<%if(exception != null){ %>
				<div class="ui-error-msg">
					<h3>错误原因：</h3>
					<p><%=exception.getMessage() %></p>
				</div>
			<%} %>
			<hr></hr>
			<span><a href="${ctxPath }">返回首页</a></span>
		</div>
	</div>
</body>
</html>