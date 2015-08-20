package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface DictionaryService extends BaseSearchService<Dictionary, Long> {

	List<Dictionary> listByType(Integer type, Integer offset, Integer count);

	Dictionary findByName(int type, String name);

}
