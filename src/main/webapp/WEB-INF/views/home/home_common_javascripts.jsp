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
	
	function _add_hover(){
		$(".fc-day").hover(
				  function(){
				      $(this).addClass("calendar_hover");
				   },
				   function(){
				      $(this).removeClass("calendar_hover");
				   }
			);
			$(".fc-day.fc-today").hover(
				  function(){
					  $(this).removeClass("fc-today").addClass("calendar_hover");
				  },
				  function(){
				      $(this).removeClass("calendar_hover").addClass("fc-today");
				  }
			);
	}
	
	function _course_grant(){
		_clearContainer();
		container.crud({
			url:'${ctxPath}/home/admin/teacher/managers',initShowSearchForm:true,
			onListSuccess:function(e,data){
				$('.ui-action-listall.ui-button').hide();
				$('.ui-action-reset.ui-button').hide();
				$('.ui-content',container).empty();
				$('.ui-content',container).html('<div id="calendar" style="max-width: 900px;margin: 0 auto;"></div>');
				$('#calendar').fullCalendar({
					editable:true,eventLimit:true,firstDay:1,header:{right:'prev,next',center:'title',left:'today'},
			        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],    
		            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		            buttonText: {today:'今天',month:'月',week:'周',day:'日',prev:'上一月',next:'下一月'},
					events:function(start, end, timezone, callback) {
						var shopId = $("select[name='searchShop']",container).val();
						_add_hover();
						$.get("${ctxPath }/home/admin/teacher/managers/render/event",{'_shop':shopId},function(data){
			                if(data&&data.success&&data.courses&&data.courses.length>0){
			                	$('#calendar').fullCalendar('removeEvents');
			                	callback(data.courses);
			                }
						});
				    },dayClick:function(date, allDay, jsEvent, view) {
				    	_edit_event_day(date);
		        	},eventClick: function(calEvent, jsEvent, view) {
		        		_edit_event_day(calEvent.start);
			        }
				});
			}
		});
	}
	
	function _edit_event_day(date){
		var shopId = $("select[name='searchShop']",container).val(),div=$('<div/>');
        div.dialog({
			title:formatDate(new Date(date))+'课程安排',width:800,height:600
		}).crud({
			url:'${ctxPath }/home/admin/teacher/managers',
			showSubviewTitle:false,showHeader:false,searchable:false,params:{'_shop':shopId,'_date':date+""},
			onFieldValueChange:function(data,field){
				if(field.name=='teacher'){
					var tid=$(this).crud("getEditFieldVal","teacher");
					if(!!tid){
						$.get("${ctxPath }/home/admin/teacher/managers/courseFee/"+tid,function(data){
							if(!!data){
								$("input[name='courseFee']").val(data);
							}
						});
					}else{
						$("input[name='courseFee']").val('0');
					}
				}
			},onSaveSuccess:function(event, data){
				$.get("${ctxPath }/home/admin/teacher/managers/"+data.entityID,function(d){
					if(d&&d.success){
						if(!data.isCreate){
							$('#calendar').fullCalendar('removeEvents', data.entityID);
						}
						$('#calendar').fullCalendar('addEventSource', d.courses);
					}
				});
			}
		});
	}
	
	function formatDate(now){
        var year=now.getFullYear();
        var month=now.getMonth()+1;
        var day=now.getDate();
        return year+"年"+month+"月"+day+"日";
    }
	
	//会员卡管理
	function _member_card_admin(){
		_clearContainer();
		container.crud({
			url:'${ctxPath}/home/shopmanager/membercard'
		});
	}
	
	function _course_appointment(){
		_clearContainer();
		var mc=$('<div></div>'),svc=new StackViewController(container);
		svc.push(mc);
		var tab=$('<div><ul><li><a href="#uncheck">未核销</a></li><li><a href="#checked">已核销</a></li></ul></div>'),
		uncheck=$('<div id="uncheck"></div>'),checked=$('<div id="checked"></div>');
		tab.append(uncheck).append(checked);
		uncheck.crud({url:'${ctxPath}/home/shop/manager/appointment',params:{_act:'uncheck'},showHeader:false,
			listItemActions:[{label:'核销',func:function(event){_appointment_audit(event,svc);},cssClass:'ui-action-statistic'}]
			 				
		});
		checked.crud({url:'${ctxPath}/home/shop/manager/appointment',params:{_act:'checked'},editable:false,createable:false,deleteable:false,showHeader:false
		});
		container.append(tab);
		tab.tabs();
	}
	
	function _appointment_audit(event,svc){
		var li=event.data.li,id=li.data('id');
		if(id){
			var dialog=$('<div></div>');
			dialog.dialog({title:'填写处理信息',width:600,height:400,close:function(){$(this).remove();}});
			dialog.crud({
				url:'${ctxPath }/home/shop/manager/appointment/',
				params:{_type:'check'},title:'预约核销',action:'edit',actionTarget:id,
				onSaveSuccess:function(event,target){
					var t=$(this);
					t.crud('tipInfo','核销成功！','pass');
					setTimeout(function(){
						c.crud('refreshList');
					},1500);
					return false;
				},
				onCancelEdit:function(){
					var t=$(this);
					setTimeout(function(){t.dialog('close');},10);
				}
			});
		}
	}
	
	function _evaluation_management(){
		_clearContainer();
		container.crud({
			url:'${ctxPath}/home/admin/teacher/managers',params:{_isEval:'isEval'},editable:false,deleteable:false,createable:false,listSelectStyle:'none',
			listItemActions:[{label:'评价',func:function(event){_user_valuation(event,container);},cssClass:'ui-action-statistic'}]
		});
	}
	
	function _user_valuation(event,container){
		var li=event.data.li,id=li.data('id'),courseName=_crudHelper.getListItemFieldValue(li,'courseName');
		var dialog=$('<div/>'),win=$(window),ww=win.width(),wh=win.height();
		dialog.dialog({
			title:courseName+'的评价',width:ww-100,height:wh-100,close:function(){$(this).dialog('destroy').remove();}
		}).crud({url:'${ctxPath}/home/shop/evaluation/manager',params:{_id:id},cssClass:'room-score-rule-edit',shoeHeader:false,
			onSaveSuccess:function(){
				var tt=$(this);
				tt.crud('tipInfo','保存成功！','pass',null,1000);
				setTimeout(function(){
					tt.crud("refreshList");
				},1000);
			},
			onCancelEdit:function(){
				var tt=$(this);
				tt.crud("refreshList");
			}
		});
	}
</script>
