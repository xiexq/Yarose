package cn.com.yarose.card;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.CourseTeacher;

public class Appointment implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	Long id;
	
	@FieldMeta(id=true,label="课程",editable=true,dictionary=true)
	private CourseTeacher courseTeacher;
	
	@FieldMeta(id=true,label="课程",editable=true,dictionary=true)
	private Integer status;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

	public CourseTeacher getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(CourseTeacher courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}