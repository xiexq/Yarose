package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;

public class CourseServiceImpl extends DaoBasedServiceImpl<Course, Long> implements CourseService {

  @SuppressWarnings("unchecked")
  @Override
  public List<Course> listByShopId(Long ShopId) {
    return (List<Course>) this.getDao().executeQueryList("Course.listByShopId", QueryCmdType.QUERY_NAME, -1, -1, ShopId);
  }

}