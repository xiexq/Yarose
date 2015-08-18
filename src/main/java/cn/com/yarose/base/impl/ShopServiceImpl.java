package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;

public class ShopServiceImpl extends DaoBasedServiceImpl<Shop, Long> implements ShopService {

  @SuppressWarnings("unchecked")
  @Override
  public Shop finByName(String name) {
    List<Shop> shopList = (List<Shop>) this.getDao().executeQueryList("Shop.findByName", QueryCmdType.QUERY_NAME, -1, -1, name);
    if(shopList != null && shopList.size() > 0){
      return shopList.get(0);
    }
    return null;
  }

}