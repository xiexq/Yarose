<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${staticResPath}/styles/default/normal/ui.errorpage.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${staticResPath}/images/favicon.png" type="image/png"/>
<title>403 访问被拒绝</title>
</head>
<body>
	<div class="ui-alert-panel">
		<a href="#" class="ui-logo">
			<img src="${staticResPath}/images/logo_transparent.png"></img>
		</a>
		<h1>- 访问被拒绝</h1>
		<div class="ui-alert-info">
			<p><strong>Oh! 芝麻开门失败，您没有权限访问该资源！</strong></p>
			<p>请联系系统管理员已获得相关使用权限。</p>
			<hr></hr>
			<span><a href="${ctxPath }">返回首页</a></span>
		</div>
	</div>
</body>
</html>