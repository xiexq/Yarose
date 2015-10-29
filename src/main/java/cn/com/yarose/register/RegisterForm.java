package cn.com.yarose.register;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.Shop;

public class RegisterForm implements Serializable {
	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,editable=false,visible=false,summary=false)
	Long id;
	@FieldMeta(label="用户名",required=true,remoteValidation=true)
	@NotNull
	@Size(min=5,max=100)
	String userid;
	@FieldMeta(label="电子邮件",required=true,tip=false,description="填写真实的电子邮件以用于找回密码",autoComplete=true)
	@Email
	@NotNull
	@Size(min=5,max=100)
	String email;
	@FieldMeta(label="密码",required=true,passwordCheckStrength=true,password=true)
	@NotNull
	@Size(min=5,max=50)
	String password;
	@FieldMeta(label="密码确认",required=true,password=true)
	String passwordConfirm;
	@FieldMeta(label="昵称",required=true,tip=false,description="请给自己填写一个能代表自己的名字吧")
	@NotNull
	@Size(min=1,max=25)
	String nick;
	@FieldMeta(label = "注册门店", i18n = true, summary = false, required = true, visible = false, dictionary = true)
	@NotNull
	Shop shop;
	@FieldMeta(label = "微信号", summary = false, visible = false, required = true)
	String weixin;
	@FieldMeta(label = "手机号", summary = false, visible = false, required = true)
	String phone;
	// member group begin
	@FieldMeta(label = "职业", editable = false, group = "member")
	String occupation;
	@FieldMeta(label = "营销人员账号", summary = false, autoComplete = true, group = "member", visible = false)
	String saler;

	@FieldMeta(label = "推荐人账号", summary = false, autoComplete = true, group = "member", visible = false)
	String referee;

	@FieldMeta(label = "会员级别", summary = false, visible = false, group = "member", dictionary = true)
	Integer stuLevel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Shop getShop() {
		return shop;
	}
	public String getWeixin() {
		return weixin;
	}
	public String getPhone() {
		return phone;
	}
	public String getOccupation() {
		return occupation;
	}
	public String getSaler() {
		return saler;
	}
	public String getReferee() {
		return referee;
	}
	public Integer getStuLevel() {
		return stuLevel;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	public void setReferee(String referee) {
		this.referee = referee;
	}
	public void setStuLevel(Integer stuLevel) {
		this.stuLevel = stuLevel;
	}
}
