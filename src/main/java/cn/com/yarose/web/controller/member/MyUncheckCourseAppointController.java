package cn.com.yarose.web.controller.member;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/my/course/appoint/uncheck")
@CRUDControllerMeta(title = "预约中的课程", service = AppointmentService.class, listable = true, viewable = true)
public class MyUncheckCourseAppointController extends
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
						Constants.APPOINTMENT_UNCHECKED, -1, -1);
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("courseName", "beginDate",
				"shopName", "teacherName");
	}

	/**
	 * 取消预约的课程
	 * 
	 * @param aid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancle/{aid}")
	public ResponseObject cancelAppointment(@PathVariable("aid") Long aid) {
		ResponseObject resp = new ResponseObject(false);
		if (aid != null) {
			// 在会员卡中把该次预约的课时给减掉
			Appointment app = this.getCrudService().findById(aid);
			if (app != null) {
				MemberCard card = app.getmCard();
				if (card != null && card.getUsedLesson() >= app.getLesson()) {
					Integer usedLesson = card.getUsedLesson() - app.getLesson();
					card.setUsedLesson(usedLesson);
					memberCardService.save(card);
					this.getCrudService().deleteById(aid);
					resp.setSuccess(true);
				}
			}
		}
		return resp;
	}
}
