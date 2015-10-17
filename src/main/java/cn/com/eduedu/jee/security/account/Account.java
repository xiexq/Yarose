package cn.com.eduedu.jee.security.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.com.eduedu.jee.entity.annotation.FieldMeta;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.Shop;

public class Account implements Authentication {
	private static final long serialVersionUID = 1L;
	UserDetails details;
	boolean authenticated = false;

	@FieldMeta(label = "账号ID", id = true, editable = false, summary = false, visible = false)
	Long accountId = null;

	@NotNull
	@Size(min = 5, max = 35)
	@FieldMeta(label = "用户名", i18n = true, required = true, description = "请用字母、数字和下划线组成。")
	String userid;

	@Size(max = 20)
	@NotNull
	@FieldMeta(label = "密码", i18n = true, summary = false, visible = false, required = true, password = true)
	String password;

	@Size(max = 20)
	@NotNull
	@FieldMeta(label = "姓名", i18n = true, required = true)
	String nick;

	@Email
	@FieldMeta(label = "电子邮件", i18n = true, summary = false)
	String email;

	@FieldMeta(label = "注册门店", i18n = true, summary = false, required = true, visible = false, dictionary = true)
	@NotNull
	Shop shop;

	@FieldMeta(label = "权限", i18n = true, visible = false, required = true, summary = false, dictionary = true)
	@NotNull
	Set<Access> accesses;

	@FieldMeta(label = "微信号", summary = false, visible = false, required = true)
	String weixin;

	@FieldMeta(label = "手机号", summary = false, visible = false, required = true)
	String phone;

	@FieldMeta(label = "生日", summary = false, visible = false)
	Date birthday;

	@FieldMeta(label = "是否已办卡", summary = false, visible = false)
	Integer hasCard;

	@FieldMeta(label = "创建日期", editable = false, datetime = true)
	Date createTime;

	// teacher group begin
	@FieldMeta(label = "教师级别", summary = false, visible = false, dictionary = true, group = "teacher")
	Long teachLevel;

	@FieldMeta(label = "标准课时费", summary = false, visible = false, group = "teacher")
	Float courseFee;

	@FieldMeta(label = "邮寄地址", summary = false, visible = false, group = "teacher")
	String address;
	// teacher group end

	// member group begin
	@FieldMeta(label = "职业", editable = false, group = "member")
	String occupation;

	@FieldMeta(label = "营销人员账号", summary = false, autoComplete = true, group = "member", visible = false)
	String saler;

	@FieldMeta(label = "推荐人账号", summary = false, autoComplete = true, group = "member", visible = false)
	String referee;

	@FieldMeta(label = "会员级别", summary = false, visible = false, group = "member", dictionary = true)
	Integer stuLevel;
	// member group end

	/**
	 * 冗余字段
	 */
	@FieldMeta(label = "区域", summary = false, visible = false, dictionary = true)
	Dictionary area;

	@FieldMeta(label = "备注", summary = false, text = true, visible = false)
	String remark;

	Integer isAdmin;
	
	/**
	 * 发送验证的次数
	 */
	Integer sendVerifyCodeCount = 0;

	public String getAlias() {
		return nick + "(" + userid + ")";
	}

	public Integer getHasCard() {
		return this.hasCard;
	}

	public void setHasCard(Integer hasCard) {
		this.hasCard = hasCard;
	}

	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getNick() {
		return this.nick;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Access> getAccesses() {
		return this.accesses;
	}

	public void setAccesses(Set<Access> accesses) {
		this.accesses = accesses;
	}

	@FieldMeta(label = "权限", summary = false, editable = false)
	public String getAccessesName() {
		StringBuffer sb = new StringBuffer();
		if (accesses != null) {
			for (Access access : accesses) {
				sb.append(access.getName() + " ");
			}
		}
		return sb.toString();
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection authorities = new ArrayList();
		authorities.add(Access.LOGIN_USER);
		if (this.accesses != null) {
			for (GrantedAuthority ga : this.accesses) {
				authorities.add(ga);
			}
		}
		if (this.details != null) {
			Collection tmps = this.details.getAuthorities();
			if (tmps != null) {
				authorities.addAll(tmps);
			}
		}
		return authorities;
	}

	public Dictionary getArea() {
		return area;
	}

	public void setArea(Dictionary area) {
		this.area = area;
	}

	public Object getCredentials() {
		return this.password;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	public Object getDetails() {
		return this.details;
	}

	public Float getCourseFee() {
		return this.courseFee;
	}

	public void setCourseFee(Float courseFee) {
		this.courseFee = courseFee;
	}

	public Object getPrincipal() {
		return this.userid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Long getTeachLevel() {
		return this.teachLevel;
	}

	public void setTeachLevel(Long teachLevel) {
		this.teachLevel = teachLevel;
	}

	public Integer getStuLevel() {
		return this.stuLevel;
	}

	public void setStuLevel(Integer stuLevel) {
		this.stuLevel = stuLevel;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	public String getName() {
		return this.nick;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result
				+ (this.userid == null ? 0 : this.userid.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (this.userid == null) {
			if (other.userid != null)
				return false;
		} else if (!this.userid.equals(other.userid))
			return false;
		return true;
	}

	public Integer getSendVerifyCodeCount() {
		return sendVerifyCodeCount;
	}

	public void setSendVerifyCodeCount(Integer sendVerifyCodeCount) {
		this.sendVerifyCodeCount = sendVerifyCodeCount;
	}
}