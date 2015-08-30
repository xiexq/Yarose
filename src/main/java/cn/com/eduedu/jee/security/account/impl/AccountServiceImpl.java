package cn.com.eduedu.jee.security.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;

public class AccountServiceImpl extends DaoBasedServiceImpl<Account, Long>
		implements AccountService, AuthenticationProvider {
	private PasswordEncoder passwordEncoder;
	private UserDetailsService userService;

	public Account findById(Long id) {
		Account account = (Account) super.findById(id);
		if (account != null) {
			account.getAccessesName();
		}
		return account;
	}

	public Authentication authenticate(Authentication token)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken uptoken = (UsernamePasswordAuthenticationToken) token;
		String userid = (String) uptoken.getPrincipal();
		String password = (String) uptoken.getCredentials();
		Account account = new Account();
		account.setUserid(userid);
		account.setPassword(password);
		account = (Account) getDao().executeQueryUnique("Account.loadByUserid",
				QueryCmdType.QUERY_NAME, new Object[] { userid });
		if ((account != null) && (account.getPassword().equals(password))) {
			account.getAccessesName();
			account.setAuthenticated(true);
			getDao().saveOrUpdate(account);
			if (this.userService != null) {
				account.setDetails(this.userService.loadUserByUsername(userid));
			}
			return account;
		}
		return null;
	}

	public Account save(Account entity) {
		if (entity != null) {
			if ((entity.getAccountId() == null)
					|| (entity.getAccountId().longValue() == -1L)) {
				entity = (Account) getDao().save(entity);
			} else {
				entity = (Account) getDao().saveOrUpdate(entity);
			}
		}
		return entity;
	}

	public boolean supports(Class<? extends Object> clazz) {
		return UsernamePasswordAuthenticationToken.class.equals(clazz);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserService(UserDetailsService userService) {
		this.userService = userService;
	}

	public boolean checkExistAccountByUserID(String userID) {
		return ((Long) getDao().executeQueryUnique("Account.countByUserid",
				QueryCmdType.QUERY_NAME, userID)) > 0;
	}

	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findByUserId(String userId, int offset, int count) {
		return (List<Account>) this.getDao().executeQueryList(
				"Account.findByUserId", QueryCmdType.QUERY_NAME, offset, count,
				userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listByRole(String role, int offset, int count) {
		String hql = "select sa.account_id,sa.user_id from t_sec_account as sa,r_sec_role_access as sra where sa.account_id = sra.role_id and sra.access_id = ?";
		List<Object[]> objs = (List<Object[]>) this.getDao().executeQueryList(
				hql, QueryCmdType.SQL, -1, -1, role);
		List<Account> accounts = new ArrayList<Account>();
		for (Object[] obj : objs) {
			Account account = new Account();
			account.setAccountId(Long.parseLong(obj[0].toString()));
			account.setUserid(obj[1].toString());
			accounts.add(account);
		}
		return accounts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listByShopId(Long shopId) {
		return (List<Account>) this.getDao()
				.executeQueryList("Account.listByShopId",
						QueryCmdType.QUERY_NAME, -1, -1, shopId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listByUserId(String salerId) {
		return (List<Account>) this.getDao().executeQueryList(
				"Account.listByUserId", QueryCmdType.QUERY_NAME, -1, -1,
				salerId);
	}

	@Override
	public long countByUserId(String salerId) {
		return (Long) this.getDao().executeQueryUnique("Account.countByUserId",
				QueryCmdType.QUERY_NAME, salerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listByArea(Long id, int offset, int count) {
		return (List<Account>) this.getDao().executeQueryList(
				"Account.listByArea", QueryCmdType.QUERY_NAME, offset, count,
				id);
	}

	@Override
	public Long countByArea(Long id) {
		return (Long) this.getDao().executeQueryUnique("Account.countByArea",
				QueryCmdType.QUERY_NAME, id);
	}
}