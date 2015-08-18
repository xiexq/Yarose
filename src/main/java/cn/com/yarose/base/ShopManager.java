package cn.com.yarose.base;

import javax.validation.constraints.NotNull;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class ShopManager implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,label="ID",editable=false)
	private Long id;
	
	private Shop shop;
	
	/**
     * 用户ID，谁管理这个店铺
     */
    Long accountId;
	
    /**
     * 管理用户类型
     */
    @FieldMeta(label = "管理用户类型", dictionary = true, required = true, editable = true, order = 8, visible = false)
    @NotNull
    private Integer adminType;
    
    /**
     * 管理状态（开通、禁用）
     */
    @FieldMeta(label = "状态", order = 1, required = true, dictionary = true, summary = true, visible = false)
    @NotNull
    private Integer status;
    
	/**
     * 答疑室ID
     */
    @FieldMeta(label = "店铺ID", editable = false, visible = false)
    public Long getShopId() {
        return shop == null ? null : shop.getId();
    }

    /**
     * 答疑室名称
     */
    @FieldMeta(label = "店铺名称", editable = false)
    public String getShopName() {
        return shop == null ? "" : shop.getName();
    } 
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

    public Long getAccountId() {
      return accountId;
    }
  
    public void setAccountId(Long accountId) {
      this.accountId = accountId;
    }

    public Integer getAdminType() {
      return adminType;
    }

    public void setAdminType(Integer adminType) {
      this.adminType = adminType;
    }

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }
	
}