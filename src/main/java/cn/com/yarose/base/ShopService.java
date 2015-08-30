package cn.com.yarose.base;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface ShopService extends BaseSearchService<Shop, Long> {

	Shop finByName(String name);

}