package cn.com.yarose.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.DictCategory;
import cn.com.yarose.base.DictCategoryService;
import cn.com.yarose.utils.Constants;

public class SetupBean implements InitializingBean {

	private DictCategoryService dictCategoryService;
	private AccessService accessService;
	private AccountService accountService;

	@Resource
	public void setDictCategoryService(DictCategoryService dictCategoryService) {
		this.dictCategoryService = dictCategoryService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}

	/**
	 * 初始化系统权限
	 */
	private void initAccess() {
		Long as = accessService.countAll();
		if (as == 0) {
			List<Access> asList = new ArrayList<Access>();
			asList.add(new Access(Constants.ROLE_SUPER, "系统管理员"));
			asList.add(new Access(Constants.ROLE_SHOP_MANAGER, "店长"));
			asList.add(new Access(Constants.ROLE_TEACHER, "老师"));
			asList.add(new Access(Constants.ROLE_SALER, "营销人员"));
			asList.add(new Access(Constants.ROLE_MEMBER, "会员"));
			for (Access access : asList) {
				accessService.save(access);
			}
		}
	}

	/**
	 * 初始化字典种类
	 */
	private void initDictCategory() {
		Long as = dictCategoryService.countAll();
		if (as == 0) {
			List<DictCategory> dcList = new ArrayList<DictCategory>();
			dcList.add(new DictCategory("会员等级", Constants.DICT_TYPE_STU_LEVEL));
			dcList.add(new DictCategory("会员卡类型", Constants.DICT_TYPE_MEMBER_CARD_TYPE));
			dcList.add(new DictCategory("教师等级", Constants.DICT_TYPE_TEACH_LEVEL));
			dcList.add(new DictCategory("舞种", Constants.DICT_TYPE_DANCE));
			for (DictCategory dc : dcList) {
				dictCategoryService.save(dc);
			}
		}
	}

	/**
	 * 初始化超级管理员
	 */
	private void initSuperMan() {
		Long as = accountService.countAll();
		if (as == 0) {
			Account account = new Account();
			account.setUserid("superman");
			account.setEmail("112@qq.com");
			account.setWeixin("123456");
			account.setNick("超级管理员");
			account.setPassword("123456");
			Access access = accessService.findById(Constants.ROLE_SUPER);
			Set<Access> accesses = new HashSet<Access>();
			accesses.add(access);
			account.setAccesses(accesses);
			accountService.save(account);
		}
	}

	@Override
	public void afterPropertiesSet() {
		initAccess();
		initDictCategory();
		initSuperMan();
	}
}
