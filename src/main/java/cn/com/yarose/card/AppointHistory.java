package cn.com.yarose.card;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.utils.Constants;

public class AppointHistory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@FieldMeta(label = "会员名称", editable = false, visible = false, summary = false)
	private String userId;

	@FieldMeta(label = "会员卡号", dictionary = true, editable = false, visible = false, summary = false)
	private MemberCard mCard;

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private CourseTeacher courseTeacher;

	@FieldMeta(id = true, label = "预约时间", editable = true, datetime = true)
	private Date createTime;

	@FieldMeta(id = true, label = "其他消费", editable = true)
	private Float otherConsum;

	@FieldMeta(id = true, label = "备注", editable = true, text = true)
	private String remark;

	@FieldMeta(label = "会员卡号", visible = true, editable = true, summary = false)
	public String getCardNo() {
		if (mCard != null) {
			return mCard.getCardNo();
		}
		return "";
	}

	@FieldMeta(label = "所属门店", visible = true, editable = true, summary = false)
	public String getShopName() {
		if (courseTeacher != null) {
			return courseTeacher.getShopName();
		}
		return "";
	}

	@FieldMeta(label = "所属课程", visible = true, editable = true, summary = false)
	public String getCourseName() {
		if (courseTeacher != null) {
			return courseTeacher.getCourseName();
		}
		return "";
	}

	@FieldMeta(label = "授课老师", visible = true, editable = true, summary = false)
	public String getTeacherName() {
		if (courseTeacher != null) {
			return courseTeacher.getTeacherName();
		}
		return "";
	}

	@FieldMeta(label = "编号", editable = false, visible = false, summary = false)
	public String getCode() {
		if (id == null) {
			return null;
		}
		return Constants.getFormatId(this.id, 5);
	}

	public CourseTeacher getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(CourseTeacher courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public MemberCard getmCard() {
		return mCard;
	}

	public void setmCard(MemberCard mCard) {
		this.mCard = mCard;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getOtherConsum() {
		return otherConsum;
	}

	public String getRemark() {
		return remark;
	}

	public void setOtherConsum(Float otherConsum) {
		this.otherConsum = otherConsum;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}