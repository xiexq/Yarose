package cn.com.yarose.web.controller.home;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/course/event")
@CRUDControllerMeta(title = "门店管理", service = TeacherManagerService.class, createable= true,listable=true,editable=true)
public class CreateEventController extends BaseCRUDControllerExt<TeacherManager, Long> {

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
  public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
    return this.generateStringSortedSet("name","courseId","shopId","beginTime","EndTime");
  }
  
  @Override
  public TeacherManager customSave(TeacherManager cmd, BindingResult result,
      HttpServletRequest request, ResponseObject response, boolean create) throws Exception {
    if(this.validate(cmd, result, request, create)){
      
    }
    return super.customSave(cmd, result, request, response, create);
  }
  
  @Override
  public void customCreate(TeacherManager cmd, HttpServletRequest request) throws Exception {
    cmd.setBeginTime(new Date());
    super.customCreate(cmd, request);
  }
  
  
  private String getAddDateFromRequest(HttpServletRequest request) {
    String date = request.getParameter("_date");
    return date;
  }
  
  public Collection<Course> _courseIds(HttpServletRequest request){
    return courseService.listAll(-1, -1);
  }
  
  public Collection<Shop> _shopIds(HttpServletRequest request){
    return shopService.listAll(-1, -1);
  }
  
  @RequestMapping("/add/{selDate}")
  public String index(@PathVariable("selDate") Long date, Model model,
                      HttpServletRequest request, HttpServletResponse response){
      this.rejectWebServletCfgs(model);
      this.rejectAccountInfo(model);
      System.out.println(date);
      model.addAttribute("userType", "");
      model.addAttribute("room", "");
      return "home/event";
  }
  
  
    //读取数据例子
    @RequestMapping("/select")
    @ResponseBody
    public ResponseObject getTitle(HttpServletRequest request, HttpServletResponse response) {
      String strvalue = "{ 'eventinfo':[{day: '3/3/2015',eventtitle:'test1'},{day: '3/8/2015',eventtitle:'test2'}　]} ";
      ResponseObject resp = new ResponseObject(true);
      System.out.println("strvalue="+strvalue);
      resp.put("doc", strvalue);
      return resp;
    }
}
