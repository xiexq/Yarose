package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.entity.NameValueBean;
import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.CRUDPhase;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.ShopManager;
import cn.com.yarose.base.ShopManagerService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/shop/managers")
@CRUDControllerMeta(viewable = true, title = "店铺授权管理用户", service = ShopManagerService.class, listable = true, createable = true, editable = true, deleteable = true)
public class ShopManagersAdminController extends
  BaseCRUDControllerExt<ShopManager, Integer> {

	
	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userId", "roomName", "roomCode",
				"adminTypeName", "authTime",
				"authOverTime", "statusName", "authSourceAlias","authSourceTypeName");
	}

	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		if (create) {
			return this.generateStringSortedSet("userId", "adminType",
					"authOverTime", "status");
		} else {
			return this.generateStringSortedSet("adminType", "authOverTime",
					"status");
		}
	}

	@Override
	public ShopManager customSave(ShopManager cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			
			return this.getCrudService().save(cmd);
		}
		return cmd;
	}

	public Collection<NameValueBean> _statuss(HttpServletRequest request) {
		return Constants.getStatusDictionary();
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userId", "adminTypeName", "authTime", "authOverTime", "statusName",
				"status");
	}

	@Override
	public Set<String> customHideFields(HttpServletRequest request,
			CRUDPhase phase) {
		if (CRUDPhase.LIST.equals(phase)) {
			return this.generateStringSet("status");
		}
		return super.customHideFields(request, phase);
	}

}
