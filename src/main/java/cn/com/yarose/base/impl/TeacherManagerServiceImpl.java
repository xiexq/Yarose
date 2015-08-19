package cn.com.yarose.base.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;

public class TeacherManagerServiceImpl extends DaoBasedServiceImpl<TeacherManager, Long> implements TeacherManagerService {

  @SuppressWarnings("unchecked")
  @Override
  public List<TeacherManager> listByCourseId(Long courseId, int offset, int count) {
    return (List<TeacherManager>) this.getDao().executeQueryList(
      "TeacherManager.listByCourseId", QueryCmdType.QUERY_NAME, offset,
      count, courseId);
  }

  @Override
  public long countTeacherAllManagers(Long courseId) {
    Long count = (Long) this.getDao().executeQueryUnique(
      "TeacherManager.countTeacherAllManagers", QueryCmdType.QUERY_NAME,
      courseId);
    return count.intValue();
  }

}