package cn.com.yarose.base;

import java.util.Date;
import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface CourseTeacherService extends BaseSearchService<CourseTeacher, Long> {

	List<CourseTeacher> listByCourseId(Long courseId, int offset, int count);

	long countTeacherAllManagers(Long courseId);

	List<CourseTeacher> listByShopId(Long shopoId);

	List<CourseTeacher> listByShopAndDay(Long shopId, Date beginTime, Date endTime);

	long countByShopAndDay(Long shopId, Date beginTime, Date endTime);

	List<CourseTeacher> listByBeginTime(Date date);

}