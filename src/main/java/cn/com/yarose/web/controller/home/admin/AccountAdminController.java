package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/account")
@CRUDControllerMeta(title = "账号管理", service = AccountService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true)
public class AccountAdminController extends
		BaseCRUDControllerExt<Account, Long> {

	private AccessService accessService;
	private static Map<String, Boolean> _editRequiredFields;
	static {
		_editRequiredFields = new HashMap<String, Boolean>();
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
	public Map<String, String> customEditFieldGroupNames(
			HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher", "老师信息");
		return map;
	}

	@Override
	public Account customSaveCmd(Account cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd = super.customSaveCmd(cmd, request, id);
		cmd.setCreateTime(new Date());
		return cmd;
	}

	@Override
	public Account customSave(Account cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			// 判断是否具有老师的权限
			Access access = accessService.findById("TEACHER");
			if (!cmd.getAccesses().contains(access)) {
				cmd.setTeachLevel(null);
				cmd.setCourseFee(null);
				cmd.setAddress(null);
			} else {
				if (cmd.getTeachLevel() == null) {
					result.rejectValue("teachLevel", "required", "不能为空");
				}
				if (cmd.getCourseFee() == null) {
					result.rejectValue("courseFee", "required", "不能为空");
				}
				if (!StringUtils.hasText(cmd.getAddress())) {
					result.rejectValue("address", "required", "不能为空");
				}
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
			boolean create) {
		// 分为系统管理员和门店管理员
		// "teachLevel", "courseFee", "address",老师专用
		// "stuLevel" saler 会员专用
		return this.generateStringSortedSet("userid", "password", "nick",
				"shop", "weixin", "phone", "email", "birthday", "teachLevel",
				"courseFee", "address", "accesses");
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userid", "password", "nick",
				"email", "accesses", "weixin");
	}
}
