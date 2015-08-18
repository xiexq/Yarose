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
@CRUDControllerMeta(title = "店铺管理", service = ShopService.class, listable = true, createable = true, editable = true, deleteable = true,viewable=true,searchable=true)
public class ShopAdminController extends
		BaseCRUDControllerExt<Shop, Long> {

	@Override
	public Shop customSaveCmd(Shop cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd= super.customSaveCmd(cmd, request, id);
		return cmd;
	}

	@Override
	public Shop customSave(Shop cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if(this.validate(cmd, result, request, create)){
		    Shop shop = ((ShopService) this.getCrudService()).finByName(cmd.getName());
		    if(create){
		      if(shop != null){
		        result.rejectValue("name","invalidate","已经存在!");
                return cmd;
		      }
		    }else{
		      if(shop != null && !shop.getId().equals(cmd.getId())){
                result.rejectValue("name","invalidate","已经存在!");
                return cmd;
              }
		    }
			return this.getCrudService().save(cmd);
		}
		return cmd;
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
	  return super.generateStringSortedSet("name");
	}
	
	@Override
	public void customSearchExample(Shop example, HttpServletRequest request) throws Exception {
	  super.customSearchExample(example, request);
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("name");
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("name");
	}
	
	@Override
	public void customDelete(Long id, HttpServletRequest request) throws Exception {
	  //判断下面是否有课程
	  super.customDelete(id, request);
	}
	
	/**级联查询
	 * @DictionaryModel(header = true, cascade = true, cascadeField = "countryCode", headerIsJustForSearch = true)
    public Collection<NameValueBean> _timeZoneOffsets(HttpServletRequest request) {
        String id = this.getParameter(request, "__id");
        if (StringUtils.hasText(id)) {
            List<NameValueBean> ctzs = TimeZoneUtils.getCountryTimeZones(id);
            if (ctzs != null && ctzs.size() > 0) {
                return ctzs;
            }
        }
        return TimeZoneUtils.getTimeZones();
    }
	 */

	/**用户自动提示
	 * @DictionaryModel(label = "alias", val = "userid")
    public List<CASAccount> _ac_userIds(String userid,
            HttpServletRequest request) {
        if(StringUtils.hasText(userid)){
            Integer roomId=this.getRoomIdFromRequest(request);
            Room room=roomService.findById(roomId);
            //如果答疑室的租户可跨租户，可以通过全局来检索用户
            //如果答疑室的租户不跨租户，只能拿当前租户中的用户
            Tenant tenant=tenantService.loadByCode(room.getTenantCode());
            if(tenant!=null&&tenant.isEnabled()){
                boolean crossTenants=tenant.getEnabledPassport();
                if(crossTenants){
                    return casAccountService.findByUserId(userid + "%", 0, 15);
                }else{
                    return casAccountService.findByUserIdAndTenant(userid+"%", tenant.getCode(), 0, 15);
                }
            }
        }
        return null;
        
    }
	 */
	
	/**选择框
	 * @DictionaryModel(header = true, headerLabel = "不限", headerValue = "",type=DictionaryModelType.URL,url="/home/banks/selector", headerIsJustForSearch=true, cascade = true, cascadeField = "tenantCode")
    public QuestionBank _questionBankIds(HttpServletRequest request, Object obj) {
        if(obj != null){
          return questionBankService.findById(Long.valueOf(obj.toString()));
        }
        return null;
    }
	 */
}
