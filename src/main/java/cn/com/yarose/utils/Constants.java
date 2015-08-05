package cn.com.yarose.utils;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.eduedu.jee.entity.NameValueBean;

public class Constants {

	private static Collection<NameValueBean> Dictionarys;
	private static Collection<NameValueBean> teachLevelDicts;

	public static final int DICTIONARY_ACTIVE = 0;
	public static final int DICTIONARY_INACTIVE = 1;

	public synchronized static Collection<NameValueBean> getStatusDictionary() {
		if (Dictionarys == null) {
			Dictionarys = new ArrayList<NameValueBean>();
			Dictionarys.add(new NameValueBean("舞蹈", "1"));
			Dictionarys.add(new NameValueBean("其他", "0"));
		}
		return Dictionarys;
	}

	public synchronized static Collection<NameValueBean> getTeachLevelDicts() {
		if (teachLevelDicts == null) {
			teachLevelDicts = new ArrayList<NameValueBean>();
			teachLevelDicts.add(new NameValueBean("", "1"));
			teachLevelDicts.add(new NameValueBean("其他", "0"));
		}
		return teachLevelDicts;
	}

	public static String getDictionaryStatusName(Integer value) {
		Collection<NameValueBean> nvbs = getStatusDictionary();
		for (NameValueBean nvb : nvbs) {
			if (nvb.getId().equals(value + ""))
				return nvb.getName();
		}
		return "";
	}
}
