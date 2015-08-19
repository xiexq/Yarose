package cn.com.yarose.base;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class DictCategory implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	Integer id;
	
	private String name;

	public DictCategory(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}