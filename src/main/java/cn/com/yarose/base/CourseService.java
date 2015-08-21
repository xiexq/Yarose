package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface CourseService extends BaseSearchService<Course, Long> {

  List<Course> listByShopId(Long ShopId);

}