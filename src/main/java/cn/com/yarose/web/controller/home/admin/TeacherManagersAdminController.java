package cn.com.yarose.web.controller.home.admin;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.controller.DictionaryModelType;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/teacher/managers")
@CRUDControllerMeta(viewable = true, title = "课程授权", service = TeacherManagerService.class, listable = true, createable = true, editable = true, deleteable = true, searchable = true)
public class TeacherManagersAdminController extends BaseCRUDControllerExt<TeacherManager, Long> {

  private CourseService courseService;
  private ShopService shopService;

  @Resource(name = "courseService")
  public void setCourseService(CourseService courseService) {
    this.courseService = courseService;
  }

  @Resource(name = "shopService")
  public void shopService(ShopService shopService) {
    this.shopService = shopService;
  }

  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("shopId");
  }

  public Collection<Shop> _shopIds(HttpServletRequest request) {
    return shopService.listAll(-1, -1);
  }

  @Override
  public void customCreate(TeacherManager cmd, HttpServletRequest request) throws Exception {
    super.customCreate(cmd, request);
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(this.getAddDateFromRequest(request));
    cmd.setBeginTime(c.getTime());
    cmd.setEndTime(c.getTime());
  }

  @Override
  public TeacherManager customSaveCmd(TeacherManager cmd, HttpServletRequest request, Long id)
      throws Exception {
    cmd.setAccountId(this.getAccount().getAccountId());
    cmd.setAuthSourceId(this.getAccount().getAccountId());
    cmd.setAuthSourceAlias(this.getAccount().getUserid());
    return cmd;
  }

  @Override
  public TeacherManager customSave(TeacherManager cmd, BindingResult result,
      HttpServletRequest request, ResponseObject response, boolean create) throws Exception {
    if (this.validate(cmd, result, request, create)) {
      if (create) {
        cmd.setCreateTime(new Date());
      }
    }
    return super.customSave(cmd, result, request, response, create);
  }

  private Long getAddDateFromRequest(HttpServletRequest request) {
    // Long date = Long.parseLong(request.getParameter("_date"));
    return 1403931367000L;
  }

  @Override
  public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
    return this.generateStringSortedSet("shopId", "courseId", "userId", "beginTime", "endTime");
  }

  @DictionaryModel(header = true, headerLabel = "不限", headerValue = "请选择", type = DictionaryModelType.URL, url = "/home/courses/selector", headerIsJustForSearch = true, cascade = true)
  public Shop _shopIds(HttpServletRequest request, Object obj) {
    if (obj != null) {
      return shopService.findById(Long.valueOf(obj.toString()));
    }
    return null;
  }

  @DictionaryModel(header = true, cascade = true, cascadeField = "shopId", headerIsJustForSearch = true)
  public Collection<Course> _courseIds(HttpServletRequest request) {
    String id = this.getParameter(request, "__id");
    if (StringUtils.hasText(id)) {
      List<Course> courseList = courseService.listByShopId(Long.parseLong(id));
      if (courseList != null && courseList.size() > 0) {
        return courseList;
      }
    }
    return null;
  }

  // 读取数据例子
  @RequestMapping("/select")
  @ResponseBody
  public String calendarEvents(HttpServletRequest request){
      Long shopoId = 2L;
      List<TeacherManager> tmList = ((TeacherManagerService)this.getCrudService()).listByShopId(shopoId);
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for(TeacherManager tm: tmList){
          sb.append("{\"id\":" + tm.getId());
          sb.append(",\"title\":\""+tm.getShopId()+"\"");
          sb.append(",\"start\":\""  + tm.getBeginTime());
          sb.append(",\"end\":\""  + tm.getEndTime());
          sb.append("},");
      }
      sb.setLength(sb.length()-1);
      sb.append("]");
      System.out.println(sb.toString());
      return sb.toString();
  }


  // @DictionaryModel(label = "alias", val = "userId")
  // public List<CASAccount> _ac_userIds(String userId,
  // HttpServletRequest request) {
  // if(StringUtils.hasText(userId)){
  // return casAccountService.findByUserId(userId + "%", 0, 15);
  // }
  // return null;
  // }

}
