package cn.com.yarose.web.controller.home.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.eduedu.jee.security.exception.NoRightsException;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/shops")
@CRUDControllerMeta(title = "门店管理", service = ShopService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true)
public class ShopAdminController extends BaseCRUDControllerExt<Shop, Long> {

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("name", "area", "address", "phone",
				"desc");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("name", "areaName", "address", "phone");
	}

	@Override
	public Shop customSave(Shop cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			Shop shop = ((ShopService) this.getCrudService()).finByName(cmd
					.getName());
			if (create) {
				if (shop != null) {
					result.rejectValue("name", "invalidate", "已经存在!");
					return cmd;
				}
			} else {
				if (shop != null && !shop.getId().equals(cmd.getId())) {
					result.rejectValue("name", "invalidate", "已经存在!");
					return cmd;
				}
			}
			return this.getCrudService().save(cmd);
		}
		return cmd;
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _areas(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_AREA, -1,
				-1);
	}

	@Override
	public void customDelete(Long id, HttpServletRequest request)
			throws Exception {
		List<CourseTeacher> ctList = courseTeacherService.listByShopId(id);
		if (ctList != null && ctList.size() > 0) {
			throw new NoRightsException("该店铺下有课程不能删除！");
		}
		List<Account> accountList = accountService.listByShopId(id);
		if (accountList != null && accountList.size() > 0) {
			throw new NoRightsException("该店铺下有会员不能删除！");
		}
		super.customDelete(id, request);
	}
}
