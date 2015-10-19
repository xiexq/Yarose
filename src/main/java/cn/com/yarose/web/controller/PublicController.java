package cn.com.yarose.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/public")
public class PublicController extends BaseControllerExt {
	@RequestMapping("/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model){
		this.rejectWebServletCfgs(model);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return isMobile(request) == false?"login":"login_page_mobile";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerEntry(HttpServletRequest request, Model model,
			HttpServletResponse response) throws IOException {
		this.rejectWebServletCfgs(model);
		return isMobile(request) ? "register_page_mobile"
				: "register_page";
	}
	
	
	@RequestMapping("/404")
	public String err404(Model model,HttpServletResponse response){
		this.rejectWebServletCfgs(model);
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "404";
	}
	@RequestMapping("/403")
	public String err403(Model model,HttpServletResponse response){
		this.rejectWebServletCfgs(model);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return "403";
	}
	@RequestMapping("/500")
	public String err500(Model model,HttpServletResponse response){
		this.rejectWebServletCfgs(model);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return "500";
	}
}
