package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface ShopManagerService extends BaseSearchService<ShopManager, Long> {

  List<ShopManager> listByShopId(Long shopId, int offset, int count);

  long countShopAllManagers(Long shopId);

}