package cn.com.eduedu.jee.security.account.impl;

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
		return Long.parseLong((String) getDao().executeQueryUnique(
				"Account.countByUserid", QueryCmdType.QUERY_NAME,
				new Object[] { userID })) > 0L;
	}

	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}
}