package cn.com.yarose.web.controller.home;

import java.util.Date;
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
@RequestMapping("/home/course/teacher/selector")
@CRUDControllerMeta(title = "课程选择", service = CourseTeacherService.class, listable = true,countable=true,searchable=true)
public class CourseTeacherSelectorController extends BaseCRUDControllerExt<CourseTeacher, Long> {

  
  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    return this.generateStringSortedSet("courseName","shopName","teacherName","beginTime","endTime");
  }
  
  @Override
  public List<CourseTeacher> customList(int offset, int count, OrderProperties orders,
      HttpServletRequest request) throws Exception {
    return ((CourseTeacherService)this.getCrudService()).listByShopAndDay(1L, new Date(), new Date());
  }

}
