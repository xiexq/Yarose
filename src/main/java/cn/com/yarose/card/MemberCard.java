package cn.com.yarose.card;

import java.util.Date;

import javax.validation.constraints.Min;

import org.springframework.util.StringUtils;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.Shop;

public class MemberCard implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	private Long id;

	@FieldMeta(label = "卡号", editable = false, required = true)
	private String cardNo;

	/**
	 * 办卡门店
	 */
	@FieldMeta(label = "办卡门店", dictionary = true)
	private Shop shop;

	/**
	 * 会员卡类型
	 */
	@FieldMeta(label = "会员卡类型", dictionary = true, required = true)
	private Dictionary type;
	/**
	 * 购买课时
	 */
	@FieldMeta(label = "购买课时", required = true)
	@Min(1)
	private Integer purchaseLesson;
	/**
	 * 赠送课时
	 */
	@Min(0)
	@FieldMeta(label = "赠送课时")
	private Integer givingLesson;
	/**
	 * 总课时
	 */
	@FieldMeta(label = "总课时")
	private Integer totalLesson;
	/**
	 * 已使用课时
	 */
	@FieldMeta(label = "已使用课时")
	private Integer usedLesson;
	/**
	 * 成交价
	 */
	@Min(0)
	@FieldMeta(label = "成交价", required = true)
	private Float price;
	/**
	 * 有效期
	 */
	@FieldMeta(label = "有效期", required = true)
	private Date expireDate;
	/**
	 * 会员账号
	 */
	@FieldMeta(label = "会员账号", autoComplete = true, required = true)
	private String userId;

	@FieldMeta(label = "创建时间", editable = false, summary = false)
	private Date createDate;

	@FieldMeta(label = "创建人", editable = false, summary = false)
	private Account creator;

	// 延期费
	private Float postPhoePrice;

	@FieldMeta(label = "会员卡类型", editable = false)
	public String getTypeName() {
		if (type != null && StringUtils.hasText(type.getName())) {
			return type.getName();
		}
		return "";
	}

	@FieldMeta(label = "办卡门店", editable = false)
	public String getShopName() {
		if (shop != null && StringUtils.hasText(shop.getName())) {
			return shop.getName();
		}
		return "";
	}

	@FieldMeta(label = "创建人", editable = false, summary = false)
	public String getCreatorName() {
		if (creator != null && StringUtils.hasText(creator.getUserid())) {
			return creator.getUserid();
		}
		return "";
	}

	/**
	 * 卡号的默认起始卡号
	 */
	private Long seqNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Dictionary getType() {
		return type;
	}

	public void setType(Dictionary type) {
		this.type = type;
	}

	public Integer getPurchaseLesson() {
		if (purchaseLesson != null) {
			return purchaseLesson;
		}
		return 0;
	}

	public void setPurchaseLesson(Integer purchaseLesson) {
		this.purchaseLesson = purchaseLesson;
	}

	public Integer getGivingLesson() {
		if (givingLesson != null) {
			return givingLesson;
		}
		return 0;
	}

	public void setGivingLesson(Integer givingLesson) {
		this.givingLesson = givingLesson;
	}

	public Integer getTotalLesson() {
		if (totalLesson != null) {
			return totalLesson;
		}
		return totalLesson;
	}

	public void setTotalLesson(Integer totalLesson) {
		this.totalLesson = totalLesson;
	}

	public Integer getUsedLesson() {
		if (usedLesson != null) {
			return usedLesson;
		}
		return 0;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public Float getPostPhoePrice() {
		if (postPhoePrice == null) {
			return 0f;
		}
		return postPhoePrice;
	}

	public void setPostPhoePrice(Float postPhoePrice) {
		this.postPhoePrice = postPhoePrice;
	}

	public Account getCreator() {
		return creator;
	}

	public void setCreator(Account creator) {
		this.creator = creator;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
}
