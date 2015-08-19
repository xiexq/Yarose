package cn.com.yarose.base;

import javax.validation.constraints.Size;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class Dictionary implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@FieldMeta(id = true, label = "ID", editable = false)
	private Long id;
<<<<<<< HEAD
	
	@FieldMeta(label = "编号", editable = false, visible = false, summary = false)
	  public String getCode() {
	      if (id == null) {
	          return null;
	      }
	      return Constants.getFormatId(this.id);
	  }
	
	@FieldMeta(id=true,label="名称",editable=true,required=true)
	@Size(max = 200, min = 1)
=======

	@FieldMeta(id = true, label = "类型", editable = true, dictionary = true, required = true)
	private DictCategory type;

	@FieldMeta(id = true, label = "名称", editable = true, required = true)
	@Size(max = 30, min = 1)
>>>>>>> branch 'master' of https://github.com/xiexq/Yarose
	private String name;
<<<<<<< HEAD
	
	@FieldMeta(id=true,label="描述",editable=true,text=true)
    @Size(max = 500, min = 0)
	private String desc;
    
	public Long getId(){
=======

	@FieldMeta(id = true, label = "类型", editable = false)
	public String getTypeName() {
		if (type != null) {
			return type.getName();
		}
		return "";
	}

	public Long getId() {
>>>>>>> branch 'master' of https://github.com/xiexq/Yarose
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

<<<<<<< HEAD
    public String getName() {
      return name;
    }
=======
	public DictCategory getType() {
		return type;
	}
>>>>>>> branch 'master' of https://github.com/xiexq/Yarose

<<<<<<< HEAD
    public void setName(String name) {
      this.name = name;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }
	
=======
	public void setType(DictCategory type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

>>>>>>> branch 'master' of https://github.com/xiexq/Yarose
}
