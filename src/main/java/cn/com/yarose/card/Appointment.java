package cn.com.yarose.card;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.utils.Constants;

public class Appointment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	@FieldMeta(label = "会员名称", editable = false, autoComplete = true, visible = false, summary = false)
	private String userId;

	private Account userAccount;

	@FieldMeta(label = "会员卡号", dictionary = true, editable = false, visible = false, summary = false)
	private MemberCard mCard;

	@FieldMeta(label = "预约编号", editable = false, visible = false, summary = false)
	public String getCode() {
		if (id == null) {
			return null;
		}
		return Constants.getFormatId(id, 5);
	}

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private CourseTeacher courseTeacher;

	@FieldMeta(id = true, label = "课时", editable = true)
	public Integer getLesson() {
		if (courseTeacher != null) {
			return courseTeacher.getLesson();
		}
		return null;
	}

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private Long courseTeacherId;

	@FieldMeta(id = true, label = "状态", editable = true, dictionary = true)
	private Integer status;

	@FieldMeta(id = true, label = "预约时间", editable = true, datetime = true)
	private Date createTime;

	@FieldMeta(id = true, label = "核销时间", editable = true, datetime = true)
	private Date checkTime;

	@FieldMeta(id = true, label = "核销人", editable = true)
	private String checkUserId;

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

	@FieldMeta(label = "微信号", visible = false, editable = false, summary = false)
	public String getUserWeiXin() {
		if (userAccount != null) {
			return userAccount.getWeixin();
		}
		return "";
	}

	@FieldMeta(label = "昵称", visible = false, editable = false, summary = false)
	public String getUserNick() {
		if (userAccount != null) {
			return userAccount.getNick();
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

	public Date getCourseBeginTime() {
		if (courseTeacher != null) {
			return courseTeacher.getBeginTime();
		}
		return null;
	}

	public Date getCourseEndTime() {
		if (courseTeacher != null) {
			return courseTeacher.getEndTime();
		}
		return null;
	}

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

	public Account getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
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

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
}
