<%@ page pageEncoding="UTF-8" %>
<%@ include file="../includes/tags.jsp"%>
<link href="${staticResPath}/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'/>
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/moment.min.js"></script> 
<script type="text/javascript" src="${staticResPath}/fullcalendar/lib/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="${staticResPath}/fullcalendar/fullcalendar.min.js"></script>
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
			leftMenu=$( 'div .ui-layout-left' );
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
	
	function _account_admin(){
		_clearContainer();
		container.crud({url:"${ctxPath }/home/admin/account",enableNextEdit:true,
			onEdit:function(){
				$("input[name='accesses']").each(function(){
					if($(this).val()=='TEACHER'&&!$(this).prop('checked')){
						$(".ui-fieldset-teacher").hide();
					}
				});
				$("input[name='accesses']").click(function(){
					if($(this).val()=='TEACHER'){
						if($(this).prop('checked')){
							$(".ui-fieldset-teacher").toggle();
						}else{
							$(".ui-fieldset-teacher").hide();
						}
					}
				});
			}
		});
	}
	
	function _account_shop_admin(){
		_clearContainer();
		container.crud({url:"${ctxPath }/home/admin/account",params:{},enableNextEdit:true
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
		var mc=$('<div></div>'),svc=new StackViewController(container);
		svc.push(mc);
		mc.crud({url:'${ctxPath}/home/admin/shops',
		listItemActions:[
			{label:'管理授权',func:function(event){
				var data=event.data,li=data.li,id=li.data('id'),shopName=_crudHelper.getListItemFieldValue(li,'name'),
				c=$('<div></div>');
				svc.push(c);
				c.crud({
					url:'${ctxPath }/home/admin/shop/managers',
					params:{_shopId:id},
					title:shopName+' 管理授权',
					actions:[{label:'所有店铺',func:function(){svc.pop();}}],
				});
			},cssClass:'ui-action-manager'}
		]
		});
	}
	
	function _course_admin(){
		_clearContainer();
		var mc=$('<div></div>'),svc=new StackViewController(container);
		svc.push(mc);
		mc.crud({url:'${ctxPath}/home/admin/courses',
			onSelectBigField:function(event,data){
				var field=data.field,fieldName=field.name,dicUrl=field.dicUrl,callback=data.callback;
				if(fieldName=="dicId"){
					return _select_dic(dicUrl,callback);
				}else if(fieldName=="shopId"){
					return _select_shop(dicUrl,callback);
				}
			},
			listItemActions:[
			{label:'老师授权',func:function(event){
				var data=event.data,li=data.li,id=li.data('id'),courseName=_crudHelper.getListItemFieldValue(li,'name'),
				c=$('<div></div>');
				svc.push(c);
					c.crud({
						url:'${ctxPath }/home/admin/teacher/managers',
						params:{_courseId:id},
						title:courseName+' 管理授权',
						actions:[{label:'所有课程',func:function(){svc.pop();}}],
					});
				},cssClass:'ui-action-manager'}
			]
		});
	}
	
	function _select_shop(url,callback){
		var dc=$('<div></div>');
		dc.crud({
			url:url,listSelectStyle:'radio',showHeader:false
		}).dialog({
			title:'选择所属店鋪',width:920,height:600,
			buttons:{'确定':function(){
				var sel=dc.crud('selected');
				if(!!sel){
					var val=sel.val(),label=_crudHelper.getSelectedFieldValue(sel,'name');
					callback({value:val,label:label});
					dc.dialog('destroy').remove();
				}else{
					dc.crud('tipInfo','请选择所属店鋪','warn');
				}
			},
			'取消':function(){dc.dialog('close');}}
		});
		return false;
	}
	
	function _select_dic(url,callback){
		var dc=$('<div></div>');
		dc.crud({
			url:url,listSelectStyle:'radio',showHeader:false
		}).dialog({
			title:'选择所属舞种',width:920,height:600,
			buttons:{'确定':function(){
				var sel=dc.crud('selected');
				if(!!sel){
					var val=sel.val(),label=_crudHelper.getSelectedFieldValue(sel,'name');
					callback({value:val,label:label});
					dc.dialog('destroy').remove();
				}else{
					dc.crud('tipInfo','请选择所属舞种','warn');
				}
			},
			'取消':function(){dc.dialog('close');}}
		});
		return false;
	}
	
	function _course_grant(){
		container.crud({
			url:'${ctxPath}/home/admin/teacher/managers',
			action:'create',initShowSearchForm:true,
			onListSuccess:function(e,data){
				$('.ui-content',container).empty();
				$('.ui-content',container).html('<div id="calendar" style="max-width: 900px;margin: 0 auto;"></div>');
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
					//events:'${ctxPath }/home/admin/teacher/managers/select',
					events:function(start, end, timezone, callback) {//读取数据
				        $.ajax({
				            url:"${ctxPath }/home/admin/teacher/managers/select",
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
			            var div = $('<div/>');
			           	div.dialog({
							title: '添加课程',width:800,height:600
						}).crud({
							url:'${ctxPath }/home/admin/teacher/managers',
							action:'edit',
							onSaveSuccess:function(data){
								div.crud('tipInfo','保存成功！','pass');
							  	setTimeout(function(){div.dialog('destroy').remove();$('#calendar').crud('refreshList');},1500);
							  	return false;
							},
							onCancelEdit:function(){
								setTimeout(function(){div.dialog('destroy').remove();},1000);
								return false;
							}
						});
		        	},
		        	eventClick: function(calEvent, jsEvent, view) {//编辑日程   
			           var div = $('<div/>');
			           	div.dialog({
							title: '修改课程',width:800,height:600
						}).crud({
							url:'${ctxPath }/home/admin/teacher/managers',
							action:'edit',actionTarget:calEvent.id,
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
		        	 
				});
			}
		})
	}
	
	</script>
