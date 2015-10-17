package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface AppointmentService extends
		BaseSearchService<Appointment, Long> {

	/**
	 * 列出指定核销状态下的数据
	 * @param status
	 * @param offset
	 * @param count
	 * @return
	 */
	List<Appointment> listByCheckStatus(Integer status, int offset, int count);
	
	Long countByCheckStatus(Integer status);

	List<Appointment> listByTeacher(Long accountId, int offset, int count);

	Long countByTeacher(Long accountId);

	List<Appointment> listByCourseTeacher(Long courseTeacherId);

	Long countByCourseTeacher(Long courseTeacherId);
}