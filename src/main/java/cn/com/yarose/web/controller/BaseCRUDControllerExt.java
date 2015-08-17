package cn.com.yarose.web.controller;

import java.io.Serializable;

import org.springframework.ui.Model;

import cn.com.eduedu.jee.mvc.controller.BaseCRUDController;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.SecurityContextHelper;


public class BaseCRUDControllerExt<T,ID extends Serializable> extends BaseCRUDController<T, ID> {
	public void rejectAccountInfo(Model model){
		model.addAttribute("jeeAccount", SecurityContextHelper.getAccount());
	}
	public Account getAccount(){
		return SecurityContextHelper.getAccount();
	}
}
