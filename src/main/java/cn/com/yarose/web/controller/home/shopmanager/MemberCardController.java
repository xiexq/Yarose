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
	
	/**
	 * 
	 * 	<property name="type" column="type" not-null="true"></property>
		<property name="purchaseLesson" column="purchase_lesson" not-null="true"></property>
		<property name="userId" column="user_id" not-null="true"></property>
		<property name="totalLesson" column="total_lesson" not-null="true"></property>
		<property name="seqNum" column="seq_num" not-null="true"></property>
		<property name="cardNo" column="card_no" not-null="true"></property>
		<property name="givingLesson" column="giving_lesson"></property>
		<property name="usedLesson" column="used_lesson"></property>
		<property name="price" column="price" not-null="true"></property>
		<property name="expireDate" column="expireDate"></property>
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public Set<String> customEditFields(HttpServletRequest arg0, boolean arg1)
			throws Exception {
		return this.generateStringSet("userId","type","purchaseLesson","givingLesson","expireDate","price");
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSet("");
	}
	
	
	@DictionaryModel(label = "name", val = "id")
	public List<Dictionary> _stuLevels(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_STU_LEVEL,
				-1, -1);
	}
}
