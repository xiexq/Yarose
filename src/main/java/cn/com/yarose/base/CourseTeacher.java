package cn.com.yarose.base;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.eduedu.jee.security.account.Account;

public class CourseTeacher implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	@FieldMeta(label = "任课老师", dictionary = true, visible = true, editable = true, summary = false, required = true)
	private Account teacher;

	@FieldMeta(label = "所属课程", dictionary = true, visible = true, editable = true, summary = false, required = true)
	private Course course;

	@FieldMeta(label = "所属门店", dictionary = true, visible = true, editable = true, summary = false, required = true)
	private Shop shop;
	
	@FieldMeta(label = "所属门店", dictionary = true, visible = true, editable = true, summary = false)
	private Shop searchShop;
	
	@FieldMeta(label = "所属门店", visible = true, editable = true, summary = false)
	public String getShopName() {
		if (shop != null) {
			return shop.getName();
		}
		return "";
	}

	
	@FieldMeta(label = "所属课程", visible = true, editable = true, summary = false)
	public String getCourseName() {
		if (course != null) {
			return course.getName();
		}
		return "";
	}

	/**
	 * 授权人
	 */
	@FieldMeta(label = "授权来源", editable = true, summary = false, visible = false)
	Long authSourceId;

	@FieldMeta(label = "授权人", visible = true, editable = false, summary = false)
	String authSourceAlias;

	@FieldMeta(label = "课程开始时间", datetime = true,time=true, required = true)
	private Date beginTime;

	@FieldMeta(label = "课程结束时间", datetime = true, time=true,required = true)
	private Date endTime;

	@FieldMeta(label = "课程创建时间", datetime = true)
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthSourceId() {
		return authSourceId;
	}

	public void setAuthSourceId(Long authSourceId) {
		this.authSourceId = authSourceId;
	}

	public String getAuthSourceAlias() {
		return authSourceAlias;
	}

	public void setAuthSourceAlias(String authSourceAlias) {
		this.authSourceAlias = authSourceAlias;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	public Shop getSearchShop() {
		return searchShop;
	}


	public void setSearchShop(Shop searchShop) {
		this.searchShop = searchShop;
	}


	public Account getTeacher() {
		return teacher;
	}


	public void setTeacher(Account teacher) {
		this.teacher = teacher;
	}


}
