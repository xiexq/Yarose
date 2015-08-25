package cn.com.yarose.card.validator;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.card.MemberCard;

public class MemberCardValidator implements Validator {

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Override
	public boolean supports(Class<?> clazz) {
		return CourseTeacher.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberCard cmd = (MemberCard) target;
		if (cmd.getUserId() != null) {
			List<Account> as = accountService.findByUserId(cmd.getUserId(), -1,
					-1);
			if (as == null || as.size() == 0) {
				errors.rejectValue("userId", "userId.error", "该会员不存在");
			}
		}
		if (cmd.getExpireDate() != null
				&& cmd.getExpireDate().before(new Date())) {
			errors.rejectValue("expireDate", "expireDate.error", "有效期不能在现在之前");
		}
	}
}
