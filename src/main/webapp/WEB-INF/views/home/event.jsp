<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title></title>
	</head>
	<body>
		<div class="fancy">   
		    <h3>新建事件</h3>   
		    <form id="add_form" action="do.php" method="post">   
		    	<input type="hidden" name="action" value="add">   
		    	<p>日程内容：<input type="text" class="input" name="event" id="event" style="width:320px"   
		 			placeholder="记录你将要做的一件事..."></p>   
		    	<p>开始时间：<input type="text" class="input datepicker" name="startdate" id="startdate"   
		 			value="<?php echo $_GET['date'];?>">   
			    	<span id="sel_start" style="display:none">
				    	<select name="s_hour">   
				        	<option value="00">00</option>    
				   		</select>:   
				    	<select name="s_minute">   
				        	<option value="00" selected>00</option>    
				    	</select>   
			    	</span>   
		    	</p>   
		    	<p id="p_endtime" style="display:none">结束时间：<input type="text" class="input datepicker"    
					name="enddate" id="enddate" value="<?php echo $_GET['date'];?>">   
			    	<span id="sel_end" style="display:none">
			    		<select name="e_hour">   
			        		<option value="00">00</option>   
			    		</select>: 	  
			    		<select name="e_minute">   
			        		<option value="00" selected>00</option>   
			    		</select>   
			    	</span>   
		    	</p>   
		    	<div class="sub_btn"><span class="del"><input type="button" class="btn btn_del"   
		  			id="del_event" value="删除"></span>   
		    		<input type="submit" class="btn btn_ok" value="确定"> <input type="button"    
					class="btn btn_cancel" value="取消" onClick="$.fancybox.close()"></div>   
		    </form>   
		</div>  
	</body>
</html>
