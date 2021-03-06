package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface AppointmentService extends BaseSearchService<Appointment, Long> {

  List<Appointment> listAll(boolean isCheck, Integer status, int offset, int count);

  long countAll(boolean isCheck, Integer status);

List<Appointment> listByTeacher(Long accountId, int offset, int count);

long countByTeacher(Long accountId);

List<Appointment> listByCourseTeacher(Long courseTeacherId);

long countByCourseTeacher(Long courseTeacherId);

}