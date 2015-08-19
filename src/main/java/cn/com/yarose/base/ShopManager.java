package cn.com.yarose.base;

import java.util.Date;

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
     * 用户账号，一个冗余字段，用来友好显示
     */
    @FieldMeta(label = "用户账号", required = true, order = 9, autoComplete = true)
    String userId;
    
    /**
     * 授权时间
     */
    @FieldMeta(label = "授权时间", editable = false, order = 8)
    @NotNull
    Date authTime;
    
    /**
     * 授权人
     */
    @FieldMeta(label = "授权来源", editable = true, summary = false, visible = false)
    Long authSourceId;

    @FieldMeta(label = "授权人", visible = true, editable = false, summary = false)
    String authSourceAlias;
    
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

    public Shop getShop() {
      return shop;
    }

    public void setShop(Shop shop) {
      this.shop = shop;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public Date getAuthTime() {
      return authTime;
    }

    public void setAuthTime(Date authTime) {
      this.authTime = authTime;
    }

    public Long getAuthSourceId() {
      return authSourceId;
    }

    public void setAuthSourceId(Long authSourceId) {
      this.authSourceId = authSourceId;
    }

    public String getAuthSourceAlias() {
      return authSourceAlias;
    }

    public void setAuthSourceAlias(String authSourceAlias) {
      this.authSourceAlias = authSourceAlias;
    }

}