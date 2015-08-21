package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/teacher/managers")
@CRUDControllerMeta(viewable = true, title = "课程授权", service = TeacherManagerService.class, listable = true, createable = true, editable = true, deleteable = true,searchable=true)
public class TeacherManagersAdminController extends BaseCRUDControllerExt<TeacherManager, Long> {

  private CourseService courseService;
  
  private ShopService shopService;
  
  @Resource(name="courseService")
  public void setCourseService(CourseService courseService){
    this.courseService = courseService;
  }
  
  @Resource(name="shopService")
  public void shopService(ShopService shopService){
    this.shopService = shopService;
  }
  
  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    return this.generateStringSortedSet("shopId");
  }
  
  public Collection<Shop> _shopIds(HttpServletRequest request){
    return shopService.listAll(-1, -1);
  }
  
}
