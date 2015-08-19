package cn.com.yarose.base;

import javax.validation.constraints.Size;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.utils.Constants;

public class Shop implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	private Long id;
	
	@FieldMeta(label = "编号", editable = false, visible = false, summary = false)
	  public String getCode() {
	      if (id == null) {
	          return null;
	      }
	      return Constants.getFormatId(this.id);
	  }
	
	@FieldMeta(id=true,label="名称",editable=true,required=true)
    @Size(max = 200, min = 1)
    private String name;
	
	@FieldMeta(id=true,label="地址",editable=true,required=true)
	private String address;
	
	@FieldMeta(id=true,label="电话",editable=true,required=true)
	private String phone;
	
	@FieldMeta(id=true,label="描述",editable=true,text=true)
	private String desc;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
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

    public void setDesc(String desc) {
      this.desc = desc;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}