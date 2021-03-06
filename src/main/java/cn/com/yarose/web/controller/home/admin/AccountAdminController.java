package cn.com.yarose.web.controller.home.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/account")
@CRUDControllerMeta(title = "账号管理", service = AccountService.class, listable = true, clientValidation = true, createable = true, editable = true, deleteable = true, searchable = true, viewable = true)
public class AccountAdminController extends
		BaseCRUDControllerExt<Account, Long> {

	@Resource(name = "account_accessService")
	private AccessService accessService;

	@Resource(name = "shopService")
	private ShopService shopService;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Override
	public Map<String, String> customEditFieldGroupNames(
			HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("teacher", "老师信息");
		map.put("member", "会员信息");
		return map;
	}

	@Override
	public Account customSaveCmd(Account cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd = super.customSaveCmd(cmd, request, id);
		if (id == null) {
			cmd.setCreateTime(new Date());
		}
		return cmd;
	}

	@Override
	public Account customSave(Account cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			if (create) {
				boolean exist = ((AccountService) this.getCrudService())
						.checkExistAccountByUserID(cmd.getUserid());
				if (exist) {
					result.rejectValue("userid", "invalidate", "已被使用");
					return cmd;
				}
			}
			// 判断是否具有老师的权限
			Access ta = accessService.findById(Constants.ROLE_TEACHER);
			if (!cmd.getAccesses().contains(ta)) {
				cmd.setTeachLevel(null);
				cmd.setCourseFee(null);
				cmd.setAddress(null);
			} else {
				if (cmd.getTeachLevel() == null) {
					result.rejectValue("teachLevel", "required", "不能为空");
				} else {
					// 老师需要设置课时费
					Dictionary dict = dictionaryService.findById(cmd
							.getTeachLevel());
					cmd.setCourseFee(dict.getCourseFee());
				}
				if (cmd.getCourseFee() == null) {
					result.rejectValue("courseFee", "required", "不能为空");
				}
				if (!StringUtils.hasText(cmd.getAddress())) {
					result.rejectValue("address", "required", "不能为空");
				}
			}
			Access ma = accessService.findById(Constants.ROLE_MEMBER);
			if (!cmd.getAccesses().contains(ma)) {
				cmd.setStuLevel(null);
				cmd.setOccupation(null);
				cmd.setSaler(null);
			} else {
				if (cmd.getStuLevel() == null) {
					result.rejectValue("stuLevel", "required", "不能为空");
				}
			}
			if (result.getFieldErrorCount() == 0) {
				this.getCrudService().save(cmd);
				cmd.getAccessesName();
			}
		}
		return cmd;
	}

	@Override
	public List<Account> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		if (!isInRole(Constants.ROLE_SUPER)) {
			Shop shop = this.getAccount().getShop();
			if (shop != null && shop.getArea() != null) {
				return ((AccountService) this.getCrudService()).listByArea(shop
						.getArea().getId(), offset, count);
			}
		}
		return super.customList(offset, count, orders, request);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		if (!isInRole(Constants.ROLE_SUPER)) {
			Shop shop = this.getAccount().getShop();
			if (shop != null && shop.getArea() != null) {
				return ((AccountService) this.getCrudService())
						.countByArea(shop.getArea().getId());
			}
		}
		return super.customCount(request);
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		String isShop = request.getParameter("_shop");
		if (StringUtils.hasText(isShop)) {
			return this.generateStringSortedSet("userid", "nick", "weixin",
					"saler", "referee");
		}
		return this.generateStringSet("shop", "userid", "nick", "weixin",
				"saler", "referee");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) {
		return this.generateStringSortedSet("userid", "password", "nick",
				"shop", "weixin", "phone", "email", "birthday", "remark",
				"teachLevel", "address", "accesses", "occupation", "stuLevel",
				"saler", "referee");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userid", "password", "nick",
				"email", "accessesName", "weixin");
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _stuLevels(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_STU_LEVEL,
				-1, -1);
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _teachLevels(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(
				Constants.DICT_TYPE_TEACH_LEVEL, -1, -1);
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_salers(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			AccountService as = (AccountService) this.getCrudService();
			return as.findByUserId(value + "%", 0, 10);
		}
		return null;
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_referees(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			AccountService as = (AccountService) this.getCrudService();
			return as.findByUserId(value + "%", 0, 10);
		}
		return null;
	}

	@DictionaryModel(label = "name", val = "id", header = true, headerIsJustForSearch = true, headerLabel = "请选择")
	public List<Shop> _shops(HttpServletRequest request) {
		return shopService.listAll(-1, -1);
	}

	@DictionaryModel(val = "id", label = "name")
	public Collection<Access> _accessess(HttpServletRequest request) {
		List<Access> as = accessService.listAll(-1, -1);
		String isShop = this.getParameter(request, "_shop");
		if (StringUtils.hasText(isShop)) {
			List<Access> list = new ArrayList<Access>();
			for (Access access : as) {
				if (!Constants.ROLE_SUPER.equals(access.getId())) {
					list.add(access);
				}
			}
			return list;
		}
		return as;
	}
}
