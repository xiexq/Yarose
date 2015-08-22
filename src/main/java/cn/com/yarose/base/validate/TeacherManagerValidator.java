package cn.com.yarose.base.validate;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;

public class TeacherManagerValidator implements Validator {

	private TeacherManagerService tmService;
	
	@Resource(name="tmService")
	public void setTeacherManagerService(TeacherManagerService tmService){
		this.tmService = tmService;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return TeacherManager.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TeacherManager cmd = (TeacherManager)target;
		if(cmd.getEndTime().before(cmd.getBeginTime())){
			errors.rejectValue("endTime", "endTime.error", "必须在开始时间之后");
		}
		List<TeacherManager> tmList = tmService.listByShopId(cmd.getShop().getId());
		if(tmList != null && tmList.size() > 0){
			for(TeacherManager tm : tmList){
				if(cmd.getBeginTime().after(tm.getBeginTime()) && cmd.getBeginTime().before(tm.getEndTime())){
					errors.rejectValue("beginTime", "beginTime.error","设置有冲突,在["+tm.getBeginTime()+"]到["+tm.getEndTime()+"]时间段已安排了课程");
				}
			}
		}
	}

}
