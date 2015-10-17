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
				<div id="registerForm" class="ui-mc-container ui-login-form">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	(function(){
		$(document).ready(function(){
			$.ui.crud.prototype.options.i18n.submitLabel='提交';
			$('#registerForm').crud({url:'${ctxPath}/home/register',action:'edit',
				desc:'如果您已经注册了账号请直接<a href="${ctxPath}/public/login">登录</a>。',
				editShowCancelBtn:false,showSubviewTitle:false,editFormStyle:'li',
				onFieldValueChange:function(event,data){
					if(data.name=='userid'){
						var t=$(this),val=data.input.val();
						if(val&&val.indexOf('@')!=-1){t.crud('setEditFieldVal','email',val);}
					}
				},
				onSaveSuccess:function(event,data){
					var id=data.entityID;
					setTimeout(function(){window.location='${ctxPath}/home/register/success/'+id;},50);
					return false;
				},
				onGeneratedFormField:function(e,d){
					if(d.field.name=='userid'){
						d.input.prop('placeholder','用户名');
					}else if(d.field.name=='password'){
						d.input.prop('placeholder','密码');
					}else if(d.field.name=='passwordConfirm'){
						d.input.prop('placeholder','密码确认');
					}
				}
			});
		});
		
	})();
	</script>
</body>
</html>