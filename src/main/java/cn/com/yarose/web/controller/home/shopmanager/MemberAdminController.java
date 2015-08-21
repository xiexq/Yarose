package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shop/member")
@CRUDControllerMeta(title = "会员管理", service = AccountService.class, listable = true, createable = true, editable = true, deleteable = true, searchable = true, viewable = true)
public class MemberAdminController extends BaseCRUDControllerExt<Account, Long> {

	@Resource(name = "shopService")
	ShopService shopService;

	@Resource(name = "dictionaryService")
	DictionaryService dictionaryService;

	@Override
	public Account customSaveCmd(Account cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd = super.customSaveCmd(cmd, request, id);
		cmd.setCreateTime(new Date());
		return cmd;
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSet("shop", "userId", "nick", "weixin");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) {
		return this.generateStringSortedSet("userid", "password", "nick",
				"shop", "weixin", "phone", "stuLevel", "type", "occupation",
				"email", "birthday", "saler");
	}

	@DictionaryModel(label = "name", val = "id", header = true, headerLabel = "请选择")
	public List<Shop> _shops(HttpServletRequest request) {
		return shopService.listAll(-1, -1);
	}

	@DictionaryModel(label = "name", val = "id", header = true, headerLabel = "请选择")
	public List<Dictionary> _stuLevels(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_STU_LEVEL,
				-1, -1);
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userid", "password", "nick",
				"email", "accesses", "weixin");
	}
}
