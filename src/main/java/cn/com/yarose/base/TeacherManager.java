package cn.com.yarose.base;

import java.util.Date;

import javax.validation.constraints.NotNull;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class TeacherManager implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	Long id;
	
	private Course course;
	
	/**
     * 课程ID
     */
    @FieldMeta(label = "课程ID", editable = false, visible = false)
    public Long getCourseId() {
        return course == null ? null : course.getId();
    }

    /**
     * 课程名称
     */
    @FieldMeta(label = "课程名称", editable = false)
    public String getCourseName() {
        return course == null ? "" : course.getName();
    }
    
    /**
     * 用户ID，谁管理这个答疑室
     */
    Long accountId;
    
    /**
     * 用户账号，一个冗余字段，用来友好显示
     */
    @FieldMeta(label = "用户账号", required = true, order = 9, autoComplete = true)
    String userId;
	
    /**
     * 授权人
     */
    @FieldMeta(label = "授权来源", editable = true, summary = false, visible = false)
    Long authSourceId;

    @FieldMeta(label = "授权人", visible = true, editable = false, summary = false)
    String authSourceAlias;
    
    @FieldMeta(label = "课程开始时间", datetime = true, required = true)
    private Date beginTime;
    
    @FieldMeta(label = "课程结束时间", datetime = true, required = true)
    private Date endTime;
    
    @FieldMeta(label = "课程创建时间",datetime = true)
    private Date createTime;
    
    /**
     * 授权时间
     */
    @FieldMeta(label = "授权时间", editable = false, order = 8)
    @NotNull
    Date authTime;
    
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public Date getAuthTime() {
    return authTime;
  }

  public void setAuthTime(Date authTime) {
    this.authTime = authTime;
  }
}