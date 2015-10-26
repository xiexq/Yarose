package cn.com.yarose.web.controller.member;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/my/course/appoint/checked")
@CRUDControllerMeta(title = "预约历史记录", service = AppointmentService.class, listable = true, viewable = true)
public class MyCheckedCourseAppointController extends
		BaseCRUDControllerExt<Appointment, Long> {

	@Resource(name = "memberCardService")
	private MemberCardService memberCardService;

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "appointmentService")
	private AppointmentService appointmentService;

	@Override
	public List<Appointment> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		String userId = this.getAccount().getUserid();
		return ((AppointmentService) this.getCrudService())
				.listActiveByUserIdAndStatus(userId,
						Constants.APPOINTMENT_CHECKED, -1, -1);
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("courseName", "beginDate",
				"shopName", "teacherName");
	}
}
