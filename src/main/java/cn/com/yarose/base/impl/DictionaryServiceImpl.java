package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;

public class DictionaryServiceImpl extends DaoBasedServiceImpl<Dictionary, Long> implements DictionaryService {

  @SuppressWarnings("unchecked")
  @Override
  public Dictionary findByName(String name) {
    List<Dictionary> dicList = (List<Dictionary>) getDao().executeQueryList("Dictionary.findByName", QueryCmdType.QUERY_NAME, -1, -1,name);
    if(dicList != null && dicList.size() > 0){
      return dicList.get(0);
    }
    return null;
  }

}