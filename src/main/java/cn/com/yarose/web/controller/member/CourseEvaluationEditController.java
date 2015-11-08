package cn.com.yarose.web.controller.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.Evaluation;
import cn.com.yarose.card.EvaluationService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/member/course/evaluation/edit")
@CRUDControllerMeta(title = "会员评价课程", service = EvaluationService.class, listable = false, viewable = false, searchable = false, createable = true, editable = true, deleteable = false, paged = false)
public class CourseEvaluationEditController extends
		BaseCRUDControllerExt<Evaluation, Long> {

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "appointmentService")
	AppointmentService appointmentService;

	@Override
	public Set<String> customEditFields(HttpServletRequest reques,
			boolean create) throws Exception {
		return this.generateStringSortedSet("content", "level");
	}

	public Long getCourseTeacherId(HttpServletRequest request) {
		String cid = request.getParameter("__cid");
		if (StringUtils.hasText(cid)) {
			return Long.parseLong(cid);
		}
		return null;
	}

	@Override
	public Map<String, Boolean> customFieldsRequired(
			HttpServletRequest request, boolean search) throws Exception {
		Map<String, Boolean> map = super.customFieldsRequired(request, search);
		if (map == null) {
			map = new HashMap<String, Boolean>();
		}
		map.put("courseTeacher", false);
		return map;
	}

	@RequestMapping("/save/{cid}")
	@ResponseBody
	public ResponseObject saveEval(@PathVariable("cid") Long cid,
			HttpServletRequest request, Evaluation cmd) {
		ResponseObject resp = new ResponseObject(false);
		if (cid != null && cmd != null) {
			cmd.setAccount(this.getAccount());
			Appointment app = appointmentService.findById(cid);
			cmd.setCourseTeacher(app.getCourseTeacher());
			cmd.setCreateTime(new Date());
			cmd.setType(Constants.EVALUATION_TYPE_USER);
			String stuLevel = Constants.getevaluationType(this.getAccount()
					.getStuLevel());
			cmd.setStuLevelName(stuLevel);
			this.getCrudService().save(cmd);
			resp.setSuccess(true);
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/isevaluated")
	public ResponseObject isevaluated(HttpServletRequest request) {
		ResponseObject resp = new ResponseObject(false);
		String cid = request.getParameter("__cid");
		if (StringUtils.hasText(cid)) {
			Appointment app = appointmentService.findById(Long.parseLong(cid));
			if (app != null) {
				Long accountId = this.getAccount().getAccountId();
				List<Evaluation> evl = ((EvaluationService) this
						.getCrudService()).listByUserAndCourseTeacher(
						accountId, app.getCourseTeacher().getId());
				if (evl == null || evl.size() <= 0) {
					// 没有评论
					resp.setSuccess(true);
				}
			}
		}
		return resp;
	}
}
