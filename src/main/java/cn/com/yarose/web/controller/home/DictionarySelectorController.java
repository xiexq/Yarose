package cn.com.yarose.web.controller.home;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/dictionarys/selector")
@CRUDControllerMeta(title = "舞种管理", service = DictionaryService.class, listable = true,countable=true,searchable=true)
public class DictionarySelectorController extends BaseCRUDControllerExt<Dictionary, Long> {
  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("code","name");
  }
  
  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("name");
  }
  
  @Override
  public void customSearchExample(Dictionary example, HttpServletRequest request) throws Exception {
    super.customSearchExample(example, request);
    if(example.getName() != null){
      example.setName("%" + example.getName()+ "%");
    }
  }
}
