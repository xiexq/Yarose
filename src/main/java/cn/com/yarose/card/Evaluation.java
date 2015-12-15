package cn.com.yarose.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import cn.com.eduedu.jee.entity.NameValueBean;
import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.utils.Constants;

public class Evaluation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	@FieldMeta(id = true, label = "评价人", editable = true)
	private Account account;

	@FieldMeta(id = true, label = "课程", editable = true, dictionary = true)
	private CourseTeacher courseTeacher;

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

	@FieldMeta(label = "评论详情", visible = true, editable = true, summary = false, text = true)
	private String content;

	@FieldMeta(label = "评价人", visible = true, editable = true, summary = false)
	public String getAccountName() {
		if (account != null) {
			return account.getUserid();
		}
		return "";
	}

	/**
	 * 会员等级名称
	 */
	private String stuLevelName;

	/**
	 * 冗余字段，用于显示账号和会员等级
	 * 
	 * @return
	 */
	@FieldMeta(label = "评价人", visible = true, editable = true, summary = false)
	public String getAccountNameAndLevelName() {
		if (account != null) {
			String accountName = Constants.getHideName(account.getUserid());
			//TODO保存的时候需要保存一下stuLevelName
			if (StringUtils.hasText(stuLevelName)) {
				accountName = accountName + "（" + stuLevelName + "）";
			}
			return accountName;
		}
		return "";
	}

	@FieldMeta(id = true, label = "类型", editable = true, dictionary = true)
	private Integer type;

	@FieldMeta(label = "授权类型", order = 1, editable = false)
	public String getEvaluationTypeName() {
		return Constants.getevaluationType(type);
	}

	@FieldMeta(id = true, label = "评价时间", editable = true)
	private Date createTime;

	@FieldMeta(id = true, label = "星级", editable = true, dictionary = true)
	private Integer level;
	
	public static List<NameValueBean> getLevelDicts() {
		List<NameValueBean> list = new ArrayList<NameValueBean>();
		list.add(new NameValueBean("一星", "1"));
		list.add(new NameValueBean("二星", "2"));
		list.add(new NameValueBean("三星", "3"));
		list.add(new NameValueBean("四星", "4"));
		list.add(new NameValueBean("五星", "5"));
		return list;
	}
	
	public static String getEvalContent(int star){
		if(star==0){
			return "";
		}else if(star==1){
			return "不错";
		}else if(star==2){
			return "还行";
		}else if(star==3){
			return "一般";
		}else if(star==4){
			return "很好";
		}else{
			return "极好的";
		}
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

	public Integer getType() {
		return type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCourseTeacher(CourseTeacher courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Account getAccount() {
		return account;
	}

	public Integer getLevel() {
		return level;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStuLevelName() {
		return stuLevelName;
	}

	public void setStuLevelName(String stuLevelName) {
		this.stuLevelName = stuLevelName;
	}
}