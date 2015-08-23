package cn.com.yarose.web.controller.home.shopmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shopmanager/membercard")
@CRUDControllerMeta(title = "会员卡管理", service = MemberCardService.class, listable = true,countable=true,searchable=true)
public class MemberCardController extends BaseCRUDControllerExt<MemberCard, Long> {


}
