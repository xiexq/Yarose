package cn.com.yarose.card;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.utils.Constants;

public class Appointment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	@FieldMeta(label = "会员名称", editable = false, visible = false, summary = false)
	private String userId;

	@FieldMeta(label = "会员卡号", dictionary = true, editable = false, visible = false, summary = false)
	private MemberCard mCard;

	@FieldMeta(label = "编号", editable = false, visible = false, summary = false)
	public String getCode() {
		if (id == null) {
			return null;
		}
		return Constants.getFormatId(this.id, 5);
	}

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private CourseTeacher courseTeacher;

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private Long courseTeacherId;

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

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private Integer status;

	@FieldMeta(id = true, label = "预约时间", editable = true, datetime = true)
	private Date createTime;

	@FieldMeta(id=true,label="核销时间",editable=true,datetime=true)
	private Date checkTime;
	
	@FieldMeta(id=true,label="核销人",editable=true)
	private String chechUserId;
	
	@FieldMeta(id=true,label="核销人",editable=true)
	private Long accountId;
	
	@FieldMeta(id=true,label="其他消费",editable=true)
	private Float otherConsum;

	@FieldMeta(id = true, label = "备注", editable = true, text = true)
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCourseTeacherId() {
		return courseTeacherId;
	}

	public void setCourseTeacherId(Long courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public String getChechUserId() {
		return chechUserId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public void setChechUserId(String chechUserId) {
		this.chechUserId = chechUserId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
