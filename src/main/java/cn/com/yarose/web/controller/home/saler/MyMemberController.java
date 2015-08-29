package cn.com.yarose.web.controller.home.saler;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/my/saler/member")
@CRUDControllerMeta(title = "会员管理", service = AccountService.class, listable = true, viewable = true)
public class MyMemberController extends BaseCRUDControllerExt<Account, Long> {

	@Override
	public Set<String> customListFields(HttpServletRequest arg0) throws Exception {
		return this.generateStringSortedSet("userid","weixin","phone","birthday");
	}
	
	@Override
	public List<Account> customList(int offset, int count, OrderProperties orders, HttpServletRequest request)
			throws Exception {
		String salerId = this.getAccount().getSaler();
		return ((AccountService)this.getCrudService()).listBySalerId(salerId);
	}
	
	
	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return super.customCount(request);
	}
}
