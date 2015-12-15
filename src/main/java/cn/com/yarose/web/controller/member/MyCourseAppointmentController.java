package cn.com.yarose.web.controller.member;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseItem;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.Evaluation;
import cn.com.yarose.card.EvaluationService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/my/course/teacher")
@CRUDControllerMeta(title = "现在预约课程", service = CourseTeacherService.class, listable = true, viewable = true)
public class MyCourseAppointmentController extends
		BaseCRUDControllerExt<CourseTeacher, Long> {

	@Resource(name = "memberCardService")
	private MemberCardService memberCardService;

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "appointmentService")
	private AppointmentService appointmentService;
	
	@Resource(name = "evaluationService")
	private EvaluationService evaluationService;

	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("courseName", "beginDate",
				"shopName", "teacherName");
	}

	@Override
	public List<CourseTeacher> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		return ((CourseTeacherService) this.getCrudService()).listAllActive(-1,
				-1);
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("courseName", "beginDate",
				"endDate", "shopName", "shopAddress", "teacherName", "lesson");
	}

	/**
	 * 获取当前用户的所有会员卡
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/user/cards")
	public ResponseObject getAllCrad(HttpServletRequest request) {
		ResponseObject resp = new ResponseObject(false);
		String userId = this.getAccount().getUserid();
		if (StringUtils.hasText(userId)) {
			List<MemberCard> cards = memberCardService.listByUserId(userId);
			List<ResponseItem> items = resp.createListChildren("cards");
			for (MemberCard card : cards) {
				ResponseItem item = new ResponseItem();
				item.put(card.getId() + "", card.getCardNo());
				items.add(item);
			}
			resp.setSuccess(true);
		}
		return resp;
	}

	/**
	 * 课程预约(需验证会员卡课时是否够用)
	 * 
	 * @param request
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appointment/{courseId}/{cardId}")
	public ResponseObject saveUserAppointment(HttpServletRequest request,
			@PathVariable("courseId") Long courseId,
			@PathVariable("cardId") Long cardId) {
		ResponseObject resp = new ResponseObject(false);
		if (courseId != null && cardId != null) {
			CourseTeacher course = ((CourseTeacherService) this
					.getCrudService()).findById(courseId);
			MemberCard card = memberCardService.findById(cardId);
			// 验证该会员卡是否已过期
			if (card.getExpireDate().after(new Date())) {
				// 验证会员卡的课时余额是否够用
				if (card.getCanUseLesson() >= course.getLesson()) {
					Appointment appointment = new Appointment();
					appointment.setUserAccount(this.getAccount());
					appointment.setUserId(this.getAccount().getUserid());
					appointment.setCourseTeacher(course);
					appointment.setCourseTeacherId(courseId);
					appointment.setmCard(card);
					appointment.setStatus(Constants.APPOINTMENT_UNCHECKED);
					appointment.setCreateTime(new Date());
					appointmentService.save(appointment);
					// 从会员卡中减去使用的课时
					Integer usedLesson = card.getUsedLesson()
							+ course.getLesson();
					card.setUsedLesson(usedLesson);
					memberCardService.save(card);
					resp.setSuccess(true);
				} else {
					resp.put("notenough", true);
				}
			} else {
				resp.put("expired", true);
			}
		}
		return resp;
	}
	
	/**
	 * 查询出当前课程的星级
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping("/star/{cid}")
	public String viewStar(@PathVariable("cid") Long cid, Model model) {
		CourseTeacher ct = courseTeacherService.findById(cid);
		if (ct != null) {
			List<Evaluation> list = evaluationService.listByCourseId(
					ct.getCourse().getId(), -1, -1);
			if (list != null && list.size() > 0) {
				int star = 0;
				for (Evaluation eval : list) {
					star += eval.getLevel();
				}
				star = Math.round(star / list.size());
				model.addAttribute("star", star);
				model.addAttribute("description", Evaluation.getEvalContent(star));
			}
		}
		return "home/star";
	}
}
