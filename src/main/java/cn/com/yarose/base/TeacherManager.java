package cn.com.yarose.base;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class TeacherManager implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  @FieldMeta(id = true, label = "ID", editable = false)
  Long id;

  @FieldMeta(label = "所属店铺", dictionary = true, visible = true, editable = true, summary = false, required = true)
  private Long shopId;

  @FieldMeta(label = "所属课程", dictionary = true, visible = true, editable = true, summary = false, required = true)
  private Long courseId;

  @FieldMeta(label = "任课老师", dictionary = true, visible = true, editable = true, summary = false, required = true)
  private Long accountId;

  /**
   * 用户账号，一个冗余字段，用来友好显示
   */
  @FieldMeta(label = "老师名称", required = true, order = 9, autoComplete = true)
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

  @FieldMeta(label = "课程创建时间", datetime = true)
  private Date createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}
