package cn.com.yarose.base;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class Course implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	private Long id;
	
	private String name;
	
	private Dictionary dictionary;
	@FieldMeta(label = "课程开始时间", order = 18, datetime = true, required = true)
	private Date biginTime;
	private Date endTime;
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
}