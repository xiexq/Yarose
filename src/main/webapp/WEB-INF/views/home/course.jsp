<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/tags.jsp"%>
<div class="ui-room" style="min-height:700px;">
	<div class="ui-room-header">
		<h2>
			<span>
				<a href="javascript:void(0)" onclick="${func}()">
				<c:if test="${func eq '_myRooms' }">我的答疑室</c:if>
				<c:if test="${func eq '_freeAccessRooms' }">开放答疑室</c:if>
				<c:if test="${func eq '_my_managed_rooms' }">我管理的答疑室</c:if>
				<c:if test="${func eq '_my_teached_rooms' }">我辅导的答疑室</c:if>
				</a> 
			</span>
			<span>${room.name }</span> 
		</h2>
	</div>
	<div class="ui-search-box">
		<form>
			<fieldset>
				<input class="ui-search-input" type="text" name="keywords"/>
				<button class="ui-button-primary ui-action-search" type="submit">搜索</button>
			</fieldset>
		</form>
	</div>
	<div class="ui-room-question-list">
		<div class="ui-questions">
			<div id='calendar'></div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {

		$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			defaultDate: '2015-02-12',
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: [
				{
					title: 'All Day Event',
					start: '2015-02-01'
				},
				{
					title: 'Long Event',
					start: '2015-02-07',
					end: '2015-02-10'
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: '2015-02-09T16:00:00'
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: '2015-02-16T16:00:00'
				},
				{
					title: 'Conference',
					start: '2015-02-11',
					end: '2015-02-13'
				},
				{
					title: 'Meeting',
					start: '2015-02-12T10:30:00',
					end: '2015-02-12T12:30:00'
				},
				{
					title: 'Lunch',
					start: '2015-02-12T12:00:00'
				},
				{
					title: 'Meeting',
					start: '2015-02-12T14:30:00'
				},
				{
					title: 'Happy Hour',
					start: '2015-02-12T17:30:00'
				},
				{
					title: 'Dinner',
					start: '2015-02-12T20:00:00'
				},
				{
					title: 'Birthday Party',
					start: '2015-02-13T07:00:00'
				},
				{
					title: 'Click for Google',
					url: 'http://google.com/',
					start: '2015-02-28'
				}
			]
		});
	});
	</script>
</div>