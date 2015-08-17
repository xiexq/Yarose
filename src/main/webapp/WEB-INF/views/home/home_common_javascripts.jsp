<%@ page pageEncoding="UTF-8" %>
<script>
	var container=null;
	function _leftmenu_select_handle(){
		var t=$(this);
		var left=$( 'div .ui-layout-left' )
		$('li a.selected',left).removeClass('selected');
		t.addClass('selected');
		if(t.hasClass('crud')){
			container.crud('destroy').html('');
			var init=t.attr('init');
			if(init=='view'){
				container.crud({url:t.data('url'),action:init,actionTarget:t.attr('tar'),title:t.text()});
			}else if(init=='edit'){
				container.crud({url:t.data('url'),action:init,actionTarget:t.attr('tar'),title:t.text()});
			}else{
				container.crud({url:t.data('url')});
			}
		}
	}
	$(function() {
		$(document).ready(function(){
			var left=$( 'div .ui-layout-left' );
			left.accordion({collapsible:true,active:false});
			container=$('.ui-layout-right');
			$('li a',left).each(function(){
				var t=$(this);
				if(t.hasClass('crud')){
					t.data('url',t.attr('href'));
					t.attr('href','javascript:void(0);');
				}
			}).on('click',_leftmenu_select_handle);
			_jeeSetupAjax();/*类库方法来初始化ajax安装。*/
		});
	});
	function _account_admin(){
		container.crud('destroy').empty().crud({url:"${ctxPath }/home/admin/account",enableNextEdit:true});
	}
	function _access_admin(){
		container.crud('destroy').empty().crud({url:'${ctxPath}/home/admin/access',listSelectStyle:'none'});
	}
	</script>