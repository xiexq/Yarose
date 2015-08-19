package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.ShopManager;
import cn.com.yarose.base.ShopManagerService;

public class ShopManagerServiceImpl extends DaoBasedServiceImpl<ShopManager, Long> implements ShopManagerService {

  @SuppressWarnings("unchecked")
  @Override
  public List<ShopManager> listByShopId(Long shopId, int offset, int count) {
    return (List<ShopManager>) this.getDao().executeQueryList(
      "ShopManager.listByShopId", QueryCmdType.QUERY_NAME, offset,
      count, shopId);
  }

  @Override
  public long countShopAllManagers(Long shopId) {
    Long count = (Long) this.getDao().executeQueryUnique(
      "ShopManager.countShopAllManagers", QueryCmdType.QUERY_NAME,
      shopId);
    return count.intValue();
  }

}