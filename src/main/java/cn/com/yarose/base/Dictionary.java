package cn.com.yarose.base;

import javax.validation.constraints.Size;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.utils.Constants;

public class Dictionary implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	private Long id;
	
	@FieldMeta(id=true,label="类型",editable=true,dictionary=true,required=true)
	private Integer type;
	
	@FieldMeta(id=true,label="名称",editable=true,required=true)
	@Size(max = 200, min = 1)
	private String name;
	
	// 用户状态：正在处理中0、确认成功1、确认失败2、锁定资源3
    @FieldMeta(label = "用户状态", editable = false, order = 15)
    public String getTypeName() {
        if (type == null) {
            return null;
        }
        return Constants.getDictionaryStatusName(type);
    }
    
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

    public Integer getType() {
      return type;
    }
  
    public void setType(Integer type) {
      this.type = type;
    }
  
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
	
}