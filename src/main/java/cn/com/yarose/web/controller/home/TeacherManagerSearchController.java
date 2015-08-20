package cn.com.yarose.web.controller.home;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/teacher/manager/search")
@CRUDControllerMeta(title = "门店管理", service = TeacherManagerService.class, listable = true,countable=true,searchable=true)
public class TeacherManagerSearchController extends BaseCRUDControllerExt<TeacherManager, Long> {

  
  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    return this.generateStringSortedSet("courseName","beginTime","endTime","createTime");
  }
  
  @Override
  public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
    // TODO Auto-generated method stub
    return this.generateStringSortedSet("beginTime","endTime");
  }
  @Override
  public List<TeacherManager> customList(int offset, int count, OrderProperties orders,
      HttpServletRequest request) throws Exception {
    // TODO Auto-generated method stub
    return super.customList(offset, count, orders, request);
  }
  
  
  //读取数据例子
//  public ModelAndView getTitle(HttpServletRequest request, HttpServletResponse response) {
//    String strvalue = "{ 'eventinfo':[{day: '3/3/2012',eventtitle:'test1'},{day: '3/8/2012',eventtitle:'test2'}　]} ";
//    response.setCharacterEncoding("UTF-8");
//    System.out.println("strvalue="+strvalue);
//    try {
//        response.getWriter().print(strvalue);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    return null;
//}

}
