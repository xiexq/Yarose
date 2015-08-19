package cn.com.yarose.web.controller.home.admin;

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
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/teacher/managers")
@CRUDControllerMeta(viewable = true, title = "课程授权", service = TeacherManagerService.class, listable = true, createable = true, editable = true, deleteable = true)
public class TeacherManagersAdminController extends BaseCRUDControllerExt<TeacherManager, Long> {

  private CourseService courseService;
  
  @Resource(name="courseService")
  public void setCourseService(CourseService courseService){
    this.courseService = courseService;
  }
  
  @Override
  public Set<String> customViewFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("userId", "courseName", "authTime");
  }

  @Override
  public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
    return this.generateStringSortedSet("userId");
  }

  @Override
  public List<TeacherManager> customList(int offset, int count,
          OrderProperties orders, HttpServletRequest request)
          throws Exception {
      return ((TeacherManagerService) this.getCrudService()).listByCourseId(
              this.getCourseIdFromRequest(request), offset, count);
  }
  
  @Override
  public long customCount(HttpServletRequest request) throws Exception {
      return ((TeacherManagerService) this.getCrudService()).countTeacherAllManagers(this.getCourseIdFromRequest(request));
  }
  
  @Override
  public TeacherManager customSaveCmd(TeacherManager cmd, HttpServletRequest request, Long id)
      throws Exception {
    cmd = super.customSaveCmd(cmd, request, id);
    if (id == null) {
        cmd.setAuthSourceAlias(this.getAccount().getUserid());
        cmd.setAuthSourceId(this.getAccount().getAccountId());
        cmd.setAuthTime(new Date());
        Long courseId = getCourseIdFromRequest(request);
        Course course = null;
        if(courseId != null){
          course = courseService.findById(courseId);
        }
        cmd.setCourse(course);
    }
    return cmd;
  }
  
  @Override
  public TeacherManager customSave(TeacherManager cmd, BindingResult result,
      HttpServletRequest request, ResponseObject response, boolean create) throws Exception {
    if (this.validate(cmd, result, request, create)) {
      if (create) {
        //List<CASAccount> accounts=null;
        //accounts= casAccountService.findByUserId(cmd.getUserId(), 0, 1);
        //if (accounts == null || accounts.size() <= 0) {
         // result.rejectValue("userId", "not.exist", "不存在");
         // return cmd;
        //} else {
            //CASAccount account = accounts.get(0);
            cmd.setAccountId(1L);
            cmd.setUserId(cmd.getUserId());
       // }
      }
      return this.getCrudService().save(cmd);
    }
    return cmd;
  }

  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("userId", "authTime");
  }
  
  private Long getCourseIdFromRequest(HttpServletRequest request) {
    Long courseId = Long.parseLong(request.getParameter("_courseId"));
    return courseId;
  }

}
