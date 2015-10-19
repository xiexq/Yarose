package cn.com.yarose.web.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yarose.utils.CheckMobileUtil;
import cn.com.yarose.web.controller.BaseControllerExt;

@Controller
@RequestMapping("/home")
public class HomeIndex extends BaseControllerExt {
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		this.rejectWebServletCfgs(model);
		this.rejectAccountInfo(model);
		boolean isMobile = CheckMobileUtil.isMobile(request);
		if (isMobile) {
			return "home/mobile_index";
		}
		return "home/index";
	}
}
