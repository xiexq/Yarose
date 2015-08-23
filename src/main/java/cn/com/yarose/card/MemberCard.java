package cn.com.yarose.card;

import java.util.Date;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.Dictionary;

public class MemberCard implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	private Long id;

	/**
	 * 会员卡类型
	 */
	@FieldMeta(label = "会员卡类型", dictionary = true, required = true)
	private Dictionary type;
	/**
	 * 购买课时
	 */
	@FieldMeta(id = true, label = "购买课时", required = true)
	private Integer purchaseLesson;
	/**
	 * 赠送课时
	 */
	@FieldMeta(id = true, label = "赠送课时", required = true)
	private Integer givingLesson;
	/**
	 * 总课时
	 */
	@FieldMeta(id = true, label = "总课时", required = true)
	private Integer totalLesson;
	/**
	 * 已使用课时
	 */
	@FieldMeta(id = true, label = "已使用课时", editable = false, required = true)
	private Integer usedLesson;
	/**
	 * 成交价
	 */
	@FieldMeta(id = true, label = "成交价", required = true)
	private Float price;
	/**
	 * 有效期
	 */
	@FieldMeta(id = true, label = "有效期", required = true)
	private Date expireDate;
	/**
	 * 会员账号
	 */
	@FieldMeta(id = true, label = "会员账号", required = true)
	private Long memberId;

	public Dictionary getType() {
		return type;
	}

	public void setType(Dictionary type) {
		this.type = type;
	}

	public Integer getPurchaseLesson() {
		return purchaseLesson;
	}

	public void setPurchaseLesson(Integer purchaseLesson) {
		this.purchaseLesson = purchaseLesson;
	}

	public Integer getGivingLesson() {
		return givingLesson;
	}

	public void setGivingLesson(Integer givingLesson) {
		this.givingLesson = givingLesson;
	}

	public Integer getTotalLesson() {
		return totalLesson;
	}

	public void setTotalLesson(Integer totalLesson) {
		this.totalLesson = totalLesson;
	}

	public Integer getUsedLesson() {
		return usedLesson;
	}

	public void setUsedLesson(Integer usedLesson) {
		this.usedLesson = usedLesson;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
