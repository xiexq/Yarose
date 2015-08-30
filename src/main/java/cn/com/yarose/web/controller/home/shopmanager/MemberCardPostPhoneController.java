package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.util.StringUtils;
import cn.com.yarose.card.CardPostPhone;
import cn.com.yarose.card.CardPostPhoneService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shopmanager/membercard/postphone")
@CRUDControllerMeta(title = "会员卡延期", service = CardPostPhoneService.class, listable = true, editable = true, createable = true, deleteable = true)
public class MemberCardPostPhoneController extends
		BaseCRUDControllerExt<CardPostPhone, Long> {

	@Resource(name = "memberCardService")
	MemberCardService memberCardService;

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSet("price", "postPhoneDate", "remark");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSet("price", "postPhoneDate", "creatorName",
				"createDate", "remark");
	}

	@Override
	public List<CardPostPhone> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		String cardId = request.getParameter("_card");
		if (StringUtils.hasText(cardId)) {
			CardPostPhoneService service = ((CardPostPhoneService) this
					.getCrudService());
			return service.listByCardId(Long.parseLong(cardId), -1, -1);
		}
		return null;
	}

	@Override
	public CardPostPhone customSave(CardPostPhone cmd, BindingResult result,
			HttpServletRequest request, ResponseObject responseObject,
			boolean create) throws Exception {
		String cardIdStr = request.getParameter("_card");
		if (StringUtils.hasText(cardIdStr) && create) {
			Long cardId = Long.parseLong(cardIdStr);
			// 更新会员卡
			MemberCard card = memberCardService.findById(cardId);
			card.setExpireDate(cmd.getPostPhoneDate());
			Float postPhoePrice = cmd.getPrice() + card.getPostPhoePrice();
			card.setPostPhoePrice(postPhoePrice);
			memberCardService.save(card);
			cmd.setCardId(cardId);
			cmd.setCreator(this.getAccount().getAccountId());
			cmd.setCreatorName(this.getAccount().getUserid());
			cmd.setCreateDate(new Date());
			this.getCrudService().save(cmd);
		}
		return cmd;
	}
}
