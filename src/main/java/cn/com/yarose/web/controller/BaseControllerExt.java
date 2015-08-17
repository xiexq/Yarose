package cn.com.yarose.web.controller;

import org.springframework.ui.Model;

import cn.com.eduedu.jee.mvc.controller.BaseController;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.SecurityContextHelper;

public class BaseControllerExt extends BaseController {
	public void rejectAccountInfo(Model model){
		model.addAttribute("jeeAccount", SecurityContextHelper.getAccount());
	}
	public Account getAccount(){
		return SecurityContextHelper.getAccount();
	}
}
