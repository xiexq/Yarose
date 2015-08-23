package cn.com.yarose.web.controller.home.shopmanager;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shopmanager/membercard")
@CRUDControllerMeta(title = "会员卡管理", service = MemberCardService.class, listable = true, editable = true, createable = true, countable = true, searchable = true)
public class MemberCardController extends
		BaseCRUDControllerExt<MemberCard, Long> {
	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Override
	public Set<String> customEditFields(HttpServletRequest arg0, boolean arg1)
			throws Exception {
		return this.generateStringSortedSet("userId", "type", "purchaseLesson",
				"givingLesson", "totalLesson", "expireDate", "price");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("cardNo", "userId", "typeName",
				"purchaseLesson", "givingLesson", "expireDate", "usedLesson",
				"totalLesson", "price");
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSet("cardNo", "userId", "typeName",
				"purchaseLesson", "givingLesson", "expireDate", "usedLesson",
				"totalLesson", "price", "creatorName", "createDate");
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_salers(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			return accountService.findByUserId(value + "%", 0, 10);
		}
		return null;
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _stuLevels(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_STU_LEVEL,
				-1, -1);
	}
}
