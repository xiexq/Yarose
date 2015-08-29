package cn.com.yarose.web.controller.home.teacher;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/teacher/my/course")
@CRUDControllerMeta(viewable = true, title = "我的课程", service = CourseTeacherService.class, listable = true, paged = true)
public class MyCoureController extends
		BaseCRUDControllerExt<CourseTeacher, Long> {

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("courseName", "shopName",
				"beginTime", "endTime", "lesson", "courseFee", "userId");
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("courseName", "shopName",
				"TeacherName", "beginTime", "endTime", "lesson", "courseFee",
				"createTime");
	}

	@Override
	public List<CourseTeacher> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		return ((CourseTeacherService) this.getCrudService()).listByTeacher(
				this.getAccount().getAccountId(), offset, count);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		return ((CourseTeacherService) this.getCrudService())
				.countByTeacher(this.getAccount().getAccountId());
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("shop", "course", "teacher",
				"beginTime", "endTime", "lesson", "courseFee");
	}
}
