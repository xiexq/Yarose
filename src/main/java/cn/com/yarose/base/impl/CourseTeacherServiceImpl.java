package cn.com.yarose.base.impl;

import java.util.Date;
import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;

public class CourseTeacherServiceImpl extends
		DaoBasedServiceImpl<CourseTeacher, Long> implements
		CourseTeacherService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByCourseId(Long courseId, int offset,
			int count) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listByCourseId", QueryCmdType.QUERY_NAME,
				offset, count, courseId);
	}

	@Override
	public long countTeacherAllManagers(Long courseId) {
		Long count = (Long) this.getDao().executeQueryUnique(
				"CourseTeacher.countTeacherAllManagers",
				QueryCmdType.QUERY_NAME, courseId);
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByShopId(Long shopId) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listByShopId", QueryCmdType.QUERY_NAME, -1, -1,
				shopId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByShopAndDay(Long shopId, Date beginTime,
			Date endTime) {
		List<CourseTeacher> tmList = (List<CourseTeacher>) this.getDao()
				.executeQueryList("CourseTeacher.listByShopAndDay",
						QueryCmdType.QUERY_NAME, -1, -1, shopId, beginTime,
						endTime);
		return tmList;
	}

	@Override
	public long countByShopAndDay(Long shopId, Date beginTime, Date endTime) {
		return (Long) this.getDao().executeQueryUnique(
				"CourseTeacher.countByShopAndDay", QueryCmdType.QUERY_NAME,
				shopId, beginTime, endTime);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByBeginTime(Date date) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listByBeginTime", QueryCmdType.QUERY_NAME, -1,
				-1, date);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByEndTime(Date date) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listByEndTime", QueryCmdType.QUERY_NAME, -1, -1,
				date);
	}

	@Override
	public long countByEndTime(Date date) {
		Long count = (Long) this.getDao().executeQueryUnique(
				"CourseTeacher.countByEndTime", QueryCmdType.QUERY_NAME, date);
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listByTeacher(Long accountId, int offset,
			int count) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listByTeacher", QueryCmdType.QUERY_NAME, offset,
				count, accountId);
	}

	@Override
	public long countByTeacher(Long accountId) {
		return (Long) this.getDao().executeQueryUnique(
				"CourseTeacher.countByTeacher", QueryCmdType.QUERY_NAME,
				accountId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeacher> listAllActive(int offset, int count) {
		return (List<CourseTeacher>) this.getDao().executeQueryList(
				"CourseTeacher.listAllActive", QueryCmdType.QUERY_NAME, offset,
				count, new Date());
	}
}