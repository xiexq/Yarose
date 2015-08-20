package cn.com.yarose.web.controller.home;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shops/selector")
@CRUDControllerMeta(title = "门店管理", service = ShopService.class, listable = true,countable=true,searchable=true)
public class ShopSelectorController extends BaseCRUDControllerExt<Shop, Long> {

  @Override
  public Set<String> customListFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("code","name","desc");
  }
  
  @Override
  public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
    return this.generateStringSortedSet("name");
  }
  
  @Override
  public void customSearchExample(Shop example, HttpServletRequest request) throws Exception {
    super.customSearchExample(example, request);
    if(example.getName() != null){
      example.setName("%" + example.getName()+ "%");
    }
  }
}
