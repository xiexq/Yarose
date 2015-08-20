<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/tags.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="app.title" /></title>
<%@ include file="../includes/common_links.jsp"%>
</head>
<body class="ui-embed-content">
	<div class="ui-container"></div>
	<script type="text/javascript">
	var container=null;
	function _clearContainer(tar){
		if(!tar){tar=container;}
		if(tar.hasClass('ui-crud-widget')){tar.crud('destroy');}
		tar.empty();
	}
	;(function(){
		try{document.documentElement.focus();}catch(e){}/*hack for ie9*/
		$(document).ready(function(){
			_jeeSetupAjax();/*类库方法来初始化ajax安装。*/
			container=$('.ui-container');
			_clearContainer();
		    var tree=$('<div></div>'),childrenList=$('<div></div>');
			SplitViewController(container,tree,childrenList);
			var opts={dataSource:"${ctxPath}/home/admin/dictCategory/tree",showRootNode:true,selectStyle:"none"},
					onItemClick:function(event,li){
						var id=li.data('_id'),label=li.data('_label');				
						childrenList.crud({url:"${ctxPath}/home/admin/dictionary",params:{'_type':id}});
					}
			};
			tree.tree(opts);
	    });
    })();
	</script>
</body>
</html>