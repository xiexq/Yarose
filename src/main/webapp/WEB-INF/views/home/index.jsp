<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0-SNAPSHOT Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1,
 minimum-scale=1, maximum-scale=1, user-scalable=no"/>
<title><spring:message code="app.title" /></title>
<%@ include file="../includes/common_links.jsp"%>
</head>
<body>
	<div id="ui-container">
		<div class="ui-header ui-highlight-bar">
			<div class="ui-topnav ui-container">
				<div class="ui-logo">
					<img src="${staticResPath}/images/logo2.png" />
					<h1 title="${_appName }">${_appName }</h1>
				</div>
				<div class="ui-accounts">
					${jeeAccount.nick}(${jeeAccount.userid })<a
						href="${ctxPath }/logout">注销</a>
				</div>
			</div>
		</div>
		<div class="ui-layout-column-4-1">
			<div class="ui-container">
				<div class="ui-layout-left ui-aside-container">
					<sec:authorize ifAnyGranted="ROLE_SUPER, ROLE_user_admin">
						<h3>
							<a href="#">系统管理</a>
						</h3>
						<div>
							<ul>
							    <li><a href="javascript:_dictionary_admin()">字典管理</a></li>
							    <li><a href="javascript:_shop_admin()">门店管理</a></li>
							    <li><a href="javascript:_access_admin()">权限管理</a></li>
								<li><a href="javascript:_account_admin()">账号管理</a></li>
								<li><a href="javascript:_course_admin()">课程管理</a></li>
								<li><a href="javascript:_course_plan()">课程安排</a></li>
								<li><a href="javascript:_member_card_admin()">会员卡管理</a></li>
								<li><a href="javascript:_course_appointment()">预约管理</a></li>
								<li><a href="javascript:_evaluation_management()">评价管理</a></li>
							</ul>
						</div>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_SHOP_MANAGER">
						<h3>
							<a href="#">门店管理</a>
						</h3>
						<div>
							<ul>
							    <li><a href="javascript:_account_admin('shop')">账号管理</a></li>
								<li><a href="javascript:_course_admin()">课程管理</a></li>
								<li><a href="javascript:_course_plan()">课程安排</a></li>
								<li><a href="javascript:_member_card_admin()">会员卡管理</a></li>
								<li><a href="javascript:_course_appointment()">预约管理</a></li>
								<li><a href="javascript:_evaluation_management()">评价管理</a></li>
							</ul>
						</div>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_TEACHER">
					 <h3>
						<a href="#">老师管理</a>
					 </h3>
					 <div>
						<ul>
						    <li><a href="javascript:_my_course()">我的课程</a></li>
						    <li><a href="javascript:_my_appointment()">预约管理</a></li>
						</ul>
					 </div>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_SUPER,ROLE_SALER">
					<h3>
					    <a href="#">营销人员管理</a>
					</h3>
					<div>
						<ul>
						    <li><a href="javascript:_my_member()">我的会员</a></li>
						</ul>
					</div>
					</sec:authorize>
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
								    <dd><a href="javascript:_account_admin()"><span>账号管理</span></a></dd>
								    <dd><a href="javascript:_course_admin()"><span>课程管理</span></a></dd>
								    <dd><a href="javascript:_course_grant()"><span>课程安排</span></a></dd>
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
								    <dd><a href="javascript:_course_grant()"><span>课程安排</span></a></dd>
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
					</ul>
				</div>
				</nav>
				</div>
			</div>
			<div class="ui-layout-right ui-mc-container"></div>
		</div>
	</div>
	<%@ include file="home_common_javascripts.jsp"%>
</body>
</html>