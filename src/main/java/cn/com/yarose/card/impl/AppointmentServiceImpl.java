package cn.com.yarose.card.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;

public class AppointmentServiceImpl extends DaoBasedServiceImpl<Appointment, Long> implements AppointmentService {

  @SuppressWarnings("unchecked")
  @Override
  public List<Appointment> listAll(boolean isCheck, Integer status, int offset, int count) {
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

}