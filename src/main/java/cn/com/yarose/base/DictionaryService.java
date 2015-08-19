package cn.com.yarose.base;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface DictionaryService extends BaseSearchService<Dictionary, Long> {

  Dictionary findByName(String name);

}