<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="includes/tags.jsp"%>
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
				<div id="loginForm" class="ui-mc-container ui-login-form">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	(function(){
		$(document).ready(function(){
			var simple=false;
			if(window.parent&&window.parent!=window){
				/*对当前窗口进直接调整到精简模式*/
				simple=true;
				var c=$('#container'),login=$('#loginForm',c);
				c.removeClass('ui-fixed-940');
				login.detach();
				c.empty().append(login);
				$('body').addClass('ui-embed-login');
			}
			$.ui.crud.prototype.options.i18n.submitLabel='登录';
			$('#loginForm').crud({url:'${ctxPath}/web/login/execute',action:'edit',cssClass:'ui-login-form',
				editShowCancelBtn:false,showSubviewTitle:false,editFormStyle:'li',
				onSaveSuccess:function(event,data){
					window.location='${ctxPath}/web/login';
					return false;
				},
				onError:function(event,data){
					$(this).crud('tipInfo','用户名和密码错误！请再次输入。','err');
				},
				editFormActions:[{label:'注册',cssClass:'ui-action-register',func:function(event){if(!simple){window.location='${ctxPath}/web/register/default';}else{
					window.open('${ctxPath}/web/register/default');
				}}}],
				onGeneratedFormField:function(e,d){
					if(d.field.name=='userid'){
						d.input.prop('placeholder','用户名');
					}else if(d.field.name=='password'){
						d.input.prop('placeholder','密码');
						$(this).append('<div><a href="${ctxPath}/web/password/entry" target="'+(simple?'_blank':'_self')+'">忘记密码?</a></div>');
					}
				}
			});
		});
	})();
	</script>
</body>
</html>