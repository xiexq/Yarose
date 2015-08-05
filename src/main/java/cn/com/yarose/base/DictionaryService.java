package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface DictionaryService extends BaseSearchService<Dictionary, Long> {

  Dictionary findByName(int type, String name);

List<Dictionary> listByType(Long type);

}