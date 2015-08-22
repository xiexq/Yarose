package cn.com.yarose.utils;

import java.util.Calendar;
import java.util.Date;

public class Constants {

	public static String getFormatId(Long id) {
		return String.format("%07d", id);
	}

	// 字典的类别
	public static String DICT_TYPE_DANCE = "dance_type"; // 舞种
	public static String DICT_TYPE_TEACH_LEVEL = "teach_level";// 教师级别
	public static String DICT_TYPE_STU_LEVEL = "stu_level";// 学生等级

	// 角色
	public static String ROLE_SUPER = "SUPER"; // 超级管理员
	public static String ROLE_SHOP_MANAGER = "SHOP_MANAGER"; // 店长
	public static String ROLE_TEACHER = "TEACHER"; // 老师
	public static String ROLE_SALER = "SALER"; // 营销人员
	public static String ROLE_MEMBER = "MEMBER"; // 会员

	public static final int DICTIONARY_ACTIVE = 0;
	public static final int DICTIONARY_INACTIVE = 1;
	
	public static Date customBeginTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static Date customEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR,23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static Date customDateLinkTime(long time,Date date){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		
		Calendar other = Calendar.getInstance();
		other.setTime(date);
		c.set(Calendar.HOUR,other.get(Calendar.HOUR));
		c.set(Calendar.MINUTE,other.get(Calendar.MINUTE));
		return c.getTime();
	}
}
