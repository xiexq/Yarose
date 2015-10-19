package cn.com.eduedu.jee.security.account;

import java.util.List;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import cn.com.eduedu.jee.service.BaseSearchService;

public abstract interface AccountService extends
		BaseSearchService<Account, Long> {
	public boolean checkExistAccountByUserID(String paramString);

	public PasswordEncoder getPasswordEncoder();

	public List<Account> findByUserId(String userId, int offset, int count);

	public List<Account> listByRole(String role, int offset, int count);

	public List<Account> listByShopId(Long shopId);

	public List<Account> listByUserId(String salerId);

	public long countByUserId(String salerId);

	public List<Account> listByArea(Long id, int offset, int count);

	Long countByArea(Long id);

	public boolean getExistAccountByNick(String nick);
	/**
	 * 生成激活账户的一个随机串，邮箱用户和手机用户返回串不一样
	 * @param id
	 * @return
	 */
	String generateEnableString(Long id);

	Account findByUserId(String userId);
}
