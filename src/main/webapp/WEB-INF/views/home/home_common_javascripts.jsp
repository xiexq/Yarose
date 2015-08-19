<%@ page pageEncoding="UTF-8" %>
<%@ include file="../includes/tags.jsp"%>
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
		container.crud({url:"${ctxPath }/home/admin/account",enableNextEdit:true});
	}
	function _access_admin(){
		_clearContainer();
		container.crud({url:'${ctxPath}/home/admin/access',listSelectStyle:'none'});
	}
	
	function _dictionary_admin(){
		_clearContainer();
		container.crud({url:'${ctxPath}/home/admin/dictionary'});
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
	</script>