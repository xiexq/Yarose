package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/course/appointment")
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, createable = true, editable = false, deleteable = true, viewable = false, searchable = true)
public class CourseAppointentController extends
		BaseCRUDControllerExt<Appointment, Long> {

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "memberCardService")
	private MemberCardService memberCardService;

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("code", "userId",
				"courseBeginTime", "courseEndTime");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("userId", "mCard");
	}

	@DictionaryModel(cascade = true, cascadeField = "userId", label = "cardNo", val = "id")
	public Collection<MemberCard> _mCards(HttpServletRequest request) {
		String id = this.getParameter(request, "__id");
		if (StringUtils.hasText(id)) {
			return memberCardService.listByUserId(id);
		}
		return null;
	}

	@Override
	public List<Appointment> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		Long courseTeacherId = this.getRequestCourseId(request);
		if (courseTeacherId != null) {
			return ((AppointmentService) this.getCrudService())
					.listByCourseTeacher(courseTeacherId);
		}
		return null;
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		Long courseTeacherId = this.getRequestCourseId(request);
		if (courseTeacherId != null) {
			return ((AppointmentService) this.getCrudService())
					.countByCourseTeacher(courseTeacherId);
		}
		return 0l;
	}

	private Long getRequestCourseId(HttpServletRequest request) {
		String courseTeacherId = request.getParameter("_courseTeacherId");
		if (StringUtils.hasText(courseTeacherId)) {
			return Long.valueOf(courseTeacherId);
		}
		return null;
	}

	@Override
	public Appointment customSave(Appointment cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			// 只有创建没有修改
			if(create){
				Long courseTeacherId = this.getRequestCourseId(request);
				// 检查是否已经预约过
				List<Appointment> apps = ((AppointmentService) this
						.getCrudService()).listByUserIdAndCourseId(cmd.getUserId(),
						courseTeacherId);
				if (apps != null && apps.size() > 0) {
					result.rejectValue("userId", "userId.error", "已预约");
					return cmd;
				}
				// 创建预约时计算会员卡信息
				MemberCard memberCard = memberCardService.findById(cmd.getmCard().getId());
				CourseTeacher courseTeacher = courseTeacherService.findById(courseTeacherId);
				Integer needLesson = courseTeacher.getLesson();
				if (memberCard.getCanUseLesson() < needLesson) {
					result.rejectValue(
							"mCard",
							"mCard.error",
							"该会员卡剩余课时不足，需" + needLesson + "课时,剩余"
									+ memberCard.getCanUseLesson() + "课时");
					return cmd;
				}
				// 已使用课时=本次需要课时+以前已使用课时
				memberCard.setUsedLesson(needLesson + memberCard.getUsedLesson());
				// 先保存会员卡的先关信息
				memberCardService.save(memberCard);
				cmd.setCreateTime(new Date());
				cmd.setStatus(Constants.APPOINTMENT_UNCHECKED);
				Account account = accountService.findByUserId(cmd.getUserId());
				cmd.setUserAccount(account);
				cmd.setCourseTeacher(courseTeacher);
				cmd = this.getCrudService().save(cmd);
			}
		}
		return cmd;
	}
	
	@Override
	public void customDelete(Long id, HttpServletRequest request)
			throws Exception {
		// 删除预约时，需同步更新会员卡课时
		Appointment app = this.getCrudService().findById(id);
		if (app != null) {
			MemberCard memCard = app.getmCard();
			memCard.setUsedLesson(memCard.getUsedLesson() - app.getLesson());
			memberCardService.save(memCard);
		}
		super.customDelete(id, request);
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userId");
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_userIds(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			return accountService.findByUserId(value + "%", 0, 10);
		}
		return null;
	}
}
