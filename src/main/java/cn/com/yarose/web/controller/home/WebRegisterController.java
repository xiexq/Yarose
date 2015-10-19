package cn.com.yarose.web.controller.home;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.eduedu.jee.util.StringUtils;
import cn.com.yarose.register.RegisterForm;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/register")
@CRUDControllerMeta(title = "注册", createable = true, enableCaptcha = true, clientValidation = true)
public class WebRegisterController extends
		BaseCRUDControllerExt<RegisterForm, Long> {
	
	@Resource(name="account_accountService")
	AccountService accountService;
	@Resource(name = "account_accessService")
	private AccessService accessService;
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		if (this.isMobile(request)) {
			return this.generateStringSortedSet("userid", "password",
					"passwordConfirm");
		} else {
			return this.generateStringSortedSet("userid", "password",
					"passwordConfirm","nick");
		}

	}
	
	public String _rv_userids(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value) && accountService.checkExistAccountByUserID(value)) {
			return "已经被使用";
		}
		return null;
	}

	/**
	 * 注册用户信息保存
	 */
	@Override
	public RegisterForm customSave(RegisterForm cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, true)) {
			boolean hasErr = false;
			if (!cmd.getPassword().equals(cmd.getPasswordConfirm())) {
				result.rejectValue("password", "not.matchs", "和确认密码不一致");
				hasErr = true;
			}
			if (!StringUtils.hasText(cmd.getNick())) {
				String nick = cmd.getUserid();
				if (nick.length() > 50) {
					nick = nick.substring(0, 40);
				}
				cmd.setNick(nick);
			}
			if (accountService.checkExistAccountByUserID(cmd.getUserid())) {
				hasErr = true;
				result.rejectValue("userid", "has.exist", "已经存在");
			}
			if (accountService.getExistAccountByNick(cmd.getNick())) {
				hasErr = true;
				result.rejectValue("nick", "has.exist", "已经存在");
			}
			// 进行保存操作
			if (!hasErr) {
				Account account = new Account();
				String addr=request.getHeader("X-Real-IP");
				if(!StringUtils.hasText(addr)){
					addr=request.getHeader("X-Forwarded-For");
					if(!StringUtils.hasText(addr)){
						addr=request.getRemoteAddr();
					}
				}
				account.setCreateTime(new Date());
				account.setNick(cmd.getNick());
				account.setUserid(cmd.getUserid());
				Access ta = accessService.findById(Constants.ROLE_MEMBER);
				Set<Access> accesses = new HashSet<Access>();
				accesses.add(ta);
				account.setAccesses(accesses);
				account.setStuLevel(null);
				account.setOccupation(null);
				account.setSaler(null);
				account.setPassword(accountService.getPasswordEncoder().encodePassword(cmd.getPassword(), ""));
				account.setEmail(cmd.getEmail());
				if (StringUtils.hasText(account.getEmail())) {
					account = accountService.save(account);
				} else {
					account = accountService.save(account);
				}
				cmd.setId(account.getAccountId());
			}
		}
		return cmd;
	}

	@RequestMapping(value = "/success/{id}", method = RequestMethod.GET)
	public String success(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		this.rejectWebServletCfgs(model);
		Account account = accountService.findById(id);
		if (account == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		model.addAttribute("account", account);
		return isMobile(request) ? "register_success_page_mobile"
				: "register_success_page";
	}
	
}
