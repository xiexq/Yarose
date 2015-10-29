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
		<div style="width:100%;height:50px;margin:0;" class="ui-header ui-highlight-bar">
			<div style="width:80%;margin:0 auto;min-width:700px;">
				<%-- <img src="${staticResPath}/images/logo.png" style="float:left;"></img> --%>
			</div>
		</div>
		<div class="ui-body ui-mc-background">
			<div class="ui-container ui-title-image">
				<div id="registerForm" class="ui-mc-container ui-login-form">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	(function(){
		$(document).ready(function(){
			$.ui.crud.prototype.options.i18n.submitLabel='提交';
			$('#registerForm').crud({url:'${ctxPath}/web/register',action:'edit',
				desc:'如果您已经注册了账号请直接<a href="${ctxPath}/public/login">登录</a>。',
				editShowCancelBtn:false,showSubviewTitle:false,
				onFieldValueChange:function(event,data){
					if(data.name=='userid'){
						var t=$(this),val=data.input.val();
						if(val&&val.indexOf('@')!=-1){t.crud('setEditFieldVal','email',val);}
					}
				},
				onSaveSuccess:function(event,data){
					var id=data.entityID;
					setTimeout(function(){window.location='${ctxPath}/web/register/success/'+id;},50);
					return false;
				}
			});
		});
		
	})();
	</script>
</body>
</html>