package cn.com.yarose.base;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface TeacherManagerService extends BaseSearchService<TeacherManager, Long> {

  List<TeacherManager> listByCourseId(Long courseId, int offset, int count);

  long countTeacherAllManagers(Long courseId);

  List<TeacherManager> listByShopId(Long shopoId);

}