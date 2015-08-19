package cn.com.yarose.base;

import javax.validation.constraints.Size;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.utils.Constants;

public class Dictionary implements java.io.Serializable {

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

	@FieldMeta(id = true, label = "类型", editable = true, dictionary = true, required = true)
	private DictCategory type;

	@FieldMeta(id = true, label = "名称", editable = true, required = true)
	@Size(max = 30, min = 1)
	private String name;

	@FieldMeta(id = true, label = "类型", editable = false)
	public String getTypeName() {
		if (type != null) {
			return type.getName();
		}
		return "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DictCategory getType() {
		return type;
	}

	public void setType(DictCategory type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
