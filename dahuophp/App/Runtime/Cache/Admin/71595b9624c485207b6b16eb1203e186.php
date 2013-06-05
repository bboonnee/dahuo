<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>『ThinkPHP管理平台』By ThinkPHP <?php echo (THINK_VERSION); ?></title>
<link rel="stylesheet" type="text/css" href="__PUBLIC__/Css/blue.css" />
<script type="text/javascript" src="__PUBLIC__/Js/Base.js"></script>
<script type="text/javascript" src="__PUBLIC__/Js/prototype.js"></script>
<script type="text/javascript" src="__PUBLIC__/Js/mootools.js"></script>
<script type="text/javascript" src="__PUBLIC__/Js/Think/ThinkAjax.js"></script>
<script type="text/javascript" src="__PUBLIC__/Js/Form/CheckForm.js"></script>
<script type="text/javascript" src="__PUBLIC__/Js/common.js"></script>
<script language="JavaScript">
<!--
//指定当前组模块URL地址 
var URL = '__URL__';
var APP	 =	 '__APP__';
var PUBLIC = '__PUBLIC__';
//-->
</script>
</head>

<body>
<div id="main" class="main" >
<div class="content">
<div class="title">添加数据 [ <a href="__URL__">返回列表</a> ]</div>
<div id="result" class="result none"></div>
<script type="text/javascript" charset="utf-8" src="__PUBLIC__/Js/editor/kindeditor.js"></script>
<script type="text/javascript">
    KE.show({
        id : 'content_1' //TEXTAREA输入框的ID
   });
</script>
<form method='post' id="form1" name="form1" action="__URL__/insert"  enctype="multipart/form-data">
<table cellpadding=3 cellspacing=3 >
<tr>
	<td class="tRight" width="10%">标题：</td>
	<td class="tLeft" ><input type="text" class="huge bLeftRequire" name="title"></td>
</tr>
<tr>
	<td class="tRight tTop">内容：</td>
	<td class="tLeft"><textarea id="content_1" name="content" style="width:700px;height:300px;"></textarea>	</td>
</tr>

<tr>
	<td></td>
	<td class="center"><div style="width:85%;margin:5px">
	<input type="hidden" name="status" value="1">
    <input type="hidden" name="user_id" value="<?php echo $_SESSION[C('USER_AUTH_KEY')] ?>">
	<input type="submit" value="保 存"  class="button small"> <input type="reset" class="button small" onclick="resetEditor()" value="重 置" >
	</div></td>
</tr>
</form>
</table>
</div>
</div>