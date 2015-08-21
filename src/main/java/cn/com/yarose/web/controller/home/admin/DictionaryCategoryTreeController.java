package cn.com.yarose.web.controller.home.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.yarose.base.DictCategory;
import cn.com.yarose.base.DictCategoryService;
import cn.com.yarose.web.controller.BaseTreeControllerExt;

@Controller
@RequestMapping("/home/admin/dictCategory/tree")
public class DictionaryCategoryTreeController extends
		BaseTreeControllerExt<Long> {

	@Resource(name = "dictCategoryService")
	DictCategoryService dictCategoryService;

	@Override
	public List<DictCategory> customList(Long parentId,
			HttpServletRequest request, ResponseObject response) {
		if (parentId == 0) {
			return dictCategoryService.listAll(-1, -1);
		}
		return null;
	}
}
