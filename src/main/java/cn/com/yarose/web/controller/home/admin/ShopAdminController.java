package cn.com.yarose.web.controller.home.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/shops")
@CRUDControllerMeta(title = "门店管理", service = ShopService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true)
public class ShopAdminController extends BaseCRUDControllerExt<Shop, Long> {

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("name", "address", "phone", "desc");
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("name", "address", "phone");
	}

	@Override
	public Shop customSave(Shop cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			Shop shop = ((ShopService) this.getCrudService()).finByName(cmd
					.getName());
			if (create) {
				if (shop != null) {
					result.rejectValue("name", "invalidate", "已经存在!");
					return cmd;
				}
			} else {
				if (shop != null && !shop.getId().equals(cmd.getId())) {
					result.rejectValue("name", "invalidate", "已经存在!");
					return cmd;
				}
			}
			return this.getCrudService().save(cmd);
		}
		return cmd;
	}
}
