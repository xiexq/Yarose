package cn.com.eduedu.jee.security.account;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class DefaultAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory
			.getLogger(DefaultAuthenticationProvider.class);
	private UserDetailsService userService;
	private PasswordEncoder passwordEncoder;
	private String username;
	private String password;
	private Set<Access> defaultAccesses;

	public void setUserService(UserDetailsService userService) {
		this.userService = userService;
	}

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken uptoken = (UsernamePasswordAuthenticationToken) authentication;
		String uname = (String) uptoken.getPrincipal();
		String pass = (String) uptoken.getCredentials();
		if ((this.username.equals(uname)) && (this.password.equals(pass))) {
			Account account = new Account();
			account.setAuthenticated(true);
			account.setAccountId(Long.valueOf(-1L));
			Date now = new Date();
			account.setCreateTime(now);
			account.setNick(this.username);
			account.setPassword(this.password);
			account.setUserid(this.username);
			account.setAccesses(this.defaultAccesses);
			if (this.userService != null) {
				account.setDetails(this.userService.loadUserByUsername(uname));
			}
			return account;
		}
		return null;
	}

	public boolean supports(Class<? extends Object> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setDefaultAccesses(String defaults) {
		this.defaultAccesses = new HashSet();
		if (defaults != null) {
			String[] roles = defaults.split(",");
			for (String r : roles) {
				r = r.trim();
				if (!r.equals("")) {
					Access access = new Access();
					access.setId(r);
					access.setName(r);
					this.defaultAccesses.add(access);
				}
			}
		}
	}
}