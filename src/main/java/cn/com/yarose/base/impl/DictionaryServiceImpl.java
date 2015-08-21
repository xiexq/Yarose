package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;

public class DictionaryServiceImpl extends
		DaoBasedServiceImpl<Dictionary, Long> implements DictionaryService {

	@Override
	public Dictionary findByName(int type, String name) {
		return (Dictionary) getDao().executeQueryUnique(
				"Dictionary.findByName", QueryCmdType.QUERY_NAME, type, name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> listByType(Integer type, Integer offset,
			Integer count) {
		return (List<Dictionary>) this.getDao().executeQueryList(
				"Dictionary.listByType", QueryCmdType.QUERY_NAME, offset,
				count, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> listByTypeCode(String typeCode, int offset, int count) {
		return (List<Dictionary>) this.getDao().executeQueryList(
				"Dictionary.listByTypeCode", QueryCmdType.QUERY_NAME, offset,
				count, typeCode);
	}
}
