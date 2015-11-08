package cn.com.yarose.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.util.StringUtils;

import cn.com.eduedu.jee.entity.NameValueBean;

public class Constants {

	public static String getFormatId(Long id, Integer num) {
		return String.format("%0" + num + "d", id);
	}

	// 字典的类别
	public static String DICT_TYPE_DANCE = "dance_type"; // 舞种
	public static String DICT_TYPE_TEACH_LEVEL = "teach_level";// 教师级别
	public static String DICT_TYPE_STU_LEVEL = "stu_level";// 学生等级
	public static String DICT_TYPE_MEMBER_CARD_TYPE = "member_card_type";// 会员卡类型
	public static String DICT_TYPE_AGE_GROUP = "age_group";// 年龄段
	public static String DICT_TYPE_AREA = "area";// 年龄段

	// 角色
	public static String ROLE_SUPER = "SUPER"; // 超级管理员
	public static String ROLE_SHOP_MANAGER = "SHOP_MANAGER"; // 店长
	public static String ROLE_TEACHER = "TEACHER"; // 老师
	public static String ROLE_SALER = "SALER"; // 营销人员
	public static String ROLE_MEMBER = "MEMBER"; // 会员

	public static final int DICTIONARY_ACTIVE = 0;
	public static final int DICTIONARY_INACTIVE = 1;

	// 是否核销
	public static final int APPOINTMENT_UNCHECKED = 0;
	public static final int APPOINTMENT_CHECKED = 1;

	// 评价类型
	public static final int EVALUATION_TYPE_USER = 0;
	public static final int EVALUATION_TYPE_SHOP = 1;

	public static final int COURSE_TEACHER_STATUS_APPOINTMENT = 0;
	public static final int COURSE_TEACHER_STATUS_UNLEARN = 1;
	public static final int COURSE_TEACHER_STATUS_LEARNED = 2;

	private static Collection<NameValueBean> evaluationTypes;

	private static Collection<NameValueBean> statusDicts;

	public synchronized static Collection<NameValueBean> getevaluationTypes() {
		if (evaluationTypes == null) {
			evaluationTypes = new ArrayList<NameValueBean>();
			evaluationTypes.add(new NameValueBean("学生", EVALUATION_TYPE_USER
					+ ""));
			evaluationTypes.add(new NameValueBean("店长", EVALUATION_TYPE_SHOP
					+ ""));
		}
		return evaluationTypes;
	}

	public static String getevaluationType(Integer value) {
		Collection<NameValueBean> nvbs = getevaluationTypes();
		for (NameValueBean nvb : nvbs) {
			if (nvb.getId().equals(value + ""))
				return nvb.getName();
		}
		return "";
	}

	public static Date customBeginTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date customEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date customDateLinkTime(long time, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Calendar other = Calendar.getInstance();
		other.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, other.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, other.get(Calendar.MINUTE));
		return c.getTime();
	}

	public synchronized static Collection<NameValueBean> getStatusDictionary() {
		if (statusDicts == null) {
			statusDicts = new ArrayList<NameValueBean>();
			statusDicts.add(new NameValueBean("已上",
					COURSE_TEACHER_STATUS_LEARNED + ""));
			statusDicts.add(new NameValueBean("预约中",
					COURSE_TEACHER_STATUS_APPOINTMENT + ""));
			statusDicts.add(new NameValueBean("未上",
					COURSE_TEACHER_STATUS_UNLEARN + ""));
		}
		return statusDicts;
	}

	public static String getStatusDictionaryName(Integer value) {
		Collection<NameValueBean> nvbs = getStatusDictionary();
		for (NameValueBean nvb : nvbs) {
			if (nvb.getId().equals(value + ""))
				return nvb.getName();
		}
		return "";
	}

	/**
	 * 隐藏控制显示名称,以*号代替,只显示后面4位
	 * 
	 * @return
	 */
	public static String getHideName(String accountName) {
		if (StringUtils.hasText(accountName) && accountName.length() >= 4) {
			int length = accountName.length();
			// 取后面4位
			accountName = accountName.substring(length - 4, length);
			// 前面的位数用*号代替
			for (int i = 0; i < length - 4; i++) {
				accountName = "*" + accountName;
			}
		}
		return accountName;
	}
}
