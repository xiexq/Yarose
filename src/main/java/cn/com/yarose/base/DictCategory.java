package cn.com.yarose.base;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class DictCategory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Integer id;

	private String name;
	
	private String code;

	public Integer getId() {
		return id;
	}

	public DictCategory(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DictCategory() {
		super();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
