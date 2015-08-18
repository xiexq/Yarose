package cn.com.yarose.base;

import javax.validation.constraints.Size;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class Shop implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	private Long id;
	
	@FieldMeta(id=true,label="名称",editable=true,required=true)
    @Size(max = 200, min = 1)
    private String name;
	
	private Integer status;
	
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

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }
	
}