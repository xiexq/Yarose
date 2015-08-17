package cn.com.yarose.web.controller.home.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yarose.web.controller.BaseCRUDControllerExt;
import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;


@Controller
@RequestMapping("/home/admin/access")
@CRUDControllerMeta(title="权限管理",service=AccessService.class,listable=true,createable=true,editable=true,deleteable=false)
public class AccessAdminController extends BaseCRUDControllerExt<Access, String> {
  
}
