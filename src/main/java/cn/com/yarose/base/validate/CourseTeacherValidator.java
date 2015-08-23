package cn.com.yarose.base.validate;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;

public class CourseTeacherValidator implements Validator {

	private CourseTeacherService courseTeacherService;
	
	@Resource(name="courseTeacherService")
	public void setCourseTeacherService(CourseTeacherService courseTeacherService){
		this.courseTeacherService = courseTeacherService;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return CourseTeacher.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CourseTeacher cmd = (CourseTeacher)target;
		if(cmd.getEndTime().before(cmd.getBeginTime())){
			errors.rejectValue("endTime", "endTime.error", "必须在开始时间之后");
		}
		List<CourseTeacher> tmList = courseTeacherService.listByShopId(cmd.getShop().getId());
		if(tmList != null && tmList.size() > 0){
			for(CourseTeacher tm : tmList){
				if(cmd.getBeginTime().after(tm.getBeginTime()) && cmd.getBeginTime().before(tm.getEndTime())){
					if(cmd.getTeacher().equals(tm.getTeacher())){
						errors.rejectValue("beginTime", "beginTime.error","设置有冲突,在["+tm.getBeginTime()+"]到["+tm.getEndTime()+"]时间段已安排了课程");
					}
				}
			}
		}
		if(cmd.getBeginTime().before(new Date())){
			errors.rejectValue("beginTime", "beginTime.error","在当前时间之前,课程已经开始不能编辑");
		}
	}
}
