<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=0.65,
 minimum-scale=0.65, maximum-scale=0.65, user-scalable=no"/>
<title><spring:message code="app.title" /></title>
<%@ include file="../includes/common_links.jsp"%>
</head>
<body>
   <div class="ui-header ui-highlight-bar">
		<div class="ui-topnav ui-container">
			<div class="ui-logo">
				<img src="${staticResPath}/images/logo.png" />
				<h1 title="${_appName }">${_appName }</h1>
			</div>
			<div class="ui-accounts">
				${jeeAccount.nick}(${jeeAccount.userid })<a
					href="${ctxPath }/logout">注销</a>
			</div>
		</div>
	</div>
	<!--  底部菜单，用于手机端显示 -->
	<div class="ui-layout-bottom nav4">
	<nav>
	<div id="nav4_ul" class="nav_4">
	<ul class="box">
		  <sec:authorize ifAnyGranted="ROLE_SUPER, ROLE_user_admin">
			    <li>
				    <a href="#"><span>系统管理</span></a>
				    <dl>
					    <dd><a href="javascript:_dictionary_admin()"><span>字典管理</span></a></dd>
					    <dd><a href="javascript:_access_admin()"><span>权限管理</span></a></dd>
					    <dd><a href="javascript:_shop_admin()"><span>门店管理</span></a></dd>
					    <dd><a href="javascript:_account_admin()"><span>账号管理</span></a></dd>
					    <dd><a href="javascript:_course_admin()"><span>课程管理</span></a></dd>
					    <dd><a href="javascript:_course_plan()"><span>课程安排</span></a></dd>
					    <dd><a href="javascript:_member_card_admin()"><span>会员卡管理</span></a></dd>
					    <dd><a href="javascript:_course_appointment()"><span>预约管理</span></a></dd>
					    <dd><a href="javascript:_evaluation_management()"><span>评价管理</span></a></dd>
				    </dl>
			    </li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_SHOP_MANAGER">
			    <li>
				    <a href="#"><span>门店管理</span></a>
				    <dl>
					    <dd><a href="javascript:_account_admin('shop')"><span>账号管理</span></a></dd>
					    <dd><a href="javascript:_course_admin()"><span>课程管理</span></a></dd>
					    <dd><a href="javascript:_course_plan()"><span>课程安排</span></a></dd>
					    <dd><a href="javascript:_member_card_admin()"><span>会员卡管理</span></a></dd>
					    <dd><a href="javascript:_course_appointment()"><span>预约管理</span></a></dd>
					    <dd><a href="javascript:_evaluation_management()"><span>评价管理</span></a></dd>
				    </dl>
			    </li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_TEACHER">
			    <li>
				    <a href="#"><span>老师管理</span></a>
				    <dl>
					    <dd><a href="javascript:_my_course()"><span>我的课程</span></a></dd>
					    <dd><a href="javascript:_my_appointment()"><span>预约管理</span></a></dd>
				    </dl>
			    </li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_TEACHER">
			    <li>
				    <a href="#"><span>营销人员管理</span></a>
				    <dl>
					    <dd><a href="javascript:_my_member()"><span>我的会员</span></a></dd>
				    </dl>
			    </li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_MEMBER">
			    <li>
				    <a href="#"><span>个人中心</span></a>
				    <dl>
					    <dd><a href="javascript:_member_course_appointing()"><span>现在预约课程</span></a></dd>
					    <dd><a href="javascript:_uncheck_member_course_appoint()"><span>已预约成功</span></a></dd>
					    <dd><a href="javascript:_checked_member_course_appoint()"><span>预约历史记录</span></a></dd>
					    <dd><a href="#"><span>修改账号信息</span></a></dd>
				    </dl>
			    </li>
			</sec:authorize>
		</ul>
	</div>
	</nav>
	</div>
	<div class="ui-layout-right ui-mc-container" style="padding-bottom: 45px"></div>
	<%@ include file="home_common_javascripts.jsp"%>
</body>
</html>