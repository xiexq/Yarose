package cn.com.yarose.web.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.eduedu.jee.mvc.utils.CookieGenerator;
import cn.com.eduedu.jee.mvc.utils.WebUtils;
import cn.com.eduedu.jee.util.StringUtils;

/**
 * 维护用户请求的参数
 * 
 * @author janins
 * 
 */
public class WebRequestParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String SERVICE_KEY="service";
	private static final String REFERER_KEY="referer";
	String service;
	String registerReferer;
	/**
	 * 抓取Service，并且写cookie
	 * @param request
	 * @param response
	 * @return
	 */
	public static WebRequestParams buildFromRequest(HttpServletRequest request,HttpServletResponse response) {
		String service = request.getParameter(SERVICE_KEY);
		String referer=null;
		if (!StringUtils.hasText(service)) {
			//从cookie里面拿
			Cookie cookie=WebUtils.getCookie(request, SERVICE_KEY);
			if(cookie!=null){
				service=cookie.getValue();
			}
			cookie=WebUtils.getCookie(request, REFERER_KEY);
			if(cookie!=null){
				referer=cookie.getValue();
			}
		}
		if(!StringUtils.hasText(service)){
			return null;
		}
		if(!StringUtils.hasText(referer)){
			referer=request.getHeader("Referer");
		}
		WebRequestParams params = new WebRequestParams();
		try {
			params.setService(URLDecoder.decode(service,"utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		params.setRegisterReferer(referer);
		/*reject cookie*/
		if(response!=null){
			CookieGenerator cg=new CookieGenerator();
			cg.setCookieName(SERVICE_KEY);
			cg.addCookie(response, service);
			if(StringUtils.hasText(referer)){
				cg=new CookieGenerator();
				cg.setCookieName(REFERER_KEY);
				cg.addCookie(response, referer);
			}
		}
		return params;
	}

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

	public String getRegisterReferer() {
		return registerReferer;
	}

	public void setRegisterReferer(String registerReferer) {
		this.registerReferer = registerReferer;
	}

}
