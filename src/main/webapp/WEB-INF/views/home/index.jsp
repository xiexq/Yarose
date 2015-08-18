<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="app.title" /></title>
<%@ include file="../includes/common_links.jsp"%>
</head>
<body>
	<div id="ui-container">
		<div class="ui-header ui-highlight-bar">
			<div class="ui-topnav ui-container">
				<div class="ui-logo"><img src="${staticResPath}/images/logo.png"/><h1 title="${_appName }">${_appName }</h1></div>
				<div class="ui-accounts">
					${jeeAccount.nick}(${jeeAccount.userid })<a href="${ctxPath }/cas_security_logout">注销</a>
				</div>
			</div>
		</div>
		<div class="ui-layout-column-4-1">
			<div class="ui-container">
				<div class="ui-layout-left ui-aside-container">
					<sec:authorize ifAnyGranted="ROLE_SUPER, ROLE_user_admin">
					<h3><a href="#">用户管理</a></h3>
					<div>
						<ul>
							<li><a href="javascript:_account_admin()">账号管理</a></li>
							<li><a href="javascript:_access_admin()">权限管理</a></li>
						</ul>
					</div>
					</sec:authorize>
				</div>
			</div>
			
			<div class="ui-layout-right ui-mc-container">
			</div>
		</div>
		<div class="ui-weak-container">
			<div class="ui-container">
				<spring:message code="app.copyrights"/>
			</div>
		</div>
	</div>
	<%@ include file="home_common_javascripts.jsp"%>
</body>
</html>