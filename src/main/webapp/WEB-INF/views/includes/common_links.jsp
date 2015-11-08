<%@ page pageEncoding="UTF-8" %>
<meta name="renderer" content="webkit" />
<link rel="shortcut icon" href="${staticResPath}/images/favicon.png" type="image/png"/>
<link href="${staticResPath}/styles/layouts/all/ui.layouts.min.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/green/normal/all/ui.themes.min.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/green/normal/qa/theme.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/layouts/layouts.css" rel="stylesheet" type="text/css"/>
<link href="${staticResPath}/styles/mobile/star.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${staticResPath}/scripts/jquery.all.min.js"></script>
<script type="text/javascript" src="${staticResPath}/scripts/ui-widgets/ui.widgets.all.min.js"></script>
<script type="text/javascript" src="${staticResPath}/scripts/jquery-ui-1.10.0/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<!-- if you want to enable rich text editor, this required.  -->
<script type="text/javascript" src="${staticResPath}/scripts/tinymce_3_5_8/jquery.tinymce.js"></script>
<script type="text/javascript" src="${staticResPath}/scripts/ui-widgets/ui.inline.window.min.js"></script>
<script type="text/javascript">
(function($){
	$.ui.crud.prototype.options.tinyMCEUrl='${staticResPath}/scripts/tinymce_3_5_8/';
	$.ui.upload.prototype.options.uploadSwf='${staticResPath}/scripts/ui-widgets/ui.upload.widget.swf';
})(jQuery);
</script>