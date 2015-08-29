package cn.com.yarose.web.controller.home.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/teacher/my/appointment")
@CRUDControllerMeta(title = "我的预约管理", service = AppointmentService.class, listable = true)
public class MyAppointmentController extends
		BaseCRUDControllerExt<Appointment, Long> {

	private MemberCardService memberCardService;

	@Resource(name = "memberCardService")
	public void setMemberCardService(MemberCardService memberCardService) {
		this.memberCardService = memberCardService;
	}

	@Override
	public List<Appointment> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		AppointmentService service = (AppointmentService) this.getCrudService();
		return service.listByTeacher(this.getAccount().getAccountId(), offset, count);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		AppointmentService service = (AppointmentService) this.getCrudService();
		return service.countByTeacher(this.getAccount().getAccountId());
	}
}
