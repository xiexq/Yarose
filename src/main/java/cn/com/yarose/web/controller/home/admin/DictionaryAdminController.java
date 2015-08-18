package cn.com.yarose.web.controller.home.admin;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;
import cn.com.eduedu.jee.entity.NameValueBean;
import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseObject;

@Controller
@RequestMapping("/home/admin/dictionary")
@CRUDControllerMeta(title = "类别管理", service = DictionaryService.class, listable = true, createable = true, editable = true, deleteable = true,viewable=true,searchable=true)
public class DictionaryAdminController extends
		BaseCRUDControllerExt<Dictionary, Long> {

	@Override
	public Dictionary customSaveCmd(Dictionary cmd, HttpServletRequest request,
			Long id) throws Exception {
		cmd= super.customSaveCmd(cmd, request, id);
		return cmd;
	}

	@Override
	public Dictionary customSave(Dictionary cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if(this.validate(cmd, result, request, create)){
		    Dictionary dic = ((DictionaryService)this.getCrudService()).findByName(cmd.getType(),cmd.getName());
		    if(create){
		       if(dic != null){
		         result.rejectValue("name","invalidate","已经存在!");
                 return cmd;
		       }
		    }else{
		      if(dic != null){
                if(dic.getId()!= null && !dic.getId().equals(cmd.getId())){
                    result.rejectValue("name","invalidate","已经存在!");
                    return cmd;
                }
              }
		    }
			return this.getCrudService().save(cmd);
		}
		return cmd;
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request) throws Exception {
	  return super.generateStringSortedSet("type", "name");
	}
	
	@Override
	public void customSearchExample(Dictionary example, HttpServletRequest request) throws Exception {
	  super.customSearchExample(example, request);
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("type", "name");
	}

	@DictionaryModel(header = true, headerLabel = "请选择", headerValue = "")
	public Collection<NameValueBean> _types(HttpServletRequest request) {
		return Constants.getStatusDictionary();
	}
	
	@Override
	public Set<String> customListFields(HttpServletRequest request) throws Exception {
	  return this.generateStringSortedSet("typeName", "name");
	}

	
	@Override
	public void customDelete(Long id, HttpServletRequest request) throws Exception {
	// 如果类型下面有课程不能删除
	super.customDelete(id, request);
	}
}
