<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="includes/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${_appTitle }</title>
<%@ include file="includes/common_links_mobile.jsp" %>
</head>
<body>
	<div id="container">
		<div class="ui-body ui-mc-background">
			<div class="ui-container ui-title-image">
				<div id="registerForm" class="ui-mc-container ui-register-info">
					<h3>您已经注册成功！</h3>
					<p><a href="${ctxPath }/public/login">登录</a></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>