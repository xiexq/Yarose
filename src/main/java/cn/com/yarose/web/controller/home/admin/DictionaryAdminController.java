package cn.com.yarose.web.controller.home.admin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.base.DictCategory;
import cn.com.yarose.base.DictCategoryService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/dictionary")
@CRUDControllerMeta(title = "字典管理", service = DictionaryService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = false, paged = false)
public class DictionaryAdminController extends
		BaseCRUDControllerExt<Dictionary, Long> {

	@Resource(name = "dictCategoryService")
	DictCategoryService dictCategoryService;

	@Override
	public Dictionary customSave(Dictionary cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			Integer type = this.getTypeId(request);
			Dictionary dic = ((DictionaryService) this.getCrudService())
					.findByName(type, cmd.getName());
			if (dic == null) {
				DictCategory dc = dictCategoryService.findById(type);
				cmd.setType(dc);
				cmd.setAccount(this.getAccount());
				cmd.setCreateDate(new Date());
				return this.getCrudService().save(cmd);
			} else {
				result.rejectValue("name", "name.error", "该名称已添加");
			}
		}
		return cmd;
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		String code = getTypeCode(request);
		// 教师级别需要有课时费
		if (Constants.DICT_TYPE_TEACH_LEVEL.equals(code)) {
			return this.generateStringSortedSet("name", "courseFee");
		}
		return this.generateStringSortedSet("name");
	}

	@Override
	public boolean customCreateable(HttpServletRequest request)
			throws Exception {
		Integer type = getTypeId(request);
		// 根目录不能修改，只能查看
		if (type == 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Dictionary> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		Integer type = this.getTypeId(request);
		if (type == 0) {
			return ((DictionaryService) this.getCrudService()).listAll(offset,
					count);
		}
		return ((DictionaryService) this.getCrudService()).listByType(type,
				offset, count);
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		String code = getTypeCode(request);
		if (Constants.DICT_TYPE_TEACH_LEVEL.equals(code)) {
			return this.generateStringSortedSet("name", "typeName",
					"courseFee", "accountName", "createDate");
		}
		return this.generateStringSortedSet("name", "typeName", "accountName",
				"createDate");
	}

	public String getTypeCode(HttpServletRequest request) {
		Integer type = getTypeId(request);
		if (type != 0) {
			DictCategory dc = dictCategoryService.findById(type);
			if (dc != null) {
				return dc.getCode();
			}
		}
		return null;
	}

	public Integer getTypeId(HttpServletRequest request) {
		String type = request.getParameter("_type");
		if (StringUtils.hasText(type)) {
			return Integer.parseInt(type);
		}
		return null;
	}
}
