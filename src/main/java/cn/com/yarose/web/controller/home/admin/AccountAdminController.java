package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yarose.web.controller.BaseCRUDControllerExt;
import cn.com.eduedu.jee.entity.NameValueBean;
import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountHelper;
import cn.com.eduedu.jee.security.account.AccountService;

@Controller
@RequestMapping("/home/admin/account")
@CRUDControllerMeta(title = "账号管理", service = AccountService.class, listable = true, createable = true, editable = true, deleteable = true,viewable=true)
public class AccountAdminController extends
		BaseCRUDControllerExt<Account, Long> {

	private AccessService accessService;
	private static Map<String,Boolean> _editRequiredFields;
	static{
		_editRequiredFields=new HashMap<String,Boolean>();
		_editRequiredFields.put("email", true);
	}

	@Resource(name = "account_accessService")
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}

	@DictionaryModel(val = "id", label = "name")
	public Collection<Access> _accessess(HttpServletRequest request) {
		return accessService.listAll(-1, -1);
	}

	@Override
	public Account customSaveCmd(Account cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd= super.customSaveCmd(cmd, request, id);
		cmd.setUserid(cmd.getEmail());
		return cmd;
	}

	@Override
	public Account customSave(Account cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if(this.validate(cmd, result, request, create)){
			if (cmd.getAccountId() == null) {
				String newPass = cmd.getPassword();
				PasswordEncoder passwordEncoder = ((AccountService) this
						.getCrudService()).getPasswordEncoder();
				if (passwordEncoder != null) {
					cmd.setPassword(passwordEncoder.encodePassword(newPass, null));
				}
				cmd.setCreateTime(new Date());
			}
			return this.getCrudService().save(cmd);
		}

		return cmd;
	}

	@Override
	public Map<String, Boolean> customFieldsRequired(
			HttpServletRequest request, boolean search) throws Exception {
		return _editRequiredFields;
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {

		if (create)
			return this.generateStringSortedSet("email", "password","nick",
					"expireTime", "status", "accesses");
		else
			return this.generateStringSortedSet("email", "nick",
					"expireTime", "status", "accesses");
	}

	public Collection<NameValueBean> _statuss(HttpServletRequest request) {
		return AccountHelper.getStatusNames();
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("email", "password","nick",
        "expireTime", "status", "accesses");
	}

}
