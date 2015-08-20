<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/common_links.jsp"%>
<link href="${staticResPath}/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'/>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/jquery.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/moment.min.js"></script> 
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="${staticResPath}/script/jquery.fancybox.pack.js"></script>
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
			//theme:true,//是否显示主题
			weekNumbers:true,//是否显示周次
			defaultDate: new Date(),
			
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events:function(start, end, timezone, callback) {//读取数据
		        $.ajax({
		            url:"${ctxPath }/home/course/event/select",
		            cache:false,
		            success:function(doc) {
		                eval("var j=" + doc);
		                var events = [];
		                var info = j.eventinfo;
		                for (var i = 0; i < info.length; i++) {
		                    var ev = info[i];
		                    var title = ev.title;
		                    var evtstart = new Date(Date.parse(ev.start));
		                    var evtend = new Date(Date.parse(ev.end));
		                    events.push({
		                        title:title,
		                        start:evtstart,
		                        end:evtend,
		                        id:1
		                    });
		                }
		                callback(events);
		            },
		            error:function() {
		                alert('sdf')
		            }
		        })
    		},
    		dayClick: function(date, allDay, jsEvent, view) {//添加数据
	            //var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');//格式化日期   
	            $.fancybox({//调用fancybox弹出层   
	                'type':'ajax',   
	                'href':'${ctxPath }/home/course/event/add/'+date   
	            });   
        	}   
    		
		});
		
	});
</script>
	
</div>
