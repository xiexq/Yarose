package cn.com.yarose.web.controller.member;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Evaluation;
import cn.com.yarose.card.EvaluationService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/member/course/evaluation/view")
@CRUDControllerMeta(title = "查看评价", service = EvaluationService.class, listable = true, viewable = false, searchable = false, createable = false, editable = false, deleteable = false, paged = false)
public class CourseEvaluationViewController extends
		BaseCRUDControllerExt<Evaluation, Long> {

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("accountName", "content");
	}

	@Override
	public List<Evaluation> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		Long cid = getCourseTeacherId(request);
		if (cid != null) {
			CourseTeacher course = courseTeacherService.findById(cid);
			if (course != null && course.getCourse() != null) {
				return (List<Evaluation>) ((EvaluationService) this
						.getCrudService()).listByCourseId(course.getCourse()
						.getId(), -1, -1);
			}
		}
		return null;
	}

	public Long getCourseTeacherId(HttpServletRequest request) {
		String cid = request.getParameter("__cid");
		if (StringUtils.hasText(cid)) {
			return Long.parseLong(cid);
		}
		return null;
	}
}
