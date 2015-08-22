<%@ page pageEncoding="UTF-8" %>
<%@ include file="../includes/tags.jsp"%>
<link href="${staticResPath}/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'/>
<style>
.calendar_hover{background:#6DCE14;cursor:pointer;}
</style>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/moment.min.js"></script> 
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/fullcalendar.js"></script>
<script type="text/javascript" src="${staticResPath}/script/jquery.fancybox.pack.js"></script>
<script>
	var container=null,leftMenu=null,_inlineWindow=null,lrResizeLayout,preva;
	function _clearContainer(tar){
		if(!tar){tar=container;}
		if(tar.hasClass('ui-crud-widget')){tar.crud('destroy');}
		if(tar.hasClass('ui-report-widget')){tar.report('destroy');}
		tar.empty();
	}
	function _leftmenu_select_handle(){
		var t=$(this),left=leftMenu,func=t.data('func'),idx=t.data('idx');
		var a=t.attr('href');
		if(a==preva){return;}
		preva=a;
		$('li a.selected',left).removeClass('selected');
		t.addClass('selected');
		if(t.hasClass('crud')){
			_clearContainer();
			var init=t.attr('init');
			if(init=='view'){
				container.crud({url:t.data('url'),action:init,actionTarget:t.attr('tar'),title:t.text()});
			}else if(init=='edit'){
				container.crud({url:t.data('url'),action:init,actionTarget:t.attr('tar'),title:t.text()});
			}else{
				container.crud({url:t.data('url')});
			}
		}
		setTimeout(function(){$(document).scrollTop(0);},10);
		if(func){eval(func);}
	}
	function _goHash(hash){
		if(!hash){return false;}
		var node=$(hash,leftMenu);
		if(node&&node.length>0){
			node.trigger('click');
			var selectIdx=node.data('idx');
			if(selectIdx||selectIdx==0){leftMenu.accordion('option','active',selectIdx);}
			$(document).scrollTop(0);
			return true;
		}else{return false;}
	}
	$(function() {
		function onHistory(){
			var anchor=window.location.hash;
			_goHash(anchor);
		}
		$(document).ready(function(){
			leftMenu=$( 'div .ui-layout-left');
			var left=leftMenu;
			left.accordion({collapsible:true,active:false,heightStyle:'content'});
			container=$('.ui-layout-right'),idCounter=0,accordIdx=0;
			$('ul',left).each(function(){
				$('li a',$(this)).each(function(){
					var t=$(this),id=t.prop('id'),href=t.prop('href');
					t.data('idx',accordIdx);
					if(!id){id='sub_menu_item_'+idCounter;t.prop('id',id);idCounter++;}
					if(href){t.data('url',href);}
					t.prop('href','#'+id);
					var idx=href.indexOf('javascript:');
					if(idx==0){
						var f=href.substring('javascript:'.length);
						t.data('func',f);
					}
				}).on('click',_leftmenu_select_handle);
				accordIdx++;
			});
			_jeeSetupAjax();/*类库方法来初始化ajax安装。*/
			lrResizeLayout=new LRAutoLayout(left,container);/*让界面支持自动调整高度*/
			_inlineWindow=new InlineWindowFactory();
			/*获得当前的链接锚点*/
			var anchor=window.location.hash;
			if(!_goHash(anchor)){
				if(!_goHash('.start_node')){
					_goHash('ul li a:first');
				};
			}
			$(window).on('hashchange', onHistory);
		});
	});
	
	function _account_admin(isShop){
		_clearContainer();
		container.crud({url:"${ctxPath }/home/admin/account",enableNextEdit:true,params:{'_shop':isShop},
			onEdit:function(){
				$("input[name='accesses']").each(function(){
					if($(this).val()=='TEACHER'&&!$(this).prop('checked')){
						$(".ui-fieldset-teacher").hide();
					}
					if($(this).val()=='MEMBER'&&!$(this).prop('checked')){
						$(".ui-fieldset-member").hide();
					}
				});
				$("input[name='accesses']").click(function(){
					if($(this).val()=='TEACHER'){
						$(".ui-fieldset-teacher").toggle();
					}
					if($(this).val()=='MEMBER'){
						$(".ui-fieldset-member").toggle();
					}
				});
			}
		});
	}
	
	function _access_admin(){
		_clearContainer();
		container.crud({url:'${ctxPath}/home/admin/access',listSelectStyle:'none'
		});
	}
	
	function _dictionary_admin(){
		_clearContainer();
	    var tree=$('<div></div>'),childrenList=$('<div></div>');
		SplitViewController(container,tree,childrenList);
		var opts={dataSource:"${ctxPath}/home/admin/dictCategory/tree",showRootNode:true,selectStyle:"none",
				onItemClick:function(event,li){
					var id=li.data('_id'),label=li.data('_label');
					childrenList.crud({url:"${ctxPath}/home/admin/dictionary",title:label,params:{'_type':id}});
				}
		}
		tree.tree(opts);
		childrenList.crud({url:"${ctxPath}/home/admin/dictionary",title:'根目录',params:{'_type':0}});
	}
	
	function _shop_admin(){
		_clearContainer();
		container.crud({url:'${ctxPath}/home/admin/shops'});
	}
	
	function _course_admin(){
		_clearContainer();
		container.crud({url:'${ctxPath}/home/admin/courses'});
	}
	
	function _course_grant(){
		var editParams = {'_shop':''};
		container.crud({
			url:'${ctxPath}/home/admin/teacher/managers',
			action:'create',initShowSearchForm:true,params:editParams,
			onListSuccess:function(e,data){
				$('.ui-action-listall.ui-button').hide();
				$('.ui-action-reset.ui-button').hide();
				$('.ui-content',container).empty();
				$('.ui-content',container).html('<div id="calendar" style="max-width: 900px;margin: 0 auto;"></div>');
				$('#calendar').fullCalendar({
					defaultDate: new Date(),editable: true,eventLimit: true,
					header: {left:'prev,next today',center:'title',right:'month,agendaWeek,agendaDay'},
			        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],    
		            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],    
		            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		            buttonText: {today:'今天',month:'月',week:'周',day:'日',prev:'上一月',next:'下一月'},
					events:function(start, end, timezone, callback) {//读取数据
				        $.ajax({
				            url:"${ctxPath }/home/admin/teacher/managers/select",
				            cache:false,
				            success:function(doc) {
				            	eval("var j=" + doc);
				                var event = [];
				                var info = j.eventinfo;
				                for (var i = 0; i < info.length; i++) {
				                    var ev = info[i];
				                    var title = ev.title;
				                    var evtstart = new Date(Date.parse(ev.start));
				                    var evtend = new Date(Date.parse(ev.end));
				                    event.push({
				                        title:title,
				                        start:evtstart,
				                        end:evtend,
				                        id:ev.id
				                    });
				                }
				                callback(event);
						    }
						})
				    },
		    		dayClick: function(date, allDay, jsEvent, view) {//添加数据
		    			var shopId = $("select[name='searchShop']",container).val();
						editParams._shop = shopId,editParams._date=date+"";
						var div=$('<div/>');
			            div.dialog({
							title:'课程安排',width:800,height:600
						}).crud({
							url:'${ctxPath }/home/admin/teacher/managers',
							showSubviewTitle:false,showHeader:false,params:editParams,searchable:false,
							onStartEdit:function(){
								var c=$(this);
								$("input[name='__date_helper']").attr("disabled","disabled");
							},
							onSaveSuccess:function(data){
								$(this).crud('tipInfo','保存成功！','pass');
								$(this).crud('refreshList');
							}
						});
		        	}
		        	/* eventClick: function(calEvent, jsEvent, view) {//编辑日程   
			           var div = $('<div/>');
			           	div.dialog({
							title: '课程',width:800,height:600
						}).crud({
							url:'${ctxPath }/home/admin/teacher/managers',
							action:'edit',actionTarget:calEvent.id,showSubviewTitle:false,showHeader:false,
							onSaveSuccess:function(data){
								div.crud('tipInfo','修改成功！','pass');
							  	setTimeout(function(){div.dialog('destroy').remove();_course_grant();},1500);
							  	return false;
							},
							onCancelEdit:function(){
								setTimeout(function(){div.dialog('destroy').remove();},1000);
								return false;
							}
						});   
			        }
		        	*/
				}); 
				$(".fc-day.fc-widget-content").hover(
					  function(){
					      $(this).addClass("calendar_hover");
					   },
					   function(){
					      $(this).removeClass("calendar_hover");
					   }
				);
				$(".fc-day.fc-widget-content.fc-today").hover(
					  function(){
						  $(this).removeClass("fc-today").addClass("calendar_hover");
					  },
					  function(){
					      $(this).removeClass("calendar_hover").addClass("fc-today");
					  }
				);
			},
			onSearchSubmit:function(e,data){
				var shopId = $("select[name='searchShop']",container).val();
				editParams._shop = shopId;
			}
		})
	}
	
	</script>
