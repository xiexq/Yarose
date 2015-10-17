package cn.com.yarose.web.controller.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.eduedu.jee.security.tenant.Tenant;
import cn.com.eduedu.jee.security.tenant.TenantHelper;
import cn.com.eduedu.jee.util.StringUtils;
import cn.com.yarose.register.RegisterForm;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;
import cn.com.yarose.web.controller.WebRequestParams;

@Controller
@RequestMapping("/home/register")
@CRUDControllerMeta(title = "注册", createable = true, enableCaptcha = true, clientValidation = true)
public class WebRegisterController extends
		BaseCRUDControllerExt<RegisterForm, Long> {
	
	@Resource(name="account_accountService")
	AccountService accountService;
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		if (this.isMobile(request)) {
			return this.generateStringSortedSet("userid", "password",
					"passwordConfirm");
		} else {
			return this.generateStringSortedSet("userid", "password",
					"passwordConfirm", "email", "nick");
		}

	}
	
	public String _rv_userids(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value) && accountService.checkExistAccountByUserID(value)) {
			return "已经被使用";
		}
		return null;
	}

//	public List<String> _ac_emails(String query, HttpServletRequest request) {
//		if (query != null && query.length() > 0) {
//			String input = null;
//			int idx = query.indexOf('@');
//			if (idx != -1) {
//				input = query.substring(idx + 1);
//				query = query.substring(0, idx);
//			}
//			List<String> emailServers = config.getEmailServers();
//			List<String> beans = new ArrayList<String>();
//			for (String s : emailServers) {
//				if (input == null || s.indexOf(input) == 1) {
//					beans.add(query + s);
//				}
//			}
//			return beans;
//		}
//		return null;
//	}

	/**
	 * 注册用户信息保存
	 */
	@Override
	public RegisterForm customSave(RegisterForm cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, true)) {
			WebRequestParams params = WebRequestParams.buildFromRequest(
					request, null);
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
				// 进行账号的填写和验证激活码的生成，并且进行保存
				Tenant tenant = TenantHelper.getTenantFromRequest(request);
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
				account.setPassword(accountService.getPasswordEncoder().encodePassword(cmd.getPassword(), ""));
				account.setEmail(cmd.getEmail());
				//account.setRegisterReferer(params.getRegisterReferer());
				if (StringUtils.hasText(account.getEmail())) {
					account = accountService.save(account);
					//this.sendVerifyCode(account, false, params, tenant, request);
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
	
//	private String sendVerifyCode(Account account, boolean validateCount,
//			WebRequestParams params, Tenant tenant, HttpServletRequest request)
//			throws Exception {
//		Integer count = account.getSendVerifyCodeCount();
//		if (validateCount) {
//			if (count >= 3) {
//				return "您最多只能发送3次激活码！要再次激活此账号请与管理员联系。";
//			}
//		}
//		if (count == null) {
//			count = 0;
//		}
//		// 向任务队列里面加入一个发邮件任务
//		String serverName = "MailServer";
//		Map<String, Object> values = new HashMap<String, Object>();
//		String token = accountService.generateEnableString(account
//				.getAccountId());
//		values.put("userNick", account.getNick());
//		String path = "/web/register/enabled?t=" + token + "&service="
//				+ params.getService();
//		path = path + "&_tenant=" + tenant.getCode();
//		values.put("enableLink",
//				TenantHelper.getAppServiceUrlFromRequest(request) + path);
//		String appTitle = tenant.getName()
//				+ this.getApplicationContext().getMessage("app.title", null,
//						"CAS", LocaleContextHolder.getLocale());
//		values.put("appTitle", appTitle);
//		SimpleEMailMessage m = new SimpleEMailMessage();
//		if (StringUtils.hasText(tenant.getServiceEmail())
//				&& StringUtils.hasText(tenant.getServiceEmailSmtpServer())) {
//			m.setFrom(tenant.getServiceEmail());
//			serverName = tenant.getCode();
//			// 注册一个服务器
//			this.mailServerRegistry
//					.addServer(new SMTPMailServer(tenant.getServiceEmail(),
//							tenant.getServiceEmailSmtpServer(),
//							StringUtils.hasText(tenant
//									.getServiceEmailSmtpUsername()) ? tenant
//									.getServiceEmailSmtpUsername() : tenant
//									.getServiceEmail(), tenant
//									.getServiceEmailSmtpPassword(), StringUtils
//									.hasText(tenant
//											.getServiceEmailSmtpPassword()),
//							tenant.getCode()));
//		}
//		m.setSubject(appTitle + "注册激活");
//		m.setTo(account.getEmail());
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		templateService.render(new FileInputStream(this.config.getTemplateDir()
//				+ File.separator + "enable_account.vm"), out, values);
//		m.setText(out.toString("utf-8"));
//		MailSenderTask task = new MailSenderTask(mailSender, serverName, m,
//				true);
//		taskScheduler.schedule(task, new Date());
//		return null;
//	}
	
	
}
