<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${staticResPath}/styles/mobile/star.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${staticResPath}/scripts/jquery.all.min.js"></script>
<body>
<div id="xzw_starSys">
	<div id="xzw_starBox">
		<ul class="star" id="star">
			<li><a href="javascript:void(0)" title="1" class="one-star">1</a></li>
			<li><a href="javascript:void(0)" title="2" class="two-stars">2</a></li>
			<li><a href="javascript:void(0)" title="3" class="three-stars">3</a></li>
			<li><a href="javascript:void(0)" title="4" class="four-stars">4</a></li>
			<li><a href="javascript:void(0)" title="5" class="five-stars">5</a></li>
		</ul>
		<div class="current-rating" id="showb"></div>
	</div>
	<div class="description"></div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
    var stepW = 24;
    $("#showb").css("width",24);
});
</script>

