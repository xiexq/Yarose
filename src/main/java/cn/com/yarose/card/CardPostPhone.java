package cn.com.yarose.card;

import java.util.Date;

import javax.validation.constraints.Min;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class CardPostPhone implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Long id;

	// 会员卡号
	private Long cardId;

	@FieldMeta(label = "延期费", editable = false, visible = false)
	@Min(0)
	private Float price;

	@FieldMeta(label = "延期时间", editable = false, required = true, visible = false)
	private Date postPhoneDate;

	@FieldMeta(label = "备注", text = true, editable = false, visible = false)
	private String remark;

	// 延期人
	private Long creator;

	@FieldMeta(label = "创建人账号", editable = false, visible = false)
	private String creatorName;
	// 创建日期

	@FieldMeta(label = "创建日期", editable = false, visible = false)
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Float getPrice() {
		if (price == null) {
			return 0f;
		}
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getPostPhoneDate() {
		return postPhoneDate;
	}

	public void setPostPhoneDate(Date postPhoneDate) {
		this.postPhoneDate = postPhoneDate;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}