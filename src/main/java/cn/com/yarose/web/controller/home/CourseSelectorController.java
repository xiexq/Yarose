package cn.com.yarose.web.controller.home;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/courses/selector")
@CRUDControllerMeta(title = "课程选择", service = CourseService.class, listable = true,countable=true,searchable=true)
public class CourseSelectorController extends BaseCRUDControllerExt<Course, Long> {

  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("code","name","desc");
  }
  
  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("name");
  }
  
  @Override
  public void customSearchExample(Course example, HttpServletRequest request) throws Exception {
    super.customSearchExample(example, request);
    if(example.getName() != null){
      example.setName("%" + example.getName()+ "%");
    }
  }
}
