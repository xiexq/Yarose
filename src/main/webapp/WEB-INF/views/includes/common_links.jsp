<%@ page pageEncoding="UTF-8" %>
<link rel="shortcut icon" href="${staticResPath}/images/favicon.png" type="image/png"/>
<link href="${staticResPath}/styles/${theme }/normal/jquery-ui-1.8.16.custom.min.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/${theme }/normal/ui.widgets.all.min.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/${theme}/normal/layouts.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${staticResPath}/scripts/jquery.all.min.js"></script>
<script type="text/javascript" src="${staticResPath}/scripts/ui-widgets/ui.widgets.all.min.js"></script>
<!-- if you want to enable rich text editor, this required.  -->
<script type="text/javascript" src="${ctxPath}/statics/scripts/tinymce_2_4_8/jquery.tinymce.js"></script>
<script type="text/javascript">
(function($){
	$.ui.crud.prototype.options.tinyMCEUrl='${ctxPath}/statics/scripts/tinymce_2_4_8/';
	$.ui.upload.prototype.options.uploadSwf='${ctxPath}/statics/scripts/ui-widgets/ui.upload.widget.swf';
})(jQuery);
</script>