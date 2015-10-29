package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface AppointmentService extends
		BaseSearchService<Appointment, Long> {

	/**
	 * 列出指定核销状态下的数据
	 * 
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

	/**
	 * 根据核销状态列出当前会员的预约
	 * 
	 * @param userId
	 * @param status
	 * @param offset
	 * @param count
	 * @return
	 */
	List<Appointment> listActiveByUserIdAndStatus(String userId, int status,
			int offset, int count);

	/**
	 * 判断用户是否已经预约该课程
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	List<Appointment> listByUserIdAndCourseId(String userId, Long courseId);
}