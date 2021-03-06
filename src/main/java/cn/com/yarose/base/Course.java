package cn.com.yarose.base;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.utils.Constants;

public class Course implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	private Long id;

	@FieldMeta(label = "课程编号", editable = false, visible = false, summary = false)
	public String getCode() {
		if (id == null) {
			return null;
		}
		return Constants.getFormatId(this.id, 7);
	}

	@FieldMeta(id = true, label = "课程名称", editable = false, required = true)
	private String name;

	@FieldMeta(label = "所属舞种", dictionary = true, visible = true, editable = true, required = true)
	private Dictionary danceType;

	@FieldMeta(label = "所属门店", dictionary = true, visible = true, editable = true, required = true)
	private Shop shop;

	@FieldMeta(label = "适合年龄段", dictionary = true, visible = true, editable = true, required = true)
	private Dictionary ageGroup;

	@FieldMeta(label = "穿衣/鞋建议", text = true)
	private String desc;

	@FieldMeta(label = "适合年龄段", visible = true, editable = false, summary = false)
	public String getAgeGroupName() {
		if (ageGroup != null) {
			return ageGroup.getName();
		}
		return "";
	}

	@FieldMeta(label = "所属门店", visible = true, editable = false, summary = false)
	public String getShopName() {
		if (shop != null) {
			return shop.getName();
		}
		return "";
	}

	@FieldMeta(label = "所属舞种", visible = true, editable = false, summary = false)
	public String getDanceTypeName() {
		if (danceType != null) {
			return danceType.getName();
		}
		return "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public Dictionary getDanceType() {
		return danceType;
	}

	public void setDanceType(Dictionary danceType) {
		this.danceType = danceType;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Dictionary getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(Dictionary ageGroup) {
		this.ageGroup = ageGroup;
	}
}
