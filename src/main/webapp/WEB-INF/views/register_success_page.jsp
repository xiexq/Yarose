<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="includes/tags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="includes/common_links.jsp" %>
	<title>${_appTitle }</title>
</head>
<body>
	<div id="container" class="ui-fixed-940">
		<div class="ui-body ui-mc-background">
			<div class="ui-container ui-title-image">
				<div id="registerForm" class="ui-mc-container ui-register-info">
					<h3>您已经注册成功！</h3>
					<p>您现在可以直接<a href="${ctxPath }/public/login">登录。</a></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>