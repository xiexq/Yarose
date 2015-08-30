package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Calendar;
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
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.card.validator.MemberCardValidator;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shopmanager/membercard")
@CRUDControllerMeta(title = "会员卡管理", service = MemberCardService.class, listable = true, editable = true, createable = true, countable = true, searchable = true, deleteable = true, validator = MemberCardValidator.class)
public class MemberCardController extends
		BaseCRUDControllerExt<MemberCard, Long> {
	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Resource(name = "shopService")
	private ShopService shopService;

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		if (isInRole(Constants.ROLE_SUPER)) {
			return this.generateStringSortedSet("userId", "type",
					"purchaseLesson", "givingLesson", "expireDate", "price",
					"shop");
		}
		return this.generateStringSortedSet("userId", "type", "purchaseLesson",
				"givingLesson", "expireDate", "price");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("cardNo", "userId", "typeName",
				"purchaseLesson", "givingLesson", "usedLesson", "totalLesson",
				"expireDate", "price");
	}

	@Override
	public List<MemberCard> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		if (!isInRole(Constants.ROLE_SUPER)) {
			Shop shop = this.getAccount().getShop();
			if (shop != null && shop.getArea() != null) {
				return ((MemberCardService) this.getCrudService()).listByArea(
						shop.getArea().getId(), offset, count);
			}
		}
		return super.customList(offset, count, orders, request);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		if (!isInRole(Constants.ROLE_SUPER)) {
			Shop shop = this.getAccount().getShop();
			if (shop != null && shop.getArea() != null) {
				return ((MemberCardService) this.getCrudService())
						.countByArea(shop.getArea().getId());
			}
		}
		return super.customCount(request);
	}

	@Override
	public Map<String, Boolean> customFieldsRequired(
			HttpServletRequest request, boolean search) throws Exception {
		if (isInRole(Constants.ROLE_SUPER)) {
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			map.put("shop", true);
			return map;
		}
		return super.customFieldsRequired(request, search);
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("cardNo", "userId", "type");
	}

	@Override
	public MemberCard customSaveCmd(MemberCard cmd, HttpServletRequest request,
			Long id) throws Exception {
		super.customSaveCmd(cmd, request, id);
		// 总时间=购买课时+赠送课时
		Integer totalLesson = cmd.getPurchaseLesson() + cmd.getGivingLesson();
		cmd.setTotalLesson(totalLesson);
		return cmd;
	}

	@Override
	public MemberCard customSave(MemberCard cmd, BindingResult result,
			HttpServletRequest request, ResponseObject responseObject,
			boolean create) throws Exception {
		if (this.validate(cmd, result, request, create)) {
			// 门店管理员需要设置门店
			boolean isShop = getIsShop(request);
			if (isShop) {
				Long shopId = this.getAccount().getShop().getId();
				Shop shop = shopService.findById(shopId);
				cmd.setShop(shop);
			}
			if (create) {  
				cmd.setCreateDate(new Date());
				cmd.setCreator(this.getAccount());
				// 生成卡号规则（年份+月份+门店id+序列号）
				if (cmd.getShop() != null && cmd.getType() != null) {
					MemberCardService service = ((MemberCardService) this
							.getCrudService());
					Long seqNum = service.findMaxSeqNum();
					Calendar now = Calendar.getInstance();
					int year = now.get(Calendar.YEAR);
					String month = Constants.getFormatId(
							(long) (now.get(Calendar.MONTH) + 1), 2);
					String shopId = Constants.getFormatId(
							cmd.getShop().getId(), 3);
					String cardNo = "" + year + month + shopId + (seqNum + 1);
					cmd.setSeqNum(seqNum + 1);
					cmd.setCardNo(cardNo);
				}
			}
			cmd = this.getCrudService().save(cmd);
		}
		return cmd;
	}

	private boolean getIsShop(HttpServletRequest request) {
		String isShop = request.getParameter("_shop");
		if (StringUtils.hasText(isShop)) {
			return true;
		}
		return false;
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this
				.generateStringSet("cardNo", "userId", "typeName",
						"purchaseLesson", "givingLesson", "expireDate",
						"usedLesson", "totalLesson", "price", "creatorName",
						"shopName", "createDate");
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_userIds(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			return accountService.findByUserId(value + "%", 0, 10);
		}
		return null;
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _types(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(
				Constants.DICT_TYPE_MEMBER_CARD_TYPE, -1, -1);
	}

	@DictionaryModel(header = true, headerLabel = "请选择")
	public Collection<Shop> _shops(HttpServletRequest request) {
		if (isInRole(Constants.ROLE_SUPER)) {
			return shopService.listAll(-1, -1);
		}
		return null;
	}
}
