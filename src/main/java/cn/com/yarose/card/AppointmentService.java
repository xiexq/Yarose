package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface AppointmentService extends BaseSearchService<Appointment, Long> {

  List<Appointment> listAll(boolean isCheck, Integer status, int offset, int count);

  long countAll(boolean isCheck, Integer status);

}