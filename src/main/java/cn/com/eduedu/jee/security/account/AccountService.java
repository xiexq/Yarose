package cn.com.eduedu.jee.security.account;

import java.util.List;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import cn.com.eduedu.jee.service.BaseSearchService;

public abstract interface AccountService extends
		BaseSearchService<Account, Long> {
	public boolean checkExistAccountByUserID(String paramString);

	public PasswordEncoder getPasswordEncoder();

	public List<Account> findByUserId(String userId, int offset, int count);
	
	public List<Account> listByRole(String role,int offset,int count);

	public List<Account> listByShopId(Long shopId);

	public List<Account> listByUserId(String salerId);

	public long countByUserId(String salerId);

}