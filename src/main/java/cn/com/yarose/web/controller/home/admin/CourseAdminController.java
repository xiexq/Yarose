package cn.com.yarose.web.controller.home.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.controller.DictionaryModelType;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/courses")
@CRUDControllerMeta(title = "课程管理", service = CourseService.class, listable = true, createable = true, editable = true, deleteable = true,viewable=true,searchable=true)
public class CourseAdminController extends
		BaseCRUDControllerExt<Course, Long> {

	private DictionaryService dicService;
	private ShopService shopService;
	
	public void setDictionaryService(DictionaryService dicService){
	  this.dicService = dicService;
	}
	
	public void setShopService(ShopService shopService){
      this.shopService = shopService;
    }
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("name","dicId","shopId","desc");
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request, boolean create) throws Exception {
	  return this.generateStringSortedSet("name","dicId","shopId","desc");
	}
	
	@Override
	public Set<String> customViewFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("name","dicId","shopId","desc");
	}
	
	@Override
	public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("name","dicId","shopId");
	}
	
	@DictionaryModel(header = true, headerLabel = "不限", headerValue = "",type=DictionaryModelType.URL,url="/home/shops/selector", headerIsJustForSearch=true, cascade = true)
	public Shop _shopIds(HttpServletRequest request, Object obj) {
      if(obj != null){
        return shopService.findById(Long.valueOf(obj.toString()));
      }
      return null;
    }
	
	@DictionaryModel(header = true, headerLabel = "不限", headerValue = "",type=DictionaryModelType.URL,url="/home/dictionarys/selector", headerIsJustForSearch=true, cascade = true)
    public Dictionary _dicIds(HttpServletRequest request, Object obj) {
      if(obj != null){
        return dicService.findById(Long.valueOf(obj.toString()));
      }
      return null;
    }
	
	@Override
	public Course customSave(Course cmd, BindingResult result, HttpServletRequest request,
	  ResponseObject response, boolean create) throws Exception {
    	if(this.validate(cmd, result, request, create)){
    	  
    	}
    	return super.customSave(cmd, result, request, response, create);
	}
	
}
