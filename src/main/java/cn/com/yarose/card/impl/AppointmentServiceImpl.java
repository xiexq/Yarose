package cn.com.yarose.card.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;

public class AppointmentServiceImpl extends
		DaoBasedServiceImpl<Appointment, Long> implements AppointmentService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByCheckStatus(Integer status, int offset,
			int count) {
		return (List<Appointment>) this.getDao().executeQueryList(
				"Appointment.listByCheckStatus", QueryCmdType.QUERY_NAME,
				offset, count, status);
	}

	@Override
	public Long countByCheckStatus(Integer status) {
		return (Long) this.getDao().executeQueryUnique(
				"Appointment.countByCheckStatus", QueryCmdType.QUERY_NAME,
				status);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByTeacher(Long accountId, int offset, int count) {
		return (List<Appointment>) this.getDao().executeQueryList(
				"Appointment.listByTeacher", QueryCmdType.QUERY_NAME, offset,
				offset, accountId);
	}

	@Override
	public Long countByTeacher(Long accountId) {
		return (Long) this.getDao().executeQueryUnique(
				"Appointment.countByTeacher", QueryCmdType.QUERY_NAME,
				accountId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByCourseTeacher(Long courseTeacherId) {
		List<Appointment> appointList = (List<Appointment>) this.getDao()
				.executeQueryList("Appointment.listByCourseTeacher",
						QueryCmdType.QUERY_NAME, -1, -1, courseTeacherId);
		if (appointList != null && appointList.size() > 0) {
			for (Appointment appoinment : appointList) {
				appoinment.getCourseTeacher();
			}
		}
		return appointList;
	}

	@Override
	public Long countByCourseTeacher(Long courseTeacherId) {
		return (Long) this.getDao().executeQueryUnique(
				"Appointment.countByCourseTeacher", QueryCmdType.QUERY_NAME,
				courseTeacherId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listActiveByUserIdAndStatus(String userId,
			int status, int offset, int count) {
		return (List<Appointment>) this.getDao().executeQueryList(
				"Appointment.listActiveByUserIdAndStatus",
				QueryCmdType.QUERY_NAME, offset, count, userId, status);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByUserIdAndCourseId(String userId,
			Long courseId) {
		return (List<Appointment>) this.getDao().executeQueryList(
				"Appointment.listByUserIdAndCourseId", QueryCmdType.QUERY_NAME,
				-1, -1, userId, courseId);
	}
}