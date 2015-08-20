<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/common_links.jsp"%>
<link href="${staticResPath}/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'/>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/jquery.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/moment.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/fullcalendar.min.js"></script>
<div class="ui-room" style="min-height:700px;">
	<div class="ui-search-box">
		<form>
			<fieldset>
				<input class="ui-search-input" type="text" name="keywords"/>
				<button class="ui-button-primary ui-action-search" type="submit">搜索</button>
			</fieldset>
		</form>
	</div>
	<div class="ui-room-question-list">
		<div class="ui-questions" style="margin: 10px 10px; padding: 0; font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif; font-size: 14px;">
			<div id='calendar' style="max-width: 900px;margin: 0 auto;"></div>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			defaultDate: new Date(),
			selectable: true,
			selectHelper: true,
			select: function(start, end) {
				var title = prompt('Event Title:');
				var eventData;
				if (title) {
					eventData = {
						title: title,
						start: start,
						end: end
					};
					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
				}
				$('#calendar').fullCalendar('unselect');
			},
			eventClick: function(event) {  // 定义了点击日历项的动作，这里将会调用jQueryUi的dialog显示日历项的内容  
				
			}, 
			
			editable: true,
			eventLimit: true // allow "more" link when too many events
		});
		
	});
	
	function _view(){
		var c=$('.ui-room'),list=$('.ui-questions',c),searchBox=$('.ui-search-box',c),searchInput=$('.ui-search-input',searchBox),
						listActions=[],params={_roomId:target},type=userType;
		list.crud({url:'${ctxPath}/home/teacher/manager/search'
					
				});
	}
</script>
	
</div>
