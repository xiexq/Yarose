package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.controller.DictionaryModelType;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shop/manager/appointment")
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class AppointmentAdminController extends BaseCRUDControllerExt<Appointment, Long> {

  private MemberCardService memberCardService;

  @Resource(name = "memberCardService")
  public void setMemberCardService(MemberCardService memberCardService) {
    this.memberCardService = memberCardService;
  }
  
  public String getRequestType(HttpServletRequest request) {
    String type = request.getParameter("_type");
    if (type == null) {
      return null;
    } else {
      return type;
    }
  }

  private boolean isAuditRequest(HttpServletRequest request) {
    return "checked".equals(request.getParameter("_act"));
  }

  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("code", "userId","courseName", "shopName", "teacherName");
  }

  @Override
  public Set<String> customEditFields(HttpServletRequest request, Appointment entity)
      throws Exception {
    if (this.getRequestType(request) != null) {
      return this.generateStringSortedSet("otherConsum", "remark","lesson","giveLesson");
    }
    return this.generateStringSortedSet("userId", "mCard", "courseTeacher");
  }



  @Override
  public Set<String> customViewFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("code", "userId", "cardNo", "courseName", "shopName",
        "teacherName", "createTime");
  }

  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("");
  }

  @Override
  public void customSearchExample(Appointment cmd, HttpServletRequest request) throws Exception {
    boolean isCheck = isAuditRequest(request);
    if (isCheck) {
      cmd.setStatus(Constants.APPOLINTMENT_CHECKED);
    } else {
      cmd.setStatus(Constants.APPOLINTMENT_UNCHECKED);
    }
    super.customSearchExample(cmd, request);
  }

  @Override
  public List<Appointment> customList(int offset, int count, OrderProperties orders,
      HttpServletRequest request) throws Exception {
    AppointmentService as = (AppointmentService) this.getCrudService();
    boolean isCheck = isAuditRequest(request);
    Integer status = Constants.APPOLINTMENT_CHECKED;
    return as.listAll(isCheck, status, offset, count);
  }

  @Override
  public long customCount(HttpServletRequest request) throws Exception {
    AppointmentService as = (AppointmentService) this.getCrudService();
    boolean isCheck = isAuditRequest(request);
    Integer status = Constants.APPOLINTMENT_CHECKED;
    return as.countAll(isCheck, status);
  }

  @Override
  public Appointment customSave(Appointment cmd, BindingResult result, HttpServletRequest request,
      ResponseObject response, boolean create) throws Exception {
    if (this.validate(cmd, result, request, create)) {
      if (create) {
        cmd.setCreateTime(new Date());
      }
      String type = getRequestType(request);
      if(type != null){
    	  cmd.setStatus(Constants.APPOLINTMENT_CHECKED);
    	  cmd.setAccountId(this.getAccount().getAccountId());
    	  cmd.setChechUserId(this.getAccount().getUserid());
    	  MemberCard memberCard = cmd.getmCard();//
    	  memberCard.setUsedLesson(cmd.getLesson());
    	  memberCard.setGivingLesson(cmd.getGiveLesson());
    	  memberCardService.save(memberCard);
      }else{
    	  cmd.setStatus(Constants.APPOLINTMENT_UNCHECKED);
      }
    }
    return super.customSave(cmd, result, request, response, create);
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

  @DictionaryModel(header = true, label="courseName",val="id",type = DictionaryModelType.URL, url = "/home/course/teacher/selector")
  public CourseTeacher _courseTeachers(HttpServletRequest request, Object obj) {
    if (obj != null) {
      return (CourseTeacher) obj;
    }
    return null;
  }

  @ResponseBody
  @RequestMapping("/check/{id}")
  public ResponseObject getCourseTeacher(HttpServletRequest request, HttpServletResponse response,
      @PathVariable("id") Long id) {
    ResponseObject resp = new ResponseObject(true);
    if (id != null) {
      Appointment app = this.getCrudService().findById(id);
      CourseTeacher courseTeacher = app.getCourseTeacher();
      MemberCard memberCard = app.getmCard();
      memberCard.setUsedLesson(courseTeacher.getLesson());
      app.setStatus(Constants.APPOLINTMENT_CHECKED);
      this.getCrudService().save(app);
      memberCardService.save(memberCard);
    }
    return resp;
  }

}
