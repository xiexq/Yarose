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
	public List<Appointment> listAll(boolean isCheck, Integer status,
			int offset, int count) {
		StringBuffer sb = new StringBuffer();
		if (isCheck == true) {
			sb.append("select a from cn.com.yarose.card.Appointment a where a.status = ? order by a.createTime desc");
		} else {
			sb.append("select a from cn.com.yarose.card.Appointment a where a.status != ? order by a.createTime desc");
		}
		return (List<Appointment>) this.getDao().executeQueryList(
				sb.toString(), QueryCmdType.HSQL, offset, count, status);
	}

	@Override
	public long countAll(boolean isCheck, Integer status) {
		StringBuffer sb = new StringBuffer();
		if (isCheck == true) {
			sb.append("select count(*) from cn.com.yarose.card.Appointment a where a.status = ?");
		} else {
			sb.append("select count(*) from cn.com.yarose.card.Appointment a where a.status != ?");
		}
		return (Long) this.getDao().executeQueryUnique(sb.toString(),
				QueryCmdType.HSQL, status);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByTeacher(Long accountId, int offset, int count) {
		return (List<Appointment>) this.getDao().executeQueryList(
				"Appointment.listByTeacher", QueryCmdType.QUERY_NAME, offset,
				offset, accountId);
	}

	@Override
	public long countByTeacher(Long accountId) {
		return (Long) this.getDao().executeQueryUnique(
				"Appointment.countByTeacher", QueryCmdType.QUERY_NAME, accountId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> listByCourseTeacher(Long courseTeacherId) {
		List<Appointment> appointList = (List<Appointment>) this.getDao().executeQueryList("Appointment.listByCourseTeacher", QueryCmdType.QUERY_NAME, -1, -1, courseTeacherId);
		if(appointList != null && appointList.size() > 0){
			for(Appointment appoinment : appointList){
				appoinment.getCourseTeacher();
			}
		}
		return appointList;
	}

	@Override
	public long countByCourseTeacher(Long courseTeacherId) {
		return (Long) this.getDao().executeQueryUnique("Appointment.countByCourseTeacher", QueryCmdType.QUERY_NAME, courseTeacherId);
	}
}