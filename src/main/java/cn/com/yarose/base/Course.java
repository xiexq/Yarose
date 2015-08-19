package cn.com.yarose.base;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.utils.Constants;

public class Course implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  @FieldMeta(id = true, label = "ID", editable = false)
  private Long id;

  @FieldMeta(label = "编号", editable = false, visible = false, summary = false)
  public String getCode() {
      if (id == null) {
          return null;
      }
      return Constants.getFormatId(this.id);
  }
  
  @FieldMeta(id = true, label = "课程名称", editable = false)
  private String name;

  @FieldMeta(label = "所属舞种",dictionary = true, visible = true, editable = true, summary = false, required = true)
  private Long dicId;

  @FieldMeta(label = "所属店铺",dictionary = true, visible = true, editable = true, summary = false, required = true)
  private Long shopId;

  @FieldMeta(label = "课程开始时间", datetime = true, required = true)
  private Date beginTime;
  
  @FieldMeta(label = "课程结束时间", datetime = true, required = true)
  private Date endTime;
  
  @FieldMeta(label = "课程创建时间",datetime = true)
  private Date createTime;
  
  @FieldMeta(label = "描述",text=true)
  private String desc;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getbeginTime() {
    return beginTime;
  }

  public void setbeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public Long getDicId() {
    return dicId;
  }

  public void setDicId(Long dicId) {
    this.dicId = dicId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
