package cn.com.yarose.base;

import java.util.Date;
import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface CourseTeacherService extends
		BaseSearchService<CourseTeacher, Long> {

	List<CourseTeacher> listByCourseId(Long courseId, int offset, int count);

	long countTeacherAllManagers(Long courseId);

	List<CourseTeacher> listByShopId(Long shopoId);

	List<CourseTeacher> listByShopAndDay(Long shopId, Date beginTime,
			Date endTime);

	long countByShopAndDay(Long shopId, Date beginTime, Date endTime);

	List<CourseTeacher> listByBeginTime(Date date);

	List<CourseTeacher> listByEndTime(Date date);

	long countByEndTime(Date date);

	List<CourseTeacher> listByTeacher(Long accountId, int offset, int count);

	long countByTeacher(Long accountId);

	/**
	 * 列出所有还未上的课程
	 * @param offset
	 * @param count
	 * @return
	 */
	List<CourseTeacher> listAllActive(int offset, int count);
}