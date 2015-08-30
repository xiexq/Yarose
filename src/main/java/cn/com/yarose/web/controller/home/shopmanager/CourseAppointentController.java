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
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class CourseAppointentController extends BaseCRUDControllerExt<Appointment, Long> {

	@Resource(name="courseTeacherService")
	private CourseTeacherService courseTeacherService;
	
	@Resource(name = "account_accountService")
	private AccountService accountService;
	
	@Resource(name="memberCardService")
	private MemberCardService memberCardService;
	
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
		return this.generateStringSortedSet("code","userId","courseBeginTime","courseEndTime");
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
		return this.generateStringSortedSet("userId","","mCard");	
	}
	
	@DictionaryModel(cascade = true, cascadeField = "userId", label = "cardNo", val = "id")
	  public Collection<MemberCard> _mCards(HttpServletRequest request) {
	    String id = this.getParameter(request, "__id");
	    if (StringUtils.hasText(id)) {
	      List<MemberCard> mCardList = memberCardService.listByUserId(id);
	      if (mCardList != null && mCardList.size() > 0) {
	        return mCardList;
	      }
	    }
	    return null;
	  }
	
	@Override
	public List<Appointment> customList(int offset, int count, OrderProperties orders, HttpServletRequest request)
			throws Exception {
		Long courseTeacherId = this.getRequestCourseId(request);
		if(courseTeacherId == null){
			return null;
		}
		CourseTeacher courseTeacher = courseTeacherService.findById(courseTeacherId);
		return ((AppointmentService)this.getCrudService()).listByCourseTeacher(courseTeacher.getId());
	}
	
	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		Long courseTeacherId = this.getRequestCourseId(request);
		if(courseTeacherId == null){
			return 0l;
		}
		CourseTeacher courseTeacher = courseTeacherService.findById(courseTeacherId);
		return ((AppointmentService)this.getCrudService()).countByCourseTeacher(courseTeacher.getId());
	}
	
	private Long getRequestCourseId(HttpServletRequest request){
		String courseTeacherId = request.getParameter("_courseTeacherId");
		if(courseTeacherId != null){
			return Long.valueOf(courseTeacherId);
		}else{
			return null;
		}
	}
	
	 @Override
	  public Appointment customSave(Appointment cmd, BindingResult result, HttpServletRequest request,
	      ResponseObject response, boolean create) throws Exception {
		if(this.validate(cmd, result, request, create)){
			if(create){
				cmd.setCreateTime(new Date());
			}
			
			Long courseTeacherId = this.getRequestCourseId(request);
			CourseTeacher ct = courseTeacherService.findById(courseTeacherId);
			cmd.setCourseTeacher(ct);
			cmd.setStatus(Constants.APPOLINTMENT_UNCHECKED);
		}
		return super.customSave(cmd, result, request, response, create);
	}

	 @Override
	public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
		return this.generateStringSortedSet("userId");
	}
	 
	@DictionaryModel(label = "alias", val = "userId")
	public List<Account> _ac_userIds(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			return accountService.findByUserId(value + "%", 0, 10);
		}
		return null;
	}
}
