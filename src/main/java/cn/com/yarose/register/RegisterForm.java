package cn.com.yarose.register;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;

public class RegisterForm implements Serializable {
	private static final long serialVersionUID = 1L;
	@FieldMeta(id=true,editable=false,visible=false,summary=false)
	Long id;
	@FieldMeta(label="账号",required=true,remoteValidation=true)
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
}
