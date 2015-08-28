<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="includes/common_links.jsp"%>
</head>
<body style="margin:0;">
	<div id="container" style="width:100%;">
		<div style="width:100%;height:60px;background-color: #4575B4;margin:0;">
			<div style="width:80%;margin:0 auto;min-width:700px;">
				<%-- <img src="${staticResPath}/images/logo_b.png" style="float:left;"></img>  --%>
			</div>
		</div>
		<div style="width:80%;margin:30px auto;">
			<div id="loginForm" class="ui-widget ui-crud-widget ui-login-form">
				<div class="ui-widget-header">
					<span class="ui-title">用户登录</span>
				</div>
				<div class="ui-widget-content">
					<div class="ui-main-content">
						<div class="ui-content ui-edit-pane">
							<form action="../login" method="post">
								<fieldset class="ui-fieldset-default">
									<ul>
										<li class="ui-field-item ui-field-t-input ui-field-userid">
											<label class="ui-field-label">用户名</label>
											<input id="j_username" class="ui-input ui-autocomplete-input" name="j_username" type="text"/>
											<c:if test="${not empty(param.e) }">
												<span class="ui-field-info"><span class="ui-validate-wrong-msg">用户名或密码错误</span></span>
											</c:if>
										</li>
										<li class="ui-field-item ui-field-t-password ui-field-password">
											<label class="ui-field-label">密码</label>
											<input class="ui-input" type="password" name="j_password"/>
										</li>
									</ul>
								</fieldset>
								<div class="ui-form-operate-bar">
									<button type="submit" class="ui-action-submit ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
										<span class="ui-button-text">登录</span>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		(function($){
			document.getElementById('j_username').focus();
			if($.fn.button){$('button').button();}
		})(jQuery);
	</script>
</body>
</html>