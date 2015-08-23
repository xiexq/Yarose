package cn.com.yarose.card.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.card.MemberCard;

public class MemberCardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CourseTeacher.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberCard cmd = (MemberCard) target;
		if (cmd.getExpireDate() != null
				&& cmd.getExpireDate().before(new Date())) {
			errors.rejectValue("expireDate", "expireDate.error", "有效期不能在现在之前");
		}
	}
}
