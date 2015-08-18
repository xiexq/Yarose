package cn.com.yarose.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController extends BaseControllerExt {
	@RequestMapping("/login")
	public String login(Model model,HttpServletResponse response){
		this.rejectWebServletCfgs(model);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return "login";
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
