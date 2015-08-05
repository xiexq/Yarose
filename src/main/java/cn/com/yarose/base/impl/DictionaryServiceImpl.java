package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;

public class DictionaryServiceImpl extends
		DaoBasedServiceImpl<Dictionary, Long> implements DictionaryService {

	@SuppressWarnings("unchecked")
	@Override
	public Dictionary findByName(int type, String name) {
		List<Dictionary> dicList = (List<Dictionary>) getDao()
				.executeQueryList("Dictionary.findByName",
						QueryCmdType.QUERY_NAME, -1, -1, type, name);
		if (dicList != null && dicList.size() > 0) {
			return dicList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> listByType(Long type) {
		return (List<Dictionary>) this.getDao().executeQueryList("Dictionary.listByType",
				QueryCmdType.QUERY_NAME, -1, -1, type);
	}
}