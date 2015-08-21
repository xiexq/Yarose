package cn.com.yarose.web.controller.home.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.controller.DictionaryModelType;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/teacher/managers")
@CRUDControllerMeta(viewable = true, title = "课程授权", service = TeacherManagerService.class, listable = true, createable = true, editable = true, deleteable = true, searchable = true)
public class TeacherManagersAdminController extends BaseCRUDControllerExt<TeacherManager, Long> {

  private CourseService courseService;
  private ShopService shopService;
  @Resource(name = "account_accessService")
  private AccessService accessService;
  
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

  @Override
  public void customSearchExample(TeacherManager example, HttpServletRequest request)
      throws Exception {
    example.setShopId(1L);
    super.customSearchExample(example, request);
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
  public TeacherManager customEdit(Long id, HttpServletRequest request) throws Exception {
     TeacherManager tm = this.getCrudService().findById(id);
     return tm;
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
    Long date = Long.parseLong(request.getParameter("_date"));
    return date;
  }
  
  private Long getShopIdRequest(HttpServletRequest request) {
    String shop = request.getParameter("_shop");
    if(shop==null){
      return 1L;
    }else{
      return Long.parseLong(request.getParameter("_shop"));
    }
  }

  @Override
  public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
    return this.generateStringSortedSet("shopId", "courseId", "userId", "beginTime", "endTime");
  }

  @DictionaryModel(header = true, headerLabel = "不限", headerValue = "", type = DictionaryModelType.URL, url = "/home/courses/selector", headerIsJustForSearch = true, cascade = true)
  public Shop _shopIds(HttpServletRequest request, Object obj) {
    if (obj != null) {
      return shopService.findById(Long.valueOf(obj.toString()));
    }
    return null;
  }

  @DictionaryModel(header = true, headerLabel = "请选择", headerValue = "")
  public Collection<Shop> _shopIds(HttpServletRequest request){
      return shopService.listAll(-1, -1);
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
  
  @RequestMapping("/select")
  @ResponseBody
  public ModelAndView getTitle(HttpServletRequest request, HttpServletResponse response) {
    Long shopoId = this.getShopIdRequest(request);
    List<TeacherManager> tmList = ((TeacherManagerService)this.getCrudService()).listByShopId(shopoId);
    StringBuilder sb = new StringBuilder();
    sb.append("{\'eventinfo\':");
    sb.append("[");
    for(TeacherManager tm: tmList){
      sb.append("{\"id\":" + tm.getId());
      sb.append(",\"title\":\""+tm.getShopId()+"\"");
      sb.append(",\"start\":\""  + tm.getBeginTime()+"\"");
      sb.append(",\"end\":\""  + tm.getEndTime()+"\"");
      sb.append("},");
    }
    sb.setLength(sb.length()-1);
    sb.append("]");
    sb.append("}");
    response.setCharacterEncoding("UTF-8");
    try {
        response.getWriter().print(sb.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}

  @DictionaryModel(val = "id", label = "name")
  public Collection<Access> _accessess(HttpServletRequest request) {
      List<Access> as = accessService.listAll(-1, -1);
      String isShop = this.getParameter(request, "_shop");
      if (StringUtils.hasText(isShop)) {
          List<Access> list = new ArrayList<Access>();
          for (Access access : as) {
              if (!Constants.ROLE_SUPER.equals(access.getId())) {
                  list.add(access);
              }
          }
          return list;
      }
      return as;
  }

}
