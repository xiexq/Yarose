package cn.com.yarose.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.yarose.base.DictCategory;
import cn.com.yarose.base.DictCategoryService;

public class SetupBean implements InitializingBean {

	private DictCategoryService dictCategoryService;

	private AccessService accessService;

	@Resource
	public void setDictCategoryService(DictCategoryService dictCategoryService) {
		this.dictCategoryService = dictCategoryService;
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
			asList.add(new Access("SUPER", "超级管理员"));
			asList.add(new Access("SHOP_MANAGER", "店长"));
			asList.add(new Access("TEACHER", "老师"));
			asList.add(new Access("SALER", "营销人员"));
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
			dcList.add(new DictCategory("会员类型管理"));
			dcList.add(new DictCategory("教师等级管理"));
			dcList.add(new DictCategory("舞种管理"));
			for (DictCategory dc : dcList) {
				//dictCategoryService.save(dc);
			}
		}
	}

	@Override
	public void afterPropertiesSet() {
		initAccess();
		initDictCategory();
	}
}
