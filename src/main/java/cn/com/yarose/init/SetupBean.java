package cn.com.yarose.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;

public class SetupBean implements InitializingBean {

	private AccessService accessService;

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

	@Override
	public void afterPropertiesSet() {
		initAccess();
	}
}
