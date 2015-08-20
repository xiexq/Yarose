package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface DictionaryService extends BaseSearchService<Dictionary, Long> {

	List<Dictionary> listByType(Integer type, Integer offset, Integer count);

	Dictionary findByName(int type, String name);

	/**
	 * 根据类别编码查询
	 * 
	 * @param typeCode
	 * @param offset
	 * @param count
	 * @return
	 */
	List<Dictionary> listByTypeCode(String typeCode, int offset, int count);

}
