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
import cn.com.eduedu.jee.mvc.controller.DictionaryModelType;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shop/manager/appointment")
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class AppointmentAdminController extends BaseCRUDControllerExt<Appointment, Long> {

	private MemberCardService memberCardService;
	private CourseTeacherService courseTeacherService;
	
	@Resource(name="memberCardService")
	public void setMemberCardService(MemberCardService memberCardService){
		this.memberCardService = memberCardService;
	}
	
	@Resource(name="courseTeacherService")
	public void setCourseTeacherService(CourseTeacherService courseTeacherService){
		this.courseTeacherService = courseTeacherService;
	}
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
		return this.generateStringSortedSet("code", "courseName", "shopName", "teacherName");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request, Appointment entity) throws Exception {
		return this.generateStringSortedSet("userId", "mcard", "courseTeacher");
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request) throws Exception {
		return this.generateStringSortedSet("code", "userId", "cardNo", "courseName", "shopName", "teacherName",
				"createTime");
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
		return this.generateStringSortedSet("");
	}
	
	@Override
	public void customSearchExample(Appointment cmd, HttpServletRequest request) throws Exception {
		super.customSearchExample(cmd, request);
	}
	
	@Override
	public List<Appointment> customList(int offset, int count, OrderProperties orders, HttpServletRequest request)
			throws Exception {
		return super.customList(offset, count, orders, request);
	}
	
	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		return super.customCount(request);
	}
	
	
	@Override
	public Appointment customSave(Appointment cmd, BindingResult result, HttpServletRequest request,
			ResponseObject response, boolean create) throws Exception {
		if (this.validate(cmd, result, request, create)) {
			if (create) {
				cmd.setCreateTime(new Date());
			}
		}
		return super.customSave(cmd, result, request, response, create);
	}
	
	@DictionaryModel(header = true, cascade = true, cascadeField = "userId", headerIsJustForSearch = true)
	public Collection<MemberCard> _courses(HttpServletRequest request) {
		String id = this.getParameter(request, "__id");
		if (StringUtils.hasText(id)) {
			List<MemberCard> mCardList = memberCardService.listByUserId(id);
			if (mCardList != null && mCardList.size() > 0) {
				return mCardList;
			}
		}
		return null;
	}
	
	@DictionaryModel(header = true, headerLabel = "不限", headerValue = "",type=DictionaryModelType.URL,url="/home/course/teacher/selector", headerIsJustForSearch=true, cascade = true, cascadeField = "tenantCode")
	public CourseTeacher _courseTeachers(HttpServletRequest request, Object obj) {
	    if(obj != null){
	      return courseTeacherService.findById(Long.valueOf(obj.toString()));
	    }
		return null;
	}
	
	
	
}
