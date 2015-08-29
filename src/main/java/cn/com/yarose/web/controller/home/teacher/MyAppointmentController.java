package cn.com.yarose.web.controller.home.teacher;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/teacher/my/appointment")
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, paged = true)
public class MyAppointmentController extends
		BaseCRUDControllerExt<Appointment, Long> {

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("code", "courseName", "shopName",
				"teacherName");
	}

	@Override
	public List<Appointment> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		AppointmentService service = (AppointmentService) this.getCrudService();
		return service.listByTeacher(this.getAccount().getAccountId(), offset,
				count);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		AppointmentService service = (AppointmentService) this.getCrudService();
		return service.countByTeacher(this.getAccount().getAccountId());
	}
}
