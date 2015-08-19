package cn.com.yarose.base;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class DictCategory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public DictCategory(String name) {
		super();
		this.name = name;
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