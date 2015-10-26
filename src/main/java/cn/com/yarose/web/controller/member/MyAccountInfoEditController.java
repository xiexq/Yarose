package cn.com.yarose.web.controller.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/my/account/info")
@CRUDControllerMeta(title = "个人信息", service = AccountService.class, listable = false, editable = true, viewable = true)
public class MyAccountInfoEditController extends
		BaseCRUDControllerExt<Account, Long> {

	@Override
	public Set<String> customViewFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("userid", "nick", "weixin",
				"phone", "email", "birthday");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest arg0, boolean arg1)
			throws Exception {
		return this.generateStringSortedSet("userid", "nick", "password",
				"confirmPassword", "weixin", "phone", "email", "birthday");
	}

	@Override
	public Map<String, Boolean> customFieldsRequired(
			HttpServletRequest request, boolean search) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("confirmPassword", true);
		return map;
	}

	@Override
	public Account customSave(Account cmd, BindingResult result,
			HttpServletRequest request, ResponseObject object, boolean create)
			throws Exception {
		if (!cmd.getConfirmPassword().equals(cmd.getPassword())) {
			result.rejectValue("password", "password.error", "和确认密码不一致");
			return cmd;
		}
		this.getCrudService().save(cmd);
		return cmd;
	}
}
