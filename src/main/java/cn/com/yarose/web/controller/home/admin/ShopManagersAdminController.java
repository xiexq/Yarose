package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.entity.NameValueBean;
import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopManager;
import cn.com.yarose.base.ShopManagerService;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/shop/managers")
@CRUDControllerMeta(viewable = true, title = "店铺授权管理用户", service = ShopManagerService.class, listable = true, createable = true, editable = true, deleteable = true)
public class ShopManagersAdminController extends
  BaseCRUDControllerExt<ShopManager, Long> {

  private ShopService shopService;
  @Resource(name="shopService")
  public void setShopService(ShopService shopService){
    this.shopService = shopService;
  }
	
	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userId", "shopName","authTime");
	}

	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("userId");
	}

	@Override
    public List<ShopManager> customList(int offset, int count,
            OrderProperties orders, HttpServletRequest request)
            throws Exception {
        return ((ShopManagerService) this.getCrudService()).listByShopId(
                this.getShopIdFromRequest(request), offset, count);
    }
	
	@Override
    public long customCount(HttpServletRequest request) throws Exception {
        return ((ShopManagerService) this.getCrudService()).countShopAllManagers(this.getShopIdFromRequest(request));
    }
	
	@Override
	public ShopManager customSaveCmd(ShopManager cmd, HttpServletRequest request, Long id)
	    throws Exception {
	  cmd = super.customSaveCmd(cmd, request, id);
      if (id == null) {
          cmd.setAuthSourceAlias(this.getAccount().getUserid());
          cmd.setAuthSourceId(this.getAccount().getAccountId());
          cmd.setAuthTime(new Date());
          Long shopId = this.getShopIdFromRequest(request);
          Shop shop = null;
          if(shopId != null){
            shop = shopService.findById(shopId);
          }
          cmd.setShop(shop);
      }
      return cmd;
	}
	
	@Override
	public ShopManager customSave(ShopManager cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
		    if(create){
		    //List<CASAccount> accounts=null;
		        //accounts= casAccountService.findByUserId(cmd.getUserId(), 0, 1);
		        //if (accounts == null || accounts.size() <= 0) {
		         // result.rejectValue("userId", "not.exist", "不存在");
		         // return cmd;
		        //} else {
		            //CASAccount account = accounts.get(0);
		            cmd.setAccountId(1L);
		            cmd.setUserId(cmd.getUserId());
		       // }
  		    }
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
		return this.generateStringSortedSet("userId","authTime");
	}
	
	 private Long getShopIdFromRequest(HttpServletRequest request) {
	    Long shopId = Long.parseLong(request.getParameter("_shopId"));
	    return shopId;
	  }

}
