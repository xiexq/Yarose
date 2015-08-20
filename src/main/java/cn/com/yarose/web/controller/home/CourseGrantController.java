package cn.com.yarose.web.controller.home;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.yarose.web.controller.BaseControllerExt;

@Controller
@RequestMapping("/home/admin/courses")
public class CourseGrantController extends BaseControllerExt {
  
  @RequestMapping(value="/grant",method=RequestMethod.GET)
  public String roomIndex(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
      this.rejectWebServletCfgs(model);
//      //根据用户类型加载不同的页面
//      Set<Access> accountSet = this.getAccount().getAccesses();
//      //if(account.get){
//        
//      //}
//      model.addAttribute("userType", "");
//      model.addAttribute("room", "");
      
      return "home/course";
  }
  
}
